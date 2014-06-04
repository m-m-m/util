/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base.jaxb;

import net.sf.mmm.util.component.api.Refreshable;

/**
 * This class is a container {@link #getBean() holding} a JAXB bean. Storing this {@link JaxbBeanHolder}
 * instead of the {@link #getBean() bean} itself in a field of some class allows the actual {@link #getBean()
 * bean} to be {@link #refresh() reloaded} in the meantime. If {@link java.io.Flushable} is implemented, then
 * it can also be saved via {@link java.io.Flushable#flush()}.
 * 
 * @param <T> is the generic type of the {@link #getBean() JAXB bean}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface JaxbBeanHolder<T> extends Refreshable {

  /**
   * This method gets the current JAXB bean.
   * 
   * @return the current JAXB bean.
   */
  T getBean();

  /**
   * This method will reload the {@link #getBean() configuration} if it has changed.
   * 
   * {@inheritDoc}
   */
  boolean refresh();

}
