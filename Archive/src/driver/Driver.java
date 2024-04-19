package driver;

import control.DungeonController;
import control.DungeonControllerImpl;
import control.MvcController;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.DungeonType;
import dungeon.RandomTrue;
import view.DungeonView;
import view.DungeonViewImpl;

import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Driver class to demonstrate working of the Dungeon project.
 */
public class Driver {
  /**
   * Main class.
   *
   * @param args {1. 0 or 1 for Non-Wrapping/Wrapping
   *             2. Number of rows
   *             3. Number of columns
   *             4. Interconnectivity 5
   *             5. Treasure Percent
   *             6.Player Name
   *             7.Number of otyughs}
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      Dungeon m = new DungeonImpl();
      DungeonView view = new DungeonViewImpl(m);
      DungeonController ctrl = new MvcController(m, view);
      ctrl.playGame();
    } else {
      try {
        DungeonType type;
        if (Objects.equals(args[0], "0")) {
          type = DungeonType.NON_WRAPPING;
        } else {
          type = DungeonType.WRAPPING;
        }
        Dungeon m = new DungeonImpl(type, Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                Integer.parseInt(args[3]), Integer.parseInt(args[4]), args[5], new RandomTrue(),
                Integer.parseInt(args[6]));

        Readable input = new InputStreamReader(System.in);
        Appendable output = System.out;
        DungeonController ctrl = new DungeonControllerImpl(m, input, output);
        ctrl.playGame();
      } catch (Exception e) {
        System.out.println("Invalid arguments");
      }
    }
  }
}

