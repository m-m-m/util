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

import javax.annotation.Resource;

import net.sf.mmm.util.StringUtil;
import net.sf.mmm.util.component.AbstractLoggable;
import net.sf.mmm.util.component.AlreadyInitializedException;
import net.sf.mmm.util.file.api.FileAccessClass;
import net.sf.mmm.util.file.api.FileType;
import net.sf.mmm.util.file.base.DirectoryFilter;
import net.sf.mmm.util.file.base.FileAccessPermissions;
import net.sf.mmm.util.file.base.PatternFileFilter;
import net.sf.mmm.util.pattern.api.PatternCompiler;
import net.sf.mmm.util.pattern.base.WildcardGlobPatternCompiler;
import net.sf.mmm.util.scanner.CharSequenceScanner;

/**
 * This class is a collection of utility functions for {@link File} handling and
 * manipulation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FileUtil extends AbstractLoggable {

  /** The path segment indicating the current folder itself. */
  public static final String PATH_SEGMENT_CURRENT = ".";

  /** The path segment indicating the parent folder. */
  public static final String PATH_SEGMENT_PARENT = "..";

  /**
   * The key of the {@link System#getProperty(String) system property}
   * <code>{@value}</code>. It contains the home directory of the user that
   * started this JVM.<br>
   * Examples are <code>/home/mylogin</code> or
   * <code>C:\Windows\Profiles\mylogin</code>.
   */
  public static final String PROPERTY_USER_HOME = "user.home";

  /**
   * The key of the {@link System#getProperty(String) system property}
   * <code>{@value}</code>. It contains the directory to use for temporary
   * files.<br>
   * Examples are <code>/tmp</code>, <code>C:\Temp</code> or
   * <code>/usr/local/tomcat/temp</code>.
   */
  public static final String PROPERTY_TMP_DIR = "java.io.tmpdir";

  /** @see #getInstance() */
  private static FileUtil instance;

  /** @see #getStringUtil() */
  private StringUtil stringUtil;

  /** @see #setUserHomeDirectoryPath(String) */
  private String userHomeDirectoryPath;

  /** @see #getUserHomeDirectory() */
  private File userHomeDirectory;

  /** @see #setTemporaryDirectoryPath(String) */
  private String temporaryDirectoryPath;

  /** @see #getTemporaryDirectory() */
  private File temporaryDirectory;

  /**
   * The constructor.
   */
  public FileUtil() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link FileUtil}.<br>
   * This design is the best compromise between easy access (via this
   * indirection you have direct, static access to all offered functionality)
   * and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and
   * construct new instances via the container-framework of your choice (like
   * plexus, pico, springframework, etc.). To wire up the dependent components
   * everything is properly annotated using common-annotations (JSR-250). If
   * your container does NOT support this, you should consider using a better
   * one.
   * 
   * @return the singleton instance.
   */
  public static FileUtil getInstance() {

    if (instance == null) {
      synchronized (FileUtil.class) {
        if (instance == null) {
          FileUtil util = new FileUtil();
          util.setStringUtil(StringUtil.getInstance());
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
    if (this.temporaryDirectoryPath == null) {
      this.temporaryDirectoryPath = System.getProperty(PROPERTY_TMP_DIR);
    }
    if (this.temporaryDirectory == null) {
      this.temporaryDirectory = new File(this.temporaryDirectoryPath);
    }
    if (this.userHomeDirectoryPath == null) {
      this.userHomeDirectoryPath = System.getProperty(PROPERTY_USER_HOME);
    }
    if (this.userHomeDirectory == null) {
      this.userHomeDirectory = new File(this.userHomeDirectoryPath);
    }
  }

  /**
   * This method gets the {@link StringUtil} that is used by this
   * {@link FileUtil}.
   * 
   * @return the stringUtil the {@link StringUtil}.
   */
  protected StringUtil getStringUtil() {

    return this.stringUtil;
  }

  /**
   * This method sets the {@link #getStringUtil() StringUtil}. It can only be
   * set once during initialization.
   * 
   * @param stringUtil the stringUtil to set.
   * @throws AlreadyInitializedException if the value has already been set.
   */
  @Resource
  public void setStringUtil(StringUtil stringUtil) throws AlreadyInitializedException {

    if (this.stringUtil != null) {
      throw new AlreadyInitializedException();
    }
    this.stringUtil = stringUtil;
  }

  /**
   * This method gets the {@link File} representing the
   * {@link #PROPERTY_USER_HOME home directory of the user}.
   * 
   * @return the home directory of the user.
   */
  public File getUserHomeDirectory() {

    return this.userHomeDirectory;
  }

  /**
   * This method set the {@link #getUserHomeDirectory() users home directory}.
   * It can only be set once during initialization.
   * 
   * @param userHome is the home directory of the user.
   * @throws AlreadyInitializedException if the value has already been set.
   */
  protected void setUserHomeDirectoryPath(String userHome) throws AlreadyInitializedException {

    if (this.userHomeDirectoryPath != null) {
      throw new AlreadyInitializedException();
    }
    this.userHomeDirectoryPath = userHome;
  }

  /**
   * This method gets the {@link File} representing the
   * {@link #PROPERTY_TMP_DIR temporary directory}.
   * 
   * @return the tmp directory.
   */
  public File getTemporaryDirectory() {

    return this.temporaryDirectory;
  }

  /**
   * This method sets the {@link #getTemporaryDirectory() tmp directory}.
   * 
   * @param tmpDir the tmpDir to set
   * @throws AlreadyInitializedException if the value has already been set.
   */
  protected void setTemporaryDirectoryPath(String tmpDir) throws AlreadyInitializedException {

    if (this.temporaryDirectoryPath != null) {
      throw new AlreadyInitializedException();
    }
    this.temporaryDirectoryPath = tmpDir;
  }

  /**
   * This method normalizes a given <code>path</code>. In most cases the
   * given <code>path</code> will simply be returned. Anyhow Java is NOT able
   * to resolve paths like "~/foo" that work properly in bash-scripts. Therefore
   * this method resolves the path in such situations (e.g. to
   * "/home/login/foo") and returns a physical path.
   * 
   * @param path is the path to resolve.
   * @return the resolved path.
   */
  public String normalizePath(String path) {

    int len = path.length();
    if (len == 0) {
      return path;
    }
    StringBuilder buffer = new StringBuilder(len + 4);
    char systemSlash = File.separatorChar;
    char wrongSlash;
    if (systemSlash == '/') {
      wrongSlash = '\\';
    } else {
      wrongSlash = '/';
    }
    // TODO: work on toCharArray to avoid overhead?
    char[] chars = path.toCharArray();
    getStringUtil().replace(chars, wrongSlash, systemSlash);
    CharSequenceScanner scanner = new CharSequenceScanner(chars);
    boolean appendSlash = false;
    char c = scanner.next();
    if (c == '~') {
      appendSlash = true;
      String user = scanner.readUntil(systemSlash, true);
      if (user.length() == 0) {
        buffer.append(this.userHomeDirectoryPath);
      } else {
        // ~<user> can not be resolved properly
        // we would need to do OS-specific assumtions and look into
        // /etc/passwd or whatever what might fail by missing read permissions
        // This is just a hack that might work in most cases:
        // we use the user.home dir get the dirname and append the user
        String homeDir = getDirname(this.userHomeDirectoryPath);
        buffer.append(homeDir);
        buffer.append(systemSlash);
        buffer.append(user);
      }
    } else {
      buffer.append(c);
    }
    // TODO: handle \\server\...
    // TODO: handle <schema>://
    List<String> segments = new ArrayList<String>();
    while (scanner.hasNext()) {
      String segment = scanner.readUntil(systemSlash, true);
      int segmentLength = segment.length();
      if (PATH_SEGMENT_PARENT.equals(segment)) {
        if (!segments.isEmpty()) {
          segments.remove(segments.size() - 1);
        }
      } else if ((segmentLength > 0) && !(PATH_SEGMENT_CURRENT.equals(segment))) {
        // ignore "" (duplicated slashes) and "."
        segments.add(segment);
      }
    }
    for (String segment : segments) {
      if (appendSlash) {
        buffer.append(systemSlash);
      } else {
        appendSlash = true;
      }
      buffer.append(segment);
    }
    if (chars[chars.length - 1] == systemSlash) {
      char last = buffer.charAt(buffer.length() - 1);
      if (last != systemSlash) {
        buffer.append(systemSlash);
      }
    }
    return buffer.toString();
  }

  /**
   * This method extracts the extension from the given <code>filename</code>.<br>
   * Example:
   * <code>{@link #getExtension(String) getExtension}("test.java")</code>
   * would return <code>"java"</code>.<br>
   * <b>ATTENTION:</b><br>
   * If the <code>filename</code> is just a dot followed by the extension
   * (e.g. <code>".java"</code>), the empty string is returned.
   * 
   * @param filename is the filename and may include an absolute or relative
   *        path.
   * @return the extension of the given <code>filename</code> excluding the
   *         dot in {@link String#toLowerCase() lowercase} or the empty string
   *         if NOT present.
   */
  public String getExtension(String filename) {

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
   * This method gets the <em>basename</em> of the given <code>filename</code>
   * (path). The basename is the raw name of the file without the
   * {@link #getDirname(String) path}.<br>
   * Examples: <table border="1">
   * <tr>
   * <th>filename</th>
   * <th><code>{@link #getBasename(String) getBasename}(filename)</code></th>
   * </tr>
   * <tr>
   * <td>&nbsp;</td>
   * <td>&nbsp;</td>
   * </tr>
   * <tr>
   * <td>/</td>
   * <td>/</td>
   * </tr>
   * <tr>
   * <td>\/\</td>
   * <td>\</td>
   * </tr>
   * <tr>
   * <td>.</td>
   * <td>/.</td>
   * </tr>
   * <tr>
   * <td>/foo.bar</td>
   * <td>foo.bar</td>
   * </tr>
   * <tr>
   * <td>/foo/bar/</td>
   * <td>bar</td>
   * </tr>
   * <tr>
   * <td>c:\\</td>
   * <td>&nbsp;</td>
   * </tr>
   * <tr>
   * <td>c:\\foo</td>
   * <td>foo</td>
   * </tr>
   * <tr>
   * <td>http://foo.org/bar</td>
   * <td>bar</td>
   * </tr>
   * </table>
   * 
   * @param filename is the path to a file or directory.
   * @return the basename of the given <code>filename</code>.
   */
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
   * This method gets the directory-name of the given <code>filename</code>
   * (path).<br>
   * Examples: <table border="1">
   * <tr>
   * <th>filename</th>
   * <th>{@link #getDirname(String)}</th>
   * </tr>
   * <tr>
   * <td>foo</td>
   * <td>.</td>
   * </tr>
   * <tr>
   * <td>/foo</td>
   * <td>/</td>
   * </tr>
   * <tr>
   * <td>/foo/bar</td>
   * <td>/foo</td>
   * </tr>
   * <tr>
   * <td>/foo/bar/</td>
   * <td>/foo</td>
   * </tr>
   * <tr>
   * <td>./foo/bar/</td>
   * <td>./foo</td>
   * </tr>
   * <tr>
   * <td>./foo/bar/../</td>
   * <td>./foo/bar</td>
   * </tr>
   * </table>
   * 
   * @see #normalizePath(String)
   * 
   * @param filename is the path to a file or directory.
   * @return the path to the directory containing the file denoted by the given
   *         <code>filename</code>.
   */
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

  /**
   * This method copies the file given by <code>source</code> to the file
   * given by <code>destination</code>.
   * 
   * @param source is the existing file to copy from.
   * @param destination is the file to copy to. It will be created if it does
   *        NOT exist and overridden otherwise.
   * @throws IOException if the operation fails.
   */
  public void copyFile(File source, File destination) throws IOException {

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
  public void copyFile(File source, File destination, boolean keepFlags) throws IOException {

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
   * This method gets the {@link FileAccessPermissions permissions} of the given
   * <code>file</code>.<br>
   * <b>ATTENTION:</b><br>
   * This operation is only available since java 6. Further it is limited and
   * can only determine the permissions granted to the current user running this
   * application.
   * 
   * @param file is the file for which the permissions are requested.
   * @param accessClass is the {@link FileAccessClass distinct class} the
   *        permission should be applied to in the returned permissions. It may
   *        be <code>null</code> to apply the permissions to all distinct
   *        classes.
   * @return the permissions of <code>file</code>.
   */
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
   * This method sets the <code>{@link FileAccessPermissions permissions}</code>
   * of the given <code>file</code>.<br>
   * <b>ATTENTION:</b><br>
   * This operation is only available since java 6. Further it is limited to the
   * permissions {@link FileAccessClass#OTHERS} and {@link FileAccessClass#USER}
   * so {@link FileAccessClass#GROUP} flags are ignored as well as the global
   * s-bits ({@link FileAccessPermissions#isSticky() sticky},
   * {@link FileAccessPermissions#isSetgid() setgid} and
   * {@link FileAccessPermissions#isSetgid() setuid}).
   * 
   * @param file is the file to modify.
   * @param permissions are the permissions to set.
   */
  public void setPermissions(File file, FileAccessPermissions permissions) {

    // global permissions
    file.setExecutable(permissions.isExecutable(FileAccessClass.OTHERS));
    file.setWritable(permissions.isWritable(FileAccessClass.OTHERS));
    file.setReadable(permissions.isReadable(FileAccessClass.OTHERS));
    // user permissions
    file.setExecutable(permissions.isExecutable(FileAccessClass.USER), true);
    file.setWritable(permissions.isWritable(FileAccessClass.USER), true);
    file.setReadable(permissions.isReadable(FileAccessClass.USER), true);
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
  public void copyRecursive(File source, File destination, boolean allowOverwrite)
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
  public void copyRecursive(File source, File destination, boolean allowOverwrite, FileFilter filter)
      throws IOException {

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
  private void copyRecursive(File source, File destination, FileFilter filter) throws IOException {

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
  public int deleteRecursive(File path) throws IOException {

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
  public int deleteChildren(File directory) throws IOException {

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
   * may contain
   * {@link net.sf.mmm.util.pattern.base.PathPatternCompiler wildcards}.<br>
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
   * will return all {@link File#isFile() files} from all direct
   * {@link File#list() sub-folders} of <code>cwd</code> that end with ".xml"
   * </li>
   * <li>
   * <code>{@link #getMatchingFiles(File, String, FileType) getMatchingFiles}(cwd, 
   * "**&#47;*.xml", {@link FileType#FILE})</code>
   * will return all {@link File#isFile() files} in <code>cwd</code> or any of
   * its transitive {@link File#list() sub-folders} that end with ".xml" </li>
   * </ul>
   * 
   * @see #collectMatchingFiles(File, String, FileType, List)
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
  public File[] getMatchingFiles(File cwd, String path, FileType fileType) {

    List<File> list = new ArrayList<File>();
    collectMatchingFiles(cwd, path, fileType, list);
    return list.toArray(new File[list.size()]);
  }

  /**
   * This method adds all files matching the given <code>path</code> and
   * <code>fileType</code> to the <code>list</code>. The <code>path</code>
   * may contain
   * {@link net.sf.mmm.util.pattern.base.GlobPatternCompiler wildcards}.
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
   *         <code>true</code> if the given path contains at least one
   *         {@link net.sf.mmm.util.pattern.base.GlobPatternCompiler wildcard} (<code>'*'</code>
   *         or <code>'?'</code>).
   */
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
   * This method adds all files matching to the given <code>path</code> and
   * <code>fileType</code> to the <code>list</code>. The <code>path</code>
   * may contain
   * {@link net.sf.mmm.util.pattern.base.GlobPatternCompiler wildcards}.
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
  private void collectMatchingFiles(File cwd, PathSegment[] segments, int segmentIndex,
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
   * This method tokenized the given <code>path</code> by adding
   * {@link PathSegment}s to the given <code>list</code>.
   * 
   * @param path is the path to tokenized
   * @param list is the list where to add the segment tokens.
   * @param patternCompiler is the {@link PatternCompiler} used to compile the
   *        individual {@link PathSegment segments} of the given
   *        <code>path</code>.
   * @return <code>true</code> if the path is a glob-pattern (contains '*' or
   *         '?'), <code>false</code> otherwise.
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
   * This inner class represents a segment of a glob-matching path. It is a
   * simple container for a string and a pattern.
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
     * @param pattern is the <code>string</code> parsed as
     *        {@link #getPattern() pattern}.
     */
    public PathSegment(String string, Pattern pattern) {

      super();
      this.pattern = pattern;
      this.string = string;
    }

    /**
     * This method gets the segment as raw string. This may be a
     * {@link #getPattern() pattern}.
     * 
     * @return the string.
     */
    public String getString() {

      return this.string;
    }

    /**
     * This method gets the pattern.
     * 
     * @return the pattern or <code>null</code> if the
     *         {@link #getString() string} is to be matched exactly.
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
