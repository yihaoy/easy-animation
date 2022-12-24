package cs5004.animator.model;

/** Scale Action scales a Shapes height or width. */
public class Scale extends AbstractAction {

  private final double startDimension;
  private final double endDimension;
  private final String dimension;

  /** Constructor takes start/end time, start/end dimension, and dimension to change as String. */
  public Scale(
      int startTime, int endTime, double startDimension, double endDimension, String dimension)
      throws IllegalArgumentException {
    super(startTime, endTime);
    if (startDimension <= 0 || endDimension <= 0) {
      throw new IllegalArgumentException("Dimension cannot be scaled to negative.");
    }
    if (!(dimension.equalsIgnoreCase("width") || dimension.equalsIgnoreCase("height"))) {
      throw new IllegalArgumentException(
          String.format("Dimension must be width or height, not %s", dimension));
    }
    this.startDimension = startDimension;
    this.endDimension = endDimension;
    this.dimension = dimension;
  }

  @Override
  public Shape apply(Shape shape, int time) {
    shape = super.apply(shape, time);
    if (time < this.startTime) {
      return shape;
    } else if (time > this.endTime) {
      return applyHelper(shape, time, this.endDimension);
    }
    double totalChange = this.endDimension - this.startDimension;
    double timeRatio = (time - this.startTime) / (double) (this.endTime - this.startTime);
    double setTo = timeRatio * totalChange + this.startDimension;
    return applyHelper(shape, time, setTo);
  }

  private Shape applyHelper(Shape shape, int time, double setTo) {
    if (this.dimension.equalsIgnoreCase("width")) {
      shape.setWidth(setTo);
    } else if (this.dimension.equalsIgnoreCase("height")) {
      shape.setHeight(setTo);
    }
    return shape;
  }

  @Override
  public String toString() {
    return String.format(
        "scales %s from %.0f to %.0f from t=%d to t=%d",
        this.dimension, this.startDimension, this.endDimension, this.startTime, this.endTime);
  }

  @Override
  public Action clone() {
    return new Scale(
        this.getStartTime(),
        this.getEndTime(),
        this.startDimension,
        this.endDimension,
        this.dimension);
  }

  public double getStartDimension() {
    return this.startDimension;
  }

  public double getEndDimension() {
    return this.endDimension;
  }

  public String getDimension() {
    return this.dimension;
  }
}
