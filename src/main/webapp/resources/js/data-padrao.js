function hojeLocal(){
    const agora = new Date();
    const mes = String(agora.getMonth() + 1).padStart(2, "0");
    const dia = String(agora.getDate()).padStart(2, "0");
    return agora.getFullYear() + "-" + mes + "-" + dia;
}

document.addEventListener("DOMContentLoaded", function(){
    document.querySelectorAll("[data-padrao-hoje]").forEach(function(campo){
        if (!campo.value) campo.value = hojeLocal();
    });
});