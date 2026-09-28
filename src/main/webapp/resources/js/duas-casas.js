function formatarDuasCasas(campo) {
    if (campo.value !== "") {
        campo.value = Number(campo.value).toFixed(2);
    }
}

document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll("[data-duas-casas]").forEach(function (campo) {
        formatarDuasCasas(campo);
        campo.addEventListener("blur", function () {
            formatarDuasCasas(campo);
        });
    });
});