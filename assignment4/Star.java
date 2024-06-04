package assignment4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * star class.
 */

public class Star extends HeavBody {
  private int minRadius;
  private int maxRadius;
  private List<Planet> planets;

  /**
   * star constructor.
   */

  public Star(String name, int radius) {
    super(name, radius);
    this.planets = new ArrayList<>();
    this.minRadius = 16000;
    this.maxRadius = 200000;
  }

  /**
   * add planet.
   */

  public void addPlanet(Planet planet) {
    planets.add(planet);
  }

  /**
   * remove planet.
   */

  public void removePlanet(Planet planet) {
    planets.remove(planet);
  }

  /**
   * return a copy.
   */

  public List<Planet> getPlanets() {
    return new ArrayList<>(planets);
  }

  /**
   * sort planet.
   */
  public List<Planet> getPlanetsSortedByOrbitalRadius() {
    List<Planet> sortedPlanets = new ArrayList<>(planets);
    Collections.sort(planets, Comparator.comparingInt(Planet::getOrbitRadius));
    return sortedPlanets;
  }

  /**
   * sort planet.
   */

  public List<Planet> getPlanetsSortedBySize() {
    List<Planet> sortedPlanets = new ArrayList<>(planets);
    sortedPlanets.sort(Comparator.comparingInt(Planet::getRadius));
    return sortedPlanets;
  }

  /**
   * remove moons.
   */

  public void removeMoon(Planet planet, Moon moon) {
    planet.removeMoon(moon);
  }

  /**
   * sort moons after size.
   */

  public List<Moon> getMoonsSortedBySize() {
    List<Moon> allMoons = new ArrayList<>();
    for (Planet planet : planets) {
      allMoons.addAll(planet.getMoons());
    }
    Collections.sort(allMoons, Comparator.comparingInt(Moon::getRadius));
    return allMoons;
  }

  /**
   * get minimum radius.
   */

  public int getMinRadius() {
    return minRadius;
  }

  /**
   * set minimum radius.
   */

  public void setMinRadius(int minRadius) {
    this.minRadius = minRadius;
  }

  /**
   * get maximum radius.
   */

  public int getMaxRadius() {
    return maxRadius;
  }

  /**
   * set maximum radius.
   */

  public void setMaxRadius(int maxRadius) {
    this.maxRadius = maxRadius;
  }

  /**
   * check the size.
   */
  @Override
  public boolean sizeOfBody() {
    return getRadius() >= minRadius && getRadius() <= maxRadius;
  }
}
