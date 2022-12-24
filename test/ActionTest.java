import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.geom.Point2D;

import cs5004.animator.model.Action;
import cs5004.animator.model.Move;
import cs5004.animator.model.Oval;
import cs5004.animator.model.Recolor;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.Scale;
import cs5004.animator.model.Shape;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/** Test Action Interface. */
public class ActionTest {
  private Shape testShape1;
  private Shape testShape2;

  @Before
  public void setUp() {
    testShape1 =
        new Rectangle("rect", new Point2D.Double(0, 0), new Color(0, 0, 0), 21, 16.473, 5, 10);
    testShape2 = new Oval("o", new Point2D.Double(5, 53.55), new Color(255, 0, 67), 10, 10, 1, 100);
  }

  @Test
  public void testMove() {
    Action move =
        new Move(5, 10, (Point2D) testShape1.getOrigin().clone(), new Point2D.Double(10, 10));
    assertEquals(new Point2D.Double(0, 0), move.apply(testShape1, 3).getOrigin());
    assertEquals(new Point2D.Double(4, 4), move.apply(testShape1, 7).getOrigin());
    assertEquals(new Point2D.Double(8, 8), move.apply(testShape1, 9).getOrigin());
    assertEquals(new Point2D.Double(10, 10), move.apply(testShape1, 110).getOrigin());
  }

  @Test
  public void testRecolor() {
    Action recolor = new Recolor(5, 10, testShape1.getColor(), new Color(100, 100, 100));
    assertEquals(new Color(0, 0, 0), recolor.apply(testShape1, 3).getColor());
    assertEquals(new Color(59, 59, 59), recolor.apply(testShape1, 8).getColor());
    assertEquals(new Color(100, 100, 100), recolor.apply(testShape1, 20).getColor());

    recolor = new Recolor(5, 10, testShape2.getColor(), new Color(100, 100, 100));
    assertEquals(new Color(255, 0, 67), recolor.apply(testShape2, 3).getColor());
    assertEquals(new Color(193, 40, 80), recolor.apply(testShape2, 7).getColor());
    assertEquals(new Color(162, 60, 86), recolor.apply(testShape2, 8).getColor());
    assertEquals(new Color(100, 100, 100), recolor.apply(testShape2, 15).getColor());
  }

  @Test
  public void testScale() {
    Action wScale = new Scale(5, 10, 1, 10, "width");
    assertEquals(16.473, wScale.apply(testShape1, 3).getWidth(), .01);
    assertEquals(1, wScale.apply(testShape1, 5).getWidth(), .01);
    assertEquals(6.39, wScale.apply(testShape1, 8).getWidth(), .01);
    assertEquals(10, wScale.apply(testShape1, 10).getWidth(), .01);

    Action hScale = new Scale(5, 10, 10, 1, "height");
    assertEquals(10, hScale.apply(testShape2, 3).getHeight(), .01);
    assertEquals(10, hScale.apply(testShape2, 5).getHeight(), .01);
    assertEquals(4.6, hScale.apply(testShape2, 8).getHeight(), .01);
    assertEquals(2.8, hScale.apply(testShape2, 9).getHeight(), .01);
    assertEquals(1, hScale.apply(testShape2, 10).getHeight(), .01);
    assertEquals(1, hScale.apply(testShape2, 100).getHeight(), .01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDimensionString() {
    new Scale(100, 101, 1, 20, "goble");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStartDimension() {
    new Scale(1, 10, -5, 15, "width");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidEndDimension() {
    new Scale(1, 10, 5, -5, "height");
  }

  @Test
  public void testReturnsNewShape() {
    Action recolor = new Recolor(5, 10, testShape1.getColor(), new Color(0, 0, 0));
    assertNotEquals(testShape1, recolor.apply(testShape1, 1));
    Action scale = new Scale(5, 100, 100, 1, "width");
    assertNotEquals(testShape1, scale.apply(testShape1, 1));
    Action move =
        new Move(10, 20, (Point2D) testShape1.getOrigin().clone(), new Point2D.Double(10, 10));
    assertNotEquals(testShape1, move.apply(testShape1, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNegativeTime() {
    new Move(-1, -6, new Point2D.Double(0, 0), new Point2D.Double(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTimes() {
    new Recolor(10, 5, new Color(1, 1, 1), new Color(10, 10, 10));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTimeInRecolorApply() {
    Action recolor = new Recolor(50, 100, Color.black, new Color(255, 255, 255));
    recolor.apply(testShape1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTimeInMoveApply() {
    Action move = new Move(1, 10, testShape2.getOrigin(), new Point2D.Double(100000, 100000));
    move.apply(testShape2, -5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTimeInScaleApply() {
    Action scale = new Scale(1, 10, 5, 20, "width");
    scale.apply(testShape2, -10);
  }

  @Test
  public void testOverlap() {
    Action move = new Move(5, 15, new Point2D.Double(0, 0), new Point2D.Double(5, 5));
    Action moveOverlap = new Move(14, 17, new Point2D.Double(0, 0), new Point2D.Double(15, 2));
    assertTrue(move.overlap(moveOverlap));
    assertTrue(moveOverlap.overlap(move));

    Action scaleOverlap = new Scale(14, 17, 6, 10, "width");
    assertFalse(move.overlap(scaleOverlap));
    assertFalse(scaleOverlap.overlap(move));
  }

  @Test
  public void testToString() {
    Action recolor = new Recolor(5, 10, new Color(255, 0, 0), new Color(0, 255, 0));
    assertEquals("changes color from red to green from t=5 to t=10", recolor.toString());
    Action wScale = new Scale(5, 100, 100, 1, "width");
    assertEquals("scales width from 100 to 1 from t=5 to t=100", wScale.toString());
    Action hScale = new Scale(1, 500, 5, 20.912, "height");
    assertEquals("scales height from 5 to 21 from t=1 to t=500", hScale.toString());
    Action move = new Move(10, 20, new Point2D.Double(0, 0), new Point2D.Double(10, 10));
    assertEquals("moves from (0, 0) to (10, 10) from t=10 to t=20", move.toString());
  }

  @Test
  public void testMoveGetters() {
    Point2D start = new Point2D.Double(5, 5);
    Point2D end = new Point2D.Double(0, 0);
    Move move = new Move(1, 10, start, end);
    assertEquals(start, move.getStartOrigin());
    assertEquals(end, move.getEndOrigin());
  }

  @Test
  public void testScaleGetters() {
    double startDimension = 123.41234;
    double endDimension = 9;
    Scale scale = new Scale(1, 5, startDimension, endDimension, "height");
    assertEquals(startDimension, scale.getStartDimension(), .01);
    assertEquals(endDimension, scale.getEndDimension(), .01);
  }

  @Test
  public void testRecolorGetters() {
    Color startColor = Color.gray;
    Color endColor = Color.blue;

    Recolor recolor = new Recolor(1, 12341234, startColor, endColor);
    assertEquals(startColor, recolor.getStartColor());
    assertEquals(endColor, recolor.getEndColor());
  }

  @Test
  public void testGetTimes() {
    Action recolor = new Recolor(1, 12341234, Color.gray, Color.blue);
    Action scale = new Scale(1, 5, 123.41234, 9, "height");
    Action move = new Move(1, 10, new Point2D.Double(5, 5), new Point2D.Double(0, 0));

    assertEquals(1, recolor.getStartTime());
    assertEquals(12341234, recolor.getEndTime());
    assertEquals(1, scale.getStartTime());
    assertEquals(5, scale.getEndTime());
    assertEquals(1, move.getStartTime());
    assertEquals(10, move.getEndTime());
  }

  @Test
  public void testClone() {
    Action recolor = new Recolor(1, 12341234, Color.gray, Color.blue);
    Action scale = new Scale(1, 5, 123.41234, 9, "height");
    Action move = new Move(1, 10, new Point2D.Double(5, 5), new Point2D.Double(0, 0));

    Action moveClone = move.clone();
    assertNotEquals(moveClone, move);
    assertEquals(move.toString(), moveClone.toString());

    Action scaleClone = scale.clone();
    assertNotEquals(scaleClone, scale);
    assertEquals(scale.toString(), scaleClone.toString());

    Action recolorClone = recolor.clone();
    assertNotEquals(recolorClone, recolor);
    assertEquals(recolor.toString(), recolorClone.toString());
  }
}
