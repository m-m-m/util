/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

/**
 * This enum contains the available states of a {@link RemoteInvocationQueue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum RemoteInvocationQueueState {

  /**
   * The initial state until {@link RemoteInvocationQueue#commit()} or {@link RemoteInvocationQueue#cancel()}
   * is called for the first time.
   */
  OPEN,

  /**
   * The state if the {@link RemoteInvocationQueue} has been {@link RemoteInvocationQueue#commit() committed}.
   */
  COMITTED,

  /**
   * The state if the {@link RemoteInvocationQueue} has been {@link RemoteInvocationQueue#cancel() cancelled}.
   */
  CANCELLED,

  /**
   * The state if the {@link RemoteInvocationQueue} was to be {@link RemoteInvocationQueue#commit() committed}
   * but this failed.
   */
  FAILED,

}
