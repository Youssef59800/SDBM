package fr.fs.sdbm;

import fr.fs.sdbm.dao.SDBMConnect2;
import fr.fs.sdbm.metier.*;
import fr.fs.sdbm.service.ArticleSearch;
import fr.fs.sdbm.service.ServiceArticle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class GestionArticleController {

    @FXML
    private RadioButton choixVolume33;
    @FXML
    private RadioButton choixVolume75;
    @FXML
    private Label labelNom;
    @FXML
    private Label labelMarque;
    @FXML
    private Label labelType;
    @FXML
    private Label labelCouleur;
    @FXML
    private Label labelPays;
    @FXML
    private Label labelContinent;
    @FXML
    private Label labelTitrage;
    @FXML
    private Label labelVolume;
    @FXML
    private Label labelPrix;
    @FXML
    private Label labelFabricant;
    @FXML
    private Label labelStock;
    @FXML
    private TableView<Article> bieresTable;

    @FXML
    private ComboBox<Continent> continentSearch;

    @FXML
    private ComboBox<Couleur> couleurSearch;

    @FXML
    private ComboBox<Fabricant> fabricantSearch;

    @FXML
    private TableColumn<Article, Integer> idColumn;

    @FXML
    private TextField libelleSearch;

    @FXML
    private TableColumn<Article, String> nomColumn;

    @FXML
    private ComboBox<Pays> paysSearch;

    @FXML
    private ComboBox<TypeBiere> typeSearch;
    @FXML
    private ComboBox<Marque> marqueSearch;

    @FXML
    private TableColumn<Article, Integer> volumeColumn;

    private MenuApp menuApp;
    private ServiceArticle serviceArticle;
    private Connection connection;
    private PreparedStatement st;
    private Article articleChoisi;

    private ObservableList<Article> data = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        serviceArticle = new ServiceArticle();
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().libelleProperty());
        volumeColumn.setCellValueFactory(cellData -> cellData.getValue().volumeProperty().asObject());

        continentSearch.setItems(FXCollections.observableArrayList(serviceArticle.getContinentFiltre()));
        continentSearch.getItems().add(0, new Continent(0, "Choisir un continent"));
        continentSearch.valueProperty().addListener(observable -> filterContinent());

        paysSearch.setItems(FXCollections.observableArrayList(serviceArticle.getPaysFiltre()));
        paysSearch.valueProperty().addListener(observable -> filterArticle());

        fabricantSearch.setItems(FXCollections.observableArrayList(serviceArticle.getFabricantFiltre()));
        fabricantSearch.valueProperty().addListener(observable -> filterArticle());

        typeSearch.setItems(FXCollections.observableArrayList(serviceArticle.getTypeBieresFiltre()));
        typeSearch.valueProperty().addListener(observable -> filterArticle());

        couleurSearch.setItems(FXCollections.observableArrayList(serviceArticle.getCouleurFiltre()));
        couleurSearch.valueProperty().addListener(observable -> filterArticle());

        marqueSearch.setItems(FXCollections.observableArrayList(serviceArticle.getMarqueFiltre()));
        marqueSearch.valueProperty().addListener(observable -> filterArticle());

        libelleSearch.textProperty().addListener((observableValue, oldValue, newValue) -> filterArticle());

        bieresTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> afficherDetails(newValue));

    }


    public Article afficherDetails(Article newValue) {

        labelNom.setText(newValue.getLibelle());
        labelContinent.setText(newValue.getMarque().getPays().getContinent().getLibelle());
        labelPays.setText(newValue.getMarque().getPays().getLibelle());
        labelMarque.setText(newValue.getMarque().getLibelle());
        labelCouleur.setText(newValue.getCouleur().getLibelle());
        labelType.setText(newValue.getTypeBiere().getLibelle());
        labelPrix.setText(String.valueOf(newValue.getPrixAchat()));
        labelTitrage.setText(String.valueOf(newValue.getTitrage()));
        labelVolume.setText(String.valueOf(newValue.getVolume()));
        labelFabricant.setText(newValue.getMarque().getLibelle());
        labelStock.setText(String.valueOf(newValue.getStock()));
        System.out.println(newValue.getStock());
        this.articleChoisi=newValue;
        return newValue;
    }

    private void filterContinent() {
        if (continentSearch.getSelectionModel().getSelectedItem() != null
                && (continentSearch.getSelectionModel().getSelectedItem()).getId() != 0) {
            paysSearch.setItems(FXCollections.observableArrayList(
                    (continentSearch.getSelectionModel().getSelectedItem()).getPays()));

        } else {
            paysSearch.setItems(FXCollections.observableArrayList(serviceArticle.getPaysFiltre()));
        }
        paysSearch.getItems().add(0, new Pays("", "Choisir un pays", new Continent()));
        paysSearch.getSelectionModel().select(0);
        filterArticle();
    }

    @FXML
    private void filterArticle() {

        ArticleSearch articleSearch = new ArticleSearch();
        articleSearch.setLibelle(libelleSearch.getText());

        if (paysSearch.getSelectionModel().getSelectedItem() != null)
            articleSearch.setPays(paysSearch.getSelectionModel().getSelectedItem());
        if (continentSearch.getSelectionModel().getSelectedItem() != null)
            articleSearch.setContinent(continentSearch.getSelectionModel().getSelectedItem());
        if (fabricantSearch.getSelectionModel().getSelectedItem() != null)
            articleSearch.setFabricant(fabricantSearch.getSelectionModel().getSelectedItem());
        if (typeSearch.getSelectionModel().getSelectedItem() != null)
            articleSearch.setTypeBiere(typeSearch.getSelectionModel().getSelectedItem());
        if (couleurSearch.getSelectionModel().getSelectedItem() != null)
            articleSearch.setCouleur(couleurSearch.getSelectionModel().getSelectedItem());
        if (marqueSearch.getSelectionModel().getSelectedItem() != null)
            articleSearch.setMarque(marqueSearch.getSelectionModel().getSelectedItem());
        if (choixVolume75.isSelected() && choixVolume33.isSelected())
            articleSearch.setVolume(0);
        else if (choixVolume33.isSelected())
            articleSearch.setVolume(33);
        else if (choixVolume75.isSelected())
            articleSearch.setVolume(75);


        bieresTable.setItems(FXCollections.observableArrayList(serviceArticle.getFilteredArticles(articleSearch)));
    }

    public void setMenuApp(MenuApp menuApp) {

        this.menuApp = menuApp;
        filterArticle();
    }

    public void afficherPageAjouter() {

        menuApp.afficherWindowAjouter();
        filterArticle();
    }

    public void supprimerArticle() {

        connection = SDBMConnect2.getInstance();
        Article article = bieresTable.getSelectionModel().getSelectedItem();
        String sql = "delete from ARTICLE where ID_ARTICLE=?";

        try {
            st = connection.prepareStatement(sql);
            st.setInt(1, article.getId());
            st.execute();
            Alert alerte = new Alert(Alert.AlertType.WARNING,"Etes vous sûr de vouloir supprimer cet article",ButtonType.YES);
            alerte.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mettreTableAjour();
    }

    public void mettreTableAjour() {
        filterArticle();
    }

    public void AfficherPageUpdate() {
        menuApp.afficherWindowUpdate(articleChoisi);
        filterArticle();
    }
}