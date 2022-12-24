package cs5004.animator.view.swing;

import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

class FilePanel extends JPanel {
  private JButton loadButton;
  private JButton saveButton;
  private final Map<String, AbstractButton> buttonMap;

  public FilePanel() {
    buttonMap = new HashMap<>();
    loadButton = new JButton("Load animation");
    saveButton = new JButton("Save animation");
    loadButton.setActionCommand("Open file");
    saveButton.setActionCommand("Save file");
    buttonMap.put("Open file", loadButton);
    buttonMap.put("Save file", saveButton);
    this.add(loadButton);
    this.add(saveButton);
  }

  public Map<String, AbstractButton> getButtonMap() {
    return buttonMap;
  }
}
