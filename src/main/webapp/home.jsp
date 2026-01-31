<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>SyncFin - Home</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=Mulish:ital,wght@0,200..1000;1,200..1000&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="./resources/css/bootstrap.css">
    <link rel="stylesheet" href="./resources/css/global.css">
</head>
<body class="d-flex flex-column min-vh-100">
<%@include file="header.jsp" %>

<h2 class="mt-5 mb-4 text-center">
    Olá, ${cliente.nomeCliente}, Bem-vindo (a) de volta ao <span class="fw-bolder">SyncFin</span>!
</h2>

<div class="container mb-5">
    <div class="row">
        <div class="col-md-6 d-flex flex-column gap-3">
            <div class="card shadow-sm">
                <div class="card-header d-flex justify-content-between align-items-center"
                     style="background-color: #1F2A44">
                    <p class="text-white m-0"><strong>Saldo Disponível</strong></p>
                    <button class="btn btn-sm btn-outline-secondary text-white" onclick="toggleSaldo()">
                        Mostrar/Esconder
                    </button>
                </div>
                <div class="card-body">
                    <strong id="saldoValor" class="card-title f-6" style="color: #1F2A44">
                        R$ <fmt:formatNumber value="${saldoTotal}" type="number" minFractionDigits="2"/>
                    </strong>
                </div>
            </div>

            <div class="card shadow-sm">
                <div class="card-header" style="background-color: #1F2A44"><strong class="text-white">Contas
                    Bancárias</strong></div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${empty contas}">
                            <div class="alert alert-info">
                                Você ainda não cadastrou nenhuma conta bancária.
                                <a href="cadastro-conta.jsp" class="btn btn-sm btn-primary ms-2">Cadastrar Conta</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <ul class="list-group list-group-flush">
                                <c:forEach items="${contas}" var="conta">
                                    <li class="list-group-item d-flex justify-content-between align-items-center">
                                        <div>
                                            <strong>${conta.nomeInstituicao}</strong><br>
                                            <p> Agência ${conta.agencia}, Conta ${conta.numeroConta} (${conta.tipoConta})</p>
                                        </div>
                                        <span class="badge text-white fw-light" style="background-color: #1F2A44">
                                        <fmt:formatNumber value="${conta.saldo}" type="currency" currencySymbol="R$"/>
                                    </span>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="card shadow-sm h-100">
                <div class="card-header text-white" style="background-color: #1F2A44"><strong>Resumo Financeiro</strong>
                </div>
                <div class="card-body">
                    <p><strong>Receitas:</strong> R$ <fmt:formatNumber value="${totalReceitas}" type="number"
                                                                       minFractionDigits="2"/></p>
                    <p><strong>Despesas:</strong> R$ <fmt:formatNumber value="${totalDespesas}" type="number"
                                                                       minFractionDigits="2"/></p>
                    <p><strong>Investimentos:</strong> R$ <fmt:formatNumber value="${totalInvestimentos}" type="number"
                                                                            minFractionDigits="2"/></p>
                </div>
            </div>
        </div>
    </div>
    <%@include file="dashboard.jsp" %>
</div>
<%@include file="footer.jsp" %>
<script>
    function toggleSaldo() {
        const saldo = document.getElementById("saldoValor");
        if (saldo.style.display === "none") {
            saldo.style.display = "block";
        } else {
            saldo.style.display = "none";
        }
    }
</script>
<script src="resources/js/bootstrap.bundle.js"></script>
</body>
</html>