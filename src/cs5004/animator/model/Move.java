package cs5004.animator.model;

import java.awt.geom.Point2D;

/** Move action changes Shape origin. */
public class Move extends AbstractAction {

  private final Point2D endOrigin;
  private final Point2D startOrigin;

  /** Constructor for move, taking start and end times, and move to point. */
  public Move(int startTime, int endTime, Point2D startOrigin, Point2D endOrigin) {
    super(startTime, endTime);
    this.startOrigin = (Point2D) startOrigin.clone();
    this.endOrigin = endOrigin;
  }

  @Override
  public Shape apply(Shape shape, int time) {
    shape = super.apply(shape, time);
    if (time < this.startTime) {
      return shape;
    } else if (time > this.endTime) {
      shape.setOrigin(this.endOrigin);
      return shape;
    }
    double totalDistance = shape.getOrigin().distance(this.endOrigin);
    double timeRatio = (time - this.startTime) / (double) (this.endTime - this.startTime);
    double distanceTraveled = totalDistance * timeRatio;
    Vector2D vStart = new Vector2D(shape.getOrigin());
    Vector2D vEnd = new Vector2D(this.endOrigin);
    Vector2D moved = vStart.add(vEnd.subtract(vStart).normalize().multiply(distanceTraveled));
    shape.setOrigin(moved.toPoint());
    return shape;
  }

  @Override
  public String toString() {
    return String.format(
        "moves from (%.0f, %.0f) to (%.0f, %.0f) from t=%d to t=%d",
        this.startOrigin.getX(),
        this.startOrigin.getY(),
        this.endOrigin.getX(),
        this.endOrigin.getY(),
        this.startTime,
        this.endTime);
  }

  @Override
  public Action clone() {
    return new Move(
        this.getStartTime(),
        this.getEndTime(),
        (Point2D) this.startOrigin.clone(),
        (Point2D) this.endOrigin.clone());
  }

  public Point2D getStartOrigin() {
    return this.startOrigin;
  }

  public Point2D getEndOrigin() {
    return this.endOrigin;
  }
}
