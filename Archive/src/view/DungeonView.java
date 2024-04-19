package view;

import control.Features;
import control.MvcController;
import dungeon.ReadonlyDungeonModel;

/**
 * Interface for dungeon view  in MVC.
 * This interface outlines functionality used by controller to manipulate the view.
 */
public interface DungeonView {
  /**
   * Set up the controller as listener in this view.
   *
   * @param listener the controller
   */
  void setListener(MvcController listener);

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * Lets view know about the features to be used by it.
   * @param f Features object.
   */
  void setFeatures(Features f);

  /**
   * Updates model in view.
   * @param model model to be updated.
   */
  void updateViewModel(ReadonlyDungeonModel model);
}
