package control;

import dungeon.Direction;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.DungeonType;
import dungeon.RandomDet;
import dungeon.RandomTrue;
import view.DungeonView;

import java.util.List;

/**
 * MVC Controller class.
 * This class acts as listener to the view in the MVC pattern.
 * Class takes in view and model and adds an intermediary.
 */
public class MvcController implements Features {
  private final DungeonView view;
  private Dungeon model;

  /**
   * Constructor.
   * @param model Readonlymodel for controller.
   * @param view View.
   */
  public MvcController(Dungeon model, DungeonView view) {
    this.model = model;
    this.view = view;
    this.view.setFeatures(this);
  }

  @Override
  public void playGame() {
    this.view.setListener(this);
    this.view.makeVisible();
  }

  @Override
  public void movePlayer(Direction d) throws IllegalStateException {
    try {
      model.move(d);
      view.refresh();
    } catch (IllegalArgumentException | IllegalStateException e) {
      throw new IllegalStateException(e.getMessage());
    }

  }

  @Override
  public void pickUp() throws IllegalStateException {
    try {
      model.pickupItem();
      view.refresh();
    } catch (IllegalArgumentException | IllegalStateException e) {
      throw new IllegalStateException(e.getMessage());
    }

  }

  @Override
  public void shootArrow(Direction direction, Integer distance) {
    try {
      model.shootArrow(direction, distance);
      view.refresh();
    } catch (IllegalArgumentException | IllegalStateException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  @Override
  public void handleCellClick(int row, int column) {
    try {
      model.moveCoordinates(row, column);
      view.refresh();
    } catch (IllegalArgumentException | IllegalStateException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  @Override
  public void createDungeon(String type, int row, int column, int interconnectivity, int treasure,
                            String name, int otyughs) throws IllegalStateException {
    DungeonType t;
    if (type.equalsIgnoreCase("wrapping")) {
      t = DungeonType.WRAPPING;
    } else if (type.equalsIgnoreCase("non-wrapping")) {
      t = DungeonType.NON_WRAPPING;
    } else {
      t = null;
    }
    try {
      Dungeon d = new DungeonImpl(t, row, column, interconnectivity, treasure, name,
              new RandomTrue(), otyughs);
      this.model = d;
      view.updateViewModel(d);
      view.refresh();
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Invalid input");
    }
  }

  @Override
  public void refreshDungeon(String type, int row, int column, int interconnectivity, int treasure,
                             String name, int otyughs, List<Integer> randomArgs) {
    DungeonType t;
    if (type.equalsIgnoreCase("wrapping")) {
      t = DungeonType.WRAPPING;
    } else if (type.equalsIgnoreCase("non-wrapping")) {
      t = DungeonType.NON_WRAPPING;
    } else {
      t = null;
    }
    try {
      Dungeon d = new DungeonImpl(t, row, column, interconnectivity, treasure, name,
              new RandomDet(randomArgs.toArray(new Integer[0])), otyughs);
      this.model = d;
      view.updateViewModel(d);
      view.refresh();
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Invalid input");
    }
  }
}

