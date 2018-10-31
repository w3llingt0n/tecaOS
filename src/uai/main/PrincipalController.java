/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uai.main;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
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
    private JFXTextField user;
    @FXML
    private JFXTextField password;
    @FXML
    private MenuBar menu;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menu.setDisable(true);
    }

    @FXML
    private void logar(ActionEvent event) {
        menu.setDisable(false);
    }
    
}
