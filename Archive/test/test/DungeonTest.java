package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dungeon.Cave;
import dungeon.Direction;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.DungeonType;
import dungeon.GameState;
import dungeon.RandomDet;
import dungeon.RandomInterface;
import dungeon.RandomTrue;
import dungeon.Treasure;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Test class for Dungeon Interface.
 */
public class DungeonTest {
  RandomInterface random1;
  RandomInterface random3;
  private Dungeon d3; //Non Wrapping 0 Interconnectivity

  @Before
  public void setUp() {

    random3 = new RandomDet(28, 18, 45, 12, 35, 30, 30, 14, 8, 14, 3, 29, 0, 25, 1, 5, 17, 14,
            31, 22, 7, 11, 12, 4, 9, 17, 4, 4, 1, 15, 10, 8, 15, 14, 14, 5, 8, 12, 4, 3, 0, 5,
            7, 2, 2, 3, 2, 2, 0, 0, 1, 12, 16, 4, 16, 0, 13, 10, 12, 4, 0, 3, 0, 9, 2, 4, 1, 4,
            1, 4, 0, 13, 2, 12, 0, 4, 0, 6, 0, 21, 8, 15, 2, 5, 8, 9, 12, 11, 4, 8, 8, 10, 6, 6,
            1, 2);
    d3 = new DungeonImpl(DungeonType.WRAPPING, 5, 5, 9,
            50, "Rushi", random3, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInput1() {
    d3 = new DungeonImpl(DungeonType.NON_WRAPPING, -10, 10, 0,
            25, "Rushi", random1,10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInput2() {
    d3 = new DungeonImpl(DungeonType.NON_WRAPPING, 10, -10, 0,
            25, "Rushi", random1,10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInput3() {
    d3 = new DungeonImpl(DungeonType.NON_WRAPPING, 10, 10, -1,
            25, "Rushi", random1,10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInput4() {
    d3 = new DungeonImpl(DungeonType.NON_WRAPPING, 10, 10, 0,
            25, "", random1,10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInput5() {
    d3 = new DungeonImpl(DungeonType.NON_WRAPPING, 10, 10, 0,
            25, "Rushi", null,10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInput6() {
    d3 = new DungeonImpl(DungeonType.NON_WRAPPING, 10, 10, 0,
            -25, "Rushi", random1,10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInput7() {
    d3 = new DungeonImpl(DungeonType.NON_WRAPPING, 3, 3, 100,
            25, "Rushi", new RandomTrue(),10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInput8() {
    d3 = new DungeonImpl(DungeonType.NON_WRAPPING, 3, 3, 9,
            25, "Rushi", new RandomTrue(),0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInput9() {
    d3 = new DungeonImpl(DungeonType.NON_WRAPPING, 5, 5, 9,
            25, "Rushi", new RandomTrue(),10);
  }

  @Test
  public void movePlayerOnLegalMove() {
    assertEquals(1, d3.getPlayerX());
    assertEquals(2, d3.getPlayerY());
    d3.move(Direction.EAST);
    assertEquals(1, d3.getPlayerX());
    assertEquals(3, d3.getPlayerY());
  }

  @Test(expected =  IllegalArgumentException.class)
  public void movePlayerOnIllegalMove() {
    d3.move(Direction.SOUTH);
  }


  @Test
  public void testPlayerWrapOnRows() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.EAST);
    d3.move(Direction.EAST);
    assertEquals(2, d3.getPlayerX());
    assertEquals(0, d3.getPlayerY());
  }

  @Test
  public void testPlayerWrapOnColumns() {
    d3.move(Direction.EAST);
    d3.move(Direction.NORTH);
    d3.move(Direction.NORTH);

    assertEquals(4, d3.getPlayerX());
    assertEquals(3, d3.getPlayerY());
  }

  @Test
  public void testTreasureCreation() {
    Dungeon[] dlist = {d3};
    for (Dungeon d : dlist) {
      List<Cave> realCaves = new ArrayList<>();
      for (List<Cave> lc : ((DungeonImpl)d).getAllCaves()) {
        for (Cave c : lc) {
          if (c.getPossibleMoves().getFreedom() != 2) {
            realCaves.add(c);
          }
        }
      }
      int count = 0;
      for (Cave c : realCaves) {
        Map<Treasure, Integer> hm = c.getBag();
        if ((hm.get(Treasure.DIAMONDS)
                + hm.get(Treasure.RUBIES)
                + hm.get(Treasure.SAPPHIRES)) > 0) {
          count++;
        }
      }
      assertTrue(count >= realCaves.size() / 4);
    }

  }

  @Test
  public void testTreasurePickUp() {
    d3.move(Direction.EAST);

    d3.pickupItem();
    assertEquals(1, d3.getPlayer().getBag().get(Treasure.RUBIES), 0.00);

  }

  @Test
  public void checkEveryNodeReachable() {
    int[] src = {d3.getPlayerX(), d3.getPlayerY()};
    boolean[] visited = bfs(src, 5, 5, d3.getAllCaves(),
            DungeonType.WRAPPING);
    for (boolean b : visited) {
      assertTrue(b);
    }
  }

  //mark
  @Test
  public void checkInterconnectivity() {
    Dungeon d = new DungeonImpl(DungeonType.NON_WRAPPING, 10, 10, 10,
            25, "Rushi", new RandomTrue(),10);
    int edges = 0;
    for (List<Cave> lc : d.getAllCaves()) {
      for (Cave c : lc) {
        if (c.getPossibleMoves().getSouth()) {
          edges++;
        }
        if (c.getPossibleMoves().getEast()) {
          edges++;
        }
      }
    }
    assertEquals(10, edges - 10 * 10 + 1);

  }

  @Test
  public void checkPathLengthGreaterThan5() {
    //BFS on source and destination, and storing all possible paths. Asserting that they all are
    //greater than 5.

    Dungeon d = new DungeonImpl(DungeonType.NON_WRAPPING, 10, 10, 10,
            25, "Rushi", new RandomTrue(),10);
    int[] src = {d.getPlayerX(), d.getPlayerY()};
    int[] dest = {d.getEndX(), d.getEndY()};
    List<Integer> pathLengths = bfs(src, dest, 10, 10, d.getAllCaves(), DungeonType.NON_WRAPPING);

    for (int length : pathLengths) {
      assertTrue(length >= 5);
    }


  }

  @Test
  public void checkTunnelsHaveNoTreasure() {
    List<List<Cave>> llc = ((DungeonImpl)d3).getAllCaves();
    for (List<Cave> lc : llc) {
      for (Cave c : lc) {
        if (c.getPossibleMoves().getFreedom() == 2) {
          Map<Treasure, Integer> bag = c.getBag();
          assertEquals(0, (int) bag.get(Treasure.DIAMONDS));
          assertEquals(0, (int) bag.get(Treasure.RUBIES));
          assertEquals(0, (int) bag.get(Treasure.SAPPHIRES));
        }
      }
    }
  }

  @Test
  public void checkPlayerStartAndEndIsCave() {
    for (int i = 0; i < 1000; i++) {
      Dungeon d = new DungeonImpl(DungeonType.NON_WRAPPING, 10, 10, 0,
              25, "Rushi", new RandomTrue(),10);
      assertTrue(d.getAllCaves().get(d.getPlayerX()).get(d.getPlayerY())
              .getPossibleMoves().getFreedom() != 2);
      assertTrue(d.getAllCaves().get(d.getEndX()).get(d.getEndY())
              .getPossibleMoves().getFreedom() != 2);
    }
  }

  @Test
  public void testDungeonSize() {
    assertEquals(5, ((DungeonImpl)d3).getAllCaves().size()); //rows
    assertEquals(5, ((DungeonImpl)d3).getAllCaves().get(0).size()); //columns
  }

  @Test
  public void checkTreasureTypes() {
    Cave c = ((DungeonImpl)d3).getAllCaves().get(0).get(3);
    assertEquals(4, c.getBag().size());
  }

  private List<Integer> bfs(int[] src, int[] dest, int rows, int columns, List<List<Cave>> lc,
                            DungeonType type) {

    int srcVertex = src[0] * columns + src[1];
    int destVertex = dest[0] * columns + dest[1];
    Queue<List<Integer>> queue = new LinkedList<>();
    List<Integer> pathLengths = new ArrayList<>();

    List<Integer> path = new ArrayList<>();
    path.add(srcVertex);
    queue.add(path);

    while (!queue.isEmpty()) {
      //Get path
      path = queue.poll();

      //Grab last node from it
      int last = path.get(path.size() - 1);


      if (last == destVertex) {
        pathLengths.add(path.size() - 1); //hops
      } else {
        //Grab all nodes connected to the last and put these in lastNode
        List<Integer> lastNode = new ArrayList<>();
        Cave c = lc.get(last / columns).get(last % columns);

        if (c.getPossibleMoves().getWest()) {
          if (type == DungeonType.WRAPPING && (last % columns) - 1 < 0) {
            lastNode.add(last + columns - 1);
          } else {
            lastNode.add(last - 1);
          }
        }

        if (c.getPossibleMoves().getEast()) {
          if (type == DungeonType.WRAPPING && (last + 1) % columns == 0) {
            lastNode.add(last - columns + 1);
          } else {
            lastNode.add(last + 1);
          }
        }

        if (c.getPossibleMoves().getNorth()) {
          if (type == DungeonType.WRAPPING && last - columns < 0) {
            lastNode.add(last + (rows - 1) * columns);
          } else {
            lastNode.add(last - columns);
          }
        }

        if (c.getPossibleMoves().getSouth()) {
          if (type == DungeonType.WRAPPING && last + columns >= columns * rows) {
            lastNode.add(last - (rows - 1) * columns);
          } else {
            lastNode.add(last + columns);
          }
        }

        //Check new paths created by new ends are legal to add in our queue.
        for (Integer integer : lastNode) {
          if (isNotVisited(integer, path)) {
            List<Integer> newpath = new ArrayList<>(path);
            newpath.add(integer);
            queue.add(newpath);
          }
        }
      }
    }
    return pathLengths;
  }

  private boolean[] bfs(int[] src, int rows, int columns, List<List<Cave>> lc,
                        DungeonType type) {
    boolean[] visited = new boolean[rows * columns];
    int srcVertex = src[0] * columns + src[1];
    Queue<Integer> queue = new LinkedList<>();
    queue.add(srcVertex);

    while (!queue.isEmpty()) {
      Integer last = queue.poll();
      Cave c = lc.get(last / columns).get(last % columns);

      if (c.getPossibleMoves().getWest()) {
        if (type == DungeonType.WRAPPING && (last % columns) - 1 < 0) {
          if (!visited[last + columns - 1]) {
            queue.add(last + columns - 1);
            visited[last + columns - 1] = true;
          }
        } else {
          if (!visited[last - 1]) {
            queue.add(last - 1);
            visited[last - 1] = true;
          }
        }
      }

      if (c.getPossibleMoves().getEast()) {
        if (type == DungeonType.WRAPPING && (last + 1) % columns == 0) {
          if (!visited[last - columns + 1]) {
            queue.add(last - columns + 1);
            visited[last - columns + 1] = true;
          }
        } else {
          if (!visited[last + 1]) {
            queue.add(last + 1);
            visited[last + 1] = true;
          }
        }
      }

      if (c.getPossibleMoves().getNorth()) {
        if (type == DungeonType.WRAPPING && last - columns < 0) {
          if (!visited[last + (rows - 1) * columns]) {
            queue.add(last + (rows - 1) * columns);
            visited[last + (rows - 1) * columns] = true;
          }
        } else {
          if (!visited[last - columns]) {
            queue.add(last - columns);
            visited[last - columns] = true;
          }
        }
      }

      if (c.getPossibleMoves().getSouth()) {
        if (type == DungeonType.WRAPPING && last + columns >= columns * rows) {
          if (!visited[last - (rows - 1) * columns]) {
            queue.add(last - (rows - 1) * columns);
            visited[last - (rows - 1) * columns] = true;
          }
        } else {
          if (!visited[last + columns]) {
            queue.add(last + columns);
            visited[last + columns] = true;
          }
        }
      }
    }

    return visited;
  }

  private boolean isNotVisited(int ele, List<Integer> p) {
    for (Integer integer : p) {
      if (integer == ele) {
        return false;
      }
    }
    return true;
  }

  @Test(expected = IllegalArgumentException.class)
  public void IaeAfterEndReachAkaGameOver() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);
    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);
    d3.move(Direction.NORTH);
    d3.move(Direction.NORTH);
  }

  @Test(expected = IllegalArgumentException.class)
  public void playerDoesntPickUpTreasureWhenThereIsNone() {
    d3.move(Direction.EAST);
    d3.move(Direction.EAST);
    d3.move(Direction.NORTH);
    d3.pickupItem();
  }

  @Test
  public void testToString() {
    assertEquals(" |   | |   \n"
            + " T-E-T T c \n"
            + "   |   | | \n"
            + " @-C P-C-C \n"
            + "   |   | | \n"
            + "-T-C-$-$-C-\n"
            + "   | | | | \n"
            + "-T c T O-c-\n"
            + " |   | | | \n"
            + "-c @-c-C-C-\n"
            + " |   | |   \n", d3.toString());
  }

  @Test
  public void testDescription() {
    assertEquals("\n"
                    + "\n"
                    + "You are in a cave\n"
                    + "Doors are :E,\n"
                    + "You currently have 3 arrows\n"
                    + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n",
            d3.getStateDescription());
  }

  @Test
  public void testPlayerWins() {
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
  public void testPlayerLoses() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);
    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);
    d3.move(Direction.NORTH);
    assertEquals(GameState.DEAD, d3.getGameState());
  }

  @Test
  public void playerDiesWhenPit() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);
    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);
    d3.move(Direction.WEST);
    assertEquals(GameState.DEAD, d3.getGameState());
  }

  @Test
  public void playerLosesInventoryWhenTHief() {
    d3.move(Direction.EAST);
    d3.pickupItem();
    assertEquals(1,d3.getPlayer().getBag().get(Treasure.RUBIES), 0.0);

    d3.move(Direction.SOUTH);
    assertEquals(0,d3.getPlayer().getBag().get(Treasure.RUBIES), 0.0);
  }

  @Test
  public void playerDoestLoseArrowToThief() {
    d3.move(Direction.EAST);
    assertEquals(3,d3.getPlayer().getBag().get(Treasure.ARROWS), 0.0);

    d3.move(Direction.SOUTH);
    assertEquals(3,d3.getPlayer().getBag().get(Treasure.ARROWS), 0.0);
  }

  @Test
  public void crackNearPit() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);
    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);
    assertTrue(d3.getAllCaves().get(d3.getPlayerX()).get(d3.getPlayerY()).getCrack());
  }

  @Test
  public void checkNumberOfPits() {
    assertEquals(2, d3.toString().chars().filter(ch -> ch == '@').count());
  }

  @Test
  public void checkNumberOfThieves() {
    assertEquals(2, d3.toString().chars().filter(ch -> ch == '$').count());
  }

  @Test
  public void thiefDisappearAfterSteal() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    assertEquals("Your pockets feel lighter!",d3.getWhatHappened().toString());
    d3.move(Direction.WEST);
    d3.move(Direction.EAST);
    assertEquals("Dread fills you..",d3.getWhatHappened().toString());
  }

  @Test
  public void caveVisitedUpdateOnVisit() {
    d3.move(Direction.EAST);
    assertTrue(d3.getAllCaves().get(d3.getPlayerX()).get(d3.getPlayerY()).getVisited());
  }

  @Test
  public void caveVisitedNotUpdateOnNonVisit() {
    assertFalse(d3.getAllCaves().get(d3.getPlayerX()).get(d3.getPlayerY() + 1).getVisited());
  }

  @Test
  public void moveWithCoordinates() {
    int row = d3.getPlayerX();
    int col = d3.getPlayerY() + 1;
    d3.moveCoordinates(row, col);
    assertEquals(d3.getPlayerX(),row);
    assertEquals(d3.getPlayerY(),col);
  }

  @Test
  public void testGetStats() {
    assertEquals("[WRAPPING, 5, 5, 9, 50, Rushi, 2]", d3.getStats().toString());
  }

  @Test
  public void testCaveDescription() {
    assertEquals("Cave with otyugh. This monster will eat you up!",
            d3.getAllCaves().get(d3.getEndX()).get(d3.getEndY()).toString());
  }

  @Test
  public void testPitAtSingleEndedCaveOnly() {
    Dungeon d = new DungeonImpl(DungeonType.NON_WRAPPING, 5, 5, 5,
            25, "Rushi", new RandomTrue(),2);
    for (List<Cave> lc : d.getAllCaves()) {
      for (Cave c: lc) {
        if (c.getPossibleMoves().getFreedom() != 1) {
          assertFalse(c.hasPit());
        }
      }
    }
  }

}