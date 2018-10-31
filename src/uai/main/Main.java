/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uai.main;

import java.io.IOException;
import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author rapwe
 */
public class Main extends Application{
    public void start(Stage stage) throws IOException{
        Locale.setDefault(new Locale("pt", "BR"));
        Parent principal = FXMLLoader.load(getClass().getResource("principal.fxml"));
        
        Scene cena = new Scene(principal);
        
        stage.setTitle("TecaOs");
        stage.getIcons().add(new Image("/images/book-stack-of-three.png"));
        stage.setScene(cena);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
