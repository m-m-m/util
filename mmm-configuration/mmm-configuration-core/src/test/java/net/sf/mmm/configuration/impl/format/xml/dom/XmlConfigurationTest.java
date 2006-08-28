/* $Id$ */
package net.sf.mmm.configuration.impl.format.xml.dom;

import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import net.sf.mmm.configuration.api.ConfigurationDocumentIF;
import net.sf.mmm.configuration.api.ConfigurationIF;
import net.sf.mmm.configuration.api.access.ConfigurationAccessFactoryIF;
import net.sf.mmm.configuration.api.access.ConfigurationAccessIF;
import net.sf.mmm.configuration.base.ConfigurationUtil;
import net.sf.mmm.configuration.base.access.AbstractConfigurationAccess;
import net.sf.mmm.configuration.base.access.ConfigurationFactoryIF;
import net.sf.mmm.configuration.impl.access.file.FileAccess;
import net.sf.mmm.configuration.impl.access.resource.ResourceAccess;
import net.sf.mmm.configuration.impl.access.resource.ResourceAccessFactory;
import net.sf.mmm.configuration.impl.access.url.UrlAccess;
import net.sf.mmm.configuration.impl.format.xml.dom.XmlFactory;
import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.value.api.GenericValueIF;

import junit.framework.TestCase;

/**
 * This is the test case for the
 * {@link net.sf.mmm.configuration.api.ConfigurationIF configuration}
 * implementation {@link net.sf.mmm.configuration.impl.format.xml.dom}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class XmlConfigurationTest extends TestCase {

    public XmlConfigurationTest() {

        super();
    }

    private int countDescendants(ConfigurationIF config, String path) {

        Collection<? extends ConfigurationIF> descendants = config.getDescendants(path);
        return descendants.size();
    }

    public void testConfiguration() {

        String href = XmlConfigurationTest.class.getName().replace('.', '/') + ".xml";
        ConfigurationAccessIF access = new ResourceAccess(href);
        ConfigurationFactoryIF factory = new XmlFactory();
        ConfigurationDocumentIF xmlDoc = factory.create(access);
        assertNotNull(xmlDoc);
        ContextIF context = xmlDoc.getContext();
        assertNotNull(context);
        int port = context.getValue("server.port").getInteger();
        assertEquals(8080, port);
        String host = context.getValue("server.host").getString();
        assertEquals("localhost", host);
        ConfigurationIF config = xmlDoc.getConfiguration();
        assertNotNull(config);
        assertEquals("root", config.getName());
        ConfigurationIF portAttribute = config.getDescendant("server/@port");
        assertNotNull(portAttribute);
        assertEquals(port, portAttribute.getValue().getInteger());
        assertEquals(host, config.getDescendant("server/@host").getValue().getString());
        Collection<? extends ConfigurationIF> serviceColl = config.getDescendants("server/service");
        assertEquals(5, serviceColl.size());
        int index = 0;
        for (ConfigurationIF serviceConf : serviceColl) {
            System.out.println(serviceConf.getDescendant("@name").getValue().getString());
        }
    }
}
