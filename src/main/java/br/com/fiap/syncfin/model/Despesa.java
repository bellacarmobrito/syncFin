package br.com.fiap.syncfin.model;

import java.time.LocalDate;

public class Despesa extends Transacao {
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

    public void atualizarCategoria(String novaCategoria){
        if (novaCategoria != null && !novaCategoria.isEmpty()){
            this.categoria = novaCategoria;
            System.out.println("Categoria atualizada com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar a categoria.");
        }
    }

    public void atualizarVencimento(LocalDate novoVencimento){
        if (novoVencimento != null){
            this.vencimento = novoVencimento;
            System.out.println("Vencimento atualizado com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar o vencimento!");
        }
    }

    public void atualizarDescricao(String novaDescricao){
        if (novaDescricao != null && !novaDescricao.isEmpty()){
            this.descricao = novaDescricao;
            System.out.println("Descrição atualizada com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar a descrição.");
        }
    }

    public void atualizarStatus(String novoStatus){
        if (novoStatus != null && !novoStatus.isEmpty()){
            setStatus(novoStatus);
            System.out.println("Status atualizado com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar o status.");
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


