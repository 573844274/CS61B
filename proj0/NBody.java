public class NBody {
	/** Simulate the universe*/
	public static double readRadius(String fileName) {
		In in = new In(fileName);
		int N = in.readInt();
		double size = in.readDouble();
		return size;
	}

	public static Planet[] readPlanets(String fileName) {
		In in = new In(fileName);
		int N = in.readInt();
		double size = in.readDouble();


		Planet[] planets = new Planet[N];
		for (int index = 0; index < N; index += 1) {
			double xP = in.readDouble();
			double yP = in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();
			planets[index] = new Planet(xP, yP, xV, yV, m,img);
		}
		return planets;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		String backGround = "images/starfield.jpg";

		Planet[] planets = readPlanets(filename);
		for (Planet p : planets){
			p.imgFileName = "images/" + p.imgFileName;
		}
		double universeRadius = readRadius(filename);

		//StdDraw.setScale(-universeRadius, universeRadius);
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(-universeRadius, universeRadius);
        StdDraw.setYscale(-universeRadius, universeRadius);
		
		/*
		StdDraw.picture(0, 0, backGround);
		for (Planet p : planets){
			p.draw();
		}
		*/
		double time = 0;

		while (time < T) {
			StdDraw.clear();

			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];
			for (int i = 0; i < planets.length; i += 1) {
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}
			for (int j = 0; j < planets.length; j += 1) {
				planets[j].update(dt, xForces[j], yForces[j]);
			}

			StdDraw.picture(0, 0, backGround);
			for (Planet p : planets) {
				p.draw();
			}

			StdDraw.show();
			StdDraw.pause(10);
		
			time += dt;

		}

		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", universeRadius);
		for (int i = 0; i < planets.length; i++) {
    	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
		}

	}
}