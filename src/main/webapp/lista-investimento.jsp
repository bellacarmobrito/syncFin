<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Investimentos - SyncFin</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=Mulish:ital,wght@0,200..1000;1,200..1000&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="./resources/css/bootstrap.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="./resources/css/global.css">
</head>
<body>
<%@include file="header.jsp" %>
<div class="container">
    <div class="mt-5 ms-5 me-5">

        <div class="card mb-3 shadow-sm">
            <div class="card-header text-black bg-white border-0">
                <h5 class="card-header bg-white border-0 fw-bold" style="color: #1F2A44">Lista de Investimentos</h5>
            </div>
            <div class="card-body">

                <c:if test="${not empty mensagem}">
                    <div class="alert alert-success">${mensagem}</div>
                </c:if>
                <c:if test="${not empty erro}">
                    <div class="alert alert-danger">${erro}</div>
                </c:if>


                <c:choose>
                    <c:when test="${empty investimentos}">
                        <div class="alert alert-info">
                            Você ainda não cadastrou nenhum investimento.
                            <a href="cadastro-investimento.jsp" class="btn btn-sm btn-primary ms-2">Cadastrar
                                Investimento</a>
                        </div>
                    </c:when>

                    <c:otherwise>
                        <table class="table table-striped table-bordered">
                            <thead>
                            <tr>
                                <th class="text-start">Tipo de Investimento</th>
                                <th class="text-start">Data do Investimento</th>
                                <th class="text-start">Data de Vencimento</th>
                                <th class="text-start">Valor do Investimento</th>
                                <th class="text-start">Rendimento</th>
                                <th class="text-start">Recorrência</th>
                                <th class="text-start">Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${investimentos}" var="investimento">
                                <tr>
                                    <td class="text-start">${investimento.tipoInvestimento}</td>
                                    <td>
                                        <fmt:parseDate
                                                value="${investimento.dataInvestimento}"
                                                pattern="yyyy-MM-dd"
                                                var="dataVencimentoFmt"
                                        />
                                        <fmt:formatDate value="${dataVencimentoFmt}" pattern="dd/MM/yyyy"/>
                                    </td>
                                    <td>
                                        <fmt:parseDate
                                                value="${investimento.dataVencimento}"
                                                pattern="yyyy-MM-dd"
                                                var="dataVencimentoFmt"
                                        />
                                        <fmt:formatDate value="${dataVencimentoFmt}" pattern="dd/MM/yyyy"/>
                                    </td>
                                    <td class="text-start">
                                        <fmt:formatNumber
                                                value="${investimento.valor}"
                                                type="currency"
                                                currencySymbol="R$"
                                        />
                                    </td>

                                    <td>${investimento.rendimento}</td>
                                    <td>${investimento.recorrencia}</td>
                                    <td class="text-start">${investimento.status}</td>
                                    <td class="text-center">
                                        <c:url var="linkEditar" value="investimento">
                                            <c:param name="acao" value="editar"/>
                                            <c:param name="id" value="${investimento.id}"/>
                                        </c:url>
                                        <a href="${linkEditar}" class="btn btn-primary btn-sm">Editar</a>

                                        <button
                                                type="button"
                                                class="btn btn-secondary fw-bold btn-sm"
                                                data-bs-toggle="modal"
                                                data-bs-target="#excluirModal"
                                                onclick="codigoExcluir.value = ${investimento.id}">
                                            Excluir
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <a href="cadastro-investimento.jsp" class="btn btn-primary">Adicionar Investimento</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
<div
        class="modal fade"
        id="excluirModal"
        tabindex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h2
                        class="modal-title fs-6 fw-bold"
                        id="exampleModalLabel">
                    Confirmar Exclusão
                </h2>
                <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close">
                </button>
            </div>
            <div class="modal-body">
                <p>Tem certeza que deseja excluir este investimento ?</p>
                <p><strong>Atenção!</strong> Esta ação é irreversível.</p>
            </div>
            <div class="modal-footer">

                <form action="investimento" method="post">
                    <input
                            type="hidden"
                            name="acao"
                            value="excluir">
                    <input
                            type="hidden"
                            name="codigoExcluir"
                            id="codigoExcluir">
                    <button
                            type="button"
                            class="btn btn-secondary btn-sm"
                            data-bs-dismiss="modal">
                        Não
                    </button>
                    <button
                            type="submit"
                            class="btn btn-primary btn-sm">
                        Sim
                    </button>
                </form>

            </div>
        </div>
    </div>
</div>

<%@include file="footer.jsp" %>
<script src="resources/js/bootstrap.bundle.js"></script>
</body>
</html>