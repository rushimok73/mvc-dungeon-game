package test;

import control.Features;
import control.MvcController;
import dungeon.ReadonlyDungeonModel;
import view.DungeonView;

/**
 * Class implemention mock view for testing purposes.
 */
public class MockView implements DungeonView {
  private final Appendable log;

  /**
   * Constructor.
   * @param log Appendable log.
   */
  public MockView(Appendable log) {
    this.log = log;
  }

  @Override
  public void setListener(MvcController listener) {
    try {
      log.append("addListener called").append("\n");
    } catch (Exception ignored) {

    }
  }

  @Override
  public void refresh() {
    try {
      log.append("refresh called").append("\n");
    } catch (Exception ignored) {

    }
  }

  @Override
  public void makeVisible() {
    try {
      log.append("makeVisible called").append("\n");
    } catch (Exception ignored) {

    }
  }

  @Override
  public void setFeatures(Features f) {
    try {
      log.append("setFeatures called").append("\n");
    } catch (Exception ignored) {

    }
  }

  @Override
  public void updateViewModel(ReadonlyDungeonModel model) {
    try {
      log.append("updateViewModel called").append("\n");
    } catch (Exception ignored) {

    }
  }
}
