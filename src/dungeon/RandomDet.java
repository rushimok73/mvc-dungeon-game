package dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class which generates predetermined random values for testing.
 */
public final class RandomDet implements RandomInterface {
  private final List<Integer> args;
  private int count;
  private Random random;

  /**
   * Constructor for RandomDet.
   * @param varargs variadic arguments which nextInt will return.
   */
  public RandomDet(Integer... varargs) {
    this.count = 0;
    this.random = new Random();
    this.args = new ArrayList<>();
    if (varargs != null) {
      for (int i : varargs) {
        args.add(i);
      }
    }
  }

  @Override
  public int nextInt(int i) {
    if (count >= args.size()) {
      return random.nextInt(i);
    }
    int temp = count;
    count++;
    return this.args.get(temp);
  }
}
