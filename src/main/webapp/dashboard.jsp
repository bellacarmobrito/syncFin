<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="row g-4 mt-2">

    <div class="col-12 col-lg-4">
        <div class="card shadow-sm h-100 border-0">
            <div class="card-header text-white d-flex justify-content-between align-items-center"
                 style="background-color: #1F2A44;">
                <strong>Receitas</strong>
                <i class="bi bi-arrow-down-circle"></i>
            </div>

            <div class="card-body">
                <c:choose>
                    <c:when test="${empty receitas}">
                        <div class="alert alert-info mb-3">Nenhuma receita cadastrada.</div>
                        <a href="cadastro-receita.jsp" class="btn btn-sm btn-primary">Cadastrar Receita</a>
                    </c:when>

                    <c:otherwise>
                        <div class="list-group list-group-flush">
                            <c:forEach items="${receitas}" var="r" begin="0" end="2">
                                <div class="list-group-item px-0">
                                    <div class="d-flex justify-content-between align-items-start">
                                        <div>
                                            <div class="fw-semibold">${r.categoria}</div>
                                            <small class="text-muted">${r.descricao}</small><br>

                                            <small class="text-muted">
                                                <fmt:parseDate value="${r.dataRecebimento}" pattern="yyyy-MM-dd"
                                                               var="dataR"/>
                                                <fmt:formatDate value="${dataR}" pattern="dd/MM/yyyy"/>
                                                • ${r.status}
                                            </small>
                                        </div>

                                        <span class="fw-bold text-success">
                                            <fmt:formatNumber value="${r.valor}" type="currency" currencySymbol="R$"/>
                                        </span>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <div class="mt-3 text-center">
                            <a href="receita?acao=listar" class="btn btn-primary btn-sm">Ver todas</a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <div class="col-12 col-lg-4">
        <div class="card shadow-sm h-100 border-0">
            <div class="card-header text-white d-flex justify-content-between align-items-center"
                 style="background-color: #1F2A44;">
                <strong>Despesas</strong>
                <i class="bi bi-arrow-up-circle"></i>
            </div>

            <div class="card-body">
                <c:choose>
                    <c:when test="${empty despesas}">
                        <div class="alert alert-info mb-3">Nenhuma despesa cadastrada.</div>
                        <a href="cadastro-despesa.jsp" class="btn btn-sm btn-primary">Cadastrar Despesa</a>
                    </c:when>

                    <c:otherwise>
                        <div class="list-group list-group-flush">
                            <c:forEach items="${despesas}" var="d" begin="0" end="2">
                                <div class="list-group-item px-0">
                                    <div class="d-flex justify-content-between align-items-start">
                                        <div>
                                            <div class="fw-semibold">${d.categoria}</div>
                                            <small class="text-muted">${d.descricao}</small><br>

                                            <small class="text-muted">
                                                <fmt:parseDate value="${d.vencimento}" pattern="yyyy-MM-dd"
                                                               var="dataD"/>
                                                <fmt:formatDate value="${dataD}" pattern="dd/MM/yyyy"/>
                                                • ${d.status}
                                            </small>
                                        </div>

                                        <span class="fw-bold text-danger">
                                            <fmt:formatNumber value="${d.valor}" type="currency" currencySymbol="R$"/>
                                        </span>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <div class="mt-3 text-center">
                            <a href="despesa?acao=listar" class="btn btn-primary btn-sm">Ver todas</a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <div class="col-12 col-lg-4">
        <div class="card shadow-sm h-100 border-0">
            <div class="card-header text-white d-flex justify-content-between align-items-center"
                 style="background-color: #1F2A44;">
                <strong>Investimentos</strong>
                <i class="bi bi-graph-up-arrow"></i>
            </div>

            <div class="card-body">
                <c:choose>
                    <c:when test="${empty investimentos}">
                        <div class="alert alert-info mb-3">Nenhum investimento cadastrado.</div>
                        <a href="cadastro-investimento.jsp" class="btn btn-sm btn-primary">Cadastrar Investimento</a>
                    </c:when>

                    <c:otherwise>
                        <div class="list-group list-group-flush">
                            <c:forEach items="${investimentos}" var="i" begin="0" end="2">
                                <div class="list-group-item px-0">
                                    <div class="d-flex justify-content-between align-items-start">
                                        <div>
                                            <div class="fw-semibold">${i.tipoInvestimento}</div>
                                            <small class="text-muted">
                                                Rendimento: ${i.rendimento}
                                            </small><br>

                                            <small class="text-muted">
                                                <fmt:parseDate value="${i.dataInvestimento}" pattern="yyyy-MM-dd"
                                                               var="dataI"/>
                                                <fmt:formatDate value="${dataI}" pattern="dd/MM/yyyy"/>
                                                • ${i.status}
                                            </small>
                                        </div>

                                        <span class="fw-bold" style="color: #1F2A44;">
                                            <fmt:formatNumber value="${i.valor}" type="currency" currencySymbol="R$"/>
                                        </span>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <div class="mt-3 text-center">
                            <a href="investimento?acao=listar" class="btn btn-primary btn-sm">Ver todos</a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

</div>
