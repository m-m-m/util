/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.transferobject.api.TransferObjectUtil;
import net.sf.mmm.util.transferobject.base.example.common.Address;
import net.sf.mmm.util.transferobject.base.example.common.ContactInfo;
import net.sf.mmm.util.transferobject.base.example.common.to.AddressEto;
import net.sf.mmm.util.transferobject.base.example.common.to.PersonEto;
import net.sf.mmm.util.transferobject.base.example.persistence.AddressEntity;
import net.sf.mmm.util.transferobject.base.example.persistence.ContactInfoEntity;
import net.sf.mmm.util.transferobject.base.example.persistence.PersonEntity;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link TransferObjectUtil}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class TransferObjectUtilTest extends Assert {

  /** Test ZIP code. */
  private static final String ZIP = "9600";

  /** Test street. */
  private static final String STREET = "Heraklesstreet";

  /** Test state. */
  private static final String STATE = "Platon";

  /** Test house number. */
  private static final String HOUSENUMBER = "1a";

  /** Test country. */
  private static final String COUNTRY = "Atlantis";

  /** Test city. */
  private static final String CITY = "Smalltown";

  /** Test email. */
  private static final String EMAIL = "hohwille@users.sf.net";

  /** Test phone. */
  private static final String PHONE = "+491234567890";

  /** Test fax. */
  private static final String FAX = "+49987654321";

  /**
   * @return the {@link TransferObjectUtil} to test.
   */
  protected TransferObjectUtil getTransferObjectUtil() {

    return TransferObjectUtilImpl.getInstance();
  }

  /**
   * Tests {@link TransferObjectUtil#clone(net.sf.mmm.util.transferobject.api.AbstractTransferObject)}.
   */
  @Test
  public void testClone() {

    // given
    TransferObjectUtil util = getTransferObjectUtil();

    AddressEto address = new AddressEto();
    fillAddress(address);

    // when
    AddressEto clone = util.clone(address);

    // then
    assertNotSame(address, clone);
    verifyAddress(clone);
    assertEquals(address, clone);
  }

  /**
   * Tests {@link TransferObjectUtil#convertFromEntity(net.sf.mmm.util.entity.api.PersistenceEntity, Class)}.
   */
  @Test
  public void testConvertSimple() {

    // given
    TransferObjectUtil util = getTransferObjectUtil();

    AddressEntity addressEntity = new AddressEntity();
    fillAddress(addressEntity);

    // when
    AddressEto addressEto = util.convertFromEntity(addressEntity, AddressEto.class);
    AddressEntity clone = util.convertToEntity(addressEto, AddressEntity.class);

    // then
    verifyAddress(addressEto);
    verifyAddress(clone);
    assertEquals(addressEntity, clone);

    // special test: when entity is converted to ETO, then ETO keeps a transient and hidden reference to the
    // original entity and updates modificationCounter from this object.
    int magic = 42;
    addressEntity.setModificationCounter(magic);
    assertEquals(magic, addressEto.getModificationCounter());

    util.updateModificationCounter(addressEto, true);
    addressEntity.setModificationCounter(-1);
    assertEquals(magic, addressEto.getModificationCounter());
  }

  /**
   * Tests {@link TransferObjectUtil#convertFromEntity(net.sf.mmm.util.entity.api.PersistenceEntity, Class)}.
   */
  @Test
  public void testConvertComplex() {

    // given
    TransferObjectUtil util = getTransferObjectUtil();

    AddressEntity addressEntity = new AddressEntity();
    fillAddress(addressEntity);
    PersonEntity personEntity = new PersonEntity();
    personEntity.setAddress(addressEntity);
    List<ContactInfoEntity> contactInfos = new ArrayList<ContactInfoEntity>();
    ContactInfoEntity contactInfoEntity = new ContactInfoEntity();
    fillContactInfo(contactInfoEntity);
    personEntity.setContactInfos(contactInfos);

    // when
    PersonEto personEto = util.convertFromEntity(personEntity, PersonEto.class);
    PersonEntity clone = util.convertToEntity(personEto, PersonEntity.class);

    // then
    // verifyAddress(addressEto);
    // verifyAddress(clone);
    assertEquals(personEntity, clone);

    // special test: when entity is converted to ETO, then ETO keeps a transient and hidden reference to the
    // original entity and updates modificationCounter from this object.

    // int magic = 42;
    // addressEntity.setModificationCounter(magic);
    // assertEquals(magic, addressEto.getModificationCounter());
    //
    // util.updateModificationCounter(addressEto, true);
    // addressEntity.setModificationCounter(-1);
    // assertEquals(magic, addressEto.getModificationCounter());
  }

  /**
   * @param address is the {@link Address} to verify for the expected test attributes (see constants).
   */
  private void verifyAddress(Address address) {

    assertNotNull(address);
    assertEquals(COUNTRY, address.getCountry());
    assertEquals(STATE, address.getState());
    assertEquals(CITY, address.getCity());
    assertEquals(ZIP, address.getZip());
    assertEquals(STREET, address.getStreet());
    assertEquals(HOUSENUMBER, address.getHouseNumber());
  }

  /**
   * @param address is the address to fill with test attributes (see constants).
   */
  private void fillAddress(Address address) {

    address.setCity(CITY);
    address.setCountry(COUNTRY);
    address.setHouseNumber(HOUSENUMBER);
    address.setState(STATE);
    address.setStreet(STREET);
    address.setZip(ZIP);
  }

  private void fillContactInfo(ContactInfo contactInfo) {

    contactInfo.setEmail(EMAIL);
    contactInfo.setFax(FAX);
    contactInfo.setPhone(PHONE);
  }
}
