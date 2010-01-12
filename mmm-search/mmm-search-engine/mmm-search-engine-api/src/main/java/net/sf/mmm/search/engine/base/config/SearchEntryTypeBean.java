/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;

import net.sf.mmm.search.engine.api.config.SearchEntryType;

/**
 * This is the implementation of {@link SearchEntryType} as JAXB-ready
 * Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchEntryTypeBean implements SearchEntryType {

  /** @see #getIcon() */
  @XmlAttribute(name = "icon")
  private String icon;

  /** @see #getTitle() */
  @XmlAttribute(name = "title")
  private String title;

  /** @see #getId() */
  @XmlID
  @XmlAttribute(name = "id")
  private String id;

  /**
   * The constructor.
   */
  public SearchEntryTypeBean() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String getIcon() {

    return this.icon;
  }

  /**
   * This method sets the {@link #getIcon() Icon}.
   * 
   * @param icon is the icon to set.
   */
  public void setIcon(String icon) {

    this.icon = icon;
  }

  /**
   * {@inheritDoc}
   */
  public String getId() {

    return this.id;
  }

  /**
   * This method sets the {@link #getId() ID}.
   * 
   * @param id is the id to set
   */
  public void setId(String id) {

    this.id = id;
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
  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder();
    buffer.append(SearchEntryType.class.getSimpleName());
    buffer.append(':');
    buffer.append(this.id);
    if (this.title != null) {
      buffer.append('[');
      buffer.append(this.title);
      buffer.append(']');
    }
    return buffer.toString();
  }

}
