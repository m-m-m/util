/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.api;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import net.sf.mmm.util.file.base.FileAccessPermissions;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * This is the interface for a collection of utility functions for {@link File}
 * handling and manipulation.
 * 
 * @see net.sf.mmm.util.file.base.FileUtilImpl
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public interface FileUtil {

  /** The path segment indicating the current folder itself. */
  String PATH_SEGMENT_CURRENT = ".";

  /** The path segment indicating the parent folder. */
  String PATH_SEGMENT_PARENT = "..";

  /**
   * The key of the {@link System#getProperty(String) system property}
   * <code>{@value}</code>. It contains the home directory of the user that
   * started this JVM.<br>
   * Examples are <code>/home/mylogin</code> or
   * <code>C:\Windows\Profiles\mylogin</code>.
   */
  String PROPERTY_USER_HOME = "user.home";

  /**
   * The key of the {@link System#getProperty(String) system property}
   * <code>{@value}</code>. It contains the directory to use for temporary
   * files.<br>
   * Examples are <code>/tmp</code>, <code>C:\Temp</code> or
   * <code>/usr/local/tomcat/temp</code>.
   */
  String PROPERTY_TMP_DIR = "java.io.tmpdir";

  /** An empty file array. */
  File[] NO_FILES = new File[0];

  /**
   * This method gets the {@link File} representing the
   * {@link #PROPERTY_USER_HOME home directory of the user}.
   * 
   * @return the home directory of the user.
   */
  File getUserHomeDirectory();

  /**
   * This method gets the {@link File} representing the
   * {@link #PROPERTY_TMP_DIR temporary directory}.
   * 
   * @return the tmp directory.
   */
  File getTemporaryDirectory();

  /**
   * This method normalizes a given <code>path</code>. In most cases the given
   * <code>path</code> will simply be returned. Anyhow Java is NOT able to
   * resolve paths like "~/foo" that work properly in bash-scripts. Therefore
   * this method resolves the path in such situations (e.g. to
   * "/home/login/foo") and returns a physical path.
   * 
   * @param path is the path to resolve.
   * @return the resolved path.
   */
  String normalizePath(String path);

  /**
   * This method extracts the extension from the given <code>filename</code>.<br>
   * Example:
   * <code>{@link #getExtension(String) getExtension}("test.java")</code> would
   * return <code>"java"</code>.<br>
   * <b>ATTENTION:</b><br>
   * If the <code>filename</code> is just a dot followed by the extension (e.g.
   * <code>".java"</code>), the empty string is returned.
   * 
   * @param filename is the filename and may include an absolute or relative
   *        path.
   * @return the extension of the given <code>filename</code> excluding the dot
   *         in {@link String#toLowerCase() lowercase} or the empty string if
   *         NOT present.
   */
  String getExtension(String filename);

  /**
   * This method gets the <em>basename</em> of the given <code>filename</code>
   * (path). The basename is the raw name of the file without the
   * {@link #getDirname(String) path}.<br>
   * Examples:
   * <table border="1">
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
   * <td>/.</td>
   * <td>.</td>
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
  String getBasename(String filename);

  /**
   * This method gets the directory-name of the given <code>filename</code>
   * (path).<br>
   * Examples:
   * <table border="1">
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
  String getDirname(String filename);

  /**
   * This method copies the file given by <code>source</code> to the file given
   * by <code>destination</code>.
   * 
   * @param source is the existing file to copy from.
   * @param destination is the file to copy to. It will be created if it does
   *        NOT exist and overridden otherwise.
   * @throws RuntimeIoException if the operation fails.
   */
  void copyFile(File source, File destination) throws RuntimeIoException;

  /**
   * This method copies the file given by <code>source</code> to the file given
   * by <code>destination</code>.<br>
   * <b>ATTENTION:</b><br>
   * This method will only work with java 1.6 and above!
   * 
   * @param source is the existing file to copy from.
   * @param destination is the file to copy to. It will be created if it does
   *        NOT exist and overridden otherwise.
   * @param keepFlags - <code>true</code> if the flags of the file should be
   *        copied as well, <code>false</code> otherwise (a new file is created
   *        with default flags and only the content is copied).
   * @throws RuntimeIoException if the operation fails.
   */
  void copyFile(File source, File destination, boolean keepFlags) throws RuntimeIoException;

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
  FileAccessPermissions getPermissions(File file, FileAccessClass accessClass);

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
  void setPermissions(File file, FileAccessPermissions permissions);

  /**
   * This method copies the file or directory given by <code>source</code> into
   * the given <code>destination</code>.<br>
   * <b>ATTENTION:</b><br>
   * In order to allow giving the copy of <code>source</code> a new
   * {@link File#getName() name}, the <code>destination</code> has to point to
   * the final place where the copy should appear rather than the directory
   * where the copy will be located in.<br>
   * <br>
   * E.g. the following code copies the folder "foo" located in "/usr/local"
   * recursively to the directory "/tmp". The copy will have the same name
   * "foo".
   * 
   * <pre>
   * {@link File} source = new {@link File}("/usr/local/foo");
   * {@link File} destination = new {@link File}("/tmp", source.getName()); // file: "/tmp/foo"
   * {@link FileUtil fileUtil}.{@link #copyRecursive(File, File, boolean) copyRecursive}(source, destination, true);
   * </pre>
   * 
   * @param source is the file or directory to copy.
   * @param destination is the final place where the copy should appear.
   * @param allowOverwrite - if <code>false</code> and the
   *        <code>destination</code> already exists, a
   *        {@link RuntimeIoException} is thrown, else if <code>true</code> the
   *        <code>destination</code> will be overwritten.
   * @throws RuntimeIoException if the operation fails.
   */
  void copyRecursive(File source, File destination, boolean allowOverwrite)
      throws RuntimeIoException;

  /**
   * This method copies the file or directory given by <code>source</code> into
   * the given <code>destination</code>.<br>
   * <b>ATTENTION:</b><br>
   * In order to allow giving the copy of <code>source</code> a new
   * {@link File#getName() name}, the <code>destination</code> has to point to
   * the final place where the copy should appear rather than the directory
   * where the copy will be located in.<br>
   * <br>
   * 
   * @see #copyRecursive(File, File, boolean)
   * 
   * @param source is the file or directory to copy.
   * @param destination is the final place where the copy should appear.
   * @param allowOverwrite - if <code>false</code> and the
   *        <code>destination</code> already exists, a
   *        {@link RuntimeIoException} is thrown, else if <code>true</code> the
   *        <code>destination</code> will be overwritten.
   * @param filter is a {@link FileFilter} that {@link FileFilter#accept(File)
   *        decides} which files should be copied. Only
   *        {@link FileFilter#accept(File) accepted} files and directories are
   *        copied, others will be ignored.
   * @throws RuntimeIoException if the operation fails.
   */
  void copyRecursive(File source, File destination, boolean allowOverwrite, FileFilter filter)
      throws RuntimeIoException;

  /**
   * This method {@link File#delete() deletes} the given <code>path</code>. If
   * the <code>path</code> denotes a {@link File#isDirectory() directory} then
   * it will be deleted recursively.
   * 
   * @see #deleteChildren(File)
   * 
   * @param path is the path to delete.
   * @return the number of files that have been deleted (excluding the
   *         directories).
   * @throws RuntimeIoException if a file or directory could NOT be
   *         {@link File#delete() deleted}.
   */
  int deleteRecursive(File path) throws RuntimeIoException;

  /**
   * This method {@link File#delete() deletes} all {@link File#listFiles()
   * children} of the given <code>directory</code> recursively. If the given
   * <code>directory</code> denotes an {@link File#exists() existing}
   * {@link File#isDirectory() directory} then it will be empty after the call
   * of this method, else this method will have no effect.
   * 
   * @param directory is the directory to delete.
   * @return the number of files that have been deleted (excluding the
   *         directories).
   * @throws RuntimeIoException if a file or directory could NOT be
   *         {@link File#delete() deleted}.
   */
  int deleteChildren(File directory) throws RuntimeIoException;

  /**
   * This method gets all {@link File files} matching to the given
   * <code>path</code> and <code>fileType</code>. The <code>path</code> may
   * contain {@link net.sf.mmm.util.pattern.base.PathPatternCompiler wildcards}.<br>
   * Examples:
   * <ul>
   * <li>
   * <code>{@link #getMatchingFiles(File, String, FileType) getMatchingFiles}(cwd, 
   * "*", {@link FileType#DIRECTORY})</code> will return all
   * {@link File#isDirectory() directories} in <code>cwd</code></li>
   * <li>
   * <code>{@link #getMatchingFiles(File, String, FileType) getMatchingFiles}(cwd, 
   * "*&#47;*.xml", {@link FileType#FILE})</code> will return all
   * {@link File#isFile() files} from all direct {@link File#list() sub-folders}
   * of <code>cwd</code> that end with ".xml"</li>
   * <li>
   * <code>{@link #getMatchingFiles(File, String, FileType) getMatchingFiles}(cwd, 
   * "**&#47;*.xml", {@link FileType#FILE})</code> will return all
   * {@link File#isFile() files} in <code>cwd</code> or any of its transitive
   * {@link File#list() sub-folders} that end with ".xml"</li>
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
   * @param fileType is the type of the requested files or <code>null</code> if
   *        files of any type are acceptable.
   * @return an array containing all the {@link File files} that match the given
   *         <code>path</code> and apply to <code>ignore</code>
   */
  File[] getMatchingFiles(File cwd, String path, FileType fileType);

  /**
   * This method adds all files matching the given <code>path</code> and
   * <code>fileType</code> to the <code>list</code>. The <code>path</code> may
   * contain {@link net.sf.mmm.util.pattern.base.GlobPatternCompiler wildcards}.
   * 
   * @param cwd is the current working directory and should therefore point to
   *        an existing {@link File#isDirectory() directory}. If the given
   *        <code>path</code> is NOT {@link File#isAbsolute() absolute} it is
   *        interpreted relative to this directory.
   * @param path is the path the files to collect must match. If this path is
   *        NOT {@link File#isAbsolute() absolute} it is interpreted relative to
   *        the {@link File#isDirectory() directory} given by <code>cwd</code>.
   * @param fileType is the type of the files to collect or <code>null</code> if
   *        files of any type are acceptable.
   * @param list is the list where to {@link List#add(Object) add} the collected
   *        files.
   * @return <code>false</code> if the path is a regular string and
   *         <code>true</code> if the given path contains at least one
   *         {@link net.sf.mmm.util.pattern.base.GlobPatternCompiler wildcard} (
   *         <code>'*'</code> or <code>'?'</code>).
   */
  boolean collectMatchingFiles(File cwd, String path, FileType fileType, List<File> list);

}
