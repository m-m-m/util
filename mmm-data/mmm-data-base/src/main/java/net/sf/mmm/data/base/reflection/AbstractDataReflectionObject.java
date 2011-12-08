/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import net.sf.mmm.data.api.DataObjectView;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataReflectionObject;
import net.sf.mmm.data.base.AbstractDataObject;

/**
 * This is the abstract base implementation of the {@link DataReflectionObject}
 * interface.
 * 
 * @param <CLASS> is the generic type of the reflected {@link #getJavaClass()
 *        class}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@MappedSuperclass
public abstract class AbstractDataReflectionObject<CLASS> extends AbstractDataObject implements
    DataReflectionObject<CLASS> {

  /** UID for serialization. */
  private static final long serialVersionUID = 1971525692050639234L;

  /**
   * The constructor.
   */
  public AbstractDataReflectionObject() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  public abstract DataClass<? extends DataObjectView> getParent();

  /**
   * {@inheritDoc}
   */
  @Transient
  public boolean isCacheable() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setId(Long id) {

    super.setId(id);
  }

}
