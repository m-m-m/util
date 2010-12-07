/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.config;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation;
import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;
import net.sf.mmm.util.filter.base.FilterRuleChain;
import net.sf.mmm.util.transformer.base.StringTransformerChain;

/**
 * This is the implementation of {@link SearchIndexerDataLocation} as JAXB-ready
 * Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlRootElement(name = "location")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchIndexerDataLocationBean implements SearchIndexerDataLocation {

  /** @see #getLocationUri() */
  @XmlAttribute(name = "location-uri")
  private String localtionUri;

  /** @see #getSource() */
  // @XmlIDREF
  // @XmlAttribute(name = "source")
  private transient SearchIndexerSourceBean source;

  /** @see #getEncoding() */
  @XmlAttribute(name = "encoding")
  private String encoding;

  /** @see #getBaseUri() */
  @XmlAttribute(name = "base-uri")
  private String baseUri;

  /** @see #isAbsoluteUris() */
  @XmlAttribute(name = "absolute-uris")
  private boolean absoluteUris;

  /** @see #getFilter() */
  @XmlIDREF
  @XmlAttribute(name = "filter")
  private FilterRuleChain<String> filter;

  /** @see #getUriTransformer() */
  @XmlIDREF
  @XmlAttribute(name = "uri-transformer")
  private StringTransformerChain uriTransformer;

  /** @see #getUpdateStrategyVariant() */
  @XmlAttribute(name = "update-strategy-variant")
  private String updateStrategyVariant;

  /**
   * The constructor.
   */
  public SearchIndexerDataLocationBean() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String getLocationUri() {

    return this.localtionUri;
  }

  /**
   * @param path is the path to set
   */
  public void setLocaltionUri(String path) {

    this.localtionUri = path;
  }

  /**
   * {@inheritDoc}
   */
  public SearchIndexerSource getSource() {

    return this.source;
  }

  /**
   * @param source is the source to set
   */
  public void setSource(SearchIndexerSourceBean source) {

    this.source = source;
  }

  /**
   * {@inheritDoc}
   */
  public String getEncoding() {

    return this.encoding;
  }

  /**
   * @param encoding is the encoding to set
   */
  public void setEncoding(String encoding) {

    this.encoding = encoding;
  }

  /**
   * {@inheritDoc}
   */
  public String getBaseUri() {

    if (this.baseUri == null) {
      return "";
    }
    return this.baseUri;
  }

  /**
   * @param baseUri is the baseUri to set
   */
  public void setBaseUri(String baseUri) {

    this.baseUri = baseUri;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAbsoluteUris() {

    return this.absoluteUris;
  }

  /**
   * @param absoluteUris is the absoluteUris to set
   */
  public void setAbsoluteUris(boolean absoluteUris) {

    this.absoluteUris = absoluteUris;
  }

  /**
   * @return the filter
   */
  public FilterRuleChain<String> getFilter() {

    return this.filter;
  }

  /**
   * @param filter is the filter to set
   */
  public void setFilter(FilterRuleChain<String> filter) {

    this.filter = filter;
  }

  /**
   * {@inheritDoc}
   */
  public StringTransformerChain getUriTransformer() {

    return this.uriTransformer;
  }

  /**
   * @param uriTransformer is the uriTransformer to set
   */
  public void setUriTransformer(StringTransformerChain uriTransformer) {

    this.uriTransformer = uriTransformer;
  }

  /**
   * {@inheritDoc}
   */
  public String getUpdateStrategyVariant() {

    return this.updateStrategyVariant;
  }

  /**
   * This method sets the {@link #getUpdateStrategyVariant()
   * update-strategy-variant}.
   * 
   * @param updateStrategyVariant is the new value to set.
   */
  public void setUpdateStrategyVariant(String updateStrategyVariant) {

    this.updateStrategyVariant = updateStrategyVariant;
  }

  /**
   * This method is automatically invoked by JAXB after unmarshalling this bean.
   * It allows to link the parent object.
   * 
   * @param unmarshaller is the {@link Unmarshaller}.
   * @param parent is the java-object representing the parent node in XML.
   */
  public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {

    this.source = (SearchIndexerSourceBean) parent;
  }
}
