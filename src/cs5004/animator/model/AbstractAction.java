package cs5004.animator.model;

/** An abstract class for the Action interface and its classes. */
public abstract class AbstractAction implements Action {
  protected final int startTime;
  protected final int endTime;

  /**
   * An abstract constructor for actions in terms of a shape model and a interval.
   *
   * @param startTime the start time of this action.
   * @param endTime the end time of this action.
   * @throws IllegalArgumentException if startTime !< endTime
   */
  protected AbstractAction(int startTime, int endTime) throws IllegalArgumentException {
    if (startTime < 1 || endTime < 1) {
      throw new IllegalArgumentException("startTime and endTime must be positive");
    }
    if (endTime <= startTime) {
      throw new IllegalArgumentException("startTime must be less than endTime");
    }
    this.startTime = startTime;
    this.endTime = endTime;
  }

  @Override
  public Shape apply(Shape shape, int time) {
    if (time < 1) {
      throw new IllegalArgumentException("time cannot be negative");
    }
    return shape.clone();
  }

  @Override
  public int getStartTime() {
    return this.startTime;
  }

  @Override
  public int getEndTime() {
    return this.endTime;
  }

  @Override
  public boolean overlap(Action other) {
    if (this.getClass() != other.getClass()) {
      return false;
    }

    return (this.getEndTime() > other.getStartTime()) && (this.getStartTime() < other.getEndTime());
  }

  @Override
  public Action clone() {
    return null;
  }
}
