import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import cs5004.animator.model.Action;
import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.IAnimationModel;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.Shape;
import cs5004.animator.model.ViewModel;
import cs5004.animator.view.svg.SVGView;
import cs5004.animator.view.swing.SwingView;
import cs5004.animator.view.text.TextView;
import cs5004.animator.view.View;
import cs5004.animator.view.ViewFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** test ViewFactory. */
public class ViewFactoryTest {

  private ViewFactory factory;
  private String appendTo;

  @Before
  public void setUp() {
    factory = new ViewFactory();
    factory.registerKnown();
    appendTo = "";
  }

  @Test
  public void testCreateVisual() {
    View visual = factory.create("visual");
    assertTrue(visual instanceof SwingView);
  }

  @Test
  public void testCreateText() {
    View text = factory.create("text");
    assertTrue(text instanceof TextView);
    text = factory.create("text", new TestAppendable());
    IAnimationModel model = new AnimationModel();
    model.addShape(new Rectangle("a", new Point2D.Double(0, 0), Color.blue, 1, 1, 1, 2));
    text.setShapeState(((ViewModel) model).getShapes());
    text.setActionMap(((ViewModel) model).getActionMap());
    text.render();
    assertEquals(
        "Create blue rectangle a with corner at (0, 0), width 1 and height 1"
            + System.lineSeparator()
            + System.lineSeparator()
            + "a appears at time t=1 and disappears at time t=2"
            + System.lineSeparator()
            + System.lineSeparator(),
        appendTo);
  }

  @Test
  public void testCreateSVG() {
    View svg = factory.create("svg");
    assertTrue(svg instanceof SVGView);
    svg = factory.create("svg", new TestAppendable());
    IAnimationModel model = new AnimationModel();
    model.addShape(new Rectangle("a", new Point2D.Double(0, 0), Color.blue, 1, 1, 1, 2));
    svg.setShapeState(((ViewModel) model).getShapes());
    svg.setActionMap(((ViewModel) model).getActionMap());
    svg.render();
    assertEquals(
        "<svg width=\"500\" height=\"500\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">"
            + System.lineSeparator()
            + "<rect id=\"a\" x=\"0\" y=\"0\" width=\"1\" height=\"1\""
            + " fill=\"rgb(0,0,255)\" visibility=\"hidden\">"
            + System.lineSeparator()
            + "\t<animate attributeType=\"xml\" attributeName=\"visibility\""
            + " to=\"visible\" begin=\"1000ms\" end=\"2000ms\" />"
            + System.lineSeparator()
            + "</rect>"
            + System.lineSeparator()
            + "</svg>",
        appendTo);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCreate() {
    ViewFactory fac = new ViewFactory();
    fac.create("text");
  }

  @Test(expected = IllegalStateException.class)
  public void testFailedConstructor() {
    factory.register("test", TestView.class);
    factory.create("test");
  }

  @Test(expected = IllegalStateException.class)
  public void testFailedConstructorOneArg() {
    factory.register("test", TestView.class);
    factory.create("test", new StringBuffer());
  }

  private static class TestView implements View {

    public TestView() {
      throw new UnsupportedOperationException();
    }

    public TestView(Appendable oneArg) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void render() {
      // test view needs no functionality.
    }

    @Override
    public void setShapeState(List<Shape> shapes) {
      // test view needs no functionality.
    }

    @Override
    public void setActionMap(Map<String, List<Action>> actionMap) {
      // test view needs no functionality.
    }

    @Override
    public void setAnimationSize(int width, int height) {
      // test view needs no functionality.
    }

    @Override
    public void setOrigin(int x, int y) {
      // test view needs no functionality.
    }

    @Override
    public int getSpeed() {
      return 0;
    }

    @Override
    public void setSpeed(int speed) {
      // test view needs no functionality.
    }
  }

  private class TestAppendable implements Appendable {

    @Override
    public Appendable append(CharSequence csq) throws IOException {
      appendTo += csq;
      return this;
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
      return null;
    }

    @Override
    public Appendable append(char c) throws IOException {
      appendTo += c;
      return this;
    }
  }
}
