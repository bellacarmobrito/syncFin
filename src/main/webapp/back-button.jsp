<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String fallback = request.getParameter("fallback");
    if (fallback == null || fallback.isBlank()) fallback = "home";

    String label = request.getParameter("label");
    if (label == null || label.isBlank()) label = "Voltar";
%>

<div class="container mt-4">
    <button type="button"
            class="btn btn-link p-0 text-decoration-none d-inline-flex align-items-center gap-2"
            style="color:#1F2A44"
            onclick="goBackOrFallback('<%= fallback %>')"
            aria-label="<%= label %>">
        <i class="bi bi-arrow-left-circle fs-4 fw-bold"></i>
        <strong style="font-size:14px;"><%= label %></strong>
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
