package cs5004.animator.view.svg;

import cs5004.animator.model.Action;
import cs5004.animator.model.Oval;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.Shape;
import cs5004.animator.view.View;

import java.util.List;
import java.util.Map;

/** View implementation that renders model as SVG type xml. */
public class SVGView implements View {
  private final Appendable output;
  private List<Shape> shapes;
  private Map<String, List<Action>> actionMap;
  private int speed;
  private int x;
  private int y;
  private int width;
  private int height;

  /** Blank constructor sets vars to defaults. */
  public SVGView() {
    this.output = System.out;
    this.setDefaults();
  }

  /**
   * Initialize SVGView with output appendable, and default args.
   *
   * @param output Appendable for SVGView to write too.
   */
  public SVGView(Appendable output) {
    this.output = output;
    this.setDefaults();
  }

  private void setDefaults() {
    this.speed = 1;
    this.x = 0;
    this.y = 0;
    this.width = 500;
    this.height = 500;
  }

  @Override
  public void render() {
    writeOut(
        String.format(
            "<svg width=\"%d\" height=\"%d\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">%s",
            this.width, this.height, System.lineSeparator()));
    this.shapes.forEach(this::typeDelegator);
    writeOut("</svg>");
  }

  private void writeOut(String toWrite) {
    try {
      output.append(toWrite);
    } catch (java.io.IOException e) {
      throw new IllegalStateException(String.format("cannot append to output with error %s", e));
    }
  }

  private void typeDelegator(Shape shape) {
    if (shape.getClass() == Rectangle.class) {
      writeOut(
          new RectangleConverter(shape, actionMap.get(shape.getName()), speed, x, y).convert());
    } else if (shape.getClass() == Oval.class) {
      writeOut(new OvalConverter(shape, actionMap.get(shape.getName()), speed, x, y).convert());
    }
  }

  @Override
  public void setShapeState(List<Shape> shapes) {
    this.shapes = shapes;
  }

  @Override
  public void setActionMap(Map<String, List<Action>> actionMap) {
    this.actionMap = actionMap;
  }

  @Override
  public int getSpeed() {
    return this.speed;
  }

  @Override
  public void setSpeed(int speed) {
    this.speed = speed;
  }

  @Override
  public void setAnimationSize(int w, int h) {
    this.width = w;
    this.height = h;
  }

  @Override
  public void setOrigin(int x, int y) {
    this.x = x;
    this.y = y;
  }
}
