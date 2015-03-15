/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

/**
 * This class represents a path in a file, URL, or the like.
 *
 * @see ResourcePathNode#create(String)
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.0.0
 */
public interface ResourcePath {

  /** The path segment indicating the current folder itself. */
  String PATH_SEGMENT_CURRENT_DIRECTORY = ".";

  /** The path segment indicating the parent folder. */
  String PATH_SEGMENT_PARENT_DIRECTORY = "..";

  /** The path segment separator. */
  char PATH_SEGMENT_SEPARATOR_CHAR = '/';

  /** The path segment separator. */
  String PATH_SEGMENT_SEPARATOR = Character.toString(PATH_SEGMENT_SEPARATOR_CHAR);

  /** The path segment separator. */
  char HOME_PATH_CHAR = '~';

  /** The prefix of an UNC (Uniform Naming Convention) path (e.g. <code>\\10.0.0.1\share</code>). */
  String UNC_PATH_PREFIX = "\\\\";

  /** The separator infix between scheme and authority in an URL. */
  String URL_SCHEME_AUTHORITY_SEPARATOR = "://";

  /**
   * @return the parent {@link ResourcePathNode} or <code>null</code> if this is the {@link #isRoot()}
   *         segment.
   */
  ResourcePath getParent();

  /**
   * @return the {@link #isRoot() root} {@link #getParent() ancestor} of this {@link ResourcePathNode}.
   */
  ResourcePath getRoot();

  /**
   * @return the name of this {@link ResourcePathNode} as segment (this "folder" or "file").
   */
  String getName();

  /**
   * @return <code>true</code> if this path is absolute, <code>false</code> if it is relative.
   */
  boolean isAbsolute();

  /**
   * @return <code>true</code> if this is the root {@link ResourcePathNode}, <code>false</code> otherwise.
   */
  boolean isRoot();

}
