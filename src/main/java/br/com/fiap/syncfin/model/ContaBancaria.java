package br.com.fiap.syncfin.model;

public class ContaBancaria extends Conta {
    private int idConta;
    private Cadastro cliente;
    private String tipoConta;

    public ContaBancaria(){}

    public ContaBancaria(Cadastro cliente, String nomeInstituicao, String agencia, String numeroConta, String tipoConta, double saldo){
        super(nomeInstituicao, agencia, numeroConta, saldo);
        this.cliente = cliente;
        this.tipoConta = tipoConta;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public Cadastro getCliente() {
        return cliente;
    }

    public void setCliente(Cadastro cliente) {
        this.cliente = cliente;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

}
