/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import net.sf.mmm.search.engine.api.config.SearchEntryType;
import net.sf.mmm.search.engine.api.config.SearchEntryTypeContainer;

/**
 * This is the implementation of {@link SearchEntryType} as JAXB-ready Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchEntryTypeContainerBean implements SearchEntryTypeContainer {

  /** @see #isExtendDefaults() */
  @XmlAttribute(name = "extend-defaults")
  private boolean extendDefaults;

  /** @see #getEntryTypes() */
  @XmlElement(name = "type")
  private List<SearchEntryTypeBean> entryTypes;

  /** @see #getEntryType(String) */
  private transient Map<String, SearchEntryType> entryTypeMap;

  /**
   * The constructor.
   */
  public SearchEntryTypeContainerBean() {

    super();
    this.extendDefaults = true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isExtendDefaults() {

    return this.extendDefaults;
  }

  /**
   * @param extendDefaults is the extendDefaults to set
   */
  public void setExtendDefaults(boolean extendDefaults) {

    this.extendDefaults = extendDefaults;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchEntryType getEntryType(String id) {

    SearchEntryType result = getEntryTypeMap().get(id);
    if (result == null) {
      result = getEntryTypeMap().get(SearchEntryType.ID_ANY);
    }
    return result;
  }

  /**
   * This method gets the {@link Map} of {@link SearchEntryTypeBean}s.
   * 
   * @return the entry-type-map.
   */
  protected Map<String, SearchEntryType> getEntryTypeMap() {

    if (this.entryTypeMap == null) {
      Map<String, SearchEntryType> map = new HashMap<String, SearchEntryType>();
      for (SearchEntryType type : SearchEntryTypeDefaults.getDefaultTypes()) {
        map.put(type.getId(), type);
      }
      if (this.entryTypes != null) {
        for (SearchEntryType type : this.entryTypes) {
          map.put(type.getId(), type);
        }
      }
      this.entryTypeMap = Collections.unmodifiableMap(map);
    }
    return this.entryTypeMap;
  }

  /**
   * This method gets the list of raw entry types (without {@link #isExtendDefaults() extension}).
   * 
   * @return the {@link SearchEntryType}s.
   */
  public List<SearchEntryTypeBean> getEntryTypes() {

    if (this.entryTypes == null) {
      return Collections.emptyList();
    }
    return this.entryTypes;
  }

  /**
   * @param entryTypes is the entryTypes to set
   */
  public void setEntryTypes(List<SearchEntryTypeBean> entryTypes) {

    this.entryTypes = entryTypes;
    this.entryTypeMap = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<SearchEntryType> iterator() {

    return getEntryTypeMap().values().iterator();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getClass().getSimpleName();
  }

}
