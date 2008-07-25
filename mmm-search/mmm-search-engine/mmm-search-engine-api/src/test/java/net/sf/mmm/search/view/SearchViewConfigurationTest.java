/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view;

import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.w3c.dom.Element;

import net.sf.mmm.util.resource.ClasspathResource;
import net.sf.mmm.util.xml.base.DomUtilImpl;

import junit.framework.TestCase;

/**
 * This is the {@link TestCase} for {@link SearchViewConfiguration}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class SearchViewConfigurationTest extends TestCase {

  @Test
  public void testSearch() throws Exception {

    Locale.setDefault(Locale.US);
    ClasspathResource resource = new ClasspathResource(SearchViewConfigurationTest.class, ".xml",
        true);
    Element element = DomUtilImpl.parseDocument(resource.openStream()).getDocumentElement();
    SearchViewConfiguration configuration = new SearchViewConfiguration(element);
    // icon mapping
    assertEquals("pdf.png", configuration.getIconName("pdf"));
    assertEquals("text.png", configuration.getIconName("txt"));
    assertEquals("xml.png", configuration.getIconName("xml"));
    assertEquals("firefox.png", configuration.getIconName("html"));
    assertEquals("ie.png", configuration.getIconName("htm"));
    assertEquals("word.png", configuration.getIconName("doc"));
    assertEquals("file.png", configuration.getIconName("UNDEFINED-TYPE"));
    // URL prefix
    assertEquals("http://foo.bar/svn/trunk/", configuration.getUrlPrefixBySource("svn"));
    assertEquals("http://foo.bar/twiki/bin/view/", configuration.getUrlPrefixBySource("twiki"));
    // source names
    List<String> sourceNames = configuration.getSourceNames();
    assertEquals(3, sourceNames.size());
    assertEquals("All", sourceNames.get(0));
    assertEquals("Subversion", sourceNames.get(1));
    assertEquals("Wiki", sourceNames.get(2));
    // type names
    List<String> typeNames = configuration.getTypeNames();
    assertEquals(4, typeNames.size());
    assertEquals("All", typeNames.get(0));
    assertEquals("HTML", typeNames.get(1));
    assertEquals("PDF", typeNames.get(2));
    assertEquals("Word", typeNames.get(3));
    // source name -> source
    assertEquals("svn", configuration.getSourceByName("Subversion"));
    assertEquals("twiki", configuration.getSourceByName("Wiki"));
    assertNull(configuration.getSourceByName("UNDEFINED"));
    // type name -> type
    assertEquals("pdf", configuration.getTypeByName("PDF"));
    assertEquals("html", configuration.getTypeByName("HTML"));
    assertEquals("doc", configuration.getTypeByName("Word"));
    assertNull(configuration.getTypeByName("UNDEFINED"));
  }

}
