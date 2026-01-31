package br.com.fiap.syncfin.model;


public abstract class Transacao {
    private int id;
    private double valor;
    private String status;
    private ContaBancaria contaBancaria;

    public Transacao(){}

    public Transacao(int id, double valor, String status, ContaBancaria contaBancaria){
        this.id = id;
        this.valor = valor;
        this.status = "Pendente";
        this.contaBancaria = contaBancaria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ContaBancaria getContaBancaria() {
        return contaBancaria;
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
    }


    public void atualizarValor(Double novoValor) {
        if (novoValor != null){
            this.valor = novoValor;
            System.out.println("Valor atualizado com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar o valor.");
        }

    }

    public abstract void excluirTransacao();

    public abstract void exibirTransacao();


}
