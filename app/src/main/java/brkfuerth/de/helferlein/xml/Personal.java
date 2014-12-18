package brkfuerth.de.helferlein.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;
@Root
public class Personal {
    @Element
    private String name;

    @Element
    private String funktion;

    @Element(required = false)
    private String telefon;

    public String getName() {
        return name;
    }

    public String getFunktion() {
        return funktion;
    }

    public String getTelefon() {
        return telefon;
    }
}
