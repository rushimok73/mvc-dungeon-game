package view;


import dungeon.Cave;
import dungeon.ReadonlyDungeonModel;
import dungeon.Treasure;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Class displaying the player and cave information on screen.
 * This class displays inventory, cave contents and events in game.
 * This class is esentially an information panel.
 */
public class StatsPanel extends JPanel {
  private ReadonlyDungeonModel model;
  private int height;
  private BufferedImage arrow;
  private BufferedImage ruby;
  private BufferedImage sapphire;
  private BufferedImage diamond;

  /**
   * Constructor.
   * @param model Takes a readonly model.
   */
  public StatsPanel(ReadonlyDungeonModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model is null");
    }
    this.model = model;
    setupStats();
    cacheImages();
  }

  private void cacheImages() {
    try {
      this.arrow = ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/arrow-white.png")));
      this.diamond = ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/diamond.png")));
      this.ruby = ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/ruby.png")));
      this.sapphire = ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/emerald.png")));
    } catch (Exception e) {
      System.out.println("error reading image");
      System.exit(0);
    }

  }

  private void setupStats() {
    this.height = 420;
    this.setPreferredSize(new Dimension(250, 420));
    this.setBackground(Color.black);
  }

  void updateModel(ReadonlyDungeonModel model) {
    this.model = model;
    setupStats();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

    Map<Treasure, Integer> hm = model.getPlayer().getBag();
    g2d.setColor(Color.WHITE);
    g2d.drawString("Inventory", (float) (this.getWidth() / 2 - 50),
            (float) (this.height * 0.05));
    g2d.drawImage(diamond, 30, (int) (this.height * 0.1), this);
    g2d.drawString(hm.get(Treasure.DIAMONDS).toString(), 60, (int) (this.height * 0.1) + 25);
    g2d.drawImage(ruby, 95, (int) (this.height * 0.1), this);
    g2d.drawString(hm.get(Treasure.RUBIES).toString(), 125, (int) (this.height * 0.1) + 25);
    g2d.drawImage(sapphire, 160, (int) (this.height * 0.1),
            (int) (sapphire.getWidth() * 0.8), (int) (sapphire.getHeight() * 0.8), this);
    g2d.drawString(hm.get(Treasure.SAPPHIRES).toString(), 190,
            (int) (this.height * 0.1) + 25);
    g2d.drawImage(arrow, (this.getWidth() / 2 - 30), (int) (this.height * 0.22), this);
    g2d.drawString(hm.get(Treasure.ARROWS).toString(), (this.getWidth() / 2 - 30) + 50,
            (int) (this.height * 0.22) + 15);

    g2d.drawLine(0, (int) (this.height * 0.33),
            this.getWidth(), (int) (this.height * 0.33));

    Cave c = model.getAllCaves().get(model.getPlayerX()).get(model.getPlayerY());
    Map<Treasure, Integer> hmCave = c.getBag();
    g2d.drawString("Cave Items", (float) (this.getWidth() / 2 - 50),
            (float) (this.height * 0.4));
    g2d.drawImage(diamond, 30, (int) (this.height * 0.45), this);
    g2d.drawString(hmCave.get(Treasure.DIAMONDS).toString(), 60,
            (int) (this.height * 0.45) + 25);
    g2d.drawImage(ruby, 95, (int) (this.height * 0.45), this);
    g2d.drawString(hmCave.get(Treasure.RUBIES).toString(), 125,
            (int) (this.height * 0.45) + 25);
    g2d.drawImage(sapphire, 160, (int) (this.height * 0.45),
            (int) (sapphire.getWidth() * 0.8), (int) (sapphire.getHeight() * 0.8), this);
    g2d.drawString(hmCave.get(Treasure.SAPPHIRES).toString(), 190,
            (int) (this.height * 0.45) + 25);
    g2d.drawImage(arrow, (this.getWidth() / 2 - 30), (int) (this.height * 0.57), this);
    g2d.drawString(hmCave.get(Treasure.ARROWS).toString(), (this.getWidth() / 2 - 30) + 50,
            (int) (this.height * 0.57) + 15);

    g2d.drawLine(0, (int) (this.height * 0.66),
            this.getWidth(), (int) (this.height * 0.66));

    g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    displayArrowStat(g2d);
    g2d.drawString(model.getGameState().toString(), 10, (float) (this.height * 0.9));

  }

  private void displayArrowStat(Graphics2D g2d) {
    g2d.drawString(this.model.getWhatHappened().toString(), 10, (int) (this.height * 0.8));
  }
}
