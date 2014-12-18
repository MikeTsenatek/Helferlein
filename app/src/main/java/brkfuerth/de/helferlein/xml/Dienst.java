package brkfuerth.de.helferlein.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.util.List;
@Root
public class Dienst {
    @Element
    private String tag;

    @Element
    private String dienstbeginn;

    @Element
    private String id;

    @Element
    private String dienstdauer;

    @ElementList(inline = true)
    private List<Personal> personal;

    public String getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public String getDienstbeginn() {
        return dienstbeginn;
    }

    public String getDienstdauer() {
        return dienstdauer;
    }

    public List<Personal> getPersonal() {
        return personal;
    }
}
