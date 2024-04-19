package dungeon;

/**
 * Class indicating possible movements inside a cave.
 * Possible directions to go in are north, south, east,west.
 */
public final class Movement {
  private boolean north;
  private boolean south;
  private boolean west;
  private boolean east;

  /**
   * Constructor for movement class.
   */
  Movement() {
    this.north = false;
    this.south = false;
    this.west = false;
    this.east = false;
  }

  /**
   * Setter for west.
   * @param west value to be set.
   */
  void setWest(boolean west) {
    if (west) {
      this.west = true;
    }
  }

  /**
   * Setter for east.
   * @param east value to be set.
   */
  void setEast(boolean east) {
    if (east) {
      this.east = true;
    }
  }

  /**
   * Setter for north.
   * @param north value to be set.
   */
  void setNorth(boolean north) {
    if (north) {
      this.north = true;
    }
  }

  /**
   * Setter for south.
   * @param south value to be set.
   */
  void setSouth(boolean south) {
    if (south) {
      this.south = true;
    }
  }


  /**
   * Indicates if west direction is possible to move in.
   * @return boolean representing possibility.
   */
  public boolean getWest() {
    return this.west;
  }

  /**
   * Indicates if east direction is possible to move in.
   * @return boolean representing possibility.
   */
  public boolean getEast() {
    return this.east;
  }

  /**
   * Indicates if north direction is possible to move in.
   * @return boolean representing possibility.
   */
  public boolean getNorth() {
    return this.north;
  }

  /**
   * Indicates if south direction is possible to move in.
   * @return boolean representing possibility.
   */
  public boolean getSouth() {
    return this.south;
  }

  /**
   * Freedom is the total number of possible movements for a cave. 2 = Tunnel | 1,3,4 = Cave.
   * @return freedom in int.
   */
  public int getFreedom() {
    int f = 0;
    if (this.north) {
      f++;
    }
    if (this.south) {
      f++;
    }
    if (this.west) {
      f++;
    }
    if (this.east) {
      f++;
    }
    return f;
  }

  /**
   * String representation of possible movements.
   * @return String representation of possible movements.
   */
  @Override
  public String toString() {
    return String.format("(up-%s down-%s left-%s right-%s)",
            this.north, this.south, this.west, this.east);
  }
}
