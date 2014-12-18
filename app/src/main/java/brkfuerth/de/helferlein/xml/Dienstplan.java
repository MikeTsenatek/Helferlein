package brkfuerth.de.helferlein.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.util.List;
@Root
public class Dienstplan {
    @Element
    private String name;

    @ElementList(inline = true)
    private List<Dienst> dienst;

    public String getName() {
        return name;
    }

    public List<Dienst> getDienst() {
        return dienst;
    }
}
