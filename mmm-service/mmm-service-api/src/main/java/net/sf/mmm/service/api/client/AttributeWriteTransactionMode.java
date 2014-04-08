/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;


/**
 * This interface gives read and write access to the {@link #getTransactionMode() transactionMode} property of
 * an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteTransactionMode {

  /**
   * This method gets the {@link TransactionMode}.
   * 
   * @return the {@link TransactionMode}.
   */
  TransactionMode getTransactionMode();

  /**
   * @param transactionMode is the new value of {@link #getTransactionMode()}.
   */
  void setTransactionMode(TransactionMode transactionMode);

}
