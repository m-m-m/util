/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.entity.pim.contact;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import net.sf.mmm.data.api.entity.pim.contact.DataContact;
import net.sf.mmm.data.api.entity.pim.contact.DataContactInfo;
import net.sf.mmm.data.api.entity.pim.contact.DataContactView;
import net.sf.mmm.data.api.entity.pim.contact.DataPerson;
import net.sf.mmm.data.api.link.Link;
import net.sf.mmm.data.api.link.MutableLinkList;
import net.sf.mmm.data.api.reflection.DataFieldIds;
import net.sf.mmm.data.impl.entity.AbstractDataEntity;
import net.sf.mmm.data.impl.entity.pim.DataPersonImpl;

/**
 * This is the implementation of {@link DataContact} using JPA.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Entity(name = DataContactView.CLASS_TITLE)
public class DataContactImpl extends AbstractDataEntity implements DataContact {

  /** UID for serialization. */
  private static final long serialVersionUID = 5113284683481602195L;

  /** @see #getPerson() */
  private DataPersonImpl person;

  /**
   * The constructor.
   */
  public DataContactImpl() {

    super();
  }

  /**
   * @return the person
   */
  @OneToOne(cascade = CascadeType.ALL, optional = false)
  public DataPersonImpl getPerson() {

    return this.person;
  }

  /**
   * @param person is the person to set
   */
  public void setPerson(DataPerson person) {

    this.person = (DataPersonImpl) person;
  }

  /**
   * {@inheritDoc}
   */
  public MutableLinkList<DataContactInfo> getContactInfos() {

    return getLinklist(DataFieldIds.ID_CONTACT_CONTACT_INFOS, DataContactInfo.class);
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public DataContactInfo getDefaultContactInfo() {

    Link<DataContactInfo> firstLink = getContactInfos().getFirstLink(true,
        CONTACT_INFO_CLASSIFIER_PRIVATE, CONTACT_INFO_CLASSIFIER_BUSINESS);
    if (firstLink == null) {
      return null;
    } else {
      return firstLink.getTarget();
    }
  }

  /**
   * {@inheritDoc}
   */
  public MutableLinkList<DataContact> getRelatedContacts() {

    return getLinklist(DataFieldIds.ID_CONTACT_RELATED_CONTACTS, DataContact.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  protected long getStaticDataClassId() {

    return DataContactView.CLASS_ID;
  }

}
