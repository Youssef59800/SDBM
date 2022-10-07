package fr.fs.sdbm;
import fr.fs.sdbm.dao.SDBMConnect2;
import fr.fs.sdbm.metier.*;
import fr.fs.sdbm.service.ServiceArticle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.sql.*;

public class AjouterController {
    @FXML
    private TextField ajouterNom;

    @FXML
    private TextField ajouterPrix;

    @FXML
    private TextField ajouterVolume;

    @FXML
    private TextField ajouterTitrage;

    @FXML
    private TextField ajouterStock;

    @FXML
    private ComboBox<Marque> marqueSearch;

    @FXML
    private ComboBox<Couleur> couleurSearch;
    @FXML
    private ComboBox<TypeBiere> typeSearch;

    private MenuApp menuApp;
    private ServiceArticle serviceArticle;
    private Connection connection;
    GestionArticleController test = new GestionArticleController();
    public PreparedStatement st;
    public ResultSet result;
    @FXML
    private  void initialize() {
        connection = SDBMConnect2.getInstance();
        serviceArticle = new ServiceArticle();
        marqueSearch.setItems(FXCollections.observableArrayList(serviceArticle.getMarqueFiltre()));
        couleurSearch.setItems(FXCollections.observableArrayList(serviceArticle.getCouleurFiltre()));
        typeSearch.setItems(FXCollections.observableArrayList(serviceArticle.getTypeBieresFiltre()));
    }

    @FXML
    void addArticle() {

        String nomArticle = ajouterNom.getText();
        Integer  volumeArticle = Integer.parseInt(ajouterVolume.getText());
        Float titrageArticle = Float.valueOf(ajouterTitrage.getText()).floatValue();
        Float prixArticle = Float.valueOf(ajouterPrix.getText()).floatValue();
        Marque marqueArticle = marqueSearch.getValue();
        Couleur couleurArticle = couleurSearch.getValue();
        TypeBiere typeArticle = typeSearch.getValue();
        Integer stockArticle = Integer.parseInt(ajouterStock.getText());

        String sql="insert into ARTICLE (NOM_ARTICLE,PRIX_ACHAT,VOLUME,TITRAGE,ID_MARQUE,ID_COULEUR,ID_TYPE,STOCK) values (?,?,?,?,?,?,?,?)";
        if (!(ajouterNom.getText()).equals("")) {
            try {
                st = connection.prepareStatement(sql);
                st.setString(1, nomArticle);
                st.setFloat(2, prixArticle);
                st.setInt(3, volumeArticle);
                st.setFloat(4, titrageArticle);
                st.setInt(5, marqueArticle.getId());
                st.setInt(6, couleurArticle.getId());
                st.setInt(7, typeArticle.getId());
                st.setInt(8, stockArticle);
                st.execute();
                ajouterNom.setText("");
                ajouterVolume.setText("");
                ajouterPrix.setText("");
                ajouterTitrage.setText("");
                ajouterStock.setText("");

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Article ajouté avec succès!", ButtonType.OK);
                menuApp.getWindowAjouter().close();
                alert.showAndWait();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING,"Veuillez remplir tous les champs !", ButtonType.OK);
            alert.showAndWait();
        }
    }

        public void setMenuApp(MenuApp menuApp) {
        this.menuApp = menuApp;
    }

}
