package cs5004.animator.view.svg;

import cs5004.animator.model.Action;
import cs5004.animator.model.Move;
import cs5004.animator.model.Recolor;
import cs5004.animator.model.Scale;
import cs5004.animator.model.Shape;

import java.awt.Color;
import java.util.List;

/** Utility for converting Shapes/Actions to SVG elements. Package private. */
abstract class AbstractShapeConverter {
  protected final Shape shape;
  protected final int speed;
  protected int x;
  protected int y;
  protected List<Action> actions;
  protected String closingTag;

  AbstractShapeConverter(Shape shape, List<Action> actions, int speed, int x, int y) {
    this.shape = shape;
    this.actions = actions;
    this.speed = speed;
    this.x = x;
    this.y = y;
  }

  String convert() {
    StringBuilder out = new StringBuilder();
    out.append(this.convertShape());
    out.append(this.convertVisible());
    actions.forEach(
        action -> {
          if (action.getClass() == Recolor.class) {
            out.append(this.convertRecolor((Recolor) action));
          } else if (action.getClass() == Move.class) {
            out.append(this.convertMove((Move) action));
          } else if (action.getClass() == Scale.class) {
            out.append(this.convertScale((Scale) action));
          }
        });
    out.append(closingTag).append(System.lineSeparator());
    return out.toString();
  }

  protected String convertVisible() throws IllegalStateException {
    int startTime;
    int endTime;
    try {
      startTime = 1000 / this.speed * shape.getAppearFrame();
      endTime = 1000 / this.speed * shape.getDisappearFrame();
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format(
              "Speed, appear, and disappear frames must not be 0,"
                  + " speed: %d appear: %d disappear: %d",
              this.speed, shape.getAppearFrame(), shape.getDisappearFrame()));
    }
    return String.format(
        "\t<animate attributeType=\"xml\" attributeName=\"visibility\" "
            + "to=\"visible\" begin=\"%dms\" end=\"%sms\" />%s",
        startTime, endTime, System.lineSeparator());
  }

  protected String convertColor(Color color) {
    return String.format("rgb(%d,%d,%d)", color.getRed(), color.getGreen(), color.getBlue());
  }

  protected String convertRecolor(Recolor recolor) {
    int startTime = this.getStartTime(recolor);
    int duration = this.getDuration(recolor);
    String startColor = convertColor(recolor.getStartColor());
    String endColor = convertColor(recolor.getEndColor());
    return String.format(
        "\t<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
            + "attributeName=\"fill\" from=\"%s\" to=\"%s\" fill=\"freeze\" />%s",
        startTime, duration, startColor, endColor, System.lineSeparator());
  }

  protected int getStartTime(Action action) {
    try {
      return 1000 / this.speed * action.getStartTime();
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format(
              "Speed and action start time must not be zero, speed: %d action start time: %d",
              speed, action.getStartTime()));
    }
  }

  protected int getDuration(Action action) {
    try {
      return 1000 / this.speed * action.getEndTime() - this.getStartTime(action);
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format(
              "Speed and end time must not be zero, speed: %d endtime: %d",
              speed, action.getEndTime()));
    }
  }

  public abstract String convertMove(Move move);

  public abstract String convertScale(Scale scale);

  public abstract String convertShape();
}
