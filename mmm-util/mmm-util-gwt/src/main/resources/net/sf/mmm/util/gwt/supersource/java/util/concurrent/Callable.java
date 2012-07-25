/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package java.util.concurrent;

/**
 * This is a rewrite of {@link java.util.concurrent.Callable} to allow access in GWT clients.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <V> the generic type of the value returned by {@link #call()}.
 */
public interface Callable<V> {

  /**
   * @return the result of the call.
   * @throws Exception in case of an error.
   */
  V call() throws Exception;
}
