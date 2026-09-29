const CHAVE_CACHE = "syncfin.bancos";

export function nomeDe(banco){
    return banco.fullName || banco.name;
}

export function codigoDe(banco) {
    return String(banco.code).padStart(3,"0");
}

export function normalizar(texto){
    return texto.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase();
}

export function carregarBancos(){
    try{
        const guardado = sessionStorage.getItem(CHAVE_CACHE);
        if (guardado) return Promise.resolve(JSON.parse(guardado));
    } catch (e) {

    }

    return fetch("https://brasilapi.com.br/api/banks/v1")
        .then(function(resp) { return resp.json(); })
        .then(function (dados) {
            const bancos = dados.filter(function(banco){
                const nome = nomeDe(banco);
                return banco.code && nome && nome.length <= 100;
            })
                .map(function(banco){
                    return {
                        code: banco.code,
                        name: banco.name,
                        fullName: banco.fullName,
                        logo_url: banco.logo_url,
                    };
                });

            try {
                sessionStorage.setItem(CHAVE_CACHE, JSON.stringify(bancos));
            } catch (e) {

            }
            return bancos;
        });

}

