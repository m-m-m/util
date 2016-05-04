/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

/**
 * This is the interface for an accessor to a specific property of a typed object. It allows to {@link #getValue(Object)
 * get} and {@link #setValue(Object, Object) set} the property abstracting from the way how to do this. <br>
 * Generic implementations can be based on reflection (e.g. using
 * {@link net.sf.mmm.util.pojo.path.api.PojoPathNavigator}). However, for environments such as GWT where no reflection
 * is available implementations for each property can be provided either hand-written or generated.
 *
 * @param <POJO> is the generic type of the {@link net.sf.mmm.util.pojo.api.Pojo} to access.
 * @param <VALUE> is the generic type of the {@link net.sf.mmm.util.pojo.api.Pojo POJOs} property to access.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface PropertyAccessor<POJO, VALUE> {

  /**
   * This method gets the value of the property represented by this {@link PropertyAccessor} from the given
   * {@code element}.
   *
   * @param element is the element where to get the value from.
   * @return the value of the property from the given {@code element}.
   */
  VALUE getValue(POJO element);

  /**
   * This method sets the value of the property represented by this {@link PropertyAccessor} in the given
   * {@code element}.
   *
   * @param element is the element where to set the value.
   * @param value is the new value for the property.
   */
  void setValue(POJO element, VALUE value);

}
