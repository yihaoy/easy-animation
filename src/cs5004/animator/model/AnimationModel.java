package cs5004.animator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Implementation of Animation Model Interface. */
public class AnimationModel implements IAnimationModel, ViewModel {
  private final List<Shape> shapes;
  private final Map<String, List<Action>> actions;
  private int x;
  private int y;
  private int width;
  private int height;
  private int endTime;

  /** Blank constructor. */
  public AnimationModel() {
    this.shapes = new ArrayList<>();
    this.actions = new HashMap<>();
    this.x = 1;
    this.y = 1;
    this.width = 1;
    this.height = 1;
    this.endTime = 1;
  }

  /**
   * Constructor that takes size of Animation canvas.
   *
   * @param x left most x value.
   * @param y rightmost y value.
   * @param width of animation bounding box.
   * @param height of animation bounding box.
   */
  public AnimationModel(int x, int y, int width, int height) {
    this();
    this.setBounds(x, y, width, height);
  }

  @Override
  public void addShape(Shape shape) {
    this.shapes.forEach(
        s -> {
          if (s.getName().equals(shape.getName())) {
            throw new IllegalArgumentException(
                String.format("shapeName %s already in model", shape.getName()));
          }
        });
    this.shapes.add(shape);
    this.actions.put(shape.getName(), new ArrayList<>());
    this.updateEndTime();
  }

  @Override
  public void addAction(String shapeName, Action action) throws IllegalArgumentException {
    if (this.shapes.stream().noneMatch(shape -> shape.getName().equals(shapeName))) {
      throw new IllegalArgumentException(String.format("Shape with name %s not found", shapeName));
    }
    this.actions
        .get(shapeName)
        .forEach(
            a -> {
              if (a.overlap(action)) {
                throw new IllegalArgumentException(
                    String.format(
                        "Cannot add overlapping actions: \n %s \n %s",
                        action.toString(), a));
              }
            });
    this.actions.get(shapeName).add(action);
  }

  @Override
  public void deleteShape(String shapeName) throws IllegalArgumentException {
    boolean found = false;
    for (Shape shape : this.shapes) {
      if (shape.getName().equals(shapeName)) {
        this.shapes.remove(shape);
        found = true;
        break;
      }
    }
    if (!found) {
      throw new IllegalArgumentException(
          String.format("Shape with name %s is not found", shapeName));
    }
    this.actions.remove(shapeName);
  }

  @Override
  public List<Shape> getShapeState(int frame) throws IllegalArgumentException {
    if (frame < 1) {
      throw new IllegalArgumentException("frame cannot be less than 1.");
    }
    return shapes.stream()
        .map(
            shape -> {
              shape = shape.clone();
              shape.updateVisibility(frame);
              for (Action action : this.actions.get(shape.getName())) {
                shape = action.apply(shape, frame);
              }
              return shape;
            })
        .collect(Collectors.toList());
  }

  @Override
  public List<Shape> getShapes() {
    return shapes.stream().map(Shape::clone).collect(Collectors.toList());
  }

  @Override
  public Map<String, List<Action>> getActionMap() {
    Map<String, List<Action>> copy = new HashMap<>();
    for (String key : this.actions.keySet()) {
      copy.put(key, this.actions.get(key).stream().map(Action::clone).collect(Collectors.toList()));
    }
    return copy;
  }

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public void setBounds(int x, int y, int w, int h) throws IllegalArgumentException {
    if (w < 1 || h < 1) {
      throw new IllegalArgumentException("Width and height must be positive.");
    }
    this.x = x;
    this.y = y;
    this.width = w;
    this.height = h;
  }

  // TODO: test me
  @Override
  public int getEndTime() {
    return this.endTime;
  }

  private void updateEndTime() {
    this.getShapes()
        .forEach(
            shape -> this.endTime = Math.max(this.endTime, shape.getDisappearFrame()));
  }
}
