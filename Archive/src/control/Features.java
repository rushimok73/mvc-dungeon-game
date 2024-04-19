package control;

import dungeon.Direction;

import java.util.List;
/**
 * This interface represents a set of features that the program offers. Each
 * feature is exposed as a function in this interface. This function is used
 * suitably as a callback by the view, to pass control to the controller. How
 * the view uses them as callbacks is completely up to how the view is designed
 * (e.g. it could use them as a callback for a button, or a callback for a
 * dialog box, or a set of text inputs, etc.)
 *
 * <p>Each function is designed to take in the necessary data to complete that
 * functionality.
 */

public interface Features extends DungeonController {

  /**
   * Makes the controller move the player via the model.
   * @param d Direction to move in.
   */
  void movePlayer(Direction d);

  /**
   * Makes the controller shoot the shoot via the model.
   * @param d Direction to move in.
   * @param distance Distance to shoot in.
   */
  void shootArrow(Direction d, Integer distance);

  /**
   * Makes the controller pick up items via the model.
   */
  void pickUp();

  /**
   * Handles click event via the view.
   * @param row row where click happens.
   * @param column column where click happens.
   */
  void handleCellClick(int row , int column);

  /**
   * Creates a new dungeon via the controller.
   * @param type wrapping/ non-wrapping.
   * @param row number of rows.
   * @param column number of columns.
   * @param interconnectivity interconnectivity.
   * @param treasure treasure percentage.
   * @param name name of player.
   * @param otyughs number of otyughs.
   */
  void createDungeon(String type, int row, int column, int interconnectivity, int treasure,
                     String name, int otyughs);

  /**
   * Creates a new dungeon via the controller for replay.
   * @param type wrapping/ non-wrapping.
   * @param row number of rows.
   * @param column number of columns.
   * @param interconnectivity interconnectivity.
   * @param treasure treasure percentage.
   * @param name name of player.
   * @param otyughs number of otyughs.
   * @param randomArgs replay args of games.
   */
  void refreshDungeon(String type, int row, int column, int interconnectivity, int treasure,
                             String name, int otyughs, List<Integer> randomArgs);
}
