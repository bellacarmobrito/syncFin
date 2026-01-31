package br.com.fiap.syncfin.model;

public abstract class Conta {
    private String nomeInstituicao;
    private String agencia;
    private String numeroConta;
    private double saldo;

    public Conta(){}

    public Conta(String nomeInstituicao, String agencia, String numeroConta, double saldo){
        this.nomeInstituicao = nomeInstituicao;
        this.agencia = agencia;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
    }

    public Conta(String nomeInstituicao, String agencia, String numeroConta){
        this.nomeInstituicao = nomeInstituicao;
        this.agencia = agencia;
        this.numeroConta = numeroConta;
    }

    public void depositar(double valor){
        if (valor > 0){
            saldo += valor;
            System.out.println("Depósito no valor de R$ " + valor + " realizado com sucesso!");
        } else {
            System.out.println("Não foi possível realizar seu depósito, o valor deve ser positivo");
        }
    }

    public void sacar(double valor){
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque no valor de R$ " + valor + " realizado com sucesso!");
        } else {
            System.out.println("Saldo insuficiente para saque.");
        }
    }

    public String getNomeInstituicao() {
        return nomeInstituicao;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        if (saldo > 0) {
            this.saldo = saldo;
        } else {
            System.out.println("Por favor informe um valor positivo.");
        }
    }

}
