/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.test;

import org.junit.Assert;

/**
 * This is a utility-class with static methods that help to write negative-tests
 * that check expected exceptions.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public final class ExceptionHelper {

  /**
   * The constructor.
   */
  private ExceptionHelper() {

    super();
  }

  /**
   * This method should be invoked in try-blocks of tests that expect an
   * exception to be thrown.
   */
  public static void failExceptionExpected() {

    Assert.fail("Exception expected");
  }

  /**
   * This method should be invoked in catch-block of tests that catched a given
   * exception and expect that this exception or one of its
   * {@link Throwable#getCause() causes} are an {@link Class#isInstance(Object)
   * instance of} the given <code>expectedType</code>.
   * 
   * @param catched is the {@link Throwable} that has been catched.
   * @param expectedType is the {@link Class} of the {@link Throwable} that is
   *        expected.
   */
  public static void assertCause(Throwable catched, Class<? extends Throwable> expectedType) {

    Throwable t = catched;
    while (!expectedType.isInstance(t)) {
      Throwable cause = t.getCause();
      if ((cause == null) || (cause == t)) {
        Assert.fail(expectedType.getName() + " expected!");
      }
      t = cause;
    }
  }

}
