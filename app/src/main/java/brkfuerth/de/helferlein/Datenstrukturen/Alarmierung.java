package brkfuerth.de.helferlein.Datenstrukturen;

/**
 * Created by homer on 12.11.14.
 */
public class Alarmierung {

    private int id;
    private int nummer;
    private String name;
    private String uhrzeit;
    private int anzahl;
    private int sofort;
    private int spaeter;
    private int nicht;
    private int unbekannt;
    private int nichtErreicht;

    public Alarmierung() {}

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public String getUhrzeit() {
        return uhrzeit;
    }

    public void setUhrzeit(String uhrzeit) {
        this.uhrzeit = uhrzeit;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public int getSofort() {
        return sofort;
    }

    public void setSofort(int sofort) {
        this.sofort = sofort;
    }

    public int getSpaeter() {
        return spaeter;
    }

    public void setSpaeter(int spaeter) {
        this.spaeter = spaeter;
    }

    public int getNicht() {
        return nicht;
    }

    public void setNicht(int nicht) {
        this.nicht = nicht;
    }

    public int getUnbekannt() {
        return unbekannt;
    }

    public void setUnbekannt(int unbekannt) {
        this.unbekannt = unbekannt;
    }

    public int getNichtErreicht() {
        return nichtErreicht;
    }

    public void setNichtErreicht(int nichtErreicht) {
        this.nichtErreicht = nichtErreicht;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
