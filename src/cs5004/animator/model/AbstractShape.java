package cs5004.animator.model;

import java.awt.Color;
import java.awt.geom.Point2D;

/** An abstract class for the Action interface. */
public abstract class AbstractShape implements Shape {
  protected String name;
  protected ShapeType type;
  protected Point2D origin;
  protected Color color;
  protected boolean visible;
  protected double width;
  protected double height;
  private int disappearFrame;
  private int appearFrame;

  /**
   * Constructor that takes appear/disappear frames.
   *
   * @param name name
   * @param origin the origin position of the Shape
   * @param color the current color of the shape
   * @param height height of shape
   * @param width width of shape
   * @param appearFrame frame that shape appears at.
   * @param disappearFrame frame that shape disappears at
   */
  protected AbstractShape(
      String name,
      Point2D origin,
      Color color,
      double height,
      double width,
      int appearFrame,
      int disappearFrame) {
    this.name = name;
    this.origin = origin;
    this.color = color;
    this.height = height;
    this.width = width;
    if (appearFrame < 1 || appearFrame >= disappearFrame) {
      throw new IllegalArgumentException("appear/disappear must be valid.");
    }
    this.appearFrame = appearFrame;
    this.disappearFrame = disappearFrame;
    this.setVisible(false);
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public ShapeType getType() {
    return this.type;
  }

  @Override
  public boolean isVisible() {
    return this.visible;
  }

  @Override
  public void setVisible(boolean vis) {
    this.visible = vis;
  }

  @Override
  public void updateVisibility(int frame) {
    this.setVisible(this.getAppearFrame() <= frame && frame < this.getDisappearFrame());
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public void setColor(Color given) {
    color = given;
  }

  @Override
  public Point2D getOrigin() {
    return this.origin;
  }

  @Override
  public void setOrigin(Point2D point) {
    origin = point;
  }

  @Override
  public Shape clone() {
    return null;
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  @Override
  public void setWidth(double w) {
    this.width = w;
  }

  @Override
  public double getHeight() {
    return this.height;
  }

  @Override
  public void setHeight(double h) {
    this.height = h;
  }

  @Override
  public int getDisappearFrame() {
    return this.disappearFrame;
  }

  @Override
  public void setDisappearFrame(int frame) {
    this.disappearFrame = frame;
  }

  @Override
  public int getAppearFrame() {
    return this.appearFrame;
  }

  @Override
  public void setAppearFrame(int frame) {
    this.appearFrame = frame;
  }

  @Override
  public String toString() {
    return String.format(
        "Name: %s, Type: %s, Min corner: (%.2f, %.2f), "
            + "Height: %.2f, Width: %.2f, Color: (%d, %d, %d)",
        this.getName(),
        this.getType().toString(),
        this.getOrigin().getX(),
        this.getOrigin().getY(),
        this.getHeight(),
        this.getWidth(),
        this.getColor().getRed(),
        this.getColor().getGreen(),
        this.getColor().getBlue());
  }
}
