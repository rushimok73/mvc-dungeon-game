package dungeon;

import java.util.Random;

/**
 * Returns real random values for to be used in the driver and in the real implementation.
 */
public final class RandomTrue implements RandomInterface {

  private final Random r;

  /**
   * Constructor for real values of random interface.
   */
  public RandomTrue() {
    r = new Random();
  }

  @Override
  public int nextInt(int n) {
    return r.nextInt(n);
  }
}
