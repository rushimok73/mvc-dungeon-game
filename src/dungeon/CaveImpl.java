package dungeon;

import java.util.HashMap;
import java.util.Map;

/**
 * Class which implements the cave interface.
 */
final class CaveImpl implements Cave {
  private final Movement possibleMoves;
  private final Map<Treasure, Integer> bag;
  private Monster otyugh;
  private Monster thief;
  private int smell;
  private boolean crack;
  private boolean visited;
  private boolean pit;

  /**
   * Creates new cave instance, with all treasures as 0 initially,
   * and all possible movements as false.
   */
  CaveImpl() {
    this.possibleMoves = new Movement();
    this.bag = new HashMap<>();
    this.bag.put(Treasure.SAPPHIRES, 0);
    this.bag.put(Treasure.RUBIES, 0);
    this.bag.put(Treasure.DIAMONDS, 0);
    this.bag.put(Treasure.ARROWS, 0);
    this.otyugh = null;
    this.thief = null;
    this.smell = 0;
    this.visited = false;
    this.pit = false;
    this.crack = false;
  }

  /**
   * Copy Constructor.
   */
  CaveImpl(CaveImpl c) {
    this.possibleMoves = c.getPossibleMoves();
    this.bag = c.getBag();
    this.otyugh = c.getOtyugh();
    this.thief = c.getThief();
    this.smell = c.getSmellInt();
    this.visited = c.getVisited();
    this.pit = c.getPit();
    this.crack = c.getCrack();
  }

  /**
   * Adds treasures to the cave bag.
   *
   * @param diamonds  number of diamonds to be added.
   * @param rubies    number of rubies to be added.
   * @param sapphires number of sapphires to be added.
   */
  void addTreasure(int diamonds, int rubies, int sapphires, int arrows) {
    this.bag.put(Treasure.DIAMONDS, this.bag.get(Treasure.DIAMONDS) + diamonds);
    this.bag.put(Treasure.RUBIES, this.bag.get(Treasure.RUBIES) + rubies);
    this.bag.put(Treasure.SAPPHIRES, this.bag.get(Treasure.SAPPHIRES) + sapphires);
    this.bag.put(Treasure.ARROWS, this.bag.get(Treasure.ARROWS) + arrows);
  }

  int getSmellInt() {
    return this.smell;
  }

  void zeroSmell() {
    this.smell = 0;
  }

  void addOtyughs() {
    this.otyugh = new Otyugh();
  }

  void addPits() {
    this.pit = true;
  }

  void addThieves() {
    this.thief = new Thief();
  }

  Monster getThief() {
    return this.thief;
  }

  Monster getOtyugh() {
    return this.otyugh;
  }

  boolean getPit() {
    return this.pit;
  }

  void updateSmell(int s) {
    this.smell += s;
  }

  @Override
  public SmellEnum getSmell() {
    if (this.smell == 0) {
      return SmellEnum.NO_SMELL;
    } else if (this.smell == 1) {
      return SmellEnum.LESS_PUNGENT;
    } else {
      return SmellEnum.MORE_PUNGENT;
    }
  }

  void removeOtyugh() {
    otyugh = null;
  }

  void removeThief() {
    thief = null;
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

  void pickupItem(Treasure t) {
    if (t == Treasure.DIAMONDS) {
      this.bag.put(Treasure.DIAMONDS, this.bag.get(Treasure.DIAMONDS) - 1);
    } else if (t == Treasure.RUBIES) {
      this.bag.put(Treasure.RUBIES, this.bag.get(Treasure.RUBIES) - 1);
    } else if (t == Treasure.SAPPHIRES) {
      this.bag.put(Treasure.SAPPHIRES, this.bag.get(Treasure.SAPPHIRES) - 1);
    } else {
      this.bag.put(Treasure.ARROWS, this.bag.get(Treasure.ARROWS) - 1);
    }
  }

  @Override
  public Movement getPossibleMoves() {
    return this.possibleMoves;
  }

  @Override
  public boolean hasOtyugh() {
    return this.otyugh != null;
  }

  @Override
  public boolean hasThief() {
    return this.thief != null;
  }

  @Override
  public boolean hasPit() {
    return this.pit;
  }

  void setVisited() {
    this.visited = true;
  }

  void updateCrack() {
    this.crack = true;
  }

  @Override
  public boolean getCrack() {
    return this.crack;
  }

  @Override
  public boolean getVisited() {
    return this.visited;
  }

  @Override
  public String toString() {
    if (this.hasOtyugh()) {
      return "Cave with otyugh. " + this.otyugh.getDescription();
    } else if (this.hasThief()) {
      return "Cave with thief. " + this.thief.getDescription();
    } else {
      return "Cave with no monster. ";
    }
  }
}
