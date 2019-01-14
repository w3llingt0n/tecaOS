/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.emprestimos;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import exception.ClienteComPendenciasException;
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
import model.emprestimos.EmprestimosBO;
import model.entidades.Emprestimo;

/**
 * FXML Controller class
 *
 * @author ifnmg
 */
public class EmprestimoController implements Initializable {

    @FXML
    private JFXTextField id;
    @FXML
    private JFXComboBox<String> comboBusca;
    @FXML
    private JFXTextField busca;
    @FXML
    private TableView<Emprestimo> tabela;
    @FXML
    private JFXComboBox<String> comboCliente;
    @FXML
    private JFXComboBox<String> comboLivro;
    private EmprestimosBO eBO;
    private ObservableList<Emprestimo> dados;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        eBO = new EmprestimosBO();
        carregarCombos();
        configurarTabela();
        carregarDados();
    }

    @FXML
    private void salvar(ActionEvent event) {
        try {
            if (id.getText().isEmpty()) {
                Emprestimo e = new Emprestimo(
                        "0",
                        comboLivro.getValue(),
                        comboCliente.getValue(),
                        LocalDate.now(),
                        LocalDate.now().plusDays(30)
                );
                eBO.salvar(e);
                carregarDados();
                limpaCampos();
                alerta(Alert.AlertType.INFORMATION, "Sucesso", null, "Empréstimo salvo com sucesso.");
            } else {
                Emprestimo e = new Emprestimo(
                        id.getText(),
                        comboLivro.getValue(),
                        comboCliente.getValue(),
                        LocalDate.now(),
                        LocalDate.now().plusDays(30)
                );
                Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
                conf.setTitle("Editar");
                conf.setHeaderText(null);
                conf.setContentText("Deseja editar esse empréstimo?");
                Optional<ButtonType> op = conf.showAndWait();
                if (ButtonType.OK == op.get()) {
                    eBO.editar(e);
                    carregarDados();
                    limpaCampos();
                    alerta(Alert.AlertType.INFORMATION, "Sucesso", null, "Empréstimo editado com sucesso.");
                }
            }
        } catch (SQLException ex) {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Erro ao conectar-se ao banco de dados.");
        } catch (RegistroExistenteException | ClienteComPendenciasException ex) {
            alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        Emprestimo e = tabela.getSelectionModel().getSelectedItem();
        if (e != null) {
            id.setText(String.valueOf(e.getId()));
            comboCliente.setValue(e.getCliente());
            comboLivro.setValue(e.getLivro());
        } else {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Não foi selecionado nenhum emprestimo.");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        Emprestimo e = tabela.getSelectionModel().getSelectedItem();
        if (e != null) {
            Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
            conf.setTitle("Excluir");
            conf.setHeaderText(null);
            conf.setContentText("Deseja excluir esse empréstimo?");
            Optional<ButtonType> op = conf.showAndWait();
            if (ButtonType.OK == op.get()) {
                try {
                    eBO.excluir(e);
                    carregarDados();
                    alerta(Alert.AlertType.CONFIRMATION, "Sucesso", null, "Empréstimo excluído com sucesso.");
                } catch (SQLException ex) {
                    alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
                }
            }
        } else {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Não há empréstimo selecionado.");
        }
    }

    @FXML
    private void filtrar(ActionEvent event) {
        if (busca.getText() != null) {
            switch (busca.getText()) {
                case "ID":
                    try {
                        dados = FXCollections.observableArrayList(eBO.filtrarPeloID(busca.getText()));
                        tabela.setItems(dados);
                    } catch (SQLException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
                    } catch (ValorInvalidoException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
                    }
                    break;
                case "Cliente":
                    try {
                        dados = FXCollections.observableArrayList(eBO.filtrarPeloCliente(busca.getText()));
                        tabela.setItems(dados);
                    } catch (SQLException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
                    } catch (ValorInvalidoException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
                    }
                    break;
                case "Livro":
                    try {
                        dados = FXCollections.observableArrayList(eBO.filtrarPeloLivro(busca.getText()));
                        tabela.setItems(dados);
                    } catch (SQLException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
                    } catch (ValorInvalidoException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
                    }
                    break;
                default:
                    break;
            }
        }

    }

    private void carregarCombos() {
        ObservableList<String> lista = FXCollections.observableArrayList("ID", "Cliente", "Livro");
        comboBusca.getItems().addAll(lista);
        comboBusca.getSelectionModel().selectFirst();
        try {
            comboCliente.getItems().addAll(eBO.listarClientes());
            comboLivro.getItems().addAll(eBO.listarLivros());
        } catch (SQLException ex) {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
        }
    }

    private void configurarTabela() {
        TableColumn<Emprestimo, Integer> colId = new TableColumn("ID");
        TableColumn<Emprestimo, String> colCliente = new TableColumn("Cliente");
        TableColumn<Emprestimo, String> colLivro = new TableColumn("Livro");
        TableColumn<Emprestimo, LocalDate> colEmprestimo = new TableColumn("Data do Empréstimo");
        TableColumn<Emprestimo, LocalDate> colDevolucao = new TableColumn("Data Prevista\nde Devolução");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colLivro.setCellValueFactory(new PropertyValueFactory<>("livro"));
        colEmprestimo.setCellValueFactory(new PropertyValueFactory<>("dataEmprestimo"));
        colDevolucao.setCellValueFactory(new PropertyValueFactory<>("dataDevolucao"));

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colId.setMaxWidth(1f * Integer.MAX_VALUE * 5);
        colCliente.setMaxWidth(1f * Integer.MAX_VALUE * 25);
        colLivro.setMaxWidth(1f * Integer.MAX_VALUE * 25);
        colEmprestimo.setMaxWidth(1f * Integer.MAX_VALUE * 15);
        colDevolucao.setMaxWidth(1f * Integer.MAX_VALUE * 15);

        tabela.getColumns().addAll(colId, colCliente, colLivro, colEmprestimo, colDevolucao);
    }

    private void carregarDados() {
        try {
            dados = FXCollections.observableArrayList(eBO.listar());
            tabela.setItems(dados);
        } catch (SQLException ex) {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Erro ao carregar os dados na tabela");
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
        comboCliente.setValue(null);
        comboLivro.setValue(null);
        id.clear();
    }
}
