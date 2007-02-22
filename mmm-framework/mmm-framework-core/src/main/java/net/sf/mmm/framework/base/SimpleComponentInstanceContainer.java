/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.api.ComponentInstanceContainer;
import net.sf.mmm.framework.api.ComponentManager;

/**
 * This is the default implementation of the
 * {@link ComponentInstanceContainer} interface.
 * 
 * @param <S>
 *        is the
 *        {@link net.sf.mmm.framework.api.ComponentDescriptor#getSpecification() specification}
 *        of the {@link #getInstance() component}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimpleComponentInstanceContainer<S> implements ComponentInstanceContainer<S> {

  /** @see #getInstanceId() */
  private String instanceId;

  /** @see #getInstance() */
  private S instance;

  /**
   * The Bean constructor.
   */
  public SimpleComponentInstanceContainer() {

    this(null, null);
  }

  /**
   * The constructor.
   * 
   * @param componentInstance
   *        is the component {@link #getInstance() instance}.
   */
  public SimpleComponentInstanceContainer(S componentInstance) {

    this(componentInstance, ComponentManager.DEFAULT_INSTANCE_ID);
  }

  /**
   * The constructor.
   * 
   * @param componentInstance
   *        is the component {@link #getInstance() instance}.
   * @param componentId
   *        is the {@link #getInstanceId() ID} of the component instance.
   */
  public SimpleComponentInstanceContainer(S componentInstance, String componentId) {

    super();
    this.instanceId = componentId;
    this.instance = componentInstance;
  }

  /**
   * @see net.sf.mmm.framework.api.ComponentInstanceContainer#getInstance()
   */
  public S getInstance() {

    return this.instance;
  }

  /**
   * This method sets the {@link #getInstance() instance}. It should be
   * called only once.
   * 
   * @param newInstance
   *        is the instance to set.
   */
  public void setInstance(S newInstance) {

    if (this.instance != null) {
      throw new IllegalArgumentException("Instance already set!");
    }
    this.instance = newInstance;
  }

  /**
   * @see net.sf.mmm.framework.api.ComponentInstanceContainer#getInstanceId()
   */
  public String getInstanceId() {

    return this.instanceId;
  }

  /**
   * This method sets the {@link #getInstanceId() instanceId}.
   * 
   * @param newInstanceId
   *        is the instanceId to set.
   */
  public void setInstanceId(String newInstanceId) {

    if (this.instanceId != null) {
      throw new IllegalArgumentException("Instance-id already set!");
    }
    this.instanceId = newInstanceId;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    int bufferSize = 0;
    String instanceString;
    String instanceClass;
    if (this.instance == null) {
      bufferSize += 4;
      instanceString = null;
      instanceClass = null;
    } else {
      instanceString = this.instance.toString();
      instanceClass = this.instance.getClass().getName();
      if (instanceString.startsWith(instanceClass)) {
        instanceClass = null;
        bufferSize += instanceString.length();
      } else {
        bufferSize += instanceString.length() + instanceClass.length() + 2;
      }
    }
    if (this.instanceId != null) {
      bufferSize += this.instanceId.length() + 6;
    }
    StringBuffer sb = new StringBuffer(bufferSize);
    if (instanceString == null) {
      sb.append("null");
    } else {
      sb.append(instanceString);
      if (instanceClass != null) {
        sb.append('[');
        sb.append(instanceClass);
        sb.append(']');
      }
    }
    if (this.instanceId != null) {
      sb.append(" [id=");
      sb.append(this.instanceId);
      sb.append(']');
    }
    assert (sb.length() == bufferSize);
    return sb.toString();
  }

}
