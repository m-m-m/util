/* $Id$
 * Copyright The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.impl.format;

import java.util.Collection;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.ConfigurationDocument;
import net.sf.mmm.configuration.api.access.ConfigurationAccess;
import net.sf.mmm.configuration.base.access.ConfigurationFactory;
import net.sf.mmm.context.api.Context;

import junit.framework.TestCase;

/**
 * This is the abstract {@link TestCase test-case} for different
 * {@link ConfigurationFactory format} implementations of a
 * {@link net.sf.mmm.configuration.api.Configuration configuration}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public abstract class AbstractConfigurationFormatTest extends TestCase {

  /**
   * The constructor
   */
  public AbstractConfigurationFormatTest() {

    super();
  }

  public void checkConfiguration(ConfigurationFactory factory, ConfigurationAccess access) {

    ConfigurationDocument doc = factory.create(access);
    assertNotNull(doc);
    Configuration config = doc.getConfiguration();
    assertNotNull(config);
    assertEquals("root", config.getName());
    Configuration portAttribute = config.getDescendant("server/@port");
    assertNotNull(portAttribute);
    //assertEquals(port, portAttribute.getValue().getInteger());
    //assertEquals(host, config.getDescendant("server/@host").getValue().getString());
    Collection<? extends Configuration> serviceColl = config.getDescendants("server/service");
    assertEquals(2, serviceColl.size());
    int index = 0;
    for (Configuration serviceConf : serviceColl) {
      System.out.println(serviceConf.getDescendant("@name").getValue().getString());
    }
  }

}
