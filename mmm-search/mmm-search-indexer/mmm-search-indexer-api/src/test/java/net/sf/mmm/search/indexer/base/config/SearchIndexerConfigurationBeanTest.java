/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.config;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.mmm.search.base.config.SearchSourceBean;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.util.filter.base.FilterRuleChain;
import net.sf.mmm.util.filter.base.PatternFilterRule;
import net.sf.mmm.util.io.api.EncodingUtil;
import net.sf.mmm.util.transformer.base.RegexStringTransformerRule;
import net.sf.mmm.util.transformer.base.StringTransformerChain;
import net.sf.mmm.util.xml.base.DomUtilImpl;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

/**
 * This is the test-case for SearchIndexerConfigurationBean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchIndexerConfigurationBeanTest {

  /**
   * This method creates a {@link SearchIndexerConfigurationBean} with test-data
   * converts it to XML, parses the XML back to java-objects and converts these
   * again to XML to finally check that both XMLs are the same.
   * 
   * @throws Exception if something went wrong.
   */
  @SuppressWarnings("unchecked")
  @Test
  public void testXmlSerialization() throws Exception {

    JAXBContext context = JAXBContext.newInstance(SearchIndexerConfigurationBean.class);
    SearchIndexerConfigurationBean config = new SearchIndexerConfigurationBean();
    // sources
    List<SearchSourceBean> sources = new ArrayList<SearchSourceBean>();
    config.setSources(sources);
    SearchSourceBean source;
    source = new SearchSourceBean();
    source.setId("Wiki");
    source.setTitle("Wiki");
    source.setUrlPrefix("http://foo.org/twiki/");
    sources.add(source);
    source = new SearchSourceBean();
    source.setId("SVN");
    source.setTitle("Subversion");
    source.setUrlPrefix("http://foo.org/svn/trunk");
    sources.add(source);

    // transformers
    List<StringTransformerChain> transformerList = new ArrayList<StringTransformerChain>();
    config.setTransformers(transformerList);
    RegexStringTransformerRule rule1 = new RegexStringTransformerRule(Pattern.compile(".txt$"), "",
        false, false);
    RegexStringTransformerRule rule2 = new RegexStringTransformerRule(Pattern.compile("/\\.foo/"),
        "", false, true);
    StringTransformerChain transformer = new StringTransformerChain("wiki-transformer", null,
        rule1, rule2);
    transformerList.add(transformer);

    // filters
    List<FilterRuleChain<String>> filters = new ArrayList<FilterRuleChain<String>>();
    config.setFilters(filters);
    FilterRuleChain<String> filter;
    PatternFilterRule filterRule1 = new PatternFilterRule("^/doc/", true);
    PatternFilterRule filterRule2 = new PatternFilterRule("(?i)\\.pdf$", false);
    filter = new FilterRuleChain<String>("my-filter", null, true, filterRule1, filterRule2);
    filters.add(filter);
    filterRule1 = new PatternFilterRule("^/data/", true);
    filterRule2 = new PatternFilterRule("(?i)\\.(xml|xsl)$", false);
    filter = new FilterRuleChain<String>("extended-filter", filter, true, filterRule1, filterRule2);
    filters.add(filter);

    // directories
    List<SearchIndexDataLocationBean> directoryList = new ArrayList<SearchIndexDataLocationBean>();
    config.setLocations(directoryList);
    SearchIndexDataLocationBean directory = new SearchIndexDataLocationBean();
    directory.setEncoding(EncodingUtil.ENCODING_UTF_8);
    directory.setFilter(filter);
    directory.setLocaltion("file:///data/repository");
    directory.setSource(source);
    directory.setUriTransformer(transformer);
    directoryList.add(directory);
    StringWriter buffer = new StringWriter();
    context.createMarshaller().marshal(config, buffer);
    String xml = buffer.toString();
    StringReader reader = new StringReader(xml);
    SearchIndexerConfiguration newConfig = (SearchIndexerConfiguration) context
        .createUnmarshaller().unmarshal(reader);
    buffer = new StringWriter();
    context.createMarshaller().marshal(newConfig, buffer);
    String xml2 = buffer.toString();
    Assert.assertEquals(xml, xml2);

    // TODO: remove this code...
    Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    context.createMarshaller().marshal(newConfig, document);
    DomUtilImpl.getInstance().writeXml(document, System.out, true);
    System.out.println(newConfig.getLocations().get(0).getSource().getTitle());
    System.out.println(((StringTransformerChain) newConfig.getLocations().get(0)
        .getUriTransformer()).getId());
  }
}
