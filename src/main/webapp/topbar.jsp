<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="topbar d-flex justify-content-between align-items-center px-4 py-3 bg-white border-bottom">
    <h5 class="m-0 fw-semibold" style="color: #1F2A44;">Dashboard</h5>

    <div class="d-flex align-items-center gap-3">
        <div class="dropdown">
            <button class="btn btn-primary btn-sm dropdown-toggle" data-bs-toggle="dropdown">
                + Nova transação
            </button>
            <ul class="dropdown-menu dropdown-menu-end">
                <li><a class="dropdown-item" href="cadastro-receita.jsp">Receita</a></li>
                <li><a class="dropdown-item" href="cadastro-despesa.jsp">Despesa</a></li>
                <li><a class="dropdown-item" href="cadastro-investimento.jsp">Investimento</a></li>
            </ul>
        </div>

        <div class="dropdown">
            <button class="btn btn-light border dropdown-toggle d-flex align-items-center gap-2" data-bs-toggle="dropdown">
                <i class="bi bi-person-circle"></i>
                <span>${cliente.nomeCliente}</span>
            </button>
            <ul class="dropdown-menu dropdown-menu-end">
                <li><a class="dropdown-item" href="visualizar-cadastro.jsp">Visualizar cadastro</a></li>
                <li><a class="dropdown-item" href="cadastro?acao=abrir-form-edicao&codigo=${cliente.idCliente}">Editar cadastro</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" href="logout">Sair</a></li>
            </ul>
        </div>
    </div>
</div>