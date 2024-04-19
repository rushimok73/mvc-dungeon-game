package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import dungeon.Cave;
import dungeon.Direction;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.DungeonType;
import dungeon.GameState;
import dungeon.Player;
import dungeon.RandomDet;
import dungeon.RandomInterface;
import dungeon.Treasure;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Testing class for the functionality of Arrow in the dungeon game.
 */
public class ArrowTest {
  RandomInterface random1;
  private Dungeon d1; //Non Wrapping 0 Interconnectivity
  RandomInterface random3;
  private Dungeon d3; //Non Wrapping 0 Interconnectivity

  @Before
  public void setUp() {
    random1 = new RandomDet(110, 80, 66, 59, 131, 26, 94, 126, 88, 151, 74, 145, 106,
            106, 69, 90, 58, 77, 82, 150, 4, 25, 91, 96, 96, 135, 76, 21, 70, 94, 7, 11, 130,
            116, 118, 97, 33, 67, 107, 2, 83, 41, 124, 83, 110, 36, 81, 37, 31, 73, 124, 0, 38,
            78, 106, 72, 111, 46, 90, 8, 103, 19, 23, 103, 66, 103, 108, 57, 42, 84, 52, 79, 71,
            50, 7, 83, 25, 29, 8, 89, 79, 70, 33, 11, 2, 2, 46, 78, 6, 57, 88, 77, 34, 66, 39, 81,
            62, 1, 20, 80, 65, 6, 2, 75, 29, 59, 33, 39, 17, 70, 29, 51, 1, 35, 61, 22, 3, 36, 23,
            53, 56, 22, 54, 52, 52, 32, 8, 44, 23, 6, 15, 47, 44, 26, 9, 24, 29, 8, 32, 40, 33, 0,
            31, 9, 35, 6, 24, 30, 15, 17, 12, 6, 10, 16, 10, 7, 7, 11, 1, 16, 9, 18, 13, 15, 10,
            1, 8, 2, 10, 10, 4, 7, 3, 5, 1, 0, 3, 1, 0, 0, 47, 24, 42, 2, 36, 2, 26, 1, 42, 2, 6,
            1, 27, 2, 37, 2, 48, 0, 7, 2, 46, 0, 29, 0, 12, 2, 5, 1, 42, 2, 35, 73, 65, 72, 61, 2,
            39, 53, 7, 50, 85, 35, 74, 83, 35, 13, 11, 63, 42, 32, 16, 75, 55, 20, 64, 19, 16, 20,
            50, 40, 36, 37, 12, 38
    );

    d1 = new DungeonImpl(DungeonType.NON_WRAPPING, 10, 10, 0,
            25, "Rushi", random1, 10);

    random3 = new RandomDet(28, 18, 45, 12, 35, 30, 30, 14, 8, 14, 3, 29, 0, 25, 1, 5, 17, 14,
            31, 22, 7, 11, 12, 4, 9, 17, 4, 4, 1, 15, 10, 8, 15, 14, 14, 5, 8, 12, 4, 3, 0, 5,
            7, 2, 2, 3, 2, 2, 0, 0, 1, 12, 16, 4, 16, 0, 13, 10, 12, 4, 0, 3, 0, 9, 2, 4, 1, 4,
            1, 4, 0, 13, 2, 12, 0, 4, 0, 6, 0, 21, 8, 15, 2, 5, 8, 9, 12, 11, 4, 8, 8, 10, 6, 6,
            1, 2);
    d3 = new DungeonImpl(DungeonType.WRAPPING, 5, 5, 9,
            50, "Rushi", random3, 2);
  }

  @Test
  public void testArrowTurnsInTunnel() {
    d3.move(Direction.EAST);
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.SOUTH);
    d3.move(Direction.SOUTH);
    d3.move(Direction.EAST);
    d3.shootArrow(Direction.SOUTH, 1);
    d3.shootArrow(Direction.SOUTH, 1);
    d3.move(Direction.SOUTH);
    d3.move(Direction.EAST);
    assertEquals(GameState.OVER, d3.getGameState());
  }

  @Test
  public void arrowDoesntTurnInCave() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);
    d3.shootArrow(Direction.EAST, 3);
    d3.shootArrow(Direction.EAST, 3);
    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);
    d3.move(Direction.NORTH);
    assertEquals(d3.getGameState(), GameState.DEAD);
  }

  @Test
  public void arrowCountGoDown() {
    Player p1 = d1.getPlayer();
    assertEquals(3, p1.getBag().get(Treasure.ARROWS), 0.001);
    d1.shootArrow(Direction.NORTH, 3);
    assertEquals(2, p1.getBag().get(Treasure.ARROWS), 0.001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void playerCantShootIf0Arrows() {
    d1.shootArrow(Direction.NORTH, 1);
    d1.shootArrow(Direction.NORTH, 1);
    d1.shootArrow(Direction.NORTH, 1);
    d1.shootArrow(Direction.NORTH, 1);
  }

  @Test
  public void arrowAndTreasureNotAlwaysTogether() {
    d3.move(Direction.EAST);
    assertEquals("\n"
                    + "\n"
                    + "You are in a cave\n"
                    + "Doors are :E,W,S,N,\n"
                    + "You can make out a faint smell in the distance..\n"
                    + "You currently have 3 arrows\n"
                    + "You found 0 diamonds, 1 rubies, 0 sapphires 1 arrows\n",
            d3.getStateDescription());
    d3.move(Direction.EAST);
    assertEquals("\n"
                    + "\n"
                    + "You are in a cave\n"
                    + "Doors are :W,S,N,\n"
                    + "You currently have 3 arrows\n"
                    + "You found 0 diamonds, 1 rubies, 0 sapphires 0 arrows\n",
            d3.getStateDescription());
  }

  @Test
  public void checkArrowPickUp() {
    d1.move(Direction.NORTH);
    d1.move(Direction.NORTH);
    assertEquals("\n"
                    + "\n"
                    + "You are in a tunnel\n"
                    + "Doors are :S,N,\n"
                    + "You currently have 3 arrows\n"
                    + "You found 0 diamonds, 0 rubies, 0 sapphires 1 arrows\n",
            d1.getStateDescription());
    d1.pickupItem();
    assertEquals("\n"
                    + "\n"
                    + "You are in a tunnel\n"
                    + "Doors are :S,N,\n"
                    + "You currently have 4 arrows\n"
                    + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n",
            d1.getStateDescription());
  }

  @Test(expected = IllegalArgumentException.class)
  public void IaeOnIfItemDoesntExist() {
    assertEquals("\n"
                    + "\n"
                    + "You are in a cave\n"
                    + "Doors are :N,\n"
                    + "You currently have 3 arrows\n"
                    + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n",
            d1.getStateDescription());
    d1.pickupItem();
  }

  @Test
  public void checkPlayerStartsWith3Arrows() {
    assertEquals("\n"
                    + "\n"
                    + "You are in a cave\n"
                    + "Doors are :N,\n"
                    + "You currently have 3 arrows\n"
                    + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n",
            d1.getStateDescription());
  }

  @Test
  public void testNumberOfArrowsInDungeon() {
    int arrows = 0;
    int caves = 0;
    for (List<Cave> lc : d1.getAllCaves()) {
      for (Cave c : lc) {
        arrows += c.getBag().get(Treasure.ARROWS);
        caves++;
      }
    }
    assertEquals(caves / 4, arrows);
  }

  @Test
  public void checkNumberOfOtyughs() {
    assertEquals(10, d1.toString().chars().filter(ch -> ch == 'O').count() + 1);
  }

  @Test
  public void checkNoOtyughAtStart() {
    assertEquals(d1.getGameState(), GameState.PLAYING);
  }

  @Test
  public void checkOtyughAtEnd() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);
    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 3 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n", d3.getStateDescription());
    d3.shootArrow(Direction.NORTH,1);
    d3.shootArrow(Direction.NORTH,1);
    d3.move(Direction.NORTH);
    assertEquals(GameState.OVER, d3.getGameState());

  }

  @Test
  public void checkFinalTreasure() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);
    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);
    d3.shootArrow(Direction.NORTH,1);
    d3.shootArrow(Direction.NORTH,1);
    d3.move(Direction.NORTH);
    assertEquals("\n"
                    + "\n"
                    + "You are in a cave\n"
                    + "Doors are :N,\n"
                    + "You currently have 3 arrows\n"
                    + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n",
            d1.getStateDescription());
  }

  @Test
  public void testOtyughDoesNotDieOn1AccurateShot() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);

    d3.shootArrow(Direction.SOUTH, 1);

    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 2 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 1 arrows\n", d3.getStateDescription());
  }

  @Test
  public void testOtyughDiesOn2AccurateShots() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);

    d3.shootArrow(Direction.SOUTH, 1);
    d3.shootArrow(Direction.SOUTH, 1);

    d3.move(Direction.SOUTH);

    assertEquals(GameState.PLAYING, d3.getGameState());

  }

  @Test
  public void testPlayerCanEscapeDeadOtyugh() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);

    d3.shootArrow(Direction.SOUTH, 1);
    d3.shootArrow(Direction.SOUTH, 1);

    d3.move(Direction.SOUTH);

    assertEquals(GameState.PLAYING, d3.getGameState());
  }


  @Test
  public void testOtyughDoesNotDieOnInaccurateShot() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);

    d3.shootArrow(Direction.SOUTH, 3);
    d3.shootArrow(Direction.SOUTH, 3);

    d3.move(Direction.SOUTH);

    assertEquals(GameState.DEAD, d3.getGameState());
  }

  @Test
  public void testSmell2CavesAway() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);
    d3.move(Direction.WEST);

    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,W,S,N,\n"
            + "You can make out a faint smell in the distance..\n"
            + "You currently have 3 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n", d3.getStateDescription());
  }

  @Test
  public void testSmell1CaveAway() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);
    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);

    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 3 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n", d3.getStateDescription());
  }

  @Test
  public void playerCanMoveAfterKillingOtyugh() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);

    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 1 arrows\n", d3.getStateDescription());
    d3.shootArrow(Direction.SOUTH , 1);
    d3.shootArrow(Direction.SOUTH , 1);
    d3.move(Direction.SOUTH);
    assertEquals(GameState.PLAYING, d3.getGameState());

  }

  @Test
  public void playerHas50PercentChanceOfSurvival() {
    List<GameState> results = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      random3 = new RandomDet(28, 18, 45, 12, 35, 30, 30, 14, 8, 14, 3, 29, 0, 25, 1, 5, 17, 14,
              31, 22, 7, 11, 12, 4, 9, 17, 4, 4, 1, 15, 10, 8, 15, 14, 14, 5, 8, 12, 4, 3, 0, 5,
              7, 2, 2, 3, 2, 2, 0, 0, 1, 12, 16, 4, 16, 0, 13, 10, 12, 4, 0, 3, 0, 9, 2, 4, 1, 4,
              1, 4, 0, 13, 2, 12, 0, 4, 0, 6, 0, 21, 8, 15, 2, 5, 8, 9, 12, 11, 4, 8, 8, 10, 6, 6,
              1, 2);
      d3 = new DungeonImpl(DungeonType.WRAPPING, 5, 5, 9,
              50, "Rushi", random3, 2);
      d3.move(Direction.EAST);
      d3.move(Direction.SOUTH);
      d3.move(Direction.WEST);
      d3.move(Direction.WEST);
      d3.move(Direction.NORTH);
      d3.shootArrow(Direction.NORTH, 1);
      d3.move(Direction.NORTH);
      results.add(d3.getGameState());
    }
    int play = 0;
    int dead = 0;
    for (GameState g : results) {
      if (g == GameState.OVER) {
        play++;
      } else {
        dead++;
      }
    }
    assertTrue(play >= 300 && play <= 800 && dead >= 300 && dead <= 800);
  }

  @Test
  public void testPlayerCanKillOtyugh() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);
    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);
    d3.shootArrow(Direction.NORTH, 1);
    d3.shootArrow(Direction.NORTH, 1);
    d3.move(Direction.NORTH);
    assertEquals(GameState.OVER, d3.getGameState());
  }

  @Test
  public void otyughKillsPlayer() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);
    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);
    d3.move(Direction.NORTH);
    assertEquals(GameState.DEAD, d3.getGameState());
  }


}
