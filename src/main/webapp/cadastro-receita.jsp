<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registro de Receita</title>
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
<main class="container my-5">
    <div class="form-wrapper">
        <div class="card mb-3 shadow-sm">
            <div class="card-header text-black bg-white border-0">
                <h5 class="card-header bg-white border-0 fw-bold" style="color: #1F2A44">Registro de Receita</h5>
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

                    <input type="hidden" name="acao" value="cadastrar">

                    <div class="mb-3">
                        <label for="categoria" class="form-label"><strong>Categoria</strong></label><br>
                        <select name="categoria" id="categoria" class="form-select" required>
                            <option value="">Selecione uma categoria</option>
                            <option value="Salário">Salário</option>
                            <option value="Extra">Renda Extra</option>
                            <option value="Reembolso">Reembolso</option>
                            <option value="Investimentos">Investimentos</option>
                            <option value="Outros">Outros</option>
                        </select>
                    </div>


                    <div class="mb-3">
                        <label for="dataRecebimento"><strong>Data de Recebimento</strong></label><br>
                        <input type="date" name="dataRecebimento" id="dataRecebimento" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label for="descricao"><strong>Descrição</strong></label><br>
                        <input type="text" name="descricao" id="descricao" class="form-control" autocomplete="off">
                    </div>

                    <div class="mb-3">
                        <label for="status"><strong>Status</strong></label><br>
                        <select name="status" id="status" class="form-select" required>
                            <option value="">Selecione um status</option>
                            <option value="Recebido">Recebido</option>
                            <option value="Pendente">Pendente</option>
                            <option value="Programada">Programada</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="valor"><strong>Valor da Receita</strong></label><br>
                        <input type="number" name="valor" step="0.01" min="0.01" id="valor" class="form-control"
                               required>
                    </div>

                    <div class="d-flex justify-content-center mt-4">
                        <input type="submit" value="Cadastrar" class="btn btn-primary mt-3 w-auto"></div>
                </form>
            </div>
        </div>
    </div>
</main>
<%@include file="footer.jsp" %>
<script src="resources/js/bootstrap.bundle.js"></script>
</body>
</html>
