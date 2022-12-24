import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import cs5004.animator.model.Action;
import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.IAnimationModel;
import cs5004.animator.model.Move;
import cs5004.animator.model.Oval;
import cs5004.animator.model.Recolor;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.Scale;
import cs5004.animator.model.ViewModel;
import cs5004.animator.view.svg.SVGView;
import cs5004.animator.view.View;

import static org.junit.Assert.assertEquals;

/** Test SVGView. */
public class SVGViewTest {

  private IAnimationModel model;
  private View view;
  private Appendable output;
  private Rectangle rect1;
  private Oval oval;
  private Action move;

  @Before
  public void setUp() {
    rect1 =
        new Rectangle("r1", new Point2D.Double(-5, 10), new Color(255, 55, 100), 20, 15, 1, 100);
    oval = new Oval("o1", new Point2D.Double(5, 10), new Color(10, 255, 1), 10.5, 4.37, 5, 10);
    move = new Move(5, 15, rect1.getOrigin(), new Point2D.Double(5, 5));
    output = new StringBuilder();
    view = new SVGView(output);
    model = new AnimationModel();
  }

  // Todo: should we test setOrigin, setAnimationsize too
  @Test
  public void testConstructors() {
    model.addShape(rect1);
    model.addAction("r1", move);
    view.setShapeState(((ViewModel) model).getShapes());
    view.setActionMap(((ViewModel) model).getActionMap());
    assertEquals(1, view.getSpeed());
    View view2 = new SVGView(output);
    view2.setSpeed(2);
    assertEquals(2, view2.getSpeed());

    view.setAnimationSize(300, 300);
    view.render();
    assertEquals(
        "<svg width=\"300\" height=\"300\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">"
            + System.lineSeparator()
            + "<rect id=\"r1\" x=\"-5\" y=\"10\" width=\"15\" height=\"20\" fill=\"rgb(255,55,100)\" visibility=\"hidden\">"
            + System.lineSeparator()
            + "\t<animate attributeType=\"xml\" attributeName=\"visibility\" to=\"visible\" begin=\"1000ms\" end=\"100000ms\" />"
            + System.lineSeparator()
            + "\t<animate attributeType=\"xml\" begin=\"5000ms\" dur=\"10000ms\" attributeName=\"x\" from=\"-5\" to=\"5\" fill=\"freeze\" />"
            + System.lineSeparator()
            + "\t<animate attributeType=\"xml\" begin=\"5000ms\" dur=\"10000ms\" attributeName=\"y\" from=\"10\" to=\"5\" fill=\"freeze\" />"
            + System.lineSeparator()
            + "</rect>"
            + System.lineSeparator()
            + "</svg>",
        output.toString());
  }

  @Test
  public void testGetAppendable1() {
    view.setShapeState(((ViewModel) model).getShapes());
    view.setActionMap(((ViewModel) model).getActionMap());
    view.render();
    assertEquals(
        "<svg width=\"500\" height=\"500\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">"
            + System.lineSeparator()
            + "</svg>",
        output.toString());
  }

  @Test
  public void testGetAppendable2() {
    model.addShape(rect1);
    model.addAction("r1", move);
    view.setShapeState(((ViewModel) model).getShapes());
    view.setActionMap(((ViewModel) model).getActionMap());
    view.render();

    assertEquals(
        "<svg width=\"500\" height=\"500\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">"
            + System.lineSeparator()
            + "<rect id=\"r1\" x=\"-5\" y=\"10\" width=\"15\" height=\"20\""
            + " fill=\"rgb(255,55,100)\" visibility=\"hidden\">"
            + System.lineSeparator()
            + "\t<animate attributeType=\"xml\" attributeName=\"visibility\""
            + " to=\"visible\" begin=\"1000ms\" end=\"100000ms\" />"
            + System.lineSeparator()
            + "\t<animate attributeType=\"xml\" begin=\"5000ms\" dur=\"10000ms\" "
            + "attributeName=\"x\" from=\"-5\" to=\"5\" fill=\"freeze\" />"
            + System.lineSeparator()
            + "\t<animate attributeType=\"xml\" begin=\"5000ms\" dur=\"10000ms\""
            + " attributeName=\"y\" from=\"10\" to=\"5\" fill=\"freeze\" />"
            + System.lineSeparator()
            + "</rect>"
            + System.lineSeparator()
            + "</svg>",
        output.toString());
  }

  @Test
  public void testGetAppendable3() {
    Action scaleOverlap = new Scale(14, 17, 6, 10, "width");
    Action recolor = new Recolor(6, 9, oval.getColor(), new Color(100, 100, 100));
    model.addShape(oval);
    model.addAction("o1", scaleOverlap);
    model.addAction("o1", recolor);
    view.setShapeState(((ViewModel) model).getShapes());
    view.setActionMap(((ViewModel) model).getActionMap());
    view.render();

    assertEquals(
        "<svg width=\"500\" height=\"500\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">"
            + System.lineSeparator()
            + "<ellipse id=\"o1\" cx=\"7\" cy=\"15\" rx=\"2\" ry=\"5\" "
            + "fill=\"rgb(10,255,1)\" visibility=\"hidden\">"
            + System.lineSeparator()
            + "\t<animate attributeType=\"xml\" attributeName=\"visibility\""
            + " to=\"visible\" begin=\"5000ms\" end=\"10000ms\" />"
            + System.lineSeparator()
            + "\t<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"3000ms\" "
            + "attributeName=\"rx\" from=\"3\" to=\"5\" fill=\"freeze\" />"
            + System.lineSeparator()
            + "\t<animate attributeType=\"xml\" begin=\"6000ms\" dur=\"3000ms\" "
            + "attributeName=\"fill\" from=\"rgb(10,255,1)\" "
            + "to=\"rgb(100,100,100)\" fill=\"freeze\" />"
            + System.lineSeparator()
            + "</ellipse>"
            + System.lineSeparator()
            + "</svg>",
        output.toString());
  }
}
