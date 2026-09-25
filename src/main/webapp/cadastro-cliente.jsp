<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cadastro de Cliente</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=Mulish:ital,wght@0,200..1000;1,200..1000&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="./resources/css/bootstrap.css">
    <link rel="stylesheet" href="./resources/css/global.css">
</head>
<body>
<%@include file="header-publico.jsp" %>
<main class="container my-5">
    <div class="form-wrapper">
        <div class="card mb-3">
            <div class="card-header text-black bg-white border-0">
                <h5 class="card-header bg-white border-0 fw-bold" style="color: #1F2A44">Cadastre-se</h5>
            </div>

            <c:if test="${not empty mensagem}">
                <div class="alert alert-success ms-2 me-2 mt-2">${mensagem}</div>

                <div class="alert alert-info ms-2 me-2 mt-2">
                    Você será redirecionado para a tela de login em instantes...
                </div>

                <script>
                    setTimeout(function () {
                        window.location.href = "index.jsp";
                    }, 5000);
                </script>

            </c:if>

            <c:if test="${not empty erro}">
                <div class="alert alert-danger ms-2 me-2 mt-2">${erro}</div>
            </c:if>

            <div class="card-body">
                <form action="cadastro?acao=cadastrar" method="post">
                    <div class="mb-3">
                        <label for="id-nome"><strong>Nome</strong></label><br>
                        <input type="text" name="nomeCliente" id="id-nome" class="form-control" autocomplete="off" required>
                    </div>
                    <div class="mb-3">
                        <label for="id-telefone"><strong>Telefone</strong></label><br>
                        <input type="text" name="telefone" id="id-telefone" class="form-control" autocomplete="off" required>
                    </div>
                    <div class="mb-3">
                        <label for="id-cpf"><strong>CPF</strong></label><br>
                        <input type="text" name="cpf" id="id-cpf" class="form-control" autocomplete="off" required>
                    </div>
                    <div class="mb-3">
                        <label for="id-email"><strong>E-mail</strong></label><br>
                        <input type="email" name="email" id="id-email" class="form-control" autocomplete="off" required>
                    </div>
                    <div class="mb-3">
                        <label for="id-senha"><strong>Senha</strong></label><br>
                        <input type="password" name="senha" id="id-senha" class="form-control" autocomplete="off" required>
                    </div>

                    <p class="text-muted mb-3">Endereço (opcional) — preencha o CEP para completar automaticamente</p>

                    <div class="mb-3">
                        <label for="id-cep"><strong>CEP</strong></label><br>
                        <input type="text" name="cep" id="id-cep" class="form-control" autocomplete="off" maxlength="9" placeholder="00000-000">
                    </div>
                    <div class="mb-3">
                        <label for="id-logradouro"><strong>Logradouro</strong></label><br>
                        <input type="text" name="logradouro" id="id-logradouro" class="form-control" autocomplete="off">
                    </div>
                    <div class="mb-3">
                        <label for="id-numero"><strong>Número</strong></label><br>
                        <input type="number" name="numero" id="id-numero" class="form-control" autocomplete="off">
                    </div>
                    <div class="mb-3">
                        <label for="id-bairro"><strong>Bairro</strong></label><br>
                        <input type="text" name="bairro" id="id-bairro" class="form-control" autocomplete="off">
                    </div>
                    <div class="mb-3">
                        <label for="id-cidade"><strong>Cidade</strong></label><br>
                        <input type="text" name="cidade" id="id-cidade" class="form-control" autocomplete="off">
                    </div>
                    <div class="mb-3">
                        <label for="id-estado"><strong>Estado (UF)</strong></label><br>
                        <input type="text" name="estado" id="id-estado" class="form-control" autocomplete="off" maxlength="2">
                    </div>

                    <div class="d-flex justify-content-center">
                        <input type="submit" value="Cadastrar" class="btn btn-primary w-auto">
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>
<%@include file="footer.jsp" %>
<script src="resources/js/bootstrap.bundle.js"></script>
<script src="resources/js/viacep.js"></script>
</body>
</html>