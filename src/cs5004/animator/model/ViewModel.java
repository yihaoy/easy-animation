package cs5004.animator.model;

import java.util.List;
import java.util.Map;

/** Interface for accessing information about the model. (for PULLing info into a View). */
public interface ViewModel {
  /**
   * Return a List of Shape objects for a specific frame number. The Shape state reflects any
   * Action(s) that have been taken. Returns a set of shape clones.
   *
   * @param frame number for Animation state.
   * @return List of Shapes specific to that frame.
   * @throws IllegalArgumentException if frame less than 1.
   */
  List<Shape> getShapeState(int frame) throws IllegalArgumentException;

  /**
   * Return list of copies of all Shapes in Model.
   *
   * @return list of copies of Shapes.
   */
  List<Shape> getShapes();

  /**
   * Return a copy of the shapeName to Action list map in model.
   *
   * @return Map of String to list of Actions.
   */
  Map<String, List<Action>> getActionMap();

  /**
   * Get the leftmost x value for the animations bounding box.
   *
   * @return leftmost x value of animation canvas.
   */
  int getX();

  /**
   * Get the topmost value for the animations bounding box.
   *
   * @return topmost y value.
   */
  int getY();

  /**
   * Get the width of the animations bounding box.
   *
   * @return width of animation canvas.
   */
  int getWidth();

  /**
   * Get the height of the animations bounding box.
   *
   * @return height of the animation canvas.
   */
  int getHeight();

  /**
   * Get the ending frame number for this animation. Automatically updated as Shapes are added.
   *
   * @return int end time of animation.
   */
  int getEndTime();
}
