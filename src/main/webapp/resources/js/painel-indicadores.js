import {carregarIndicadores, formatarPercentual} from "./indicadores.js";

const ITENS = [
    { chave: "selic", rotulo: "Selic", detalhe: "ao ano" },
    { chave: "cdi", rotulo: "CDI", detalhe: "ao ano" },
    { chave: "ipca", rotulo: "IPCA", detalhe: "12 meses" },
];

function criarPainel(container, indicadores){
    const linha = document.createElement("div");
    linha.className = "d-flex flex-wrap gap-3 mb-2";

    ITENS.forEach(function(item){
        const valor = indicadores[item.chave];

        if (valor === undefined) return;

        const cartao = document.createElement("div");
        cartao.className = "border rounded px-3 py-2";

        const rotulo = document.createElement("div");
        rotulo.className = "small text-muted";
        rotulo.textContent = item.rotulo + " (" + item.detalhe + ")";

        const numero = document.createElement("strong");
        numero.style.color = "#1F2A44";
        numero.textContent = formatarPercentual(valor);

        cartao.appendChild(rotulo);
        cartao.appendChild(numero);
        linha.appendChild(cartao);
    });

    const aviso = document.createElement("p");
    aviso.className = "small text-muted mb-3";
    aviso.textContent = "Valores de referência obtidos da Brasil API. Não constituem recomendação de investimento.";

    container.appendChild(linha);
    container.appendChild(aviso);
}

function criarDica(container, indicadores){
    if (indicadores.cdi === undefined || indicadores.selic === undefined) return;

    container.textContent = "Referência: CDI " + formatarPercentual(indicadores.cdi) + " ao ano, Selic " + formatarPercentual(indicadores.selic) + " ao ano (Brasil API).";
}

document.addEventListener("DOMContentLoaded", function(){
    const painel = document.querySelector('[data-indicadores="painel"]');
    const dica = document.querySelector('[data-indicadores="dica"]');

    if (!painel && !dica) return;

    carregarIndicadores()
        .then(function(indicadores){
            if (painel) criarPainel(painel, indicadores);
            if (dica) criarDica(dica, indicadores);
        })
        .catch(function(){

        });
});