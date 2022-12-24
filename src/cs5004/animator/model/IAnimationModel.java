package cs5004.animator.model;

/** Interface for mutating Animation Model. */
public interface IAnimationModel {

  /**
   * Add a shape to the model.
   *
   * @param shape to add to model.
   * @throws IllegalArgumentException if shape with name is already present.
   */
  void addShape(Shape shape) throws IllegalArgumentException;

  /**
   * Add an action operating on Shape with shapeName.
   *
   * @param shapeName Shape to apply action too.
   * @param action to add to model.
   * @throws IllegalArgumentException if shapeName not in model.
   */
  void addAction(String shapeName, Action action) throws IllegalArgumentException;

  /**
   * Remove shape and all related actions from model. If shapeName is not present, an
   * IllegalArgumentException is thrown.
   *
   * @param shapeName name of Shape to remove from model.
   * @throws IllegalArgumentException if shape is not in model.
   */
  void deleteShape(String shapeName) throws IllegalArgumentException;

  /**
   * Set the bounds of the animations bounding box.
   *
   * @param x leftmost x value.
   * @param y topmost y value.
   * @param width width of bounding box.
   * @param height height of bounding box.
   * @throws IllegalArgumentException if width or height is less than 1.
   */
  void setBounds(int x, int y, int width, int height) throws IllegalArgumentException;
}
