package com.example.projetojavafxavaliativa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
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
    private DatePicker dateVenc;

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
        dateVenc.setDisable(true);
    }

    @FXML
    void onClickEspecial() {
        txtLimite.setDisable(false);
        dateVenc.setDisable(true);
    }

    @FXML
    void onClickPoupanca() {
        txtLimite.setDisable(true);
        dateVenc.setDisable(false);
    }


    // AO CLICAR EM CADASTRAR
    @FXML
    void onClickCadastrar() {
        if(!rbCorrente.isSelected()  && !rbEspecial.isSelected()  && !rbPoupanca.isSelected()) {
            campoVazio("Tipo de conta não selecionado");
        }

        Integer numeroConta = Integer.parseInt(txtConta.getText());

        for (int i = 0; i < listaContas.size(); i++) {
            Conta c = listaContas.get(i);
            if (c.getNumeroConta().equals(numeroConta)) {
                campoVazio("Número da conta já cadastrado");
                txtConta.requestFocus();
                return;
            }
        }

        if(rbCorrente.isSelected()) {
            if(txtConta.getText().equals("")) {
                campoVazio("Número da conta em branco");
                txtConta.requestFocus();
            } else if(txtTitular.getText().equals("")) {
                campoVazio("Nome do titular em branco");
                txtTitular.requestFocus();
            } else {
                // Obter o texto do lblSaldo e substituir a vírgula pelo ponto
                String saldoText = lblSaldo.getText().replace(',', '.');
                Double saldo = Double.parseDouble(saldoText);
                conta = new Conta(Integer.parseInt(txtConta.getText()), txtTitular.getText(), saldo);
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
                // Obter o texto do lblSaldo e substituir a vírgula pelo ponto
                String saldoText = lblSaldo.getText().replace(',', '.');
                Double saldo = Double.parseDouble(saldoText);
                Especial especial = new Especial(Integer.parseInt(txtConta.getText()), txtTitular.getText(), saldo, Double.parseDouble(txtLimite.getText()));
                listaContas.add(especial);
                txtAreaDados.setText(listaContas.toString());
                limpaCampo();
            }
        }

        if(rbPoupanca.isSelected()) {
            if(txtConta.getText().equals("")) {
                campoVazio("Número da conta em branco");
                txtConta.requestFocus();
            } else if(txtTitular.getText().equals("")) {
                campoVazio("Nome do titular em branco");
                txtTitular.requestFocus();
            } else if(dateVenc.getValue() == null) {
                campoVazio("Data de vencimento em branco");
                dateVenc.requestFocus();
            } else {
                LocalDate dataVenc = dateVenc.getValue(); // pega o datePicker

                // Obter o texto do lblSaldo e substituir a vírgula pelo ponto
                String saldoText = lblSaldo.getText().replace(',', '.');
                Double saldo = Double.parseDouble(saldoText);
                Poupanca poupanca = new Poupanca(Integer.parseInt(txtConta.getText()), txtTitular.getText(), saldo, dataVenc);
                listaContas.add(poupanca);
                txtAreaDados.setText(listaContas.toString());
                limpaCampo();
            }
        }

    }


    // AO CLICAR EM DEPOSITAR
    @FXML
    void onClickDepositar() {
        if (txtDepositoSaque.getText().equals("")) {
            campoVazio("Campo de depósito/saque vazio");
            txtDepositoSaque.requestFocus();
        } else {
            try {
                // Obtém o valor digitado no campo
                Double valorDeposito = Double.parseDouble(txtDepositoSaque.getText());

                // Verifica se existe uma conta selecionada
                if (conta != null) {
                    // Adiciona o valor do depósito ao saldo da conta
                    conta.setSaldoConta(conta.getSaldoConta() + valorDeposito);

                    // Atualiza o label com o saldo atualizado
                    lblSaldo.setText("Saldo: " + Double.toString(conta.getSaldoConta()));

                    // Limpa o campo de depósito
                    txtDepositoSaque.clear();
                } else {
                    campoVazio("Nenhuma conta selecionada para depósito");
                }
            } catch (NumberFormatException e) {
                campoVazio("Valor de depósito inválido");
                txtDepositoSaque.requestFocus();
            }
        }
    }

    // AO CLICAR EM SACAR
    @FXML
    void onClickSacar() {
        if (txtDepositoSaque.getText().equals("")) {
            campoVazio("Campo de depósito/saque vazio");
            txtDepositoSaque.requestFocus();
        } else {
            try {
                // Obtém o valor digitado no campo
                double valorSaque = Double.parseDouble(txtDepositoSaque.getText());

                // Verifica se existe uma conta selecionada
                if (conta != null) {
                    // Verifica se o saldo é suficiente para o saque
                    if (conta.getSaldoConta() >= valorSaque) {
                        // Subtrai o valor do saque do saldo da conta
                        conta.setSaldoConta(conta.getSaldoConta() - valorSaque);

                        // Atualiza o label com o saldo atualizado
                        lblSaldo.setText("Saldo: " + Double.toString(conta.getSaldoConta()));

                        // Limpa o campo de saque
                        txtDepositoSaque.clear();
                    } else {
                        campoVazio("Saldo insuficiente para o saque");
                    }
                } else {
                    campoVazio("Nenhuma conta selecionada para saque");
                }
            } catch (NumberFormatException e) {
                campoVazio("Valor de saque inválido");
                txtDepositoSaque.requestFocus();
            }
        }
    }



    @FXML
    void onClickPesquisar() {
        Integer idBusca;

        try {
            idBusca = Integer.parseInt(txtPesquisar.getText());
        } catch (Exception err) {
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Erro");
            alertErro.setHeaderText("Erro de conversão");
            alertErro.setContentText("O campo não é um número válido");
            alertErro.show();
            return;
        }

        for(Integer i = 0; i < listaContas.size(); i++) {
            if(listaContas.get(i).getNumeroConta() == idBusca) {
                Conta contas = listaContas.get(i);
                populaCampos(contas);
            } else if(listaContas.size() < Integer.parseInt(txtPesquisar.getText())) {
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Erro");
                alertErro.setHeaderText("Erro de conversão");
                alertErro.setContentText("O campo não é um número válido");
                alertErro.show();
                return;
            }
        }
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
        dateVenc.setValue(null);
        txtDepositoSaque.setText("");
        rbCorrente.setSelected(false);
        rbEspecial.setSelected(false);
        rbPoupanca.setSelected(false);
    }


    private void populaCampos(Conta con) {
        txtConta.setText(String.valueOf(con.getNumeroConta())); // converte para Integer
        txtTitular.setText(con.getNomeTitular());
        lblSaldo.setText(String.valueOf(con.getSaldoConta()));

        // Limpa as seleções dos RadioButtons
        rbCorrente.setSelected(false);
        rbEspecial.setSelected(false);
        rbPoupanca.setSelected(false);
        txtLimite.clear();
        dateVenc.setValue(null);

        // Verifica o tipo de conta e preenche os campos correspondentes
        if (con instanceof Especial) { // instanceof verifica se é um objeto ou uma instancia de uma classe especifica ou de uma subclasse dela
            rbEspecial.setSelected(true);
            Especial especial = (Especial) con; // tudo da classe Conta foi herdado para Especial
            txtLimite.setText(String.valueOf(especial.getLimite())); // Preenche o limite
        } else if (con instanceof Poupanca) {
            rbPoupanca.setSelected(true);
            Poupanca poupanca = (Poupanca) con; // Cast para o tipo Poupança
            dateVenc.setValue(poupanca.getDataVenc()); // Preenche a data de vencimento
        } else if (con instanceof Conta) { // Isso seria para a Conta Corrente
            rbCorrente.setSelected(true);
        }

    }
}