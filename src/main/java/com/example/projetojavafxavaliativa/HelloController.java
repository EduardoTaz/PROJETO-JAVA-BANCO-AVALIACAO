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


    // DESABILITAR CAMPOS NÃO USADOS DE ACORDO COM O TIPO DE CONTA
    @FXML
    void onClickCorrente() {
        txtLimite.setDisable(true);
        txtDataVenc.setDisable(false);
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


    @FXML
    void onClickCadastrar() {
        if(rbCorrente.isSelected()) {
            if(txtConta.getText().equals("")) {
                campoVazio("Número da conta em branco");
                txtConta.requestFocus();
            } else if(txtTitular.getText().equals("")) {
                campoVazio("Nome do titular em branco");
                txtTitular.requestFocus();
            } else {
                Conta conta = new Conta(Integer.parseInt(txtConta.getText()), txtTitular.getText(), Double.parseDouble(lblSaldo.getText()));
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
                Especial especial = new Especial(Integer.parseInt(txtConta.getText()), txtTitular.getText(), Double.parseDouble(lblSaldo.getText()), Double.parseDouble(txtLimite.getText()));
                listaContas.add(especial);
                txtAreaDados.setText(listaContas.toString());
                limpaCampo();
            }
        }

    }


    @FXML
    void onClickDepositar() {

    }

    @FXML
    void onClickSacar() {

    }




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