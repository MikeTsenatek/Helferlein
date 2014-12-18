package brkfuerth.de.helferlein.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by homer on 23.10.14.
 */
@Root
public class Dienste {

    @ElementList(inline = true)
    private List<Dienstplan> dienstplan;

    public List<Dienstplan> getDienstplan() {
        return dienstplan;
    }
}

