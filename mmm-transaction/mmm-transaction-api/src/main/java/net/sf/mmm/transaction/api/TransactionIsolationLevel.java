/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.api;

import java.sql.Connection;

/**
 * This enum contains the available isolation-levels for a transaction as
 * defined by{@link Connection#getTransactionIsolation()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum TransactionIsolationLevel {

  /**
   * @see Connection#TRANSACTION_NONE
   */
  NONE(Connection.TRANSACTION_NONE),

  /**
   * @see Connection#TRANSACTION_READ_UNCOMMITTED
   */
  READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),

  /**
   * @see Connection#TRANSACTION_READ_COMMITTED
   */
  READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),

  /**
   * @see Connection#TRANSACTION_REPEATABLE_READ
   */
  REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),

  /**
   * @see Connection#TRANSACTION_SERIALIZABLE
   */
  SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);

  /** @see #getJdbcCode() */
  private final int jdbcCode;

  /**
   * The constructor.
   * 
   * @param jdbcCode is the {@link #getJdbcCode() JDBC-Code}.
   */
  private TransactionIsolationLevel(int jdbcCode) {

    this.jdbcCode = jdbcCode;
  }

  /**
   * This method gets the JDBC-code according to this isolation-level as defined
   * by {@link Connection#getTransactionIsolation()}.
   * 
   * @return the according JDBC-code.
   */
  public int getJdbcCode() {

    return this.jdbcCode;
  }

}
