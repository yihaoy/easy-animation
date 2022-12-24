package cs5004.animator.view.swing;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.ChangeListener;

import cs5004.animator.model.Action;
import cs5004.animator.model.Shape;
import cs5004.animator.model.ViewModel;
import cs5004.animator.view.PlayableView;

/** View that represents model animation in javax.swing view. */
public class SwingView extends JFrame implements PlayableView {
  private int speed;
  private final AnimationPanel panel;
  private final JScrollPane scrollPane;
  private ViewModel model;

  /** Blank constructor. */
  public SwingView() {
    super();
    this.setTitle("EasyAnimator");
    this.setSize(1000, 1000); // default size
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout()); // borderlayout with panel in center
    panel = new AnimationPanel();
    scrollPane = new JScrollPane(panel);

    this.add(scrollPane, BorderLayout.CENTER);
    this.pack();
  }

  public SwingView(ViewModel model) {
    this();
    this.setModel(model);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
    this.play();
  }

  @Override
  public void play() {
    int sleepTime = 1000 / this.getSpeed();
    for (int frame = 1; frame < model.getEndTime(); frame++) {
      this.setShapeState(model.getShapeState(frame));
      this.render();
      try {
        Thread.sleep(sleepTime);
      } catch (InterruptedException e) {
        this.showErrorMessages("Animation play interrupted");
      }
    }
  }

  @Override
  public void setModel(ViewModel model) {
    this.model = model;
    this.setOrigin(model.getX(), model.getY());
    this.setAnimationSize(model.getWidth(), model.getHeight());
  }

  @Override
  public void render() {
    this.repaint();
  }

  @Override
  public void setShapeState(List<Shape> shapes) {
    panel.setShapeState(shapes);
  }

  @Override
  public int getSpeed() {
    return this.speed;
  }

  @Override
  public void setSpeed(int speed) {
    this.speed = speed;
  }

  @Override
  public void showErrorMessages(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void setOrigin(int x, int y) {
    this.panel.setOrigin(x, y);
  }

  @Override
  public void setAnimationSize(int width, int height) {
    Dimension size = new Dimension(width, height);
    panel.setPreferredSize(size);
    scrollPane.setPreferredSize(size);
    this.pack();
  }

  @Override
  public void setActionMap(Map<String, List<Action>> actionMap) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void pause() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void restart() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void toggleLoop() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setActionListeners(ActionListener listener) {
    // do nothing - no interaction
  }

  @Override
  public void setChangeListeners(ChangeListener listener) {
    // do nothing - no interaction
  }

  @Override
  public File openFile() {
    throw new UnsupportedOperationException();
  }

  @Override
  public FileSaveWrapper saveFile() {
    throw new UnsupportedOperationException();
  }
}
