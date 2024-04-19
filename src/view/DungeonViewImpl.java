package view;

import control.Features;
import control.MvcController;
import dungeon.Direction;
import dungeon.GameState;
import dungeon.ReadonlyDungeonModel;
import dungeon.Treasure;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

/**
 * Class implementing DungeonView interface.
 * This class creates the panels inside the view and adds them to the frame.
 * Inside these panels the meat of the program is contained.
 */
public final class DungeonViewImpl extends JFrame implements DungeonView {

  private final JMenu createMenu;
  private final JMenu resetMenu;
  private final JMenu refreshMenu;
  private final JMenu quitMenu;
  private BoardPanel boardPanel;
  private StatsPanel statsPanel;
  private ReadonlyDungeonModel model;

  /**
   * Constructor.
   * @param model ReadonlyModel for the view.
   */
  public DungeonViewImpl(ReadonlyDungeonModel model) {
    super("World of OtyughCraft");
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    createMenu = new JMenu("Create");
    resetMenu = new JMenu("Restart");
    refreshMenu = new JMenu("Refresh");
    quitMenu = new JMenu("Quit");
    JMenuBar mb = new JMenuBar();
    mb.add(createMenu);
    mb.add(resetMenu);
    mb.add(refreshMenu);
    mb.add(quitMenu);
    this.setJMenuBar(mb);
    setUp();
  }

  private void setUp() {
    JPanel pane = (JPanel) this.getContentPane();
    pane.setLayout(new FlowLayout());
    this.boardPanel = new BoardPanel(model);
    JScrollPane scrollPane = new JScrollPane(this.boardPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setPreferredSize(new Dimension(420, 420));

    this.add(scrollPane);

    this.statsPanel = new StatsPanel(model);
    this.add(statsPanel);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    this.pack();
    refresh();
  }

  @Override
  public void refresh() {
    repaint();
    if (model.getGameState() != GameState.PLAYING) {
      String msg = String.format("Game over, your final treasure is %d Diamonds, "
                      + "%d Rubies, %d Sapphires, ",
              model.getPlayer().getBag().get(Treasure.DIAMONDS),
              model.getPlayer().getBag().get(Treasure.RUBIES),
              model.getPlayer().getBag().get(Treasure.SAPPHIRES));
      JOptionPane.showMessageDialog(this, msg,
              "Info", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void setFeatures(Features f) {
    this.addKeyListener(new java.awt.event.KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        //Unused.
      }

      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        try {
          if (keyCode == KeyEvent.VK_P) {
            f.pickUp();
          } else if (keyCode == KeyEvent.VK_UP) {
            f.movePlayer(Direction.NORTH);
          } else if (keyCode == KeyEvent.VK_DOWN) {
            f.movePlayer(Direction.SOUTH);
          } else if (keyCode == KeyEvent.VK_RIGHT) {
            f.movePlayer(Direction.EAST);
          } else if (keyCode == KeyEvent.VK_LEFT) {
            f.movePlayer(Direction.WEST);
          } else if (e.isShiftDown()) {
            if (keyCode == KeyEvent.VK_W) {
              displayDialogue(f, Direction.NORTH);
            } else if (keyCode == KeyEvent.VK_S) {
              displayDialogue(f, Direction.SOUTH);
            } else if (keyCode == KeyEvent.VK_D) {
              displayDialogue(f, Direction.EAST);
            } else if (keyCode == KeyEvent.VK_A) {
              displayDialogue(f, Direction.WEST);
            }
          }
        } catch (IllegalStateException err) {
          JOptionPane.showMessageDialog(DungeonViewImpl.this, err.getMessage(),
                  "Error", JOptionPane.ERROR_MESSAGE);
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        //Unused
      }
    });
  }

  @Override
  public void updateViewModel(ReadonlyDungeonModel model) {
    this.model = model;
    boardPanel.updateModel(model);
    statsPanel.updateModel(model);
    this.getContentPane().removeAll();
    setUp();
  }

  @Override
  public void setListener(MvcController listener) {
    addClickListener(listener);
    addCreateClickListener(listener);
    addRefreshClickListener(listener);
    addRestartClickListener(listener);
    addQuitClickListener();
  }


  private void addClickListener(MvcController listener) {
    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        int row = e.getY() / 60;
        int column = e.getX() / 60;
        try {
          listener.handleCellClick(row, column);
        } catch (IllegalStateException err) {
          JOptionPane.showMessageDialog(DungeonViewImpl.this, err.getMessage(),
                  "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    };
    boardPanel.addMouseListener(clickAdapter);
  }

  private void addCreateClickListener(MvcController listener) {

    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        displayOptions(listener);
      }
    };
    createMenu.addMouseListener(clickAdapter);
  }

  private void addRestartClickListener(MvcController listener) {
    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        resetGame(listener);
      }
    };
    resetMenu.addMouseListener(clickAdapter);
  }

  private void addRefreshClickListener(MvcController listener) {
    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        refreshGame(listener);
      }
    };
    refreshMenu.addMouseListener(clickAdapter);
  }

  private void addQuitClickListener() {
    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        System.exit(0);
      }
    };
    quitMenu.addMouseListener(clickAdapter);
  }

  private void displayDialogue(Features f, Direction d) {
    JPanel p = new JPanel(new GridLayout(2, 2, 10, 10));

    p.setPreferredSize(new Dimension(400, 50));

    Integer[] distance = {1, 2, 3, 4, 5};
    JComboBox<Integer> distanceList = new JComboBox<>(distance);
    distanceList.setSelectedIndex(0);
    p.add(distanceList);

    JLabel label = new JLabel("Enter Distance");
    p.add(label);
    Object[] choices = {"Shoot", "Cancel"};
    int option = JOptionPane.showOptionDialog(null, p, "Shoot",
            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
            choices, null);
    if (option == 0) {
      f.shootArrow(d, (Integer) distanceList.getSelectedItem());
    }
  }

  private void displayOptions(MvcController listener) {
    JPanel p = new JPanel();
    p.setLayout(new GridLayout(14, 1));

    JLabel row = new JLabel("Rows");
    p.add(row);
    JTextField rowInput = new JTextField("5", 15);
    p.add(rowInput);

    JLabel col = new JLabel("Columns");
    p.add(col);
    JTextField colInput = new JTextField("5", 15);
    p.add(colInput);

    JLabel name = new JLabel("Player Name");
    p.add(name);
    JTextField nameInput = new JTextField("Rushi", 15);
    p.add(nameInput);

    JLabel wrapping = new JLabel("Wrapping / Non-Wrapping?");
    p.add(wrapping);
    String[] type = {"Wrapping", "Non-Wrapping"};
    JComboBox<String> typeList = new JComboBox<>(type);
    typeList.setSelectedIndex(0);
    p.add(typeList);

    JLabel interconnectivity = new JLabel("Interconnectivity");
    p.add(interconnectivity);
    JTextField interconnectivityInput = new JTextField("5", 15);
    p.add(interconnectivityInput);

    JLabel treasure = new JLabel("Treasure Percent");
    p.add(treasure);
    JTextField treasureInput = new JTextField("50", 15);
    p.add(treasureInput);

    JLabel otyughs = new JLabel("Number of Otyughs");
    p.add(otyughs);
    JTextField otyughsInput = new JTextField("2", 15);
    p.add(otyughsInput);

    Object[] choices = {"Create!", "Cancel"};
    int option = JOptionPane.showOptionDialog(null, p, "Shoot",
            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
            choices, null);
    if (option == 0) {
      try {
        listener.createDungeon((String) typeList.getSelectedItem(),
                Integer.parseInt(rowInput.getText()),
                Integer.parseInt(colInput.getText()),
                Integer.parseInt(interconnectivityInput.getText()),
                Integer.parseInt(treasureInput.getText()),
                nameInput.getText(),
                Integer.parseInt(otyughsInput.getText())
        );
      } catch (IllegalStateException | NumberFormatException err) {
        JOptionPane.showMessageDialog(DungeonViewImpl.this, err.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
      }
    }

  }

  private void resetGame(MvcController listener) {
    List<String> stats = this.model.getStats();
    listener.createDungeon(stats.get(0),
            Integer.parseInt(stats.get(1)),
            Integer.parseInt(stats.get(2)),
            Integer.parseInt(stats.get(3)),
            Integer.parseInt(stats.get(4)),
            stats.get(5),
            Integer.parseInt(stats.get(6)));
  }

  private void refreshGame(MvcController listener) {
    List<String> stats = this.model.getStats();
    List<Integer> randomArgs = this.model.getRandomArgs();
    listener.refreshDungeon(stats.get(0),
            Integer.parseInt(stats.get(1)),
            Integer.parseInt(stats.get(2)),
            Integer.parseInt(stats.get(3)),
            Integer.parseInt(stats.get(4)),
            stats.get(5),
            Integer.parseInt(stats.get(6)),
            randomArgs);
  }

}
