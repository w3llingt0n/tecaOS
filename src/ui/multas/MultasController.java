/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.multas;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import exception.ClienteInexistenteException;
import exception.RegistroExistenteException;
import exception.ValorInvalidoException;
import java.net.URL;
import java.sql.SQLException;
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
import model.entidades.Multa;
import model.multas.MultasBO;

/**
 * FXML Controller class
 *
 * @author rapwe
 */
public class MultasController implements Initializable {

    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField valorMulta;
    @FXML
    private JFXComboBox<String> comboCliente;
    @FXML
    private JFXComboBox<String> comboStatus;
    @FXML
    private JFXComboBox<String> comboBusca;
    @FXML
    private JFXTextField busca;
    @FXML
    private TableView<Multa> tabela;
    private ObservableList<Multa> dados;
    private MultasBO mBO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mBO = new MultasBO();
        carregarCombos();
        configurarTabela();
        carregarDados();
    }

    @FXML
    private void salvar(ActionEvent event) {
        try {
            if (id.getText().isEmpty()) {
                Multa m = new Multa(
                        "0",
                        comboCliente.getValue(),
                        Double.parseDouble(valorMulta.getText()),
                        comboStatus.getValue()
                );
                mBO.salvar(m);
                carregarDados();
                limpaCampos();
                alerta(Alert.AlertType.INFORMATION, "Sucesso", null, "Multa salva com sucesso.");
            } else {
                Multa m = new Multa(
                        id.getText(),
                        comboCliente.getValue(),
                        Double.parseDouble(valorMulta.getText()),
                        comboStatus.getValue()
                );
                Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
                conf.setTitle("Editar");
                conf.setHeaderText(null);
                conf.setContentText("Deseja editar essa multa?");
                Optional<ButtonType> op = conf.showAndWait();
                if (ButtonType.OK == op.get()) {
                    mBO.editar(m);
                    carregarDados();
                    limpaCampos();
                    alerta(Alert.AlertType.INFORMATION, "Sucesso", null, "Multa editada com sucesso.");
                }
            }
        } catch (SQLException ex) {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Erro ao conectar-se ao banco de dados.");
        } catch (RegistroExistenteException | ClienteInexistenteException ex) {
            alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        Multa m = tabela.getSelectionModel().getSelectedItem();
        if (m != null) {
            id.setText(String.valueOf(m.getId()));
            valorMulta.setText(String.valueOf(m.getValor()));
            comboCliente.setValue(m.getCliente());
            comboStatus.setValue(m.getStatus());
        } else {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Não foi selecionado nenhuma multa.");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        Multa m = tabela.getSelectionModel().getSelectedItem();
        if (m != null) {
            Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
            conf.setTitle("Excluir");
            conf.setHeaderText(null);
            conf.setContentText("Deseja excluir essa multa?");
            Optional<ButtonType> op = conf.showAndWait();
            if (ButtonType.OK == op.get()) {
                try {
                    mBO.excluir(m);
                    carregarDados();
                    alerta(Alert.AlertType.CONFIRMATION, "Sucesso", null, "Multa excluída com sucesso.");
                } catch (SQLException ex) {
                    alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
                } catch (ClienteInexistenteException ex) {
                    alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
                }
            }
        } else {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Não há multa selecionada.");
        }
    }

    @FXML
    private void filtrar(ActionEvent event) {
        if (busca.getText() != null) {
            switch (busca.getText()) {
                case "ID":
                    try {
                        dados = FXCollections.observableArrayList(mBO.filtrarPeloID(busca.getText()));
                        tabela.setItems(dados);
                    } catch (SQLException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
                    } catch (ValorInvalidoException | ClienteInexistenteException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
                    }
                    break;
                case "Cliente":
                    try {
                        dados = FXCollections.observableArrayList(mBO.filtrarPeloCliente(busca.getText()));
                        tabela.setItems(dados);
                    } catch (SQLException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
                    } catch (ValorInvalidoException | ClienteInexistenteException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @FXML
    private void pagar(ActionEvent event) {
        Multa m = tabela.getSelectionModel().getSelectedItem();
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
        conf.setTitle("Pagar");
        conf.setHeaderText(null);
        conf.setContentText("Deseja pagar essa multa?");
        Optional<ButtonType> op = conf.showAndWait();
        try {
            if (op.get() == ButtonType.OK) {
                m.setStatus("Pago");
            }
            mBO.editar(m);
            carregarDados();
            alerta(Alert.AlertType.INFORMATION,"Sucesso",null,"Multa paga com sucesso.");
        } catch (SQLException ex) {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
        } catch (RegistroExistenteException | ClienteInexistenteException ex) {
            alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
        }
    }

    private void carregarCombos() {
        ObservableList<String> lista = FXCollections.observableArrayList("ID", "CPF", "ID do livro");
        comboBusca.getItems().addAll(lista);
        comboBusca.getSelectionModel().selectFirst();
        ObservableList<String> lista2 = FXCollections.observableArrayList("Pago", "Pendente");
        comboStatus.getItems().addAll(lista2);
        try {
            comboCliente.getItems().addAll(mBO.listarClientes());
        } catch (SQLException ex) {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
        }
    }

    private void configurarTabela() {
        TableColumn<Multa, Integer> colId = new TableColumn("ID");
        TableColumn<Multa, Double> colMulta = new TableColumn("Multa");
        TableColumn<Multa, String> colCliente = new TableColumn("Cliente");
        TableColumn<Multa, String> colStatus = new TableColumn("Status");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMulta.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colId.setMaxWidth(1f * Integer.MAX_VALUE * 5);
        colMulta.setMaxWidth(1f * Integer.MAX_VALUE * 5);
        colCliente.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        colStatus.setMaxWidth(1f * Integer.MAX_VALUE * 10);

        tabela.getColumns().addAll(colId, colMulta, colCliente, colStatus);
    }

    private void carregarDados() {
        try {
            dados = FXCollections.observableArrayList(mBO.listar());
            tabela.setItems(dados);
        } catch (SQLException ex) {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Erro ao carregar os dados na tabela");
        } catch (ClienteInexistenteException ex) {
            alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
        }
    }

    private void alerta(Alert.AlertType aT, String title, String header, String content) {
        Alert alert = new Alert(aT);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void limpaCampos() {
        id.clear();
        valorMulta.clear();
        comboCliente.setValue(null);
        comboStatus.setValue(null);
    }
}
