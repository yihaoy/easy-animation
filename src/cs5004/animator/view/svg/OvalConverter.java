package cs5004.animator.view.svg;

import cs5004.animator.model.Action;
import cs5004.animator.model.Move;
import cs5004.animator.model.Scale;
import cs5004.animator.model.Shape;

import java.util.List;

class OvalConverter extends AbstractShapeConverter {

  OvalConverter(Shape shape, List<Action> actions, int speed, int x, int y) {
    super(shape, actions, speed, x, y);
    closingTag = "</ellipse>";
  }

  public String convertShape() {
    String color = convertColor(shape.getColor());
    double cx = shape.getOrigin().getX() + (shape.getWidth() / 2) - this.x;
    double cy = shape.getOrigin().getY() + (shape.getHeight() / 2) - this.y;
    double rx = shape.getWidth() / 2;
    double ry = shape.getHeight() / 2;
    return String.format(
        "<ellipse id=\"%s\" cx=\"%.0f\" cy=\"%.0f\" rx=\"%.0f\""
            + " ry=\"%.0f\" fill=\"%s\" visibility=\"hidden\">%s",
        shape.getName(), cx, cy, rx, ry, color, System.lineSeparator());
  }

  public String convertMove(Move move) {
    StringBuilder out = new StringBuilder();
    int startTime = this.getStartTime(move);
    int duration = this.getDuration(move);
    double startCx = move.getStartOrigin().getX() + (shape.getWidth() / 2) - this.x;
    double startCy = move.getStartOrigin().getY() + (shape.getHeight() / 2) - this.y;
    double endCx = move.getEndOrigin().getX() + (shape.getWidth() / 2) - this.x;
    double endCy = move.getEndOrigin().getY() + (shape.getHeight() / 2) - this.y;
    out.append(
        String.format(
            "\t<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                + "attributeName=\"cx\" from=\"%.0f\" to=\"%.0f\" fill=\"freeze\" />",
            startTime, duration, startCx, endCx));
    out.append(System.lineSeparator());
    out.append(
        String.format(
            "\t<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                + "attributeName=\"cy\" from=\"%.0f\" to=\"%.0f\" fill=\"freeze\" />",
            startTime, duration, startCy, endCy));
    out.append(System.lineSeparator());
    return out.toString();
  }

  public String convertScale(Scale scale) {
    int startTime = getStartTime(scale);
    int duration = getDuration(scale);
    String dim = scale.getDimension().equalsIgnoreCase("width") ? "rx" : "ry";
    return String.format(
        "\t<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
            + "attributeName=\"%s\" from=\"%.0f\" to=\"%.0f\" fill=\"freeze\" />%s",
        startTime,
        duration,
        dim,
        scale.getStartDimension() / 2,
        scale.getEndDimension() / 2,
        System.lineSeparator());
  }
}
