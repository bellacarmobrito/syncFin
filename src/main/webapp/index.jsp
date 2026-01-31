<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>SyncFin - Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=Mulish:ital,wght@0,200..1000;1,200..1000&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="./resources/css/bootstrap.css">
    <link rel="stylesheet" href="./resources/css/global.css">
</head>
<body>
<%@include file="header-publico.jsp" %>
<div class="container d-flex align-items-center justify-content-center min-vh-90 mt-5 gap-3">
    <div class="row align-items-center justify-content-center mt-5">

        <div class="col-md-6">
            <h1 class="mb-3">Controle suas <span class="badge text-white fw-bold" style="background-color: #1F2A44">finanças</span> sem planilha.</h1><br>
            <p>Organizar as suas finanças nunca foi tão fácil. Com a <strong class="fw-bold">SyncFin</strong>, você tem tudo num único
                lugar.</p> <br>
            <a class="btn btn-primary" href="cadastro-cliente.jsp">Cadastre-se</a>
        </div>

        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-header text-black bg-white border-0">
                    <h5 class="card-header bg-white border-0 fw-bold text-center mt-2" style="color: #1F2A44">Login</h5>
                </div>
                <div class="card-body">
                    <c:if test="${not empty erro}">
                        <div class="alert alert-danger">${erro}</div>
                    </c:if>

                    <form action="login" method="post">

                        <div class="mb-3">
                            <label for="email" class="form-label"><strong>E-mail</strong></label>
                            <input type="email" name="email" id="email" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label for="senha" class="form-label"><strong>Senha</strong></label>
                            <input type="password" name="senha" id="senha" class="form-control" required>
                        </div>

                        <div class="d-flex justify-content-center mt-4 gap-2">
                            <button type="submit" class="btn btn-primary w-auto">Entrar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </div>
</div>


<%@include file="footer.jsp" %>
<script src="resources/js/bootstrap.bundle.js"></script>
</body>
</html>