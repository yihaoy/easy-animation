package cs5004.animator.model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Shape Reflection Factory. For use when reading input from files/humans to build models with shape
 * objects.
 */
public class ShapeFactory {
  private Map<String, Class> shapeMap;

  /** Blank constructor. */
  public ShapeFactory() {
    this.shapeMap = new HashMap<>();
  }

  /**
   * Register a Shape class.
   *
   * @param shapeType type of shape as a string.
   * @param theClass class of Shape.
   */
  public void register(String shapeType, Class theClass) {
    shapeMap.put(shapeType.toUpperCase(), theClass);
  }

  /**
   * Helper for registering all defined shapes in the model. Add more shapes here if you create them
   * in the model.
   */
  public void registerKnown() {
    this.register("Rectangle", Rectangle.class);
    this.register("Oval", Oval.class);
  }

  /**
   * Create a shape object via reflection.
   *
   * @param shapeType the type of the requested shape.
   * @param name name of the created shape. access with shape.getName().
   * @return Shape with given type and name.
   * @throws IllegalStateException if an issue encountered with making Shape object.
   */
  public Shape create(String shapeType, String name) throws IllegalStateException {
    if (shapeMap.containsKey(shapeType.toUpperCase())) {
      Class aClass = shapeMap.get(shapeType.toUpperCase());
      Constructor[] constructors = aClass.getConstructors();
      for (Constructor c : constructors) {
        if (c.getParameterTypes().length == 1) {
          try {
            return (Shape) c.newInstance(name);
          } catch (Exception e) {
            throw new IllegalStateException(
                String.format("Shape factory failed with exception: %s", e));
          }
        }
      }
    }
    return null;
  }

  /**
   * Create shape with custom arguments to shapes constructor.
   *
   * @param shapeType type of shape.
   * @param name name of shape.
   * @param origin Point2D origin of shape.
   * @param color Color of shape.
   * @param height of shape.
   * @param width of shape.
   * @param appearFrame frame to appear at.
   * @param disappearFrame frame to disappear at.
   * @return Shape object as requested.
   * @throws IllegalStateException if an issue encountered with making Shape object.
   */
  public Shape create(
      String shapeType,
      String name,
      Point2D origin,
      Color color,
      double height,
      double width,
      int appearFrame,
      int disappearFrame)
      throws IllegalStateException {
    if (shapeMap.containsKey(shapeType.toUpperCase())) {
      Class aClass = shapeMap.get(shapeType.toUpperCase());
      Constructor[] constructors = aClass.getConstructors();
      for (Constructor c : constructors) {
        if (c.getParameterTypes().length == 7) {
          try {
            return (Shape)
                c.newInstance(name, origin, color, height, width, appearFrame, disappearFrame);
          } catch (Exception e) {
            throw new IllegalStateException(
                String.format("Shape factory failed with exception: %s", e));
          }
        }
      }
    }
    return null;
  }
}
