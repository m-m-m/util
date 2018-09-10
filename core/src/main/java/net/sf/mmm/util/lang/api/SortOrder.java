/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This enum contains the possible values for the sort order of a list.
 *
 * @deprecated too many redundant enums for this purpose already available. Use alternative from spring-data, swing,
 *             etc.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public enum SortOrder implements SimpleDatatype<String> {

  /** Indicating that values are in increasing order (e.g. "1, 2, 3"). */
  ASCENDING("asc", "ascending"),

  /** Indicating that values are in decreasing order (e.g. "3, 2, 1"). */
  DESCENDING("desc", "descending");

  private final String value;

  private final String title;

  /**
   * The constructor.
   *
   * @param value is the {@link #getValue() raw value} (symbol).
   * @param title is the {@link #toString() string representation}.
   */
  private SortOrder(String value, String title) {

    this.value = value;
    this.title = title;
  }

  @Override
  public String getValue() {

    return this.value;
  }

  /**
   * Adjusts the {@link Math#signum(double)} of a {@link Comparable#compareTo(Object) compare to} result with this
   * {@link SortOrder} to the semantic of {@link java.util.Collections#sort(java.util.List, java.util.Comparator)}. In
   * other words *
   *
   * @param compareTo is the result of a regular {@link Comparable#compareTo(Object) compare to} operation.
   * @return the given value ({@code compareTo}) for {@link #ASCENDING} and the negation ( {@code -compareTo}) otherwise
   *         (for {@link #DESCENDING}).
   */
  public int adjustSignum(int compareTo) {

    if (this == DESCENDING) {
      return -compareTo;
    }
    return compareTo;
  }

  @Override
  public String toString() {

    return this.title;
  }

}
