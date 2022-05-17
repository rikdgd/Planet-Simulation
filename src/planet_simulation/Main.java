package planet_simulation;

import graphics_engine.SimulationDisplay;
import graphics_engine.SimulationPanel;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Planet> planets = new ArrayList<>();

        int x = 0;
        int y = 0;
        for (int i = 0; i < 10; i++){
            Planet planet = new Planet(x, y, 500, 0, 0);
            x += 20;
            y += 20;

            planets.add(planet);
        }

        SimulationDisplay display = new SimulationDisplay();

        PhysicsEngine physicsEngine = new PhysicsEngine(planets);
        physicsEngine.start();

        int i = 0;

        while(physicsEngine.isAlive()){

            SimulationPanel panel = new SimulationPanel(physicsEngine.getPlanetList(), 400, 400);
            display.AddSimulationPanel(panel);

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Planet> planetList = physicsEngine.getPlanetList();
            System.out.println(planetList.get(0).xPos + " - " + planetList.get(0).yPos);
        }
    }
}
