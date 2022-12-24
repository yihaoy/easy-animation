package cs5004.animator.view;

import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.event.ChangeListener;

import cs5004.animator.model.ViewModel;
import cs5004.animator.view.swing.FileSaveWrapper;

/** PlayableView extends View, provides interface for adjusting animation as it plays. */
public interface PlayableView extends View {
  /** Start playing. */
  void play();

  /** Stop playing. */
  void pause();

  /** Restart play-through at beginning. Does not play/pause. */
  void restart();

  /** Toggle the looping state. Restarts at beginning if looping. */
  void toggleLoop();

  /**
   * Set the button listeners for buttons in this view.
   *
   * @param actionListener Controller that responds to button presses.
   */
  void setActionListeners(ActionListener actionListener);

  /**
   * Set the change listener for sliders in this view.
   *
   * @param listener to listen for changes.
   */
  void setChangeListeners(ChangeListener listener);

  /**
   * Set the model for this view.
   *
   * @param model for this view to represent.
   */
  void setModel(ViewModel model);

  /** Open a dialogue to get and return a file. */
  File openFile();

  /**
   * Open a dialogue to ask the user what kind of view they would like to save the animation as, get
   * that file and give it to controller with user choice.
   *
   * @return FileSaveWrapper holding user File selection and their view choice type.
   */
  FileSaveWrapper saveFile();

  /**
   * Transmit an error message to the view, in case a command could not be processed correctly.
   *
   * @param error message to pass to view.
   */
  void showErrorMessages(String error);

  /** Make the view visible. */
  void makeVisible();
}
