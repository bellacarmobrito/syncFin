import {criarAutocomplete} from "./autocomplete.js";

document.addEventListener("DOMContentLoaded", function() {
    const campo = document.getElementById("id-instituicao");

    if (!campo) return;
    let bancos = [];

    function nomeDe(banco){
        return banco.fullName || banco.name;
    }

    function codigoDe(banco) {
        return String(banco.code).padStart(3,"0");
    }

    function normalizar(texto){
        return texto.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase();
    }

    criarAutocomplete(campo, {
        buscar: function(termo) {
            const busca = normalizar(termo);
            return bancos.filter(function (banco) {
                return normalizar(nomeDe(banco)).includes(busca) || codigoDe(banco).startsWith(busca);
            }).slice(0,8);
        },
        texto: function(banco) {
            return codigoDe(banco) + " - " + nomeDe(banco);
        },
        valor: nomeDe,
        imagem: function(banco){
            return banco.logo_url;
        }
    })

    fetch("https://brasilapi.com.br/api/banks/v1")
        .then(function(resp) { return resp.json(); })
        .then(function (dados) {

            bancos = dados.filter(function(banco){
                const nome = nomeDe(banco);
                return banco.code && nome && nome.length <= 100;
            })

        })
        .catch(function(){
            // Falha na consulta não impede a digitação manual do banco.
        })
})