package application;
import java.util.*;
public class Plan {
private Map<Long,Intersection> intersections;
private Map<Long, Map<Long, Troncon>> troncons;


private void addIntersection(Intersection intersection) {
    this.intersections.put(intersection.getId(), intersection);
}

private void addTroncon(Troncon troncon) {
    Map<Long, Troncon> tronconorigine = troncons.get(troncon.getOrigine().getId());
    if (tronconorigine == null) {
    	tronconorigine = new TreeMap<Long, Troncon>();
    }

    tronconorigine.put(troncon.getDestination().getId(), troncon);
    this.troncons.put(troncon.getOrigine().getId(), tronconorigine);
}

public Plan(Iterable<Intersection> intersections, Iterable<Troncon> troncons) {
    this.intersections = new TreeMap<Long, Intersection>();
    this.troncons = new TreeMap<Long, Map<Long, Troncon>>();

    for (Intersection intersection : intersections) {
        this.addIntersection(intersection);
    }
    for (Troncon streetSection : troncons) {
        this.addTroncon(streetSection);
    }
}
public List<Intersection> getIntersection() {
    List<Intersection> listIntersection = new ArrayList<Intersection>();
    listIntersection.addAll(this.intersections.values());
    return listIntersection;
}

public List<Troncon> getTroncon() {
    List<Troncon> listStreetSection = new ArrayList<Troncon>();
    for (Map<Long, Troncon> value : this.troncons.values()) {
        listStreetSection.addAll(value.values());
    }
    return listStreetSection;
}
}
