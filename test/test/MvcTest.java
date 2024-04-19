package test;

import static org.junit.Assert.assertEquals;

import control.Features;
import control.MvcController;
import dungeon.Direction;
import dungeon.Dungeon;
import dungeon.RandomDet;
import dungeon.RandomInterface;
import org.junit.Before;
import org.junit.Test;
import view.DungeonView;

import java.util.ArrayList;

/**
 * Class for testing ModelViewController interaction in the dungeon.
 */
public class MvcTest {
  RandomInterface random1;

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
            50, 40, 36, 37, 12, 38);
  }

  @Test
  public void testMockView() {
    StringBuilder mockSb = new StringBuilder();
    //Non Wrapping 0 Interconnectivity
    Dungeon d1 = new MockDungeon(mockSb);
    DungeonView view = new MockView(mockSb);
    Features c = new MvcController(d1, view);
    c.playGame();
    c.movePlayer(Direction.SOUTH);
    c.handleCellClick(5, 10);
    c.pickUp();
    c.shootArrow(Direction.SOUTH, 4);
    c.refreshDungeon("Wrapping", 5, 5, 5,
            25 , "Rushi", 2, new ArrayList<>());
    c.createDungeon("Wrapping", 5, 5, 5,
            25 , "Rushi", 2);

    assertEquals( "setFeatures called\n"
            + "addListener called\n"
            + "makeVisible called\n"
            + "Input to move = SOUTH\n"
            + "refresh called\n"
            + "Input to moveCoordinates = 5,10\n"
            + "refresh called\n"
            + "Called pickup\n"
            + "refresh called\n"
            + "Input to shootArrow = SOUTH,4\n"
            + "refresh called\n"
            + "updateViewModel called\n"
            + "refresh called\n"
            + "updateViewModel called\n"
            + "refresh called\n",mockSb.toString());
  }
}
