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
   * @return the name of the property. By convention it should start with a {@link Character#isUpperCase(char) capital}
   *         letter followed by alpha-numeric characters. The name of a single property must especially not contain the
   *         dot character (.) that is used to separate segments in a {@link PropertyPath path}.
   */
  @Override
  String getName();

  /**
   * @return the type of the property {@link #getValue() value}. Please note that the generic signature of this method
   *         is returning the type {@link GenericType}{@literal <? extends V>} instead of {@link GenericType}
   *         {@literal <V>} because JavaFx is binding the type {@link Number} for all numeric properties while e.g.
   *         {@link IntegerProperty} should be able to return {@link GenericType}{@literal <Integer>} what is compatible
   *         with {@link GenericType}{@literal <? extends Number>}.
   */
  GenericType<? extends V> getType();

  @Override
  Bean getBean();

}
