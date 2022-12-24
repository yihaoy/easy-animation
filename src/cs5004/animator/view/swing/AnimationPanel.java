package cs5004.animator.view.swing;

import cs5004.animator.model.Shape;
import cs5004.animator.model.ShapeType;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import javax.swing.JPanel;

class AnimationPanel extends JPanel {
  private List<Shape> shapes;
  private int x;
  private int y;

  AnimationPanel() {
    super();
    this.setOrigin(0, 0);
  }

  public void setShapeState(List<Shape> shapes) {
    this.shapes = shapes;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;
    shapes.forEach(shape -> paintShape(g2d, shape));
  }

  public void setOrigin(int x, int y) {
    this.x = x;
    this.y = y;
  }

  private void paintShape(Graphics2D g2d, Shape shape) {
    if (!shape.isVisible()) {
      return;
    }
    g2d.setPaint(shape.getColor());
    if (shape.getType() == ShapeType.RECTANGLE) {
      g2d.fill(
          new Rectangle2D.Double(
              shape.getOrigin().getX() - this.x,
              shape.getOrigin().getY() - this.y,
              shape.getWidth(),
              shape.getHeight()));
    } else if (shape.getType() == ShapeType.OVAL) {
      g2d.fill(
          new Ellipse2D.Double(
              shape.getOrigin().getX() - this.x,
              shape.getOrigin().getY() - this.y,
              shape.getWidth(),
              shape.getHeight()));
    }
  }
}
