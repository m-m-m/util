/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import net.sf.mmm.util.lang.api.attribute.AttributeReadValueType;
import net.sf.mmm.util.pojo.path.api.PojoPathContext;
import net.sf.mmm.util.pojo.path.api.PojoPathMode;
import net.sf.mmm.util.pojo.path.api.PojoPathNavigator;
import net.sf.mmm.util.pojo.path.base.PojoPathContextBean;
import net.sf.mmm.util.value.api.PropertyAccessor;

/**
 * This is an implementation of {@link PropertyAccessor} using {@link PojoPathNavigator} (based on
 * reflection).
 *
 * @param <POJO> is the generic type of the {@link net.sf.mmm.util.pojo.api.Pojo} to access.
 * @param <VALUE> is the generic type of the {@link net.sf.mmm.util.pojo.api.Pojo POJOs} property to access.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class PojoPathPropertyAccessor<POJO, VALUE> implements PropertyAccessor<POJO, VALUE>,
    AttributeReadValueType<VALUE> {

  /** The {@link PojoPathNavigator} instance to use. */
  private final PojoPathNavigator navigator;

  /** The {@link net.sf.mmm.util.pojo.path.api.PojoPath} pointing to the according property to access. */
  private final String pojoPath;

  private  final Class<VALUE> valueType;

  /** The {@link PojoPathMode} used for {@link #setValue(Object, Object) write access}. */
  private final PojoPathMode writeMode;

  /** The {@link PojoPathContext} to use. */
  private final PojoPathContext context;

  /**
   * The constructor.
   *
   * @param navigator is the {@link PojoPathNavigator} instance to use.
   * @param pojoPath is the {@link net.sf.mmm.util.pojo.path.api.PojoPath} pointing to the according property
   *        to access.
   * @param valueType is the {@link #getValueType() value type}.
   */
  public PojoPathPropertyAccessor(PojoPathNavigator navigator, String pojoPath, Class<VALUE> valueType) {

    this(navigator, pojoPath, valueType, PojoPathMode.CREATE_IF_NULL);
  }

  /**
   * The constructor.
   *
   * @param navigator is the {@link PojoPathNavigator} instance to use.
   * @param pojoPath is the {@link net.sf.mmm.util.pojo.path.api.PojoPath} pointing to the according property
   *        to access.
   * @param valueType is the {@link #getValueType() value type}.
   * @param writeMode is the {@link PojoPathMode} used for {@link #setValue(Object, Object) write access}. For
   *        {@link #getValue(Object) read-access} always {@link PojoPathMode#RETURN_IF_NULL} is used.
   */
  public PojoPathPropertyAccessor(PojoPathNavigator navigator, String pojoPath, Class<VALUE> valueType,
      PojoPathMode writeMode) {

    this(navigator, pojoPath, valueType, writeMode, new PojoPathContextBean());
  }

  /**
   * The constructor.
   *
   * @param navigator is the {@link PojoPathNavigator} instance to use.
   * @param pojoPath is the {@link net.sf.mmm.util.pojo.path.api.PojoPath} pointing to the according property
   *        to access.
   * @param valueType is the {@link #getValueType() value type}.
   * @param writeMode is the {@link PojoPathMode} used for {@link #setValue(Object, Object) write access}. For
   *        {@link #getValue(Object) read-access} always {@link PojoPathMode#RETURN_IF_NULL} is used.
   * @param context is the {@link PojoPathContext} to use.
   */
  public PojoPathPropertyAccessor(PojoPathNavigator navigator, String pojoPath, Class<VALUE> valueType,
      PojoPathMode writeMode, PojoPathContext context) {

    super();
    this.navigator = navigator;
    this.pojoPath = pojoPath;
    this.valueType = valueType;
    this.writeMode = writeMode;
    this.context = context;
  }

  @Override
  public Class<VALUE> getValueType() {

    return this.valueType;
  }

  @SuppressWarnings("unchecked")
  @Override
  public VALUE getValue(POJO element) {

    if (this.valueType == null) {
      return (VALUE) this.navigator.get(element, this.pojoPath, PojoPathMode.RETURN_IF_NULL, this.context);
    } else {
      return this.navigator.get(element, this.pojoPath, PojoPathMode.RETURN_IF_NULL, this.context, this.valueType);
    }
  }

  @Override
  public void setValue(POJO element, VALUE value) {

    this.navigator.set(element, this.pojoPath, this.writeMode, this.context, value);
  }

}
