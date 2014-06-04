/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.file.api.FileUtilLimited;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;

/**
 * This class is a collection of utility functions for {@link File} handling and manipulation.
 *
 * @see #getInstance()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class FileUtilLimitedImpl extends AbstractLoggableComponent implements FileUtilLimited {

  /** The typical home directory of the user "root" under Unix/Linux. */
  protected static final String HOME_ROOT = "/root";

  /**
   * The prefix of an UNC (Uniform Naming Convention) path (e.g. <code>\\10.0.0.1\share</code>).
   */
  protected static final String UNC_PATH_PREFIX = "\\\\";

  /**
   * The {@link Pattern} for an URL schema such as <code>http://</code> or <code>ftp://</code>.
   */
  protected static final Pattern URL_SCHEMA_PATTERN = Pattern.compile("([a-zA-Z]+://)(.*)");

  /** @see #getInstance() */
  private static FileUtilLimited instance;

  /**
   * The constructor.
   */
  public FileUtilLimitedImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link FileUtilLimitedImpl}.<br/>
   * <b>ATTENTION:</b><br/>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
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

  /**
   * {@inheritDoc}
   */
  @Override
  public String normalizePath(String path) {

    return normalizePath(path, File.separatorChar);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String normalizePath(String path, char separator) {

    if (path == null) {
      throw new NlsNullPointerException("path");
    }
    int len = path.length();
    if (len == 0) {
      return path;
    }
    if (path.startsWith(UNC_PATH_PREFIX)) {
      return path;
    }
    // char systemSlash = File.separatorChar;
    Matcher matcher = URL_SCHEMA_PATTERN.matcher(path);
    if (matcher.matches()) {
      String urlPath = normalizePath(matcher.group(2), '/');
      return matcher.group(1) + urlPath;
    }
    return normalizePathInternal(path, separator);
  }

  /**
   * @return the path to the home directory of the current user.
   */
  protected String getUserHomeDirectoryPath() {

    return "~";
  }

  /**
   * @return the login of the current user (from system property "user.name").
   */
  protected String getUserLogin() {

    return "anonymous";
  }

  /**
   * This method handles {@link #normalizePath(String)} internally.
   *
   * @param path is the path.
   * @param slash is the character used to to separate folders.
   * @return the resolved path.
   */
  private String normalizePathInternal(String path, char slash) {

    if (path.length() == 0) {
      return path;
    }
    char wrongSlash;
    if (slash == '/') {
      wrongSlash = '\\';
    } else {
      wrongSlash = '/';
    }
    String pathWithHome = path.replace(wrongSlash, slash);
    if (path.startsWith("~")) {
      StringBuilder sb = new StringBuilder(getUserHomeDirectoryPath().length() + path.length());
      int slashIndex = pathWithHome.indexOf(slash, 1);
      String user;
      if (slashIndex > 1) {
        user = pathWithHome.substring(1, slashIndex);
      } else if (slashIndex < 0) {
        user = pathWithHome.substring(1);
      } else {
        user = "";
      }
      if (user.length() == 0) {
        sb.append(getUserHomeDirectoryPath());
      } else {
        // ~<user> can not be resolved properly
        // we would need to do OS-specific assumptions and look into
        // /etc/passwd or whatever what might fail by missing read permissions
        // This is just a hack that might work in most cases:
        // we use the user.home dir get the dirname and append the user
        if ("root".equals(user)) {
          if ("root".equals(getUserLogin())) {
            sb.append(getUserHomeDirectoryPath());
          } else {
            sb.append(HOME_ROOT);
          }
        } else {
          if (HOME_ROOT.equals(getUserHomeDirectoryPath())) {
            sb.append("/home");
          } else {
            sb.append(getUserHomeDirectoryPath());
            sb.append(slash);
            sb.append(PATH_SEGMENT_PARENT);
          }
          sb.append(slash);
          sb.append(user);
          sb.append(slash);
        }
      }
      if (slashIndex > 0) {
        sb.append(pathWithHome.substring(slashIndex));
      }
      pathWithHome = sb.toString();
    }
    StringBuilder buffer = new StringBuilder(pathWithHome.length());
    CharSequenceScanner scanner = new CharSequenceScanner(pathWithHome);
    int segmentStart = 0;
    List<String> segments = new ArrayList<String>();
    boolean absolutePath = false;
    char c = scanner.peek();
    if (c == slash) {
      scanner.next();
      buffer.append(slash);
      absolutePath = true;
    }
    if (absolutePath) {
      segmentStart = -1;
    }
    while (scanner.hasNext()) {
      String segment = scanner.readUntil(slash, true);
      int segmentLength = segment.length();
      if (PATH_SEGMENT_PARENT.equals(segment)) {
        int size = segments.size();
        if (size == segmentStart) {
          segmentStart++;
          segments.add(segment);
        } else if (size > 0) {
          segments.remove(segments.size() - 1);
        }
      } else if ((segmentLength > 0) && !(PATH_SEGMENT_CURRENT.equals(segment))) {
        // ignore "" (duplicated slashes) and "."
        segments.add(segment);
      }
    }
    boolean appendSlash = false;
    for (String segment : segments) {
      if (appendSlash) {
        buffer.append(slash);
      } else {
        appendSlash = true;
      }
      buffer.append(segment);
    }
    if (path.endsWith(Character.toString(slash))) {
      int index = buffer.length() - 1;
      if (index > 0) {
        char last = buffer.charAt(index);
        if (last != slash) {
          buffer.append(slash);
        }
      }
    }
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
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
