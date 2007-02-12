/* $Id$ */
package net.sf.mmm.configuration.impl.format.xml.dom;

import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import net.sf.mmm.configuration.api.ConfigurationDocument;
import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.access.ConfigurationAccessFactory;
import net.sf.mmm.configuration.api.access.ConfigurationAccess;
import net.sf.mmm.configuration.base.ConfigurationUtil;
import net.sf.mmm.configuration.base.access.AbstractConfigurationAccess;
import net.sf.mmm.configuration.base.access.ConfigurationFactory;
import net.sf.mmm.configuration.impl.access.file.FileAccess;
import net.sf.mmm.configuration.impl.access.resource.ResourceAccess;
import net.sf.mmm.configuration.impl.access.resource.ResourceAccessFactory;
import net.sf.mmm.configuration.impl.access.url.UrlAccess;
import net.sf.mmm.configuration.impl.format.AbstractConfigurationFormatTest;
import net.sf.mmm.configuration.impl.format.xml.dom.XmlFactory;
import net.sf.mmm.context.api.Context;
import net.sf.mmm.value.api.GenericValue;

import junit.framework.TestCase;

/**
 * This is the {@link TestCase test-case} for the
 * {@link net.sf.mmm.configuration.api.Configuration configuration}
 * implementation {@link net.sf.mmm.configuration.impl.format.xml.dom}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class XmlConfigurationTest extends TestCase {

  public XmlConfigurationTest() {

    super();
  }

  @Test
  public void testConfiguration() {

    String href = XmlConfigurationTest.class.getName().replace('.', '/') + ".xml";
    ConfigurationAccess access = new ResourceAccess(href);
    ConfigurationFactory factory = new XmlFactory();
    ConfigurationDocument doc = factory.create(access);
    assertNotNull(doc);
    Context context = doc.getContext();
    assertNotNull(context);
    int port = context.getValue("server.port").getInteger();
    assertEquals(8080, port);
    String host = context.getValue("server.host").getString();
    assertEquals("localhost", host);
    Configuration config = doc.getConfiguration();
    assertNotNull(config);
    assertEquals("root", config.getName());
    Configuration portAttribute = config.getDescendant("server/@port");
    assertNotNull(portAttribute);
    assertEquals(port, portAttribute.getValue().getInteger());
    assertEquals(host, config.getDescendant("server/@host").getValue().getString());
    Configuration serviceAConf = config.getDescendant("server/service[@name=ServiceA]");
    Configuration serviceAClassConf = serviceAConf.getDescendant("@class");
    assertEquals(String.class, serviceAClassConf.getValue().getJavaClass());
    Collection<? extends Configuration> serviceColl = config.getDescendants("server/service[@name=Service*]");
    assertEquals(5, serviceColl.size());
    int index = 0;
    for (Configuration serviceConf : serviceColl) {
      System.out.println(serviceConf.getDescendant("@name").getValue().getString());
    }
  }
}
