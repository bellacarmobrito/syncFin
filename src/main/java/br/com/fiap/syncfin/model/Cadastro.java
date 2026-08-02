package br.com.fiap.syncfin.model;

import java.time.LocalDateTime;


public class Cadastro {
    private int idCliente;
    private String nomeCliente;
    private Endereco endereco;
    private String celular;
    private String cpf;
    private String email;
    private String senha;
    private LocalDateTime dataCadastro;
    private String statusConta = "Ativa";

    public Cadastro(String nome, String celular, String cpf, String email, String senha, String status) {
        this.nomeCliente = nome;
        this.celular = celular;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.statusConta = (status != null && !status.isBlank()) ? status : "Ativa";

    }

    public Cadastro(String nome, Endereco endereco, String celular, String cpf, String email, String senha, String status) {
        this.nomeCliente = nome;
        this.endereco = endereco;
        this.celular = celular;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.statusConta = (status != null && !status.isBlank()) ? status : "Ativa";
    }

    public Cadastro(int idCliente, String nomeCliente, String celular, String cpf, String email, String senha, LocalDateTime dataCadastro, String status) {
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.celular = celular;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
        this.statusConta = (status != null && !status.isBlank()) ? status : "Ativa";
    }


    public Cadastro(int idCliente, String nomeCliente, Endereco endereco, String celular, String cpf, String email, String senha) {
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.endereco = endereco;
        this.celular = celular;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.statusConta = "Ativa";
    }

    public Cadastro() {
    }

    @Override
    public String toString() {
        return "Cadastro{" +
                "idCliente=" + idCliente +
                ", nome='" + nomeCliente + '\'' +
                ", celular='" + celular + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", statusConta='" + statusConta + '\'' +
                ", dataCadastro=" + dataCadastro +
                '}';
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean isStatusConta() {

        if (statusConta != null && statusConta.equalsIgnoreCase("Ativa")) {
            return true;
        }
        return false;
    }

}
