/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.reflection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.api.reflection.DataFieldModifiers;
import net.sf.mmm.data.base.reflection.AbstractDataField;

import org.hibernate.annotations.Type;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.data.api.reflection.DataField} interface.
 * 
 * @param <FIELD> is the generic type of the {@link #getFieldValue(DataObject)
 *        value} reflected by this field.
 * @param <CLASS> is the generic type of the reflected {@link #getJavaClass()
 *        class}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Entity
@Table(name = "DATA_FIELD")
@DataClassAnnotation(id = DataField.CLASS_ID, title = DataField.CLASS_TITLE)
public final class DataFieldImpl<CLASS extends DataObject, FIELD> extends
    AbstractDataField<CLASS, FIELD> {

  /** UID for serialization. */
  private static final long serialVersionUID = -2021919603941862430L;

  /**
   * The constructor.
   */
  public DataFieldImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Type(type = "net.sf.mmm.data.impl.datatype.usertype.DataFieldModifiersUserType")
  public DataFieldModifiers getModifiers() {

    return super.getModifiers();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @ManyToOne
  public DataClassImpl getDeclaringClass() {

    return (DataClassImpl) super.getDeclaringClass();
  }

}
