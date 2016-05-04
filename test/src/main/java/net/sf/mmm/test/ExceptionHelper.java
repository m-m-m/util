/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.test;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a utility-class with static methods that help to write negative-tests that check expected exceptions.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class ExceptionHelper {

  /** The {@link Logger} instance. */
  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHelper.class);

  /**
   * The constructor.
   */
  private ExceptionHelper() {

    super();
  }

  /**
   * This method should be invoked in try-blocks of tests that expect an exception to be thrown.
   */
  public static void failExceptionExpected() {

    Assert.fail("Exception expected");
  }

  /**
   * This method should be invoked in catch-block of tests that catched a given exception and expect that this exception
   * or one of its {@link Throwable#getCause() causes} are an {@link Class#isInstance(Object) instance of} the given
   * {@code expectedType}.
   *
   * @param <T> is the generic type of the expected cause.
   * @param catched is the {@link Throwable} that has been catched.
   * @param expectedType is the {@link Class} of the {@link Throwable} that is expected.
   * @return the first {@link Throwable} in the hierarchy of {@code catched} matching to the given {@code expectedType}.
   */
  public static <T extends Throwable> T assertCause(Throwable catched, Class<T> expectedType) {

    T cause = getCause(catched, expectedType);
    if (cause == null) {
      String message = expectedType.getName() + " expected but catched " + catched.getClass().getName() + "!";
      LOGGER.error(message, catched);
      Assert.fail(message);
    }
    return cause;
  }

  /**
   * This method checks if a catched exception or one of its {@link Throwable#getCause() causes} is an
   * {@link Class#isInstance(Object) instance of} the given {@code expectedType}.
   *
   * @param catched is the {@link Throwable} that has been catched.
   * @param expectedType is the {@link Class} of the {@link Throwable} that is expected.
   * @return {@code true} if the given {@code catched} or one of its {@link Throwable#getCause() causes} is an
   *         {@link Class#isInstance(Object) instance of} the given {@code expectedType}.
   */
  public static boolean isCause(Throwable catched, Class<? extends Throwable> expectedType) {

    Throwable cause = getCause(catched, expectedType);
    return (cause != null);
  }

  /**
   * This method gets a catched exception or one of its {@link Throwable#getCause() causes} that is an
   * {@link Class#isInstance(Object) instance of} the given {@code expectedType}.
   *
   * @param <T> is the generic type of the expected cause.
   * @param catched is the {@link Throwable} that has been catched.
   * @param expectedType is the {@link Class} of the {@link Throwable} that is expected.
   * @return {@code true} if the given {@code catched} or one of its {@link Throwable#getCause() causes} is an
   *         {@link Class#isInstance(Object) instance of} the given {@code expectedType}.
   */
  @SuppressWarnings("unchecked")
  public static <T extends Throwable> T getCause(Throwable catched, Class<T> expectedType) {

    Throwable t = catched;
    while (!expectedType.isInstance(t)) {
      Throwable cause = t.getCause();
      if ((cause == null) || (cause == t)) {
        return null;
      }
      t = cause;
    }
    return (T) t;
  }

  /**
   * This method should be invoked in catch-block of tests that catched a given exception and expect that this exception
   * or one of its {@link Throwable#getCause() causes} is the same as the given {@code expectedCause}.
   *
   * @param catched is the {@link Throwable} that has been catched.
   * @param expectedCause is the {@link Throwable} that is expected as .
   */
  public static void assertCause(Throwable catched, Throwable expectedCause) {

    if (!isCause(catched, expectedCause)) {
      Assert.fail(expectedCause + " expected!");
    }
  }

  /**
   * This method should be invoked in catch-block of tests that catched a given exception and expect that this exception
   * or one of its {@link Throwable#getCause() causes} is the same as the given {@code expectedCause}.
   *
   * @param catched is the {@link Throwable} that has been catched.
   * @param expectedCause is the {@link Throwable} that is expected as .
   * @return {@code true} if the given {@code expectedCause} is the same as {@code catched} or one of its
   *         {@link Throwable#getCause() causes}.
   */
  public static boolean isCause(Throwable catched, Throwable expectedCause) {

    Throwable t = catched;
    while (expectedCause != t) {
      Throwable cause = t.getCause();
      if ((cause == null) || (cause == t)) {
        return false;
      }
      t = cause;
    }
    return true;
  }

}
