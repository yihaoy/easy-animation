package cs5004.animator.view.text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cs5004.animator.model.Action;
import cs5004.animator.model.Shape;
import cs5004.animator.view.View;

/** Text View implementation of View interface. */
public class TextView implements View {
  private final Appendable output;
  private List<Shape> shapes;
  private Map<String, List<Action>> actionMap;

  /** Blank constructor sets output to System.out. */
  public TextView() {
    this.output = System.out;
  }

  /**
   * Constructor takes appendable output.
   *
   * @param output to append model representation.
   */
  public TextView(Appendable output) {
    this.output = output;
  }

  @Override
  public void setShapeState(List<Shape> shapes) {
    this.shapes = shapes;
  }

  @Override
  public void setActionMap(Map<String, List<Action>> actionMap) {
    this.actionMap = actionMap;
  }

  @Override
  public void setAnimationSize(int width, int height) {
    // left unimplemented for factory/ initialization convenience
  }

  @Override
  public void setOrigin(int x, int y) {
    // left unimplemented for factory/ initialization convenience
  }

  @Override
  public int getSpeed() {
    return 0;
  }

  @Override
  public void setSpeed(int speed) {
    // left unimplemented for factory/ initialization convenience
  }

  @Override
  public void render() {
    StringBuilder out = new StringBuilder();
    this.shapes.forEach(
        shape -> out.append("Create ").append(shape.toString()).append(System.lineSeparator()));

    out.append(System.lineSeparator());

    this.shapes.forEach(
        shape ->
            out.append(
                    String.format(
                        "%s appears at time t=%d and disappears at time t=%d",
                        shape.getName(), shape.getAppearFrame(), shape.getDisappearFrame()))
                .append(System.lineSeparator()));

    out.append(System.lineSeparator());

    List<ActionWrapper> congregateActions = new ArrayList<>();
    this.shapes.forEach(
        shape ->
            this.actionMap
                .get(shape.getName())
                .forEach(
                    action -> congregateActions.add(new ActionWrapper(shape.getName(), action))));
    congregateActions.sort(Comparator.comparingInt(aw -> aw.action.getStartTime()));
    out.append(
        congregateActions.stream()
            .map(ActionWrapper::toString)
            .collect(Collectors.joining(System.lineSeparator())));
    try {
      this.output.append(out.toString());
    } catch (java.io.IOException e) {
      throw new IllegalArgumentException("failed to output to given appendable.");
    }
  }

  private static class ActionWrapper {
    String shapeName;
    Action action;

    ActionWrapper(String shapeName, Action action) {
      this.shapeName = shapeName;
      this.action = action;
    }

    public String toString() {
      return String.format("%s %s", this.shapeName, this.action.toString());
    }
  }
}
