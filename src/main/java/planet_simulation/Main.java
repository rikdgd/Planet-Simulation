package planet_simulation;

import graphics_engine.SimulationDisplay;
import graphics_engine.SimulationPanel;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Planet> planets = new ArrayList<>();

        double x = 0.0;
        double y = 0.0;
        for (int i = 0; i < 10; i++){
            Planet planet = new Planet(x, y, 500.0, 0.0, 0.0);
            x += 20.0;
            y += 20.0;

            planets.add(planet);
        }

        SimulationDisplay display = new SimulationDisplay();

        PhysicsEngine physicsEngine = new PhysicsEngine(planets);
        physicsEngine.start();


        while(physicsEngine.isAlive()){

            // Create the window
            SimulationPanel panel = new SimulationPanel(physicsEngine.getPlanetList(), 400, 400);
            display.AddSimulationPanel(panel);

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

            List<Planet> planetList = physicsEngine.getPlanetList();
            System.out.println("vel: " + planetList.get(0).xVel + " - " + planetList.get(0).yVel);
            System.out.println("pos: " + planetList.get(0).xPos + " - " + planetList.get(0).yPos);
        }
    }
}
