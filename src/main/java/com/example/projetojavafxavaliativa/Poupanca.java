package com.example.projetojavafxavaliativa;

import java.time.LocalDate;

public class Poupanca extends Conta{

    private LocalDate dataVenc;

    public Poupanca(Integer numeroConta, String nomeTitular, Double saldoConta, LocalDate dataVenc) {
        super(numeroConta, nomeTitular, saldoConta);
        this.dataVenc = dataVenc;
    }

    public LocalDate getDataVenc() {
        return dataVenc;
    }

    public void setDataVenc(LocalDate dataVenc) {
        this.dataVenc = dataVenc;
    }

    @Override
    public String toString() {
        return  "id=" + getNumeroConta() +
                ", nome='" + getNomeTitular() + '\'' +
                ", email='" + getSaldoConta() + '\'' +
                ", telefone='" + dataVenc + System.lineSeparator();
    }
}
