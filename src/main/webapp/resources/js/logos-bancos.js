import {carregarBancos, nomeDe, normalizar} from "./catalogo-bancos.js";

const TAMANHO = 32;

function inicialDe(nome){
    return nome.trim().charAt(0).toUpperCase() || "?";
}

function criarInicial(nome){
    const circulo = document.createElement("span");
    circulo.className = "d-inline-flex align-items-center justify-content-center rounded-circle text-white fw-bold flex-shrink-0";
    circulo.style.width = TAMANHO + "px";
    circulo.style.height = TAMANHO + "px";
    circulo.style.backgroundColor = "#1F2A44";
    circulo.textContent = inicialDe(nome);
    return circulo;
}

function trocarPorLogo(marcador, url){
    const logo = new Image();
    logo.alt = "";
    logo.width = TAMANHO;
    logo.height = TAMANHO;
    logo.style.objectFit = "contain";
    logo.className = "flex-shrink-0";

    logo.addEventListener("load", function(){
        marcador.replaceChildren(logo);
    })
    logo.src = url;
}

document.addEventListener("DOMContentLoaded", function(){
    const marcadores = document.querySelectorAll("[data-banco]");

    if (marcadores.length === 0) return;

    marcadores.forEach(function(marcador){
        marcador.appendChild(criarInicial(marcador.dataset.banco));
    });

    carregarBancos()
        .then(function(bancos){
            const porNome = new Map();

            bancos.forEach(function(banco){
                porNome.set(normalizar(nomeDe(banco)), banco);
            });

            marcadores.forEach(function(marcador){
                const banco = porNome.get(normalizar(marcador.dataset.banco));

                if (banco && banco.logo_url && banco.logo_url.startsWith("https://")){
                    trocarPorLogo(marcador, banco.logo_url);
                }
            });

        })
        .catch(function(){

        })
});




