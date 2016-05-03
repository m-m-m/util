/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsObject;

/**
 * A {@link CompareOperator} represents a function that compares two given values.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public enum CompareOperator implements SimpleDatatype<String>, NlsObject {

  /** {@link CompareOperator} to check if some value is greater than another. */
  GREATER_THAN(">", NlsBundleUtilCoreRoot.INF_GREATER_THAN) {

    @Override
    public boolean eval(double arg1, double arg2) {

      return arg1 > arg2;
    }

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoGreaterThan();
    }

    @Override
    public CompareOperator negate() {

      return LESS_OR_EQUAL;
    }
  },

  /** {@link CompareOperator} to check if some value is greater or equal to another. */
  GREATER_OR_EQUAL(">=", NlsBundleUtilCoreRoot.INF_GREATER_OR_EQUAL) {

    @Override
    public boolean eval(double arg1, double arg2) {

      return arg1 >= arg2;
    }

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoGreaterOrEqual();
    }

    @Override
    public CompareOperator negate() {

      return LESS_THAN;
    }
  },

  /** {@link CompareOperator} to check if some value is less than another. */
  LESS_THAN("<", NlsBundleUtilCoreRoot.INF_LESS_THAN) {

    @Override
    public boolean eval(double arg1, double arg2) {

      return arg1 < arg2;
    }

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoLessThan();
    }

    @Override
    public CompareOperator negate() {

      return GREATER_OR_EQUAL;
    }
  },

  /** {@link CompareOperator} to check if some value is less or equal than another. */
  LESS_OR_EQUAL("<=", NlsBundleUtilCoreRoot.INF_LESS_OR_EQUAL) {

    @Override
    public boolean eval(double arg1, double arg2) {

      return arg1 <= arg2;
    }

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoLessOrEqual();
    }

    @Override
    public CompareOperator negate() {

      return GREATER_THAN;
    }
  },

  /**
   * {@link CompareOperator} to check if objects are {@link Object#equals(Object) equal}.
   */
  EQUAL("==", NlsBundleUtilCoreRoot.INF_EQUAL) {

    @Override
    public boolean eval(double arg1, double arg2) {

      return arg1 == arg2;
    }

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoEqual();
    }

    @Override
    public CompareOperator negate() {

      return NOT_EQUAL;
    }
  },

  /**
   * {@link CompareOperator} to check if objects are NOT {@link Object#equals(Object) equal}.
   */
  NOT_EQUAL("!=", NlsBundleUtilCoreRoot.INF_NOT_EQUAL) {

    @Override
    public boolean eval(double arg1, double arg2) {

      return arg1 != arg2;
    }

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoNotEqual();
    }

    @Override
    public CompareOperator negate() {

      return EQUAL;
    }
  };

  /** @see #getValue() */
  private final String value;

  /** @see #toString() */
  private final String title;

  /**
   * The constructor.
   *
   * @param value is the {@link #getValue() raw value} (symbol).
   * @param title is the {@link #toString() string representation}.
   * @param evalTrueIfEquals - {@code true} if {@link CompareOperator} {@link #eval(Object, Object) evaluates} to
   *        {@code true} if arguments are equal, {@code false} otherwise.
   * @param less - {@link Boolean#TRUE} if {@link CompareOperator} {@link #eval(Object, Object) evaluates} to
   *        {@code true} if first argument is less than second, {@link Boolean#FALSE} on greater, {@code null}
   *        otherwise.
   */
  private CompareOperator(String value, String title) {

    this.value = value;
    this.title = title;
  }

  /**
   * @return {@code true} if this {@link CompareOperator} {@link #eval(Object, Object) evaluates} to
   *         {@code true} for {@link Object}s that are {@link Object#equals(Object) equal} to each other,
   *         {@code false} otherwise.
   */
  boolean isTrueIfEquals() {

    return (this == EQUAL) || (this == GREATER_OR_EQUAL) || (this == LESS_OR_EQUAL);
  }

  /**
   * @return {@code true} if this {@link CompareOperator} {@link #eval(Object, Object) evaluates} to
   *         {@code true} in case the first argument is less than the second, {@code false} otherwise.
   */
  boolean isTrueIfLess() {

    return (this == LESS_THAN) || (this == LESS_OR_EQUAL) || (this == NOT_EQUAL);
  }

  /**
   * @return {@code true} if this {@link CompareOperator} {@link #eval(Object, Object) evaluates} to
   *         {@code true} in case the first argument is greater than the second, {@code false} otherwise.
   */
  boolean isTrueIfGreater() {

    return (this == GREATER_THAN) || (this == GREATER_OR_EQUAL) || (this == NOT_EQUAL);
  }

  /**
   * This method gets the symbol of the {@link CompareOperator}. E.g. "==", ">", ">=", etc.
   *
   * @return the comparator symbol.
   */
  @Override
  public String getValue() {

    return this.value;
  }

  /**
   * @return the instance of {@link NlsBundleUtilCoreRoot}.
   */
  private static NlsBundleUtilCoreRoot getBundle() {

    return NlsAccess.getBundleFactory().createBundle(NlsBundleUtilCoreRoot.class);
  }

  /**
   * This method evaluates this {@link CompareOperator} for the given arguments.
   *
   * @param arg1 is the first argument.
   * @param arg2 is the second argument.
   * @return the result of the {@link CompareOperator} applied to the given arguments.
   */
  public abstract boolean eval(double arg1, double arg2);

  /**
   * @since 7.1.0
   * @return the negation of this {@link CompareOperator} that {@link #eval(double, double) evaluates} to the negated
   *         result.
   */
  public abstract CompareOperator negate();

  /**
   * @see ComparatorHelper#convert(Object, Class)
   *
   * @param object is the value to convert.
   * @param otherType the type of the value to compare that differs from the type
   * @return a simpler representation of {@code value} or the same {@code value} if on simpler type is known.
   */
  private Object convert(Object object, Class<?> otherType) {

    return ComparatorHelper.convert(object, otherType);
  }

  /**
   * This method handles {@link #eval(Object, Object)} for two {@link Comparable}s.
   *
   * @param arg1 is the first argument.
   * @param arg2 is the second argument.
   * @return the result of the {@link CompareOperator} applied to the given arguments.
   */
  private boolean evalComparable(Comparable arg1, Comparable arg2) {

    return ComparatorHelper.evalComparable(this, arg1, arg2);
  }

  /**
   * This method evaluates this {@link CompareOperator} for the given arguments.
   *
   * @param arg1 is the first argument.
   * @param arg2 is the second argument.
   * @return the result of the {@link CompareOperator} applied to the given arguments.
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
      if ((v1 instanceof Number) && (v2 instanceof Number)) {
        return eval(((Number) v1).doubleValue(), ((Number) v2).doubleValue());
      } else if ((v1 instanceof Comparable) && (v2 instanceof Comparable)) {
        return evalComparable((Comparable) v1, (Comparable) v2);
      } else if (v1.equals(v2)) {
        return isTrueIfEquals();
      } else {
        return !isTrueIfEquals();
      }
    }

  }

  @Override
  public String toString() {

    return this.title;
  }

  /**
   * This method gets the {@link CompareOperator} for the given {@code symbol}.
   *
   * @param value is the {@link #getValue() symbol} of the requested {@link CompareOperator}.
   * @return the requested {@link CompareOperator} or {@code null} if no such {@link CompareOperator} exists.
   */
  public static CompareOperator fromValue(String value) {

    for (CompareOperator comparator : values()) {
      if (comparator.value.equals(value)) {
        return comparator;
      }
    }
    return null;
  }
}
