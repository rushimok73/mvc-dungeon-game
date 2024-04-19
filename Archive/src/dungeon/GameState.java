package dungeon;

/**
 * Enum indicating the state of game. Default = PLAYING, switches to OVER when player reaches end.
 */
public enum GameState {
  PLAYING("You keep questing"),
  OVER("You win, Congrats!"),
  DEAD("You died");

  private String s;

  GameState(String s) {
    this.s = s;
  }

  @Override
  public String toString() {
    return s;
  }
}
