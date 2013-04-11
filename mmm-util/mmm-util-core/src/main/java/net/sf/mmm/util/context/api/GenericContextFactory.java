/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.context.api;

import java.util.Map;

import net.sf.mmm.util.collection.api.MapFactory;
import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.value.api.GenericValueConverter;

/**
 * This is the interface for a factory of generic {@link MutableGenericContext contexts}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@ComponentSpecification
public interface GenericContextFactory {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.util.context.api.GenericContextFactory";

  /**
   * This method creates a new instance of a {@link MutableGenericContext}. It will use a
   * {@link net.sf.mmm.util.collection.base.HashMapFactory} and delegate to {@link #createContext(MapFactory)}
   * .<br>
   * <b>ATTENTION:</b><br>
   * Such context is usually suitable even if {@link GenericContext#createChildContext() child-contexts} are
   * created and passed to other threads while the original thread is modifying the original context as proved
   * in the according test-case. However {@link java.util.HashMap#get(Object)} is not academically thread-safe
   * so if you need mission-critical safety, you might want to pass
   * {@link net.sf.mmm.util.collection.base.ConcurrentHashMapFactory} to {@link #createContext(MapFactory)}.
   * 
   * @return the new context.
   */
  MutableGenericContext createContext();

  /**
   * This method creates a new instance of a {@link MutableGenericContext} using the given {@link MapFactory}.
   * 
   * @param mapFactory is the factory used to create maps for the underlying context.
   * @return the new context.
   */
  @SuppressWarnings("rawtypes")
  MutableGenericContext createContext(MapFactory<? extends Map> mapFactory);

  /**
   * This method creates a new instance of a {@link MutableGenericContext} using the given {@link MapFactory}.
   * 
   * @param mapFactory is the factory used to create maps for the underlying context.
   * @param valueConverter is the {@link GenericValueConverter} used for
   *        {@link GenericValueConverter#convertValue(Object, Object, Class, Object) converting} variables if
   *        {@link GenericContext#getVariable(String, Class) requested} for a different type.
   * @return the new context.
   * @since 2.0.0
   */
  @SuppressWarnings("rawtypes")
  MutableGenericContext createContext(MapFactory<? extends Map> mapFactory, GenericValueConverter<Object> valueConverter);

}
