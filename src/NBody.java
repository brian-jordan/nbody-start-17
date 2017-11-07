import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NBody {
	
	public static void main(String[] args){
		double totalTime = 157788000.0;
		double dt = 25000.0;
		String pfile = "data/planets.txt";
		if (args.length > 2) {
			totalTime = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			pfile = args[2];
		}
		
		String fname= "./data/planets.txt";
		Planet[] planets = readPlanets(fname);
		double radius = readRadius(fname);
		
		// create image witha all the planets
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0,0,"images/starfield.jpg");
		for(int i = 0; i < planets.length; i++){
			Planet a = planets[i];
			a.draw();
		}
		// simulation
		for(double t = 0.0; t < totalTime; t += dt) {
			// calculate forces on each planet
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];
			for(int i = 0; i < planets.length; i++){
				Planet a = planets[i];
				xForces[i] = a.calcNetForceExertedByX(planets);
				yForces[i] = a.calcNetForceExertedByY(planets);
			}
			for(int i = 0; i < planets.length; i++){
				// update planet data based on changes caused by the forces
				Planet a = planets[i];
				a.update(dt, xForces[i], yForces[i]);
			}
			StdDraw.picture(0,0,"images/starfield.jpg");
			for(int i = 0; i < planets.length; i++){
				// redraw planets according to new data
				Planet a = planets[i];
				a.draw();
			}
			StdDraw.show(10);
		}
		// print final data for the planets
		System.out.printf("%d\n", planets.length);
		System.out.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
		    System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		   		              planets[i].myXPos, planets[i].myYPos, 
		                      planets[i].myXVel, planets[i].myYVel, 
		                      planets[i].myMass, planets[i].myFileName);
		}
	}
	public static double readRadius(String fname){
		try{
			// create scanner and locate radius of universe value
			Scanner scan = new Scanner(new File(fname));
			scan.nextInt();
			double value = scan.nextDouble();
			scan.close();
			return value;
		}
		catch(FileNotFoundException e){
			// identify errors in locating file
			System.out.println("Error: File could not be found");
			double error = -1.0;
			return error;
		}
	}
	public static Planet[] readPlanets(String fname){
		try{
			// identify number of planets to make an array of planet objects
			Scanner scan = new Scanner(new File(fname));
			int numPlanets = scan.nextInt();
			scan.nextDouble();
			Planet[] planets = new Planet[numPlanets];
			// iterate through planets array to assign variable values from file
			for(int i = 0; i < planets.length; i++){
				planets[i] = new Planet(scan.nextDouble(), scan.nextDouble(), scan.nextDouble(), scan.nextDouble(), scan.nextDouble(), scan.next());
			}
			scan.close();
			return planets;
		}
		catch(FileNotFoundException e){
			// identify errors in locating file
			System.out.println("Error: File could not be found");
			Planet[] error = new Planet[0];
			return error;
		}
	}
}
