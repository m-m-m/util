/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import java.util.concurrent.Callable;

import net.sf.mmm.data.api.DataObjectView;
import net.sf.mmm.data.api.entity.pim.address.DataCountry;
import net.sf.mmm.data.api.entity.pim.contact.DataContactInfo;
import net.sf.mmm.data.api.link.Link;
import net.sf.mmm.data.api.link.MutableLinkList;
import net.sf.mmm.data.base.DataClassGroupRoot;
import net.sf.mmm.data.impl.entity.pim.DataCountryImpl;
import net.sf.mmm.data.impl.entity.pim.DataPersonImpl;
import net.sf.mmm.data.impl.entity.pim.contact.DataAddressImpl;
import net.sf.mmm.data.impl.entity.pim.contact.DataContactImpl;
import net.sf.mmm.data.impl.entity.pim.contact.DataContactInfoImpl;
import net.sf.mmm.data.impl.entity.resource.DataFileImpl;
import net.sf.mmm.data.impl.entity.resource.DataFolderImpl;
import net.sf.mmm.data.impl.reflection.DataClassGroupVersionImpl;
import net.sf.mmm.data.impl.reflection.DataClassImpl;
import net.sf.mmm.data.impl.reflection.DataFieldImpl;
import net.sf.mmm.persistence.api.PersistenceManager;
import net.sf.mmm.transaction.api.TransactionExecutor;
import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.datatype.api.address.Iso2CountryCode;
import net.sf.mmm.util.datatype.api.address.PostalCode;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.version.impl.VersionUtilImpl;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for the persistence. It performs a full integration
 * test using hibernate as OR mapper and an embedded database.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PersistenceTest {

  private static final String SPRING_XML = "/net/sf/mmm/data/impl/beans-data-impl.xml";

  protected PersistenceManager getPersistenceManager() {

    return SpringContainerPool.getInstance(SPRING_XML).getComponent(PersistenceManager.class);
  }

  protected ReflectionUtil getReflectionUtil() {

    return SpringContainerPool.getInstance(SPRING_XML).getComponent(ReflectionUtil.class);
  }

  @Test
  public void testPersistence() throws Exception {

    TransactionExecutor transactionExecutor = SpringContainerPool.getInstance(SPRING_XML)
        .getComponent(TransactionExecutor.class);

    DataContactImpl contact = transactionExecutor.doInTransaction(new Callable<DataContactImpl>() {

      public DataContactImpl call() throws Exception {

        return createAndSave();
      }
    });
    final Long contactId = contact.getId();
    transactionExecutor.doInTransaction(new Callable<Void>() {

      public Void call() throws Exception {

        loadAndUpdate(contactId);
        return null;
      }
    });
  }

  /**
   * TODO: javadoc
   * 
   * @param fileId
   * @return
   */
  protected void loadAndUpdate(Long contactId) {

    PersistenceManager persistenceManager = getPersistenceManager();
    DataContactImpl contact = persistenceManager.load(DataContactImpl.class, contactId);
    MutableLinkList<DataContactInfo> linkList = contact.getContactInfos();
    Assert.assertEquals(1, linkList.size());
    Link<DataContactInfo> link = linkList.getLink(0);
    Assert.assertEquals("private", link.getClassifier());
    DataContactInfo contactInfo = link.getTarget();
    Assert.assertNotNull(contactInfo);
    Assert.assertEquals("Kahl", contactInfo.getAddress().getCity());
  }

  protected DataContactImpl createAndSave() {

    PersistenceManager persistenceManager = getPersistenceManager();
    DataClassImpl<DataObjectView> rootClass = new DataClassImpl<DataObjectView>();
    rootClass.setTitle(DataObjectView.CLASS_TITLE);
    rootClass.setId(Long.valueOf(DataObjectView.CLASS_ID));
    rootClass.setJavaClass(DataObjectView.class);
    rootClass.setModifiers(DataClassModifiersBean.SYSTEM_ABSTRACT_UNEXTENDABLE);
    DataClassGroupVersionImpl groupVersion = persistenceManager.loadIfExists(
        DataClassGroupVersionImpl.class, DataClassGroupRoot.GROUP_ID);
    if (groupVersion == null) {
      groupVersion = new DataClassGroupVersionImpl(DataClassGroupRoot.GROUP_ID, VersionUtilImpl
          .getInstance().createVersionIdentifier(DataClassGroupRoot.GROUP_VERSION));
    }
    rootClass.setGroupVersion(groupVersion);
    DataFieldImpl<DataObjectView, Long> idField = new DataFieldImpl<DataObjectView, Long>();
    idField.setTitle("id");
    idField.setDeclaringClass(rootClass);
    idField.setFieldType(getReflectionUtil().createGenericType(Long.class));
    // aint this a compiler bug? remove the cast and see...
    ((AbstractDataClass<DataObjectView>) rootClass).addDeclaredField(idField);
    persistenceManager.save(rootClass);
    Assert.assertNotNull(idField.getId());

    // test folder
    DataFolderImpl folder = new DataFolderImpl();
    folder.setTitle("folder");

    DataFileImpl file = new DataFileImpl();
    file.setTitle("file");
    folder.getChildren().add(file);

    file.setParent(folder);

    persistenceManager.save(folder);
    persistenceManager.save(file);

    DataPersonImpl person = new DataPersonImpl();
    person.setFirstName("Jörg");
    person.setLastName("Hohwiller");

    DataAddressImpl address = new DataAddressImpl();
    address.setCity("Kahl");
    DataCountry dataCountry = new DataCountryImpl();
    dataCountry.setIsoCode(new Iso2CountryCode("DE"));
    address.setCountry(dataCountry);
    address.setHouseNumber("5");
    address.setPostalCode(new PostalCode("55543"));
    address.setStreet("Ostring");

    DataContactImpl contact = new DataContactImpl();
    contact.setPerson(person);
    DataContactInfo contactInfo = new DataContactInfoImpl();
    contactInfo.setAddress(address);
    contact.getContactInfos().addLink(contactInfo, "private");

    persistenceManager.save(contact);

    return contact;
  }
}
