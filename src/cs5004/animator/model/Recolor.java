package cs5004.animator.model;

import cs5004.animator.util.ColorUtil;

import java.awt.Color;

/** Recolor action changes Shape color. */
public class Recolor extends AbstractAction {

  private final Color startColor;
  private final Color endColor;

  /** Constructor takes start/end Time, and Color to change to. */
  public Recolor(int startTime, int endTime, Color startColor, Color endColor) {
    super(startTime, endTime);
    this.endColor = endColor;
    this.startColor = startColor;
  }

  @Override
  public Shape apply(Shape shape, int time) {
    shape = super.apply(shape, time);
    if (time < this.startTime) {
      return shape;
    } else if (time > this.endTime) {
      shape.setColor(this.endColor);
      return shape;
    }
    double timeRatio = (time - this.startTime) / (double) (this.endTime - this.startTime);
    double totalDistance = ColorUtil.rgbDistance(shape.getColor(), this.endColor);
    double distanceTraveled = timeRatio * totalDistance;
    Vector3D vStart = new Vector3D(shape.getColor());
    Vector3D vEnd = new Vector3D(this.endColor);
    Vector3D moved = vStart.add(vEnd.subtract(vStart).normalize().multiply(distanceTraveled));
    shape.setColor(moved.toColor());
    return shape;
  }

  @Override
  public String toString() {
    ColorUtil colorUtil = new ColorUtil();
    return String.format(
        "changes color from %s to %s from t=%d to t=%d",
        colorUtil.colorName(this.startColor),
        colorUtil.colorName(this.endColor),
        this.startTime,
        this.endTime);
  }

  @Override
  public Action clone() {
    return new Recolor(
        this.getStartTime(),
        this.getEndTime(),
        new Color(this.startColor.getRed(), this.startColor.getGreen(), this.startColor.getBlue()),
        new Color(this.endColor.getRed(), this.endColor.getGreen(), this.endColor.getBlue()));
  }

  public Color getStartColor() {
    return this.startColor;
  }

  public Color getEndColor() {
    return this.endColor;
  }
}
