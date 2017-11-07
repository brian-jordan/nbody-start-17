
public class Planet {
	// Instance Variables
	double myXPos;
	double myYPos;
	double myXVel;
	double myYVel;
	double myMass;
	String myFileName;
	
	// Constructors
	public Planet(double xp, double yp, double xv, double yv, double mass, String filename){
		myXPos = xp;
		myYPos = yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;
	}
	public Planet(Planet p){
		myXPos = p.myXPos;
		myYPos = p.myYPos;
		myXVel = p.myXVel;
		myYVel = p.myYVel;
		myMass = p.myMass;
		myFileName = p.myFileName;
	}
	
	// Methods
	public double calcDistance(Planet p){
		// distance equation given, plugging in correct inputs
		double distance = Math.sqrt(Math.pow(p.myXPos - this.myXPos, 2) + Math.pow(p.myYPos - this.myYPos, 2));
		return distance;
	}
	
	public double calcForceExertedBy(Planet p){
		double G = 6.67 * Math.pow(10, -11);
		// force equation given, plugging in correct inputs
		double force = (G * this.myMass * p.myMass) / Math.pow(this.calcDistance(p), 2);
		return force;
	}
	
	public double calcForceExertedByX(Planet p){
		// x component of force calculation using given equation
		double forceX = this.calcForceExertedBy(p) * (p.myXPos - this.myXPos) / this.calcDistance(p);
		return forceX;
	}
	
	public double calcForceExertedByY(Planet p){
		// y component of force calculation using given equation
		double forceY = this.calcForceExertedBy(p) * (p.myYPos - this.myYPos) / this.calcDistance(p);
		return forceY;
	}
	public double calcNetForceExertedByX(Planet[] p){
		double sum = 0;
		// iterating through array of planets adding up their forces in the x direction on this planet
		for (Planet k: p){
			if (! k.equals(this)){
				sum += this.calcForceExertedByX(k);
			}
		}
		return sum;
	}
	
	public double calcNetForceExertedByY(Planet[] p){
		double sum = 0;
		// iterating through the array of planets adding up their forces in the y direction on this planet
		for (Planet k: p){
			if (! k.equals(this)){
				sum += this.calcForceExertedByY(k);
			}
		}
		return sum;
	}
	
	public void update(double seconds, double xforce, double yforce){
		// update instance variables with new values
		double aX = xforce / this.myMass;
		double aY = yforce / this.myMass;
		this.myXVel = this.myXVel + seconds * aX;
		this.myYVel = this.myYVel + seconds * aY;
		this.myXPos = this.myXPos + seconds * this.myXVel;
		this.myYPos = this.myYPos + seconds * this.myYVel;
	}
	public void draw(){
		// creates planet image
		StdDraw.picture(myXPos, myYPos, "images/" + myFileName);
	}
}
