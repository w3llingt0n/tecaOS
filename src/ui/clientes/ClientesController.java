/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.clientes;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import exception.RegistroExistenteException;
import exception.ValorInvalidoException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.clientes.ClientesBO;
import model.entidades.Cliente;

/**
 * FXML Controller class
 *
 * @author ifnmg
 */
public class ClientesController implements Initializable {

    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField nome;
    @FXML
    private JFXTextField cpf;
    @FXML
    private JFXTextField rg;
    @FXML
    private JFXDatePicker dataNascimento;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField busca;
    @FXML
    private TableView<Cliente> tabela;
    private ClientesBO cBO;
    @FXML
    private JFXComboBox<String> comboBusca;
    private ObservableList<Cliente> dados;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cBO = new ClientesBO();
        carregarComboBusca();
        configurarTabela();
        carregarDados();
    }

    @FXML
    private void salvar(ActionEvent event) {
        try {
            if (id.getText().isEmpty()) {
                Cliente c = new Cliente(
                        "0",
                        nome.getText(),
                        cpf.getText(),
                        rg.getText(),
                        dataNascimento.getValue(),
                        email.getText()
                );
                cBO.salvar(c);
                carregarDados();
                limpaCampos();
                alerta(Alert.AlertType.INFORMATION, "Sucesso", null, "Cliente salvo com sucesso.");
            } else {
                Cliente c = new Cliente(
                        id.getText(),
                        nome.getText(),
                        cpf.getText(),
                        rg.getText(),
                        dataNascimento.getValue(),
                        email.getText()
                );
                Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
                conf.setTitle("Editar");
                conf.setHeaderText(null);
                conf.setContentText("Deseja editar esse Cliente?");
                Optional<ButtonType> op = conf.showAndWait();
                if (ButtonType.OK == op.get()) {
                    cBO.editar(c);
                    carregarDados();
                    limpaCampos();
                    alerta(Alert.AlertType.INFORMATION, "Sucesso", null, "Cliente editado com sucesso");
                }
            }
        } catch (SQLException ex) {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Erro ao conectar-se ao banco de dados.");
        } catch (RegistroExistenteException ex) {
            alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        Cliente c = tabela.getSelectionModel().getSelectedItem();
        if (c != null) {
            id.setText(String.valueOf(c.getId()));
            nome.setText(c.getNome());
            cpf.setText(c.getCpf());
            rg.setText(c.getRg());
            dataNascimento.setValue(c.getDataNasc());
            email.setText(c.getEmail());
        } else {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Não foi selecionado nenhum clieinte");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        Cliente c = tabela.getSelectionModel().getSelectedItem();
        if (c != null) {
            Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
            conf.setTitle("Excluir");
            conf.setHeaderText(null);
            conf.setContentText("Deseja excluir esse cliente?");
            Optional<ButtonType> op = conf.showAndWait();
            if (ButtonType.OK == op.get()) {
                try {
                    cBO.excluir(c);
                    carregarDados();
                    alerta(Alert.AlertType.CONFIRMATION, "Sucesso", null, "Cliente excluído com sucesso.");
                } catch (SQLException ex) {
                    alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
                }
            }
        } else {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Não há cliente selecionado");
        }
    }

    @FXML
    private void filtrar(ActionEvent event) {
        if (busca.getText() != null) {
            if (comboBusca.getValue().equals("ID")) {
                try {
                    dados = FXCollections.observableArrayList(cBO.filtrarPeloID(busca.getText()));
                    tabela.setItems(dados);
                } catch (SQLException ex) {
                    alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
                } catch (ValorInvalidoException ex) {
                    alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
                }
            } else {
                try {
                    dados = FXCollections.observableArrayList(cBO.filtrarPeloNome(busca.getText()));
                    tabela.setItems(dados);
                } catch (SQLException ex) {
                    alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
                } catch (ValorInvalidoException ex) {
                    alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
                }
            }

        } else {
            alerta(Alert.AlertType.ERROR,"Erro",null,"Favor selecionar um campo para busca.");
        }
    }

    private void carregarDados() {
        try {
            dados = FXCollections.observableArrayList(cBO.listar());
            tabela.setItems(dados);
        } catch (SQLException ex) {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Erro ao carregar os dados na tabela");
        }
    }

    private void limpaCampos() {
        id.clear();
        cpf.clear();
        email.clear();
        nome.clear();
        rg.clear();
        dataNascimento.setValue(null);
    }

    private void alerta(Alert.AlertType aT, String title, String header, String content) {
        Alert alert = new Alert(aT);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void carregarComboBusca() {
        ObservableList<String> lista = FXCollections.observableArrayList("ID", "Nome");
        comboBusca.getItems().addAll(lista);
        comboBusca.getSelectionModel().selectFirst();
    }

    private void configurarTabela() {
        TableColumn<Cliente, Integer> colId = new TableColumn("ID");
        TableColumn<Cliente, String> colNome = new TableColumn("Nome");
        TableColumn<Cliente, String> colCPF = new TableColumn("CPF");
        TableColumn<Cliente, String> colRG = new TableColumn("RG");
        TableColumn<Cliente, LocalDate> colDataNasc = new TableColumn("Data de\n Nascimento");
        TableColumn<Cliente, String> colEmail = new TableColumn("Email");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colRG.setCellValueFactory(new PropertyValueFactory<>("rg"));
        colDataNasc.setCellValueFactory(new PropertyValueFactory<>("dataNasc"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colNome.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        colId.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        colDataNasc.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        colEmail.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        colRG.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        colCPF.setMaxWidth(1f * Integer.MAX_VALUE * 20);

        tabela.getColumns().addAll(colId, colNome, colCPF, colRG, colDataNasc, colEmail);
    }
}
