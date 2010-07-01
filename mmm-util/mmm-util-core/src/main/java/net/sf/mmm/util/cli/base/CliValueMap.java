/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;

/**
 * This class is a container for the values for
 * {@link net.sf.mmm.util.cli.api.CliOption options} and
 * {@link net.sf.mmm.util.cli.api.CliArgument arguments}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliValueMap {

  /** @see #getOrCreate(CliParameterContainer) */
  private final Map<CliParameterContainer, CliValueContainer> map;

  /** The {@link CliParserConfiguration}. */
  private final CliParserConfiguration configuration;

  /**
   * The constructor.
   * 
   * @param configuration is the {@link CliParserConfiguration}.
   */
  public CliValueMap(CliParserConfiguration configuration) {

    super();
    this.map = new HashMap<CliParameterContainer, CliValueContainer>();
    this.configuration = configuration;
  }

  /**
   * This method gets the {@link CliValueContainer} for the given
   * {@link CliParameterContainer}.
   * 
   * @param parameterContainer is the {@link CliParameterContainer} that acts as
   *        key to the requested {@link CliValueContainerObject}.
   * @return the requested {@link CliValueContainerObject} or <code>null</code>
   *         if NOT present.
   */
  public CliValueContainer get(CliParameterContainer parameterContainer) {

    return this.map.get(parameterContainer);
  }

  /**
   * This method gets the {@link CliValueContainer} for the given
   * {@link CliParameterContainer}. In advance to
   * {@link #get(CliParameterContainer)} this method will create an according
   * {@link CliValueContainerObject} if not present and the
   * {@link CliParameterContainer} has a
   * {@link CliParameterContainer#getSetter() setter} with a
   * {@link PojoPropertyAccessorOneArg#getPropertyType() property-type}
   * reflecting an array, {@link Collection} or {@link Map}.
   * 
   * @param parameterContainer is the {@link CliParameterContainer} that acts as
   *        key to the requested {@link CliValueContainerObject}.
   * @return the requested {@link CliValueContainerObject} or <code>null</code>
   *         if NOT present and NOT created.
   */
  @SuppressWarnings("unchecked")
  public CliValueContainer getOrCreate(CliParameterContainer parameterContainer) {

    CliValueContainer result = this.map.get(parameterContainer);
    if (result == null) {
      PojoPropertyAccessorOneArg setter = parameterContainer.getSetter();
      Class<?> propertyClass = setter.getPropertyClass();
      if (propertyClass.isArray()) {
        result = new CliValueContainerArray(this.configuration.getCollectionReflectionUtil(),
            propertyClass.getComponentType());
      } else if (Collection.class.isAssignableFrom(propertyClass)) {
        Class<? extends Collection<?>> collectionClass = (Class<? extends Collection<?>>) propertyClass;
        Collection<Object> collection = this.configuration.getCollectionFactoryManager()
            .getCollectionFactory(collectionClass).create();
        result = new CliValueContainerCollection(collection);
      } else if (Map.class.isAssignableFrom(propertyClass)) {
        Class<? extends Map<?, ?>> mapClass = (Class<? extends Map<?, ?>>) propertyClass;
        Map<Object, Object> mapValue = this.configuration.getCollectionFactoryManager()
            .getMapFactory(mapClass).create();
        result = new CliValueContainerMap(mapValue);
      } else {
        result = new CliValueContainerObject();
      }
      this.map.put(parameterContainer, result);
    }
    return result;
  }

  /**
   * This method applies the parsed CLI values to the given <code>state</code>.
   * 
   * @param state is the {@link AbstractCliParser#getState() state-object} where
   *        to apply the values of this {@link CliValueMap}.
   */
  public void assign(Object state) {

    for (CliParameterContainer parameter : this.map.keySet()) {
      CliValueContainer valueContainer = this.map.get(parameter);
      parameter.getSetter().invoke(state, valueContainer.getValue());
    }
  }

}