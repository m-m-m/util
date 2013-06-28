/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsObject;

/**
 * A {@link Comparator} represents a function that compares two given values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public enum Comparator implements Datatype<String>, NlsObject {

  /** {@link Comparator} to check if some value is greater than another. */
  GREATER_THAN(">", NlsBundleUtilCoreRoot.INF_GREATER_THAN, false, Boolean.FALSE) {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eval(double arg1, double arg2) {

      return arg1 > arg2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoGreaterThan();
    }
  },

  /** {@link Comparator} to check if some value is greater or equal to another. */
  GREATER_OR_EQUAL(">=", NlsBundleUtilCoreRoot.INF_GREATER_OR_EQUAL, true, Boolean.FALSE) {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eval(double arg1, double arg2) {

      return arg1 >= arg2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoGreaterOrEqual();
    }
  },

  /** {@link Comparator} to check if some value is less than another. */
  LESS_THAN("<", NlsBundleUtilCoreRoot.INF_LESS_THAN, false, Boolean.TRUE) {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eval(double arg1, double arg2) {

      return arg1 < arg2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoLessThan();
    }
  },

  /** {@link Comparator} to check if some value is less or equal than another. */
  LESS_OR_EQUAL("<=", NlsBundleUtilCoreRoot.INF_LESS_OR_EQUAL, true, Boolean.TRUE) {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eval(double arg1, double arg2) {

      return arg1 <= arg2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoLessOrEqual();
    }
  },

  /**
   * {@link Comparator} to check if objects are {@link Object#equals(Object) equal}.
   */
  EQUAL("==", NlsBundleUtilCoreRoot.INF_EQUAL, true, null) {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eval(double arg1, double arg2) {

      return arg1 == arg2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoEqual();
    }
  },

  /**
   * {@link Comparator} to check if objects are NOT {@link Object#equals(Object) equal}.
   */
  NOT_EQUAL("!=", NlsBundleUtilCoreRoot.INF_NOT_EQUAL, false, null) {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eval(double arg1, double arg2) {

      return arg1 != arg2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoNotEqual();
    }
  };

  /** @see #getValue() */
  private final String value;

  /** @see #getTitle() */
  private final String title;

  /** @see #eval(Object, Object) */
  // private final boolean evalTrueIfEquals;

  /** @see #evalComparable(Comparable, Comparable) */
  // private final Boolean less;

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() raw value} (symbol).
   * @param title is the {@link #getTitle() title}.
   * @param evalTrueIfEquals - <code>true</code> if {@link Comparator} {@link #eval(Object, Object) evaluates}
   *        to <code>true</code> if arguments are equal, <code>false</code> otherwise.
   * @param less - {@link Boolean#TRUE} if {@link Comparator} {@link #eval(Object, Object) evaluates} to
   *        <code>true</code> if first argument is less than second, {@link Boolean#FALSE} on greater,
   *        <code>null</code> otherwise.
   */
  private Comparator(String value, String title, boolean evalTrueIfEquals, Boolean less) {

    this.value = value;
    this.title = title;
    // this.evalTrueIfEquals = evalTrueIfEquals;
    // this.less = less;
  }

  /**
   * @return <code>true</code> if this {@link Comparator} {@link #eval(Object, Object) evaluates} to
   *         <code>true</code> for {@link Object}s that are {@link Object#equals(Object) equal} to each other,
   *         <code>false</code> otherwise.
   */
  boolean isTrueIfEquals() {

    return (this == EQUAL) || (this == GREATER_OR_EQUAL) || (this == LESS_OR_EQUAL);
  }

  /**
   * @return <code>true</code> if this {@link Comparator} {@link #eval(Object, Object) evaluates} to
   *         <code>true</code> in case the first argument is less than the second, <code>false</code>
   *         otherwise.
   */
  boolean isTrueIfLess() {

    return (this == LESS_THAN) || (this == LESS_OR_EQUAL) || (this == NOT_EQUAL);
  }

  /**
   * @return <code>true</code> if this {@link Comparator} {@link #eval(Object, Object) evaluates} to
   *         <code>true</code> in case the first argument is greater than the second, <code>false</code>
   *         otherwise.
   */
  boolean isTrueIfGreater() {

    return (this == GREATER_THAN) || (this == GREATER_OR_EQUAL) || (this == NOT_EQUAL);
  }

  /**
   * This method gets the symbol of the {@link Comparator}. E.g. "==", ">", ">=", etc.
   * 
   * @return the comparator symbol.
   */
  @Override
  public String getValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return this.title;
  }

  /**
   * @return the instance of {@link NlsBundleUtilCoreRoot}.
   */
  private static NlsBundleUtilCoreRoot getBundle() {

    return NlsAccess.getBundleFactory().createBundle(NlsBundleUtilCoreRoot.class);
  }

  /**
   * This method evaluates this {@link Comparator} for the given arguments.
   * 
   * @param arg1 is the first argument.
   * @param arg2 is the second argument.
   * @return the result of the {@link Comparator} applied to the given arguments.
   */
  public abstract boolean eval(double arg1, double arg2);

  /**
   * @see ComparatorHelper#convert(Object, Class)
   * 
   * @param object is the value to convert.
   * @param otherType the type of the value to compare that differs from the type
   * @return a simpler representation of <code>value</code> or the same <code>value</code> if on simpler type
   *         is known.
   */
  private Object convert(Object object, Class<?> otherType) {

    return ComparatorHelper.convert(object, otherType);
  }

  /**
   * This method handles {@link #eval(Object, Object)} for two {@link Comparable}s.
   * 
   * @param arg1 is the first argument.
   * @param arg2 is the second argument.
   * @return the result of the {@link Comparator} applied to the given arguments.
   */
  private boolean evalComparable(Comparable arg1, Comparable arg2) {

    return ComparatorHelper.evalComparable(this, arg1, arg2);
  }

  /**
   * This method evaluates this {@link Comparator} for the given arguments.
   * 
   * @param arg1 is the first argument.
   * @param arg2 is the second argument.
   * @return the result of the {@link Comparator} applied to the given arguments.
   */
  @SuppressWarnings("rawtypes")
  public boolean eval(Object arg1, Object arg2) {

    if (arg1 == arg2) {
      return isTrueIfEquals();
    } else if ((arg1 == null) || (arg2 == null)) {
      return !isTrueIfEquals();
    } else {
      Object v1 = arg1;
      Object v2 = arg2;
      Class<?> type1 = v1.getClass();
      Class<?> type2 = v2.getClass();
      if (!type1.equals(type2)) {
        v1 = convert(v1, type2);
        v2 = convert(v2, type1);
      }
      if ((v1 instanceof Comparable) && (v2 instanceof Comparable)) {
        return evalComparable((Comparable) v1, (Comparable) v2);
      } else if ((v1 instanceof Number) && (v2 instanceof Number)) {
        return eval(((Number) v1).doubleValue(), ((Number) v2).doubleValue());
      } else if (v1.equals(v2)) {
        return isTrueIfEquals();
      } else {
        return !isTrueIfEquals();
      }
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getTitle();
  }

  /**
   * This method gets the {@link Comparator} for the given <code>symbol</code>.
   * 
   * @param value is the {@link #getValue() symbol} of the requested {@link Comparator}.
   * @return the requested {@link Comparator} or <code>null</code> if no such {@link Comparator} exists.
   */
  public static Comparator fromValue(String value) {

    for (Comparator comparator : values()) {
      if (comparator.value.equals(value)) {
        return comparator;
      }
    }
    return null;
  }
}
