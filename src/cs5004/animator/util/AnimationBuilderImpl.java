package cs5004.animator.util;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cs5004.animator.model.Action;
import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.IAnimationModel;
import cs5004.animator.model.Move;
import cs5004.animator.model.Oval;
import cs5004.animator.model.Recolor;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.Scale;
import cs5004.animator.model.Shape;
import cs5004.animator.model.ShapeFactory;
import cs5004.animator.model.ViewModel;

/** Implementation of AnimationBuilder interface. */
public class AnimationBuilderImpl implements AnimationBuilder<IAnimationModel> {
  private final IAnimationModel outModel;
  private final List<Shape> shapeStorage;
  private final Map<String, List<Action>> actionStorage;

  /**
   * Constructor, takes IAnimationModel object.
   *
   * @param model model representing animation.
   */
  public AnimationBuilderImpl(IAnimationModel model) {
    this.outModel = new AnimationModel();
    ViewModel viewModel = (ViewModel) model;
    outModel.setBounds(
        viewModel.getX(), viewModel.getY(), viewModel.getWidth(), viewModel.getHeight());
    this.shapeStorage = ((ViewModel) model).getShapes();
    this.actionStorage = ((ViewModel) model).getActionMap();
  }

  @Override
  public IAnimationModel build() {
    this.shapeStorage.forEach(outModel::addShape);
    this.actionStorage.forEach(
        (key, list) -> list.forEach(action -> outModel.addAction(key, action)));
    return this.outModel;
  }

  @Override
  public AnimationBuilder<IAnimationModel> setBounds(int x, int y, int width, int height) {
    this.outModel.setBounds(x, y, width, height);
    return this;
  }

  @Override
  public AnimationBuilder<IAnimationModel> declareShape(String name, String type) {
    ShapeFactory factory = new ShapeFactory();
    factory.register("rectangle", Rectangle.class);
    factory.register("ellipse", Oval.class);
    try {
      Shape shape = factory.create(type, name);
      if (shape == null) {
        throw new IllegalArgumentException("Invalid shape type.");
      }
      shapeStorage.add(shape);
      actionStorage.put(name, new ArrayList<>());
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format("Creating the shape " + "failed with error: %s.", e));
    }
    return this;
  }

  @Override
  public AnimationBuilder<IAnimationModel> addMotion(
      String name,
      int t1,
      int x1,
      int y1,
      int w1,
      int h1,
      int r1,
      int g1,
      int b1,
      int t2,
      int x2,
      int y2,
      int w2,
      int h2,
      int r2,
      int g2,
      int b2) {
    if ((x1 != x2) || (y1 != y2)) {
      actionStorage
          .get(name)
          .add(new Move(t1, t2, new Point2D.Double(x1, y1), new Point2D.Double(x2, y2)));
    }
    if (w1 != w2) {
      actionStorage.get(name).add(new Scale(t1, t2, w1, w2, "width"));
    }
    if (h1 != h2) {
      actionStorage.get(name).add(new Scale(t1, t2, h1, h2, "height"));
    }
    if ((r1 != r2) || (g1 != g2) || (b1 != b2)) {
      actionStorage
          .get(name)
          .add(new Recolor(t1, t2, new Color(r1, g2, b1), new Color(r2, g2, b2)));
    }
    Shape shape = shapeStorage.stream().filter(s -> s.getName().equals(name)).findFirst().get();
    if (t1 < shape.getAppearFrame()) {
      this.setShapeState(shape, t1, x1, y1, w1, h1, r1, g1, b1);
    }
    if (t2 > shape.getDisappearFrame()) {
      shape.setDisappearFrame(t2);
    }
    return this;
  }

  private void setShapeState(Shape shape, int t, int x, int y, int w, int h, int r, int g, int b) {
    shape.setAppearFrame(t);
    shape.setOrigin(new Point2D.Double(x, y));
    shape.setWidth(w);
    shape.setHeight(h);
    shape.setColor(new Color(r, g, b));
  }
}
