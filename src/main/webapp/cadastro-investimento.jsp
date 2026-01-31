<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registro de Investimento - SyncFyn</title>
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
        <div class="card mb-3 shadow">
            <div class="card-header text-black bg-white border-0">
                <h5 class="card-header bg-white border-0 fw-bold" style="color: #1F2A44">Registro de Investimento</h5>
            </div>

            <c:if test="${not empty mensagem}">
                <div class="alert alert-success ms-2 me-2 mt-2">${mensagem}</div>

                <div class="alert alert-info ms-2 me-2 mt-2">
                    Você será redirecionado para a lista de investimentos em instantes...
                </div>

                <script>
                    setTimeout(function () {
                        window.location.href = "investimento?acao=listar";
                    }, 5000);
                </script>
            </c:if>

            <c:if test="${not empty erro}">
                <div class="alert alert-danger ms-2 me-2 mt-2">${erro}</div>
            </c:if>

            <div class="card-body">
                <form action="investimento" method="post">

                    <input type="hidden" name="acao" value="cadastrar">

                    <div class="mb-3">
                        <label for="tipoInvestimento" class="form-label"><strong>Tipo de
                            Investimento</strong></label><br>
                        <select name="tipoInvestimento" id="tipoInvestimento" class="form-select" required>
                            <option value="">Selecione um tipo</option>
                            <option value="Tesouro Direto">Tesouro Direto</option>
                            <option value="CDB / RDB">CDB / RDB</option>
                            <option value="LCI / LCA">LCI / LCA</option>
                            <option value="Fundos de Investimento">Fundos de Investimento</option>
                            <option value="Ações">Ações</option>
                            <option value="Criptomoedas">Criptomoedas</option>
                            <option value="Previdência Privada">Previdência Privada</option>
                            <option value="Outros">Outros</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="dataInvestimento"><strong>Data do Investimento</strong></label><br>
                        <input type="date" name="dataInvestimento" id="dataInvestimento" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label for="dataVencimento"><strong>Data de Vencimento</strong></label><br>
                        <input type="date" name="dataVencimento" id="dataVencimento" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label for="rendimento"><strong>Rendimento</strong></label><br>
                        <input type="number" name="rendimento" step="0.01" min="0.01" id="rendimento"
                               class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label for="recorrencia" class="form-label"><strong>Recorrência</strong></label><br>
                        <select name="recorrencia" id="recorrencia" class="form-select" required>
                            <option value="">Selecione a recorrência</option>
                            <option value="Único">Único</option>
                            <option value="Mensal">Mensal</option>
                            <option value="Bimestral">Bimestral</option>
                            <option value="Trimestral">Trimestral</option>
                            <option value="Semestral">Semestral</option>
                            <option value="Anual">Anual</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="status" class="form-label"><strong>Status</strong></label><br>
                        <select name="status" id="status" class="form-select" required>
                            <option value="">Selecione o status</option>
                            <option value="Ativo">Ativo</option>
                            <option value="Resgatado">Resgatado</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="valor"><strong>Valor do Investimento</strong></label><br>
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
