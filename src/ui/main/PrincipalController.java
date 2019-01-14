/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rapwe
 */
public class PrincipalController implements Initializable {

    @FXML
    private MenuBar menu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void abrirEmprestimos(ActionEvent event) {
        Stage stage = new Stage();
        Parent principal;
        try {
            principal = FXMLLoader.load(getClass().getResource("/ui/emprestimos/emprestimo.fxml"));
            Scene cena = new Scene(principal);

            stage.setTitle("Empréstimos");
            stage.getIcons().add(new Image("/images/books-stack-of-three.png"));
            stage.setScene(cena);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void abrirDevolucoes(ActionEvent event) {
        Stage stage = new Stage();
        Parent principal;
        try {
            principal = FXMLLoader.load(getClass().getResource("/ui/devolucao/devolucao.fxml"));
            Scene cena = new Scene(principal);

            stage.setTitle("Devoluções");
            stage.getIcons().add(new Image("/images/books-stack-of-three.png"));
            stage.setScene(cena);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void abrirClientes(ActionEvent event) {
        Stage stage = new Stage();
        Parent principal;
        try {
            principal = FXMLLoader.load(getClass().getResource("/ui/clientes/clientes.fxml"));
            Scene cena = new Scene(principal);

            stage.setTitle("Clientes");
            stage.getIcons().add(new Image("/images/books-stack-of-three.png"));
            stage.setScene(cena);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void abrirLivros(ActionEvent event) {
        Stage stage = new Stage();
        Parent principal;
        try {
            principal = FXMLLoader.load(getClass().getResource("/ui/livros/livros.fxml"));
            Scene cena = new Scene(principal);

            stage.setTitle("Livros");
            stage.getIcons().add(new Image("/images/books-stack-of-three.png"));
            stage.setScene(cena);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void abrirMultas(ActionEvent event) {
        Stage stage = new Stage();
        Parent principal;
        try {
            principal = FXMLLoader.load(getClass().getResource("/ui/multas/multas.fxml"));
            Scene cena = new Scene(principal);

            stage.setTitle("Multas");
            stage.getIcons().add(new Image("/images/books-stack-of-three.png"));
            stage.setScene(cena);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
