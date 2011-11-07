/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.reflection;

import javax.persistence.Entity;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.base.reflection.AbstractDataClass;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.data.api.reflection.DataClass} interface.
 * 
 * @param <CLASS> is the generic type of the reflected {@link #getJavaClass()
 *        class}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Entity
@DataClassAnnotation(id = DataClass.CLASS_ID, title = DataClass.CLASS_NAME)
public final class DataClassImpl<CLASS extends DataObject> extends AbstractDataClass<CLASS> {

  /** UID for serialization. */
  private static final long serialVersionUID = -6926223109885122995L;

  /**
   * The constructor.
   */
  public DataClassImpl() {

    super();
  }

}
