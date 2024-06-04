package assignment4;

import java.util.ArrayList;
import java.util.List;

/**
 * Registry class.
 */

public class Registry {

  private List<Star> stars;

  public Registry() {
    stars = new ArrayList<>();
  }

  public void addStar(Star star) {
    stars.add(star);
  }

  public void removeStar(Star star) {
    stars.remove(star);
  }

  public List<Star> getStars() {
    return new ArrayList<>(stars);
  }

}
