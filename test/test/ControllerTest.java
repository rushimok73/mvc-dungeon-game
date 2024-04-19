package test;

import static org.junit.Assert.assertEquals;

import control.DungeonController;
import control.DungeonControllerImpl;
import dungeon.Direction;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.DungeonType;
import dungeon.RandomDet;
import dungeon.RandomInterface;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

/**
 * Testing class for the Controller for the Dungeon.
 */
public class ControllerTest {
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

  @Test
  public void invalidCommandChoice() {
    StringReader input = new StringReader("X Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?Invalid Choice\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void invalidDirectionChoice() {
    StringReader input = new StringReader("M X Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?Enter Door to go in Invalid Input\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void invalidShootDistance() {
    StringReader input = new StringReader("S N 7 Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Enter Direction to shoot in :\n"
            + "Enter distance to shoot in (1-5) :\n"
            + "Invalid arrow shoot\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void testQuit() {
    StringReader input = new StringReader("Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void invalidShootDirection() {
    StringReader input = new StringReader("S S 3 Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Enter Direction to shoot in :\n"
            + "Enter distance to shoot in (1-5) :\n"
            + "Invalid arrow shoot\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void testControllerMove() {
    StringReader input = new StringReader("M E Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?Enter Door to go in \n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,W,S,N,\n"
            + "You can make out a faint smell in the distance..\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 1 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void testMoveIntoWall() {
    StringReader input = new StringReader("M S Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();


    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?Enter Door to go in Invalid move\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());

  }

  @Test
  public void testControllerShoot() {
    StringReader input = new StringReader("S E 3 Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();


    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Enter Direction to shoot in :\n"
            + "Enter distance to shoot in (1-5) :\n"
            + "You shoot an arrow into the darkness...\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 2 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void testControllerShootWithNoArrows() {
    d3.shootArrow(Direction.EAST, 2);
    d3.shootArrow(Direction.EAST, 2);
    d3.shootArrow(Direction.EAST, 2);
    StringReader input = new StringReader("S E 2 Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();


    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 0 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Enter Direction to shoot in :\n"
            + "Enter distance to shoot in (1-5) :\n"
            + "Invalid arrow shoot\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 0 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void testControllerShootIntoWall() {
    StringReader input = new StringReader("S S 3 Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();


    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Enter Direction to shoot in :\n"
            + "Enter distance to shoot in (1-5) :\n"
            + "Invalid arrow shoot\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void testDeathByOtyugh() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);

    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);
    StringReader input = new StringReader("M N ");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();


    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 3 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?Enter Door to go in \n"
            + "Game Over! You got eaten by an otyugh\n", output.toString());
  }

  @Test
  public void testEndReached() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);

    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);
    d3.shootArrow(Direction.NORTH, 1);
    d3.shootArrow(Direction.NORTH, 1);
    StringReader input = new StringReader("M N");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();


    assertEquals("\n"
                    + "\n"
                    + "You are in a cave\n"
                    + "Doors are :W,S,N,\n"
                    + "You currently have 1 arrows\n"
                    + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
                    + "Move, Pickup, Shoot or Quit (M-P-S-Q)?Enter Door to go in \n"
                    + "\n"
                    + "You won! Congrats\n"
                    + "Your final treasure is: 0 diamonds, 0 rubies, 0 sapphires, 1 arrows\n",
            output.toString());
  }

  @Test
  public void testInitial3Arrows() {
    StringReader input = new StringReader("Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();


    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void smell2CavesAway() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);

    StringReader input = new StringReader("M W Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();

    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,W,S,\n"
            + "You currently have 3 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?Enter Door to go in \n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,W,S,N,\n"
            + "You can make out a faint smell in the distance..\n"
            + "You currently have 3 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void smell1CavesAway() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);
    d3.move(Direction.WEST);

    StringReader input = new StringReader("M N Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();


    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,W,S,N,\n"
            + "You can make out a faint smell in the distance..\n"
            + "You currently have 3 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?Enter Door to go in \n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 3 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void otyughNotAtStart() {
    StringReader input = new StringReader("Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void testOtyughAtEnd() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);
    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);

    StringReader input = new StringReader("S N 1 S N 1 M N");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();


    assertEquals("\n"
                    + "\n"
                    + "You are in a cave\n"
                    + "Doors are :W,S,N,\n"
                    + "You smell something terrible near you..\n"
                    + "You currently have 3 arrows\n"
                    + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
                    + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
                    + "Enter Direction to shoot in :\n"
                    + "Enter distance to shoot in (1-5) :\n"
                    + "You shoot an arrow into the darkness...\n"
                    + "\n"
                    + "\n"
                    + "You are in a cave\n"
                    + "Doors are :W,S,N,\n"
                    + "You smell something terrible near you..\n"
                    + "You currently have 2 arrows\n"
                    + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
                    + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
                    + "Enter Direction to shoot in :\n"
                    + "Enter distance to shoot in (1-5) :\n"
                    + "You shoot an arrow into the darkness...\n"
                    + "\n"
                    + "\n"
                    + "You are in a cave\n"
                    + "Doors are :W,S,N,\n"
                    + "You currently have 1 arrows\n"
                    + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
                    + "Move, Pickup, Shoot or Quit (M-P-S-Q)?Enter Door to go in \n"
                    + "\n"
                    + "You won! Congrats\n"
                    + "Your final treasure is: 0 diamonds, 0 rubies, 0 sapphires, 1 arrows\n",
            output.toString());
  }

  @Test
  public void testOtyughKillsPlayer() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);

    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);
    StringReader input = new StringReader("M N");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();


    assertEquals("\n"
                    + "\n"
                    + "You are in a cave\n"
                    + "Doors are :W,S,N,\n"
                    + "You smell something terrible near you..\n"
                    + "You currently have 3 arrows\n"
                    + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
                    + "Move, Pickup, Shoot or Quit (M-P-S-Q)?Enter Door to go in \n"
                    + "Game Over! You got eaten by an otyugh\n",
            output.toString());
  }

  @Test
  public void otyughDoesntDieOn1Shot() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);

    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);
    StringReader input = new StringReader("S N 1 Q ");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 3 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Enter Direction to shoot in :\n"
            + "Enter distance to shoot in (1-5) :\n"
            + "You shoot an arrow into the darkness...\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 2 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void otyughDoesNotDieOnInaccurateShot() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);

    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);
    StringReader input = new StringReader("S N 3 S N 3 Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 3 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Enter Direction to shoot in :\n"
            + "Enter distance to shoot in (1-5) :\n"
            + "You shoot an arrow into the darkness...\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 2 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Enter Direction to shoot in :\n"
            + "Enter distance to shoot in (1-5) :\n"
            + "You shoot an arrow into the darkness...\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 1 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void otyughDiesOn2Shot() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);

    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);
    StringReader input = new StringReader("S N 1 S N 1 M N");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 3 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Enter Direction to shoot in :\n"
            + "Enter distance to shoot in (1-5) :\n"
            + "You shoot an arrow into the darkness...\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 2 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Enter Direction to shoot in :\n"
            + "Enter distance to shoot in (1-5) :\n"
            + "You shoot an arrow into the darkness...\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :W,S,N,\n"
            + "You currently have 1 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?Enter Door to go in \n"
            + "\n"
            + "You won! Congrats\n"
            + "Your final treasure is: 0 diamonds, 0 rubies, 0 sapphires, 1 arrows\n",
            output.toString());
  }

  @Test
  public void playerCanMoveAfterKillingOtyugh() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    StringReader input = new StringReader("S S 1 S S 1 M S Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Enter Direction to shoot in :\n"
            + "Enter distance to shoot in (1-5) :\n"
            + "You shoot an arrow into the darkness...\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 2 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Enter Direction to shoot in :\n"
            + "Enter distance to shoot in (1-5) :\n"
            + "You shoot an arrow into the darkness...\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,W,S,N,\n"
            + "You currently have 1 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?Enter Door to go in \n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,S,N,\n"
            + "You currently have 1 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void testItemPickUpInvalidWhenNone() {
    d3.move(Direction.EAST);
    d3.move(Direction.EAST);
    d3.move(Direction.NORTH);
    StringReader input = new StringReader("P Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();

    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :S,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?Invalid item\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :S,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void mockModelTest()  {
    StringReader input = new StringReader("M S M N M E M W P A S E 3 Q");
    StringBuilder output = new StringBuilder();
    StringBuilder mockSb = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(new MockDungeon(mockSb), input, output);
    c.playGame();


    assertEquals("Gamestate called\n"
            + "getStateDescription called\n"
            + "Input to move = SOUTH\n"
            + "Gamestate called\n"
            + "Gamestate called\n"
            + "getStateDescription called\n"
            + "Input to move = NORTH\n"
            + "Gamestate called\n"
            + "Gamestate called\n"
            + "getStateDescription called\n"
            + "Input to move = EAST\n"
            + "Gamestate called\n"
            + "Gamestate called\n"
            + "getStateDescription called\n"
            + "Input to move = WEST\n"
            + "Gamestate called\n"
            + "Gamestate called\n"
            + "getStateDescription called\n"
            + "Called pickup\n"
            + "Gamestate called\n"
            + "Gamestate called\n"
            + "getStateDescription called\n"
            + "Gamestate called\n"
            + "getStateDescription called\n"
            + "Input to shootArrow = EAST,3\n"
            + "Gamestate called\n"
            + "Gamestate called\n"
            + "getStateDescription called\n", mockSb.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void checkFailingAppendable() {
    StringReader input = new StringReader("P S P A P D P R Q");
    Appendable gameLog = new FailingAppendable();
    DungeonController c = new DungeonControllerImpl(d3, input, gameLog);
    c.playGame();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIae1() {
    StringReader input = new StringReader("P S P A P D P R Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(null, input, output);
    c.playGame();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIae2() {
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, null, output);
    c.playGame();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIae3() {
    StringReader input = new StringReader("P S P A P D P R Q");
    DungeonController c = new DungeonControllerImpl(d3, input, null);
    c.playGame();
  }

  @Test
  public void testDescription() {
    StringReader input = new StringReader("Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void pickUpItem() {
    StringReader input = new StringReader("M E P Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?Enter Door to go in \n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,W,S,N,\n"
            + "You can make out a faint smell in the distance..\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 1 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,W,S,N,\n"
            + "You can make out a faint smell in the distance..\n"
            + "You currently have 4 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void checkArrowPickup() {
    StringReader input = new StringReader("M E P Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?Enter Door to go in \n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,W,S,N,\n"
            + "You can make out a faint smell in the distance..\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 1 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,W,S,N,\n"
            + "You can make out a faint smell in the distance..\n"
            + "You currently have 4 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void checkInvalidPickup() {
    StringReader input = new StringReader("P Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?Invalid item\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void playerStatsWith3Arrows() {
    StringReader input = new StringReader("Q");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :E,\n"
            + "You currently have 3 arrows\n"
            + "You found 0 diamonds, 0 rubies, 0 sapphires 0 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Quitting Game...\n", output.toString());
  }

  @Test
  public void playerWinsGame() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);

    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);
    StringReader input = new StringReader("S N 1 S N 1 M N");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 3 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Enter Direction to shoot in :\n"
            + "Enter distance to shoot in (1-5) :\n"
            + "You shoot an arrow into the darkness...\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 2 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Enter Direction to shoot in :\n"
            + "Enter distance to shoot in (1-5) :\n"
            + "You shoot an arrow into the darkness...\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :W,S,N,\n"
            + "You currently have 1 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?Enter Door to go in \n"
            + "\n"
            + "You won! Congrats\n"
            + "Your final treasure is: 0 diamonds, 0 rubies, 0 sapphires, 1 arrows\n",
            output.toString());
  }

  @Test
  public void checkFinalInventory() {
    d3.move(Direction.EAST);
    d3.move(Direction.SOUTH);
    d3.move(Direction.WEST);

    d3.move(Direction.WEST);
    d3.move(Direction.NORTH);
    StringReader input = new StringReader("S N 1 S N 1 M N");
    StringBuilder output = new StringBuilder();
    DungeonController c = new DungeonControllerImpl(d3, input, output);
    c.playGame();
    assertEquals("\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 3 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Enter Direction to shoot in :\n"
            + "Enter distance to shoot in (1-5) :\n"
            + "You shoot an arrow into the darkness...\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :W,S,N,\n"
            + "You smell something terrible near you..\n"
            + "You currently have 2 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?\n"
            + "Enter Direction to shoot in :\n"
            + "Enter distance to shoot in (1-5) :\n"
            + "You shoot an arrow into the darkness...\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors are :W,S,N,\n"
            + "You currently have 1 arrows\n"
            + "You found 1 diamonds, 0 rubies, 0 sapphires 1 arrows\n"
            + "Move, Pickup, Shoot or Quit (M-P-S-Q)?Enter Door to go in \n"
            + "\n"
            + "You won! Congrats\n"
            + "Your final treasure is: 0 diamonds, 0 rubies, 0 sapphires, 1 arrows\n",
            output.toString());
  }
}