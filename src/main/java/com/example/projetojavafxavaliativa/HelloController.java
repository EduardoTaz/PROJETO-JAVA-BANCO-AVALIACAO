package com.example.projetojavafxavaliativa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnDepositar;

    @FXML
    private Button btnPesquisar;

    @FXML
    private Button btnSacar;

    @FXML
    private Label lblSaldo;

    @FXML
    private RadioButton rbCorrente;

    @FXML
    private RadioButton rbEspecial;

    @FXML
    private RadioButton rbPoupanca;

    @FXML
    private ToggleGroup tipoConta;

    @FXML
    private TextArea txtAreaDados;

    @FXML
    private TextField txtConta;

    @FXML
    private TextField txtDataVenc;

    @FXML
    private TextField txtDepositoSaque;

    @FXML
    private TextField txtLimite;

    @FXML
    private TextField txtPesquisar;

    @FXML
    private TextField txtTitular;

    private Conta conta;
    private List<Conta> listaContas = new ArrayList<>();


    // DESABILITAR CAMPOS
    @FXML
    void onClickCorrente() {
        txtLimite.setDisable(true);
        txtDataVenc.setDisable(true);
    }

    @FXML
    void onClickEspecial() {
        txtLimite.setDisable(false);
        txtDataVenc.setDisable(true);
    }

    @FXML
    void onClickPoupanca() {
        txtLimite.setDisable(true);
        txtDataVenc.setDisable(false);
    }


    // AO CLICAR EM CADASTRAR
    @FXML
    void onClickCadastrar() {
        if(!rbCorrente.isSelected()  && !rbEspecial.isSelected()  && !rbPoupanca.isSelected()) {
            campoVazio("Tipo de conta não selecionado");
        }

        if(rbCorrente.isSelected()) {
            if(txtConta.getText().equals("")) {
                campoVazio("Número da conta em branco");
                txtConta.requestFocus();
            } else if(txtTitular.getText().equals("")) {
                campoVazio("Nome do titular em branco");
                txtTitular.requestFocus();
            } else {
                conta = new Conta(Integer.parseInt(txtConta.getText()), txtTitular.getText(), 0.00);
                listaContas.add(conta);
                txtAreaDados.setText(listaContas.toString());
                limpaCampo();
            }
        }

        if(rbEspecial.isSelected()) {
            if(txtConta.getText().equals("")) {
                campoVazio("Número da conta em branco");
                txtConta.requestFocus();
            } else if(txtTitular.getText().equals("")) {
                campoVazio("Nome do titular em branco");
                txtTitular.requestFocus();
            } else if(txtLimite.getText().equals("")) {
                campoVazio("Limite em branco");
                txtLimite.requestFocus();
            } else {
                Especial especial = new Especial(Integer.parseInt(txtConta.getText()), txtTitular.getText(), 0.00, Double.parseDouble(txtLimite.getText()));
                listaContas.add(especial);
                txtAreaDados.setText(listaContas.toString());
                limpaCampo();
            }
        }

    }


    // AO CLICAR EM DEPOSITAR
    @FXML
    void onClickDepositar() {
        if(txtDepositoSaque.getText().equals("")) {
            campoVazio("Campo de depósito ou saque vazio");
            txtDepositoSaque.requestFocus();
        } else {

        }
    }

    // AO CLICAR EM SACAR
    @FXML
    void onClickSacar() {

    }

    // AO CLICAR EM PESQUISAR
    @FXML
    void onClickPesquisar() {

    }



    // ------------------------------------ //

    @FXML
    private void campoVazio(String msg) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("ERRO");
        alerta.setHeaderText("Campo em branco");
        alerta.setContentText(msg);
        alerta.show();
        return;
    }

    @FXML
    private void limpaCampo() {
        txtConta.setText("");
        txtTitular.setText("");
        txtLimite.setText("");
        txtDataVenc.setText("");
        txtDepositoSaque.setText("");
        rbCorrente.setSelected(false);
        rbEspecial.setSelected(false);
        rbPoupanca.setSelected(false);
    }

}