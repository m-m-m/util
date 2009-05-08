/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.api;

/**
 * This class is a simple bean that can be used to configure transactions used
 * by the {@link TransactionExecutor}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class TransactionSettings {

  /** @see #getIsolationLevel() */
  private TransactionIsolationLevel isolationLevel;

  /** @see #getTimeout() */
  private Integer timeout;

  /**
   * The constructor.
   */
  public TransactionSettings() {

    super();
  }

  /**
   * This method gets the {@link TransactionIsolationLevel isolation-level}.
   * 
   * @return the isolation-level or <code>null</code> to use the default.
   */
  public TransactionIsolationLevel getIsolationLevel() {

    return this.isolationLevel;
  }

  /**
   * @param isolationLevel is the isolationLevel to set
   */
  public void setIsolationLevel(TransactionIsolationLevel isolationLevel) {

    this.isolationLevel = isolationLevel;
  }

  /**
   * This method gets the timeout.
   * 
   * @return the timeout is seconds or <code>null</code> for no timeout.
   */
  public Integer getTimeout() {

    return this.timeout;
  }

  /**
   * This method sets the {@link #getTimeout() timeout}.
   * 
   * @param timeout is the timeout to set.
   */
  public void setTimeout(Integer timeout) {

    this.timeout = timeout;
  }

}
