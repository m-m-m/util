/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.base.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.sf.mmm.search.api.config.SearchFieldConfiguration;
import net.sf.mmm.search.api.config.SearchFieldMode;
import net.sf.mmm.search.api.config.SearchFieldType;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.xml.base.jaxb.XmlAdapterEnum;

/**
 * This is the implementation of {@link SearchFieldConfiguration} as JAXB-ready Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchFieldConfigurationBean implements SearchFieldConfiguration {

  /** @see #getName() */
  @XmlAttribute(name = "name")
  private String name;

  /** @see #isHidden */
  @XmlAttribute(name = "hidden")
  private boolean hidden;

  /** @see #getMode() */
  @XmlAttribute(name = "mode")
  @XmlJavaTypeAdapter(value = XmlAdapterSearchFieldMode.class)
  private SearchFieldMode mode;

  /** @see #getType() */
  @XmlAttribute(name = "type")
  @XmlJavaTypeAdapter(value = XmlAdapterSearchFieldType.class)
  private SearchFieldType type;

  /**
   * The constructor.
   */
  public SearchFieldConfigurationBean() {

    super();
    this.mode = SearchFieldMode.SEARCHABLE_AND_RETRIEVABLE;
    this.type = SearchFieldType.TEXT;
  }

  /**
   * The constructor.<br/>
   * Creates a default field with {@link SearchFieldType#TEXT} and
   * {@link SearchFieldMode#SEARCHABLE_AND_RETRIEVABLE}.
   * 
   * @param name is the {@link #getName() name}.
   */
  public SearchFieldConfigurationBean(String name) {

    this(name, SearchFieldType.TEXT);
  }

  /**
   * The constructor.<br/>
   * Creates a field with {@link SearchFieldMode#SEARCHABLE_AND_RETRIEVABLE}.
   * 
   * @param name is the {@link #getName() name}.
   * @param type is the {@link #getType() type}.
   */
  public SearchFieldConfigurationBean(String name, SearchFieldType type) {

    this(name, type, SearchFieldMode.SEARCHABLE_AND_RETRIEVABLE);
  }

  /**
   * The constructor.
   * 
   * @param name is the {@link #getName() name}.
   * @param type is the {@link #getType() type}.
   * @param mode is the {@link #getMode() mode}.
   */
  public SearchFieldConfigurationBean(String name, SearchFieldType type, SearchFieldMode mode) {

    super();
    setName(name);
    setMode(mode);
    setType(type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return this.name;
  }

  /**
   * @param name is the name to set
   */
  public void setName(String name) {

    NlsNullPointerException.checkNotNull("name", name);
    this.name = name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchFieldType getType() {

    if (this.type == null) {
      return SearchFieldType.TEXT;
    }
    return this.type;
  }

  /**
   * @param type is the type to set
   */
  public void setType(SearchFieldType type) {

    NlsNullPointerException.checkNotNull(SearchFieldType.class, type);
    this.type = type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchFieldMode getMode() {

    return this.mode;
  }

  /**
   * @param mode is the mode to set
   */
  public void setMode(SearchFieldMode mode) {

    NlsNullPointerException.checkNotNull(SearchFieldMode.class, mode);
    this.mode = mode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isHidden() {

    return this.hidden;
  }

  /**
   * @param hidden is the hidden to set
   */
  public void setHidden(boolean hidden) {

    this.hidden = hidden;
  }

  /**
   * @see XmlAdapterEnum
   */
  public static class XmlAdapterSearchFieldMode extends XmlAdapterEnum<SearchFieldMode> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<SearchFieldMode> getEnumClass() {

      return SearchFieldMode.class;
    }
  }

  /**
   * @see XmlAdapterEnum
   */
  public static class XmlAdapterSearchFieldType extends XmlAdapterEnum<SearchFieldType> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<SearchFieldType> getEnumClass() {

      return SearchFieldType.class;
    }
  }

}
