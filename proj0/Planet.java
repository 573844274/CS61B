public class Planet {
	/** represent a body in the universe*/
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	private static double G = 6.67e-11; 

	public Planet(double xP, double yP, double xV,
			double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet p) {
		// init a copy
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p) {
		/** return the distance between two planets*/
		double distance;
		distance = Math.sqrt(Math.pow((xxPos - p.xxPos),2) + Math.pow((yyPos - p.yyPos),2));
		return distance;
	}

	public double calcForceExertedBy(Planet p) {
		/** return the scalar force between two planets*/
		double r = calcDistance(p);
		double totalForce = (G * mass * p.mass)/(Math.pow(r,2));
		return totalForce;
	}

	public double calcForceExertedByX(Planet p) {
		/** Vector F_x*/
		double r = calcDistance(p);
		double dx = p.xxPos - xxPos;
		double totalForce = calcForceExertedBy(p);
		double fX = totalForce * dx /r; 
		return fX;

	}

	public double calcForceExertedByY(Planet p) {
		/** Vector F_y*/
		double r = calcDistance(p);
		double dy = p.yyPos - yyPos;
		double totalForce = calcForceExertedBy(p);
		double fY = totalForce * dy /r; 
		return fY;
	}

	public double calcNetForceExertedByX(Planet[] ps){
	/** Net force*/
		double fNetX = 0;
		for (Planet p : ps){
			if (!equals(p)){
				fNetX += calcForceExertedByX(p);
			}
		}
		return fNetX;
    }

    public double calcNetForceExertedByY(Planet[] ps){
	/** Net force*/
		double fNetY = 0;
		for (Planet p : ps){
			if (!equals(p)){
				fNetY += calcForceExertedByY(p);
			}
		}
		return fNetY;
    }

    public void update(double dt, double fX, double fY){
    	/**Update the position and the velocity*/
    	double aX = fX / mass;
    	double aY = fY / mass;
    	xxVel += dt * aX;
    	yyVel += dt * aY;
    	xxPos += dt * xxVel;
    	yyPos += dt * yyVel;

    }

    public void draw() {
    	/** Draw the planet on the background*/
    	StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}