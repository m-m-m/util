/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.binding.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.junit.Test;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.ConfigurationDocument;
import net.sf.mmm.configuration.api.access.ConfigurationAccess;
import net.sf.mmm.configuration.base.access.ConfigurationFactory;
import net.sf.mmm.configuration.binding.api.ConfigurationBindingService;
import net.sf.mmm.configuration.impl.access.resource.ResourceAccess;
import net.sf.mmm.configuration.impl.format.xml.dom.XmlFactory;
import net.sf.mmm.util.reflect.pojo.api.PojoDescriptorBuilder;
import net.sf.mmm.util.reflect.pojo.impl.PublicMethodPojoDescriptorBuilder;

import junit.framework.TestCase;

/**
 * This is the {@link TestCase} for {@link ConfigurationBindingServiceImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ConfigurationBindingServiceImplTest extends TestCase {

  @Test
  public void testBinding() {

    // create binding service...
    PojoDescriptorBuilder descriptorBuilder = new PublicMethodPojoDescriptorBuilder();
    ConfigurationBindingServiceImpl serviceImpl = new ConfigurationBindingServiceImpl();
    serviceImpl.setDescriptorBuilder(descriptorBuilder);
    serviceImpl.initialize();
    ConfigurationBindingService service = serviceImpl;

    // create example config...
    String href = ConfigurationBindingServiceImplTest.class.getName().replace('.', '/') + ".xml";
    ConfigurationAccess access = new ResourceAccess(href);
    ConfigurationFactory factory = new XmlFactory();
    ConfigurationDocument doc = factory.create(access);
    Configuration configuration = doc.getConfiguration();

    // create example pojo...
    MyPojo pojo = new MyPojo();

    service.configure(configuration, pojo);

    assertEquals("SomeName", pojo.getPojoName());
    assertEquals(42, pojo.getSpecialNumber());
    Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
    cal.set(Calendar.MILLISECOND, 0);
    cal.set(1999, Calendar.DECEMBER, 31, 23, 59, 59);
    assertEquals(cal.getTime(), pojo.getDate());
    assertEquals(3, pojo.list.size());
    assertEquals("First", pojo.list.get(0));
    assertEquals("Second", pojo.list.get(1));
    assertEquals("Third", pojo.list.get(2));
  }

  public static class MyPojo {

    private String pojoName;

    private int specialNumber;

    private Date date;

    private List<String> list;

    public MyPojo() {

      super();
      this.list = new ArrayList<String>();
    }

    public Date getDate() {

      return this.date;
    }

    public void setDate(Date date) {

      this.date = date;
    }

    public String getPojoName() {

      return this.pojoName;
    }

    public void setPojoName(String name) {

      this.pojoName = name;
    }

    public int getSpecialNumber() {

      return this.specialNumber;
    }

    public void setSpecialNumber(int number) {

      this.specialNumber = number;
    }

    public void addItem(String item) {

      this.list.add(item);
    }

  }

}
