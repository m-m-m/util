/* $Id$
 * Copyright The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.impl.format.properties;

import org.junit.Test;

import junit.framework.TestCase;

import net.sf.mmm.configuration.api.access.ConfigurationAccess;
import net.sf.mmm.configuration.base.access.ConfigurationFactory;
import net.sf.mmm.configuration.impl.access.resource.ResourceAccess;
import net.sf.mmm.configuration.impl.format.AbstractConfigurationFormatTest;
import net.sf.mmm.configuration.impl.format.xml.dom.XmlConfigurationTest;
import net.sf.mmm.configuration.impl.format.xml.dom.XmlFactory;


/**
 * This is the {@link TestCase test-case} for the
 * {@link net.sf.mmm.configuration.api.Configuration configuration}
 * implementation {@link net.sf.mmm.configuration.impl.format.properties}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PropertiesConfigurationFormatTest extends AbstractConfigurationFormatTest {

  /**
   * The constructor
   *
   */
  public PropertiesConfigurationFormatTest() {

    super();
  }

  @Test
  public void testConfiguration() {

    String href = PropertiesConfigurationFormatTest.class.getName().replace('.', '/') + ".properties";
    ConfigurationAccess access = new ResourceAccess(href);
    ConfigurationFactory factory = new PropertiesFactory();
    checkConfiguration(factory, access);
  }

}
