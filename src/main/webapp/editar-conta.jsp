<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Atualização Conta Bancária</title>
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
        <div class="card mb-3 shadow-sm">
            <div class="card-header text-black bg-white border-0">
                <h5 class="card-header bg-white border-0 fw-bold" style="color: #1F2A44">Atualize os dados da sua conta
                    bancária</h5>
            </div>

            <c:if test="${not empty mensagem}">
                <div class="alert alert-success ms-2 me-2 mt-2">${mensagem}</div>

                <div class="alert alert-info ms-2 me-2 mt-2">
                    Você será redirecionado para a lista de contas em instantes...
                </div>

                <script>
                    setTimeout(function () {
                        window.location.href = "conta-bancaria?acao=listar";
                    }, 5000);
                </script>
            </c:if>

            <c:if test="${not empty erro}">
                <div class="alert alert-danger ms-2 me-2 mt-2">${erro}</div>
            </c:if>

            <div class="card-body">
                <form action="conta-bancaria" method="post">

                    <input type="hidden" name="acao" value="atualizar">
                    <input type="hidden" name="idConta" value="${conta.idConta}">

                    <div class="mb-3">
                        <label for="id-instituicao"><strong>Instituição Bancária</strong></label><br>
                        <input type="text" name="instituicao" id="id-nome" class="form-control" required
                               autocomplete="off"
                               value="${conta.nomeInstituicao}">
                    </div>

                    <div class="mb-3">
                        <label for="id-agencia"><strong>Agência</strong></label><br>
                        <input type="text" name="agencia" id="id-agencia" class="form-control" required
                               autocomplete="off"
                               value="${conta.agencia}">
                    </div>

                    <div class="mb-3">
                        <label for="id-numero"><strong>Número da Conta</strong></label><br>
                        <input type="text" name="numeroConta" id="id-numero" class="form-control" required
                               autocomplete="off"
                               value="${conta.numeroConta}">
                    </div>

                    <div class="mb-3">
                        <label for="tipoConta"><strong>Tipo de Conta</strong></label><br>
                        <select name="tipoConta" id="tipoConta" class="form-select" required>
                            <option value="">Selecione o tipo</option>
                            <option value="Conta Corrente" ${conta.tipoConta == 'Conta Corrente' ? 'selected' : ''}>
                                Conta Corrente
                            </option>
                            <option value="Conta Poupança" ${conta.tipoConta == 'Conta Poupança' ? 'selected' : ''}>
                                Conta Poupança
                            </option>
                            <option value="Conta Salário" ${conta.tipoConta == 'Conta Salário' ? 'selected' : ''}>Conta
                                Salário
                            </option>
                            <option value="Conta Pagamento" ${conta.tipoConta == 'Conta Pagamento' ? 'selected' : ''}>
                                Conta Pagamento
                            </option>
                            <option value="Conta Investimento" ${conta.tipoConta == 'Conta Investimento' ? 'selected' : ''}>
                                Conta Investimento
                            </option>
                            <option value="Outros" ${conta.tipoConta == 'Outros' ? 'selected' : ''}>Outros</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="id-saldo"><strong>Saldo</strong></label><br>
                        <input type="number" step="0.01" name="saldo" id="id-saldo" class="form-control"
                               value="${conta.saldo}">
                    </div>

                    <div class="d-flex justify-content-center mt-4 gap-2">
                        <input type="submit" value="Atualizar" class="btn btn-primary w-auto ">
                        <a href="home" class="btn btn-secondary w-auto fw-bold">Cancelar</a>
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