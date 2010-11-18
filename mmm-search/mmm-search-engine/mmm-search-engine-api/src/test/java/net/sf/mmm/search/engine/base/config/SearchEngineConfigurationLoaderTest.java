/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base.config;

import java.util.Collection;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.api.config.SearchFieldConfiguration;
import net.sf.mmm.search.api.config.SearchFieldMode;
import net.sf.mmm.search.api.config.SearchFieldType;
import net.sf.mmm.search.api.config.SearchFields;
import net.sf.mmm.search.api.config.SearchProperties;
import net.sf.mmm.search.api.config.SearchSource;
import net.sf.mmm.search.engine.api.config.SearchEngineConfiguration;
import net.sf.mmm.search.engine.api.config.SearchEngineConfigurationLoader;
import net.sf.mmm.search.engine.api.config.SearchEntryType;
import net.sf.mmm.search.engine.api.config.SearchEntryTypeContainer;
import net.sf.mmm.util.resource.base.ClasspathResource;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link SearchEngineConfigurationLoader}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchEngineConfigurationLoaderTest {

  /**
   * @return the {@link SearchEngineConfigurationLoader} to test.
   */
  protected SearchEngineConfigurationLoader getSearchEngineConfigurationLoader() {

    SearchEngineConfigurationLoaderImpl reader = new SearchEngineConfigurationLoaderImpl();
    reader.initialize();
    return reader;
  }

  /**
   * Checks the specified {@link SearchEntryType}.
   * 
   * @param types is the {@link SearchEntryTypeContainer}.
   * @param id - see {@link SearchEntryType#getId()}.
   * @param title - see {@link SearchEntryType#getTitle()}.
   * @param icon - see {@link SearchEntryType#getIcon()}.
   * @return the {@link SearchEntryType}.
   */
  protected SearchEntryType checkEntryType(SearchEntryTypeContainer types, String id, String title,
      String icon) {

    SearchEntryType entryType = types.getEntryType(id);
    Assert.assertNotNull(entryType);
    Assert.assertEquals(id, entryType.getId());
    Assert.assertEquals(title, entryType.getTitle());
    Assert.assertEquals(icon, entryType.getIcon());
    return entryType;
  }

  /**
   * Checks the specified {@link SearchEntryType}.
   * 
   * @param configuration is the {@link SearchEngineConfiguration}.
   * @param id - see {@link SearchSource#getId()}.
   * @param title - see {@link SearchSource#getTitle()}.
   * @param urlPrefix - see {@link SearchSource#getUrlPrefix()}.
   * @return the {@link SearchSource}.
   */
  protected SearchSource checkSource(SearchEngineConfiguration configuration, String id,
      String title, String urlPrefix) {

    SearchSource source = configuration.getSource(id);
    Assert.assertNotNull(source);
    Assert.assertEquals(id, source.getId());
    Assert.assertEquals(title, source.getTitle());
    Assert.assertEquals(urlPrefix, source.getUrlPrefix());
    return source;
  }

  /**
   * Test of {@link SearchEngineConfigurationLoader#loadConfiguration(String)}.
   */
  @Test
  public void testReadConfiguration() {

    SearchEngineConfigurationLoader loader = getSearchEngineConfigurationLoader();
    ClasspathResource resource = new ClasspathResource(SearchEngineConfigurationLoaderTest.class,
        ".xml", true);
    SearchEngineConfiguration configuration = loader.loadConfiguration(resource.getUri()).getBean();
    // entryTypes
    SearchEntryTypeContainer types = configuration.getEntryTypes();
    SearchEntryType anyType = checkEntryType(types, SearchEntryType.ID_ANY, "All", "file.png");
    checkEntryType(types, "pdf", "PDF", "pdf.png");
    checkEntryType(types, "doc", "Word", "word.png");
    checkEntryType(types, "txt", "Text", "text.png");
    checkEntryType(types, "xml", "XML", "xml.png");
    checkEntryType(types, "html", "HTML", "firefox.png");
    checkEntryType(types, "htm", "HTM", "ie.png");
    Assert.assertSame(anyType, types.getEntryType("UNDEFINED-TYPE"));

    // sources
    Collection<? extends SearchSource> sources = configuration.getSources();
    Assert.assertEquals(3, sources.size());
    checkSource(configuration, SearchSource.ID_ANY, "All", null);
    checkSource(configuration, "twiki", "Wiki", "http://foo.bar/twiki/bin/view/");
    checkSource(configuration, "svn", "Subversion", "http://foo.bar/svn/trunk/");

    Assert.assertNull(configuration.getSource("UNDEFINED"));

    // properties
    SearchProperties properties = configuration.getProperties();
    Assert.assertNotNull(properties);
    Assert.assertEquals("bar", properties.getProperty("foo"));
    Assert.assertNull(properties.getProperty("undefined"));

    // fields
    SearchFields fields = configuration.getFields();
    SearchFieldConfiguration field;
    // field defaults
    field = fields.getFieldConfiguration(SearchEntry.FIELD_ID);
    Assert.assertEquals(SearchEntry.FIELD_ID, field.getName());
    Assert.assertEquals(SearchFieldMode.SEARCHABLE_AND_RETRIEVABLE, field.getMode());
    Assert.assertEquals(SearchFieldType.LONG, field.getType());
    field = fields.getFieldConfiguration(SearchEntry.FIELD_CREATOR);
    Assert.assertEquals(SearchEntry.FIELD_CREATOR, field.getName());
    Assert.assertEquals(SearchFieldMode.SEARCHABLE_AND_RETRIEVABLE, field.getMode());
    Assert.assertEquals(SearchFieldType.TEXT, field.getType());
    field = fields.getFieldConfiguration(SearchEntry.FIELD_KEYWORDS);
    Assert.assertEquals(SearchEntry.FIELD_KEYWORDS, field.getName());
    Assert.assertEquals(SearchFieldMode.SEARCHABLE_AND_RETRIEVABLE, field.getMode());
    Assert.assertEquals(SearchFieldType.TEXT, field.getType());
    field = fields.getFieldConfiguration(SearchEntry.FIELD_LANGUAGE);
    Assert.assertEquals(SearchEntry.FIELD_LANGUAGE, field.getName());
    Assert.assertEquals(SearchFieldMode.SEARCHABLE_AND_RETRIEVABLE, field.getMode());
    Assert.assertEquals(SearchFieldType.STRING, field.getType());
    field = fields.getFieldConfiguration(SearchEntry.FIELD_SIZE);
    Assert.assertEquals(SearchEntry.FIELD_SIZE, field.getName());
    Assert.assertEquals(SearchFieldMode.SEARCHABLE_AND_RETRIEVABLE, field.getMode());
    Assert.assertEquals(SearchFieldType.LONG, field.getType());
    field = fields.getFieldConfiguration(SearchEntry.FIELD_SOURCE);
    Assert.assertEquals(SearchEntry.FIELD_SOURCE, field.getName());
    Assert.assertEquals(SearchFieldMode.SEARCHABLE_AND_RETRIEVABLE, field.getMode());
    Assert.assertEquals(SearchFieldType.STRING, field.getType());
    field = fields.getFieldConfiguration(SearchEntry.FIELD_TITLE);
    Assert.assertEquals(SearchEntry.FIELD_TITLE, field.getName());
    Assert.assertEquals(SearchFieldMode.SEARCHABLE_AND_RETRIEVABLE, field.getMode());
    Assert.assertEquals(SearchFieldType.TEXT, field.getType());
    field = fields.getFieldConfiguration(SearchEntry.FIELD_TYPE);
    Assert.assertEquals(SearchEntry.FIELD_TYPE, field.getName());
    Assert.assertEquals(SearchFieldMode.SEARCHABLE_AND_RETRIEVABLE, field.getMode());
    Assert.assertEquals(SearchFieldType.STRING, field.getType());
    field = fields.getFieldConfiguration(SearchEntry.FIELD_URI);
    Assert.assertEquals(SearchEntry.FIELD_URI, field.getName());
    Assert.assertEquals(SearchFieldMode.SEARCHABLE_AND_RETRIEVABLE, field.getMode());
    Assert.assertEquals(SearchFieldType.STRING, field.getType());
    // overridden fields
    field = fields.getFieldConfiguration(SearchEntry.FIELD_TEXT);
    Assert.assertEquals(SearchEntry.FIELD_TEXT, field.getName());
    Assert.assertEquals(SearchFieldMode.SEARCHABLE, field.getMode());
    Assert.assertEquals(SearchFieldType.TEXT, field.getType());
    field = fields.getFieldConfiguration(SearchEntry.FIELD_CUSTOM_ID);
    Assert.assertEquals(SearchEntry.FIELD_CUSTOM_ID, field.getName());
    Assert.assertEquals(SearchFieldMode.SEARCHABLE_AND_RETRIEVABLE, field.getMode());
    Assert.assertEquals(SearchFieldType.LONG, field.getType());
    // custom fields
    field = fields.getFieldConfiguration("foo");
    Assert.assertEquals("foo", field.getName());
    Assert.assertEquals(SearchFieldMode.RETRIEVABLE, field.getMode());
    Assert.assertEquals(SearchFieldType.DATE, field.getType());
  }

}
