package brkfuerth.de.helferlein.Datenstrukturen;

/**
 * Created by homer on 12.11.14.
 */
public class AlarmNummer {
    private int nummer;
    private String name;

    public AlarmNummer(int nummer, String name) {
        this.setNummer(nummer);
        this.setName(name);
    }

    public AlarmNummer() {}

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
