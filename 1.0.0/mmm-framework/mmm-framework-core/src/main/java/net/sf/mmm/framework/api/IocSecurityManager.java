/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.api;

/**
 * This is the interface of a manager that
 * {@link #checkPermission(Class, Class, String) checks} if a
 * {@link ComponentDescriptor#getSpecification() source component} is allowed to
 * access a {@link ComponentDescriptor#getSpecification() target component}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface IocSecurityManager {

  /**
   * This method determines if a component identified by the
   * {@link ComponentDescriptor#getSpecification() specification}
   * <code>source</code> is allowed to
   * {@link ComponentManager#requestComponent(Class) access} a component
   * identified by <code>target</code>.
   * 
   * @param source is the
   *        {@link ComponentDescriptor#getSpecification() specification} of the
   *        {@link ComponentManager#requestComponent(Class, String) requesting}
   *        component.
   * @param target is the
   *        {@link ComponentDescriptor#getSpecification() specification} of the
   *        {@link ComponentManager#requestComponent(Class, String) requested}
   *        component.
   * @param instanceId is the
   *        {@link ComponentManager#requestComponent(Class, String) requested instance-ID}.
   * @return <code>true</code> if <code>source</code> is allowed to access
   *         <code>target</code> with the given <code>instanceId</code>.
   */
  boolean checkPermission(Class source, Class target, String instanceId);

}
