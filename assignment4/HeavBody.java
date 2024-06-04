package assignment4;

/**
 * class for heavenly body.
 */

public abstract class HeavBody {

  private String name;
  private int radius;

  public HeavBody(String name, int radius) {
    this.name = name;
    this.radius = radius;
  }

  /**
   * return the name.
   */

  public String getName() {
    return name;
  }

  /**
   * method for set name.
   */

  public void setName(String name) {
    this.name = name;
  }

  /**
   * return the radius.
   */

  public int getRadius() {
    return radius;
  }

  /**
   * set radius method.
   */

  public void setRadius(int radius) {
    this.radius = radius;
  }

  // check the size
  public abstract boolean sizeOfBody();
}
