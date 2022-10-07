package fr.fs.sdbm;


import fr.fs.sdbm.dao.SDBMConnect2;
import fr.fs.sdbm.metier.Article;
import fr.fs.sdbm.metier.Couleur;
import fr.fs.sdbm.metier.Marque;
import fr.fs.sdbm.metier.TypeBiere;
import fr.fs.sdbm.service.ServiceArticle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateController {
    @FXML
    private ComboBox<Couleur> editCouleur;
    @FXML
    private ComboBox<Marque> editMarque;
    @FXML
    private TextField editNom;
    @FXML
    private TextField editPrix;
    @FXML
    private TextField editStock;
    @FXML
    private TextField editTitrage;
    @FXML
    private ComboBox<TypeBiere> editType;
    @FXML
    private TextField editVolume;
    private Connection connection;
    public PreparedStatement st;
    private MenuApp menuApp;
    Article articleChoisi;
    private ServiceArticle serviceArticle= new ServiceArticle();


    public void initialize(Article articleChoisi) {
        this.articleChoisi=articleChoisi;
        connection = SDBMConnect2.getInstance();
        editNom.setText(articleChoisi.getLibelle());
        editPrix.setText(String.valueOf(articleChoisi.getPrixAchat()));
        editVolume.setText(String.valueOf(articleChoisi.getVolume()));
        editTitrage.setText(String.valueOf(articleChoisi.getTitrage()));
        editStock.setText(String.valueOf(articleChoisi.getStock()));
        editMarque.setItems(FXCollections.observableArrayList(serviceArticle.getMarqueFiltre()));
        editMarque.getSelectionModel().select(articleChoisi.getMarque());
        editCouleur.setItems(FXCollections.observableArrayList(serviceArticle.getCouleurFiltre()));
        editCouleur.getSelectionModel().select(articleChoisi.getCouleur());
        editType.setItems(FXCollections.observableArrayList(serviceArticle.getTypeBieresFiltre()));
        editType.getSelectionModel().select(articleChoisi.getTypeBiere());

    }

    public void setMenuApp(MenuApp menuApp) {
        this.menuApp=menuApp;
    }

    @FXML
    public void editArticle() {
        articleChoisi.setLibelle(editNom.getText());
        articleChoisi.setStock(Integer.valueOf(editStock.getText()));
        articleChoisi.setVolume(Integer.valueOf(editVolume.getText()));
        articleChoisi.setTitrage(Float.valueOf(editTitrage.getText()));
        articleChoisi.setPrixAchat(Float.valueOf(editPrix.getText()));
        articleChoisi.setMarque(editMarque.getSelectionModel().getSelectedItem());
        articleChoisi.setCouleur(editCouleur.getSelectionModel().getSelectedItem());
        articleChoisi.setTypeBiere(editType.getSelectionModel().getSelectedItem());
        confirmerModif(articleChoisi);
    }

    public void confirmerModif(Article article) {

        connection = SDBMConnect2.getInstance();
        String sql="update ARTICLE set NOM_ARTICLE=? ,PRIX_ACHAT=?, VOLUME=? , TITRAGE=? ,ID_MARQUE=?, ID_COULEUR=?,ID_TYPE=?, STOCK=? where ID_ARTICLE='" + article.getId() + "'";
        try {
            st = connection.prepareStatement(sql);

            st.setString(1,article.getLibelle());
            st.setFloat(2, article.getPrixAchat());
            st.setInt(3, article.getVolume());
            st.setFloat(4, article.getTitrage());
            st.setInt(5, article.getMarque().getId());
            st.setInt(6, article.getCouleur().getId());
            st.setInt(7, article.getTypeBiere().getId());
            st.setInt(8, article.getStock());
            st.execute();
            editNom.setText("");
            editVolume.setText("");
            editPrix.setText("");
            editTitrage.setText("");
            editStock.setText("");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Article modifié avec succès!", ButtonType.OK);
            alert.showAndWait();
            st.executeQuery();
            menuApp.getWindowUpdate().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
