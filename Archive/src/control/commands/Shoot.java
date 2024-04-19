package control.commands;

import control.DungeonCommand;
import dungeon.Direction;
import dungeon.Dungeon;

import java.io.IOException;
import java.util.Scanner;

/**
 * Class which implements the Shoot command in Command Design Pattern.
 */
public class Shoot implements DungeonCommand {

  @Override
  public void execute(Dungeon m, Appendable ap, Scanner scan) throws IOException {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }

    ap.append("\nEnter Direction to shoot in :");
    String dString = scan.next();
    ap.append("\nEnter distance to shoot in (1-5) :");
    int distance = scan.nextInt();
    try {
      switch (dString) {
        case "N":
          m.shootArrow(Direction.NORTH, distance);
          ap.append("\nYou shoot an arrow into the darkness...\n");
          break;
        case "S":
          m.shootArrow(Direction.SOUTH, distance);
          ap.append("\nYou shoot an arrow into the darkness...\n");
          break;
        case "E":
          m.shootArrow(Direction.EAST, distance);
          ap.append("\nYou shoot an arrow into the darkness...\n");
          break;
        case "W":
          m.shootArrow(Direction.WEST, distance);
          ap.append("\nYou shoot an arrow into the darkness...\n");
          break;
        default:
          ap.append("\nInvalid Input\n");
          break;
      }
    } catch (IllegalArgumentException e) {
      ap.append("\nInvalid arrow shoot\n");
    }

  }
}
