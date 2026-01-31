package br.com.fiap.syncfin.model;

import br.com.fiap.syncfin.util.CriptografiaUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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

    public Cadastro(String nome, String celular, String cpf, String email, String senha, String status){
        this.nomeCliente = nome;
        this.celular = celular;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.statusConta = (status != null && !status.isBlank()) ? status : "Ativa";

    }

    public Cadastro(String nome, Endereco endereco, String celular, String cpf, String email, String senha, String status){
        this.nomeCliente = nome;
        this.endereco = endereco;
        this.celular = celular;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.statusConta = (status != null && !status.isBlank()) ? status : "Ativa";
    }

    public Cadastro(int idCliente, String nomeCliente, String celular, String cpf, String email, String senha, LocalDateTime dataCadastro, String status){
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.celular = celular;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
        this.statusConta = (status != null && !status.isBlank()) ? status : "Ativa";
    }


    public Cadastro(int idCliente, String nomeCliente, Endereco endereco, String celular, String cpf, String email, String senha){
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.endereco = endereco;
        this.celular = celular;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.statusConta = "Ativa";
    }

    public Cadastro(){}

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


    public void exibirCadastro(){
        System.out.println("Detalhamento do Cadastro");
        System.out.println("-------------------------------------------");
        System.out.println("ID: " + getIdCliente());
        System.out.println("Nome: " + getNomeCliente());
        System.out.println("Endereço: " + endereco.getEnderecoCompleto());
        System.out.println("Celular: " + getCelular());
        System.out.println("CPF: " + getCpf());
        System.out.println("E-mail: " + getEmail());
        System.out.println("Senha: " + getSenha());
        System.out.println("Data de Cadastro: " + getDataCadastro());
        System.out.println("Status da Conta: " + isStatusConta());
    }

    public void atualizarNome(String novoNome){
        if (novoNome != null && !novoNome.isEmpty()){
            this.nomeCliente = novoNome;
            System.out.println("Nome atualizado com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar seu nome.");
        }

    }

    public void atualizarEndereco(Endereco novoEndereco){
        if (novoEndereco != null) {
            this.endereco = novoEndereco;
            System.out.println("Endereço atualizado com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar seu endereço.");
        }
    }

    public void atualizarCpf(String novoCpf){
        if (novoCpf != null && !novoCpf.isEmpty()){
            this.cpf = novoCpf;
            System.out.println("CPF atualizado com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar seu CPF.");
        }
    }

    public void atualizarCelular(String novoCelular){
        if (novoCelular != null && !novoCelular.isEmpty()){
            this.celular = novoCelular;
            System.out.println("Celular atualizado com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar seu celular.");
        }
    }

    public void atualizarEmail(String novoEmail){
        if (novoEmail != null && !novoEmail.isEmpty()) {
            this.email = novoEmail;
            System.out.println("E-mail atualizado com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar seu e-mail.");
        }
    }

    public void atualizarSenha(String novaSenha){
        if (novaSenha != null && !novaSenha.isEmpty()) {
            this.senha = novaSenha;
            System.out.println("Senha alterada com sucesso!");
        } else {
            System.out.println("Não foi possível alterar sua senha.");
        }
    }

    public void excluirCadastro(){
        this.nomeCliente = null;
        this.endereco = null;
        this.celular = null;
        this.cpf = null;
        this.email = null;
        this.senha = null;
        this.statusConta = "Inativa";
        System.out.println("Cadastro excluído com sucesso!");
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
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

        try {
            this.senha = CriptografiaUtils.criptografar(senha);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Boolean isStatusConta() {

        if (statusConta != null && statusConta.equalsIgnoreCase("Ativa")) {
            return true;
        }
        return false;
    }

    public void setStatusConta(String statusConta) {
        this.statusConta = statusConta;
    }

    public String getStatusConta() {
        return statusConta;
    }


}
