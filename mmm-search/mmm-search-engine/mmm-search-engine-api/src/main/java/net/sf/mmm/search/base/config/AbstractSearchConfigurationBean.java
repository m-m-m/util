/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.base.config;

import java.util.Properties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.sf.mmm.search.api.config.SearchConfiguration;
import net.sf.mmm.search.api.config.SearchIndexConfiguration;
import net.sf.mmm.util.xml.base.jaxb.XmlAdapterProperties;

/**
 * This is the abstract base-implementation of {@link SearchConfiguration} as JAXB-ready Java-Bean. <br>
 * Unfortunately JAXB does not properly support generics so we cannot use a generic for polymorphism of
 * {@link SearchSourceBean}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractSearchConfigurationBean implements SearchConfiguration {

  /** The tag-name of the XML element for a {@link SearchSourceBean}. */
  protected static final String XML_ELEMENT_SOURCE = "source";

  /** The tag-name of the XML element for the {@link #getSources() sources}. */
  protected static final String XML_ELEMENT_SOURCES = "sources";

  /** @see #getSearchIndex() */
  @XmlElement(name = "search-index")
  private SearchIndexConfigurationBean searchIndex;

  /** @see #getProperties() */
  // JAXB does not allow us to omit an element and directly embed
  // SearchPropertiesBean, so we have to do a workaround here...
  @XmlElement(name = "properties")
  @XmlJavaTypeAdapter(value = XmlAdapterProperties.class)
  private Properties rawProperties;

  /** @see #getProperties() */
  private transient SearchPropertiesBean properties;

  /** @see #getFields() */
  @XmlElement(name = "fields")
  private SearchFieldsBean fields;

  /**
   * The constructor.
   */
  public AbstractSearchConfigurationBean() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchIndexConfiguration getSearchIndex() {

    return this.searchIndex;
  }

  /**
   * @param searchIndex is the {@link SearchIndexConfiguration} to set.
   */
  public void setSearchIndex(SearchIndexConfigurationBean searchIndex) {

    this.searchIndex = searchIndex;
  }

  /**
   * This method gets the raw {@link Properties}.
   * 
   * @return the {@link Properties}.
   */
  protected Properties getRawProperties() {

    if (this.rawProperties == null) {
      this.rawProperties = new Properties();
    }
    return this.rawProperties;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchPropertiesBean getProperties() {

    if (this.properties == null) {
      this.properties = new SearchPropertiesBean(getRawProperties());
    }
    return this.properties;
  }

  /**
   * @param searchProperties is the searchProperties to set
   */
  public void setProperties(SearchPropertiesBean searchProperties) {

    this.properties = searchProperties;
    this.rawProperties = searchProperties.getProperties();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchFieldsBean getFields() {

    if (this.fields == null) {
      this.fields = new SearchFieldsBean();
    }
    return this.fields;
  }

  /**
   * @param fields is the fields to set
   */
  public void setFields(SearchFieldsBean fields) {

    this.fields = fields;
  }

}
