package control.commands;

import control.DungeonCommand;
import dungeon.Direction;
import dungeon.Dungeon;

import java.io.IOException;
import java.util.Scanner;


/**
 * Class which implements the Move command in Command Design Pattern.
 */
public class Move implements DungeonCommand {

  @Override
  public void execute(Dungeon m, Appendable ap, Scanner scan) throws IOException {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }

    ap.append("Enter Door to go in ");
    String dString = scan.next();
    try {
      switch (dString) {
        case "N":
          m.move(Direction.NORTH);
          break;
        case "S":
          m.move(Direction.SOUTH);
          break;
        case "E":
          m.move(Direction.EAST);
          break;
        case "W":
          m.move(Direction.WEST);
          break;
        default:
          ap.append("Invalid Input\n");
          break;
      }
    } catch (IllegalArgumentException e) {
      ap.append("Invalid move\n");
    }

  }
}
