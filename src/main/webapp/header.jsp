<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<nav class="navbar navbar-dark navbar-expand-lg bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="home">
            <img src="./resources/images/SyncFinWhite.png" width="50" class="d-block ms-2" alt="SyncFin Logo">
        </a>

        <a class="btn btn-dark" href="home" style="font-size: 14px">Home</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse navbar-dark bg-dark" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">

                <div class="dropdown">
                    <button class="btn btn-dark dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"
                            style="font-size: 14px">
                        Conta Bancária
                    </button>
                    <ul class="dropdown-menu dropdown-menu-dark">
                        <li><a class="dropdown-item" href="cadastro-conta.jsp" style="font-size: 14px">Cadastrar Conta
                            Bancaria</a></li>
                        <li><a class="dropdown-item" href="conta-bancaria?acao=listar" style="font-size: 14px">Lista de
                            Contas Cadastradas</a>
                        </li>
                    </ul>
                </div>

                <div class="dropdown">
                    <button class="btn btn-dark dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"
                            style="font-size: 14px">
                        Receitas
                    </button>
                    <ul class="dropdown-menu dropdown-menu-dark">
                        <li><a class="dropdown-item" href="cadastro-receita.jsp" style="font-size: 14px">Cadastrar
                            Receita</a></li>
                        <li><a class="dropdown-item" href="receita?acao=listar" style="font-size: 14px">Lista de
                            Receitas</a></li>
                    </ul>
                </div>

                <div class="dropdown">
                    <button class="btn btn-dark dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"
                            style="font-size: 14px">
                        Despesas
                    </button>

                    <ul class="dropdown-menu dropdown-menu-dark">
                        <li><a class="dropdown-item" href="cadastro-despesa.jsp" style="font-size: 14px">Cadastrar
                            Despesa</a></li>
                        <li><a class="dropdown-item" href="despesa?acao=listar" style="font-size: 14px">Lista de
                            Despesas</a></li>
                    </ul>
                </div>
                <div class="dropdown">
                    <button class="btn btn-dark dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"
                            style="font-size: 14px">
                        Investimentos
                    </button>

                    <ul class="dropdown-menu dropdown-menu-dark">
                        <li><a class="dropdown-item" href="cadastro-investimento.jsp" style="font-size: 14px">Cadastrar
                            Investimento</a></li>
                        <li><a class="dropdown-item" href="investimento?acao=listar" style="font-size: 14px">Lista de
                            Investimentos</a></li>
                    </ul>
                </div>
            </ul>


            <c:if test="${empty cliente}">
                <span class="navbar-text text-danger" style="margin-right: 10px; font-size: 14px">
                        ${erro}
                </span>
            </c:if>

            <c:if test="${not empty cliente}">
                <div class="dropdown ms-auto">
                    <button class="btn btn-dark dropdown-toggle d-flex align-items-center gap-2"
                            data-bs-toggle="dropdown" aria-expanded="false" style="font-size: 14px">
                        <i class="bi bi-person-circle text-white fs-4"></i>
                        <span class="text-white">
                                ${cliente.nomeCliente}
                        </span>
                    </button>

                    <ul class="dropdown-menu dropdown-menu-dark dropdown-menu-end">
                        <li>
                            <a class="dropdown-item" href="visualizar-cadastro.jsp" style="font-size: 14px">
                                <i class="bi bi-person me-2"></i> Visualizar cadastro
                            </a>
                        </li>
                        <li>
                            <a class="dropdown-item" href="cadastro?acao=abrir-form-edicao&codigo=${cliente.idCliente}"
                               style="font-size: 14px">
                                <i class="bi bi-pencil me-2"></i> Editar cadastro
                            </a>
                        </li>

                        <li>
                            <hr class="dropdown-divider">
                        </li>

                        <li>
                            <a class="dropdown-item" href="login" style="font-size: 14px">
                                <i class="bi bi-box-arrow-right me-2"></i> Sair
                            </a>
                        </li>
                    </ul>
                </div>
            </c:if>


        </div>
    </div>
</nav>