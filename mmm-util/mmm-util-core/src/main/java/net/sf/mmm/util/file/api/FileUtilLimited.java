/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.api;

import java.io.File;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for a collection of utility functions for {@link File} handling and manipulation.<br/>
 * <b>NOTE:</b><br/>
 * Since Java7 there is also {@link java.nio.file.Files} that offers similar and additional features. However
 * this is not supported in limited environments such as e.g. GWT.
 *
 * @see net.sf.mmm.util.file.base.FileUtilLimitedImpl
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
@ComponentSpecification
public interface FileUtilLimited {

  /** The path segment indicating the current folder itself. */
  String PATH_SEGMENT_CURRENT = ".";

  /** The path segment indicating the parent folder. */
  String PATH_SEGMENT_PARENT = "..";

  /**
   * The key of the {@link System#getProperty(String) system property} <code>{@value}</code>. It contains the
   * home directory of the user that started this JVM.<br>
   * Examples are <code>/home/mylogin</code> or <code>C:\Windows\Profiles\mylogin</code>.
   */
  String PROPERTY_USER_HOME = "user.home";

  /**
   * The key of the {@link System#getProperty(String) system property} <code>{@value}</code>. It contains the
   * directory to use for temporary files.<br>
   * Examples are <code>/tmp</code>, <code>C:\Temp</code> or <code>/usr/local/tomcat/temp</code>.
   */
  String PROPERTY_TMP_DIR = "java.io.tmpdir";

  /** An empty file array. */
  File[] NO_FILES = new File[0];

  /**
   * This method normalizes a given <code>path</code>. It will resolve ".." and "." segments, normalize
   * backslashes and remove duplicated slashes. Further it can resolve "~" at the beginning of the path (like
   * in bash-scripts, etc.). Therefore this method resolves the path in such situations (e.g. to
   * "/home/login/foo") and returns a physical path.<br/>
   * Here are some examples assuming that <code>separator</code> is '/' (backslashes are NOT escaped):
   * <table border="1">
   * <tr>
   * <th><code>path</code></th>
   * <th><code>normalizePath(path)</code></th>
   * </tr>
   * <tr>
   * <td><code>"folder/subfolder//../.\some.file"</code></td>
   * <td><code>"folder/some.file"</code></td>
   * </tr>
   * <tr>
   * <td><code>"../.\some.file"</code></td>
   * <td><code>"../some.file"</code></td>
   * </tr>
   * <tr>
   * <td><code>"http://www.host.com/foo/bar/./test/.././.."</code></td>
   * <td><code>"http://www.host.com/foo"</code></td>
   * </tr>
   * <tr>
   * <td><code>"\\unc.host\printers\pr3761"</code></td>
   * <td><code>"\\unc.host\printers\pr3761"</code></td>
   * </tr>
   * <tr>
   * <td><code>"~/documents/index.html"</code></td>
   * <td><code>{@link #PROPERTY_USER_HOME home} + "/documents/index.html"</code></td>
   * </tr>
   * <tr>
   * <td><code>"~root/subfolder/../folder/.//index.html"</code></td>
   * <td><code>"/root/folder/index.html"</code></td>
   * </tr>
   * </table>
   * <b>ATTENTION:</b><br>
   * Normalizing home-directories of other users (e.g. "~user/") makes various assumptions and is NOT
   * guaranteed to be correct in any case.
   *
   * @param path is the path to resolve.
   * @param separator is the character to use as {@link File#separatorChar file separator}.
   * @return the resolved path.
   */
  String normalizePath(String path, char separator);

  /**
   * This method is a shortcut for <code>{@link #normalizePath(String, char) normalizePath}(path,
   * {@link File#separatorChar})</code>.
   *
   * @see #normalizePath(String, char)
   *
   * @param path is the path to resolve.
   * @return the resolved path.
   */
  String normalizePath(String path);

  /**
   * This method extracts the extension from the given <code>filename</code>.<br>
   * Example: <code>{@link #getExtension(String) getExtension}("test.java")</code> would return
   * <code>"java"</code>.<br>
   * <b>ATTENTION:</b><br>
   * If the <code>filename</code> is just a dot followed by the extension (e.g. <code>".java"</code>), the
   * empty string is returned.
   *
   * @param filename is the filename and may include an absolute or relative path.
   * @return the extension of the given <code>filename</code> excluding the dot in
   *         {@link String#toLowerCase() lowercase} or the empty string if NOT present.
   */
  String getExtension(String filename);

  /**
   * This method gets the <em>basename</em> of the given <code>filename</code> (path). The basename is the raw
   * name of the file without the {@link #getDirname(String) path}.<br>
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
   * This method gets the directory-name of the given <code>filename</code> (path).<br>
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
   * @return the path to the directory containing the file denoted by the given <code>filename</code>.
   */
  String getDirname(String filename);

}
