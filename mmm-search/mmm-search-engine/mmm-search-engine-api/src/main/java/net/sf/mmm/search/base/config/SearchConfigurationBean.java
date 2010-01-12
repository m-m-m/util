/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.base.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.mmm.search.api.config.SearchConfiguration;
import net.sf.mmm.search.api.config.SearchSource;

/**
 * This is the implementation of {@link SearchConfiguration} as JAXB-ready
 * Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlRootElement(name = "search")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchConfigurationBean implements SearchConfiguration {

  /** @see #getSources() */
  @XmlElementWrapper(name = "sources")
  @XmlElement(name = "source")
  private List<SearchSourceBean> sources;

  /** @see #getSourceMap() */
  private transient Map<String, SearchSourceBean> sourceMap;

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
   * {@inheritDoc}
   */
  public SearchSource getSource(String id) {

    return getSourceMap().get(id);
  }

  /**
   * @param sources is the sources to set
   */
  public void setSources(List<SearchSourceBean> sources) {

    this.sources = sources;
  }

  /**
   * @return the sourceMap
   */
  public Map<String, SearchSourceBean> getSourceMap() {

    if (this.sourceMap == null) {
      Map<String, SearchSourceBean> map = new HashMap<String, SearchSourceBean>();
      for (SearchSourceBean source : this.sources) {
        map.put(source.getId(), source);
      }
      this.sourceMap = map;
    }
    return this.sourceMap;
  }
}
