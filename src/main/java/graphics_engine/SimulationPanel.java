package graphics_engine;

import planet_simulation.Planet;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SimulationPanel extends JPanel {

    private transient List<Planet> planetList;

    public List<Planet> getPlanetList() {
        return planetList;
    }

    public void setPlanetList(List<Planet> planetList) {
        this.planetList = planetList;
    }

    public SimulationPanel(List<Planet> planets, int panelWidth, int panelHeight){
        this.planetList = planets;
        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Planet planet : planetList){
            int x = GetPlanetDrawLocation(planet)[0];
            int y = GetPlanetDrawLocation(planet)[1];
            int radius = (int)planet.CalcRadius();

            g2d.fillOval(x, y, radius, radius);
        }

    }

    // todo: add method to clear the panel, display should not be cleared.
    public void UpdatePanel(List<Planet> planets){
        this.planetList = planets;
        this.repaint();
    }

    private int[] GetPlanetDrawLocation(Planet planet){
        // Ellipses are drawn from the top left corner of the surrounding rectangle.
        // So the planets draw location needs to be shifted to the top left.
        int drawX = (int)planet.getxPos() - (int)planet.CalcRadius();
        int drawY = (int)planet.getyPos() - (int)planet.CalcRadius();

        return new int[] {drawX, drawY};
    }
}
