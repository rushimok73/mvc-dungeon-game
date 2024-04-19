package view;

import dungeon.Cave;
import dungeon.Movement;
import dungeon.ReadonlyDungeonModel;
import dungeon.SmellEnum;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * A Panel Class for Displaying the dungeon. The player can move inside this panel
 * by using various key and mouse clicks.
 */
public class BoardPanel extends JPanel {
  private ReadonlyDungeonModel model;
  private int width;
  private int height;
  private BufferedImage player;
  private BufferedImage otyugh;
  private BufferedImage faintSmell;
  private BufferedImage strongSmell;
  private BufferedImage thief;
  private BufferedImage pit;
  private BufferedImage crack;
  private BufferedImage black;
  private Map<String, BufferedImage> caves;

  /**
   * Constructor for board panel.
   * @param model Read only dungeon model.
   */
  public BoardPanel(ReadonlyDungeonModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model is null");
    }
    this.model = model;
    setupBoard();
    cacheImages();
  }

  private void cacheImages() {
    try {
      this.player = ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/player.png")));
      this.otyugh = ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/otyugh.png")));
      this.faintSmell = ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/stench01.png")));
      this.strongSmell = ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/stench02.png")));
      this.thief = ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/thief.png")));
      this.pit = ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/pit.png")));
      this.crack = ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/crack.png")));
      this.black = ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/black.png")));
      this.caves = new HashMap<>();
      caves.put("N", ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/N.png"))));
      caves.put("S", ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/S.png"))));
      caves.put("E", ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/E.png"))));
      caves.put("W", ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/W.png"))));
      caves.put("NE", ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/NE.png"))));
      caves.put("NS", ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/NS.png"))));
      caves.put("NW", ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/NW.png"))));
      caves.put("EW", ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/EW.png"))));
      caves.put("SE", ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/SE.png"))));
      caves.put("SW", ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/SW.png"))));
      caves.put("SEW", ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/SEW.png"))));
      caves.put("NEW", ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/NEW.png"))));
      caves.put("NSW", ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/NSW.png"))));
      caves.put("NSE", ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/NSE.png"))));
      caves.put("NSEW", ImageIO.read(Objects.requireNonNull(
              getClass().getResource("/dungeon-images-bw/NSEW.png"))));

    } catch (IOException e) {
      System.out.println("Error reading image");
      System.exit(0);
    }
  }

  private void setupBoard() {
    int rows = model.getAllCaves().size();
    int cols = model.getAllCaves().get(0).size();
    this.width = 60 * cols;
    this.height = 60 * rows;
    this.setPreferredSize(new Dimension(width, height));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setFont(new Font("Times New Roman", Font.PLAIN, 30));

    int rows = model.getAllCaves().size();
    int columns = model.getAllCaves().get(0).size();

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        Cave c = model.getAllCaves().get(i).get(j);

        Movement m = c.getPossibleMoves();
        StringBuilder pic = new StringBuilder();
        if (m.getNorth()) {
          pic.append("N");
        }

        if (m.getSouth()) {
          pic.append("S");
        }

        if (m.getEast()) {
          pic.append("E");
        }

        if (m.getWest()) {
          pic.append("W");
        }

        BufferedImage imgBg;
        BufferedImage imgFg;
        imgBg = copyImage(this.caves.get(pic.toString()));

        // otyugh and player
        if (i == model.getPlayerX() && j == model.getPlayerY()) {
          imgFg = this.player;
          imageOverlay(imgBg, imgFg);
        }

        if (c.getCrack()) {
          imgFg = this.crack;
          imageOverlay(imgBg, imgFg);
        }
        if (c.hasOtyugh()) {
          imgFg = this.otyugh;
          imageOverlay(imgBg, imgFg);
        }

        if (c.hasThief()) {
          imgFg = this.thief;
          imageOverlay(imgBg, imgFg);
        }

        if (c.hasPit()) {
          imgFg = this.pit;
          imageOverlay(imgBg, imgFg);
        }

        if (c.getSmell() == SmellEnum.LESS_PUNGENT) {
          imgFg = this.faintSmell;
          imageOverlay(imgBg, imgFg);
        }
        if (c.getSmell() == SmellEnum.MORE_PUNGENT) {
          imgFg = this.strongSmell;
          imageOverlay(imgBg, imgFg);
        }

        if (!c.getVisited()) {
          imgBg = this.black;
        }

        g2d.drawImage(imgBg, j * (width / columns), i * (height / rows), this);
      }
    }
  }

  void updateModel(ReadonlyDungeonModel model) {
    this.model = model;
    setupBoard();
  }

  private void imageOverlay(BufferedImage imgBg, BufferedImage imgFg) {
    Graphics2D g2D = (Graphics2D) imgBg.getGraphics();
    int fgWidth = (int) (imgFg.getWidth() * 0.707);
    int fgHeight = (int) (imgFg.getHeight() * 0.707);
    g2D.drawImage(imgFg, imgBg.getWidth() / 2 - fgWidth / 2,
            imgBg.getHeight() / 2 - fgHeight / 2, fgWidth, fgHeight, null);
    g2D.dispose();
  }

  private BufferedImage copyImage(BufferedImage source) {
    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
    Graphics2D g2d = b.createGraphics();
    g2d.drawImage(source, 0, 0, null);
    g2d.dispose();
    return b;
  }
}
