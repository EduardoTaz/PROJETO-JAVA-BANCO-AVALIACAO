package com.example.projetojavafxavaliativa;

public class Conta {

    private Integer numeroConta;
    private String nomeTitular;
    private Double saldoConta;

    public Conta(Integer numeroConta, String nomeTitular, Double saldoConta) {
        this.numeroConta = numeroConta;
        this.nomeTitular = nomeTitular;
        this.saldoConta = saldoConta;
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

    public void deposito(double valor) {
        if (valor > 0) {
            saldoConta += valor;
        } else {
            //erro
        }
    }

    public void saque(double valor) {
        if (valor > 0 && valor <= saldoConta) {
            saldoConta -= valor;
        } else {
            //erro
        }
    }

}

