package application;

import application.Intersection;

public class Troncon {
private String nomRue;
private Intersection destination,origine;
private double longueur;

public Troncon(String nomRue, Intersection destination, Intersection origine, double longueur) {
	super();
	this.nomRue = nomRue;
	this.destination = destination;
	this.origine = origine;
	this.longueur = longueur;
}

public String getNomRue() {
	return nomRue;
}


public Intersection getDestination() {
	return destination;
}

public Intersection getOrigine() {
	return origine;
}

public double getLongueur() {
	return longueur;
}

}
