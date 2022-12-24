package cs5004.animator;

import cs5004.animator.controller.Controller;
import cs5004.animator.controller.IController;
import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.IAnimationModel;
import cs5004.animator.model.ViewModel;
import cs5004.animator.util.AnimationBuilderImpl;
import cs5004.animator.util.AnimationReader;
import cs5004.animator.view.PlayableView;
import cs5004.animator.view.View;
import cs5004.animator.view.ViewFactory;
import cs5004.animator.view.ViewType;

import java.io.Closeable;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/** Entrance point for EasyAnimator Application. */
public class EasyAnimator {
  private static final String[] VIEWS = {"text", "svg"};
  private static final String[] PLAYABLE_VIEWS = {"visual", "playback"};

  /**
   * Main entry point.
   *
   * @param args from command line to be decoded.
   */
  public static void main(String[] args) {
    String viewType = null;
    Readable in = null;
    int speed = 1;
    Appendable out = System.out;
    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-in" -> in = processIn(args[++i]);
        case "-view" -> viewType = processView(args[++i]);
        case "-speed" -> speed = processSpeed(args[++i]);
        case "-out" -> out = processOut(args[++i]);
        default -> throw new IllegalArgumentException(
                String.format(
                        "Unexpected command line arg %s at position %d. "
                                + "Args should come in option - value pairs.",
                        args[i], i));
      }
    }
    if (viewType == null || in == null) {
      throw new IllegalArgumentException("Must include both -view and -in arguments");
    }
    delegateView(in, out, viewType, speed);
  }

  private static Readable processIn(String in) {
    try {
      return new FileReader(in);
    } catch (Exception e) {
      throw new IllegalArgumentException(
          String.format("Failed to read input file %s with error %s", in, e));
    }
  }

  private static String processView(String viewType) {
    List<String>
            allowedViews =
            Arrays.stream(ViewType.values()).map(ViewType::toString).collect(Collectors.toList());
    if (!allowedViews.contains(viewType)) {
      throw new IllegalArgumentException(String.format("Illegal view type: %s", viewType));
    }
    return viewType;
  }

  private static int processSpeed(String speed) {
    try {
      return Integer.parseInt(speed);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Illegal speed: " + speed);
    }
  }

  private static Appendable processOut(String out) {
    try {
      return new FileWriter(out);
    } catch (Exception e) {
      throw new IllegalArgumentException(
          String.format("Invalid out file, failed with error: %s", e));
    }
  }

  private static void delegateView(Readable in, Appendable out, String viewType, int speed) {
    if (Arrays.asList(VIEWS).contains(viewType)) {
      runView(in, out, viewType, speed);
    } else if (Arrays.asList(PLAYABLE_VIEWS).contains(viewType)) {
      runPlayableView(in, speed, viewType);
    }
  }

  private static void runView(Readable in, Appendable out, String viewType, int speed) {
    ViewFactory factory = new ViewFactory();
    factory.registerKnown();
    View view = factory.create(viewType, out);
    ViewModel model = buildModel(in);
    view.setShapeState(model.getShapes());
    view.setActionMap(model.getActionMap());
    view.setSpeed(speed);
    view.setAnimationSize(model.getWidth(), model.getHeight());
    view.setOrigin(model.getX(), model.getY());
    try {
      view.render();
    } catch (Exception e) {
      throw new IllegalStateException(String.format("View failed to render with error %s", e));
    }
    try {
      ((Closeable) out).close();
    } catch (Exception e) {
      throw new IllegalStateException("Couldn't close stream.");
    }
  }

  private static void runPlayableView(Readable in, int speed, String viewType) {
    ViewModel viewModel = buildModel(in);
    ViewFactory factory = new ViewFactory();
    factory.registerKnown();
    PlayableView view = (PlayableView) factory.create(viewType);
    IController controller = new Controller((IAnimationModel) viewModel, view);
    view.setSpeed(speed);
    controller.go();
  }

  private static ViewModel buildModel(Readable in) {
    return (ViewModel)
        AnimationReader.parseFile(in, new AnimationBuilderImpl(new AnimationModel()));
  }
}
