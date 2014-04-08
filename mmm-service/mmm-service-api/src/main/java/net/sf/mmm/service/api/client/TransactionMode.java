/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

/**
 * This enum contains the available mode for the transactional behavior of a
 * {@link RemoteInvocationQueue}.
 * 
 * @see RemoteInvocationQueueSettings#getTransactionMode()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum TransactionMode {

  /**
   * Process all invocations to a remote invocation collected in the configured queue and recursively in all
   * sub-queues with the same {@link TransactionMode} in a single transaction on the server. However,
   * sub-queues with a different {@link TransactionMode} will create their own transaction(s).
   */
  ALL_INVOCATIONS,

  /**
   * Process each invocation to a remote invocation in a separate transaction on the server.
   */
  PER_INVOCATION,

  /**
   * Process all invocations to a remote invocation collected in the configured queue in a single transaction
   * on the server. However sub-queues will be processed in separate transactions according to their
   * {@link TransactionMode}.
   * 
   */
  PER_QUEUE
}
