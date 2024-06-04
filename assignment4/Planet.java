package assignment4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * planet class.
 */

public class Planet extends HeavBody {
  private int minRadius;
  private int maxRadius;
  private int minOrbitRdius;
  private int maxOrbitRdius;
  private int orbitRadius;
  private List<Moon> moons;

  /**
   * constructor.
   */

  public Planet(String name, int radius) {
    super(name, radius);
    moons = new ArrayList<>();
  }

  /**
   * planet constructor.
   */

  public Planet(String name, int radius, int orbitRadius) {
    super(name, radius);
    this.orbitRadius = orbitRadius;
    this.moons = new ArrayList<>();
    this.minRadius = 200;
    this.maxRadius = 6000;
    this.minOrbitRdius = 100;
    this.maxOrbitRdius = 1000;
  }

  /**
   * add moon method.
   */

  public void addMoon(Moon moon) {
    moons.add(moon);
  }

  /**
   * get orbit radius.
   */

  public int getOrbitRadius() {
    return orbitRadius;
  }

  /**
   * remove moon.
   */
  
  public void removeMoon(Moon moon) {
    moons.remove(moon);
  }

  /**
   * return a copy.
   */

  public List<Moon> getMoons() {
    return new ArrayList<>(moons);
  }

  /**
   * return list of moons.
   */

  public List<Moon> getMoonsSortedBySize() {
    List<Moon> sortedMoons = new ArrayList<>(moons);
    Collections.sort(sortedMoons, Comparator.comparingInt(Moon::getRadius));
    return sortedMoons;
  }

  /**
   * sort moons after OR.
   */

  public List<Moon> getMoonsSortedByOrbitalRadius() {
    List<Moon> sortedMoons = new ArrayList<>(moons);
    Collections.sort(sortedMoons, Comparator.comparingInt(Moon::getOrbitRadius));
    return sortedMoons;
  }

  /**
   * get minimum radius.
   */

  public int getMinRadius() {
    return minRadius;
  }

  /**
   * get minimum orbit radius.
   */

  public int getMinOrbitRadius() {
    return minOrbitRdius;
  }

  /**
   * set minOrbitRdius.
   */

  public void setMinOrbitRadius(int minOrbitRdius) {
    this.minOrbitRdius = minOrbitRdius;
  }

  /**
   * return maximum orbit radius.
   */

  public int getMaxOrbitRadius() {
    return maxOrbitRdius;
  }

  /**
   * set maxOrbitRdius.
   */

  public void setMaxOrbitRadius(int maxOrbitRdius) {
    this.maxOrbitRdius = maxOrbitRdius;
  }

  /**
   * set minRadius.
   */

  public void setMinRadius(int minRadius) {
    this.minRadius = minRadius;
  }

  /**
   * return maximum radius.
   */

  public int getMaxRadius() {
    return maxRadius;
  }

  /**
   * set maxRadius.
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
