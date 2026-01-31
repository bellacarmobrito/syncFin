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
                        Meu Cadastro
                    </button>
                    <ul class="dropdown-menu dropdown-menu-dark">
                        <li><a class="dropdown-item" href="visualizar-cadastro.jsp" style="font-size: 14px">Visualizar
                            Cadastro</a></li>
                        <li><a class="dropdown-item" href="cadastro?acao=abrir-form-edicao&codigo=${cliente.idCliente}"
                               style="font-size: 14px">Editar
                            Cadastro</a></li>
                    </ul>
                </div>

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


            <c:if test="${empty user}">
                <span class="navbar-text text-danger" style="margin-right: 10px; font-size: 14px">
                        ${erro}
                </span>


                <form class="form-inline my-lg-0" action="login" method="post">
                    <div class="row">
                        <div class="col">
                            <input
                                    class="form-control mr-sm-2"
                                    type="text"
                                    name="email"
                                    placeholder="E-mail">
                        </div>
                        <div class="col">
                            <input
                                    class="form-control mr-sm-2"
                                    type="password"
                                    name="senha"
                                    placeholder="Senha">
                        </div>
                        <div class="col">
                            <button class="btn btn-outline-light my-2 my-sm-0"
                                    type="submit">Entrar
                            </button>
                        </div>
                    </div>
                </form>
            </c:if>
            <c:if test="${not empty user}">
                <span class="navbar-text" style="font-size: 14px; color: white">
                    ${user}
                    <a href="login" class="btn btn-light my-2 my-sm-0"
                       style="font-size: 14px; color: #1F2A44; margin-left: 10px">Sair</a>
                </span>
            </c:if>


        </div>
    </div>
</nav>