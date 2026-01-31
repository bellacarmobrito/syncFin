<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>SYNCFIN</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=Mulish:ital,wght@0,200..1000;1,200..1000&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="./resources/css/bootstrap.css">
    <link rel="stylesheet" href="./resources/css/global.css">
</head>
<body>
<%@include file="header.jsp" %>
<main class="container my-5 custom-bg text-dark">
    <div class="d-flex align-items-center justify-content-center min-vh-90">
        <div class="text-center mt-5">
            <h1 class="display-1 fw-bold" style="color: #1F2A44">404</h1>
            <p class="fs-2 fw-medium mt-4" style="color: #1F2A44">Oops! Página não encontrada</p>
            <p class="mt-4 ">A página que você está procurando não existe.</p>
            <a href="home" class="btn btn-primary fw-semibold rounded-pill px-4 py-2 custom-btn">
                Home
            </a>
        </div>
    </div>
</main>
<%@include file="footer.jsp" %>
<script src="resources/js/bootstrap.bundle.js"></script>
</body>
</html>