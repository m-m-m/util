/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.reflection.base;

import net.sf.mmm.data.api.ContentException;
import net.sf.mmm.data.reflection.api.access.ContentFieldAccessor;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.nls.api.ReadOnlyException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;

/**
 * TODO: this class ...
 * 
 * @param <CLASS> is the generic type for the bound of
 *        {@link net.sf.mmm.data.reflection.api.ContentField#getJavaClass()}.
 * @param <FIELD> is the generic type of the value reflected by this field.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContentFieldAccessorPojo<CLASS, FIELD> implements ContentFieldAccessor<CLASS, FIELD> {

  /** The type of the content-field to access. */
  private final Class<FIELD> fieldClass;

  /** @see #getFieldValue(Object) */
  private final PojoPropertyAccessorNonArg getter;

  /** @see #setFieldValue(Object, Object) */
  private final PojoPropertyAccessorOneArg setter;

  /**
   * The constructor.
   * 
   * @param fieldClass is the {@link Class} reflecting the accessed
   *        {@link net.sf.mmm.data.reflection.api.ContentField} (see
   *        {@link net.sf.mmm.data.reflection.api.ContentField#getFieldType()
   *        }).
   * @param getter is the
   *        {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode#GET
   *        getter} of the field.
   */
  public ContentFieldAccessorPojo(Class<FIELD> fieldClass, PojoPropertyAccessorNonArg getter) {

    this(fieldClass, getter, null);
  }

  /**
   * The constructor.
   * 
   * @param fieldClass is the {@link Class} reflecting the accessed
   *        {@link net.sf.mmm.data.reflection.api.ContentField} (see
   *        {@link net.sf.mmm.data.reflection.api.ContentField#getFieldType()
   *        }).
   * @param getter is the
   *        {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode#GET
   *        getter} of the field.
   * @param setter is the
   *        {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode#SET
   *        setter} of the field.
   */
  public ContentFieldAccessorPojo(Class<FIELD> fieldClass, PojoPropertyAccessorNonArg getter,
      PojoPropertyAccessorOneArg setter) {

    super();
    this.fieldClass = fieldClass;
    this.getter = getter;
    if (!this.fieldClass.isAssignableFrom(this.getter.getPropertyClass())) {
      // type of getter is not compatible with fieldClass
      // TODO
      throw new NlsIllegalStateException();
    }
    this.setter = setter;
    if ((this.setter != null) && (!this.setter.getPropertyClass().isAssignableFrom(fieldClass))) {
      // type of setter is not compatible with fieldClass
      // TODO
      throw new NlsIllegalStateException();
    }
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public FIELD getFieldValue(CLASS object) throws ContentException {

    return (FIELD) this.getter.invoke(object);
  }

  /**
   * {@inheritDoc}
   */
  public void setFieldValue(CLASS object, FIELD value) throws ContentException {

    if (this.setter == null) {
      throw new ReadOnlyException("" + object + "." + this.getter.getName());
    }
    this.setter.invoke(object, value);
  }

}
