/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.process;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This is the interface for an async executor of a {@link Process}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface AsyncProcessExecutor extends Future<Integer> {

  /**
   * {@inheritDoc}
   * 
   * @return the {@link Process#waitFor() exit-code} of the process.
   */
  Integer get() throws InterruptedException, ExecutionException;

  /**
   * {@inheritDoc}
   * 
   * @return the {@link Process#waitFor() exit-code} of the process.
   */
  Integer get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException,
      TimeoutException;

}
