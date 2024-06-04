package assignment4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is the App.
 */

public class App {
  private List<Star> stars;
  private Scanner input;

  private App(Scanner scannerInput) {
    this.input = scannerInput;
    this.stars = new ArrayList<>();
  }

  /**
   * print solar system menu.
   */

  public void menu() {
    int choice;
    do {
      System.out.println("Solar System menu:");
      System.out.println("1. Create a new solar system");
      System.out.println("2. List all solar systems");
      System.out.println("3. Display detailed information about a solar system");
      System.out.println("4. Delete a member of a solar system");
      System.out.println("5. Add a member to a solar system");
      System.out.println("6. Save the registry");
      System.out.println("7. Quit the application");
      System.out.println("Enter your choice: ");

      choice = input.nextInt();
      input.nextLine();

      switch (choice) {
        case 1:
          createSolarSystem();
          break;
        case 2:
          listSolarSystems();
          break;
        case 3:
          displaySolarSystem();
          break;
        case 4:
          deleteMemberFromSolarSystem();
          break;
        case 5:
          addSolarSystem();
          break;
        case 6:
          saveRegistryToFile("Solarsystem.data");
          break;
        case 7:
          throw new RuntimeException("Quitting the application.");
        default:
          System.out.println("Invalid choice. Please try again.");
          break;
      }
    } while (choice != 7);
  }

  /**
   * method to load file.
   */

  public void loadRegistryFromFile(String fileName) {
    try (Scanner scanner = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();
        if (!line.isEmpty()) {
          String[] parts = line.split(":");
          if (parts.length < 2) {
            System.out.println("Invalid line format: " + line);
            continue;
          }
          String name = parts[0];
          int radius = Integer.parseInt(parts[1]);
          Star star = new Star(name, radius);
          stars.add(star);

          for (int i = 2; i < parts.length; i++) {
            String[] bodyInfo = parts[i].split(",");
            String bodyType = bodyInfo[0];
            String bodyName = bodyInfo[1];
            int bodyRadius = Integer.parseInt(bodyInfo[2]);
            int orbitRadius = Integer.parseInt(bodyInfo[3]);
            if (bodyType.equals("Planet ")) {
              Planet planet = new Planet(bodyName, bodyRadius, orbitRadius);
              star.addPlanet(planet);
            } else if (bodyType.equals("Moon ")) {
              Moon moon = new Moon(name, radius, orbitRadius);
              Planet lastPlanet = star.getPlanets().get(star.getPlanets().size() - 1);
              lastPlanet.addMoon(moon);
            }
          }
        }
      }
      System.out.println("Registry loaded successfully.");
    } catch (FileNotFoundException e) {
      System.out.println("Error: Registry file not found.");
    } catch (IOException e) {
      System.out.println("Error: File not found.");
    }
  }

  /**
   * method to sve registry to a file.
   */

  public void saveRegistryToFile(String fileName) {
    try (PrintWriter writer = new PrintWriter(new File(fileName), StandardCharsets.UTF_8)) {
      for (Star star : stars) {
        writer.println(star.getName() + ":" + star.getRadius());
        for (Planet planet : star.getPlanets()) {
          writer.println("-Planet, " + planet.getName() + "," + planet.getRadius() + "," + planet.getOrbitRadius());
          for (Moon moon : planet.getMoons()) {
            writer.println("--Moon, " + moon.getName() + "," + moon.getRadius() + "," + moon.getOrbitRadius());
          }
        }
      }
      System.out.println("Registry saved successfully.");
    } catch (IOException e) {
      System.out.println("Error: saving registry.");
    }
  }

  /**
   * create solar system.
   */

  private void createSolarSystem() {
    System.out.println("Enter the name of star: ");
    String starName;
    starName = input.nextLine();

    System.out.println("Enter the radius of the star: ");
    int starRadius;
    starRadius = input.nextInt();
    input.nextLine();

    Star star = new Star(starName, starRadius);
    stars.add(star);

    boolean addPlanets = true;

    while (addPlanets) {
      System.out.println("Would you like to add a planet to the solar system? (y/n)");

      String answer = input.nextLine();
      if (answer.equals("y")) {
        addNewPlanet(star);
      } else if (answer.equals("n")) {
        addPlanets = false;
        break;
      } else {
        System.out.println("Invalid choice. Enter 'y' or 'n'.");
      }
    }

    boolean addMoons = true;

    while (addMoons) {
      System.out.println("Would you like to add a moon to the solar system? (y/n)");
      String answer1 = input.nextLine();

      if (answer1.equals("y")) {
        addNewMoon(star);
      } else if (answer1.equals("n")) {
        addMoons = false;
        break;
      } else {
        System.out.println("Invalid choice. Enter 'y' or 'n'.");
      }
    }
  }

  /**
   * add new planet to a solar system.
   */

  private void addNewPlanet(Star star) {
    System.out.println("Enter the name of planet: ");
    String planetName;
    planetName = input.nextLine();

    System.out.println("Enter the radius of the planet: ");
    int planetRadius;
    planetRadius = input.nextInt();
    input.nextLine();

    System.out.println("Enter the orbitradius of the planet: ");
    int orbitRadius;
    orbitRadius = input.nextInt();
    input.nextLine();
    Planet planet = new Planet(planetName, planetRadius, orbitRadius);
    star.addPlanet(planet);
  }

  /**
   * add new moon to a solar system.
   */

  private void addNewMoon(Star star) {
    if (star.getPlanets().isEmpty()) {
      System.out.println("No planets available to add a moon");
      return;
    }

    System.out.println("Select planet to add a moon: ");
    List<Planet> planets = star.getPlanets();

    for (int i = 0; i < planets.size(); i++) {
      System.out.println((i + 1) + ". " + planets.get(i).getName());
    }

    int choice = input.nextInt();
    input.nextLine();

    if (choice < 1 || choice > planets.size()) {
      System.out.println("Invalid choice. Please try again.");
      return;
    }

    Planet selectedPlanet = planets.get(choice - 1);
    System.out.println("Enter the name of moon: ");
    String moonName;
    moonName = input.nextLine();
    System.out.println("Enter the radius of the moon: ");
    int moonRadius;
    moonRadius = input.nextInt();
    System.out.println("Enter the orbit radius of the moon: ");
    int moonOrbitRadius;
    moonOrbitRadius = input.nextInt();
    Moon moon = new Moon(moonName, moonRadius, moonOrbitRadius);
    selectedPlanet.addMoon(moon);
  }

  /**
   * list all solar systems.
   */

  private void listSolarSystems() {

    if (stars.isEmpty()) {
      System.out.println("No solar system created yet");
    } else {
      System.out.println("Solar system: ");
      for (Star s : stars) {
        System.out.println("Star: " + s.getName() + "Radius: " + s.getRadius());
        List<Planet> planets = s.getPlanets();

        for (Planet p : planets) {
          System.out.println("Planet: " + p.getName() + "Radius: " + p.getRadius());
          List<Moon> moons = p.getMoons();

          for (Moon m : moons) {
            System.out.println("Moon: " + m.getName() + "Radius: " + m.getRadius());
          }
        }
      }
    }
  }

  /**
   * display solar systems.
   */

  private void displaySolarSystem() {
    if (stars.isEmpty()) {
      System.out.println("No solar systems created yet.");
      return;
    }

    System.out.println("Select a solar system:");
    for (int i = 0; i < stars.size(); i++) {
      System.out.println((i + 1) + ". " + stars.get(i).getName());
    }

    int choice = input.nextInt();
    if (choice < 1 || choice > stars.size()) {
      System.out.println("Invalid choice.");
      return;
    }

    Star selectedStar = stars.get(choice - 1);
    System.out.println("Solar System: " + selectedStar.getName());

    List<Planet> sortedPlanetsBySize = selectedStar.getPlanetsSortedBySize();
    for (Planet planet : sortedPlanetsBySize) {
      System.out.println("- Planet: " + planet.getName() + ", Radius: " + planet.getRadius());
      List<Moon> sortedMoonsBySize = planet.getMoonsSortedBySize();
      for (Moon moon : sortedMoonsBySize) {
        System.out.println("\t- Moon: " + moon.getName() + ", Radius: " + moon.getRadius());
      }
    }

    List<Planet> sortedPlanetsByOrbitalRadius = selectedStar.getPlanetsSortedByOrbitalRadius();
    for (Planet planet : sortedPlanetsByOrbitalRadius) {
      System.out.println("- Planet: " + planet.getName() + ", Orbital Radius: " + planet.getOrbitRadius());
      List<Moon> sortedMoonsByOrbitalRadius = planet.getMoonsSortedByOrbitalRadius();
      for (Moon moon : sortedMoonsByOrbitalRadius) {
        System.out.println("\t- Moon: " + moon.getName() + ", Orbital Radius: " + moon.getOrbitRadius());
      }
    }

    System.out.println("Select a member to perform actions (planet/moon index or 0 to go back):");
    int memberChoice = input.nextInt();
    input.nextLine();
    if (memberChoice == 0) {
      return;
    } else if (memberChoice <= sortedPlanetsBySize.size()) {
      Planet selectedPlanet = sortedPlanetsBySize.get(memberChoice - 1);
      deletePlanet(selectedStar, selectedPlanet);
    } else {
      List<Moon> sortedMoonsBySize = selectedStar.getMoonsSortedBySize();
      int moonIndex = memberChoice - 1 - sortedPlanetsBySize.size();
      if (moonIndex >= 0 && moonIndex < sortedMoonsBySize.size()) {
        Moon selectedMoon = sortedMoonsBySize.get(moonIndex);
        deleteMoon(selectedStar, selectedMoon);
      } else {
        System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  /**
   * remove moon from solar system.
   */

  private void deleteMoon(Star selectedStar, Moon selectedMoon) {
    for (Planet planet : selectedStar.getPlanets()) {
      if (planet.getMoons().contains(selectedMoon)) {
        planet.removeMoon(selectedMoon);
        System.out.println("Moon removed from solar system");
        return;
      }
    }
    System.out.println("Moon not found in solar system");
  }

  /**
   * remove planet + moons from solar system.
   */

  private void deletePlanet(Star selectedStar, Planet selectedPlanet) {
    selectedStar.removePlanet(selectedPlanet);
    System.out.println("Planet removed from solar system");

    List<Moon> moonRemove = selectedPlanet.getMoons();
    for (Moon moon : moonRemove) {
      selectedStar.removeMoon(selectedPlanet, moon);
    }
    System.out.println("Moon removed from solar system");
  }

  /**
   * add a solar sysstem.
   */

  private void addSolarSystem() {
    System.out.println("Enter the name of the star: ");
    String starName;
    starName = input.nextLine();

    System.out.println("Enter the radius of the star: ");
    int starRadius;
    starRadius = input.nextInt();
    input.nextLine();

    Star star = new Star(starName, starRadius);
    stars.add(star);
    System.out.println("Star added to solar system");

    boolean addPlanets = true;

    while (addPlanets) {
      System.out.println("Would you like to add a planet to the solar system? (y/n)");
      String answer = input.nextLine();
      if (answer.equalsIgnoreCase("y")) {
        addPlanetToSolarSystem(star);
      } else if (answer.equalsIgnoreCase("n")) {
        addPlanets = false;
        break;
      } else {
        System.out.println("Invalid choice. Please try again.");
      }
    }

    boolean addMoons = true;

    while (addMoons) {
      System.out.println("Would you like to add a moon to the solar system? (y/n)");
      String answer = input.nextLine();
      if (answer.equalsIgnoreCase("y")) {
        addMoonToSolarSystem(star);
      } else if (answer.equalsIgnoreCase("n")) {
        addMoons = false;
        break;
      } else {
        System.out.println("Invalid choice. Please try again.");
      }

    }
  }

  /**
   * add planet.
   */

  private void addPlanetToSolarSystem(Star star) {
    System.out.println("Enter the name of the planet: ");
    String planetName;
    planetName = input.nextLine();
    System.out.println("Enter the radius of the planet: ");
    int planetRadius;
    planetRadius = input.nextInt();
    input.nextLine();

    System.out.println("Enter the orbitradius of the planet: ");
    int orbitRadius;
    orbitRadius = input.nextInt();
    input.nextLine();

    Planet planet = new Planet(planetName, planetRadius, orbitRadius);
    star.addPlanet(planet);
    System.out.println("Planet added to solar system");
  }

  /**
   * add moons.
   */

  private void addMoonToSolarSystem(Star star) {
    List<Planet> planets = star.getPlanets();
    if (planets.isEmpty()) {
      System.out.println("No planets available to add a moon");
      return;
    }

    System.out.println("Select planet to add a moon: ");
    for (int i = 0; i < planets.size(); i++) {
      System.out.println((i + 1) + ". " + planets.get(i).getName());
    }

    int choice = input.nextInt();
    input.nextLine();
    if (choice < 1 || choice > planets.size()) {
      System.out.println("Invalid choice. Please try again.");
      return;
    }

    Planet selectedPlanet = planets.get(choice - 1);
    System.out.println("Enter the name of moon: ");
    String moonName;
    moonName = input.nextLine();
    System.out.println("Enter the radius of the moon: ");
    int moonRadius;
    moonRadius = input.nextInt();
    System.out.println("Enter the orbit radius of the moon: ");
    int moonOrbitRadius;
    moonOrbitRadius = input.nextInt();
    Moon moon = new Moon(moonName, moonRadius, moonOrbitRadius);
    selectedPlanet.addMoon(moon);
    System.out.println("Moon added to solar system");

  }

  /**
   * delete a solar system.
   */

  private void deleteMemberFromSolarSystem() {
    if (stars.isEmpty()) {
      System.out.println("No solar system created yet.");
      return;
    }

    System.out.println("Select a solar system to delete a member: ");

    for (int i = 0; i < stars.size(); i++) {
      System.out.println((i + 1) + " . " + stars.get(i).getName());
    }

    int choice = input.nextInt();
    input.nextLine();

    if (choice < 1 || choice > stars.size()) {
      System.out.print("Invalid choice.");
      return;
    }

    Star selectedStar = stars.get(choice - 1);
    System.out.println("Select the member type to delete (1. Star, 2. Planet, 3. Moon): ");
    int memberTypeChoice = input.nextInt();
    input.nextLine();

    switch (memberTypeChoice) {
      case 1:
        stars.remove(selectedStar);
        System.out.println("Star deleted from the solar system.");
        break;

      case 2:
        deletePlanetFromStar(selectedStar);
        break;

      case 3:
        deleteMoonFromPlanet(selectedStar.getPlanets());
        break;

      default:
        System.out.println("Invalid choice.");
        break;
    }
  }

  /**
   * delete a planet from a star.
   */

  private void deletePlanetFromStar(Star star) {
    if (star.getPlanets().isEmpty()) {
      System.out.println("No planets available to delete.");
      return;
    }

    System.out.println("Select the planet to delete: ");
    List<Planet> planets = star.getPlanets();
    for (int i = 0; i < planets.size(); i++) {
      System.out.println((i + 1) + " . " + planets.get(i).getName());
    }

    int choice = input.nextInt();
    input.nextLine();

    if (choice < 1 || choice > planets.size()) {
      System.out.print("Invalid choice. ");
      return;
    }

    Planet deletedPlanet = planets.remove(choice - 1);
    System.out.println("Planet " + deletedPlanet.getName() + " deleted from the solar system");
  }

  private void deleteMoonFromPlanet(List<Planet> planets) {
    if (planets.isEmpty()) {
      System.out.println("No planets available to delete moons from.");
      return;
    }

    System.out.println("Select the planet to delete a moon from: ");
    for (int i = 0; i < planets.size(); i++) {
      System.out.println((i + 1) + " . " + planets.get(i).getName());
    }

    int choice = input.nextInt();
    input.nextLine();

    if (choice < 1 || choice > planets.size()) {
      System.out.println("Invalid choice.");
      return;
    }

    Planet selectedPlanet = planets.get(choice - 1);
    List<Moon> moons = selectedPlanet.getMoons();

    if (moons.isEmpty()) {
      System.out.println("No moons available to delete.");
      return;
    }

    System.out.println("Select the moon to delete: ");
    for (int i = 0; i < moons.size(); i++) {
      System.out.println((i + 1) + " . " + moons.get(i).getName());

    }

    choice = input.nextInt();
    input.nextLine();

    if (choice < 1 || choice > moons.size()) {
      System.out.println("Invaild choice.");
      return;
    }

    Moon deletedMoon = moons.remove(choice - 1);
    System.out.println("Moon " + deletedMoon.getName() + " deleted from solar system.");
  }

  /**
   * The App starting point.
   */

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    App theApp = new App(scanner);

    theApp.loadRegistryFromFile("Solarsystem.data");

    theApp.menu();

    theApp.saveRegistryToFile("Solarsystem.data");
    scanner.close();
  }
}
