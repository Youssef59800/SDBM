package fr.fs.sdbm.metier;

import java.sql.Date;




public class Ticket {
  private long id;
  private Date date;
  private String heure;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getHeure() {
    return heure;
  }

  public void setHeure(String heure) {
    this.heure = heure;
  }

}
