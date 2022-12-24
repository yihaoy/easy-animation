package cs5004.animator.view.swing;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import cs5004.animator.model.Shape;

public class ModifyPanel extends JPanel {
  private final Map<String, AbstractButton> buttonMap;
  private final JComboBox<String> shapeList;
  private final JTextField addTextField;
  private List<Shape> shapes;

  public ModifyPanel() {
    super();
    buttonMap = new HashMap<>();
    JLabel listLabel = new JLabel("Delete a Shape: ");
    shapeList = new JComboBox<>();
    shapeList.setActionCommand("Set shape");
    JButton deleteButton = new JButton("Delete");
    deleteButton.setActionCommand("Delete");

    JLabel addLabel = new JLabel("Add a Shape or Motion: (ENTER to execute)");
    addTextField = new JTextField(40);
    addTextField.setActionCommand("Add");

    buttonMap.put("Delete", deleteButton);

    JPanel container = new JPanel(new BorderLayout());
    JPanel deleteContainer = new JPanel();
    JPanel addContainer = new JPanel();
    deleteContainer.add(listLabel);
    deleteContainer.add(shapeList);
    deleteContainer.add(deleteButton);
    addContainer.add(addLabel);
    addContainer.add(addTextField);
    container.add(deleteContainer, BorderLayout.NORTH);
    container.add(addContainer, BorderLayout.SOUTH);
    this.add(container);
  }

  public void setShapes(List<Shape> shapes) {
    this.shapes = shapes;
    shapeList.removeAllItems();
    shapes.forEach(shape -> shapeList.addItem(shape.getName()));
    if (shapeList.getItemCount() > 0) {
      shapeList.setSelectedIndex(0);
    }
  }

  public Map<String, AbstractButton> getButtonMap() {
    return this.buttonMap;
  }

  public JComboBox<String> getComboBox() {
    return this.shapeList;
  }

  public JTextField getTextField() {
    return this.addTextField;
  }
}
