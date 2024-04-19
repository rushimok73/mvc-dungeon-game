package dungeon;

import java.util.List;



/**
 * Read-only dungeon model.
 * Interface representing a dungeon in our game.
 * Dungeon contains a player, caves and tunnels.
 * Player can move through this dungeon from its start point to its end point.
 * Player can also collect treasures while moving through this dungeon.
 * Player can shoot arrows and kill monsters.
 * Player wins if he reaches the end without dying.
 */

public interface ReadonlyDungeonModel {

  /**
   * Indicates the game state in the dungeon.
   * @return gamestate of current game.
   */
  GameState getGameState();

  /**
   * Returns the player of the game.
   * @return player of the game.
   */
  Player getPlayer();

  /**
   * Returns the x coordinate of the player in the dungeon.
   * @return the x coordinate of the player in the dungeon.
   */
  int getPlayerX();

  /**
   * Returns the x coordinate of the end in the dungeon.
   * @return the x coordinate of the end in the dungeon.
   */
  int getEndX();

  /**
   * Returns the y coordinate of the end in the dungeon.
   * @return the y coordinate of the end in the dungeon.
   */
  int getEndY();

  /**
   * Returns the y coordinate of the player in the dungeon.
   * @return the y coordinate of the player in the dungeon.
   */
  int getPlayerY();

  /**
   * Returns all the caves inside the dungeon.
   * @return Returns all the caves inside the dungeon.
   */
  List<List<Cave>> getAllCaves();

  /**
   * Returns the current state of the game.
   * @return The current state of the game.
   */
  String getStateDescription();

  /**
   * Returns the parameters used to create the dungeon.
   * @return List of all parameters.
   */
  List<String> getStats();

  /**
   * Returns the random values used to create the dungeon.
   * @return List of all random values.
   */
  List<Integer> getRandomArgs();

  /**
   * Returns what is happening in the model.
   * @return Status of model event.
   */
  StringBuilder getWhatHappened();
}
