package planet_simulation;

public class Planet {

    double xPos;
    double yPos;
    double mass;
    private double radius;
    double xVel;
    double yVel;
    double sumVel;
    public boolean isActive;
    // todo: color prop

    public Planet(double XPos, double YPos, double Mass, double XVel, double YVel) {
        xPos = XPos;
        yPos = YPos;
        mass = Mass;
        radius = this.CalcRadius();
        xVel = XVel;
        yVel = YVel;

        double rawSumVel = Math.sqrt((xVel * xVel) + (yVel * yVel));
        sumVel = Math.round(rawSumVel);

        isActive = true;
    }

    public Planet(double XPos, double YPos, double Mass, double XVel, double YVel, boolean active) {
        xPos = XPos;
        yPos = YPos;
        mass = Mass;
        radius = this.CalcRadius();
        xVel = XVel;
        yVel = YVel;

        double rawSumVel = Math.sqrt((xVel * xVel) + (yVel * yVel));
        sumVel = Math.round(rawSumVel);

        isActive = active;
    }


    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public double getMass() {
        return mass;
    }

    public double getxVel() {
        return xVel;
    }

    public double getyVel() {
        return yVel;
    }

    public double getSumVel() {
        return sumVel;
    }

    // Calculates the planet's radius based on its mass.
    public double CalcRadius(){
        double newRadius = (Math.pow(mass / ((4 * Math.PI) / 3), 1.0 / 3.0));
        return newRadius;
    }

    public static double CalcRadius(double planetMass){
        double planetRadius = Math.round(Math.pow(planetMass / ((4 * Math.PI) / 3), 1 / 3));
        return planetRadius;
    }

    public void SetRadius(double newRadius){
        this.radius = newRadius;
    }

    public double GetXDist(Planet otherPlanet){
        return otherPlanet.xPos - this.xPos;
    }

    public double GetYDist(Planet otherPlanet){
        return otherPlanet.yPos - this.yPos;
    }

    public double CalcDistance(Planet otherPlanet){
        double dx = this.GetXDist(otherPlanet);
        double dy = this.GetYDist(otherPlanet);
        double distance = Math.sqrt((dx * dx) + (dy + dy));

        return distance;
    }

    public double CalcGravity(Planet otherPlanet){
        double massMultiplication = this.mass * otherPlanet.mass;
        double distance = CalcDistance(otherPlanet);
        // the 0.5 is the gravity constant used for the simulation.
        double forceGravity = massMultiplication / (distance * distance) * 0.5;
        return forceGravity;
    }

    public double[] CalcXYAcceleration(Planet otherPlanet){
        double dx = this.GetXDist(otherPlanet);
        double dy = this.GetYDist(otherPlanet);
        double forceGravity = this.CalcGravity(otherPlanet);

        // The part below is still in doubt
        double xAcceleration = forceGravity * dx * 0.000000067 * otherPlanet.mass;
        double yAcceleration = forceGravity * dy * 0.000000067 * otherPlanet.mass;

        return new double[]{xAcceleration, yAcceleration};
    }

    public double CalcAngle(Planet otherPlanet){
        double dx = this.GetXDist(otherPlanet);
        double dy = this.GetYDist(otherPlanet);

        double angleRadians = Math.atan(dy / dx);
        double angleDegrees = Math.toDegrees(angleRadians);

        return angleDegrees;
    }
}
