package cs5004.animator.model;

import cs5004.animator.util.ColorUtil;

import java.awt.Color;
import java.awt.geom.Point2D;

/** Oval implementation of Shape class. */
public class Oval extends AbstractShape {

  /**
   * Nearly blank constructor, only includes name for reference.
   *
   * @param name of shape.
   */
  public Oval(String name) {
    super(name, null, null, 0, 0, 1, 2);
    this.setAppearFrame(Integer.MAX_VALUE);
    this.setDisappearFrame(-Integer.MAX_VALUE);
    this.type = ShapeType.OVAL;
  }

  /** Constructs an oval with all the fields except for transitions. */
  public Oval(
      String name,
      Point2D origin,
      Color color,
      double height,
      double width,
      int appearFrame,
      int disappearFrame) {
    super(name, origin, color, height, width, appearFrame, disappearFrame);

    this.type = ShapeType.OVAL;
  }

  @Override
  public Shape clone() {
    Shape newShape = new Oval(this.getName());
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
        "%s oval %s with center at (%.0f, %.0f), radius %.0f and %.0f",
        new ColorUtil().colorName(this.getColor()),
        this.getName(),
        this.getOrigin().getX(),
        this.getOrigin().getY(),
        this.getWidth(),
        this.getHeight());
  }
}
