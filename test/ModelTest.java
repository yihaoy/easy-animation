import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.geom.Point2D;

import cs5004.animator.model.Action;
import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.IAnimationModel;
import cs5004.animator.model.Move;
import cs5004.animator.model.Oval;
import cs5004.animator.model.Recolor;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.Scale;
import cs5004.animator.model.Shape;
import cs5004.animator.model.ViewModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/** Test Model Interface. */
public class ModelTest {

  private Shape testShape1;
  private Shape testShape2;
  private IAnimationModel model;

  @Before
  public void setUp() {
    testShape1 =
        new Rectangle("rect", new Point2D.Double(0, 0), new Color(0, 0, 0), 21, 16.473, 5, 10);
    testShape2 = new Oval("o", new Point2D.Double(5, 53.55), new Color(255, 0, 67), 10, 10, 1, 100);

    model = new AnimationModel();
  }

  @Test
  public void testAddShape() {
    model.addShape(testShape1);
    Shape shape1 = ((ViewModel) model).getShapes().get(0);
    assertEquals(
        "red rectangle rect with corner at (0, 0), width 16 and height 21", shape1.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddDuplicateShape() {
    model.addShape(testShape1);
    model.addShape(
        new Oval(testShape1.getName(), new Point2D.Double(1, 1), new Color(1, 1, 1), 1, 1, 1, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAddActionMove() {
    model.addShape(testShape1);
    Action move = new Move(5, 15, testShape1.getOrigin(), new Point2D.Double(5, 5));
    Action moveOverlap = new Move(14, 17, testShape1.getOrigin(), new Point2D.Double(15, 2));
    model.addAction("rect", move);
    model.addAction("rect", moveOverlap);
  }

  @Test
  public void testAllowedOverlapAddAction() {
    model.addShape(testShape1);
    Action move = new Move(5, 15, testShape1.getOrigin(), new Point2D.Double(5, 5));
    Action scaleOverlap = new Scale(14, 17, 6, 10, "width");
    model.addAction("rect", move);
    model.addAction("rect", scaleOverlap);
    assertEquals(move.toString(), ((ViewModel) model).getActionMap().get("rect").get(0).toString());
    assertEquals(scaleOverlap.toString(), ((ViewModel) model).getActionMap().get("rect").get(1).toString());
  }

  @Test
  public void testDeleteShape() {
    model.addShape(testShape1);
    model.addShape(testShape2);
    model.deleteShape("o");
    assertFalse(((ViewModel) model).getShapes().contains("o"));
    assertFalse(((ViewModel) model).getActionMap().containsKey("o"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidShapeNameInDelete() {
    model.addShape(testShape1);
    model.addShape(testShape2);
    model.deleteShape("omg");
  }

  @Test
  public void testAddAction() {
    Action move = new Move(5, 10, testShape2.getOrigin(), new Point2D.Double(10, 10));
    Action recolor = new Recolor(6, 9, testShape2.getColor(), new Color(100, 100, 100));
    model.addShape(testShape2);
    model.addAction("o", move);
    model.addAction("o", recolor);

    assertEquals(
        "moves from (5, 54) to (10, 10) from t=5 to t=10",
            ((ViewModel) model).getActionMap().get("o").get(0).toString());
    assertEquals(
        "changes color from red to red from t=6 to t=9",
            ((ViewModel) model).getActionMap().get("o").get(1).toString());
  }

  @Test
  public void testGetShape() {
    model.addShape(testShape1);
    assertEquals(
        "red rectangle rect with corner at (0, 0), width 16 and height 21",
            ((ViewModel) model).getShapes().get(0).toString());

    model.addShape(testShape2);
    assertEquals(
        "red oval o with center at (5, 54), radius 10 and 10", ((ViewModel) model).getShapes().get(1).toString());
  }

  @Test
  public void testGetActionMap() {
    Action move = new Move(5, 10, testShape2.getOrigin(), new Point2D.Double(10, 10));
    Action recolor = new Recolor(6, 9, testShape2.getColor(), new Color(100, 100, 100));
    model.addShape(testShape2);
    model.addAction("o", move);
    model.addAction("o", recolor);

    assertTrue(((ViewModel) model).getActionMap().containsKey("o"));
    assertEquals(
        "moves from (5, 54) to (10, 10) from t=5 to t=10",
            ((ViewModel) model).getActionMap().get("o").get(0).toString());
    assertEquals(
        "changes color from red to red from t=6 to t=9",
            ((ViewModel) model).getActionMap().get("o").get(1).toString());
  }

  @Test
  public void testGetShapeState() {
    model.addShape(testShape1);
    model.addShape(testShape2);
    assertFalse(((ViewModel) model).getShapeState(5).contains(testShape1));
    assertFalse(((ViewModel) model).getShapeState(1).contains(testShape2));
    assertEquals(testShape1.toString(), ((ViewModel) model).getShapeState(100).get(0).toString());
    assertEquals(testShape2.toString(), ((ViewModel) model).getShapeState(100).get(1).toString());

    assertEquals(testShape1.isVisible(), ((ViewModel) model).getShapeState(1).get(0).isVisible());
    assertNotEquals(testShape1.isVisible(), ((ViewModel) model).getShapeState(7).get(0).isVisible());
    assertEquals(testShape1.isVisible(), ((ViewModel) model).getShapeState(15).get(0).isVisible());

    Action move = new Move(10, 20, testShape2.getOrigin(), new Point2D.Double(10, 10));
    Action hScale = new Scale(1, 500, 5, 20.912, "height");
    Action recolor = new Recolor(5, 10, testShape1.getColor(), new Color(100, 100, 100));
    model.addAction("o", move);
    model.addAction("rect", recolor);
    model.addAction("o", hScale);
    assertEquals(20.912, ((ViewModel) model).getShapeState(501).get(1).getHeight(), .01);
    assertEquals(new Color(59, 59, 59), ((ViewModel) model).getShapeState(8).get(0).getColor());
    assertEquals(new Color(100, 100, 100), ((ViewModel) model).getShapeState(50).get(0).getColor());
    assertEquals(new Point2D.Double(7.5, 31.775), ((ViewModel) model).getShapeState(15).get(1).getOrigin());
    assertEquals(new Point2D.Double(10, 10), ((ViewModel) model).getShapeState(20).get(1).getOrigin());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFrame() {
    ((ViewModel) model).getShapeState(-1);
  }

  @Test
  public void testGetSetBounds() {
    model.setBounds(1, 1, 1, 1);
    assertEquals(1, ((ViewModel) model).getX());
    assertEquals(1, ((ViewModel) model).getY());
    assertEquals(1, ((ViewModel) model).getHeight());
    assertEquals(1, ((ViewModel) model).getWidth());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalWidth() {
    model.setBounds(-1, -1, -1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalHeight() {
    model.setBounds(-1, -1, 1, -1);
  }

  @Test
  public void testGetEndTime() {
    ViewModel viewModel = new AnimationModel();
    assertEquals(1, viewModel.getEndTime());
    model.addShape(testShape1);
    model.addShape(testShape2);
    assertEquals(100, ((ViewModel) model).getEndTime());
  }
}
