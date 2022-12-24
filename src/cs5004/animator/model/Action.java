package cs5004.animator.model;

/** Interface for Actions taken on Shapes. */
public interface Action {

  /**
   * Apply the actions operation to a given Shape at the given time int. If the input time is less
   * than the Actions startTime, or greater than the Actions endTime, then no modification is made
   * to its Shape. If the input time is within the Actions time bounds, the Shape is updated to
   * reflect its state at that time while the Action is taking place.
   *
   * @param shape Shape object to apply action too.
   * @param time int of the animation.
   * @return Shape reflecting changes.
   */
  Shape apply(Shape shape, int time);

  /**
   * Return a String representation of the Action object, with the format: "[takes action] to [end
   * state] from t=[startTime] to t=[endTime]."
   *
   * @return String representation of Action object.
   */
  String toString();

  /**
   * Get the endTime of the Action.
   *
   * @return int endTime of Action.
   */
  int getStartTime();

  /**
   * Get the startTime of the Action.
   *
   * @return int startTime of Action.
   */
  int getEndTime();

  /**
   * Determine if this Action overlaps with other Action. Overlap is defined as having the same
   * Action type and overlapping startTime - endTime ranges.
   *
   * @return true if overlaps, false otherwise.
   */
  boolean overlap(Action other);

  /**
   * Return a clone of this Action.
   *
   * @return clone of this action.
   */
  Action clone(); // TODO : test
}
