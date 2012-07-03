/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

/**
 * This is a simple java bean with properties used to configure a
 * {@link RemoteInvocationServiceCaller#newQueue(RemoteInvocationServiceQueueSettings) new}
 * {@link RemoteInvocationServiceQueue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RemoteInvocationServiceQueueSettings {

  /** @see #getId() */
  private String id;

  /** @see #isRejectNestedInvocation() */
  private boolean rejectNestedInvocation;

  /**
   * The constructor.
   */
  public RemoteInvocationServiceQueueSettings() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param id - see {@link #getId()}.
   */
  public RemoteInvocationServiceQueueSettings(String id) {

    super();
    this.id = id;
  }

  /**
   * This method gets the {@link RemoteInvocationServiceQueue#getId() identifier} of the
   * {@link RemoteInvocationServiceQueue} to
   * {@link RemoteInvocationServiceCaller#newQueue(RemoteInvocationServiceQueueSettings) create}.
   * 
   * @see RemoteInvocationServiceQueue#getId()
   * 
   * @return the ID of the {@link RemoteInvocationServiceQueue} to
   *         {@link RemoteInvocationServiceCaller#newQueue(RemoteInvocationServiceQueueSettings) create} or
   *         <code>null</code> for none.
   */
  public String getId() {

    return this.id;
  }

  /**
   * Sets the {@link #getId() ID}.
   * 
   * @param id is the ID for the {@link RemoteInvocationServiceQueue} to
   *        {@link RemoteInvocationServiceCaller#newQueue(RemoteInvocationServiceQueueSettings) create}.
   */
  public void setId(String id) {

    this.id = id;
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
