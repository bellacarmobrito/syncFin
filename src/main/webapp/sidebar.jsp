<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="sidebar d-none d-md-flex flex-column flex-shrink-0 p-3" style="background-color: #1F2A44">
    <a href="home" class="d-flex align-items-center mb-4 text-white text-decoration-none">
        <img src="./resources/images/SyncFinWhite.png" width="36" class="me-2" alt="SyncFin Logo">
        <span class="fs-5 fw-semibold">SyncFin</span>
    </a>

    <ul class="nav nav-pills flex-column gap-2">

        <li class="nav-item">
            <a href="home" class="nav-link text-white">
                <i class="bi bi-grid me-2"></i> Dashboard
            </a>
        </li>

        <li class="sidebar-section">Contas</li>
        <li>
            <a href="cadastro-conta.jsp" class="nav-link text-white-50">
                <i class="bi bi-plus-circle me-2"></i> Nova conta
            </a>
        </li>
        <li>
            <a href="conta-bancaria?acao=listar" class="nav-link text-white-50">
                <i class="bi bi-bank me-2"></i> Minhas contas
            </a>
        </li>

        <li class="sidebar-section">Receitas</li>
        <li>
            <a href="cadastro-receita.jsp" class="nav-link text-white-50">
                <i class="bi bi-arrow-down-circle me-2"></i> Nova receita
            </a>
        </li>
        <li>
            <a href="receita?acao=listar" class="nav-link text-white-50">
                <i class="bi bi-list-ul me-2"></i> Listar receitas
            </a>
        </li>

        <li class="sidebar-section">Despesas</li>
        <li>
            <a href="cadastro-despesa.jsp" class="nav-link text-white-50">
                <i class="bi bi-arrow-up-circle me-2"></i> Nova despesa
            </a>
        </li>
        <li>
            <a href="despesa?acao=listar" class="nav-link text-white-50">
                <i class="bi bi-list-ul me-2"></i> Listar despesas
            </a>
        </li>

        <li class="sidebar-section">Investimentos</li>
        <li>
            <a href="cadastro-investimento.jsp" class="nav-link text-white-50">
                <i class="bi bi-graph-up-arrow me-2"></i> Novo investimento
            </a>
        </li>
        <li>
            <a href="investimento?acao=listar" class="nav-link text-white-50">
                <i class="bi bi-list-ul me-2"></i> Listar investimentos
            </a>
        </li>
    </ul>

    <div class="mt-auto pt-3 border-top border-secondary">
        <a href="visualizar-cadastro.jsp" class="nav-link text-white-50">
            <i class="bi bi-person me-2"></i> Meu perfil
        </a>
        <a href="logout" class="nav-link text-white-50">
            <i class="bi bi-box-arrow-right me-2"></i> Sair
        </a>
    </div>
</div>