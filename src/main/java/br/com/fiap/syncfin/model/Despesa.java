package br.com.fiap.syncfin.model;

import java.time.LocalDate;
import java.util.Set;

public class Despesa extends Transacao {

    public static final Set<String> STATUS_VALIDOS = Set.of("Pendente", "Pago", "Vencida");

    private String categoria;
    private LocalDate vencimento;
    private String descricao;

    public Despesa() {}

    public Despesa(int id, double valor, String categoria, LocalDate vencimento, String descricao, String status, ContaBancaria contaBancaria){
        super(id, valor, status, contaBancaria);
        this.categoria = categoria;
        this.vencimento = vencimento;
        this.descricao = descricao;
    }

    @Override
    public void atualizarValor(Double novoValor) {
        if (novoValor != null){
            setValor(novoValor);
            System.out.println("Valor da despesa atualizada com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar o valor.");
        }
    }

    @Override
    public void excluirTransacao(){
        if (getStatus().equals("Pago")){
            System.out.println("Não é possível excluir uma Despesa com o status Pago!");
        } else {
            setValor(0);
            this.categoria = null;
            this.vencimento = null;
            this.descricao = null;
            setStatus("INATIVO");
            System.out.println("Despesa excluída com sucesso!");
        }
    }

    @Override
    public void exibirTransacao(){
        System.out.println("Detalhamento de Despesa");
        System.out.println("-------------------------------------------");
        System.out.println("ID da Despesa: " + getId());
        System.out.println("Valor: R$ " + getValor());
        System.out.println("Categoria: " + getCategoria() );
        System.out.println("Vencimento: " + getVencimento());
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

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}


