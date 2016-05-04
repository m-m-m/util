/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

/**
 * This is a container for a {@link CliMode} together with additional associated information.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface CliModeObject {

  /**
   * The {@link CliMode#id() ID} of the {@link #getMode() mode}. <br>
   * <b>ATTENTION:</b><br>
   * Please use this method to get the ID as {@link #getMode()} may return {@code null}.
   *
   * @return the ID.
   */
  String getId();

  /**
   * The {@link CliMode#title() title} of the {@link #getMode() mode}. If the title is NOT set, the {@link #getId() ID}
   * is returned<br>
   * <b>ATTENTION:</b><br>
   * Please use this method to get the title as {@link #getMode()} may return {@code null}.
   *
   * @return the title or the {@link #getId() ID} if title is NOT set.
   */
  String getTitle();

  /**
   * This method gets the actual {@link CliMode}.
   *
   * @return the {@link CliMode} or {@code null} if no {@link CliMode} annotation was present.
   */
  CliMode getMode();

  /**
   * This method gets the {@link Class} that was annotated with the {@link #getMode() mode}.
   *
   * @return the annotated {@link Class} or {@code null} if {@link #getMode()} is {@code null}.
   */
  Class<?> getAnnotatedClass();

  /**
   * Determines if this {@link CliModeObject} is an ancestor of the given {@code childMode}. So if the given
   * {@code childMode} extends this {@link CliModeObject} directly or indirect, this method will return {@code true}.
   *
   * @param childMode is the {@link CliModeObject} to relate to.
   * @return {@code true} this {@link CliModeObject} is an ancestor of the given {@code childMode}, {@code false}
   *         otherwise.
   */
  boolean isAncestorOf(CliModeObject childMode);

  /**
   * Determines if this {@link CliModeObject} is a descendant of the given {@code parentMode}. So if this
   * {@link CliModeObject} extends the given {@code childMode} directly or indirect, this method will return
   * {@code true}.
   *
   * @param parentMode is the {@link CliModeObject} to relate to.
   * @return {@code true} this {@link CliModeObject} is a descendant of the given {@code parentMode} , {@code false}
   *         otherwise.
   */
  boolean isDescendantOf(CliModeObject parentMode);

}
