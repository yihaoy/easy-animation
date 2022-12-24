package cs5004.animator.view;

/** Encodes all implemented View/PlayableViews */
public enum ViewType {
  VISUAL("visual"),
  SVG("svg"),
  TEXT("text"),
  PLAYBACK("playback");

  private final String string;

  ViewType(String string) {
    this.string = string;
  }

  /**
   * String representation of ViewType. Matches the command line arguments at the main entry point.
   *
   * @return String representation of view type.
   */
  public String toString() {
    return this.string;
  }
}
