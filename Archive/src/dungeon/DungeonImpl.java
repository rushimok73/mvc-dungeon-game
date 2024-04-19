package dungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * Interface representing a dungeon in our game.
 * Dungeon contains a player, caves and tunnels.
 * Player can move through this dungeon from its start point to its end point.
 * Player can also collect treasures while moving through this dungeon.
 * The dungeon size must be 5x5 or greater than 5x5.
 * The interconnectivity should not exceed 10.
 */
public final class DungeonImpl implements Dungeon {
  private final List<List<Cave>> caveList;
  private final Player player;
  private final RandomInterface random;
  private final String name;
  private final int rows;
  private final int columns;
  private final int interconnectivity;
  private final int treasure;
  private final DungeonType type;
  private final List<int[]> realCaveList;
  private final List<Cave> entityCaves;
  private final int otyuhgsip;
  private final List<Integer> randomArgs;
  private int startx;
  private int starty;
  private int endx;
  private int endy;
  private GameState gameState;
  private int currentx;
  private int currenty;
  private int otyuhgs;
  private int thieves;
  private int pits;
  private StringBuilder whatHappened;

  /**
   * Constructor for dungeon.
   *
   * @param type              type of the dungeon.
   * @param rows              number of rows. Should be >= 5.
   * @param columns           number of rows. Should be >= 5.
   * @param interconnectivity number of rows. Should be <= 10.
   * @param treasurePercent   percentage of caves you want treasure to be added to.
   * @param name              Name of the player.
   * @param random            RandomInterface object.
   * @param otyughs           if otyughs are less than 1.
   * @throws IllegalArgumentException when rows >5, columns >5 , interconnectivity is greater than
   *                                  10 or less than 0, tresurePercent > 0 Name is null or empty,
   *                                  RandomInterface object is null.
   */
  public DungeonImpl(DungeonType type, int rows, int columns, int interconnectivity,
                     int treasurePercent, String name, RandomInterface random, int otyughs)
          throws IllegalArgumentException {
    if (rows < 5 || columns < 5) {
      throw new IllegalArgumentException("Both dimensions should be greater than 5");
    }
    if (interconnectivity > 10) {
      throw new IllegalArgumentException("Interconnectivity should be less than 10");
    }
    if (treasurePercent < 0 || interconnectivity < 0) {
      throw new IllegalArgumentException("Values cannot be negative");
    }
    if (Objects.isNull(type)) {
      throw new IllegalArgumentException("type cannot be null");
    }
    if (Objects.isNull(random)) {
      throw new IllegalArgumentException("Please input valid random instance");
    }
    if (Objects.isNull(name) || name.compareTo("") == 0) {
      throw new IllegalArgumentException("Please input a valid name");
    }
    if (otyughs < 1 || otyughs > rows * columns / 10) {
      throw new IllegalArgumentException("Otyughs need to be 1 and 1/10th of all cells");
    }
    this.randomArgs = new ArrayList<>();
    this.entityCaves = new ArrayList<>();
    this.whatHappened = new StringBuilder("Dread fills you..");
    this.rows = rows;
    this.columns = columns;
    this.type = type;
    caveList = new ArrayList<>();
    realCaveList = new ArrayList<>();
    this.random = random;
    this.otyuhgs = otyughs;
    this.pits = otyughs;
    this.thieves = otyughs;
    this.otyuhgsip = otyughs;
    this.treasure = treasurePercent;
    this.name = name;
    this.interconnectivity = interconnectivity;
    generateDungeon(type, rows, columns, interconnectivity);
    findStartEnd(rows, columns);
    addTreasure(treasurePercent);
    addArrows(treasurePercent);
    getEntityCaves();
    addOtyughs();
    addThieves();
    addPits();
    updateCaveSmell();
    updateCaveShaky();
    this.currentx = this.startx;
    this.currenty = this.starty;
    this.player = new PlayerImpl(name);
    this.gameState = GameState.PLAYING;
    ((CaveImpl) this.caveList.get(this.startx).get(this.starty)).setVisited();
    randomArgs.add(-1);
  }

  /**
   * Defualt constructor.
   */
  public DungeonImpl() {
    this(DungeonType.WRAPPING, 7, 7, 5,
            50, "Rushi", new RandomTrue(), 3);
  }

  private void generateDungeon(DungeonType type, int rows, int columns, int interconnectivity)
          throws IllegalArgumentException {
    //Create edges.
    List<int[]> allEdges = new ArrayList<>();
    List<int[]> resultEdges = new ArrayList<>();
    List<int[]> discardedEdges = new ArrayList<>();

    //Create edge pairs and empty caves
    for (int i = 0; i < rows; i++) {
      List<Cave> temp = new ArrayList<>();
      for (int j = 0; j < columns; j++) {
        temp.add(new CaveImpl());
        if (j + 1 < columns) {
          allEdges.add(new int[]{columns * i + j, columns * i + (j + 1)});
        }
        if (i + 1 < rows) {
          allEdges.add(new int[]{columns * i + j, columns * (i + 1) + j});
        }
      }
      caveList.add(temp);
    }


    if (type == DungeonType.WRAPPING) { //add wrapping edges.
      for (int j = 0; j < columns; j++) { //first-last row edges.
        allEdges.add(new int[]{j, (rows - 1) * columns + j});
      }

      for (int i = 0; i < rows; i++) { //first-last column edges.
        allEdges.add(new int[]{columns * i, (columns - 1) + columns * i});
      }
    }

    //Making row x column sets.
    DisjointSet ds = new DisjointSet();
    for (int i = 0; i < rows * columns; i++) {
      ds.makeSet(i);
    }

    //Kruskal Implementation.
    while (allEdges.size() > 0) {
      int pos = random.nextInt(allEdges.size());
      randomArgs.add(pos);
      int[] edge = allEdges.get(pos);
      int r1 = ds.findSet(edge[0]);
      int r2 = ds.findSet(edge[1]);

      if (r1 == r2) {
        discardedEdges.add(edge);
        allEdges.remove(pos);
      } else {
        resultEdges.add(edge);
        allEdges.remove(pos);
        ds.union(edge[0], edge[1]);
      }
    }

    if (interconnectivity > discardedEdges.size()) {
      throw new IllegalArgumentException("interconnectivity too high");
    }

    //Check interconnectivity.
    while (interconnectivity > 0 && discardedEdges.size() > 0) {
      int pos = random.nextInt(discardedEdges.size());
      randomArgs.add(pos);
      int[] edge = discardedEdges.get(pos);
      resultEdges.add(edge);
      discardedEdges.remove(pos);
      interconnectivity--;
    }

    //Setting cave directions
    for (int[] edge : resultEdges) {
      int x1 = edge[0] / columns;
      int y1 = edge[0] % columns;
      int x2 = edge[1] / columns;
      int y2 = edge[1] % columns;

      Movement m1 = caveList.get(x1).get(y1).getPossibleMoves();
      Movement m2 = caveList.get(x2).get(y2).getPossibleMoves();

      if ((x1 == 0 && x2 == rows - 1) && type == DungeonType.WRAPPING) { //Wrapping column.
        m1.setNorth(true);
        m2.setSouth(true);
      } else if ((y1 == 0 && y2 == columns - 1) && type == DungeonType.WRAPPING) { //Wrapping row.
        m1.setWest(true);
        m2.setEast(true);
      } else { //Non Wrapping edges.
        m1.setNorth(x1 > x2);
        m1.setSouth(x2 > x1);
        m1.setWest(y1 > y2);
        m1.setEast(y2 > y1);

        m2.setNorth(x1 < x2);
        m2.setSouth(x2 < x1);
        m2.setWest(y1 < y2);
        m2.setEast(y2 < y1);
      }
    }

    //Store cave and tunnel in seperate lists.
    for (int i = 0; i < caveList.size(); i++) {
      for (int j = 0; j < caveList.get(i).size(); j++) {
        if (caveList.get(i).get(j).getPossibleMoves().getFreedom() != 2) {
          realCaveList.add(new int[]{i, j});
        }
      }
    }
  }

  private void getEntityCaves() {
    Cave startCave = this.caveList.get(startx).get(starty);
    for (List<Cave> lc : this.caveList) {
      for (Cave c : lc) {
        if (c.getPossibleMoves().getFreedom() != 2 && c != startCave) {
          entityCaves.add(c);
        }
      }
    }
  }

  private void addTreasure(int treasurePercent) {
    List<Cave> treasureCaves = new ArrayList<>();
    for (List<Cave> lc : caveList) {
      for (Cave c : lc) {
        if (c.getPossibleMoves().getFreedom() != 2) {
          treasureCaves.add(c);
        }
      }
    }
    int count = treasureCaves.size() * treasurePercent / 100;

    while (count > 0) {
      int pos = random.nextInt(treasureCaves.size());
      randomArgs.add(pos);
      Cave c = treasureCaves.get(pos);
      int treasure = random.nextInt(3);
      randomArgs.add(treasure);
      if (treasure == 0) {
        ((CaveImpl) c).addTreasure(1, 0, 0, 0);
      } else if (treasure == 1) {
        ((CaveImpl) c).addTreasure(0, 1, 0, 0);
      } else {
        ((CaveImpl) c).addTreasure(0, 0, 1, 0);
      }
      treasureCaves.remove(c);
      count--;
    }
  }

  private void addArrows(int arrowPercent) {
    List<Cave> allCaves = new ArrayList<>();
    for (List<Cave> caves : caveList) {
      allCaves.addAll(caves);
    }
    int count = allCaves.size() * arrowPercent / 100;

    while (count > 0) {
      int pos = random.nextInt(allCaves.size());
      randomArgs.add(pos);
      Cave c = allCaves.get(pos);
      ((CaveImpl) c).addTreasure(0, 0, 0, 1);
      allCaves.remove(c);
      count--;
    }
  }

  private void validateDirection(Direction d) {
    CaveImpl c = (CaveImpl) this.caveList.get(currentx).get(currenty);
    if (d == Direction.NORTH && !c.getPossibleMoves().getNorth()) {
      throw new IllegalArgumentException("Going towards wall");
    }

    if (d == Direction.SOUTH && !c.getPossibleMoves().getSouth()) {
      throw new IllegalArgumentException("Going towards wall");
    }

    if (d == Direction.EAST && !c.getPossibleMoves().getEast()) {
      throw new IllegalArgumentException("Going towards wall");
    }

    if (d == Direction.WEST && !c.getPossibleMoves().getWest()) {
      throw new IllegalArgumentException("Going towards wall");
    }
  }

  @Override
  public void shootArrow(Direction d, int distance) throws IllegalArgumentException {
    if (this.gameState == GameState.OVER) {
      throw new IllegalArgumentException("Game has finished");
    }

    if (this.gameState == GameState.DEAD) {
      throw new IllegalArgumentException("You have died! Game is over");
    }

    if (distance < 1 || distance > 5) {
      throw new IllegalArgumentException("Distance must be between 1 and 5 inclusive");
    }

    if (player.getBag().get(Treasure.ARROWS) <= 0) {
      throw new IllegalArgumentException("Player doesn't have arrows");
    }

    validateDirection(d);

    int arrowx = currentx;
    int arrowy = currenty;
    while (true) {
      if (distance == 0) {
        break;
      }
      List<Integer> newPos = getUpdatedPos(arrowx, arrowy, d);
      int x = newPos.get(0);
      int y = newPos.get(1);
      if (x == arrowx && y == arrowy) { //Arrow reached wall now.
        break;
      } else {
        arrowx = x;
        arrowy = y;
        Cave newCave = this.caveList.get(x).get(y);
        if (newCave.getPossibleMoves().getFreedom() == 2) { //Its a tunnel
          if (d == Direction.SOUTH) { //Entered tunnel from North
            if (newCave.getPossibleMoves().getWest()) {
              d = Direction.WEST;
            } else if (newCave.getPossibleMoves().getEast()) {
              d = Direction.EAST;
            }
          } else if (d == Direction.NORTH) { //Entered from tunnels South
            if (newCave.getPossibleMoves().getWest()) {
              d = Direction.WEST;
            } else if (newCave.getPossibleMoves().getEast()) {
              d = Direction.EAST;
            }
          } else if (d == Direction.EAST) { //Entered from tunnels West
            if (newCave.getPossibleMoves().getSouth()) {
              d = Direction.SOUTH;
            } else if (newCave.getPossibleMoves().getNorth()) {
              d = Direction.NORTH;
            }
          } else if (d == Direction.WEST) { //Entered from tunnels East
            if (newCave.getPossibleMoves().getSouth()) {
              d = Direction.SOUTH;
            } else if (newCave.getPossibleMoves().getNorth()) {
              d = Direction.NORTH;
            }
          }

        } else { //Its a cave
          distance--;
          if (d == Direction.SOUTH) { //Entered cave from North
            if (!newCave.getPossibleMoves().getSouth()) {
              ((PlayerImpl) this.player).decrementArrow();
              if (distance == 0) {
                checkOtyughDead(arrowx, arrowy);
              }
              return;
            }
          }

          if (d == Direction.NORTH) {
            if (!newCave.getPossibleMoves().getNorth()) {
              ((PlayerImpl) this.player).decrementArrow();
              if (distance == 0) {
                checkOtyughDead(arrowx, arrowy);
              }
              return;
            }
          }

          if (d == Direction.WEST) {
            if (!newCave.getPossibleMoves().getWest()) {
              ((PlayerImpl) this.player).decrementArrow();
              if (distance == 0) {
                checkOtyughDead(arrowx, arrowy);
              }
              return;
            }
          }

          if (d == Direction.EAST) {
            if (!newCave.getPossibleMoves().getEast()) {
              ((PlayerImpl) this.player).decrementArrow();
              if (distance == 0) {
                checkOtyughDead(arrowx, arrowy);
              }
              return;
            }
          }

        }
      }
    }
    ((PlayerImpl) this.player).decrementArrow();
    checkOtyughDead(arrowx, arrowy);
  }

  @Override
  public int getPlayerX() {
    return this.currentx;
  }

  @Override
  public int getEndX() {
    return this.endx;
  }

  @Override
  public int getEndY() {
    return this.endy;
  }

  @Override
  public int getPlayerY() {
    return this.currenty;
  }

  @Override
  public GameState getGameState() {
    return this.gameState;
  }

  @Override
  public Player getPlayer() {
    return this.player;
  }

  @Override
  public List<List<Cave>> getAllCaves() {
    List<List<Cave>> deepCopy = new ArrayList<>();
    for (List<Cave> lc : this.caveList) {
      List<Cave> temp = new ArrayList<>();
      for (Cave c : lc) {
        temp.add(new CaveImpl((CaveImpl) c));
      }
      deepCopy.add(temp);
    }
    return deepCopy;
  }

  private List<Integer> getUpdatedPos(int x, int y, Direction direction) {
    int vertex = x * columns + y;
    Cave c = caveList.get(x).get(y);
    if (direction == Direction.NORTH) {
      if (c.getPossibleMoves().getNorth()) {
        if (vertex - columns < 0) {  //check if up will go through upper edge.
          if (this.type == DungeonType.WRAPPING) {
            vertex = vertex + (rows - 1) * columns; //send it down.
            x = vertex / columns;
            y = vertex % columns;
          }
        } else {
          vertex = vertex - columns;
          x = vertex / columns;
          y = vertex % columns;
        }
      }
    } else if (direction == Direction.SOUTH) {
      if (c.getPossibleMoves().getSouth()) {
        if (vertex + columns >= rows * columns) {  //check if down will go through lower edge.
          if (this.type == DungeonType.WRAPPING) {
            vertex = vertex - (rows - 1) * columns; //send it to top.
            x = vertex / columns;
            y = vertex % columns;
          }
        } else {
          vertex = vertex + columns;
          x = vertex / columns;
          y = vertex % columns;
        }
      }
    } else if (direction == Direction.WEST) {
      if (c.getPossibleMoves().getWest()) {
        if (vertex % columns - 1 < 0) {  //check if left will go through left edge.
          if (this.type == DungeonType.WRAPPING) {
            vertex = vertex + columns - 1; //send it to right.
            x = vertex / columns;
            y = vertex % columns;
          }
        } else {
          vertex = vertex - 1;
          x = vertex / columns;
          y = vertex % columns;
        }
      }
    } else if (direction == Direction.EAST) {
      if (c.getPossibleMoves().getEast()) {
        if (vertex % columns + 1 >= columns) {  //check if right will go through right edge.
          if (this.type == DungeonType.WRAPPING) {
            vertex = vertex - columns + 1; //send it to left.
            x = vertex / columns;
            y = vertex % columns;
          }
        } else {
          vertex = vertex + 1;
          x = vertex / columns;
          y = vertex % columns;
        }
      }
    } else {
      throw new IllegalArgumentException("Illegal move");
    }
    return new ArrayList<>(Arrays.asList(x, y));
  }

  @Override
  public void move(Direction direction) throws IllegalArgumentException {
    if (this.gameState == GameState.OVER) {
      throw new IllegalArgumentException("Game has finished");
    }

    if (this.gameState == GameState.DEAD) {
      throw new IllegalArgumentException("You have died! Game is over");
    }
    validateDirection(direction);

    List<Integer> newPos = getUpdatedPos(currentx, currenty, direction);
    this.currentx = newPos.get(0);
    this.currenty = newPos.get(1);
    CaveImpl newCave = ((CaveImpl) this.caveList.get(currentx).get(currenty));
    this.whatHappened = new StringBuilder("Dread fills you..");
    if (newCave.getPit()) {
      this.gameState = GameState.DEAD;
      this.whatHappened = new StringBuilder("You fell to your death..");
    }
    newCave.setVisited();

    if (newCave.getThief() != null) {
      PlayerImpl p = (PlayerImpl) this.player;
      p.emptyBag();
      newCave.removeThief();
      this.whatHappened = new StringBuilder("Your pockets feel lighter!");
    }

    if (newCave.getOtyugh() != null) {
      if (((Otyugh)newCave.getOtyugh()).getHp() == 1) {
        int chance = random.nextInt(2);
        randomArgs.add(chance);
        if (chance == 1) {
          this.gameState = GameState.DEAD;
          return;
        }
      } else { //Player dies.
        this.gameState = GameState.DEAD;
        return;
      }

    }
    if (this.currentx == this.endx && this.currenty == this.endy) {
      this.gameState = GameState.OVER;
    }
  }

  @Override
  public void pickupItem() throws IllegalArgumentException {
    if (this.gameState == GameState.OVER) {
      throw new IllegalArgumentException("Game has finished");
    }

    if (this.gameState == GameState.DEAD) {
      throw new IllegalArgumentException("You have died! Game is over");
    }

    CaveImpl c = (CaveImpl) this.caveList.get(currentx).get(currenty);
    PlayerImpl p = (PlayerImpl) this.player;
    if (c.getBag().get(Treasure.ARROWS)
            + c.getBag().get(Treasure.DIAMONDS)
            + c.getBag().get(Treasure.RUBIES)
            + c.getBag().get(Treasure.SAPPHIRES) <= 0) {

      throw new IllegalArgumentException("No treasure to pickup");
    } else {
      if (c.getBag().get(Treasure.DIAMONDS) > 0) {
        c.pickupItem(Treasure.DIAMONDS);
        p.addTreasure(Treasure.DIAMONDS);
      }
      if (c.getBag().get(Treasure.RUBIES) > 0) {
        c.pickupItem(Treasure.RUBIES);
        p.addTreasure(Treasure.RUBIES);
      }
      if (c.getBag().get(Treasure.SAPPHIRES) > 0) {
        c.pickupItem(Treasure.SAPPHIRES);
        p.addTreasure(Treasure.SAPPHIRES);
      }
      if (c.getBag().get(Treasure.ARROWS) > 0) {
        c.pickupItem(Treasure.ARROWS);
        p.addTreasure(Treasure.ARROWS);
      }


    }
  }

  @Override
  public void moveCoordinates(int row, int column) throws IllegalArgumentException {
    if (Math.abs(row - this.getPlayerX()) + Math.abs(column - this.getPlayerY()) != 1) {
      throw new IllegalArgumentException("Invalid move");
    } else {
      if (row > this.getPlayerX()) {
        move(Direction.SOUTH);
      }
      else if (row < this.getPlayerX()) {
        move(Direction.NORTH);
      }
      else if (column > this.getPlayerY()) {
        move(Direction.EAST);
      }
      else if (column < this.getPlayerY()) {
        move(Direction.WEST);
      }

    }

  }

  /**
   * String representation of the dungeon as a 2d matrix.
   * Legend: P:Player  E:End  T:Tunnel  C:Cave with treasure  c:Cave without treasure O: Otyugh
   * End cave always has atleast 1 otyugh it.
   *
   * @return String representation of the dungeon,
   */
  @Override
  public String toString() {
    int m = 2 * this.rows + 1;
    int n = 2 * this.columns + 1;
    char[][] grid = new char[m][n];
    for (char[] row : grid) {
      Arrays.fill(row, ' ');
    }

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {

        if (i % 2 == 1 && j % 2 == 1) { //Cave spot, set 4 locs around it.
          Cave c = caveList.get((i - 1) / 2).get((j - 1) / 2);
          if (c.getPossibleMoves().getNorth()) {
            grid[i - 1][j] = '|';
          }
          if (c.getPossibleMoves().getSouth()) {
            grid[i + 1][j] = '|';
          }
          if (c.getPossibleMoves().getWest()) {
            grid[i][j - 1] = '-';
          }
          if (c.getPossibleMoves().getEast()) {
            grid[i][j + 1] = '-';
          }

          if (c.getPossibleMoves().getFreedom() == 2) {
            grid[i][j] = 'T';
          } else {
            if (c.getBag().get(Treasure.DIAMONDS)
                    + c.getBag().get(Treasure.RUBIES)
                    + c.getBag().get(Treasure.SAPPHIRES) == 0) {
              grid[i][j] = 'c';
            } else {
              grid[i][j] = 'C';
            }
          }

          if (((CaveImpl) c).getPit()) {
            grid[i][j] = '@';
          }

          if (((CaveImpl) c).getThief() != null) {
            grid[i][j] = '$';
          }


          if (((CaveImpl) c).getOtyugh() != null) {
            grid[i][j] = 'O';
          }

          if (this.endx == (i - 1) / 2 && this.endy == (j - 1) / 2) {
            grid[i][j] = 'E';
          }
          if (this.currentx == (i - 1) / 2 && this.currenty == (j - 1) / 2) {
            grid[i][j] = 'P';
          }
        }
      }
    }

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        sb.append(grid[i][j]);
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  private void findStartEnd(int rows, int columns) throws IllegalArgumentException {

    //Go through each and every cave now to find suitable end.
    int[] src = {-1, -1}; //dummy initialization
    while (true) {
      if (src[0] != -1 && src[1] != -1) {
        realCaveList.remove(src); //remove previous src. We cannot find any ends with it.
      }
      if (realCaveList.size() == 0) {
        throw new IllegalArgumentException("This dungeon is impossible to "
                + "create, please try again");
      }
      int pos = random.nextInt(realCaveList.size());
      randomArgs.add(pos);
      src = realCaveList.get(pos);
      realCaveList.remove(src);

      int l = bfs(src, rows, columns);
      if (l != -1) {
        int st = ((src[0] * columns) + src[1]);
        this.startx = st / columns;
        this.starty = st % columns;
        this.endx = l / columns;
        this.endy = l % columns;
        break;
      }
    }
  }

  private int bfs(int[] src, int rows, int columns) {
    int srcVertex = src[0] * columns + src[1];
    Queue<List<Integer>> queue = new LinkedList<>();
    HashMap<Integer, Integer> endLengths = new HashMap<>();

    List<Integer> path = new ArrayList<>();
    path.add(srcVertex);
    queue.add(path);

    while (!queue.isEmpty()) {
      //Get path
      path = queue.poll();

      //Grab last node from it
      int last = path.get(path.size() - 1);

      //Check tunnel and check if srcVertex, then update map.
      if (caveList.get(last / columns).get(last % columns).getPossibleMoves().getFreedom() != 2
              && last != srcVertex) {
        if (endLengths.containsKey(last)) {
          if (endLengths.get(last) > path.size()) { //only update if smaller
            endLengths.put(last, path.size());
          }
        } else {
          endLengths.put(last, path.size());
        }
      }

      //Grab all nodes connected to the last and put these in lastNode
      List<Integer> lastNode = new ArrayList<>();
      Cave c = caveList.get(last / columns).get(last % columns);

      if (c.getPossibleMoves().getWest()) {
        if (this.type == DungeonType.WRAPPING && (last % columns) - 1 < 0) {
          lastNode.add(last + columns - 1);
        } else {
          lastNode.add(last - 1);
        }
      }

      if (c.getPossibleMoves().getEast()) {
        if (this.type == DungeonType.WRAPPING && (last + 1) % columns == 0) {
          lastNode.add(last - columns + 1);
        } else {
          lastNode.add(last + 1);
        }
      }

      if (c.getPossibleMoves().getNorth()) {
        if (this.type == DungeonType.WRAPPING && last - columns < 0) {
          lastNode.add(last + (rows - 1) * columns);
        } else {
          lastNode.add(last - columns);
        }
      }

      if (c.getPossibleMoves().getSouth()) {
        if (this.type == DungeonType.WRAPPING && last + columns >= columns * rows) {
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

    List<int[]> pairs = new ArrayList<>();

    for (HashMap.Entry<Integer, Integer> entry : endLengths.entrySet()) {
      Integer key = entry.getKey();
      Integer value = entry.getValue();
      if (value > 5 && key != srcVertex) {
        pairs.add(new int[]{key, value});
      }
    }

    if (pairs.size() > 0) {
      int pos = random.nextInt(pairs.size());
      randomArgs.add(pos);
      return pairs.get(pos)[0];
    } else {
      return -1;
    }

  }

  private boolean isNotVisited(int ele, List<Integer> p) {
    for (Integer integer : p) {
      if (integer == ele) {
        return false;
      }
    }
    return true;
  }

  private void addOtyughs() {
    //Add one to end.
    Cave endCave = this.caveList.get(endx).get(endy);
    ((CaveImpl) endCave).addOtyughs();
    entityCaves.remove(endCave);
    this.otyuhgs--;

    while (this.otyuhgs > 0 && entityCaves.size() > 0) {
      int pos = random.nextInt(entityCaves.size());
      randomArgs.add(pos);
      Cave c = entityCaves.get(pos);
      ((CaveImpl) c).addOtyughs();
      entityCaves.remove(c);
      this.otyuhgs--;
    }
  }

  private void addThieves() {
    while (this.thieves > 0 && entityCaves.size() > 0) {
      int pos = random.nextInt(entityCaves.size());
      randomArgs.add(pos);
      Cave c = entityCaves.get(pos);
      ((CaveImpl) c).addThieves();
      entityCaves.remove(c);
      this.thieves = this.thieves - 1;
    }
  }

  private void addPits() {
    List<Cave> pitCave = new ArrayList<>();
    for (Cave c: entityCaves) {
      if (c.getPossibleMoves().getFreedom() == 1) {
        pitCave.add(c);
      }
    }
    while (this.pits > 0 && pitCave.size() > 0) {
      int pos = random.nextInt(pitCave.size());
      randomArgs.add(pos);
      Cave c = pitCave.get(pos);
      ((CaveImpl) c).addPits();
      entityCaves.remove(c);
      pitCave.remove(c);
      this.pits--;
    }
  }

  private void updateCaveSmell() {
    //Set all smells to zero first.
    for (List<Cave> caves : caveList) {
      for (Cave cave : caves) {
        ((CaveImpl) cave).zeroSmell();
      }
    }
    for (int i = 0; i < caveList.size(); i++) {
      for (int j = 0; j < caveList.get(i).size(); j++) {
        if (((CaveImpl) caveList.get(i).get(j)).getOtyugh() != null) {
          updateNeighbours(i, j);
        }
      }
    }
  }

  private void updateCaveShaky() {
    for (int i = 0; i < caveList.size(); i++) {
      for (int j = 0; j < caveList.get(i).size(); j++) {
        if (caveList.get(i).get(j).hasPit()) {
          Cave c = caveList.get(i).get(j);
          if (c.getPossibleMoves().getNorth()) {
            List<Integer> pos1 = getUpdatedPos(i, j, Direction.NORTH);
            Cave newCave1 = caveList.get(pos1.get(0)).get(pos1.get(1));
            ((CaveImpl) newCave1).updateCrack();
          }

          if (c.getPossibleMoves().getSouth()) {
            List<Integer> pos1 = getUpdatedPos(i, j, Direction.SOUTH);
            Cave newCave1 = caveList.get(pos1.get(0)).get(pos1.get(1));
            ((CaveImpl) newCave1).updateCrack();
          }

          if (c.getPossibleMoves().getEast()) {
            List<Integer> pos1 = getUpdatedPos(i, j, Direction.EAST);
            Cave newCave1 = caveList.get(pos1.get(0)).get(pos1.get(1));
            ((CaveImpl) newCave1).updateCrack();
          }

          if (c.getPossibleMoves().getWest()) {
            List<Integer> pos1 = getUpdatedPos(i, j, Direction.WEST);
            Cave newCave1 = caveList.get(pos1.get(0)).get(pos1.get(1));
            ((CaveImpl) newCave1).updateCrack();
          }
        }
      }
    }
  }


  private void updateNeighbours(int i, int j) {
    Cave c = caveList.get(i).get(j);
    if (c.getPossibleMoves().getNorth()) {
      List<Integer> pos1 = getUpdatedPos(i, j, Direction.NORTH);
      Cave newCave1 = caveList.get(pos1.get(0)).get(pos1.get(1));
      ((CaveImpl) newCave1).updateSmell(2);

      if (newCave1.getPossibleMoves().getNorth()) {
        List<Integer> pos2 = getUpdatedPos(pos1.get(0), pos1.get(1), Direction.NORTH);
        Cave newCave2 = caveList.get(pos2.get(0)).get(pos2.get(1));
        ((CaveImpl) newCave2).updateSmell(1);
      }
    }

    if (c.getPossibleMoves().getSouth()) {
      List<Integer> pos1 = getUpdatedPos(i, j, Direction.SOUTH);
      Cave newCave1 = caveList.get(pos1.get(0)).get(pos1.get(1));
      ((CaveImpl) newCave1).updateSmell(2);

      if (newCave1.getPossibleMoves().getSouth()) {
        List<Integer> pos2 = getUpdatedPos(pos1.get(0), pos1.get(1), Direction.SOUTH);
        Cave newCave2 = caveList.get(pos2.get(0)).get(pos2.get(1));
        ((CaveImpl) newCave2).updateSmell(1);
      }
    }

    if (c.getPossibleMoves().getEast()) {
      List<Integer> pos1 = getUpdatedPos(i, j, Direction.EAST);
      Cave newCave1 = caveList.get(pos1.get(0)).get(pos1.get(1));
      ((CaveImpl) newCave1).updateSmell(2);

      if (newCave1.getPossibleMoves().getEast()) {
        List<Integer> pos2 = getUpdatedPos(pos1.get(0), pos1.get(1), Direction.EAST);
        Cave newCave2 = caveList.get(pos2.get(0)).get(pos2.get(1));
        ((CaveImpl) newCave2).updateSmell(1);
      }
    }

    if (c.getPossibleMoves().getWest()) {
      List<Integer> pos1 = getUpdatedPos(i, j, Direction.WEST);
      Cave newCave1 = caveList.get(pos1.get(0)).get(pos1.get(1));
      ((CaveImpl) newCave1).updateSmell(2);

      if (newCave1.getPossibleMoves().getWest()) {
        List<Integer> pos2 = getUpdatedPos(pos1.get(0), pos1.get(1), Direction.WEST);
        Cave newCave2 = caveList.get(pos2.get(0)).get(pos2.get(1));
        ((CaveImpl) newCave2).updateSmell(1);
      }
    }
  }

  private void checkOtyughDead(int arrowx, int arrowy) {
    CaveImpl c = ((CaveImpl) caveList.get(arrowx).get(arrowy));
    if (c.getOtyugh() != null) {
      Otyugh ot = (Otyugh) c.getOtyugh();
      ot.reduceHp();
      if (ot.isDead()) {
        c.removeOtyugh();
        updateCaveSmell();
        this.whatHappened = new StringBuilder("You smell blood..");
      } else {
        this.whatHappened = new StringBuilder("You hear wailing..");
      }
    } else {
      this.whatHappened = new StringBuilder("Your arrow pierces through");
    }
  }

  private boolean currentIsCave() {
    return this.caveList.get(currentx).get(currenty).getPossibleMoves().getFreedom() != 2;
  }

  private List<String> getEntrances() {
    List<String> ret = new ArrayList<>();
    CaveImpl c = (CaveImpl) caveList.get(currentx).get(currenty);
    if (c.getPossibleMoves().getEast()) {
      ret.add("E");
    }
    if (c.getPossibleMoves().getWest()) {
      ret.add("W");
    }
    if (c.getPossibleMoves().getSouth()) {
      ret.add("S");
    }
    if (c.getPossibleMoves().getNorth()) {
      ret.add("N");
    }

    return ret;
  }

  @Override
  public String getStateDescription() {
    StringBuilder sb = new StringBuilder();

    if (gameState == GameState.DEAD) {
      sb.append("\nGame Over! You got eaten by an otyugh\n");
      return sb.toString();
    }

    if (gameState == GameState.OVER) {
      sb.append("\n\nYou won! Congrats");
      sb.append("\nYour final treasure is: ");
      sb.append(String.format("%d diamonds, %d rubies, %d sapphires, %d arrows\n",
              player.getBag().get(Treasure.DIAMONDS), player.getBag().get(Treasure.RUBIES),
              player.getBag().get(Treasure.SAPPHIRES), player.getBag().get(Treasure.ARROWS)));
      return sb.toString();
    }

    if (this.currentIsCave()) {
      sb.append("\n\nYou are in a cave\n");
    } else {
      sb.append("\n\nYou are in a tunnel\n");
    }
    sb.append("Doors are :");
    for (String s : this.getEntrances()) {
      sb.append(s).append(",");
    }
    sb.append("\n");

    CaveImpl c = (CaveImpl) this.caveList.get(currentx).get(currenty);
    switch (c.getSmell().toString()) {
      case "LESS_PUNGENT":
        sb.append("You can make out a faint smell in the distance..\n");
        break;
      case "MORE_PUNGENT":
        sb.append("You smell something terrible near you..\n");
        break;
      default:
        break;
    }

    sb.append(String.format("You currently have %d arrows\n",
            player.getBag().get(Treasure.ARROWS)));

    sb.append(String.format("You found %d diamonds, %d rubies, %d sapphires %d arrows\n",
            c.getBag().get(Treasure.DIAMONDS), c.getBag().get(Treasure.RUBIES),
            c.getBag().get(Treasure.SAPPHIRES), c.getBag().get(Treasure.ARROWS)));
    return sb.toString();
  }

  @Override
  public List<String> getStats() {
    return Arrays.asList(type.toString(), String.valueOf(rows), String.valueOf(columns),
            String.valueOf(interconnectivity), String.valueOf(treasure), name,
            String.valueOf(otyuhgsip));
  }

  @Override
  public List<Integer> getRandomArgs() {
    List<Integer> ret = new ArrayList<>();
    for (int i : randomArgs) {
      if (i == -1) {
        return ret;
      } else {
        ret.add(i);
      }
    }
    return ret;
  }

  @Override
  public StringBuilder getWhatHappened() {
    return this.whatHappened;
  }
}

