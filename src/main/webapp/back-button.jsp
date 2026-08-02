<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    String fallback = request.getParameter("fallback");
    if (fallback == null || fallback.isBlank()) fallback = "home";

    String label = request.getParameter("label");
    if (label == null || label.isBlank()) label = "Voltar";

    request.setAttribute("fallbackValue", fallback);
    request.setAttribute("labelValue", label);
%>

<div class="container mt-4">
    <button type="button"
            class="btn btn-link p-0 text-decoration-none d-inline-flex align-items-center gap-2"
            style="color:#1F2A44"
            data-fallback="<c:out value="${fallbackValue}"/>"
            onclick="goBackOrFallback(this.dataset.fallback)"
            aria-label="<c:out value="${labelValue}" />">
        <i class="bi bi-arrow-left-circle fs-4 fw-bold"></i>
        <strong style="font-size:14px;"><c:out value="${labelValue}" /></strong>
    </button>
</div>

<script>

    if (typeof goBackOrFallback !== "function") {
        function goBackOrFallback(fallbackUrl) {
            try {
                const ref = document.referrer || "";
                const isFromLogin = ref.includes("index.jsp") || ref.includes("/login");


                if (isFromLogin) {
                    window.location.href = fallbackUrl;
                    return;
                }


                if (window.history.length > 1) {
                    window.history.back();
                } else {
                    window.location.href = fallbackUrl;
                }
            } catch (e) {
                window.location.href = fallbackUrl;
            }
        }
    }
</script>
