package dungeon;

import java.util.Map;

/**
 * Interface representing a player inside the dungeon.
 * A player can move through the dungeon and collect treasure.
 * A player can only move if the cave permits movement in that direction.
 */
public interface Player {

  /**
   * Gives the collected treasure with the player.
   * @return Gives the collected treasure.
   */
  Map<Treasure, Integer> getBag();

}
