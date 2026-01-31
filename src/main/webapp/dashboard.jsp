<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="accordion mt-4 shadow-sm" id="dashboardAccordion">
    <div class="accordion-item">
        <h2 class="accordion-header" id="headingReceita">
            <button class="accordion-button collapsed custom-arrow text-white" style="background-color: #1F2A44"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#collapseReceita"
                    aria-expanded="false"
                    aria-controls="collapseReceita">
                Receitas Recentes
            </button>
        </h2>
        <div id="collapseReceita" class="accordion-collapse collapse"
             aria-labelledby="headingReceita"
             data-bs-parent="#dashboardAccordion">
            <div class="accordion-body">
                <c:choose>
                    <c:when test="${empty receitas}">
                        <div class="alert alert-info">Nenhuma receita cadastrada.</div>
                        <a href="cadastro-receita.jsp" class="btn btn-sm btn-primary ms-2">Cadastrar Receita</a>
                    </c:when>
                    <c:otherwise>
                        <table class="table table-striped table-sm">
                            <thead>
                            <tr>
                                <th>Categoria</th>
                                <th>Descrição</th>
                                <th>Valor</th>
                                <th>Recebimento</th>
                                <th>Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${receitas}" var="r">
                                <tr>
                                    <td>${r.categoria}</td>
                                    <td>${r.descricao}</td>
                                    <td><fmt:formatNumber value="${r.valor}" type="currency" currencySymbol="R$"/></td>
                                    <td>
                                        <fmt:parseDate value="${r.dataRecebimento}" pattern="yyyy-MM-dd" var="dataR"/>
                                        <fmt:formatDate value="${dataR}" pattern="dd/MM/yyyy"/>
                                    </td>
                                    <td>${r.status}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <div class="accordion-item">
        <h2 class="accordion-header" id="headingDespesa">
            <button class="accordion-button collapsed text-white custom-arrow" style="background-color: #1F2A44"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#collapseDespesa"
                    aria-expanded="false"
                    aria-controls="collapseDespesa">
                Despesas Recentes
            </button>
        </h2>
        <div id="collapseDespesa" class="accordion-collapse collapse"
             aria-labelledby="headingDespesa"
             data-bs-parent="#dashboardAccordion">
            <div class="accordion-body">
                <c:choose>
                    <c:when test="${empty despesas}">
                        <div class="alert alert-info">Nenhuma despesa cadastrada.</div>
                        <a href="cadastro-despesa.jsp" class="btn btn-sm btn-primary ms-2">Cadastrar Despesa</a>
                    </c:when>
                    <c:otherwise>
                        <table class="table table-striped table-sm">
                            <thead>
                            <tr>
                                <th>Categoria</th>
                                <th>Descrição</th>
                                <th>Valor</th>
                                <th>Vencimento</th>
                                <th>Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${despesas}" var="d">
                                <tr>
                                    <td>${d.categoria}</td>
                                    <td>${d.descricao}</td>
                                    <td><fmt:formatNumber value="${d.valor}" type="currency" currencySymbol="R$"/></td>
                                    <td>
                                        <fmt:parseDate value="${d.vencimento}" pattern="yyyy-MM-dd" var="dataD"/>
                                        <fmt:formatDate value="${dataD}" pattern="dd/MM/yyyy"/>
                                    </td>
                                    <td>${d.status}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <div class="accordion-item">
        <h2 class="accordion-header" id="headingInvestimento">
            <button class="accordion-button collapsed custom-arrow text-white" style="background-color: #1F2A44"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#collapseInvestimento"
                    aria-expanded="false"
                    aria-controls="collapseInvestimento">
                Investimentos Recentes
            </button>
        </h2>
        <div id="collapseInvestimento" class="accordion-collapse collapse"
             aria-labelledby="headingInvestimento"
             data-bs-parent="#dashboardAccordion">
            <div class="accordion-body">
                <c:choose>
                    <c:when test="${empty investimentos}">
                        <div class="alert alert-info">Nenhum investimento cadastrado.</div>
                        <a href="cadastro-investimento.jsp" class="btn btn-sm btn-primary ms-2">Cadastrar
                            Investimento</a>
                    </c:when>
                    <c:otherwise>
                        <table class="table table-striped table-sm">
                            <thead>
                            <tr>
                                <th>Tipo</th>
                                <th>Valor</th>
                                <th>Rendimento</th>
                                <th>Data Investimento</th>
                                <th>Vencimento</th>
                                <th>Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${investimentos}" var="i">
                                <tr>
                                    <td>${i.tipoInvestimento}</td>
                                    <td><fmt:formatNumber value="${i.valor}" type="currency" currencySymbol="R$"/></td>
                                    <td>${i.rendimento}</td>
                                    <td>
                                        <fmt:parseDate value="${i.dataInvestimento}" pattern="yyyy-MM-dd" var="dataI"/>
                                        <fmt:formatDate value="${dataI}" pattern="dd/MM/yyyy"/>
                                    </td>
                                    <td>
                                        <fmt:parseDate value="${i.dataVencimento}" pattern="yyyy-MM-dd" var="dataV"/>
                                        <fmt:formatDate value="${dataV}" pattern="dd/MM/yyyy"/>
                                    </td>
                                    <td>${i.status}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
