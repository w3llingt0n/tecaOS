/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.devolucao;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import exception.ClienteInexistenteException;
import exception.EmprestimoInexistenteException;
import exception.RegistroExistenteException;
import exception.ValorInvalidoException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
import model.devolucao.DevolucaoBO;
import model.entidades.Devolucao;

/**
 * FXML Controller class
 *
 * @author rapwe
 */
public class DevolucaoController implements Initializable {

    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField cpfCliente;
    @FXML
    private JFXTextField codLivro;
    @FXML
    private JFXComboBox<String> comboBusca;
    @FXML
    private JFXTextField busca;
    @FXML
    private TableView<Devolucao> tabela;
    private DevolucaoBO dBO;
    private ObservableList<Devolucao> dados;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dBO = new DevolucaoBO();
        carregarCombo();
        configurarTabela();
        carregarDados();
    }

    @FXML
    private void salvar(ActionEvent event) {
        try {
            if (id.getText().isEmpty()) {
                Devolucao d = new Devolucao(
                        "0",
                        dBO.buscarEmprestimo(cpfCliente.getText(),codLivro.getText()),
                        cpfCliente.getText(),
                        Integer.parseInt(codLivro.getText()),
                        LocalDate.now(),
                        aplicarMulta(dBO.buscarEmprestimo(cpfCliente.getText(),codLivro.getText()))
                );
                dBO.salvar(d);
                carregarDados();
                limpaCampos();
                alerta(Alert.AlertType.INFORMATION, "Sucesso", null, "Devolução salva com sucesso.");
            } else {
                Devolucao d = new Devolucao(
                        id.getText(),
                        dBO.buscarEmprestimo(cpfCliente.getText(),codLivro.getText()),
                        cpfCliente.getText(),
                        Integer.parseInt(codLivro.getText()),
                        LocalDate.now(),
                        aplicarMulta(dBO.buscarEmprestimo(cpfCliente.getText(),codLivro.getText()))
                );
                Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
                conf.setTitle("Editar");
                conf.setHeaderText(null);
                conf.setContentText("Deseja editar essa devolução?");
                Optional<ButtonType> op = conf.showAndWait();
                if (ButtonType.OK == op.get()) {
                    dBO.editar(d);
                    carregarDados();
                    limpaCampos();
                    alerta(Alert.AlertType.INFORMATION, "Sucesso", null, "Devolução editada com sucesso.");
                }
            }
        } catch (SQLException ex) {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Erro ao conectar-se ao banco de dados.");
        } catch (RegistroExistenteException | EmprestimoInexistenteException | ClienteInexistenteException ex) {
            alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        Devolucao d = tabela.getSelectionModel().getSelectedItem();
        if (d != null) {
            id.setText(String.valueOf(d.getId()));
            cpfCliente.setText(d.getCliente());
            codLivro.setText(String.valueOf(d.getLivro()));
        } else {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Não foi selecionado nenhuma devolução.");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        Devolucao d = tabela.getSelectionModel().getSelectedItem();
        if (d != null) {
            Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
            conf.setTitle("Excluir");
            conf.setHeaderText(null);
            conf.setContentText("Deseja excluir essa devolução?");
            Optional<ButtonType> op = conf.showAndWait();
            if (ButtonType.OK == op.get()) {
                try {
                    dBO.excluir(d);
                    carregarDados();
                    alerta(Alert.AlertType.CONFIRMATION, "Sucesso", null, "Devolução excluída com sucesso.");
                } catch (SQLException ex) {
                    alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
                } catch (ClienteInexistenteException ex) {
                    alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
                }
            }
        } else {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Não há devolução selecionada.");
        }
    }

    @FXML
    private void filtrar(ActionEvent event) {
        if (busca.getText() != null) {
            switch (busca.getText()) {
                case "ID":
                    try {
                        dados = FXCollections.observableArrayList(dBO.filtrarPeloID(busca.getText()));
                        tabela.setItems(dados);
                    } catch (SQLException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
                    } catch (ValorInvalidoException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
                    }
                    break;
                case "Cliente":
                    try {
                        dados = FXCollections.observableArrayList(dBO.filtrarPeloCliente(busca.getText()));
                        tabela.setItems(dados);
                    } catch (SQLException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
                    } catch (ValorInvalidoException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
                    }
                    break;
                case "Livro":
                    try {
                        dados = FXCollections.observableArrayList(dBO.filtrarPeloLivro(busca.getText()));
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

    private void configurarTabela() {
        TableColumn<Devolucao, Integer> colId = new TableColumn("ID");
        TableColumn<Devolucao, String> colCliente = new TableColumn("CPF");
        TableColumn<Devolucao, Integer> colLivro = new TableColumn("ID do Livro");
        TableColumn<Devolucao, Integer> colEmprestimo = new TableColumn("ID do Emprestimo");
        TableColumn<Devolucao, LocalDate> colDevolucao = new TableColumn("Data de Devolução");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colLivro.setCellValueFactory(new PropertyValueFactory<>("livro"));
        colEmprestimo.setCellValueFactory(new PropertyValueFactory<>("emprestimo"));
        colDevolucao.setCellValueFactory(new PropertyValueFactory<>("dataDevolucao"));

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colId.setMaxWidth(1f * Integer.MAX_VALUE * 5);
        colCliente.setMaxWidth(1f * Integer.MAX_VALUE * 15);
        colLivro.setMaxWidth(1f * Integer.MAX_VALUE * 5);
        colEmprestimo.setMaxWidth(1f * Integer.MAX_VALUE * 5);
        colDevolucao.setMaxWidth(1f * Integer.MAX_VALUE * 15);

        tabela.getColumns().addAll(colId, colCliente, colLivro, colEmprestimo, colDevolucao);
    }

    private void carregarDados() {
        try {
            dados = FXCollections.observableArrayList(dBO.listar());
            tabela.setItems(dados);
        } catch (SQLException ex) {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Erro ao carregar os dados na tabela");
        }
    }

    private void carregarCombo() {
        ObservableList<String> lista = FXCollections.observableArrayList("ID", "CPF", "ID do livro");
        comboBusca.getItems().addAll(lista);
        comboBusca.getSelectionModel().selectFirst();
    }

    private void alerta(Alert.AlertType aT, String title, String header, String content) {
        Alert alert = new Alert(aT);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private double aplicarMulta(int id) throws EmprestimoInexistenteException, SQLException {
        long temp = ChronoUnit.DAYS.between(LocalDate.now(), dBO.buscarDataDevolucaoPrevista(id));
        if(temp >0){
            return 0.5 * temp;
        }
        return 0;    
    }

    private void limpaCampos() {
        id.clear();
        cpfCliente.clear();
        codLivro.clear();
    }
}
