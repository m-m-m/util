/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.mmm.search.base.config.SearchConfigurationBean;
import net.sf.mmm.search.engine.api.config.SearchEngineConfiguration;
import net.sf.mmm.search.engine.api.config.SearchEntryTypeContainer;

/**
 * This is the implementation of {@link SearchEngineConfiguration} as JAXB-ready Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlRootElement(name = "search")
public class SearchEngineConfigurationBean extends SearchConfigurationBean implements SearchEngineConfiguration {

  /** @see #getEntryTypes() */
  @XmlElement(name = "entry-types")
  private SearchEntryTypeContainerBean entryTypes;

  /**
   * The constructor.
   */
  public SearchEngineConfigurationBean() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchEntryTypeContainer getEntryTypes() {

    if (this.entryTypes == null) {
      this.entryTypes = new SearchEntryTypeContainerBean();
    }
    return this.entryTypes;
  }

  /**
   * @param entryTypes is the entryTypes to set
   */
  public void setEntryTypes(SearchEntryTypeContainerBean entryTypes) {

    this.entryTypes = entryTypes;
  }

}
