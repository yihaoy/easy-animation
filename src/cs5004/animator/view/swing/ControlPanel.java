package cs5004.animator.view.swing;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

class ControlPanel extends JPanel {
  private final Map<String, AbstractButton> buttonMap;
  private final JButton playButton;
  private final JToggleButton loopButton;
  private final JSlider speedSlider;
  private final JLabel speedLabel;
  private final Icon playIcon;
  private final Icon pauseIcon;

  public ControlPanel() {
    this.setLayout(new FlowLayout());

    playIcon = new ImageIcon("./images/play.png");
    pauseIcon = new ImageIcon("./images/pause.png");
    Icon loopIcon = new ImageIcon("./images/loop.png");
    Icon rewind = new ImageIcon("./images/rewind.png");

    playButton = new JButton(playIcon);
    playButton.setActionCommand("Play");
    JButton restartButton = new JButton(rewind);
    restartButton.setActionCommand("Restart");
    loopButton = new JToggleButton(loopIcon);
    loopButton.setActionCommand("Loop");

    speedLabel = new JLabel("frames per second", JLabel.CENTER);
    speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 60, 1);
    speedSlider.setMajorTickSpacing(10);
    speedSlider.setMinorTickSpacing(1);
    speedSlider.setPaintTicks(true);
    speedSlider.setPaintLabels(true);
    // group slider & label in a panel for easy relative layouts
    JPanel sliderPanel = new JPanel(new BorderLayout());
    sliderPanel.add(speedLabel, BorderLayout.NORTH);
    sliderPanel.add(speedSlider, BorderLayout.SOUTH);

    this.add(restartButton);
    this.add(playButton);
    this.add(loopButton);
    this.add(sliderPanel);

    this.buttonMap = new HashMap<>();
    buttonMap.put("Play", playButton);
    buttonMap.put("Restart", restartButton);
    buttonMap.put("Loop", loopButton);
    buttonMap.forEach((key, button) -> removeStyles(button));
    buttonMap.forEach((key, button) -> button.setToolTipText(key));
  }

  public void togglePlay(boolean playing) {
    if (!playing) {
      playButton.setActionCommand("Play");
      playButton.setIcon(playIcon);
    } else {
      playButton.setActionCommand("Pause");
      playButton.setIcon(pauseIcon);
    }
  }

  public void toggleLoopBackground() {
    loopButton.setContentAreaFilled(!loopButton.isContentAreaFilled());
  }

  public JSlider getSlider() {
    return this.speedSlider;
  }

  public void setSlider(int speed) {
    speedSlider.setValue(speed);
    speedLabel.setText(String.format("frames per second: %d", speed));
  }

  public Map<String, AbstractButton> getButtonMap() {
    return this.buttonMap;
  }

  private void removeStyles(AbstractButton button) {
    button.setOpaque(false);
    button.setContentAreaFilled(false);
    button.setBorderPainted(false);
  }
}
