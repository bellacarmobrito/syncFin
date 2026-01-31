package br.com.fiap.syncfin.model;

public class Endereco {
    private int idCliente;
    private String logradouro;
    private int numero;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;

    public Endereco(){}

    public Endereco(String logradouro, int numero, String bairro, String cep, String cidade, String estado){
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Endereco(int idCliente, String logradouro, int numero, String bairro, String cep, String cidade, String estado){
        this.idCliente = idCliente;
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEnderecoCompleto(){
        return  "ID CLIENTE: " + this.idCliente + " " +
                this.logradouro + " , nº: " +
                this.numero + ", Bairro: " +
                this.bairro + ", Cep: " +
                this.cep + ", Cidade: " +
                this.cidade + ", Estado: "
                + this.estado;
    }

}


