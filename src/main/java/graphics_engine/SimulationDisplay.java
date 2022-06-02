package graphics_engine;

import javax.swing.*;

public class SimulationDisplay extends JFrame {

    public SimulationDisplay(){
        this.setTitle("Planet Simulation");
        this.setDefaultCloseOperation(SimulationDisplay.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null); // Centers the display on the monitor.
    }

    public void AddSimulationPanel(SimulationPanel simulationPanel){
        this.add(simulationPanel);
        this.pack();
    }
}
