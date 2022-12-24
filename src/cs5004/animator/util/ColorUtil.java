package cs5004.animator.util;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/** Util for managing Colors. */
public class ColorUtil {

  /**
   * Get the name of the color closest to the given color. Known colors are listed in initColors()
   *
   * @param color to compute name of.
   * @return computed name of color.
   */
  public String colorName(Color color) {
    Map<String, Color> colorMap = this.initColors();
    return computeClosest(color, colorMap);
  }

  private String computeClosest(Color color, Map<String, Color> nameMap) {
    String closest = null;
    double minDistance = Double.MAX_VALUE;
    for (String name : nameMap.keySet()) {
      double d = rgbDistance(color, nameMap.get(name));
      if (d < minDistance) {
        minDistance = d;
        closest = name;
      }
    }
    return closest;
  }

  /**
   * Compute the distance between colors.
   *
   * @param startColor color as starting point.
   * @param endColor color as ending point.
   * @return euclidean distance between startColor and endColor as points in 3D space.
   */
  public static double rgbDistance(Color startColor, Color endColor) {
    int x1 = startColor.getRed();
    int y1 = startColor.getGreen();
    int z1 = startColor.getBlue();
    int x2 = endColor.getRed();
    int y2 = endColor.getGreen();
    int z2 = endColor.getBlue();
    return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
  }

  private Map<String, Color> initColors() {
    Map<String, Color> out = new HashMap<>();
    out.put("green", Color.green);
    out.put("red", Color.red);
    out.put("blue", Color.blue);
    return out;
  }
}
