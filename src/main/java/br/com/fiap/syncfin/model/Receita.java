package br.com.fiap.syncfin.model;

import java.time.LocalDate;

public class Receita extends Transacao {
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

    public void atualizarStatus(String novoStatus){
        if (getStatus().equals("Recebido") || getStatus().equals("Cancelado")){
            System.out.println("Alteração não permitida. O status já foi definido como: " + getStatus());
        }

        switch (novoStatus){
            case "Recebido":
                setStatus(novoStatus);
                getContaBancaria().depositar(getValor());
                System.out.println("Status alterado para " + getStatus() + " e depósito no valor de R$ " + getValor() + " realizado na conta!");
                break;
            case "Cancelado":
                setStatus(novoStatus);
                System.out.println("Status alterado para " + getStatus() + ".");
                break;
            default:
                System.out.println("Status inválido, utilize somente Recebido ou Cancelado.");
        }

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

    public void atualizarCategoria(String novaCategoria){
        if (novaCategoria != null && !novaCategoria.isEmpty()){
            this.categoria = novaCategoria;
            System.out.println("Categoria atualizada com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar a categoria.");
        }
    }

    public void atualizarRecebimento(LocalDate novoRecebimento){
        if (novoRecebimento != null){
            this.dataRecebimento = novoRecebimento;
            System.out.println("Data do recebimento atualizado com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar a data do recebimento!");
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
