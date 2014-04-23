/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package java.util.function;

import java.util.Arrays;
import java.util.Iterator;

/**
 * This is the back-port for the according class of Java 1.8+.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class Predicates {

  /**
   * Construction prohibited.
   */
  private Predicates() {

  }

  /**
   * Returns a {@link Predicate} that who's result is <code>target == object</code>.
   * 
   * @param <T> the type of values evaluated by the {@link Predicate}.
   * @param target The target value to be compared for identity equality.
   * @return a {@link Predicate} that who's result is <code>target == object</code>.
   */
  public static <T> Predicate<T> isSame(final Object target) {

    return new Predicate<T>() {

      @Override
      public boolean test(T value) {

        return (value == target);
      }
    };
  }

  /**
   * Returns a {@link Predicate} who's result matches a null-safe {@link Object#equals(Object) equals}.
   * 
   * @param <T> the type of values evaluated by the {@link Predicate}.
   * @param target is the target value to be compared for equality.
   * @return a {@link Predicate} who's result matches a null-safe {@link Object#equals(Object) equals}.
   */
  public static <T> Predicate<T> isEqual(final Object target) {

    if (target == null) {
      return new Predicate<T>() {

        @Override
        public boolean test(T value) {

          return (value == null);
        }
      };
    } else {
      return new Predicate<T>() {

        @Override
        public boolean test(T value) {

          return target.equals(value);
        }
      };
    }
  }

  /**
   * Returns a {@link Predicate} that evaluates to <code>true</code> if all of the component
   * {@link Predicates} evaluate to <code>true</code>. The components are evaluated in order, and evaluation
   * will end upon the first <code>false</code> {@link Predicate}.
   * 
   * @param <T> the type of values evaluated by the predicates.
   * @param predicates The predicates to be evaluated.
   * @return A {@link Predicate} who's result is <code>true</code> if all component {@link Predicates} are
   *         <code>true</code> and <code>false</code> otherwise.
   */
  public static <T> Predicate<T> and(final Iterable<? extends Predicate<? super T>> predicates) {

    if (predicates == null) {
      throw new NullPointerException();
    }

    return new Predicate<T>() {

      @Override
      public boolean test(T value) {

        for (Predicate<? super T> predicate : predicates) {
          if (!predicate.test(value)) {
            return false;
          }
        }
        return true;
      }
    };
  }

  /**
   * Returns a {@link Predicate} that evaluates to <code>true</code> if all of the component predicates
   * evaluate to <code>true</code>. The components are evaluated in order, and evaluation will end upon the
   * first <code>false</code> predicate.
   * 
   * @param <T> the type of values evaluated by the predicates.
   * @param first An initial predicate to be evaluated before the others.
   * @param predicates The predicates to be evaluated.
   * @return A predicate who's result is <code>true</code> iff all component predicates are <code>true</code>.
   */
  static <T> Predicate<T> and(final Predicate<? super T> first,
      final Iterable<? extends Predicate<? super T>> predicates) {

    if ((first == null) || (predicates == null)) {
      throw new NullPointerException();
    }

    return new Predicate<T>() {

      @Override
      public boolean test(T value) {

        if (!first.test(value)) {
          return false;
        }
        for (Predicate<? super T> predicate : predicates) {
          if (!predicate.test(value)) {
            return false;
          }
        }
        return true;
      }
    };
  }

  /**
   * Returns a {@link Predicate} that evaluates to <code>true</code> if all of the component predicates
   * evaluate to <code>true</code>. The components are evaluated in order, and evaluation will end upon the
   * first <code>false</code> predicate.
   * 
   * @param <T> the type of values evaluated by the predicates.
   * @param predicates The predicates to be evaluated.
   * @return A predicate who's result is <code>true</code> iff all component predicates are <code>true</code>.
   */
  public static <T> Predicate<T> and(Predicate<? super T>... predicates) {

    return and(Arrays.asList(predicates));
  }

  /**
   * Returns a {@link Predicate} that evaluates to <code>true</code> if all of the component predicates
   * evaluate to <code>true</code>. The components are evaluated in order, and evaluation will end upon the
   * first <code>false</code> predicate.
   * 
   * @param <T> the type of values evaluated by the predicates.
   * @param first An initial predicate to be evaluated.
   * @param predicates The predicates to be evaluated.
   * @return A predicate who's result is <code>true</code> iff all component predicates are <code>true</code>.
   */
  static <T> Predicate<T> and(Predicate<? super T> first, Predicate<? super T>... predicates) {

    return and(first, Arrays.asList(predicates));
  }

  /**
   * Returns a {@link Predicate} that evaluates to <code>true</code> if any of the component predicates
   * evaluate to <code>true</code>. The components are evaluated in order, and evaluation will end upon the
   * first <code>true</code> {@link Predicate}.
   * 
   * @param <T> the type of values evaluated by the predicates.
   * @param predicates The {@link Predicate}s to be evaluated.
   * @return A predicate who's result is <code>true</code> if any component {@link Predicate}'s result is
   *         <code>true</code>.
   */
  public static <T> Predicate<T> or(final Iterable<? extends Predicate<? super T>> predicates) {

    if (predicates == null) {
      throw new NullPointerException();
    }

    return new Predicate<T>() {

      @Override
      public boolean test(T value) {

        for (Predicate<? super T> predicate : predicates) {
          if (predicate.test(value)) {
            return true;
          }
        }
        return false;
      };
    };
  }

  /**
   * Returns a {@link Predicate} that evaluates to <code>true</code> if any of the component predicates
   * evaluate to <code>true</code>. The components are evaluated in order, and evaluation will end upon the
   * first <code>true</code> {@link Predicate}.
   * 
   * @param <T> the type of values evaluated by the predicates.
   * @param first is the first {@link Predicate} to be evaluated.
   * @param predicates The predicates to be evaluated.
   * @return A predicate who's result is <code>true</code> if any component {@link Predicate}'s result is
   *         <code>true</code>.
   */
  static <T> Predicate<T> or(final Predicate<? super T> first, final Iterable<? extends Predicate<? super T>> predicates) {

    if ((first == null) || (predicates == null)) {
      throw new NullPointerException();
    }

    return new Predicate<T>() {

      @Override
      public boolean test(T value) {

        if (first.test(value)) {
          return true;
        }
        for (Predicate<? super T> predicate : predicates) {
          if (predicate.test(value)) {
            return true;
          }
        }
        return false;
      }
    };
  }

  /**
   * Returns a {@link Predicate} that evaluates to <code>true</code> if any of the component predicates
   * evaluate to <code>true</code>. The components are evaluated in order, and evaluation will terminate upon
   * the first <code>true</code> {@link Predicate}.
   * 
   * @param <T> the type of values evaluated by the predicates.
   * @param predicates The predicates to be evaluated.
   * @return A predicate who's result is <code>true</code> if any component {@link Predicate}'s result is
   *         <code>true</code>.
   */
  public static <T> Predicate<T> or(Predicate<? super T>... predicates) {

    return or(Arrays.asList(predicates));
  }

  /**
   * Returns a {@link Predicate} that evaluates to <code>true</code> if any of the component predicates
   * evaluate to <code>true</code>. The components are evaluated in order, and evaluation will terminate upon
   * the first <code>true</code> predicate.
   * 
   * @param <T> the type of values evaluated by the predicates.
   * @param first is the first {@link Predicate} to be evaluated.
   * @param predicates The predicates to be evaluated.
   * @return A predicate who's result is <code>true</code> if any component {@link Predicate}'s result is
   *         <code>true</code>.
   */
  static <T> Predicate<T> or(Predicate<? super T> first, Predicate<? super T>... predicates) {

    return or(first, Arrays.asList(predicates));
  }

  /**
   * Returns a {@link Predicate} that evaluates to <code>false</code> if all or none of the component
   * {@link Predicate}s evaluate to <code>true</code>. The components are evaluated in order, and evaluation
   * will end if a {@link Predicate} result fails to match the first {@link Predicate}'s result.
   * 
   * @param <T> the type of values evaluated by the predicates.
   * @param predicates The {@link Predicate}s to be evaluated.
   * @return a {@link Predicate} that evaluates to <code>false</code> if all or none of the component
   *         predicates evaluate to <code>true</code>
   */
  public static <T> Predicate<T> xor(final Iterable<? extends Predicate<? super T>> predicates) {

    if (predicates == null) {
      throw new NullPointerException();
    }

    return new Predicate<T>() {

      @Override
      public boolean test(T value) {

        Iterator<? extends Predicate<? super T>> iterator = predicates.iterator();
        if (!iterator.hasNext()) {
          return false;
        }
        boolean initial = iterator.next().test(value);
        while (iterator.hasNext()) {
          boolean current = iterator.next().test(value);
          if (!(initial ^ current)) {
            return false;
          }
        }
        return true;
      }
    };
  }

  /**
   * Returns a {@link Predicate} that evaluates to <code>false</code> if all or none of the component
   * predicates evaluate to <code>true</code>. The components are evaluated in order, and evaluation will end
   * if a {@link Predicate} result fails to match the first {@link Predicate}'s result.
   * 
   * @param <T> the type of values evaluated by the predicates.
   * @param first is the first {@link Predicate} to be evaluated.
   * @param predicates The {@link Predicate}s to be evaluated.
   * @return a {@link Predicate} that evaluates to <code>false</code> if all or none of the component
   *         predicates evaluate to <code>true</code>
   */
  static <T> Predicate<T> xor(final Predicate<? super T> first,
      final Iterable<? extends Predicate<? super T>> predicates) {

    if ((first == null) || (predicates == null)) {
      throw new NullPointerException();
    }
    return new Predicate<T>() {

      @Override
      public boolean test(T value) {

        boolean initial = first.test(value);
        for (Predicate<? super T> predicate : predicates) {
          if (!(initial ^ predicate.test(value))) {
            return false;
          }
        }
        return true;
      }
    };
  }

  /**
   * Returns a {@link Predicate} that evaluates to <code>false</code> if all or none of the component
   * predicates evaluate to <code>true</code>. The components are evaluated in order, and evaluation will
   * terminate if a {@link Predicate} result fails to match the first {@link Predicate}'s result.
   * 
   * @param <T> the type of values evaluated by the predicates.
   * @param predicates The {@link Predicate}s to be evaluated.
   * @return a {@link Predicate} that evaluates to <code>false</code> if all or none of the component
   *         predicates evaluate to <code>true</code>
   */
  public static <T> Predicate<T> xor(Predicate<? super T>... predicates) {

    return xor(Arrays.asList(predicates));
  }

  /**
   * Returns a {@link Predicate} that evaluates to <code>false</code> if all or none of the component
   * predicates evaluate to <code>true</code>. The components are evaluated in order, and evaluation will end
   * if a {@link Predicate} result fails to match the first {@link Predicate}'s result.
   * 
   * @param <T> the type of values evaluated by the predicates.
   * @param first is the first {@link Predicate} to be evaluated.
   * @param predicates The {@link Predicate}s to be evaluated.
   * @return a {@link Predicate} that evaluates to <code>false</code> if all or none of the component
   *         predicates evaluate to <code>true</code>
   */
  static <T> Predicate<T> xor(Predicate<? super T> first, Predicate<? super T>... predicates) {

    return xor(first, Arrays.asList(predicates));
  }

}
