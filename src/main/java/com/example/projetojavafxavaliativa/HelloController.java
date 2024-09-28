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
    private List<Conta> listaContas = new ArrayList<>(); // CRIA UM ARRAY CHAMADO listaContas


    // DESABILITAR CAMPOS DE ACORDO COM O TIPO DE CONTA
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

    @FXML
    public void initialize() {
        btnDepositar.setDisable(true); // DESATIVA O BOTAO DEPOSITCAR
        btnSacar.setDisable(true); // DESATICA O BOTAO SACAR
        txtDepositoSaque.setDisable(true); // DESATIVA O INPUT DO DEPOSITO/SAQUE
    }

    @FXML
    void dateControl() {
        //DESABILITA DATAS ANTIGAS
        dateVenc.setDayCellFactory(picker -> new DateCell() { // PRMITE PERSONALIZAR COMO CADA CÉLULA É EXIBIDA E INTERAGE
            @Override
            public void updateItem(LocalDate date, boolean empty) { // DESATIVA DATAS ANTERIORES
                super.updateItem(date, empty); // DESATIVA A CELULA SE A DATA FOR ANTEIRRO A DATA ATUAL
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true); // DESABILITA DATAS ANTIGAS
                }
            }
        });
    }

    // AO CLICAR EM CADASTRAR
    @FXML
    void onClickCadastrar() {
        if(!rbCorrente.isSelected()  && !rbEspecial.isSelected()  && !rbPoupanca.isSelected()) { // SE NÃO ESTIVER NADA SELECIONADO, O ! ANTES DA VARIÁVEL DO RADIOBUTTON SIGNIFICA DIFERENTE OU SEJA, NÃO ESTÁ
            campoVazio("Tipo de conta não selecionado"); // PUXA O MÉTODO campoVazio QUE EMITE O ERRO
            return; // INTERROMPE A EXECUCAO CASO NAO ESTEJA SELECIONADO
        }


        Integer numeroConta = Integer.parseInt(txtConta.getText()); // JOGA A CONVERSÃO PARA INTEGER DE txtConta DENTRO DE numeroConta

        for (int i = 0; i < listaContas.size(); i++) { // LOOP PARA VERIFICAR TODOS OS NUMEROS DE CONTAS PARA EVITAR REDUNDANCIAS
            Conta c = listaContas.get(i);
            if (c.getNumeroConta().equals(numeroConta)) { // SE
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Erro");
                alertErro.setHeaderText("Número de conta já cadastrado");
                alertErro.setContentText("O campo não é um número válido");
                alertErro.show();
                txtConta.requestFocus(); // FOCA NO txtConta QUE SERIA O NUMERO DA CONTA SE JÁ ESTIVER CADASTRADO ESSE NUMERO
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
                // SUBSTITUIR A VIRUGLA PELO PONTO POR QUE ESTÁ DANDO ERRO NA HORA DE CONVCERTER
                String saldoText = lblSaldo.getText().replace(',', '.'); // SUBSTITUI A VIRGULA PELO PONTO NO lblSaldo
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
                return;
            } else {
                // SUBSTITUIR A VIRUGLA PELO PONTO POR QUE ESTÁ DANDO ERRO NA HORA DE CONVCERTER
                String saldoText = lblSaldo.getText().replace(',', '.'); // SUBSTITUI A VIRGULA PELO PONTO NO lblSaldo
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
                LocalDate dataVenc = dateVenc.getValue(); // PEGA O  VALOR DE DATEPICKER QUE COLOQUEI NA DATA DE VENCIMENTO
                if (dataVenc != null && dataVenc.isBefore(LocalDate.now())) {
                    campoVazio("Data de vencimento não pode ser anterior à data atual");
                    dateVenc.requestFocus();
                    return;
                }

                // SUBSTITUIR A VIRUGLA PELO PONTO POR QUE ESTÁ DANDO ERRO NA HORA DE CONVCERTER
                String saldoText = lblSaldo.getText().replace(',', '.'); // SUBSTITUI A VIRGULA PELO PONTO NO lblSaldo
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
        if (txtDepositoSaque.getText().equals("")) { // VERIFICA SE O CAMPO DE DEPOSITO E SAQUE ESTÁ VAZIO
            campoVazio("Campo de depósito/saque vazio");
            txtDepositoSaque.requestFocus();
        } else {
            try {
                String valorText = txtDepositoSaque.getText().replace(',', '.');
                if (valorText.isEmpty()) {
                    campoVazio("Campo de depósito/saque vazio");
                    txtDepositoSaque.requestFocus();
                    return;
                }
                Double valorDeposito = Double.parseDouble(txtDepositoSaque.getText().replace(',', '.')); //SUBSTITUI VIRGULA PELO PONTO PARA CONVERSAO
                if (conta != null) {
                    conta.setSaldoConta(conta.getSaldoConta() + valorDeposito);
                    lblSaldo.setText("Saldo: " + String.format("%.2f", conta.getSaldoConta()));
                    txtDepositoSaque.clear();
                } else {
                    campoVazio("Nenhuma conta selecionada para depósito");
                }
            } catch (NumberFormatException e) {
                campoVazio("Valor de depósito inválido: " + e.getMessage());
                txtDepositoSaque.requestFocus();
            }
        }
    }

    // AO CLICAR EM SACAR
    @FXML
    void onClickSacar() {
        if (txtDepositoSaque.getText().equals("")) {
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Erro");
            alertErro.setHeaderText("Saque vazio");
            alertErro.setContentText("O valor de saque não pode ser vazio.");
            alertErro.show();
            txtDepositoSaque.requestFocus();
            return;
        } else {
            try {
                // Pega o valor do campo Deposito/Saque
                double valorSaque = Double.parseDouble(txtDepositoSaque.getText().replace(',', '.'));

                if (conta != null) {
                    if (conta instanceof Especial) {
                        Especial contaEspecial = (Especial) conta;

                        if(Double.parseDouble(txtDepositoSaque.getText()) > Double.parseDouble(txtLimite.getText())){
                            Alert alertErro = new Alert(Alert.AlertType.ERROR);
                            alertErro.setTitle("Erro");
                            alertErro.setHeaderText("Saldo insuficiente");
                            alertErro.setContentText("Saldo insuficiente para realizar o saque, incluindo o limite.");
                            alertErro.show();
                            txtDepositoSaque.requestFocus();
                            return;
                        }

                        // Verifica se o saque é possível, considerando saldo e limite
                        double saldoTotalDisponivel = conta.getSaldoConta()  + contaEspecial.getLimite();

                        if (valorSaque <= saldoTotalDisponivel) {
                            contaEspecial.setSaldoConta(contaEspecial.getSaldoConta() - valorSaque);
                            lblSaldo.setText(String.format("%.2f", contaEspecial.getSaldoConta()));
                            txtDepositoSaque.clear();
                        } else {
                            // Se o valor de saque ultrapassa o saldo + limite
                            Alert alertErro = new Alert(Alert.AlertType.ERROR);
                            alertErro.setTitle("Erro");
                            alertErro.setHeaderText("Saldo insuficiente");
                            alertErro.setContentText("Saldo insuficiente para realizar o saque, incluindo o limite.");
                            alertErro.show();
                            txtDepositoSaque.requestFocus();
                            return;
                        }
                    } else {
                        // Se a conta não é especial, realiza o saque normalmente
                        if (conta.getSaldoConta() >= valorSaque) {
                            conta.setSaldoConta(conta.getSaldoConta() - valorSaque);
                            lblSaldo.setText("Saldo: " + String.format("%.2f", conta.getSaldoConta()));
                            txtDepositoSaque.clear();
                        } else {
                            Alert alertErro = new Alert(Alert.AlertType.ERROR);
                            alertErro.setTitle("Erro");
                            alertErro.setHeaderText("Saldo insuficiente");
                            alertErro.setContentText("Saldo insuficiente para realizar o saque.");
                            alertErro.show();
                            txtDepositoSaque.requestFocus();
                            return;
                        }
                    }
                } else {
                    // Verifica se nenhuma conta está selecionada
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
            idBusca = Integer.parseInt(txtPesquisar.getText()); // ATRIBUI O VALOR DE txtPesquisar PRA idBusca
        } catch (Exception err) { // CASO ISSO NÃO DÊ CERTO ELE RETORNA UM ERRO
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Erro");
            alertErro.setHeaderText("Erro de conversão");
            alertErro.setContentText("O campo não é um número válido");
            alertErro.show();
            return;
        }

        Conta contaEncontrada = null; // contaEncontrada FICA VAZIA
        for (Conta c : listaContas) { // OS DOIS PONTOS SIGNIFICA FOR-EACH, OU SEJA, ELE PERCORRE PELO ARRAY INTEIRO CHAMADO listaContas
            if (c.getNumeroConta().equals(idBusca)) { // SE O NUMERO DA CONTA FOR IGUAL O IDBUSCA
                contaEncontrada = c; // SE A CONTA ATUAL TEM O MEMSO NUMERO QUE idBusca ENBTAO A VARIAVEL c É ARMAZENADA EM contaEncontrada
                break; // INTERROMPE ASSIM QUE É ENCONTRADO
            }
        }

        if (contaEncontrada != null) { // SE contaEncontrada NÃO FOR NULO, O QUE SINGIFICA QUE UMA CONTA COM O NUMERO PESQUISADO FOI ENCONTRADO
            populaCampos(contaEncontrada); // ESSE POPULACAMPOS PREENCHE OS CAMPOS COM OS DADOS DA CONTA contaEncontrada, PREENCHENDO AUTOMATICAMENTE TODOS OS CAMPOS
            this.conta = contaEncontrada; // ATUALIZA A REFERENCIA DA CONTA PRA NAO SOMAR COM SALDO DE OUTRAS CONTAS
            btnDepositar.setDisable(false);// ATIVA O BOTAO DEPOSITAR
            btnSacar.setDisable(false); // ATIVA O BOTAO SACAR
            txtDepositoSaque.setDisable(false);// ATIVA O DEPOSITO;SAQUE INPUT
            txtPesquisar.setText("");
            txtConta.setDisable(true);
            txtTitular.setDisable(true);
            rbCorrente.setDisable(true);
            rbEspecial.setDisable(true);
            rbPoupanca.setDisable(true);
            txtLimite.setDisable(true);
            dateVenc.setDisable(true);
            btnCadastrar.setDisable(true);
        } else {
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Erro");
            alertErro.setHeaderText("Conta não encontrada");
            alertErro.setContentText("Não existe uma conta com o número fornecido.");
            alertErro.show();
        }

        for(Integer i = 0; i < listaContas.size(); i++) { // PERCORRE TODAS AS CONTAS NA LISTA listaContas
            if(listaContas.get(i).getNumeroConta() == idBusca) { // VERIFICA SE O NUMERO DA CONTA É IGUAL AO NUMEOR DA CONTA PESQUISADOP
                Conta contas = listaContas.get(i);
                populaCampos(contas);
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

        btnDepositar.setDisable(true);
        btnSacar.setDisable(true);
        txtDepositoSaque.setDisable(true);
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

    @FXML
    void criarConta() {
        limpaCampo(); // LIMPA OS CAMPOS AO CLICAR EM CRIAR CONTA
        txtConta.requestFocus(); // FOCA NO PRIMEIRO ELEMENTO QUE É O NUMERO DA CONTA
        lblSaldo.setText(""); // RESETA A LABEL SALDO
        txtPesquisar.setText(""); // RESETA O INPUT PESQUISAR

        // HABILITAR OS CAMPOS NOVAMENTE
        txtConta.setDisable(false);
        txtTitular.setDisable(false);
        rbCorrente.setDisable(false);
        rbEspecial.setDisable(false);
        rbPoupanca.setDisable(false);
        txtLimite.setDisable(false);
        dateVenc.setDisable(false);
        btnCadastrar.setDisable(false);
    }

}