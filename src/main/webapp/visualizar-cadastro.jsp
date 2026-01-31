<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Meu Cadastro</title>
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
    <div class="form-wrapper">

        <div class="card shadow-sm">
            <div class="card-header text-black bg-white">
                <h5 class="mb-0 fw-bold">Seus dados de cadastro</h5>
            </div>
            <div class="card-body align-items-center">

                <form>
                    <div class="mb-3">
                        <label class="form-label"><strong>Nome</strong></label><br>
                        <input type="text" class="form-control" value="${cliente.nomeCliente}" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label"><strong>Telefone</strong></label><br>
                        <input type="text" class="form-control" value="${cliente.celular}" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label"><strong>CPF</strong></label><br>
                        <input type="text" class="form-control" value="${cliente.cpf}" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label"><strong>Email</strong></label><br>
                        <input type="email" class="form-control" value="${cliente.email}" readonly>
                    </div>

                    <div class="text-center mt-4">
                        <a href="cadastro?acao=abrir-form-edicao&codigo=${cliente.idCliente}" class="btn fw-bold"
                           style="background-color: #1F2A44; color: white; font-size: 15px;">
                            Editar Cadastro
                        </a>
                    </div>
                </form>

            </div>
        </div>

    </div>
</main>

<%@include file="footer.jsp" %>

<script src="resources/js/bootstrap.bundle.js"></script>
</body>
</html>
