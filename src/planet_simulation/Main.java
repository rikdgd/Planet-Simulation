package planet_simulation;

import graphics_engine.SimulationDisplay;
import graphics_engine.SimulationPanel;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Planet> planets = new ArrayList<Planet>();

        int x = 0;
        int y = 0;
        for (int i = 0; i < 10; i++){
            Planet planet = new Planet(x, y, 500, 0, 0);
            x += 20;
            y += 20;

            planets.add(planet);
        }

        SimulationDisplay display = new SimulationDisplay();
        SimulationPanel panel = new SimulationPanel(planets, 400, 400);
        display.AddSimulationPanel(panel);

        int planetCount = planets.size();
        while(planetCount > 1){

            PhysicsEngine pEngine = new PhysicsEngine(planets);
            pEngine.CalculateNewPlanetFrame();
            planets = pEngine.getPlanetList();

            panel.UpdatePanel(planets);
            planetCount = planets.size();
        }
    }
}
