package cs5004.animator.model;

import java.awt.geom.Point2D;

class Vector2D {
  private final double x;
  private final double y;

  Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  Vector2D(Point2D point) {
    this.x = point.getX();
    this.y = point.getY();
  }

  Vector2D add(Vector2D other) {
    return new Vector2D(this.getX() + other.getX(), this.getY() + other.getY());
  }

  Vector2D subtract(Vector2D other) {
    return new Vector2D(this.getX() - other.getX(), this.getY() - other.getY());
  }

  Vector2D multiply(double c) {
    return new Vector2D(c * this.getX(), c * this.getY());
  }

  double getMagnitude() {
    return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY());
  }

  Vector2D normalize() {
    double mag = this.getMagnitude();
    return new Vector2D(this.getX() / mag, this.getY() / mag);
  }

  double getX() {
    return this.x;
  }

  double getY() {
    return this.y;
  }

  Point2D toPoint() {
    return new Point2D.Double(this.getX(), this.getY());
  }
}
