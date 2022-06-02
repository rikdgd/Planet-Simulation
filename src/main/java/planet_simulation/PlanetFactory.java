package planet_simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlanetFactory {

    private Random random;

    public PlanetFactory(){
        random = new Random();
    }


    public List<Planet> CreateScatterPlanetList(int planetCount, int xPosMax, int yPosMax, double xVelMax, double yVelMax, double mass){
        List<Planet> planetList = new ArrayList<>();

        for (int i = 0; i < planetCount; i++){
            double randomXVel = -xVelMax + (random.nextDouble() * (xVelMax + xVelMax));
            double randomYVel = -yVelMax + (random.nextDouble() * (yVelMax + yVelMax));

            Planet newPlanet = new Planet(
                    random.nextInt(xPosMax),
                    random.nextInt(yPosMax),
                    mass,
                    randomXVel,
                    randomYVel
            );

            planetList.add(newPlanet);
        }

        return planetList;
    }

    public List<Planet> CreateStableOrbitList(int planetCount, int xPosMax, int yPosMax, double mass){
        List<Planet> planetList = new ArrayList<>();

        // Start by creating the big planet to orbit the smaller ones around
        Planet bigPlanet = new Planet(xPosMax / 2d, yPosMax / 2d, mass*100, 0, 0);
        planetList.add(bigPlanet);

        // Start creating the planets with stable orbits
        for (int i = 0; i < planetCount; i++){
            int planetXPos = random.nextInt(xPosMax);
            int planetYPos = random.nextInt(yPosMax);
            Planet newPlanet = new Planet(planetXPos, planetYPos, mass, 0, 0);

            // Get the straight and x/y distance
            double distToPlanet = newPlanet.CalcDistance(bigPlanet);
            double dx = bigPlanet.xPos - newPlanet.xPos;
            double dy = bigPlanet.yPos - newPlanet.yPos;

            // Calculate the 90 degrees vector to the big planet
            double orbitXVector = dy * -1;
            double orbitYVector = dx;

            // Calculate the stable orbit velocity
            // http://www.dzre.com/alex/P441/lectures/lec_22.pdf  page 1 last formula
            double stableVelocity = Math.sqrt(bigPlanet.mass / distToPlanet);

            // Use polar coordinates to determine the new x and y velocity
            double movementAngle = newPlanet.CalcAngle(bigPlanet) + 90;
            double newXVel = Math.cos(movementAngle) * stableVelocity;
            double newYVel = Math.sin(movementAngle) * stableVelocity;

            // Change the new planets velocity and add it to the list
            newPlanet.xVel = newXVel;
            newPlanet.yVel = newYVel;
            planetList.add(newPlanet);
        }

        return planetList;
    }
}
