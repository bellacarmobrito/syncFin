<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Cadastro</title>
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
    <jsp:param name="fallback" value="visualizar-cadastro.jsp"/>
    <jsp:param name="label" value="Voltar"/>
</jsp:include>

<main class="container my-5">
    <div class="form-wrapper">
        <div class="card mb-3 shadow-sm">
            <h5 class="card-header bg-white border-0 fw-bold" style="color: #1F2A44">
                Editar Cadastro
            </h5>

            <c:if test="${not empty mensagem}">
                <div class="alert alert-success ms-2 me-2 mt-2">${mensagem}</div>

                <div class="alert alert-info ms-2 me-2 mt-2">
                    Você será redirecionado em instantes...
                </div>

            </c:if>

            <c:if test="${not empty erro}">
                <div class="alert alert-danger ms-2 me-2 mt-2">${erro}</div>
            </c:if>

            <div class="card-body">
                <form action="cadastro?acao=editar" method="post">

                    <input type="hidden" value="${cadastro.idCliente}" name="codigo">

                    <div class="mb-3">
                        <label for="id-nome"><strong>Nome</strong></label><br>
                        <input type="text" name="nomeCliente" id="id-nome" class="form-control"
                               value="<c:out value="${cadastro.nomeCliente}"/>" required>
                    </div>
                    <div class="mb-3">
                        <label for="id-telefone"><strong>Telefone</strong></label><br>
                        <input type="text" name="telefone" id="id-telefone" class="form-control"
                               value="<c:out value="${cadastro.celular}"/>" required>
                    </div>
                    <div class="mb-3">
                        <label for="id-cpf"><strong>CPF</strong></label><br>
                        <input type="text" name="cpf" id="id-cpf" class="form-control" value="<c:out value="${cadastro.cpf}"/>" required>
                    </div>
                    <div class="mb-3">
                        <label for="id-email"><strong>E-mail</strong></label><br>
                        <input type="text" name="email" id="id-email" class="form-control" value="<c:out value="${cadastro.email}"/>" required>
                    </div>
                    <div class="mb-3">
                        <label for="id-senha"><strong>Senha</strong></label><br>
                        <input type="password" name="senha" id="id-senha" class="form-control">
                    </div>

                    <p class="text-muted mb-3">Endereço (opcional) — preencha o CEP para completar automaticamente</p>

                    <div class="mb-3">
                        <label for="id-cep"><strong>CEP</strong></label><br>
                        <input type="text" name="cep" id="id-cep" class="form-control" maxlength="9" placeholder="00000-000"
                               value="<c:out value="${cadastro.endereco.cep}"/>">
                    </div>
                    <div class="mb-3">
                        <label for="id-logradouro"><strong>Logradouro</strong></label><br>
                        <input type="text" name="logradouro" id="id-logradouro" class="form-control"
                               value="<c:out value="${cadastro.endereco.logradouro}"/>">
                    </div>
                    <div class="mb-3">
                        <label for="id-numero"><strong>Número</strong></label><br>
                        <input type="number" name="numero" id="id-numero" class="form-control"
                               value="<c:out value="${cadastro.endereco.numero}"/>">
                    </div>
                    <div class="mb-3">
                        <label for="id-bairro"><strong>Bairro</strong></label><br>
                        <input type="text" name="bairro" id="id-bairro" class="form-control"
                               value="<c:out value="${cadastro.endereco.bairro}"/>">
                    </div>
                    <div class="mb-3">
                        <label for="id-cidade"><strong>Cidade</strong></label><br>
                        <input type="text" name="cidade" id="id-cidade" class="form-control"
                               value="<c:out value="${cadastro.endereco.cidade}"/>">
                    </div>
                    <div class="mb-3">
                        <label for="id-estado"><strong>Estado (UF)</strong></label><br>
                        <input type="text" name="estado" id="id-estado" class="form-control" maxlength="2"
                               value="<c:out value="${cadastro.endereco.estado}"/>">
                    </div>

                    <div class="d-flex justify-content-center mt-4 gap-2">
                        <button type="submit" value="Salvar" class="btn btn-primary">Salvar</button>
                        <button type="button" href="visualizar-cadastro.jsp" class="btn btn-secondary fw-bold">Cancelar</button>
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
