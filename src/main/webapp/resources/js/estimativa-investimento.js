export function estimarValorFinal(valor, taxaAnualPercentual, dataInicioTexto, dataFimTexto){
    const inicio = new Date(dataInicioTexto);
    const fim = new Date(dataFimTexto);

    const dias = Math.round((fim - inicio) / 86400000);

    if (!(valor > 0) || !(taxaAnualPercentual >= 0) || !(dias > 0)) return null;

    const taxaAnual = taxaAnualPercentual / 100;
    return valor * Math.pow(1 + taxaAnual, dias / 365);
}

function formatarMoeda(numero){
    return numero.toLocaleString("pt-BR", {style: "currency", currency: "BRL"});
}

document.addEventListener("DOMContentLoaded", function(){
    const campoValor = document.getElementById("valor");
    const campoRendimento = document.getElementById("rendimento");
    const campoInicio = document.getElementById("dataInvestimento");
    const campoFim = document.getElementById("dataVencimento");
    const saida = document.querySelector('[data-estimativa="form"]');

    if (campoValor && campoRendimento && campoInicio && campoFim && saida){
        function atualizar(){
            const resultado = estimarValorFinal(
                Number(campoValor.value), Number(campoRendimento.value),
                campoInicio.value, campoFim.value);

            saida.textContent = resultado === null ? "" :
                "Estimativa bruta até o vencimento: " + formatarMoeda(resultado) + " (sem impostos, IOF ou taxas).";
        }

        [campoValor, campoRendimento, campoInicio, campoFim].forEach(function(campo){
            campo.addEventListener("input", atualizar);
        });
        atualizar();
    }

    document.querySelectorAll('[data-estimativa="linha"]').forEach(function (celula){
        const resultado = celula.dataset.status === "Resgatado" ? null :
            estimarValorFinal(Number(celula.dataset.valor), Number(celula.dataset.rendimento), celula.dataset.inicio, celula.dataset.fim);

        celula.textContent = resultado === null ? "-" : formatarMoeda(resultado);
    });
});