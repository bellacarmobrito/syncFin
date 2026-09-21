document.addEventListener("DOMContentLoaded", function () {
    const campoCep = document.getElementById("id-cep");
    if (!campoCep) return;

    campoCep.addEventListener("blur", function () {
        const cep = campoCep.value.replace(/\D/g, "");
        if (cep.length !== 8) return;

        fetch("https://viacep.com.br/ws/" + cep + "/json/")
            .then(function (resp) { return resp.json(); })
            .then(function (data) {
                if (data.erro) return;

                const logradouro = document.getElementById("id-logradouro");
                const bairro = document.getElementById("id-bairro");
                const cidade = document.getElementById("id-cidade");
                const estado = document.getElementById("id-estado");
                const numero = document.getElementById("id-numero");

                if (logradouro) logradouro.value = data.logradouro || "";
                if (bairro) bairro.value = data.bairro || "";
                if (cidade) cidade.value = data.localidade || "";
                if (estado) estado.value = data.uf || "";
                if (numero) numero.focus();
            })
            .catch(function () {
                // Falha na consulta não impede o preenchimento manual do endereço.
            });
    });
});
