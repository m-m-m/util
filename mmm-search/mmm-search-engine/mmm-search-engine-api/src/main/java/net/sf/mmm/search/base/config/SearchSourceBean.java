/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.base.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.mmm.search.api.config.SearchSource;

/**
 * This is the implementation of {@link SearchSource} as JAXB-ready Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlRootElement(name = "source")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchSourceBean implements SearchSource {

  /** @see #getId() */
  @XmlID
  @XmlAttribute(name = "id")
  private String id;

  /** @see #getTitle() */
  @XmlAttribute(name = "title")
  private String title;

  /** @see #getUrlPrefix() */
  @XmlAttribute(name = "url-prefix")
  private String urlPrefix;

  // /** @see #getUpdateStrategy() */
  // @XmlAttribute(name = "update-strategy")
  // private String updateStrategy;

  /**
   * The constructor.
   */
  public SearchSourceBean() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String getId() {

    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return this.title;
  }

  /**
   * {@inheritDoc}
   */
  public String getUrlPrefix() {

    return this.urlPrefix;
  }

  /**
   * @param id is the id to set
   */
  public void setId(String id) {

    this.id = id;
  }

  /**
   * @param title is the title to set
   */
  public void setTitle(String title) {

    this.title = title;
  }

  /**
   * @param urlPrefix is the urlPrefix to set
   */
  public void setUrlPrefix(String urlPrefix) {

    this.urlPrefix = urlPrefix;
  }

  // /**
  // * {@inheritDoc}
  // */
  // public String getUpdateStrategy() {
  //
  // return this.updateStrategy;
  // }
  //
  // /**
  // * This method sets the {@link #getUpdateStrategy() update-strategy}.
  // *
  // * @param updateStrategy is the new value to set.
  // */
  // public void setUpdateStrategy(String updateStrategy) {
  //
  // this.updateStrategy = updateStrategy;
  // }

}
