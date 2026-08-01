package br.com.fiap.syncfin.model;

import java.time.LocalDate;
import java.util.Set;

public class Receita extends Transacao {

    public static final Set<String> STATUS_VALIDOS = Set.of("Recebido", "Pendente", "Programada");

    private String categoria;
    private LocalDate dataRecebimento;
    private String descricao;

    public Receita(){}

    public Receita(int id, double valor, String status, String categoria, LocalDate dataRecebimento, String descricao, ContaBancaria contaBancaria){
        super(id, valor, status, contaBancaria);
        this.categoria = categoria;
        this.dataRecebimento = dataRecebimento;
        this.descricao = descricao;
    }

    @Override
    public void atualizarValor(Double novoValor) {
        if (novoValor != null){
            setValor(novoValor);
            System.out.println("Valor da receita atualizada com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar o valor.");
        }

    }

    @Override
    public void excluirTransacao(){
        if (getStatus().equals("Recebido")){
            System.out.println("Não é possível excluir uma receita com o status Recebido");
        } else {
            setValor(0);
            this.categoria = null;
            this.dataRecebimento = null;
            this.descricao = null;
            setStatus("CANCELADO");
            System.out.println("Recebimento excluído com sucesso!");
        }

    }

    @Override
    public void exibirTransacao(){
        System.out.println("Detalhamento de Receita");
        System.out.println("-------------------------------------------");
        System.out.println("ID Receita: " + getId());
        System.out.println("Valor: R$ " + getValor());
        System.out.println("Categoria: " + getCategoria() );
        System.out.println("Vencimento: " + getDataRecebimento());
        System.out.println("Descrição: " + getDescricao());
        System.out.println("Status: " + getStatus());
        System.out.println("-------------------------------------------");
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(LocalDate dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
