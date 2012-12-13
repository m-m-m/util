/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.entity.pim.contact;

import javax.inject.Named;

import net.sf.mmm.data.api.entity.pim.contact.DataContact;
import net.sf.mmm.data.api.entity.pim.contact.DataContactView;
import net.sf.mmm.persistence.base.jpa.AbstractJpaGenericDao;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named
public class DataContactManagerImpl extends AbstractJpaGenericDao<Long, DataContact> {

  /**
   * The constructor.
   */
  public DataContactManagerImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<? extends DataContact> getEntityClassImplementation() {

    return DataContactImpl.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<? super DataContact> getEntityClassReadOnly() {

    return DataContactView.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<DataContact> getEntityClassReadWrite() {

    return DataContact.class;
  }

}
