package cs5004.animator.model;

import java.awt.Color;
import java.awt.geom.Point2D;

/** Interface for Shapes in Animation. */
public interface Shape {

  /**
   * Return a String representation of the Shape with format: " Name: [name] Type: [type] Min
   * corner: [origin], Width: [width], Height: [height], Color: [color in rgb] ".
   *
   * @return String representation of Shape.
   */
  String toString();

  /**
   * Return the name of the shape.
   *
   * @return name of Shape object.
   */
  String getName();

  /**
   * Get the shape type of this shape.
   *
   * @return the type of this shape
   */
  ShapeType getType();

  /**
   * Get visibility setting of Shape.
   *
   * @return true if visible, false otherwise.
   */
  boolean isVisible();

  /**
   * Set visibility of Shape.
   *
   * @param vis value to set visibility too. True if visible, false otherwise.
   */
  void setVisible(boolean vis);

  /**
   * Update visibility based on internal appearAt and disappearAt fields.
   *
   * @param frame to update visibility at.
   */
  void updateVisibility(int frame); // TODO: test!

  /**
   * Get the frame that the Shape appears at.
   *
   * @return frame that shape appears.
   */
  int getAppearFrame();

  /**
   * Set the appear frame of the shape.
   *
   * @param frame for shape to appear at.
   */
  void setAppearFrame(int frame);

  /**
   * Get the frame that the Shape disappears at.
   *
   * @return frame that shape disappears.
   */
  int getDisappearFrame();

  /**
   * Set the Disappearance frame of the shape.
   *
   * @param frame for shape to disappear at.
   */
  void setDisappearFrame(int frame);

  /**
   * Get origin point of the Shape.
   *
   * @return Point2D representing the origin of the Shape.
   */
  Point2D getOrigin();

  /**
   * Set the origin of the Shape.
   *
   * @param point to use as origin.
   */
  void setOrigin(Point2D point);

  /**
   * Get the color of the Shape.
   *
   * @return Color object representing color of the shape.
   */
  Color getColor();

  /**
   * Set the color of the Shape.
   *
   * @param color Color to set.
   */
  void setColor(Color color);

  /**
   * return a clone of this Shape.
   *
   * @return clone of this Shape.
   */
  Shape clone();

  /**
   * Gets the height.
   *
   * @return the height value.
   */
  double getHeight();

  /**
   * Set the height.
   *
   * @param h to set height too.
   */
  void setHeight(double h);

  /**
   * Gets the width.
   *
   * @return the width value.
   */
  double getWidth();

  /**
   * Set the width.
   *
   * @param w to set width too.
   */
  void setWidth(double w);
}
