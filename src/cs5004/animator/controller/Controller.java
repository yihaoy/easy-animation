package cs5004.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.IAnimationModel;
import cs5004.animator.model.ViewModel;
import cs5004.animator.util.AnimationBuilder;
import cs5004.animator.util.AnimationBuilderImpl;
import cs5004.animator.util.AnimationReader;
import cs5004.animator.view.PlayableView;
import cs5004.animator.view.View;
import cs5004.animator.view.ViewFactory;
import cs5004.animator.view.swing.FileSaveWrapper;

/**
 * Implementation of IController interface.
 */
public class Controller implements IController, ActionListener, ChangeListener {
  private final PlayableView view;
  private IAnimationModel model;
  private String selectedShape;

  /**
   * Constructor takes model and view.
   *
   * @param model model for app.
   * @param view view for app.
   */
  public Controller(IAnimationModel model, PlayableView view) {
    this.view = view;
    setModel(model);
  }

  private void setModel(IAnimationModel model) {
    this.model = model;
    this.view.setModel((ViewModel) model);
  }

  @Override
  public void go() {
    this.view.setActionListeners(this);
    this.view.setChangeListeners(this);
    this.view.makeVisible();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Play" -> view.play();
      case "Pause" -> view.pause();
      case "Restart" -> view.restart();
      case "Loop" -> view.toggleLoop();
      case "Open file" -> loadFile();
      case "Save file" -> saveFile();
      case "Set shape" -> setShape(e);
      case "Delete" -> deleteShape();
      case "Add" -> addAnimationComponent(e);
    }
  }

  private void setShape(ActionEvent e) {
    JComboBox cb = (JComboBox) e.getSource();
    this.selectedShape = (String)cb.getSelectedItem();
  }

  private void deleteShape() {
    if (selectedShape == null) {
      view.showErrorMessages("Must select a Shape (no selection by default)");
      return;
    }
    model.deleteShape(selectedShape);
    view.setModel((ViewModel) model);
  }

  private void addAnimationComponent(ActionEvent e) {
    JTextField source = (JTextField) e.getSource();
    Readable stringReader = new StringReader(source.getText());
    AnimationBuilder<IAnimationModel> tempBuilder = new AnimationBuilderImpl(model);
    try {
      IAnimationModel newModel = AnimationReader.parseFile(stringReader, tempBuilder);
      this.setModel(newModel);
    } catch (Exception ex) {
      view.showErrorMessages("Invalid string: " + ex.getMessage());
    }
    source.setText("");
  }

  private void loadFile() {
    File f = view.openFile();
    Readable file;
    try {
      file = new FileReader(f);
    } catch (FileNotFoundException e) {
      view.showErrorMessages(e.toString());
      return;
    }
    this.model = AnimationReader.parseFile(file, new AnimationBuilderImpl(new AnimationModel()));
    view.setModel((ViewModel) model);
  }

  private void saveFile() {
    FileSaveWrapper f = view.saveFile();
    if (f == null) {
      return;
    }
    try (FileWriter writer = new FileWriter(f.file)) {
      saveView(writer, f.typeChoice);
    } catch (IOException e) {
      view.showErrorMessages(e.toString());
    }
  }

  private void saveView(Appendable out, String viewType) {
    ViewFactory factory = new ViewFactory();
    factory.registerKnown();
    View tempView;
    try {
      tempView = factory.create(viewType, out);
    } catch (Exception e) {
      view.showErrorMessages(e.toString());
      return;
    }
    ViewModel viewModel = (ViewModel) model;
    tempView.setShapeState(viewModel.getShapes());
    tempView.setActionMap(viewModel.getActionMap());
    tempView.setSpeed(this.view.getSpeed());
    tempView.setAnimationSize(viewModel.getWidth(), viewModel.getHeight());
    tempView.setOrigin(viewModel.getX(), viewModel.getY());
    try {
      tempView.render();
    } catch (Exception e) {
      view.showErrorMessages(e.toString());
    }
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    JSlider source = (JSlider) e.getSource();
    view.setSpeed(source.getValue());
  }
}
