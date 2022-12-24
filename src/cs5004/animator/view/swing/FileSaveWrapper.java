package cs5004.animator.view.swing;

import java.io.File;

/** Class that wraps a File and String. */
public class FileSaveWrapper {

  public String typeChoice;
  public File file;

  /**
   * Construct a wrapper of a File and a String.
   *
   * @param file File to wrap.
   * @param typeChoice String representing type of View to produce.
   */
  public FileSaveWrapper(File file, String typeChoice) {
    this.file = file;
    this.typeChoice = typeChoice;
  }
}
