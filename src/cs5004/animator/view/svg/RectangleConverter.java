package cs5004.animator.view.svg;

import cs5004.animator.model.Action;
import cs5004.animator.model.Move;
import cs5004.animator.model.Scale;
import cs5004.animator.model.Shape;

import java.util.List;

class RectangleConverter extends AbstractShapeConverter {

  RectangleConverter(Shape shape, List<Action> actions, int speed, int x, int y) {
    super(shape, actions, speed, x, y);
    closingTag = "</rect>";
  }

  public String convertShape() {
    String color = convertColor(this.shape.getColor());
    return String.format(
        "<rect id=\"%s\" x=\"%.0f\" y=\"%.0f\" width=\"%.0f\" height=\"%.0f\" "
            + "fill=\"%s\" visibility=\"hidden\">%s",
        shape.getName(),
        shape.getOrigin().getX() - this.x,
        shape.getOrigin().getY() - this.y,
        shape.getWidth(),
        shape.getHeight(),
        color,
        System.lineSeparator());
  }

  public String convertScale(Scale scale) {
    int startTime = this.getStartTime(scale);
    int duration = this.getDuration(scale);
    return String.format(
        "\t<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
            + "attributeName=\"%s\" from=\"%.0f\" to=\"%.0f\" fill=\"freeze\" />%s",
        startTime,
        duration,
        scale.getDimension(),
        scale.getStartDimension(),
        scale.getEndDimension(),
        System.lineSeparator());
  }

  public String convertMove(Move move) {
    int startTime = this.getStartTime(move);
    int duration = this.getDuration(move);
    return String.format(
            "\t<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                + "attributeName=\"x\" from=\"%.0f\" to=\"%.0f\" fill=\"freeze\" />",
            startTime,
            duration,
            move.getStartOrigin().getX() - this.x,
            move.getEndOrigin().getX() - this.x)
        + System.lineSeparator()
        + String.format(
            "\t<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\""
                + " attributeName=\"y\" from=\"%.0f\" to=\"%.0f\" fill=\"freeze\" />",
            startTime,
            duration,
            move.getStartOrigin().getY() - this.y,
            move.getEndOrigin().getY() - this.y)
        + System.lineSeparator();
  }
}
