import cs5004.animator.view.PlayableView;
import java.security.PublicKey;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import cs5004.animator.model.Action;
import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.IAnimationModel;
import cs5004.animator.model.Move;
import cs5004.animator.model.Oval;
import cs5004.animator.model.Recolor;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.Scale;
import cs5004.animator.model.ViewModel;
import cs5004.animator.view.View;
import cs5004.animator.controller.IController;
import cs5004.animator.controller.Controller;

import static org.junit.Assert.assertEquals;

/** Test controller. */
public class ControllerTest {
  private IController controller;
  private PlayableView view;
  private IAnimationModel model;

  @Before
  public void setup() {
    model = new AnimationModel();
    this.view = view;
    this.controller = new Controller(this.model, this.view);

  }


  @Test
  public void TestGo() {


  }


  @Test
  public void TestActionPerformed() {


  }

  @Test
  public void TestStateChanged() {

  }
}
