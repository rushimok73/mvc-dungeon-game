package control;

import dungeon.Dungeon;

import java.io.IOException;
import java.util.Scanner;

/**
 * Interface for the dungeon command controller.
 * Implements the command pattern design.
 * Has commands of move, pickUp, Shoot and Quit.
 */
public interface DungeonCommand {

  /**
   * Executes commands using polymorphism.D
   * @param d Model.
   * @param ap Appendable Object.
   * @param scan Scanner Object.
   * @throws IOException When invalid inputs or null are entered.
   */
  void execute(Dungeon d, Appendable ap, Scanner scan) throws IOException;
}
