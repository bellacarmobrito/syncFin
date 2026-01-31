package br.com.fiap.syncfin.model;

public class ContaBancaria extends Conta {
    private int idConta;
    private Cadastro cliente;
    private String tipoConta;
    private boolean status;

    public ContaBancaria(){}

    public ContaBancaria(Cadastro cliente, String nomeInstituicao, String agencia, String numeroConta, String tipoConta, double saldo){
        super(nomeInstituicao, agencia, numeroConta, saldo);
        this.cliente = cliente;
        this.tipoConta = tipoConta;
        this.status = true;
    }

    public ContaBancaria(int idCliente, String nomeInstituicao, String agencia, String tipoConta, String numeroConta, double saldo) {
        super(nomeInstituicao, agencia, numeroConta, saldo);
        this.cliente = new Cadastro();
        this.cliente.setIdCliente(idCliente);
        this.tipoConta = tipoConta;
        this.status = true;
    }


    public void transferir(ContaBancaria destino, double valor){
        if (destino != null && valor <= getSaldo()){
            setSaldo(getSaldo() - valor);
            destino.depositar(valor);
            System.out.println("Transferência no valor de R$ " + valor + "para conta " + destino + " realizada com sucesso!");
        } else {
            System.out.println("Transferência não realizada.");
        }
    }

    public void exibirSaldo(){
        System.out.println("Seu saldo atual é de R$ " + getSaldo());
    }

    public void exibirConta(){
        System.out.println("Detalhamento da Conta");
        System.out.println("-------------------------------------------");
        System.out.println("ID da Conta: " + getIdConta());
        System.out.println("Banco: " + getNomeInstituicao() );
        System.out.println("Agência: " + getAgencia());
        System.out.println("Conta: " + getNumeroConta());
        System.out.println("Tipo de conta: " + getTipoConta());
        System.out.println("Saldo: R$ " + getSaldo());
        System.out.println("Status Conta: " + (isStatus() ? "Ativa" : "Inativa"));
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
