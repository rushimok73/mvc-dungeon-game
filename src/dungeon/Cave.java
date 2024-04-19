package dungeon;

import java.util.Map;

/**
 * This interface represents either a cave or a tunnel.
 * A dungeon is made up of caves and tunnels.
 * A cave with only 2 entrances is termed as a tunnel.
 * A cave with 1,3,4 entrances is termed as a cave.
 * Caves can hold treasures.
 */
public interface Cave {
  /**
   * Gives you treasure bag inside the cave.
   * @return Map containing treasures.
   */
  Map<Treasure, Integer> getBag();

  /**
   * Reports possible moves for a given cave.
   * @return Object containing possible moves for a given cave.
   */
  Movement getPossibleMoves();

  /**
   * Reports otyugh for a given cave.
   * @return boolean indicating presence.
   */
  boolean hasOtyugh();

  /**
   * Reports thief for a given cave.
   * @return boolean indicating presence.
   */
  boolean hasThief();

  /**
   * Reports pit for a given cave.
   * @return boolean indicating presence.
   */
  boolean hasPit();

  /**
   * Lets player know if he visited the cave.
   * @return boolean indicating visited status.
   */
  boolean getVisited();

  /**
   * Reports smell for the cave.
   * @return SmellEnum for the cave.
   */
  SmellEnum getSmell();

  /**
   * Reports crackd for a given cave.
   * @return boolean indicating presence.
   */
  boolean getCrack();
}
