/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.base.example.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.transferobject.base.example.common.Address;
import net.sf.mmm.util.transferobject.base.example.common.Person;

/**
 * This is the {@link PersistenceEntity} implementing {@link Person}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
@Entity
public class PersonEntity extends AbstractEntity<Long> implements Person {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** @see #getFirstName() */
  private String firstName;

  /** @see #getLastName() */
  private String lastName;

  /** @see #getAddress() */
  private AddressEntity address;

  /** @see #getContactInfos() */
  private List<ContactInfoEntity> contactInfos;

  /**
   * The constructor.
   */
  public PersonEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getFirstName() {

    return this.firstName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFirstName(String firstName) {

    this.firstName = firstName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLastName() {

    return this.lastName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLastName(String lastName) {

    this.lastName = lastName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Long getAddressId() {

    if (this.address != null) {
      return this.address.getId();
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAddressId(Long addressId) {

    AddressEntity entity = new AddressEntity();
    entity.setId(addressId);
    this.address = entity;
  }

  /**
   * @return the {@link Address} or {@code null} if not defined.
   */
  public AddressEntity getAddress() {

    return this.address;
  }

  /**
   * @param address is the new value of {@link #getAddress()}.
   */
  public void setAddress(AddressEntity address) {

    this.address = address;
  }

  /**
   * @return the {@link List} of associated {@link ContactInfoEntity}.
   */
  public List<ContactInfoEntity> getContactInfos() {

    if (this.contactInfos == null) {
      this.contactInfos = new ArrayList<>();
    }
    return this.contactInfos;
  }

  /**
   * @param contactInfos is the new value of {@link #getContactInfos()}.
   */
  public void setContactInfos(List<ContactInfoEntity> contactInfos) {

    this.contactInfos = contactInfos;
  }

}
