import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.IAnimationModel;
import cs5004.animator.model.ViewModel;
import cs5004.animator.view.PlayableView;
import cs5004.animator.view.swing.CompositeView;
import org.junit.Before;
import org.junit.Test;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** Test CompositeView. */
public class CompositeViewTest {
  private PlayableView cView;

  @Before
  public void setUp() {
    IAnimationModel model = new AnimationModel();
    cView = new CompositeView((ViewModel) model, 1);
  }

  @Test
  public void testSpeed() {
    assertEquals(1, cView.getSpeed());
    cView.setSpeed(10);
    assertEquals(10, cView.getSpeed());
    cView.setSpeed(-1);
    assertEquals(1, cView.getSpeed());
  }

  @Test
  public void testChangeListener() {
    // this tests the implementation a little too tightly, I think.
    ChangeListener view = (ChangeListener) cView;
    MockChangeEvent mock = new MockChangeEvent(new Object());
    view.stateChanged(mock);
    assertTrue(mock.interacted);
  }

  private static class MockChangeEvent extends ChangeEvent {
    public boolean interacted = false;

    public MockChangeEvent(Object source) {
      super(source);
    }

    public JSlider getSource() {
      return new MockSlider();
    }

    private class MockSlider extends JSlider {
      public int getValue() {
        interacted = true;
        return 1;
      }
    }
  }
}
