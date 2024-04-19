package dungeon;

import java.util.HashMap;
import java.util.Map;

/**
 * Interface representing a player inside the dungeon.
 * A player can move through the dungeon and collect treasure.
 * A player can only move if the cave permits movement in that direction.
 */
final class PlayerImpl implements Player {
  private final String name;
  private final HashMap<Treasure, Integer> bag;

  /**
   * Constructor for player.
   * @param name name of the player.
   */
  PlayerImpl(String name) {
    this.name = name;
    this.bag = new HashMap<>();
    this.bag.put(Treasure.ARROWS, 3);
    this.bag.put(Treasure.SAPPHIRES, 0);
    this.bag.put(Treasure.RUBIES, 0);
    this.bag.put(Treasure.DIAMONDS, 0);
  }

  /**
   * String representation of player and his treasures.
   * @return String representation of player and his treasures.
   */
  @Override
  public String toString() {
    return String.format("%s %s", this.name, this.bag.toString());
  }

  void addTreasure(Treasure t) {
    if (t == Treasure.DIAMONDS) {
      this.bag.put(Treasure.DIAMONDS, this.bag.get(Treasure.DIAMONDS) + 1);
    } else if (t == Treasure.RUBIES) {
      this.bag.put(Treasure.RUBIES, this.bag.get(Treasure.RUBIES) + 1);
    } else if (t == Treasure.SAPPHIRES) {
      this.bag.put(Treasure.SAPPHIRES, this.bag.get(Treasure.SAPPHIRES) + 1);
    } else if (t == Treasure.ARROWS) {
      this.bag.put(Treasure.ARROWS, this.bag.get(Treasure.ARROWS) + 1);
    }
  }

  void decrementArrow() {
    this.bag.put(Treasure.ARROWS, Math.max(this.bag.get(Treasure.ARROWS) - 1,0));
  }

  void emptyBag() {
    this.bag.put(Treasure.DIAMONDS, 0);
    this.bag.put(Treasure.RUBIES, 0);
    this.bag.put(Treasure.SAPPHIRES, 0);
  }



  @Override
  public Map<Treasure, Integer> getBag() {
    Map<Treasure, Integer> deepCopy = new HashMap<>();
    for (Map.Entry<Treasure, Integer> entry : this.bag.entrySet()) {
      Treasure key = entry.getKey();
      Integer value = entry.getValue();
      deepCopy.put(key, value);
    }
    return deepCopy;
  }
}
