package cs5004.animator.view.swing;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import cs5004.animator.model.Action;
import cs5004.animator.model.Shape;
import cs5004.animator.model.ViewModel;
import cs5004.animator.view.PlayableView;

/** CompositeView composes AnimationPanel to provide a UI that handles user input. */
public class CompositeView extends JFrame implements PlayableView {
  private final AnimationPanel animationPanel;
  private final ControlPanel controlPanel;
  private final JScrollPane scrollPane;
  private final FilePanel filePanel;
  private final ModifyPanel modifyPanel;
  private final JProgressBar progressBar;

  private ViewModel model;
  private int speed = 1;
  private boolean playing;
  private boolean looping;
  private int frame;
  private int totalTime;

  /** Blank constructor. */
  public CompositeView() {
    super();
    this.setTitle("EasyAnimator");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    animationPanel = new AnimationPanel();
    scrollPane = new JScrollPane(animationPanel);
    controlPanel = new ControlPanel();
    filePanel = new FilePanel();
    modifyPanel = new ModifyPanel();
    progressBar = new JProgressBar(1, 2);
    progressBar.setStringPainted(true);

    this.setLayout(new BorderLayout());
    this.add(scrollPane, BorderLayout.CENTER);
    JPanel controlContainer = new JPanel();
    JScrollPane controlScrollPane = new JScrollPane(controlContainer);
    controlContainer.setLayout(new BoxLayout(controlContainer, BoxLayout.Y_AXIS));
    this.add(controlScrollPane, BorderLayout.SOUTH);

    controlContainer.add(progressBar);
    controlContainer.add(controlPanel);
    controlContainer.add(modifyPanel);
    controlContainer.add(filePanel);

    this.playing = false;
    this.looping = false;
    this.pack();
  }

  /**
   * Constructor that takes a ViewModel to set.
   *
   * @param model to set in view.
   * @param i
   */
  public CompositeView(ViewModel model, int i) {
    this();
    this.setModel(model);
  }

  public void setModel(ViewModel model) {
    this.model = model;
    this.setAnimationSize(model.getWidth(), model.getHeight());
    this.setOrigin(model.getX(), model.getY());
    progressBar.setValue(0);
    progressBar.setMaximum(model.getEndTime());
    totalTime = model.getEndTime() / speed;
    updateFrame(1);
    this.setShapeState(model.getShapeState(frame));
    modifyPanel.setShapes(model.getShapes());
    render();
    pause();
    this.pack();
  }

  private void updateFrame(int frame) {
    this.frame = frame;
    progressBar.setValue(frame);
    updateProgressString();
  }

  private void updateProgressString() {
    progressBar.setString(String.format("%ds/%ds", frame / speed, totalTime));
  }

  @Override
  public void play() {
    if (playing) {
      return;
    }
    this.playing = true;
    controlPanel.togglePlay(true);
    Thread playThread = new Thread(new PlayThread(), "playThread");
    playThread.start();
  }

  @Override
  public void render() {
    this.setShapeState(model.getShapeState(frame));
    this.repaint();
  }

  @Override
  public void showErrorMessages(String error) {
    JOptionPane.showMessageDialog(CompositeView.this, error, "ERROR", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public int getSpeed() {
    return this.speed;
  }

  @Override
  public void setSpeed(int speed) {
    if (speed < 1) {
      pause();
      setSpeed(1);
    }
    this.speed = speed;
    controlPanel.setSlider(speed);
    totalTime = model.getEndTime() / speed;
    updateProgressString();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void setShapeState(List<Shape> shapes) {
    animationPanel.setShapeState(shapes);
  }

  @Override
  public void setActionMap(Map<String, List<Action>> actionMap) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setAnimationSize(int width, int height) {
    Dimension size = new Dimension(width, height);
    animationPanel.setPreferredSize(size);
    scrollPane.setPreferredSize(size);
  }

  @Override
  public void setOrigin(int x, int y) {
    this.animationPanel.setOrigin(x, y);
  }

  @Override
  public void pause() {
    this.playing = false;
    controlPanel.togglePlay(false);
  }

  @Override
  public void restart() {
    updateFrame(1);
    this.render();
  }

  @Override
  public void toggleLoop() {
    this.looping = !this.looping;
    this.controlPanel.toggleLoopBackground();
  }

  @Override
  public void setActionListeners(ActionListener actionListener) {
    Map<String, AbstractButton> buttonMap = controlPanel.getButtonMap();
    buttonMap.forEach((key, button) -> button.addActionListener(actionListener));
    filePanel.getButtonMap().forEach((key, button) -> button.addActionListener(actionListener));
    modifyPanel.getButtonMap().forEach((key, button) -> button.addActionListener(actionListener));
    modifyPanel.getComboBox().addActionListener(actionListener);
    modifyPanel.getTextField().addActionListener(actionListener);
  }

  @Override
  public void setChangeListeners(ChangeListener listener) {
    controlPanel.getSlider().addChangeListener(listener);
  }

  @Override
  public File openFile() {
    JFileChooser fileChooser = new JFileChooser(".");
    FileNameExtensionFilter filter =
        new FileNameExtensionFilter("animation formatted txt files", "txt");
    fileChooser.setFileFilter(filter);
    if (fileChooser.showOpenDialog(CompositeView.this) == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile();
    }
    return null;
  }

  @Override
  public FileSaveWrapper saveFile() {
    Object[] fileTypeChoices = {"svg", "text"};
    String s =
        (String)
            JOptionPane.showInputDialog(
                CompositeView.this,
                "Save the animation as...",
                "Save as...",
                JOptionPane.QUESTION_MESSAGE,
                new ImageIcon(),
                fileTypeChoices,
                fileTypeChoices[0]);
    String ext;
    if ((s != null) && (s.length() > 0)) {
      ext = s.equals("svg") ? s : "txt";
    } else {
      return null;
    }
    JFileChooser fileChooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(ext + " file", ext);
    fileChooser.setFileFilter(filter);
    if (fileChooser.showOpenDialog(CompositeView.this) == JFileChooser.APPROVE_OPTION) {
      return new FileSaveWrapper(fileChooser.getSelectedFile(), s);
    }
    return null;
  }

  private class PlayThread implements Runnable {

    @Override
    public void run() {
      while (playing) {
        render();
        int sleepTime = 1000 / speed;
        if (frame < model.getEndTime()) {
          updateFrame(frame + 1);
        } else if (looping) {
          restart();
        } else {
          restart();
          pause();
        }
        try {
          Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
          showErrorMessages(e.toString());
        }
      }
    }
  }
}
