package cs5004.animator.model;


/**
 * Enum for various kinds of 2D shapes in EasyAnimator.
 * Rectangle : it represent the name of shape for rectangle, which
 * will have one position, width, height, color and visibility.
 * Oval : it represents the name of shape for oval, which
 * will have one position, width, height, color and visibility.
 */
public enum ShapeType {
  RECTANGLE("Rectangle"),
  OVAL("Oval");

  private final String string;

  ShapeType(String string) {
    this.string = string;
  }

  public String toString() {
    return this.string;
  }
}