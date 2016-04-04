/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.expression;

import java.util.Objects;
import java.util.regex.Pattern;

import net.sf.mmm.util.lang.api.CompareOperator;
import net.sf.mmm.util.value.api.Range;

/**
 * A {@link SqlOperator} represents the SQL function that compares two given values in expressions (e.g. in where
 * clauses).
 *
 * @param <T> the generic type of the {@link #getType() comparison type}.
 * @param <S> the generic type of the {@link #evaluate(Object, Object) second comparison argument} - typically same as first
 *        ({@literal <T>}).
 * @author hohwille
 * @since 8.0.0
 */
public abstract class SqlOperator<T, S> {

  /** {@link SqlOperator} to check if some value is greater than another. */
  public static final SqlOperator<Number, Number> GREATER_THAN = new SqlOperatorNumber(">") {

    @Override
    public boolean eval(int delta) {

      return (delta > 0);
    }

    @Override
    public SqlOperator<Number, Number> negate() {

      return LESS_OR_EQUAL;
    }

  };

  /** {@link SqlOperator} to check if some value is greater or equal to another. */
  public static final SqlOperator<Number, Number> GREATER_OR_EQUAL = new SqlOperatorNumber(">=") {

    @Override
    public boolean eval(int delta) {

      return (delta >= 0);
    }

    @Override
    public SqlOperator<Number, Number> negate() {

      return LESS_THAN;
    }

  };

  /** {@link SqlOperator} to check if some value is less than another. */
  public static final SqlOperator<Number, Number> LESS_THAN = new SqlOperatorNumber("<") {

    @Override
    public boolean eval(int delta) {

      return (delta < 0);
    }

    @Override
    public SqlOperator<Number, Number> negate() {

      return GREATER_OR_EQUAL;
    }

  };

  /** {@link SqlOperator} to check if some value is less or equal than another. */
  public static final SqlOperator<Number, Number> LESS_OR_EQUAL = new SqlOperatorNumber("<=") {

    @Override
    public boolean eval(int delta) {

      return (delta <= 0);
    }

    @Override
    public SqlOperator<Number, Number> negate() {

      return GREATER_THAN;
    }

  };

  /** {@link SqlOperator} to check if objects are {@link Object#equals(Object) equal}. */
  public static final SqlOperator<Object, Object> EQUAL = new SqlOperator<Object, Object>("==", Object.class) {

    @Override
    public boolean evaluate(Object arg1, Object arg2) {

      return Objects.equals(arg1, arg2);
    }

    @Override
    public SqlOperator<Object, Object> negate() {

      return NOT_EQUAL;
    }

  };

  /** {@link SqlOperator} to check if objects are NOT {@link Object#equals(Object) equal}. */
  public static final SqlOperator<Object, Object> NOT_EQUAL = new SqlOperator<Object, Object>("<>", Object.class) {

    @Override
    public boolean evaluate(Object arg1, Object arg2) {

      return !Objects.equals(arg1, arg2);
    }

    @Override
    public SqlOperator<Object, Object> negate() {

      return EQUAL;
    }

  };

  /** {@link SqlOperator} to check if objects match using SQL like pattern. */
  public static final SqlOperator<String, String> LIKE = new SqlOperatorLike(true) {

    @Override
    public SqlOperator<String, String> negate() {

      return NOT_LIKE;
    }
  };

  /** {@link SqlOperator} to check if objects match using SQL like pattern. */
  public static final SqlOperator<String, String> NOT_LIKE = new SqlOperatorLike(false) {

    @Override
    public SqlOperator<String, String> negate() {

      return LIKE;
    }
  };

  /** {@link SqlOperator} to check if objects match using SQL BETWEEN operation. */
  public static final SqlOperator<Number, Range<Number>> BETWEEN = new SqlOperatorBetween<Number>(true,
      Number.class) {

    @Override
    public boolean evaluate(Number arg1, Range<Number> arg2) {

      return false;
    }

    @Override
    public SqlOperator<Number, Range<Number>> negate() {

      return NOT_BETWEEN;
    }
  };

  /** {@link SqlOperator} to check if objects do NOT match using SQL BETWEEN operation. */
  public static final SqlOperator<Number, Range<Number>> NOT_BETWEEN = new SqlOperatorBetween<Number>(false,
      Number.class) {

    @Override
    public boolean evaluate(Number arg1, Range<Number> arg2) {

      return false;
    }

    @Override
    public SqlOperator<Number, Range<Number>> negate() {

      return BETWEEN;
    }
  };

  private final String sql;

  private final Class<T> type;

  /**
   * The constructor.
   *
   * @param sql - see {@link #getSql()}.
   * @param type - see {@link #getType()}.
   */
  protected SqlOperator(String sql, Class<T> type) {
    super();
    this.sql = sql;
    this.type = type;
  }

  /**
   * This method gets the SQL operator representation of this {@link SqlOperator}. E.g. "==", ">", ">=", etc.
   *
   * @return the SQL operator representation.
   */
  public String getSql() {

    return this.sql;
  }

  /**
   * @return the type this {@link SqlOperator} can be {@link #evaluate(Object, Object) applied} to.
   */
  public Class<T> getType() {

    return this.type;
  }

  /**
   * This method evaluates this {@link SqlOperator} for the given arguments.
   *
   * @param arg1 is the first argument.
   * @param arg2 is the second argument.
   * @return the result of the {@link SqlOperator} applied to the given arguments.
   */
  public abstract boolean evaluate(T arg1, S arg2);

  /**
   * @return the negation of this {@link CompareOperator} that {@link #evaluate(Object, Object) evaluates} to the negated
   *         result.
   */
  public abstract SqlOperator<T, S> negate();

  @Override
  public String toString() {

    return this.sql;
  }

  /**
   * Abstract base implementation of {@link SqlOperator} for {@link Number} comparison operations.
   */
  public static abstract class SqlOperatorNumber extends SqlOperator<Number, Number> {

    /**
     * The constructor.
     *
     * @param sql - see {@link #getSql()}.
     */
    public SqlOperatorNumber(String sql) {
      super(sql, Number.class);
    }

    /**
     * @param delta the result of {@link Comparable#compareTo(Object)}.
     * @return the result of {@link #evaluate(Number, Number)}.
     */
    protected abstract boolean eval(int delta);

    /**
     * @return the result of {@link #evaluate(Number, Number)} if one argument is {@code null} and the other is NOT
     *         {@code null}.
     */
    protected boolean evalNullAndNotNull() {

      return !eval(0);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public boolean evaluate(Number arg1, Number arg2) {

      if (arg1 == arg2) {
        return eval(0);
      } else if ((arg1 == null) || (arg2 == null)) {
        return evalNullAndNotNull();
      } else {
        try {
          Comparable c1 = (Comparable) arg1;
          Comparable c2 = (Comparable) arg2;
          return eval(c1.compareTo(c2));
        } catch (ClassCastException e) {
          double delta = arg1.doubleValue() - arg2.doubleValue();
          int d;
          if (delta == 0) {
            d = 0;
          } else if (delta < 0) {
            d = -1;
          } else {
            d = 1;
          }
          return eval(d);
        }
      }
    }
  }

  /**
   * Abstract base implementation of {@link SqlOperator} for {@link String} comparison operations.
   */
  public static abstract class SqlOperatorString extends SqlOperator<String, String> {

    /**
     * The constructor.
     *
     * @param sql - see {@link #getSql()}.
     */
    public SqlOperatorString(String sql) {
      super(sql, String.class);
    }
  }

  /**
   * Abstract base implementation of {@link SqlOperator} for SQL like match operation on {@link String}.
   */
  public static class SqlOperatorLike extends SqlOperatorString {

    private static final char WILDCARD_SINGLE = '_';

    private static final char WILDCARD_ANY = '%';

    /** {@value} */
    public static final String SQL_LIKE = "LIKE";

    /** {@value} */
    public static final String SQL_NOT_LIKE = "NOT LIKE";

    private static final char NO_ESCAPE = '\0';

    private final char escape;

    /**
     * The constructor.
     *
     * @param positive - {@code true} for {@link #SQL_LIKE}, {@code false} for {@link #SQL_NOT_LIKE}.
     */
    public SqlOperatorLike(boolean positive) {
      this(positive, NO_ESCAPE);
    }

    /**
     * The constructor.
     *
     * @param positive - {@code true} for {@link #SQL_LIKE}, {@code false} for {@link #SQL_NOT_LIKE}.
     * @param escape - see {@link #getEscape()}.
     */
    public SqlOperatorLike(boolean positive, char escape) {
      super(positive ? SQL_LIKE : SQL_NOT_LIKE);
      this.escape = escape;
    }

    /**
     * @return the escape character.
     */
    public char getEscape() {

      return this.escape;
    }

    private boolean isPositive() {

      return SQL_LIKE.equals(getSql());
    }

    @Override
    public boolean evaluate(String arg1, String arg2) {

      if (arg1 == arg2) {
        return isPositive();
      } else if ((arg1 == null) || (arg2 == null)) {
        return !isPositive();
      } else {
        int length = arg2.length();
        StringBuilder b = new StringBuilder(length + 4);
        boolean escaped = false;
        for (int i = 0; i < length; i++) {
          char c = arg2.charAt(i);
          if (c == '.') {
            b.append("[.]");
          } else if (c == '*') {
            b.append("[*]");
          } else if ((c == WILDCARD_SINGLE) && (!escaped)) {
            b.append('.');
          } else if ((c == WILDCARD_ANY) && (!escaped)) {
            b.append(".*");
          } else if (c != this.escape) {
            b.append(c);
          }
          escaped = (c == this.escape);
        }
        Pattern pattern = Pattern.compile(b.toString());
        return pattern.matcher(arg1).matches();
      }
    }

    @Override
    public SqlOperator<String, String> negate() {

      return new SqlOperatorLike(!isPositive(), this.escape);
    }
  }

  /**
   * Abstract base implementation of {@link SqlOperator} for comparison of {@link Number} with {@link Range} using SQL
   * BETWEEN operation.
   *
   * @param <N> the genric type of {@link #getType()}.
   */
  public static abstract class SqlOperatorBetween<N extends Number> extends SqlOperator<N, Range<N>> {

    /** {@value} */
    public static final String SQL_BETWEEN = "BETWEEN";

    /** {@value} */
    public static final String SQL_NOT_BETWEEN = "NOT BETWEEN";

    /**
     * The constructor.
     *
     * @param positive - {@code true} for {@link #SQL_BETWEEN}, {@code false} for {@link #SQL_NOT_BETWEEN}.
     * @param type - see {@link #getType()}.
     */
    public SqlOperatorBetween(boolean positive, Class<N> type) {
      super(positive ? SQL_BETWEEN : SQL_NOT_BETWEEN, type);
    }

    private boolean isPositive() {

      return SQL_BETWEEN.equals(getSql());
    }

    @Override
    public boolean evaluate(N arg1, Range<N> arg2) {

      if (arg2 == null) {
        return isPositive();
      }
      return (arg2.isContained(arg1) == isPositive());
    }

  }

}
