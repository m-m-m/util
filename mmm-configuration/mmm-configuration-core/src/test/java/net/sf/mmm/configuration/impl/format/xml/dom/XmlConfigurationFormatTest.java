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
public class XmlConfigurationFormatTest extends AbstractConfigurationFormatTest {

  public XmlConfigurationFormatTest() {

    super();
  }

  @Test
  public void testConfiguration() {

    String href = XmlConfigurationFormatTest.class.getName().replace('.', '/') + ".xml";
    ConfigurationAccess access = new ResourceAccess(href);
    ConfigurationFactory factory = new XmlFactory();
    ConfigurationDocument doc = factory.create(access);
    checkConfiguration(factory, access);
  }
}
