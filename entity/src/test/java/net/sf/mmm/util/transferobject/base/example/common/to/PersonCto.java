/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.base.example.common.to;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.transferobject.api.CompositeTo;
import net.sf.mmm.util.transferobject.base.example.common.Person;

/**
 * This is the {@link CompositeTo CTO} for a {@link Person}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
public class PersonCto extends CompositeTo {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** @see #getPerson() */
  private PersonEto person;

  /** @see #getAddress() */
  private AddressEto address;

  /** @see #getContactInfos() */
  private List<ContactInfoEto> contactInfos;

  /**
   * The constructor.
   */
  public PersonCto() {

    super();
  }

  /**
   * @return the {@link PersonEto person} or {@code null} if not set.
   */
  public PersonEto getPerson() {

    return this.person;
  }

  /**
   * @param person is the new value of {@link #getPerson()}.
   */
  public void setPerson(PersonEto person) {

    this.person = person;
  }

  /**
   * @return the {@link AddressEto address} or {@code null} if not set.
   */
  public AddressEto getAddress() {

    return this.address;
  }

  /**
   * @param address is the new value of {@link #getAddress()}.
   */
  public void setAddress(AddressEto address) {

    this.address = address;
  }

  /**
   * @return the {@link List} of {@link ContactInfoEto} objects. Will be {@link List#isEmpty() empty} if not
   *         set.
   */
  public List<ContactInfoEto> getContactInfos() {

    if (this.contactInfos == null) {
      this.contactInfos = new ArrayList<>();
    }
    return this.contactInfos;
  }

  /**
   * @param contactInfos is the new value of {@link #getContactInfos()}.
   */
  public void setContactInfos(List<ContactInfoEto> contactInfos) {

    this.contactInfos = contactInfos;
  }

}
