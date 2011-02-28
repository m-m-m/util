/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package java.util.concurrent;

/**
 * This is a signature compatible "rewrite" of the same interface from the JDK.
 */
public interface Callable<V> {

  V call() throws Exception;
}
