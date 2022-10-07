package fr.fs.sdbm.service;

import fr.fs.sdbm.dao.DaoFactory;
import fr.fs.sdbm.metier.*;
import java.util.ArrayList;

public class ServiceArticle  {
    private ArrayList<Pays> paysFiltre;
    private ArrayList<Continent> continentFiltre;
    private ArrayList<Fabricant> fabricantFiltre;
    private ArrayList<Marque> marqueFiltre;
    private ArrayList<TypeBiere> typeBieresFiltre;
    private ArrayList<Couleur> couleurFiltre;


    public ServiceArticle() {
        couleurFiltre = DaoFactory.getCouleurDAO().getAll();
        typeBieresFiltre = DaoFactory.getTypeBiere().getAll();
        fabricantFiltre = DaoFactory.getFabricantDAO().getAll();
        paysFiltre = DaoFactory.getPaysDAO().getAll();
        continentFiltre = DaoFactory.getContinentDAO().getAll();
        marqueFiltre=DaoFactory.getMarqueDAO().getAll();
        Fabricant fabricant = new Fabricant();
        fabricant.setLibelle("Choisir un fabricant");
        fabricantFiltre.add(0, fabricant);
        TypeBiere typeBiere = new TypeBiere();
        typeBiere.setLibelle("Choisir un type");
        typeBieresFiltre.add(0, typeBiere);
        Couleur couleur = new Couleur();
        couleur.setLibelle("Choisir une couleur");
        couleurFiltre.add(0, couleur);
        Pays pays = new Pays();
        pays.setLibelle("Choisir un pays");
        paysFiltre.add(0, pays);
        Marque marque = new Marque();
        marque.setLibelle("Choisir une marque");
        marqueFiltre.add(0, marque);

    }

    public ArrayList<Pays> getPaysFiltre()
    {
        return paysFiltre;
    }

    public ArrayList<Continent> getContinentFiltre()
    {
        return continentFiltre;
    }


    public ArrayList<Fabricant> getFabricantFiltre()
    {
        return fabricantFiltre;
    }

    public ArrayList<Marque> getMarqueFiltre()
    {
        return marqueFiltre;
    }

    public ArrayList<TypeBiere> getTypeBieresFiltre() {return typeBieresFiltre;}

    public ArrayList<Couleur> getCouleurFiltre() { return couleurFiltre;}

    public ArrayList<Article> getFilteredArticles(ArticleSearch articleSearch)
    {
        return DaoFactory.getArticleDAO().getLike(articleSearch);
    }

}
