package cs5004.animator.model;

import java.awt.Color;

class Vector3D {
  private final double x;
  private final double y;
  private final double z;

  Vector3D(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  Vector3D(Color color) {
    this.x = color.getRed();
    this.y = color.getGreen();
    this.z = color.getBlue();
  }

  Vector3D add(Vector3D other) {
    return new Vector3D(
        this.getX() + other.getX(), this.getY() + other.getY(), this.getZ() + other.getZ());
  }

  Vector3D subtract(Vector3D other) {
    return new Vector3D(
        this.getX() - other.getX(), this.getY() - other.getY(), this.getZ() - other.getZ());
  }

  Vector3D multiply(double c) {
    return new Vector3D(c * this.getX(), c * this.getY(), c * this.getZ());
  }

  double getMagnitude() {
    return Math.sqrt(
        this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());
  }

  Vector3D normalize() {
    double mag = this.getMagnitude();
    return new Vector3D(this.getX() / mag, this.getY() / mag, this.getZ() / mag);
  }

  double getX() {
    return this.x;
  }

  double getY() {
    return this.y;
  }

  double getZ() {
    return this.z;
  }

  Color toColor() {
    return new Color((int) this.getX(), (int) this.getY(), (int) this.getZ());
  }
}
