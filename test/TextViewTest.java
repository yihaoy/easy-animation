import org.junit.Before;
import org.junit.Test;

import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.IAnimationModel;
import cs5004.animator.model.ViewModel;
import cs5004.animator.util.AnimationBuilderImpl;
import cs5004.animator.view.View;
import cs5004.animator.view.text.TextView;

import static org.junit.Assert.assertEquals;

/** Tests for class TextView. */
public class TextViewTest {

  private IAnimationModel model;
  private AnimationBuilderImpl builder;
  private View view;
  private Appendable output;

  @Before
  public void setUp() {
    model = new AnimationModel();
    builder = new AnimationBuilderImpl(model);
    output = new StringBuffer();
    view = new TextView(output);
  }

  @Test
  public void testGetText1() {
    this.builder
        .declareShape("r", "Rectangle")
        .addMotion("r", 1, 10, 10, 10, 30, 0, 255, 0, 5, 20, 10, 10, 30, 0, 255, 0)
        .addMotion("r", 5, 20, 10, 10, 30, 0, 255, 0, 10, 20, 20, 10, 30, 0, 255, 0);
    this.model = this.builder.build();
    view.setShapeState(((ViewModel) model).getShapes());
    view.setActionMap(((ViewModel) model).getActionMap());
    view.render();
    assertEquals(
        "Create green rectangle r with corner at (10, 10), width 10 and height 30"
            + System.lineSeparator()
            + System.lineSeparator()
            + "r appears at time t=1 and disappears at time t=10"
            + System.lineSeparator()
            + System.lineSeparator()
            + "r moves from (10, 10) to (20, 10) from t=1 to t=5"
            + System.lineSeparator()
            + "r moves from (20, 10) to (20, 20) from t=5 to t=10",
        output.toString());
  }

  // Null
  @Test(expected = NullPointerException.class)
  public void testNull() {
    view.render();
  }
}
