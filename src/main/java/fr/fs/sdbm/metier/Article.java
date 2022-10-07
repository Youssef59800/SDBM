package fr.fs.sdbm.metier;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Article
{

	private Integer id;
	private String libelle;
	private Integer volume;
	private Float titrage;
	private Float prixAchat;
	private Integer stock;
	private Marque marque;
	private Couleur couleur;
	private TypeBiere typeBiere;

	public Article(String libelle, Integer volume, Float titrage, Float prixAchat, Marque marque, Couleur couleur, TypeBiere typeBiere, Integer stock) {
		this.libelle = libelle;
		this.volume = volume;
		this.titrage = titrage;
		this.prixAchat = prixAchat;
		this.marque = marque;
		this.couleur = couleur;
		this.typeBiere = typeBiere;
		this.stock = stock;

	}


	public Article()
	{
		couleur = new Couleur();
		typeBiere = new TypeBiere();
		marque = new Marque();
	}

	public Article(Integer id, String libelle)
	{
		this.id = id;
		this.libelle = libelle;
		couleur = new Couleur();
		typeBiere = new TypeBiere();
		marque = new Marque();
	}

	public Article(int id, String libelle, int volume) {
		this.id =id;
		this.libelle =libelle;
		this.volume=volume;
	}

	public IntegerProperty idProperty()
	{
		return new SimpleIntegerProperty(id);
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public StringProperty libelleProperty()
	{
		return new SimpleStringProperty(libelle);
	}

	public String getLibelle()
	{
		return libelle;
	}

	public void setLibelle(String libelle)
	{
		this.libelle = libelle;
	}

	public IntegerProperty volumeProperty()
	{
		return new SimpleIntegerProperty(volume);
	}
	public IntegerProperty stockProperty() { return new SimpleIntegerProperty(stock);}

	public Integer getVolume()
	{
		return volume;
	}

	public void setVolume(Integer volume)
	{
		this.volume = volume;
	}

	public FloatProperty titrageProperty()
	{
		return new SimpleFloatProperty(titrage);
	}

	public Float getTitrage()
	{
		return titrage;
	}

	public void setTitrage(Float titrage)
	{
		this.titrage = titrage;
	}

	public Couleur getCouleur()
	{
		return couleur;
	}

	public void setCouleur(Couleur couleur)
	{
		this.couleur = couleur;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Marque getMarque()
	{
		return marque;
	}

	public void setMarque(Marque marque)
	{
		this.marque = marque;
	}

	public TypeBiere getTypeBiere()
	{
		return typeBiere;
	}

	public void setTypeBiere(TypeBiere typeBiere)
	{
		this.typeBiere = typeBiere;
	}

	public Float getPrixAchat()
	{
		return prixAchat;
	}

	public void setPrixAchat(Float prixAchat)
	{
		this.prixAchat = prixAchat;
	}

	@Override
	public String toString() {
		return "Article{" +
				"libelle='" + libelle + '\'' +
				", volume=" + volume +
				", titrage=" + titrage +
				", prixAchat=" + prixAchat +
				", stock=" + stock +
				", marque=" + marque +
				", couleur=" + couleur +
				", typeBiere=" + typeBiere +
				'}';
	}
}
