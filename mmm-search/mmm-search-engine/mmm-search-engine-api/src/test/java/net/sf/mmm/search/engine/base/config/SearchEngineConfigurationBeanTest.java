/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base.config;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBContext;

import junit.framework.TestCase;
import net.sf.mmm.search.api.config.SearchSource;
import net.sf.mmm.search.engine.api.config.SearchEngineConfiguration;
import net.sf.mmm.search.engine.api.config.SearchEntryType;
import net.sf.mmm.util.resource.base.ClasspathResource;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the {@link TestCase} for {@link SearchEngineConfigurationBean}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class SearchEngineConfigurationBeanTest {

  @Test
  public void testReadConfig() throws Exception {

    Locale.setDefault(Locale.US);
    JAXBContext context = JAXBContext.newInstance(SearchEngineConfigurationBean.class);
    ClasspathResource resource = new ClasspathResource(SearchEngineConfigurationBeanTest.class,
        ".xml", true);
    InputStream inStream = resource.openStream();
    try {
      SearchEngineConfiguration configuration = (SearchEngineConfiguration) context
          .createUnmarshaller().unmarshal(inStream);
      // entryTypes
      List<? extends SearchEntryType> types = configuration.getEntryTypes();
      Assert.assertEquals(8, types.size());
      SearchEntryType entryType;

      entryType = configuration.getEntryType(SearchEntryType.ID_ANY);
      Assert.assertNotNull(entryType);
      Assert.assertNull(entryType.getIcon());
      Assert.assertEquals("All", entryType.getTitle());
      Assert.assertEquals(entryType, types.get(0));

      entryType = configuration.getEntryType(SearchEntryType.ID_DEFAULT);
      Assert.assertNotNull(entryType);
      Assert.assertEquals(SearchEntryType.ID_DEFAULT, entryType.getId());
      Assert.assertNull(entryType.getTitle());
      Assert.assertEquals(entryType, types.get(1));

      entryType = configuration.getEntryType("pdf");
      Assert.assertNotNull(entryType);
      Assert.assertEquals("pdf.png", entryType.getIcon());
      Assert.assertEquals("PDF", entryType.getTitle());
      Assert.assertEquals(entryType, types.get(2));

      entryType = configuration.getEntryType("doc");
      Assert.assertNotNull(entryType);
      Assert.assertEquals("word.png", entryType.getIcon());
      Assert.assertEquals("Word", entryType.getTitle());
      Assert.assertEquals(entryType, types.get(3));

      entryType = configuration.getEntryType("txt");
      Assert.assertNotNull(entryType);
      Assert.assertEquals("text.png", entryType.getIcon());
      Assert.assertEquals("Text", entryType.getTitle());
      Assert.assertEquals(entryType, types.get(4));

      entryType = configuration.getEntryType("xml");
      Assert.assertNotNull(entryType);
      Assert.assertEquals("xml.png", entryType.getIcon());
      Assert.assertEquals(entryType, types.get(5));

      entryType = configuration.getEntryType("html");
      Assert.assertNotNull(entryType);
      Assert.assertEquals("firefox.png", entryType.getIcon());
      Assert.assertEquals("HTML", entryType.getTitle());
      Assert.assertEquals(entryType, types.get(6));

      entryType = configuration.getEntryType("htm");
      Assert.assertNotNull(entryType);
      Assert.assertEquals("ie.png", entryType.getIcon());
      Assert.assertEquals(entryType, types.get(7));

      entryType = configuration.getEntryType("UNDEFINED-TYPE");
      Assert.assertNotNull(entryType);
      Assert.assertEquals(SearchEntryType.ID_DEFAULT, entryType.getId());
      Assert.assertEquals("file.png", entryType.getIcon());

      // sources
      List<? extends SearchSource> sources = configuration.getSources();
      Assert.assertEquals(3, sources.size());
      SearchSource source;

      source = configuration.getSource(SearchSource.ID_ANY);
      Assert.assertNotNull(source);
      Assert.assertNull(source.getUrlPrefix());
      Assert.assertEquals("All", source.getTitle());
      Assert.assertEquals(source, sources.get(0));

      source = configuration.getSource("twiki");
      Assert.assertNotNull(source);
      Assert.assertEquals("http://foo.bar/twiki/bin/view/", source.getUrlPrefix());
      Assert.assertEquals("Wiki", source.getTitle());
      Assert.assertEquals(source, sources.get(1));

      source = configuration.getSource("svn");
      Assert.assertNotNull(source);
      Assert.assertEquals("http://foo.bar/svn/trunk/", source.getUrlPrefix());
      Assert.assertEquals("Subversion", source.getTitle());
      Assert.assertEquals(source, sources.get(2));

      Assert.assertNull(configuration.getSource("UNDEFINED"));
    } finally {
      inStream.close();
    }
  }
}
