package fr.fs.sdbm;

import fr.fs.sdbm.metier.Article;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuApp extends Application {
    private Stage primaryStage;
    private Stage windowAjouter;
    private Stage windowUpdate;

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public Stage getWindowAjouter() {
        return windowAjouter;
    }
    public Stage getWindowUpdate() {
        return windowUpdate;
    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        //this.primaryStage.setTitle("Gestion des Marques");
        this.primaryStage.setTitle("Gestion des Articles");
        //showMarque();
        showBiere();
    }

    private void showMarque() {
        try {
            // Chargement du fichier fxml
            FXMLLoader loader = new FXMLLoader();
           loader.setLocation(MenuApp.class.getResource("GestionArticle.fxml"));

            AnchorPane menuLayout = (AnchorPane) loader.load();

            Scene scene = new Scene(menuLayout);
            primaryStage.setScene(scene);

            GestionMarqueController controller = loader.getController();
            controller.setMenuApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showBiere() {
        try {
            // Chargement du fichier fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MenuApp.class.getResource("GestionBiere.fxml"));

            AnchorPane menuLayout = (AnchorPane) loader.load();

            Scene scene = new Scene(menuLayout);
            primaryStage.setScene(scene);

            GestionArticleController controller = loader.getController();
            controller.setMenuApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void afficherWindowAjouter() {

        try {
            windowAjouter=new Stage();
            FXMLLoader myFXMLloader = new FXMLLoader();
            myFXMLloader.setLocation(MenuApp.class.getResource("AjouterArticle.fxml"));
            AnchorPane ajouterAnchor=myFXMLloader.load();
            AjouterController ajouterController=myFXMLloader.getController();
            ajouterController.setMenuApp(this);
            windowAjouter.initModality(Modality.WINDOW_MODAL);
            windowAjouter.initOwner(primaryStage);
            windowAjouter.setTitle("Ajouter un article");
            windowAjouter.setScene(new Scene(ajouterAnchor));
            windowAjouter.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void afficherWindowUpdate(Article article) {
        try {
            windowUpdate=new Stage();
            FXMLLoader myFXMLloader = new FXMLLoader();
            myFXMLloader.setLocation(MenuApp.class.getResource("UpdateArticle.fxml"));
            AnchorPane editAnchor=myFXMLloader.load();
            UpdateController updateController=myFXMLloader.getController();
            updateController.setMenuApp(this);
            updateController.initialize(article);
            windowUpdate.initModality(Modality.WINDOW_MODAL);
            windowUpdate.initOwner(primaryStage);
            windowUpdate.setTitle("Modifier un article");
            windowUpdate.setScene(new Scene(editAnchor));
            windowUpdate.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
