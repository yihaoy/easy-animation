package cs5004.animator.view;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import cs5004.animator.view.svg.SVGView;
import cs5004.animator.view.swing.CompositeView;
import cs5004.animator.view.swing.SwingView;
import cs5004.animator.view.text.TextView;

/** View Reflection Factory. */
public class ViewFactory {
  private final Map<String, Class> viewMap;

  /** Blank constructor. */
  public ViewFactory() {
    this.viewMap = new HashMap<>();
  }

  /**
   * Register a View class in the factory.
   *
   * @param viewType string representation of view type.
   * @param theClass the class of the view to add.
   */
  public void register(String viewType, Class theClass) {
    viewMap.put(viewType.toLowerCase(), theClass);
  }

  /** Add known views, convenience function. */
  public void registerKnown() {
    this.register("svg", SVGView.class);
    this.register("visual", SwingView.class);
    this.register("text", TextView.class);
    this.register("playback", CompositeView.class);
  }

  /**
   * Create a View using its blank constructor.
   *
   * @param viewType type of view to make.
   * @return View associated with viewType string.
   * @throws IllegalStateException if instantiating View fails.
   * @throws IllegalArgumentException if no given viewType is known.
   */
  public View create(String viewType) throws IllegalStateException, IllegalArgumentException {
    if (viewMap.containsKey(viewType.toLowerCase())) {
      Class aClass = viewMap.get(viewType.toLowerCase());
      Constructor[] constructors = aClass.getConstructors();
      for (Constructor c : constructors) {
        if (c.getParameterTypes().length == 0) {
          try {
            return (View) c.newInstance();
          } catch (Exception e) {
            throw new IllegalStateException(
                String.format("View factory failed with exception: %s", e));
          }
        }
      }
    }
    throw new IllegalArgumentException("No view in factory with name " + viewType);
  }

  /**
   * Create a View that takes an Appendable.
   *
   * @param viewType type of view to make.
   * @param output Appendable to use as argument to View constructor.
   * @return View associated with viewType string.
   * @throws IllegalStateException if instantiating View fails.
   * @throws IllegalArgumentException if no given viewType is known.
   */
  public View create(String viewType, Appendable output)
      throws IllegalArgumentException, IllegalStateException {
    if (viewMap.containsKey(viewType.toLowerCase())) {
      Class aClass = viewMap.get(viewType.toLowerCase());
      Constructor[] constructors = aClass.getConstructors();
      for (Constructor c : constructors) {
        if (c.getParameterTypes().length == 1) {
          try {
            return (View) c.newInstance(output);
          } catch (Exception e) {
            throw new IllegalStateException(
                String.format("View factory failed with exception: %s", e));
          }
        }
      }
    }
    throw new IllegalArgumentException("No view in factory with name " + viewType);
  }
}
