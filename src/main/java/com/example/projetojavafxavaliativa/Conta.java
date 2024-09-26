package com.example.projetojavafxavaliativa;

public class Conta {

    public Double valor;
    private Integer numeroConta;
    private String nomeTitular;
    private Double saldoConta;

    public Conta(Integer numeroConta, String nomeTitular, Double saldoConta) {
        this.numeroConta = numeroConta;
        this.nomeTitular = nomeTitular;
        this.saldoConta = saldoConta;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Integer numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public Double getSaldoConta() {
        return saldoConta;
    }

    public void setSaldoConta(Double saldoConta) {
        this.saldoConta = saldoConta;
    }

    @Override
    public String toString() {
        return  "Nº da conta=" + numeroConta +
                ", Nome do titular='" + nomeTitular + '\'' +
                ", Saldo da conta='" + saldoConta + System.lineSeparator();
    }

}

