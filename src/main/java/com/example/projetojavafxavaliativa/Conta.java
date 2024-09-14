package com.example.projetojavafxavaliativa;

public class Conta {

    private numero Integer;
    private String titular;
    private Double saldo;

    public Conta(numero integer, String titular) {
        Integer = integer;
        this.titular = titular;
        this.saldo = 0.0;
    }

    public numero getInteger() {
        return Integer;
    }

    public void setInteger(numero integer) {
        Integer = integer;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public void depositar(Double valor) {
        this.saldo = saldo + valor;
    }


}

