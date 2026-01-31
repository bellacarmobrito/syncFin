<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Conta Bancária Necessária</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=Mulish:ital,wght@0,200..1000;1,200..1000&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="./resources/css/bootstrap.css">
    <link rel="stylesheet" href="./resources/css/global.css">
</head>
<body>
<%@include file="header.jsp" %>

<main class="container my-5">
    <div class="card text-center shadow-sm">
        <div class="card-header card-header text-black bg-white border-0">
            <h5 class="card-header bg-white border-0 fw-bold" style="color: #1F2A44">Atenção !</h5>
        </div>
        <div class="card-body">
            <c:choose>
                <c:when test="${param.origem == 'receita'}">
                    <strong class="card-title">Para cadastrar uma receita, você precisa ter uma conta bancária
                        vinculada.</strong>
                </c:when>
                <c:when test="${param.origem == 'despesa'}">
                    <strong class="card-title">Para cadastrar uma despesa, é necessário cadastrar uma conta
                        bancária.</strong>
                </c:when>
                <c:when test="${param.origem == 'investimento'}">
                    <strong class="card-title">Para cadastrar um investimento, você precisa primeiro informar uma conta
                        bancária.</strong>
                </c:when>
                <c:otherwise>
                    <strong class="card-title">Você precisa ter uma conta bancária cadastrada para acessar esta
                        funcionalidade.</strong>>
                </c:otherwise>
            </c:choose>
            <p class="card-text mt-3">Clique no botão abaixo para registrar sua conta.</p>
            <a href="cadastro-conta.jsp" class="btn btn-primary">Cadastrar Conta Bancária</a>
        </div>
        <div class="card-footer text-muted">
            SyncFin • Segurança e controle financeiro
        </div>
    </div>
</main>

<%@include file="footer.jsp" %>
<script src="resources/js/bootstrap.bundle.js"></script>
</body>
</html>
