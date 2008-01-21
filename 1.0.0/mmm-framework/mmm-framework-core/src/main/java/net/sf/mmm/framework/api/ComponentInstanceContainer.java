/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.api;

/**
 * This is the interface of a container for a single
 * {@link #getInstance() instance} of the component for a specific
 * {@link ComponentProvider provider}.
 * 
 * @param <S> is the
 *        {@link ComponentDescriptor#getSpecification() specification} of the
 *        component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ComponentInstanceContainer<S> {

  /**
   * This method gets the component instance.
   * 
   * @return the component instance.
   */
  S getInstance();

  /**
   * This method gets the
   * {@link ComponentManager#requestComponent(Class, String) instance-ID} of
   * this component {@link #getInstance() instance}. The default is
   * <code>null</code>.
   * 
   * @return the instance-ID.
   */
  String getInstanceId();

  /**
   * This method gets the string representation of this object. It should
   * contain the {@link Object#toString() string representation} of the
   * {@link #getInstance() instance} itself, the
   * {@link #getInstanceId() instance-ID} and the
   * {@link ComponentDescriptor#getSpecification() specification} of the
   * component.
   * 
   * @see java.lang.Object#toString()
   */
  String toString();

}
