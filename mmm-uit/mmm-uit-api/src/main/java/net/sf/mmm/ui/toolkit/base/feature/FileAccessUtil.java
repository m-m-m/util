/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.feature;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.mmm.ui.toolkit.api.feature.FileAccess;
import net.sf.mmm.ui.toolkit.api.window.MessageType;
import net.sf.mmm.ui.toolkit.api.window.UIWindow;

/**
 * This class contains utility methods for dealing with
 * {@link net.sf.mmm.ui.toolkit.api.feature.FileAccess}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class FileAccessUtil {

  /**
   * The constructor.
   */
  private FileAccessUtil() {

    super();
  }

  /**
   * This method saves the data given as {@link FileAccess access} to the
   * specified file. It will perform question dialogs before overwriting
   * existing files or creating additional folders. If will show info dialogs
   * if a problem arises.
   * 
   * @param access
   * @param target
   * @param parentWindow
   * @return <code>true</code> if the data has been saved successfully,
   *         <code>false</code> otherwise.
   */
  public static boolean save(FileAccess access, File target, UIWindow parentWindow) {

    if (target.exists()) {
      if (target.isFile()) {
        boolean okay = parentWindow.showQuestion("The file " + target.getAbsolutePath()
            + " already exists. Do you want to overwrite it?", "Overwrite");
        if (!okay) {
          return false;
        }
      } else {
        parentWindow.showMessage("The selected file " + target.getAbsolutePath()
            + " exists but is no file! Please choose an alternative location.", "Warning",
            MessageType.WARNING);
        return false;
      }
    }
    File folder = target.getParentFile();
    if (!folder.isDirectory()) {
      if (folder.exists()) {
        parentWindow.showMessage("The selected path " + folder.getAbsolutePath()
            + " exists but is no folder! Please choose an alternative location.", "Warning",
            MessageType.WARNING);
        return false;
      } else {
        boolean okay = parentWindow.showQuestion("The folder " + folder.getAbsolutePath()
            + " does not exist. Do you want to create it?", "Create Folder");
        if (okay) {
          boolean result = folder.mkdirs();
          if (!result) {
            parentWindow.showMessage("Failed to create the folder " + folder.getAbsolutePath()
                + "!", "Error", MessageType.ERROR);
            return false;
          }
        } else {
          return false;
        }
      }
    }
    InputStream inStream = null;
    OutputStream outStream = null;
    try {
      try {
        inStream = access.getFile();
      } catch (IOException e) {
        parentWindow.showMessage("Failed to open data to download!", "Error", MessageType.ERROR, e);
        return false;
      }
      target.createNewFile();
      outStream = new FileOutputStream(target.getAbsoluteFile());
      byte[] buffer = new byte[4096];
      int len;
      while (-1 != (len = inStream.read(buffer))) {
        outStream.write(buffer, 0, len);
      }
      return true;
    } catch (FileNotFoundException e) {
      parentWindow.showMessage("Failed to create the file " + target.getAbsolutePath() + "!",
          "Error", MessageType.ERROR, e);
      return false;
    } catch (IOException e) {
      parentWindow.showMessage("Failed to write data to file " + target.getAbsolutePath() + "!",
          "Error", MessageType.ERROR, e);
      return false;
    } finally {
      if (inStream != null) {
        try {
          inStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (outStream != null) {
        try {
          outStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

  }
}
