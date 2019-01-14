/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.livros;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import model.entidades.Livro;
import model.livros.LivrosBO;

/**
 * FXML Controller class
 *
 * @author ifnmg
 */
public class LivrosController implements Initializable {

    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField titulo;
    @FXML
    private JFXTextField autor;
    @FXML
    private JFXTextField lancamento;
    @FXML
    private JFXComboBox<String> comboCategoria;
    @FXML
    private JFXComboBox<String> comboBusca;
    @FXML
    private JFXTextField busca;
    @FXML
    private TableView<Livro> tabela;
    private LivrosBO lBO;
    private ObservableList<Livro> dados;
    @FXML
    private JFXTextField quantidade;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lBO = new LivrosBO();
        carregarCombos();
        configurarTabela();
        carregarDados();
    }

    @FXML
    private void salvar(ActionEvent event) {
        try {
            if (id.getText().isEmpty()) {
                Livro l = new Livro(
                        "0",
                        titulo.getText(),
                        lancamento.getText(),
                        autor.getText(),
                        comboCategoria.getValue(),
                        Integer.parseInt(quantidade.getText())
                );
                lBO.salvar(l);
                carregarDados();
                limpaCampos();
                alerta(Alert.AlertType.INFORMATION, "Sucesso", null, "Livro salvo com sucesso.");
            } else {
                Livro l = new Livro(
                        id.getText(),
                        titulo.getText(),
                        lancamento.getText(),
                        autor.getText(),
                        comboCategoria.getValue(),
                        Integer.parseInt(quantidade.getText())
                );
                Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
                conf.setTitle("Editar");
                conf.setHeaderText(null);
                conf.setContentText("Deseja editar esse Livro?");
                Optional<ButtonType> op = conf.showAndWait();
                if (ButtonType.OK == op.get()) {
                    lBO.editar(l);
                    carregarDados();
                    limpaCampos();
                    alerta(Alert.AlertType.INFORMATION, "Sucesso", null, "Livro editado com sucesso.");
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
        Livro l = tabela.getSelectionModel().getSelectedItem();
        if (l != null) {
            id.setText(String.valueOf(l.getId()));
            titulo.setText(l.getTitulo());
            autor.setText(l.getAutor());
            lancamento.setText(l.getAnoLancamento());
            comboCategoria.setValue(l.getCategoria());
            quantidade.setText(String.valueOf(l.getQuantidade()));
        } else {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Não foi selecionado nenhum livro.");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        Livro l = tabela.getSelectionModel().getSelectedItem();
        if (l != null) {
            Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
            conf.setTitle("Excluir");
            conf.setHeaderText(null);
            conf.setContentText("Deseja excluir esse livro?");
            Optional<ButtonType> op = conf.showAndWait();
            if (ButtonType.OK == op.get()) {
                try {
                    lBO.excluir(l);
                    carregarDados();
                    alerta(Alert.AlertType.CONFIRMATION, "Sucesso", null, "Livro excluído com sucesso.");
                } catch (SQLException ex) {
                    alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
                }
            }
        } else {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Não há livro selecionado.");
        }
    }

    @FXML
    private void filtrar(ActionEvent event) {
        if (busca.getText() != null) {
            switch (comboBusca.getValue()) {
                case "ID":
                    try {
                        dados = FXCollections.observableArrayList(lBO.filtrarPeloID(busca.getText()));
                        tabela.setItems(dados);
                    } catch (SQLException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
                    } catch (ValorInvalidoException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
                    }   break;
                case "Título":
                    try {
                        dados = FXCollections.observableArrayList(lBO.filtrarPeloTitulo(busca.getText()));
                        tabela.setItems(dados);
                    } catch (SQLException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
                    } catch (ValorInvalidoException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
                    }   break;
                case "Autor":
                    try {
                        dados = FXCollections.observableArrayList(lBO.filtrarPeloAutor(busca.getText()));
                        tabela.setItems(dados);
                    } catch (SQLException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
                    } catch (ValorInvalidoException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
                    }   break;
                case "Categoria":
                    try {
                        dados = FXCollections.observableArrayList(lBO.filtrarPelaCategoria(busca.getText()));
                        tabela.setItems(dados);
                    } catch (SQLException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, "Erro de comunicação com o Banco de Dados procure o administrador do sistema.");
                    } catch (ValorInvalidoException ex) {
                        alerta(Alert.AlertType.ERROR, "Erro", null, ex.getMessage());
                    }   break;
                default:
                    alerta(Alert.AlertType.ERROR,"Erro",null,"Seleção Inválida.");
                    break;
            }
        } else {
            alerta(Alert.AlertType.ERROR, "Erro", null, "Favor selecionar um campo para busca.");
        }
    }

    private void carregarCombos() {
        ObservableList<String> lista = FXCollections.observableArrayList("ID", "Título", "Autor", "Categoria");
        comboBusca.getItems().addAll(lista);
        comboBusca.getSelectionModel().selectFirst();
        ObservableList<String> lista2 = FXCollections.observableArrayList("A", "B", "C", "D", "E");
        comboCategoria.getItems().addAll(lista2);
        comboCategoria.getSelectionModel().selectFirst();
    }

    private void configurarTabela() {
        TableColumn<Livro, Integer> colId = new TableColumn("ID");
        TableColumn<Livro, String> colTitulo = new TableColumn("Título");
        TableColumn<Livro, String> colAutor = new TableColumn("Autor");
        TableColumn<Livro, String> colLancamento = new TableColumn("Ano de Lançamento");
        TableColumn<Livro, String> colCategoria = new TableColumn("Categoria");
        TableColumn<Livro, Integer> colQuantidade = new TableColumn("Quantidade");
        TableColumn<Livro, Integer> colEmprestados = new TableColumn("Emprestados");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colLancamento.setCellValueFactory(new PropertyValueFactory<>("AnoLancamento"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colEmprestados.setCellValueFactory(new PropertyValueFactory<>("emprestados"));

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colTitulo.setMaxWidth(1f * Integer.MAX_VALUE * 25);
        colId.setMaxWidth(1f * Integer.MAX_VALUE * 5);
        colAutor.setMaxWidth(1f * Integer.MAX_VALUE * 25);
        colCategoria.setMaxWidth(1f * Integer.MAX_VALUE * 10);
        colQuantidade.setMaxWidth(1f * Integer.MAX_VALUE * 15);
        colLancamento.setMaxWidth(1f * Integer.MAX_VALUE * 15);
        colEmprestados.setMaxWidth(1f * Integer.MAX_VALUE * 15);

        tabela.getColumns().addAll(colId, colTitulo, colAutor, colLancamento, colCategoria, colQuantidade,colEmprestados);
    }

    private void carregarDados() {
        try {
            dados = FXCollections.observableArrayList(lBO.listar());
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
        id.clear();
        titulo.clear();
        autor.clear();
        lancamento.clear();
        comboCategoria.setValue(null);
        quantidade.clear();
    }
}
