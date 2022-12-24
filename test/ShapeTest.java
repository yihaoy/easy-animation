import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.geom.Point2D;

import cs5004.animator.model.Oval;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.Shape;
import cs5004.animator.model.ShapeType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

/** Test concrete implementations of Shape interface. */
public class ShapeTest {

  private Shape oval;
  private Shape rectangle;

  // Set up the test cases.
  @Before
  public void setUp() {
    oval = new Oval("o1", new Point2D.Double(5, 10), new Color(10, 255, 1), 10.5, 4.37, 5, 10);
    rectangle =
        new Rectangle("r1", new Point2D.Double(-5, 10), new Color(255, 55, 100), 20, 15, 1, 100);
  }

  @Test
  public void testUpdateVisibility() {
    assertFalse(oval.isVisible());
    oval.updateVisibility(4);
    assertFalse(oval.isVisible());
    oval.updateVisibility(7);
    assertTrue(oval.isVisible());
    oval.updateVisibility(10);
    assertFalse(oval.isVisible());
    oval.updateVisibility(100);
    assertFalse(oval.isVisible());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAppearFrame() {
    new Oval("o1", new Point2D.Double(5, 10), new Color(10, 255, 1), 10.5, 4.37, -6, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDisappearFrame() {
    new Oval("o1", new Point2D.Double(5, 10), new Color(10, 255, 1), 10.5, 4.37, 6, -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAppearAndDisappearFrames() {
    new Oval("o1", new Point2D.Double(5, 10), new Color(10, 255, 1), 10.5, 4.37, -6, -10);
  }

  // Test getName method.
  @Test
  public void testGetName() {
    assertEquals("o1", oval.getName());
    assertEquals("r1", rectangle.getName());
  }

  // TestGetOrigin, getX, getY method.
  @Test
  public void testGetOrigin() {
    assertEquals(oval.getOrigin().getX(), 5, 0.01);
    assertEquals(oval.getOrigin().getY(), 10, 0.01);
    assertEquals(rectangle.getOrigin().getX(), -5, 0.01);
    assertEquals(rectangle.getOrigin().getY(), 10, 0.01);
  }

  // Test getColor method.
  @Test
  public void testGetColor() {
    assertEquals(oval.getColor().getRed(), 10, 0.01);
    assertEquals(oval.getColor().getGreen(), 255, 0.01);
    assertEquals(oval.getColor().getBlue(), 1, 0.01);
    assertEquals(rectangle.getColor().getRed(), 255, 0.01);
    assertEquals(rectangle.getColor().getGreen(), 55, 0.01);
    assertEquals(rectangle.getColor().getBlue(), 100, 0.01);
  }

  // Test getType method().
  @Test
  public void testGetType() {
    assertEquals(oval.getType(), ShapeType.OVAL);
    assertEquals(rectangle.getType(), ShapeType.RECTANGLE);
  }

  // Test toString method which will generate the shapestate info.
  @Test
  public void testToString() {
    assertEquals(
        "red rectangle r1 with corner at (-5, 10), width 15 and height 20", rectangle.toString());

    assertEquals("green oval o1 with center at (5, 10), radius 4 and 11", oval.toString());
  }

  // Test getWidth method.
  @Test
  public void testGetWidth() {
    assertEquals(oval.getWidth(), 4.37, 0.001);
    assertEquals(rectangle.getWidth(), 15, 0.001);
  }

  // Test the getHeight method.
  @Test
  public void testGetHeight() {
    assertEquals(oval.getHeight(), 10.5, 0.001);
    assertEquals(rectangle.getHeight(), 20, 0.001);
  }

  // Test the clone method.
  @Test
  public void testClone() {
    Shape rectangle2 = rectangle.clone();
    assertEquals(
        "red rectangle r1 with corner at (-5, 10), width 15 and height 20", rectangle2.toString());
  }

  // Test illegalShapeType.
  @Test
  public void illegalShapeType() {
    assertNotSame(oval.getType(), ShapeType.RECTANGLE);
    assertNotSame(rectangle.getType(), ShapeType.OVAL);
  }

  // Test illegalColor input.
  @Test(expected = IllegalArgumentException.class)
  public void illegalShapeColor() {
    Shape rectangle2 =
        new Rectangle("r2", new Point2D.Double(-5, 10), new Color(-1, 155, 100), 20, 15, 5, 10);
    assertEquals(rectangle2.getColor().getRed(), -1, 0.01);

    Shape oval2 = new Oval("o2", new Point2D.Double(-5, 10), new Color(0, 0, 555), 20, 15, 4, 10);
    assertEquals(oval2.getColor().getBlue(), 555, 0.01);
  }
}
