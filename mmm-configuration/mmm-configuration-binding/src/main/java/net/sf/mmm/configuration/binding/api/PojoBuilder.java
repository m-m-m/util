/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.binding.api;

import net.sf.mmm.configuration.api.Configuration;

/**
 * This is a call-back interface used to create (instantiate) POJOs.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoBuilder {

  /**
   * This method is called if the
   * {@link ConfigurationBindingService binding-service}
   * {@link ConfigurationBindingService#configure(Configuration, Object) configures}
   * a POJO with a <em>complex</em> property. Here <code>complex</code>
   * means that the <code>type</code> is NOT any of the following:
   * <ul>
   * <li>{@link String}</li>
   * <li>{@link Boolean}</li>
   * <li>{@link Class}</li>
   * <li>{@link Number}</li>
   * <li>{@link Integer}</li>
   * <li>{@link Long}</li>
   * <li>{@link Double}</li>
   * <li>{@link Float}</li>
   * <li>{@link Short}</li>
   * <li>{@link Byte}</li>
   * <li>{@link java.util.Date}</li>
   * <li>{@link java.util.Collection}</li>
   * <li>{@link java.util.List}</li>
   * <li>{@link java.util.Set}</li>
   * <li>{@link java.util.Map}</li>
   * <li>an {@link java.lang.reflect.Array array}</li>
   * </ul>
   * Please note that for arrays and collection types the items will be
   * constructed via this method if their component-type is <code>complex</code>.
   * 
   * 
   * @see net.sf.mmm.value.api.GenericValue#getValue(Class, Object)
   * 
   * @param <O>
   *        is the templated type of the expected type.
   * @param configuration
   *        is the configuration to create.
   * @param type
   *        is the class reflecting the expected type (may be abstract class or
   *        interface).
   * @return the requested instance of the given <code>type</code>. This
   *         method should only construct the instance and may therefore use the
   *         given <code>configuration</code> (e.g. for constructor
   *         arguments). Further injection (setters, fields) is performed
   *         automatically by the {@link ConfigurationBindingService} and should
   *         NOT be covered by the implementation of this method.
   */
  <O> O create(Configuration configuration, Class<O> type);

}
