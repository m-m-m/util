/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.api;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.file.base.FileAccessPermissions;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * This is the interface for a collection of utility functions for {@link File} handling and manipulation. <br>
 * <b>NOTE:</b><br>
 * Since Java7 there is also {@link java.nio.file.Files} that offers similar and additional features. However this is
 * not supported in limited environments such as e.g. GWT.
 *
 * @see net.sf.mmm.util.file.base.FileUtilImpl
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
@ComponentSpecification
public interface FileUtil extends FileUtilLimited {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.util.file.api.FileUtil";

  /**
   * This method gets the {@link File} representing the {@link #PROPERTY_USER_HOME home directory of the current user}.
   *
   * @return the home directory of the user.
   */
  File getUserHomeDirectory();

  /**
   * This method gets the {@link File} representing the {@link #PROPERTY_TMP_DIR temporary directory}.
   *
   * @return the tmp directory.
   */
  File getTemporaryDirectory();

  /**
   * This method copies the file given by {@code source} to the file given by {@code destination}.
   *
   * @param source is the existing file to copy from.
   * @param destination is the file to copy to. It will be created if it does NOT exist and overridden otherwise.
   * @throws RuntimeIoException if the operation fails.
   */
  void copyFile(File source, File destination) throws RuntimeIoException;

  /**
   * This method copies the file given by {@code source} to the file given by {@code destination}. <br>
   * <b>ATTENTION:</b><br>
   * This method will only work with java 1.6 and above!
   *
   * @param source is the existing file to copy from.
   * @param destination is the file to copy to. It will be created if it does NOT exist and overridden otherwise.
   * @param keepFlags - {@code true} if the flags of the file should be copied as well, {@code false} otherwise (a new
   *        file is created with default flags and only the content is copied).
   * @throws RuntimeIoException if the operation fails.
   */
  void copyFile(File source, File destination, boolean keepFlags) throws RuntimeIoException;

  /**
   * This method gets the {@link FileAccessPermissions permissions} of the given {@code file}. <br>
   * <b>ATTENTION:</b><br>
   * This operation is only available since java 6. Further it is limited and can only determine the permissions granted
   * to the current user running this application.
   *
   * @param file is the file for which the permissions are requested.
   * @param accessClass is the {@link FileAccessClass distinct class} the permission should be applied to in the
   *        returned permissions. It may be {@code null} to apply the permissions to all distinct classes.
   * @return the permissions of {@code file}.
   */
  FileAccessPermissions getPermissions(File file, FileAccessClass accessClass);

  /**
   * This method sets the {@link FileAccessPermissions permissions} of the given {@code file}. <br>
   * <b>ATTENTION:</b><br>
   * This operation is only available since java 6. Further it is limited to the permissions
   * {@link FileAccessClass#OTHERS} and {@link FileAccessClass#USER} so {@link FileAccessClass#GROUP} flags are ignored
   * as well as the global s-bits ({@link FileAccessPermissions#isSticky() sticky},
   * {@link FileAccessPermissions#isSetgid() setgid} and {@link FileAccessPermissions#isSetgid() setuid}).
   *
   * @param file is the file to modify.
   * @param permissions are the permissions to set.
   */
  void setPermissions(File file, FileAccessPermissions permissions);

  /**
   * This method copies the file or directory given by {@code source} into the given {@code destination}. <br>
   * <b>ATTENTION:</b><br>
   * In order to allow giving the copy of {@code source} a new {@link File#getName() name}, the {@code destination} has
   * to point to the final place where the copy should appear rather than the directory where the copy will be located
   * in. <br>
   * <br>
   * E.g. the following code copies the folder "foo" located in "/usr/local" recursively to the directory "/tmp". The
   * copy will have the same name "foo".
   *
   * <pre>
   * {@link File} source = new {@link File}("/usr/local/foo");
   * {@link File} destination = new {@link File}("/tmp", source.getName()); // file: "/tmp/foo"
   * {@link FileUtil fileUtil}.{@link #copyRecursive(File, File, boolean) copyRecursive}(source, destination, true);
   * </pre>
   *
   * @param source is the file or directory to copy.
   * @param destination is the final place where the copy should appear.
   * @param allowOverwrite - if {@code false} and the {@code destination} already exists, a {@link RuntimeIoException}
   *        is thrown, else if {@code true} the {@code destination} will be overwritten.
   * @throws RuntimeIoException if the operation fails.
   */
  void copyRecursive(File source, File destination, boolean allowOverwrite) throws RuntimeIoException;

  /**
   * This method copies the file or directory given by {@code source} into the given {@code destination}. <br>
   * <b>ATTENTION:</b><br>
   * In order to allow giving the copy of {@code source} a new {@link File#getName() name}, the {@code destination} has
   * to point to the final place where the copy should appear rather than the directory where the copy will be located
   * in. <br>
   * <br>
   *
   * @see #copyRecursive(File, File, boolean)
   *
   * @param source is the file or directory to copy.
   * @param destination is the final place where the copy should appear.
   * @param allowOverwrite - if {@code false} and the {@code destination} already exists, a {@link RuntimeIoException}
   *        is thrown, else if {@code true} the {@code destination} will be overwritten.
   * @param filter is a {@link FileFilter} that {@link FileFilter#accept(File) decides} which files should be copied.
   *        Only {@link FileFilter#accept(File) accepted} files and directories are copied, others will be ignored.
   * @throws RuntimeIoException if the operation fails.
   */
  void copyRecursive(File source, File destination, boolean allowOverwrite, FileFilter filter)
      throws RuntimeIoException;

  /**
   * This method {@link File#delete() deletes} the given {@code path}. If the {@code path} denotes a
   * {@link File#isDirectory() directory} then it will be deleted recursively.
   *
   * @see #deleteChildren(File)
   *
   * @param path is the path to delete.
   * @return the number of files that have been deleted (excluding the directories).
   * @throws RuntimeIoException if a file or directory could NOT be {@link File#delete() deleted}.
   */
  int deleteRecursive(File path) throws RuntimeIoException;

  /**
   * This method {@link File#delete() deletes} all {@link File#listFiles() children} of the given {@code directory}
   * recursively. If the given {@code directory} denotes an {@link File#exists() existing} {@link File#isDirectory()
   * directory} then it will be empty after the call of this method, else this method will have no effect.
   *
   * @param directory is the directory to delete.
   * @return the number of files that have been deleted (excluding the directories).
   * @throws RuntimeIoException if a file or directory could NOT be {@link File#delete() deleted}.
   */
  int deleteChildren(File directory) throws RuntimeIoException;

  /**
   * This method gets all {@link File files} matching to the given {@code path} and {@code fileType} . The {@code path}
   * may contain {@link net.sf.mmm.util.pattern.base.PathPatternCompiler wildcards}. <br>
   * Examples:
   * <ul>
   * <li><code>{@link #getMatchingFiles(File, String, FileType) getMatchingFiles}(cwd,
   * "*", {@link FileType#DIRECTORY})</code> will return all {@link File#isDirectory() directories} in {@code cwd}</li>
   * <li><code>{@link #getMatchingFiles(File, String, FileType) getMatchingFiles}(cwd,
   * "*&#47;*.xml", {@link FileType#FILE})</code> will return all {@link File#isFile() files} from all direct
   * {@link File#list() sub-folders} of {@code cwd} that end with ".xml"</li>
   * <li><code>{@link #getMatchingFiles(File, String, FileType) getMatchingFiles}(cwd,
   * "**&#47;*.xml", {@link FileType#FILE})</code> will return all {@link File#isFile() files} in {@code cwd} or any of
   * its transitive {@link File#list() sub-folders} that end with ".xml"</li>
   * </ul>
   *
   * @see #collectMatchingFiles(File, String, FileType, List)
   *
   * @param cwd is the current working directory and should therefore point to an existing {@link File#isDirectory()
   *        directory}. If the given {@code path} is NOT {@link File#isAbsolute() absolute} it is interpreted relative
   *        to this directory.
   * @param path is the path the requested files must match. If this path is NOT {@link File#isAbsolute() absolute} it
   *        is interpreted relative to the {@link File#isDirectory() directory} given by {@code cwd}.
   * @param fileType is the type of the requested files or {@code null} if files of any type are acceptable.
   * @return an array containing all the {@link File files} that match the given {@code path} and apply to
   *         {@code ignore}
   */
  File[] getMatchingFiles(File cwd, String path, FileType fileType);

  /**
   * This method adds all files matching the given {@code path} and {@code fileType} to the {@code list}. The
   * {@code path} may contain {@link net.sf.mmm.util.pattern.base.GlobPatternCompiler wildcards}.
   *
   * @param cwd is the current working directory and should therefore point to an existing {@link File#isDirectory()
   *        directory}. If the given {@code path} is NOT {@link File#isAbsolute() absolute} it is interpreted relative
   *        to this directory.
   * @param path is the path the files to collect must match. If this path is NOT {@link File#isAbsolute() absolute} it
   *        is interpreted relative to the {@link File#isDirectory() directory} given by {@code cwd}.
   * @param fileType is the type of the files to collect or {@code null} if files of any type are acceptable.
   * @param list is the list where to {@link List#add(Object) add} the collected files.
   * @return {@code false} if the path is a regular string and {@code true} if the given path contains at least one
   *         {@link net.sf.mmm.util.pattern.base.GlobPatternCompiler wildcard} ( {@code '*'} or {@code '?'}).
   */
  boolean collectMatchingFiles(File cwd, String path, FileType fileType, List<File> list);

}
