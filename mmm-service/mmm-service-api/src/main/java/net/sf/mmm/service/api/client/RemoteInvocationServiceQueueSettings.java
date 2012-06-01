/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

/**
 * This is a simple java bean with properties used to configure a
 * {@link RemoteInvocationServiceCaller#newQueue(RemoteInvocationServiceQueueSettings) new}
 * {@link RemoteInvocationServiceQueue}.
 * 
 * @author hohwille
 * @since 1.0.0
 */
public class RemoteInvocationServiceQueueSettings {

  /** @see #isOmitBusyIndication() */
  private boolean omitBusyIndication;

  /** @see #isRejectNestedInvocation() */
  private boolean rejectNestedInvocation;

  /**
   * The constructor.
   * 
   */
  public RemoteInvocationServiceQueueSettings() {

    super();
  }

  /**
   * @return the omitBusyIndication
   */
  public boolean isOmitBusyIndication() {

    return this.omitBusyIndication;
  }

  /**
   * @param omitBusyIndication is the omitBusyIndication to set
   */
  public void setOmitBusyIndication(boolean omitBusyIndication) {

    this.omitBusyIndication = omitBusyIndication;
  }

  /**
   * @return the rejectNestedInvocation
   */
  public boolean isRejectNestedInvocation() {

    return this.rejectNestedInvocation;
  }

  /**
   * @param rejectNestedInvocation is the rejectNestedInvocation to set
   */
  public void setRejectNestedInvocation(boolean rejectNestedInvocation) {

    this.rejectNestedInvocation = rejectNestedInvocation;
  }

}
