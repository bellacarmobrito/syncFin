package br.com.fiap.syncfin.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class Investimento extends Transacao{
    private String tipoInvestimento;
    private LocalDate dataInvestimento;
    private LocalDate dataVencimento;
    private double rendimento;
    private String recorrencia;

    public Investimento(){}

    public Investimento(int id, double valor, String status, String tipoInvestimento, LocalDate dataVencimento, LocalDate dataInvestimento, double rendimento, ContaBancaria contaBancaria, String recorrencia){
        super(id, valor, status, contaBancaria);
        this.tipoInvestimento = tipoInvestimento;
        this.dataInvestimento = dataInvestimento;
        this.dataVencimento = dataVencimento;
        this.rendimento = rendimento;
        this.recorrencia = recorrencia;
    }

    public boolean realizarInvestimento(){

        if (getContaBancaria().getSaldo() >= getValor()){
            getContaBancaria().sacar(getValor());
            setStatus("Ativo");
            System.out.println("Investimento realizado com sucesso no valor de R$ " + getValor() + " em " + tipoInvestimento + ".");
            return true;
        } else {
            System.out.println("Saldo insuficiente para realizar essa operação.");
            return false;
        }
    }

    @Override
    public void excluirTransacao(){
        if (getStatus().equals("Ativo")){
            getContaBancaria().depositar(getValor());
            setStatus("Cancelado");
            System.out.println("Investimento cancelado. O valor de R$ " + getValor() + "está disponível na conta.");
        } else {
            System.out.println("O investimento precisa estar com o status Ativo para poder ser cancelado.");
        }
    }

    @Override
    public void exibirTransacao(){
        System.out.println("Detalhamento de Investimento");
        System.out.println("-------------------------------------------");
        System.out.println("ID do Investimento: " + getId());
        System.out.println("Tipo de Investimento: " + getTipoInvestimento());
        System.out.println("Valor: R$ " + getValor());
        System.out.println("Data do Investimento: " + getDataInvestimento() );
        System.out.println("Data do Vencimento: " + getDataVencimento());
        System.out.println("Rendimento: " + getRendimento() + "%");
        System.out.println("Status: " + getStatus());
        System.out.println("Recorrência: " + getRecorrencia());
        System.out.println("-------------------------------------------");
    }

    public void calcularRendimento(){
       double valorFinal = getValor();

        switch (this.recorrencia.toUpperCase()) {
            case "MENSAL":
                long mesesInvestidos = ChronoUnit.MONTHS.between(this.dataInvestimento, this.dataVencimento);
                valorFinal *= Math.pow((1 + (this.rendimento / 100)), mesesInvestidos);
                System.out.println("Valor Inicial R$: " + getValor());
                System.out.println("Valor Final R$: " + valorFinal);
                break;
            case "ANUAL":
                long anosInvestidos = ChronoUnit.YEARS.between(this.dataInvestimento, this.dataVencimento);
                valorFinal *= Math.pow((1 + (this.rendimento / 100)), anosInvestidos);
                System.out.println("Valor Inicial R$: " + getValor());
                System.out.println("Valor Final R$: " + valorFinal);
                break;
            default:
                System.out.println("Recorrência inválida, somente são válidas MENSAL ou ANUAL.");

        }
    }

    private void acompanharInvestimento(){
        double valorAtual = getValor();
        LocalDate hoje = LocalDate.now();

        switch (this.recorrencia.toUpperCase()) {
            case "MENSAL":
                long mesesPassados = ChronoUnit.MONTHS.between(this.dataInvestimento, hoje);
                valorAtual *= Math.pow((1 + (this.rendimento / 100)), mesesPassados);
                System.out.println("Valor Inicial R$: " + getValor());
                System.out.println("Valor Atual R$: " + valorAtual);
                break;
            case "ANUAL":
                long anosPassados = ChronoUnit.YEARS.between(this.dataInvestimento, hoje);
                valorAtual *= Math.pow((1 + (this.rendimento / 100)), anosPassados);
                System.out.println("Valor Inicial R$: " + getValor());
                System.out.println("Valor Atual R$: " + valorAtual);
                break;
            default:
                System.out.println("Recorrência inválida, somente são válidas MENSAL ou ANUAL.");

        }
    }

    public void atualizarRecorrencia(String novaRecorrencia){
        if (novaRecorrencia != null && !novaRecorrencia.isEmpty()){
            this.recorrencia = novaRecorrencia;
            System.out.println("Recorrência atualizada com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar a recorrência.");
        }
    }

    public void atualizarRendimento(Double novoRendimento){
        if (novoRendimento != null && novoRendimento > 0){
            this.rendimento = novoRendimento;
            System.out.println("Rendimento atualizado para " + novoRendimento + recorrencia );
        } else {
            System.out.println("Não foi possível atualizar o rendimento.");
        }
    }

    public void resgatarInvestimento(double valorResgate){
        if (valorResgate <= getValor() && getStatus().equals("Ativo")){
            setValor(getValor() - valorResgate);
            getContaBancaria().depositar(valorResgate);
            if (this.getValor() == 0){
                setStatus("Concluído");
                System.out.println("Resgate no valor de R$ " + valorResgate + " realizado com sucesso!");
                System.out.println("Este investimento foi encerrado pois não há saldo remanescente.");
            } else {
                System.out.println("Resgate no valor de R$ " + valorResgate + " realizado com sucesso!");
                System.out.println("Seu investimento possui o seguinte saldo: R$ " + getValor());
            }

        } else {
            System.out.println("Não foi possível realizar o resgate.");
        }
    }

    public String getTipoInvestimento() {
        return tipoInvestimento;
    }

    public void setTipoInvestimento(String tipoInvestimento) {
        this.tipoInvestimento = tipoInvestimento;
    }

    public LocalDate getDataInvestimento() {
        return dataInvestimento;
    }

    public void setDataInvestimento(LocalDate dataInvestimento) {
        this.dataInvestimento = dataInvestimento;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public double getRendimento() {
        return rendimento;
    }

    public void setRendimento(double rendimento) {
        this.rendimento = rendimento;
    }

    public String getRecorrencia() {
        return recorrencia;
    }

    public void setRecorrencia(String recorrencia) {
        this.recorrencia = recorrencia;
    }

}
