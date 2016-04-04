/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import javafx.beans.property.ReadOnlyProperty;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.lang.IntegerProperty;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the interface for a generic property.
 *
 * @param <V> is the generic type of the {@link #getValue() value}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface ReadableProperty<V> extends ReadOnlyProperty<V>, PropertyPath<V> {

  /**
   * @return the type of the property. Please note that the generic signature of this method is returning the type
   *         {@link GenericType}{@literal <? extends VALUE>} instead of {@link GenericType}{@literal <VALUE>} because
   *         JavaFx is binding the type {@link Number} for all numeric properties while e.g. {@link IntegerProperty}
   *         should be able to return {@link GenericType}{@literal <Integer>} what is compatible with
   *         {@link GenericType}{@literal <? extends Number>}.
   */
  GenericType<? extends V> getType();

  @Override
  Bean getBean();

}
