package fr.fs.sdbm.dao;

import fr.fs.sdbm.metier.*;
import fr.fs.sdbm.service.ArticleSearch;

import java.sql.*;
import java.util.ArrayList;

public class ArticleDAO extends DAO<Article, ArticleSearch> {

    public ArticleDAO(Connection connexion)
    {
        super(connexion);
    }

    public ArrayList<Article> getLike(ArticleSearch articleSearch) {
        ResultSet rs;
        ArrayList<Article> liste = new ArrayList<>();
        String procedureStockee = "{call dbo.sp_QBE_Vue_Article (?,?,?,?,?,?,?,?,?,?,?,?)}";
        try (CallableStatement cStmt = this.connexion.prepareCall(procedureStockee)) {
            cStmt.setString(1, articleSearch.getLibelle());
            cStmt.setInt(2, articleSearch.getVolume());
            cStmt.setFloat(3, articleSearch.getTitrage());
            cStmt.setFloat(4, articleSearch.getTitrage());
            cStmt.setInt(5, articleSearch.getMarque().getId());
            cStmt.setInt(6, articleSearch.getFabricant().getId());
            cStmt.setString(7, articleSearch.getPays().getId());
            cStmt.setInt(8, articleSearch.getContinent().getId());
            cStmt.setInt(9, articleSearch.getCouleur().getId());
            cStmt.setInt(10, articleSearch.getTypeBiere().getId());
            cStmt.setNull(11, Types.INTEGER);
            cStmt.setNull(12, Types.INTEGER);

            cStmt.execute();
            rs = cStmt.getResultSet();

            while (rs.next())
            {
                // création d'un nouvel article à partir d'une ligne du resultset
                Article newArticle = new Article();
                newArticle.setId(rs.getInt(11));
                newArticle.setLibelle(rs.getString(12));
                newArticle.setPrixAchat(rs.getFloat(13));
                newArticle.setVolume(rs.getInt(14));
                newArticle.setTitrage(rs.getFloat(15));
                newArticle.setMarque(new Marque());
                newArticle.getMarque().setId(rs.getInt(5));
                newArticle.getMarque().setLibelle(rs.getString(6));
                newArticle.setCouleur(new Couleur());
                newArticle.getCouleur().setId(rs.getInt(16));
                newArticle.getCouleur().setLibelle(rs.getString(17));
                newArticle.setTypeBiere(new TypeBiere());
                newArticle.getTypeBiere().setId(rs.getInt(9));
                newArticle.getTypeBiere().setLibelle(rs.getString(10));
                newArticle.getMarque().setPays(new Pays());
                newArticle.getMarque().getPays().setId(rs.getString(2));
                newArticle.getMarque().getPays().setLibelle(rs.getString(4));
                newArticle.getMarque().getPays().setContinent(new Continent());
                newArticle.getMarque().getPays().getContinent().setId(rs.getInt(1));
                newArticle.getMarque().getPays().getContinent().setLibelle(rs.getString(3));
                newArticle.getMarque().setFabricant(new Fabricant());
                newArticle.getMarque().getFabricant().setId(rs.getInt(7));
                newArticle.getMarque().getFabricant().setLibelle(rs.getString(8));
                newArticle.setStock(rs.getInt(18));



                liste.add(newArticle);
            }
            rs.close();
        }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return liste;
    }

    @Override
    public Article getByID(int id) {
        return null;
    }

    @Override
    public ArrayList<Article> getAll() {
        return null;
    }


    @Override
    public boolean insert(Article objet) {

        return true;
    }

    @Override
    public boolean update(Article object) {
        return false;
    }

    @Override
    public boolean delete(Article object) {
        return false;
    }
}
