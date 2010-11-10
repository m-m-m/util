/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.base.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import net.sf.mmm.search.api.config.SearchConfiguration;
import net.sf.mmm.search.api.config.SearchIndexConfiguration;

/**
 * This is the abstract base-implementation of {@link SearchConfiguration} as
 * JAXB-ready Java-Bean.<br/>
 * Unfortunately JAXB does not properly support generics so we cannot use a
 * generic for polymorphism of {@link SearchSourceBean}.
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

  /**
   * The constructor.
   */
  public AbstractSearchConfigurationBean() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public SearchIndexConfiguration getSearchIndex() {

    return this.searchIndex;
  }

  /**
   * @param searchIndex is the {@link SearchIndexConfiguration} to set.
   */
  public void setSearchIndex(SearchIndexConfigurationBean searchIndex) {

    this.searchIndex = searchIndex;
  }
}
