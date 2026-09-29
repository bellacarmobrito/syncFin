import {criarAutocomplete} from "./autocomplete.js";
import {carregarBancos, nomeDe, codigoDe, normalizar} from "./catalogo-bancos.js";

document.addEventListener("DOMContentLoaded", function() {
    const campo = document.getElementById("id-instituicao");

    if (!campo) return;
    let bancos = [];

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
    });

    carregarBancos()
        .then(function(lista) {bancos = lista;})
        .catch(function() {
        });
});