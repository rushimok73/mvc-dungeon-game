package dungeon;

/**
 * Interface representing a dungeon in our game.
 * Dungeon contains a player, caves and tunnels.
 * Player can move through this dungeon from its start point to its end point.
 * Player can also collect treasures while moving through this dungeon.
 * Player can shoot arrows and kill monsters.
 * Player wins if he reaches the end without dying.
 */
public interface Dungeon extends ReadonlyDungeonModel {


  /**
   * Moves the player in the specified direction.
   * Player will pick up 1 ruby, 1 diamond and 1 sapphire if it lands on a cave with treasure.
   * @param direction Moves the player in the specified direction.
   */
  void move(Direction direction);

  /**
   * Shoots an arrow in the specified direction and distance.
   * @param direction Direction to be shot at.
   * @param distance Distance to be shot at.
   */
  void shootArrow(Direction direction, int distance);

  /**
   * Picks all items from current cave.
   */
  void pickupItem();

  /**
   * Moves player according to row and column.
   * @param row Adjacent row to move to.
   * @param column Adjacent column to move to.
   */
  void moveCoordinates(int row , int column);
}
