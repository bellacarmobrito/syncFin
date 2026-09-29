export function criarAutocomplete(campo, opcoes){
    const caixa = document.createElement("div");
    caixa.className = "list-group position-absolute w-100 shadow d-none";
    caixa.style.zIndex = "1000";
    caixa.style.maxHeight = "260px";
    caixa.style.overflowY = "auto";

    campo.parentNode.style.position = "relative";
    campo.parentNode.appendChild(caixa);

    function esconder(){
        caixa.classList.add("d-none");
    }

    function mostrar() {
        const termo = campo.value.trim();

        caixa.textContent = "";

        if (termo.length < 2) {
            esconder();
            return;
        }

        const resultados = opcoes.buscar(termo);

        if (resultados.length === 0) {
            esconder();
            return;
        }

        resultados.forEach(function(item){
            const botao = document.createElement("button");
            botao.type = "button";
            botao.className = "list-group-item list-group-item-action d-flex align-items-center gap-2";

            const url = opcoes.imagem ? opcoes.imagem(item) : null;

            if (url && url.startsWith("https://")) {
                const logo = document.createElement("img");
                logo.src = url;
                logo.alt = "logo";
                logo.width = 24;
                logo.height = 24;
                logo.style.objectFit = "contain";
                logo.addEventListener("error", function(){
                    logo.remove();
                })
                botao.append(logo);
            }

            const texto = document.createElement("span");
            texto.textContent = opcoes.texto(item);
            botao.append(texto);

            botao.addEventListener("click", function(){
                campo.value = opcoes.valor(item);
                esconder();
            });

            caixa.appendChild(botao);
        });

        caixa.classList.remove("d-none");
    }

    campo.addEventListener("input", mostrar);
    campo.addEventListener("keydown", function(evento){
        if (evento.key === "Escape") esconder();
    });
    document.addEventListener("click", function(evento){
        if (evento.target !== campo && !caixa.contains(evento.target)) esconder();
    })
}