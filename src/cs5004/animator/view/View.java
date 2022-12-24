package cs5004.animator.view;

import java.util.List;
import java.util.Map;

import cs5004.animator.model.Action;
import cs5004.animator.model.Shape;

/** Interface for Animation Model. */
public interface View {

  /** Signal the view to draw itself. */
  void render();

  /**
   * Provide the view with the current state of all Shapes.
   *
   * @param shapes List of Shapes.
   */
  void setShapeState(List<Shape> shapes);

  /**
   * Provide the view with a map of shapeName to {@code List<Action>}.
   *
   * @param actionMap to set inside view.
   */
  void setActionMap(Map<String, List<Action>> actionMap);

  /**
   * Set the width and height of the animation window. (for rendering visual views)
   *
   * @param width of animation window
   * @param height of animation window
   */
  void setAnimationSize(int width, int height);

  /**
   * Set the origin coordinates (upper left hand corner of canvas).
   *
   * @param x left most coordinate.
   * @param y upper most coordinate.
   */
  void setOrigin(int x, int y);

  /**
   * return the speed of this view.
   *
   * @return speed of this view.
   */
  int getSpeed();

  /**
   * The speed in frames per second.
   *
   * @param speed to set the view too.
   */
  void setSpeed(int speed);
}
