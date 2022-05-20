package planet_simulation;

import java.util.ArrayList;
import java.util.List;

public class PhysicsEngine extends Thread {

    private List<Planet> planetList;

    public List<Planet> getPlanetList() {
        return planetList;
    }

    public PhysicsEngine(List<Planet> planetList) {
        this.planetList = planetList;
    }

    @Override
    public void run(){
        while(this.planetList.size() > 1){
            CalculateNewPlanetFrame();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void CalculateNewPlanetFrame(){
        List<Planet> newPlanetList = new ArrayList<>();

        for (Planet planet1 : planetList) {
            for (Planet planet2 : planetList){
                if(planet1 == planet2){
                    continue;
                }

                if (MergePossible(planet1, planet2)){
                    if (planet1.isActive && planet2.isActive){
                        planet1 = MergePlanet(planet1, planet2);

                        // After the planets are merged, delete the second planet.
//                      planetList.remove(planetList.indexOf(planet2));
                        planet2.isActive = false;
                    }
                }
            }

            planet1.xVel = GetNewPlanetXYVelocity(planet1, planetList)[0];
            planet1.yVel = GetNewPlanetXYVelocity(planet1, planetList)[1];

            if (planet1.isActive){
                newPlanetList.add(planet1);
            }
        }

        planetList = newPlanetList;
    }

    private boolean MergePossible(Planet planetA, Planet planetB){
        return planetA.CalcGravity(planetB) < planetA.CalcRadius() + planetB.CalcRadius();
    }

    private Planet MergePlanet(Planet planetA, Planet planetB){
        if (planetA.CalcDistance(planetB) < planetA.CalcRadius() + planetB.CalcRadius()){

            planetA.xVel = GetMergedXYVelocity(planetA, planetB)[0];
            planetA.yVel = GetMergedXYVelocity(planetA, planetB)[1];

            // Get new mass and radius.
            planetA.mass += planetB.mass;
            planetA.SetRadius(planetA.CalcRadius());

            planetA.xPos = GetMergedXYPosition(planetA, planetB)[0];
            planetA.yPos = GetMergedXYPosition(planetA, planetB)[1];
        }

        return planetA;
    }

    private double[] GetMergedXYVelocity(Planet planetA, Planet planetB){
        // The following lines are based on the principle of conservation of momentum.
        // First the total momentum is calculated, separately for the X and the Y axis.
        // Then the new velocity is calculated.
        double MomentumX = planetA.mass * planetA.xVel + planetB.mass * planetB.xVel;
        double MomentumY = planetA.mass * planetA.yVel + planetB.mass * planetB.xVel;
        double xVel = MomentumX / (planetA.mass + planetB.mass);
        double yVel = MomentumY / (planetA.mass + planetB.mass);

        return new double[] {xVel, yVel};
    }

    private double[] GetMergedXYPosition(Planet planetA, Planet planetB){
        // double dx = planetB.xPos - planetA.xPos;
        // double dy = planetB.yPos - planetA.yPos;

        // Calculate the x and y position for center of mass.
        double comX = (planetA.mass * planetA.xPos + planetB.mass * planetB.xPos) / (planetA.mass + planetB.mass);
        double comY = (planetA.mass * planetA.yPos + planetB.mass * planetB.yPos) / (planetA.mass + planetB.mass);

        return new double[] {comX, comY};
    }

    private double[] GetNewPlanetXYVelocity(Planet planet, List<Planet> otherPlanets){
        double xVel = planet.xVel;
        double yVel = planet.yVel;

        for (Planet otherPlanet : otherPlanets){
            if(planet != otherPlanet){
                double dx = planet.GetXDist(otherPlanet);
                double dy = planet.GetYDist(otherPlanet);
                double gravity = planet.CalcGravity(otherPlanet);
    
                // Below gravity gets multiplied with the x and y distance,
                // this is done because the force needs to be distributed.
                double xAcceleration = gravity * dx * 0.000000067 * otherPlanet.mass;
                double yAcceleration = gravity * dy * 0.000000067 * otherPlanet.mass;
    
                // Add the acceleration to the speed.
                xVel += xAcceleration;
                yVel += yAcceleration;
            }
        }

        return new double[] {xVel, yVel};
    }
}
