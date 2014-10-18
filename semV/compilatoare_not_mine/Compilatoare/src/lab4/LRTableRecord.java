package lab4;


import java.util.HashSet;
import java.util.Set;


public class LRTableRecord {
    private Set<Element> outbounds=new HashSet<Element>();
    private int origin;
    public LRTableRecord(Integer origin) {
        this.origin=origin;
    }


    public int getOrigin() {
        return origin;
    }
    public void addOutbound(Element e)
    {
        outbounds.add(e);
    }

    public Set<Element> getOutbounds() {
        return outbounds;
    }

    //

    @Override
    public String toString() {
        return "LRTableRecord{" +
                "outbounds=" + outbounds +
                ", origin=" + origin +
                '}';
    }
}
