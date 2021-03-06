/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.base;

import java.util.Locale;
import java.util.Objects;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.file.api.FileUtilLimited;
import net.sf.mmm.util.resource.api.ResourcePath;
import net.sf.mmm.util.resource.api.ResourcePathNode;

/**
 * This class is a collection of utility functions for {@link java.io.File} handling and manipulation.
 *
 * @see #getInstance()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class FileUtilLimitedImpl extends AbstractComponent implements FileUtilLimited {

  private static FileUtilLimited instance;

  /**
   * The constructor.
   */
  public FileUtilLimitedImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link FileUtilLimitedImpl}. <br>
   * <b>ATTENTION:</b><br>
   * Please prefer dependency-injection instead of using this method.
   *
   * @return the singleton instance.
   */
  public static FileUtilLimited getInstance() {

    if (instance == null) {
      synchronized (FileUtilLimitedImpl.class) {
        if (instance == null) {
          FileUtilLimitedImpl util = new FileUtilLimitedImpl();
          util.initialize();
          instance = util;
        }
      }
    }
    return instance;
  }

  @Override
  public String normalizePath(String path) {

    return normalizePath(path, ResourcePath.PATH_SEGMENT_SEPARATOR_CHAR);
  }

  @Override
  public String normalizePath(String path, char separator) {

    Objects.requireNonNull(path, "path");
    if (path.isEmpty()) {
      return path;
    }
    String inputPath = ResourcePathNode.normalizeHome(path);
    ResourcePathNode<Void> resourcePath = ResourcePathNode.create(inputPath);
    return resourcePath.toString(separator);
  }

  @Override
  public String getExtension(String filename) {

    int lastDot = filename.lastIndexOf('.');
    String extension = "";
    if (lastDot > 0) {
      if ((filename.lastIndexOf('/', lastDot) == -1) && (filename.lastIndexOf('\\', lastDot) == -1)) {
        extension = filename.substring(lastDot + 1).toLowerCase(Locale.US);
      }
    }
    return extension;
  }

  @Override
  public String getBasename(String filename) {

    int len = filename.length();
    if (len == 0) {
      return filename;
    }
    // remove trailing slashes
    int end = len - 1;
    char last = filename.charAt(end);
    while ((last == '/') || (last == '\\')) {
      end--;
      if (end < 0) {
        return Character.toString(last);
      }
      last = filename.charAt(end);
    }
    int start = filename.lastIndexOf('/', end);
    if (start < 0) {
      start = filename.lastIndexOf('\\', end);
    }
    if ((last == ':') && (start < 0)) {
      return "";
    }
    return filename.substring(start + 1, end + 1);
  }

  @Override
  public String getDirname(String filename) {

    int len = filename.length();
    if (len == 0) {
      return PATH_SEGMENT_CURRENT;
    }
    // remove slashes at the end of the path (trailing slashes of filename)
    int pathEnd = len - 1;
    char last = filename.charAt(pathEnd);
    while ((last == '/') || (last == '\\')) {
      pathEnd--;
      if (pathEnd < 0) {
        return Character.toString(last);
      }
      last = filename.charAt(pathEnd);
    }
    // remove slashes at the end of dirname
    char c = '/';
    int dirEnd = filename.lastIndexOf(c, pathEnd);
    if (dirEnd < 0) {
      c = '\\';
      dirEnd = filename.lastIndexOf(c, pathEnd);
    }
    if (dirEnd >= 0) {
      int lastDirSlash = dirEnd;
      while ((c == '/') || (c == '\\')) {
        dirEnd--;
        if (dirEnd < 0) {
          return Character.toString(c);
        }
        c = filename.charAt(dirEnd);
      }
      if (c == ':') {
        if ((filename.lastIndexOf('/', dirEnd) < 0) && (filename.lastIndexOf('/', dirEnd) < 0)) {
          // special path (e.g. "C:\\" or "http://")
          dirEnd = lastDirSlash;
        }
      }
      return filename.substring(0, dirEnd + 1);
    } else if (last == ':') {
      // special path (e.g. "C:\\" or "http://")
      return filename;
    } else {
      // only trailing slashes or none
      return PATH_SEGMENT_CURRENT;
    }
  }

}
