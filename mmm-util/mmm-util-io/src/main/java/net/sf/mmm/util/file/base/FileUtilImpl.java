/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.base;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.component.api.AlreadyInitializedException;
import net.sf.mmm.util.file.api.FileAccessClass;
import net.sf.mmm.util.file.api.FileAlreadyExistsException;
import net.sf.mmm.util.file.api.FileAttributeModificationFailedException;
import net.sf.mmm.util.file.api.FileCreationFailedException;
import net.sf.mmm.util.file.api.FileDeletionFailedException;
import net.sf.mmm.util.file.api.FileType;
import net.sf.mmm.util.file.api.FileUtil;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.base.StringUtilImpl;
import net.sf.mmm.util.pattern.api.PatternCompiler;
import net.sf.mmm.util.pattern.base.WildcardGlobPatternCompiler;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;

/**
 * This class is a collection of utility functions for {@link File} handling and manipulation.
 *
 * @see #getInstance()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@Singleton
@Named(FileUtil.CDI_NAME)
public class FileUtilImpl extends FileUtilLimitedImpl implements FileUtil {

  /** @see #getInstance() */
  private static FileUtil instance;

  /** @see #getStringUtil() */
  private StringUtil stringUtil;

  /** @see #setUserHomeDirectoryPath(String) */
  private String userHomeDirectoryPath;

  /** @see #getUserHomeDirectory() */
  private File userHomeDirectory;

  /** @see #getUserLogin() */
  private String userLogin;

  /** @see #setTemporaryDirectoryPath(String) */
  private String temporaryDirectoryPath;

  /** @see #getTemporaryDirectory() */
  private File temporaryDirectory;

  /**
   * The constructor.
   */
  public FileUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link FileUtilImpl}. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   *
   * @return the singleton instance.
   */
  public static FileUtil getInstance() {

    if (instance == null) {
      synchronized (FileUtilImpl.class) {
        if (instance == null) {
          FileUtilImpl util = new FileUtilImpl();
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
  protected void doInitialize() {

    super.doInitialize();
    if (this.stringUtil == null) {
      this.stringUtil = StringUtilImpl.getInstance();
    }
    if (this.temporaryDirectoryPath == null) {
      this.temporaryDirectoryPath = System.getProperty(PROPERTY_TMP_DIR);
    }
    this.temporaryDirectoryPath = this.temporaryDirectoryPath.replace('\\', '/');
    if (this.temporaryDirectory == null) {
      this.temporaryDirectory = new File(this.temporaryDirectoryPath);
    }
    if (this.userHomeDirectoryPath == null) {
      this.userHomeDirectoryPath = System.getProperty(PROPERTY_USER_HOME);
    }
    this.userHomeDirectoryPath = this.userHomeDirectoryPath.replace('\\', '/');
    if (this.userHomeDirectory == null) {
      this.userHomeDirectory = new File(this.userHomeDirectoryPath);
    }
    if (this.userLogin == null) {
      this.userLogin = System.getProperty("user.name");
    }
  }

  /**
   * This method gets the {@link StringUtilImpl} that is used by this {@link FileUtilImpl}.
   *
   * @return the stringUtil the {@link StringUtilImpl}.
   */
  protected StringUtil getStringUtil() {

    return this.stringUtil;
  }

  /**
   * This method sets the {@link #getStringUtil() StringUtil}. It can only be set once during initialization.
   *
   * @param stringUtil the stringUtil to set.
   * @throws AlreadyInitializedException if the value has already been set.
   */
  @Inject
  public void setStringUtil(StringUtil stringUtil) throws AlreadyInitializedException {

    getInitializationState().requireNotInitilized();
    this.stringUtil = stringUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public File getUserHomeDirectory() {

    return this.userHomeDirectory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getUserHomeDirectoryPath() {

    return this.userHomeDirectoryPath;
  }

  /**
   * This method set the {@link #getUserHomeDirectory() users home directory}. It can only be set during
   * initialization.
   *
   * @param userHome is the home directory of the user.
   * @throws AlreadyInitializedException if the value has already been set.
   */
  public void setUserHomeDirectoryPath(String userHome) throws AlreadyInitializedException {

    getInitializationState().requireNotInitilized();
    this.userHomeDirectoryPath = userHome;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public File getTemporaryDirectory() {

    return this.temporaryDirectory;
  }

  /**
   * This method sets the {@link #getTemporaryDirectory() tmp directory}.
   *
   * @param tmpDir the tmpDir to set
   * @throws AlreadyInitializedException if the value has already been set.
   */
  public void setTemporaryDirectoryPath(String tmpDir) throws AlreadyInitializedException {

    getInitializationState().requireNotInitilized();
    this.temporaryDirectoryPath = tmpDir;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUserLogin() {

    return this.userLogin;
  }

  /**
   * @param userLogin is the userLogin to set
   */
  public void setUserLogin(String userLogin) {

    getInitializationState().requireNotInitilized();
    this.userLogin = userLogin;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void copyFile(File source, File destination) {

    // There is also Files.copy but its implementation does not seem as efficient...
    try (FileInputStream sourceStream = new FileInputStream(source);
        FileOutputStream destinationStream = new FileOutputStream(destination)) {

      FileChannel sourceChannel = sourceStream.getChannel();
      sourceChannel.transferTo(0, sourceChannel.size(), destinationStream.getChannel());

    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.COPY);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void copyFile(File source, File destination, boolean keepFlags) {

    copyFile(source, destination);
    if (keepFlags) {
      if (source.canExecute()) {
        destination.setExecutable(true, false);
      }
      boolean success = true;
      if (!source.canWrite()) {
        success = destination.setReadOnly();
      }
      long lastModified = source.lastModified();
      success = success && destination.setLastModified(lastModified);
      if (!success) {
        throw new FileAttributeModificationFailedException(destination);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void copyRecursive(File source, File destination, boolean allowOverwrite) {

    copyRecursive(source, destination, allowOverwrite, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void copyRecursive(File source, File destination, boolean allowOverwrite, FileFilter filter) {

    if (!allowOverwrite && (destination.exists())) {
      throw new FileAlreadyExistsException(destination);
    }
    copyRecursive(source, destination, filter);
  }

  /**
   * This method copies the file or directory given by <code>source</code> into the given
   * <code>destination</code>. <br>
   * <b>ATTENTION:</b><br>
   * In order to allow giving the copy of <code>source</code> a new {@link File#getName() name}, the
   * <code>destination</code> has to point to the final place where the copy should appear rather than the
   * directory where the copy will be located in. <br>
   * <br>
   * E.g. the following code copies the folder "foo" located in "/usr/local" recursively to the directory
   * "/tmp". The copy will have the same name "foo".
   *
   * <pre>
   * {@link File} source = new {@link File}("/usr/local/foo");
   * {@link File} destination = new {@link File}("/tmp", source.getName()); // file: "/tmp/foo"
   * {@link FileUtilImpl}.copyRecursive(source, destination, true);
   * </pre>
   *
   * @param source is the file or directory to copy.
   * @param destination is the final place where the copy should appear.
   * @param filter is a {@link FileFilter} that {@link FileFilter#accept(File) decides} which files should be
   *        copied. Only {@link FileFilter#accept(File) accepted} files and directories are copied, others
   *        will be ignored.
   */
  private void copyRecursive(File source, File destination, FileFilter filter) {

    if (source.isDirectory()) {
      boolean okay = destination.mkdir();
      if (!okay) {
        throw new FileCreationFailedException(destination.getAbsolutePath(), true);
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
   * {@inheritDoc}
   */
  @Override
  public FileAccessPermissions getPermissions(File file, FileAccessClass accessClass) {

    FileAccessPermissions permissions = new FileAccessPermissions();
    boolean x = file.canExecute();
    boolean w = file.canWrite();
    boolean r = file.canRead();
    if (accessClass == null) {
      permissions.setExecutable(x);
      permissions.setWritable(w);
      permissions.setReadable(r);
    } else {
      permissions.setExecutable(accessClass, x);
      permissions.setWritable(accessClass, w);
      permissions.setReadable(accessClass, r);
    }
    return permissions;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPermissions(File file, FileAccessPermissions permissions) {

    boolean success = true;
    // global permissions
    success = success & file.setExecutable(permissions.isExecutable(FileAccessClass.OTHERS));
    success = success & file.setWritable(permissions.isWritable(FileAccessClass.OTHERS));
    success = success & file.setReadable(permissions.isReadable(FileAccessClass.OTHERS));
    // user permissions
    success = success & file.setExecutable(permissions.isExecutable(FileAccessClass.USER), true);
    success = success & file.setWritable(permissions.isWritable(FileAccessClass.USER), true);
    success = success & file.setReadable(permissions.isReadable(FileAccessClass.USER), true);
    if (!success) {
      throw new FileAttributeModificationFailedException(file);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int deleteRecursive(File path) {

    int deleteCount = 0;
    if (path.exists()) {
      if (path.isDirectory()) {
        deleteCount = deleteChildren(path);
      } else {
        deleteCount = 1;
      }
      boolean deleted = path.delete();
      if (!deleted) {
        throw new FileDeletionFailedException(path);
      }
    }
    return deleteCount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int deleteChildren(File directory) {

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
          throw new FileDeletionFailedException(file);
        }
      }
    }
    return deleteCount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public File[] getMatchingFiles(File cwd, String path, FileType fileType) {

    List<File> list = new ArrayList<File>();
    collectMatchingFiles(cwd, path, fileType, list);
    return list.toArray(new File[list.size()]);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean collectMatchingFiles(File cwd, String path, FileType fileType, List<File> list) {

    if ((path == null) || (path.length() == 0)) {
      throw new IllegalArgumentException("Path must not be empty");
    }
    List<PathSegment> segmentList = new ArrayList<PathSegment>();
    // TODO initialize cwd according to absolute or relative path
    boolean pathIsPattern = tokenizePath(path, segmentList, WildcardGlobPatternCompiler.INSTANCE);
    PathSegment[] segments = segmentList.toArray(new PathSegment[segmentList.size()]);
    collectMatchingFiles(cwd, segments, 0, fileType, list);
    return pathIsPattern;
  }

  /**
   * This method adds all files matching to the given <code>path</code> and <code>fileType</code> to the
   * <code>list</code>. The <code>path</code> may contain
   * {@link net.sf.mmm.util.pattern.base.GlobPatternCompiler wildcards}.
   *
   * @param cwd is the current working directory and should therefore point to an existing
   *        {@link File#isDirectory() directory}. If the given <code>path</code> is NOT
   *        {@link File#isAbsolute() absolute} it is interpreted relative to this directory.
   * @param segments is the path the files to collect must match. If this path is NOT
   *        {@link File#isAbsolute() absolute} it is interpreted relative to the {@link File#isDirectory()
   *        directory} given by <code>cwd</code>.
   * @param segmentIndex is the current index in <code>pathChars</code> for the collection process.
   * @param fileType is the type of the files to collect or <code>null</code> if files of any type are
   *        acceptable.
   * @param list is the list where to {@link List#add(Object) add} the collected files.
   */
  private void collectMatchingFiles(File cwd, PathSegment[] segments, int segmentIndex, FileType fileType,
      List<File> list) {

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
    } else if ("**".equals(segments[segmentIndex].string)) {
      collectMatchingFiles(cwd, segments, segmentIndex + 1, fileType, list);
      File[] children = cwd.listFiles(DirectoryFilter.getInstance());
      for (File file : children) {
        collectMatchingFiles(file, segments, segmentIndex, fileType, list);
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
   * This method tokenized the given <code>path</code> by adding {@link PathSegment}s to the given
   * <code>list</code>.
   *
   * @param path is the path to tokenized
   * @param list is the list where to add the segment tokens.
   * @param patternCompiler is the {@link PatternCompiler} used to compile the individual {@link PathSegment
   *        segments} of the given <code>path</code>.
   * @return <code>true</code> if the path is a glob-pattern (contains '*' or '?'), <code>false</code>
   *         otherwise.
   */
  private boolean tokenizePath(String path, List<PathSegment> list, PatternCompiler patternCompiler) {

    char[] chars = path.toCharArray();
    getStringUtil().replace(chars, '\\', '/');
    boolean pathIsPattern = false;
    CharSequenceScanner scanner = new CharSequenceScanner(chars);
    while (scanner.hasNext()) {
      String segmentString = scanner.readUntil('/', true);
      // ignore double slashes ("//") and segment "."
      if ((segmentString.length() > 0) && (!PATH_SEGMENT_CURRENT.equals(segmentString))) {
        if (PATH_SEGMENT_PARENT.equals(segmentString)) {
          // ".." --> remove last segment from list...
          int lastIndex = list.size() - 1;
          if (lastIndex >= 0) {
            list.remove(lastIndex);
          }
        } else {
          Pattern segmentPattern = patternCompiler.compile(segmentString);
          if (segmentPattern != null) {
            pathIsPattern = true;
          }
          PathSegment segment = new PathSegment(segmentString, segmentPattern);
          list.add(segment);
        }
      }
    }
    return pathIsPattern;
  }

  /**
   * This inner class represents a segment of a glob-matching path. It is a simple container for a string and
   * a pattern.
   */
  protected static class PathSegment {

    /** @see #getString() */
    private final String string;

    /** @see #getPattern() */
    private final Pattern pattern;

    /**
     * The constructor.
     *
     * @param string is the {@link #getString() string} of the segment.
     */
    public PathSegment(String string) {

      this(string, null);
    }

    /**
     * The constructor.
     *
     * @param string is the {@link #getString() string} of the segment.
     * @param pattern is the <code>string</code> parsed as {@link #getPattern() pattern}.
     */
    public PathSegment(String string, Pattern pattern) {

      super();
      this.pattern = pattern;
      this.string = string;
    }

    /**
     * This method gets the segment as raw string. This may be a {@link #getPattern() pattern}.
     *
     * @return the string.
     */
    public String getString() {

      return this.string;
    }

    /**
     * This method gets the pattern.
     *
     * @return the pattern or <code>null</code> if the {@link #getString() string} is to be matched exactly.
     */
    public Pattern getPattern() {

      return this.pattern;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

      return this.string;
    }
  }

}
