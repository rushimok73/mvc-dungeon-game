package control.commands;

import control.DungeonCommand;
import dungeon.Dungeon;

import java.io.IOException;
import java.util.Scanner;

/**
 * Class which implements the PickUp command in Command Design Pattern.
 */
public class PickUp implements DungeonCommand {
  @Override
  public void execute(Dungeon m, Appendable ap, Scanner scan) throws IOException {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }

    try {
      m.pickupItem();
    } catch (IllegalArgumentException e) {
      ap.append("Invalid item\n");
    }

  }
}