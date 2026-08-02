package br.com.fiap.syncfin.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;


public class Investimento extends Transacao{

    public static final Set<String> STATUS_VALIDOS = Set.of("Ativo", "Resgatado");

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
