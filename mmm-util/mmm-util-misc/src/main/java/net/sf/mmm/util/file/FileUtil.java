/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.sf.mmm.util.StringUtil;
import net.sf.mmm.util.filter.PatternFileFilter;

/**
 * This class is a collection of utility functions for {@link File} handling and
 * manipulation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class FileUtil {

  /**
   * The constructor.
   */
  private FileUtil() {

    super();
  }

  /**
   * This method resolves a given path. In most cases the given
   * <code>path</code> will simply be returned. Anyhow Java is NOT able to
   * resolve paths like "~/foo" that work properly in bash-scripts. Therefore
   * this method resolves the path in such situations (e.g. to
   * "/home/login/foo") and returns a physical path.
   * 
   * @param path is the path to resolve.
   * @return the resolved path.
   */
  public static String resolvePath(String path) {

    if (path.startsWith("~/")) {
      return System.getProperty("user.home") + path.substring(1);
    }
    // TODO: also support "~user/"...
    return path;
  }

  /**
   * This method extracts the extension from the given <code>filename</code>.
   * 
   * @param filename is the filename and may include an absolute or relative
   *        path.
   * @return the extension of the given <code>filename</code> in
   *         {@link String#toLowerCase() lowercase} or <code>null</code> if
   *         NOT present.
   */
  public static String getExtension(String filename) {

    int lastDot = filename.lastIndexOf('.');
    String extension = "";
    if (lastDot > 0) {
      if ((filename.lastIndexOf('/', lastDot) == -1) && (filename.lastIndexOf('\\', lastDot) == -1)) {
        extension = filename.substring(lastDot + 1).toLowerCase();
      }
    }
    return extension;
  }

  /**
   * This method copies the file given by <code>source</code> to the file
   * given by <code>destination</code>.
   * 
   * @param source is the existing file to copy from.
   * @param destination is the file to copy to. It will be created if it does
   *        NOT exist and overridden otherwise.
   * @throws IOException if the operation fails.
   */
  public static void copyFile(File source, File destination) throws IOException {

    FileInputStream sourceStream = new FileInputStream(source);
    try {
      FileOutputStream destinationStream = new FileOutputStream(destination);
      try {
        FileChannel sourceChannel = sourceStream.getChannel();
        sourceChannel.transferTo(0, sourceChannel.size(), destinationStream.getChannel());
      } finally {
        destinationStream.close();
      }
    } finally {
      sourceStream.close();
    }
  }

  /**
   * This method copies the file given by <code>source</code> to the file
   * given by <code>destination</code>.
   * 
   * TODO
   * 
   * @param source is the existing file to copy from.
   * @param destination is the file to copy to. It will be created if it does
   *        NOT exist and overridden otherwise.
   * @param keepFlags - <code>true</code> if the flags of the file should be
   *        copied as well, <code>false</code> otherwise (a new file is
   *        created with default flags and only the content is copied).
   * @throws IOException if the operation fails.
   */
  public static void copyFile(File source, File destination, boolean keepFlags) throws IOException {

    copyFile(source, destination);
    if (keepFlags) {
      if (source.canExecute()) {
        destination.setExecutable(true, false);
      }
      if (!source.canWrite()) {
        destination.setReadOnly();
      }
      long lastModified = source.lastModified();
      destination.setLastModified(lastModified);
    }
  }

  /**
   * This method copies the file or directory given by <code>source</code>
   * into the given <code>destination</code>.<br>
   * <b>ATTENTION:</b><br>
   * In order to allow giving the copy of <code>source</code> a new
   * {@link File#getName() name}, the <code>destination</code> has to point
   * to the final place where the copy should appear rather than the directory
   * where the copy will be located in.<br>
   * <br>
   * E.g. the following code copies the folder "foo" located in "/usr/local"
   * recursively to the directory "/tmp". The copy will have the same name
   * "foo".
   * 
   * <pre>
   * {@link File} source = new {@link File}("/usr/local/foo");
   * {@link File} destination = new {@link File}("/tmp", source.getName()); // file: "/tmp/foo"
   * {@link FileUtil}.copyRecursive(source, destination, true);
   * </pre>
   * 
   * @param source is the file or directory to copy.
   * @param destination is the final place where the copy should appear.
   * @param allowOverwrite - if <code>false</code> and the
   *        <code>destination</code> already exists, an {@link IOException} is
   *        thrown, else if <code>true</code> the <code>destination</code>
   *        will be overwritten.
   * @throws IOException if the operation fails.
   */
  public static void copyRecursive(File source, File destination, boolean allowOverwrite)
      throws IOException {

    copyRecursive(source, destination, allowOverwrite, null);
  }

  /**
   * This method copies the file or directory given by <code>source</code>
   * into the given <code>destination</code>.<br>
   * <b>ATTENTION:</b><br>
   * In order to allow giving the copy of <code>source</code> a new
   * {@link File#getName() name}, the <code>destination</code> has to point
   * to the final place where the copy should appear rather than the directory
   * where the copy will be located in.<br>
   * <br>
   * E.g. the following code copies the folder "foo" located in "/usr/local"
   * recursively to the directory "/tmp". The copy will have the same name
   * "foo".
   * 
   * <pre>
   * {@link File} source = new {@link File}("/usr/local/foo");
   * {@link File} destination = new {@link File}("/tmp", source.getName()); // file: "/tmp/foo"
   * {@link FileUtil}.copyRecursive(source, destination, true);
   * </pre>
   * 
   * @param source is the file or directory to copy.
   * @param destination is the final place where the copy should appear.
   * @param allowOverwrite - if <code>false</code> and the
   *        <code>destination</code> already exists, an {@link IOException} is
   *        thrown, else if <code>true</code> the <code>destination</code>
   *        will be overwritten.
   * @param filter is a {@link FileFilter} that
   *        {@link FileFilter#accept(File) decides} which files should be
   *        copied. Only {@link FileFilter#accept(File) accepted} files and
   *        directories are copied, others will be ignored.
   * @throws IOException if the operation fails.
   */
  public static void copyRecursive(File source, File destination, boolean allowOverwrite,
      FileFilter filter) throws IOException {

    if (!allowOverwrite && (destination.exists())) {
      throw new IOException("Destination path \"" + destination.getAbsolutePath()
          + "\" already exists!");
    }
    copyRecursive(source, destination, filter);
  }

  /**
   * This method copies the file or directory given by <code>source</code>
   * into the given <code>destination</code>.<br>
   * <b>ATTENTION:</b><br>
   * In order to allow giving the copy of <code>source</code> a new
   * {@link File#getName() name}, the <code>destination</code> has to point
   * to the final place where the copy should appear rather than the directory
   * where the copy will be located in.<br>
   * <br>
   * E.g. the following code copies the folder "foo" located in "/usr/local"
   * recursively to the directory "/tmp". The copy will have the same name
   * "foo".
   * 
   * <pre>
   * {@link File} source = new {@link File}("/usr/local/foo");
   * {@link File} destination = new {@link File}("/tmp", source.getName()); // file: "/tmp/foo"
   * {@link FileUtil}.copyRecursive(source, destination, true);
   * </pre>
   * 
   * @param source is the file or directory to copy.
   * @param destination is the final place where the copy should appear.
   * @param filter is a {@link FileFilter} that
   *        {@link FileFilter#accept(File) decides} which files should be
   *        copied. Only {@link FileFilter#accept(File) accepted} files and
   *        directories are copied, others will be ignored.
   * @throws IOException if the operation fails.
   */
  private static void copyRecursive(File source, File destination, FileFilter filter)
      throws IOException {

    if (source.isDirectory()) {
      boolean okay = destination.mkdir();
      if (!okay) {
        throw new IOException("Failed to create path \"" + destination.getAbsolutePath() + "\"!");
      }
      File[] children;
      if (filter == null) {
        children = source.listFiles();
      } else {
        children = source.listFiles(filter);
      }
      for (File file : children) {
        copyRecursive(file, new File(destination, file.getName()), filter);
      }
    } else {
      copyFile(source, destination);
    }
  }

  /**
   * This method {@link File#delete() deletes} the given <code>path</code>.
   * If the <code>path</code> denotes a {@link File#isDirectory() directory}
   * then it will be deleted recursively.
   * 
   * @see #deleteChildren(File)
   * 
   * @param path is the path to delete.
   * @return the number of files that have been deleted (excluding the
   *         directories).
   * @throws IOException if a file or directory could NOT be
   *         {@link File#delete() deleted}.
   */
  public static int deleteRecursive(File path) throws IOException {

    int deleteCount = 0;
    if (path.exists()) {
      if (path.isDirectory()) {
        deleteCount = deleteChildren(path);
      } else {
        deleteCount = 1;
      }
      boolean deleted = path.delete();
      if (!deleted) {
        throw new IOException("Could not delete '" + path + "'!");
      }
    }
    return deleteCount;
  }

  /**
   * This method {@link File#delete() deletes} all
   * {@link File#listFiles() children} of the given <code>directory</code>
   * recursively. If the given <code>directory</code> denotes an
   * {@link File#exists() existing} {@link File#isDirectory() directory} then it
   * will be empty after the call of this method, else this method will have no
   * effect.
   * 
   * @param directory is the directory to delete.
   * @return the number of files that have been deleted (excluding the
   *         directories).
   * @throws IOException if a file or directory could NOT be
   *         {@link File#delete() deleted}.
   */
  public static int deleteChildren(File directory) throws IOException {

    int deleteCount = 0;
    File[] children = directory.listFiles();
    if (children != null) {
      for (File file : children) {
        if (file.isDirectory()) {
          deleteCount += deleteChildren(file);
        } else {
          deleteCount++;
        }
        boolean deleted = file.delete();
        if (!deleted) {
          throw new IOException("Could not delete '" + file + "'!");
        }
      }
    }
    return deleteCount;
  }

  /**
   * This method gets all {@link File files} matching to the given
   * <code>path</code> and <code>fileType</code>. The <code>path</code>
   * may be a {@link StringUtil#compileGlobPattern(String) glob-pattern}.<br>
   * Examples:
   * <ul>
   * <li>
   * <code>{@link #getMatchingFiles(File, String, FileType) getMatchingFiles}(cwd, 
   * "*", {@link FileType#DIRECTORY})</code>
   * will return all {@link File#isDirectory() directories} in <code>cwd</code>
   * </li>
   * <li>
   * <code>{@link #getMatchingFiles(File, String, FileType) getMatchingFiles}(cwd, 
   * "*&#47;*.xml", {@link FileType#FILE})</code>
   * will return all {@link File#isFile() files} from all
   * {@link File#list() subdirectories} of <code>cwd</code> that end with
   * ".xml" </li>
   * </ul>
   * 
   * @param cwd is the current working directory and should therefore point to
   *        an existing {@link File#isDirectory() directory}. If the given
   *        <code>path</code> is NOT {@link File#isAbsolute() absolute} it is
   *        interpreted relative to this directory.
   * @param path is the path the requested files must match. If this path is NOT
   *        {@link File#isAbsolute() absolute} it is interpreted relative to the
   *        {@link File#isDirectory() directory} given by <code>cwd</code>.
   * @param fileType is the type of the requested files or <code>null</code>
   *        if files of any type are acceptable.
   * @return an array containing all the {@link File files} that match the given
   *         <code>path</code> and apply to <code>ignore</code>
   */
  public static File[] getMatchingFiles(File cwd, String path, FileType fileType) {

    List<File> list = new ArrayList<File>();
    collectMatchingFiles(cwd, path, fileType, list);
    return list.toArray(new File[list.size()]);
  }

  /**
   * This method adds all files matching to the given <code>path</code> and
   * <code>fileType</code> to the <code>list</code>. The <code>path</code>
   * may be contain {@link StringUtil#compileGlobPattern(String) wildcards}.
   * 
   * @param cwd is the current working directory and should therefore point to
   *        an existing {@link File#isDirectory() directory}. If the given
   *        <code>path</code> is NOT {@link File#isAbsolute() absolute} it is
   *        interpreted relative to this directory.
   * @param path is the path the files to collect must match. If this path is
   *        NOT {@link File#isAbsolute() absolute} it is interpreted relative to
   *        the {@link File#isDirectory() directory} given by <code>cwd</code>.
   * @param fileType is the type of the files to collect or <code>null</code>
   *        if files of any type are acceptable.
   * @param list is the list where to {@link List#add(Object) add} the collected
   *        files.
   * @return <code>false</code> if the path is a regular string and
   *         <code>true</code> if the given path is a
   *         {@link StringUtil#compileGlobPattern(String) glob-pattern},
   *         meaning that it contained at least one of the characters '*' or
   *         '?'.
   */
  public static boolean collectMatchingFiles(File cwd, String path, FileType fileType,
      List<File> list) {

    if ((path == null) || (path.length() == 0)) {
      throw new IllegalArgumentException("Path must not be empty");
    }
    List<PathSegment> segmentList = new ArrayList<PathSegment>();
    // TODO initialize cwd according to absolute or relative path
    boolean pathIsPattern = tokenizePath(path, segmentList);
    PathSegment[] segments = segmentList.toArray(new PathSegment[segmentList.size()]);
    collectMatchingFiles(cwd, segments, 0, fileType, list);
    return pathIsPattern;
  }

  /**
   * This method adds all files matching to the given <code>path</code> and
   * <code>fileType</code> to the <code>list</code>. The <code>path</code>
   * may be a {@link StringUtil#compileGlobPattern(String) glob-pattern}
   * 
   * @param cwd is the current working directory and should therefore point to
   *        an existing {@link File#isDirectory() directory}. If the given
   *        <code>path</code> is NOT {@link File#isAbsolute() absolute} it is
   *        interpreted relative to this directory.
   * @param segments is the path the files to collect must match. If this path
   *        is NOT {@link File#isAbsolute() absolute} it is interpreted relative
   *        to the {@link File#isDirectory() directory} given by
   *        <code>cwd</code>.
   * @param segmentIndex is the current index in <code>pathChars</code> for
   *        the collection process.
   * @param fileType is the type of the files to collect or <code>null</code>
   *        if files of any type are acceptable.
   * @param list is the list where to {@link List#add(Object) add} the collected
   *        files.
   */
  private static void collectMatchingFiles(File cwd, PathSegment[] segments, int segmentIndex,
      FileType fileType, List<File> list) {

    boolean lastSegment;
    if ((segmentIndex + 1) < segments.length) {
      lastSegment = false;
    } else {
      lastSegment = true;
    }
    Pattern pattern = segments[segmentIndex].pattern;
    if (pattern == null) {
      File newCwd = new File(cwd, segments[segmentIndex].string);
      if (newCwd.exists()) {
        if (lastSegment) {
          if ((fileType == null) || (FileType.getType(newCwd) == fileType)) {
            list.add(newCwd);
          }
        } else if (newCwd.isDirectory()) {
          collectMatchingFiles(newCwd, segments, segmentIndex + 1, fileType, list);
        }
      }
    } else {
      FileType filterType = fileType;
      if (!lastSegment) {
        filterType = FileType.DIRECTORY;
      }
      FileFilter filter = new PatternFileFilter(pattern, filterType);
      File[] children = cwd.listFiles(filter);
      if (lastSegment) {
        for (File file : children) {
          list.add(file);
        }
      } else {
        for (File file : children) {
          collectMatchingFiles(file, segments, segmentIndex + 1, fileType, list);
        }
      }
    }
  }

  /**
   * This method tokenized the given <code>path</code> by adding
   * {@link PathSegment}s to the given <code>list</code>.
   * 
   * @param path is the path to tokenized
   * @param list is the list where to add the segment tokens.
   * @return <code>true</code> if the path is a glob-pattern (contains '*' or
   *         '?'), <code>false</code> otherwise.
   */
  private static boolean tokenizePath(String path, List<PathSegment> list) {

    char[] pathChars = path.toCharArray();
    int segmentStartIndex = 0;
    int currentIndex = 0;
    boolean pathIsPattern = false;
    boolean segmentIsPattern = false;
    char c = 0;
    while (currentIndex < pathChars.length) {
      c = pathChars[currentIndex++];
      if ((c == '/') || (c == '\\')) {
        int length = currentIndex - segmentStartIndex - 1;
        if (length == 0) {
          throw new IllegalArgumentException("Duplicate separator in path!");
        }
        PathSegment segment = new PathSegment();
        segment.string = new String(pathChars, segmentStartIndex, length);
        if (segmentIsPattern) {
          segment.pattern = StringUtil.compileGlobPattern(segment.string);
        } else {
          segment.pattern = null;
        }
        list.add(segment);
        segmentStartIndex = currentIndex;
        segmentIsPattern = false;
      }
      if ((c == '*') || (c == '?')) {
        segmentIsPattern = true;
        pathIsPattern = true;
      }
    }
    if ((c == '/') || (c == '\\')) {
      // allow this in any case?
    } else {
      PathSegment segment = new PathSegment();
      int length = currentIndex - segmentStartIndex;
      segment.string = new String(pathChars, segmentStartIndex, length);
      if (segmentIsPattern) {
        segment.pattern = StringUtil.compileGlobPattern(segment.string);
      } else {
        segment.pattern = null;
      }
      list.add(segment);
    }
    return pathIsPattern;
  }

  /**
   * This inner class represents a segment of a glob-matching path. It is a
   * simple container for a string and a pattern.
   */
  private static class PathSegment {

    /** the pattern */
    private Pattern pattern;

    /** the string */
    private String string;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

      return this.string;
    }
  }

}
