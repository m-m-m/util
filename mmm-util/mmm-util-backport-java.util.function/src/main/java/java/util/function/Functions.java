/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package java.util.function;

import java.lang.reflect.Constructor;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Map;

/**
 * This is the back-port for the according class of Java 1.8+.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class Functions {

  /**
   * Construction prohibited.
   */
  private Functions() {

  }

  /**
   * @param <T> is the generic type of the input and result of the function.
   * @return a function whose {@link Function#apply(Object) apply} method returns the provided input.
   */
  public static <T> Function<T, T> identity() {

    return new Function<T, T>() {

      @Override
      public T apply(T t) {

        return t;
      }
    };
  }

  /**
   * Returns a function which performs the first function followed by the second function.
   * 
   * @param <T> is the generic type for inputs to the first function.
   * @param <U> is the generic type for results from first function and inputs to the second function. May be
   *        the same type as {@code <T>}.
   * @param <R> Type for results from the second function. May be the same type as * {@code <U>}.
   * @param first Initial function from {@code <T>} to {@code <U>}.
   * @param second additional function from {@code <U>} to {@code <R>}.
   * @return a function which performs the first function followed by the second function.
   */
  public static <T, U, R> Function<T, R> chain(final Function<? super T, ? extends U> first,
      final Function<? super U, ? extends R> second) {

    if ((first == null) || (second == null)) {
      throw new NullPointerException();
    }

    return new Function<T, R>() {

      @Override
      public R apply(T t) {

        return second.apply(first.apply(t));
      }
    };
  }

  /**
   * Returns a constant output regardless of input.
   * 
   * @param <T> is the generic type of the (ignored) input to the function.
   * @param <R> is the generic type of the result.
   * 
   * @param constant The value to be returned by the {@link Function#apply(Object) apply} method.
   * @return a {@link Function} whose {@link Function#apply(Object) apply} method provides a constant result.
   */
  public static <T, R> Function<T, R> constant(final R constant) {

    return new Function<T, R>() {

      @Override
      public R apply(T t) {

        return constant;
      }
    };
  }

  /**
   * A function that substitutes a single input value with a specified replacement. Input values are compared
   * using {@code equals()}.
   * 
   * @param <T> The type of values.
   * @param subOut The input value to be substituted out.
   * @param subIn The replacement value for matching inputs.
   * @return a {@link Function} that substitutes a single input value with a specified replacement.
   */
  public static <T> Function<T, T> substitute(final T subOut, final T subIn) {

    return new Function<T, T>() {

      @Override
      public T apply(T t) {

        // return (a == b) || (a != null && a.equals(b));
        if ((subOut == t) || ((subOut != null) && (subOut.equals(t)))) {
          return subIn;
        }
        return t;
      }
    };
  }

  /**
   * Returns a new instance of {@code <R>} constructed with provided {@code <T>}.
   * 
   * @param <R> Type of output values from mapping
   * @param <T> Type of input values to mapping
   * @param clazzT The {@code Class} which defines objects of type {@code <T>}
   * @param clazzR The {@code Class} which defines objects of type {@code <U>}
   * @return a {@link Function} which creates instances of {@code <R>} using {@code <T>} as the constructor
   *         parameter.
   */
  public static <T, R> Function<T, R> instantiate(Class<? extends T> clazzT, Class<? extends R> clazzR) {

    if ((clazzT == null) || (clazzR == null)) {
      throw new NullPointerException();
    }

    final Constructor<? extends R> constructor;
    try {
      constructor = clazzR.getConstructor(clazzT);
    } catch (NoSuchMethodException noConstructor) {
      throw new IllegalArgumentException("no constructor for " + clazzR.getSimpleName() + "(" + clazzT.getSimpleName()
          + ")", noConstructor);
    }

    return new Function<T, R>() {

      /**
       * {@inheritDoc}
       */
      @Override
      public R apply(T t) {

        try {
          return constructor.newInstance(t);
        } catch (Exception ex) {
          throw new UndeclaredThrowableException(ex);
        }
      }
    };
  }

  /**
   * Returns a function which maps inputs according to the provided mapping. Attempting to apply a value not
   * from the given map will cause an {@code IllegalArgumentException} to be thrown. A copy is
   * <strong>not</strong> made of the map. Care should be taken to avoid changes to the map during operation
   * may produce results which violate the {@code apply} method contract.
   * 
   * @param <R> output type from mapping operation
   * @param <T> input type to mapping operation
   * @param map provides the mappings from {@code <T>} to {@code <U>}
   * @return the {@link Function} as described above.
   */
  public static <R, T> Function<T, R> forMap(final Map<? super T, ? extends R> map) {

    if (map == null) {
      throw new NullPointerException();
    }

    return new Function<T, R>() {

      @Override
      public R apply(T t) {

        if (map.containsKey(t)) {
          return map.get(t);
        } else {
          throw new IllegalArgumentException("unmappable <T> : " + t);
        }
      }
    };
  }

  /**
   * Returns a function which maps inputs according to the provided mapping. The provided default value is
   * returned for all {@code <T>} keys not found in the map. A copy is <strong>not</strong> made of the apply
   * and care should be taken to avoid changes to the apply during operation may produce results which violate
   * the {@code apply} method contract.
   * 
   * @param <T> input type to mapping function
   * @param <R> output type from mapping function
   * @param <RR> output type from mapping function
   * @param mapping provides the mappings from {@code <T>} to {@code <U>}
   * @param defaultValue the value returned by {@code apply} method for {@code <T>} values not contained in
   *        the provided map.
   * @return the {@link Function} as described above.
   */
  public static <T, R, RR extends R> Function<T, R> forMap(final Map<? super T, RR> mapping, final RR defaultValue) {

    if (mapping == null) {
      throw new NullPointerException();
    }

    return new Function<T, R>() {

      /**
       * {@inheritDoc}
       */
      @Override
      public R apply(T t) {

        R result = mapping.get(t);
        if ((result != null) || (mapping.containsKey(t))) {
          return result;
        } else {
          return defaultValue;
        }
      }
    };
  }

  /**
   * Map according to the provided predicate. Two output values are provided {@code forTrue} is returned if
   * the predicate returns {@code true} otherwise the {@code forFalse} value is returned.
   * 
   * @param <T> input type to mapping function
   * @param <R> output type from mapping function
   * @param predicate decides which value {@code apply} method should return
   * @param forTrue value to be returned for {@code true} predicate results
   * @param forFalse value to be returned for {@code false} predicate results
   * @return a Function whose {@code apply} method provides results according to the provided predicate.
   */
  public static <T, R> Function<T, R> forPredicate(final Predicate<? super T> predicate, final R forTrue,
      final R forFalse) {

    if (predicate == null) {
      throw new NullPointerException();
    }

    return new Function<T, R>() {

      @Override
      public R apply(T t) {

        return predicate.test(t) ? forTrue : forFalse;
      }
    };
  }
}
