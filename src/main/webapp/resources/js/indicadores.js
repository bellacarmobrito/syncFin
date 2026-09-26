const CHAVE_CACHE = "syncfin.indicadores";

export function formatarPercentual(valor){
    return valor.toLocaleString("pt-BR", {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2,
    }) + "%";
}

export function carregarIndicadores(){
    try{
        const guardado = sessionStorage.getItem(CHAVE_CACHE);

        if (guardado) return Promise.resolve(JSON.parse(guardado));
    } catch (e) {

    }

    return fetch("https://brasilapi.com.br/api/taxas/v1")
        .then(function(resp){
            if (!resp.ok) throw new Error("Falha ao consultar taxas");
            return resp.json();
        })
        .then(function(dados){
            const indicadores = {};

            dados.forEach(function(taxa){
                if (taxa.nome && typeof taxa.valor === "number") {
                    indicadores[taxa.nome.toLowerCase()] = taxa.valor;
                }
            });

            try {
                sessionStorage.setItem(CHAVE_CACHE, JSON.stringify(indicadores));
            } catch (e) {

            }

            return indicadores;
        });
}

