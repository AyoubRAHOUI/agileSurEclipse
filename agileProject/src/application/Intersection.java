package application;

public class Intersection {
	private long id;
	private	double x,y;
	public Intersection(long id,double x,double y) {
	
	this.id=id;
	this.x=x;
	this.y=y;
}
	public long getId() {
		return this.id;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
}
