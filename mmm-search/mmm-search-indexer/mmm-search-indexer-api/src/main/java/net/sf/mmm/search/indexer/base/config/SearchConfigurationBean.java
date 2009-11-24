/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.config;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.mmm.search.indexer.api.config.SearchConfiguration;
import net.sf.mmm.search.indexer.base.SearchConfigurationDirectoryBean;
import net.sf.mmm.util.filter.base.FilterRuleChain;
import net.sf.mmm.util.transformer.api.Transformer;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since X 12.10.2009
 */
@XmlRootElement(name = "sources")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchConfigurationBean implements SearchConfiguration {

  private transient Map<String, FilterRuleChain<String>> filters;

  private transient Map<String, ? extends Transformer<String>> transformers;

  private transient List<SearchConfigurationDirectoryBean> directories;

  @XmlElement(name = "source")
  private List<SearchSourceBean> sources;

  /**
   * The constructor.
   */
  public SearchConfigurationBean() {

    super();
  }

  /**
   * @return the sources
   */
  public List<SearchSourceBean> getSources() {

    return this.sources;
  }

  /**
   * @param sources is the sources to set
   */
  public void setSources(List<SearchSourceBean> sources) {

    this.sources = sources;
  }

}
