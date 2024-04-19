package test;

import dungeon.Cave;
import dungeon.Direction;
import dungeon.Dungeon;
import dungeon.GameState;
import dungeon.Player;

import java.util.List;


/**
 * Mock class for testing the inputs from Dungeon Controller.
 */
final class MockDungeon implements Dungeon {
  private final StringBuilder sb;

  public MockDungeon(StringBuilder sb) {
    this.sb = sb;
  }

  @Override
  public GameState getGameState() {
    sb.append("Gamestate called").append("\n");
    return GameState.PLAYING;
  }

  @Override
  public Player getPlayer() {
    return null;
  }

  @Override
  public int getPlayerX() {
    return 0;
  }

  @Override
  public int getEndX() {
    return 0;
  }

  @Override
  public int getEndY() {
    return 0;
  }

  @Override
  public int getPlayerY() {
    return 0;
  }

  @Override
  public void move(Direction direction) {
    sb.append("Input to move = ").append(direction.toString()).append("\n");
  }

  @Override
  public List<List<Cave>> getAllCaves() {
    return null;
  }

  @Override
  public void shootArrow(Direction direction, int distance) {
    sb.append("Input to shootArrow = ").append(direction.toString()).append(",")
            .append(distance).append("\n");
  }

  @Override
  public String getStateDescription() {
    sb.append("getStateDescription called").append("\n");
    return null;
  }

  @Override
  public List<String> getStats() {
    sb.append("get stats called").append("\n");
    return null;
  }

  @Override
  public List<Integer> getRandomArgs() {
    sb.append("random args called").append("\n");
    return null;
  }

  @Override
  public StringBuilder getWhatHappened() {
    sb.append("Called get what happened").append("\n");
    return null;
  }


  @Override
  public void pickupItem() {
    sb.append("Called pickup").append("\n");
  }

  @Override
  public void moveCoordinates(int row, int column) {
    sb.append("Input to moveCoordinates = ").append(row).append(",")
            .append(column).append("\n");
  }
}
