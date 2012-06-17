/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import net.sf.mmm.search.engine.api.config.SearchEntryType;
import net.sf.mmm.util.lang.base.attribute.AbstractAttributeReadId;

/**
 * This is the implementation of {@link SearchEntryType} as JAXB-ready Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchEntryTypeBean extends AbstractAttributeReadId<String> implements SearchEntryType {

  /** @see #getIcon() */
  @XmlAttribute(name = "icon")
  private String icon;

  /** @see #getTitle() */
  @XmlAttribute(name = "title")
  private String title;

  /** @see #getId() */
  @XmlAttribute(name = "id")
  private String id;

  /**
   * The constructor.
   */
  public SearchEntryTypeBean() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param id is the {@link #getId() ID}.
   * @param title is the {@link #getTitle() title}.
   * @param icon is the {@link #getIcon() icon}.
   */
  public SearchEntryTypeBean(String id, String title, String icon) {

    super();
    this.icon = icon;
    this.title = title;
    this.id = id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
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
  @Override
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
  @Override
  public String getTitle() {

    return this.title;
  }

  /**
   * @param title is the title to set
   */
  public void setTitle(String title) {

    this.title = title;
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
