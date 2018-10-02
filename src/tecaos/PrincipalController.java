/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecaos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author rapwe
 */
public class PrincipalController implements Initializable {
    
    @FXML
    private AnchorPane login;
    @FXML
    private AnchorPane cliente;
    @FXML
    private TableView<Livro> tabela;
    @FXML
    private TableColumn<Livro, Integer> isbnCol;
    @FXML
    private TableColumn<Livro, String> tituloCol;
    @FXML
    private TableColumn<Livro, String> autorCol;
    @FXML
    private TableColumn<Livro, Integer> edicaoCol;
    @FXML
    private TableColumn<Livro, String> localCol;
    @FXML
    private TableColumn<Livro, Integer> copiasCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //cliente.setDisable(true);
        //livros.setDisable(true);
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tituloCol.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        autorCol.setCellValueFactory(new PropertyValueFactory<>("autor"));
        edicaoCol.setCellValueFactory(new PropertyValueFactory<>("edicao"));
        localCol.setCellValueFactory(new PropertyValueFactory<>("local"));
        copiasCol.setCellValueFactory(new PropertyValueFactory<>("disponivel"));
        
        tabela.setItems(listaDeLivros());
    }    
    private ObservableList<Livro> listaDeLivros(){
            return FXCollections.observableArrayList(
                    new Livro(1,"A","A",1,"B",5),
                    new Livro(1,"A","A",1,"B",5),
                    new Livro(1,"A","A",1,"B",5),
                    new Livro(1,"A","A",1,"B",5)
            );
    }
    @FXML
    private void logar(ActionEvent event) {
        
    }
    
}
