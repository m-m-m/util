/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This is the interface for an async transferrer of streams or readers/writers.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AsyncTransferrer extends Future<Long> {

  /**
   * {@inheritDoc}
   * 
   * @return the number of bytes that have been transferred.
   */
  Long get() throws InterruptedException, ExecutionException;

  /**
   * {@inheritDoc}
   * 
   * @return the number of bytes that have been transferred.
   */
  Long get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;

}
