/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.api;

import java.util.Collection;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.pojo.descriptor.api.attribute.PojoAttributeName;
import net.sf.mmm.util.validation.api.ValueValidator;

/**
 * This interface represents a property of a {@link net.sf.mmm.util.pojo.api.Pojo}. A property is some
 * attribute of a {@link net.sf.mmm.util.pojo.api.Pojo} that can be accessed directly via a
 * {@link java.lang.reflect.Field} or via a {@link java.lang.reflect.Method} (getter, setter, etc.). This
 * {@link PojoPropertyDescriptor} is an alternative to {@link java.beans.PropertyDescriptor} but only has
 * focus on reflectively accessing objects. Therefore it works on any {@link net.sf.mmm.util.pojo.api.Pojo}.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public interface PojoPropertyDescriptor extends PojoAttributeName {

  /**
   * This method gets the {@link PojoPropertyAccessor accessor} to access the represented property in the way
   * given by <code>mode</code>.
   * 
   * @param <ACCESSOR> is the type of the requested accessor.
   * @param mode is the {@link PojoPropertyAccessor#getMode() mode} of the requested accessor.
   * @return the accessor for the given <code>mode</code> or <code>null</code> if no such accessor exists.
   */
  <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(PojoPropertyAccessorMode<ACCESSOR> mode);

  /**
   * This method gets all available {@link #getAccessor(PojoPropertyAccessorMode) accessors} for the
   * represented property.
   * 
   * @return a collection with all {@link PojoPropertyDescriptor property descriptor}s
   */
  Collection<? extends PojoPropertyAccessor> getAccessors();

  /**
   * This method gets or creates the {@link ValueValidator} for the associated property derived from
   * annotations (e.g. JSR 303 or JSR 308).<br/>
   * <b>ATTENTION:</b><br/>
   * This is NOT an implementation of JSR 303 (<code>javax.validation</code>) nor is it strictly compatible to
   * it. Instead this is an abstraction that may be implemented using JSR 303 or maybe not. However, we want
   * to be GWT compatible and we want to directly bind the validation to the UI widgets (see
   * <code>UiWidgetWithValue#addValidator(ValueValidator)</code>). The API of JSR 303 is unfortunately biased.
   * A simple bridge to JSR 303 can be achieved via
   * <code>javax.validation.Validator.validateProperty(Object, String, Class)</code>.
   * 
   * @return the {@link ValueValidator} for the associated property.
   * @since 3.1.0
   */
  ValueValidator<?> getValidator();

}
