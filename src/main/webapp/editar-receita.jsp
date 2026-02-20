<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Atualização de Receita - SyncFin</title>
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

<jsp:include page="back-button.jsp">
    <jsp:param name="fallback" value="receita?acao=listar"/>
    <jsp:param name="label" value="Voltar"/>
</jsp:include>

<main class="container my-5">
    <div class="form-wrapper">
        <div class="card mb-3 shadow-sm">
            <div class="card-header text-black bg-white border-0">
                <h5 class="card-header bg-white border-0 fw-bold" style="color: #1F2A44">Atualização de Receita</h5>
            </div>

            <c:if test="${not empty mensagem}">
                <div class="alert alert-success ms-2 me-2 mt-2">${mensagem}</div>

                <div class="alert alert-info ms-2 me-2 mt-2">
                    Você será redirecionado para a lista de receitas em instantes...
                </div>

                <script>
                    setTimeout(function () {
                        window.location.href = "receita?acao=listar";
                    }, 5000);
                </script>
            </c:if>

            <c:if test="${not empty erro}">
                <div class="alert alert-danger ms-2 me-2 mt-2">${erro}</div>
            </c:if>

            <div class="card-body">
                <form action="receita" method="post">

                    <input type="hidden" name="acao" value="atualizar">
                    <input type="hidden" name="id" value="${receita.id}">

                    <div class="mb-3">
                        <label for="categoria"><strong>Categoria</strong></label><br>
                        <select name="categoria" id="categoria" class="form-select" required>
                            <option value="">Selecione uma categoria</option>
                            <option value="Salário" ${receita.categoria == 'Salário' ? 'selected' : ''}>Salário</option>
                            <option value="Extra" ${receita.categoria == 'Extra' ? 'selected' : ''}>Renda Extra</option>
                            <option value="Reembolso" ${receita.categoria == 'Reembolso' ? 'selected' : ''}>Reembolso
                            </option>
                            <option value="Investimentos" ${receita.categoria == 'Investimentos' ? 'selected' : ''}>
                                Investimentos
                            </option>
                            <option value="Outros" ${receita.categoria == 'Outros' ? 'selected' : ''}>Outros</option>
                        </select>
                    </div>


                    <div class="mb-3">
                        <label for="dataRecebimento"><strong>Data de Recebimento</strong></label><br>
                        <input type="date" name="dataRecebimento" id="dataRecebimento" class="form-control" required
                               value="${receita.dataRecebimento}">
                    </div>

                    <div class="mb-3">
                        <label for="descricao"><strong>Descrição</strong></label><br>
                        <input type="text" name="descricao" id="descricao" class="form-control"
                               value="${receita.descricao}">
                    </div>

                    <div class="mb-3">
                        <label for="status"><strong>Status</strong></label><br>
                        <select name="status" id="status" class="form-select" required>
                            <option value="">Selecione um status</option>
                            <option value="Recebido" ${receita.status == 'Recebido' ? 'selected': ''}>Recebido</option>
                            <option value="Pendente" ${receita.status == 'Pendente' ? 'selected': ''}>Pendente</option>
                            <option value="Programada" ${receita.status == 'Programada' ? 'selected': ''}>Programada
                            </option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="valor"><strong>Valor da Receita</strong></label><br>
                        <input type="number" name="valor" step="0.01" min="0.01" id="valor" class="form-control"
                               required value="${receita.valor}">
                    </div>


                    <div class="d-flex justify-content-center mt-4 gap-2">
                        <input type="submit" value="Atualizar" class="btn btn-primary w-auto">
                        <a href="receita?acao=listar" class="btn btn-secondary fw-bold w-auto">Cancelar</a></div>
                </form>
            </div>
        </div>
    </div>
</main>
<%@include file="footer.jsp" %>
<script src="resources/js/bootstrap.bundle.js"></script>
</body>
</html>
