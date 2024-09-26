package com.example.projetojavafxavaliativa;

public class Especial extends Conta{ // com esse extends nao preciso criar as variaveis que criei em Conta
    private Double limite;

    public Especial(Integer numeroConta, String nomeTitular, Double saldoConta, Double limite) {
        super(numeroConta, nomeTitular, saldoConta);
        this.limite = limite;
    }

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    @Override
    public String toString() {
        return  "id=" + getNumeroConta() +
                ", nome='" + getNomeTitular() + '\'' +
                ", email='" + getSaldoConta() + '\'' +
                ", telefone='" + limite + System.lineSeparator();
    }
}
