package assignment4;

/**
 * moon class.
 */

public class Moon extends HeavBody {
  private int minRadius;
  private int maxRadius;
  private int minOrbitRdius;
  private int maxOrbitRdius;
  private int orbitRadius;


  /**
   * moon constructor.
   */

  public Moon(String name, int radius, int orbitRadius) {
    super(name, radius);
    this.minRadius = 20;
    this.maxRadius = 600;
    this.minOrbitRdius = 10;
    this.maxOrbitRdius = 100;
  }

  /**
   * get orbit radius.
   */

  public int getOrbitRadius() {
    return orbitRadius;
  }

  /**
   * get minimum orbit radius.
   */

  public int getMinOrbitRadius() {
    return minOrbitRdius;
  }

  /**
   * set minimum orbit radius.
   */

  public void setMinOrbitRadius(int minOrbitRdius) {
    this.minOrbitRdius = minOrbitRdius;
  }

  /**
   * get maximum orbit radius.
   */

  public int getMaxOrbitRadius() {
    return maxOrbitRdius;
  }

  /**
   * set maximum orbit radius.
   */

  public void setMaxOrbitRadius(int maxOrbitRdius) {
    this.maxOrbitRdius = maxOrbitRdius;
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

  @Override
  public boolean sizeOfBody() {
    return getRadius() >= minRadius && getRadius() <= maxRadius;
  }

}
