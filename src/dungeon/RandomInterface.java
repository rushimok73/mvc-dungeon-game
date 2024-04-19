package dungeon;

/**
 * Interface to generate random values inside the project.
 */
public interface RandomInterface {
  /**
   * Bonded random value generator.
   * @param n bounded value.
   * @return random value within the bound.
   */
  int nextInt(int n);
}
