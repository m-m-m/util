/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

/**
 * This class configures the (potential) truncation of an exception. A truncation includes the following
 * aspects:
 * <ul>
 * <li>{@link #isRemoveCause() remove cause}</li>
 * <li>{@link #isRemoveStacktrace() remove stacktrace}</li>
 * <li>{@link #isRemoveSuppressed() remove suppressed exceptions}</li>
 * </ul>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class ExceptionTruncation {

  /** Instance to remove all additional details. */
  public static final ExceptionTruncation REMOVE_ALL = new ExceptionTruncation(true, true, true);

  /**
   * Instance to remove {@link #isRemoveStacktrace() stacktrace} and {@link #isRemoveSuppressed() suppressed}.
   */
  public static final ExceptionTruncation REMOVE_STACKTRACE_AND_SUPPRESSED = new ExceptionTruncation(true, false, true);

  /** Instance to remove no additional details. */
  public static final ExceptionTruncation REMOVE_NONE = new ExceptionTruncation(false, false, false);

  private  final boolean removeCause;

  private  final boolean removeSuppressed;

  private  final boolean removeStacktrace;

  /**
   * The constructor.
   *
   * @param removeSuppressed - see {@link #isRemoveSuppressed()}.
   * @param removeCause - see {@link #isRemoveCause()}.
   * @param removeStacktrace - see {@link #isRemoveStacktrace()}.
   */
  public ExceptionTruncation(boolean removeSuppressed, boolean removeCause, boolean removeStacktrace) {

    super();
    this.removeSuppressed = removeSuppressed;
    this.removeCause = removeCause;
    this.removeStacktrace = removeStacktrace;
  }

  /**
   * @return {@code true} if the {@link Throwable#getSuppressed() suppressed exceptions} shall be
   *         removed, {@code false} otherwise.
   */
  public boolean isRemoveSuppressed() {

    return this.removeSuppressed;
  }

  /**
   * @return {@code true} if the {@link Throwable#getCause() cause} shall be removed, {@code false}
   *         otherwise.
   */
  public boolean isRemoveCause() {

    return this.removeCause;
  }

  /**
   * @return {@code true} if the {@link Throwable#getStackTrace() stacktrace} shall be removed,
   *         {@code false} otherwise.
   */
  public boolean isRemoveStacktrace() {

    return this.removeStacktrace;
  }

  /**
   * @see #isRemoveStacktrace()
   * @see #isRemoveCause()
   * @see #isRemoveSuppressed()
   *
   * @return {@code true} if all details shall be removed, {@code false} otherwise.
   */
  public boolean isRemoveAll() {

    return this.removeCause && this.removeStacktrace && this.removeSuppressed;
  }

  /**
   * @see #isRemoveStacktrace()
   * @see #isRemoveCause()
   * @see #isRemoveSuppressed()
   *
   * @return {@code true} if no details shall be removed (all information is retained),
   *         {@code false} otherwise.
   */
  public boolean isRetainAll() {

    return !this.removeCause && !this.removeStacktrace && !this.removeSuppressed;
  }

  @Override
  public String toString() {

    return "ExceptionTruncation [removeCause=" + this.removeCause + ", removeSuppressed=" + this.removeSuppressed
        + ", removeStacktrace=" + this.removeStacktrace + "]";
  }

}
