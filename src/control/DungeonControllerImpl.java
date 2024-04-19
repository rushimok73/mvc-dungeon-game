package control;

import control.commands.Move;
import control.commands.PickUp;
import control.commands.Shoot;
import dungeon.Dungeon;
import dungeon.GameState;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Class implementation for the DungeonController Interface.
 * Implements the Command Design Pattern.
 */
public class DungeonControllerImpl implements DungeonController {
  private final Dungeon m;
  private final Readable rd;
  private final Appendable ap;
  private final Map<String, Function<Scanner, DungeonCommand>> knownCommands = new HashMap<>();

  /**
   * Constructor for the dungeon Controller.
   * @param m Model of the Dungeon.
   * @param rd Readable object.
   * @param ap Appendable object.
   * @throws IllegalArgumentException On any invalid or null input.
   */
  public DungeonControllerImpl(Dungeon m, Readable rd, Appendable ap)
          throws IllegalArgumentException {
    if (m == null || rd == null || ap == null) {
      throw new IllegalArgumentException("Inputs cant be null");
    }
    this.m = m;
    this.rd = rd;
    this.ap = ap;
    knownCommands.put("M", s -> new Move());
    knownCommands.put("S", s -> new Shoot());
    knownCommands.put("P", s -> new PickUp());
  }

  @Override
  public void playGame() {
    Scanner scan = new Scanner(rd);
    try {
      DungeonCommand c;
      while (m.getGameState() == GameState.PLAYING) {
        ap.append(m.getStateDescription());
        ap.append("Move, Pickup, Shoot or Quit (M-P-S-Q)?");
        String in = scan.next();
        if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
          ap.append("\nQuitting Game...\n");
          return;
        }

        Function<Scanner, DungeonCommand> cmd = knownCommands.getOrDefault(in, null);
        if (cmd == null) {
          ap.append("Invalid Choice\n");
        } else {
          c = cmd.apply(scan);
          c.execute(m, ap, scan);
          if (m.getGameState() != GameState.PLAYING) {
            ap.append(m.getStateDescription());
            return;
          }
        }
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }
}