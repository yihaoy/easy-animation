package cs5004.animator.model;

import java.awt.*;
import java.awt.geom.Point2D;

import cs5004.animator.util.ColorUtil;

/** Rectangle Implementation of Shape class. */
public class Rectangle extends AbstractShape {

  /**
   * Nearly blank constructor, includes only name for reference.
   *
   * @param name of Shape.
   */
  public Rectangle(String name) {
    super(name, null, null, 0, 0, 1, 2);
    this.setAppearFrame(Integer.MAX_VALUE);
    this.setDisappearFrame(-Integer.MAX_VALUE);
    this.type = ShapeType.RECTANGLE;
  }

  /** Constructor with all required fields. */
  public Rectangle(
      String name,
      Point2D origin,
      Color color,
      double height,
      double width,
      int appearFrame,
      int disappearFrame) {
    super(name, origin, color, height, width, appearFrame, disappearFrame);
    this.type = ShapeType.RECTANGLE;
  }

  @Override
  public Shape clone() {
    Shape newShape = new Rectangle(this.getName());
    newShape.setOrigin(this.getOrigin());
    newShape.setColor(this.getColor());
    newShape.setHeight(this.getHeight());
    newShape.setWidth(this.getWidth());
    newShape.setAppearFrame(this.getAppearFrame());
    newShape.setDisappearFrame(this.getDisappearFrame());
    newShape.setVisible(this.isVisible());
    return newShape;
  }

  @Override
  public String toString() {
    return String.format(
        "%s rectangle %s with corner at (%.0f, %.0f), width %.0f and height %.0f",
        new ColorUtil().colorName(this.getColor()),
        this.getName(),
        this.getOrigin().getX(),
        this.getOrigin().getY(),
        this.getWidth(),
        this.getHeight());
  }
}
