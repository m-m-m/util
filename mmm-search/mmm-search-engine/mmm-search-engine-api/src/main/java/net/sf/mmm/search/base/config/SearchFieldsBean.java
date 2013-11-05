/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.base.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.api.config.SearchFieldType;
import net.sf.mmm.search.api.config.SearchFields;
import net.sf.mmm.util.nls.api.DuplicateObjectException;

/**
 * This is the implementation of {@link SearchFields} as JAXB-ready Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchFieldsBean implements SearchFields {

  /** The defaults for internal {@link #getFieldConfiguration(String) fields}. */
  private static final Map<String, SearchFieldConfigurationBean> DEFAULTS;

  static {
    DEFAULTS = new HashMap<String, SearchFieldConfigurationBean>();
    add(DEFAULTS, new SearchFieldConfigurationBean(SearchEntry.FIELD_ID, SearchFieldType.LONG));
    add(DEFAULTS, new SearchFieldConfigurationBean(SearchEntry.FIELD_CUSTOM_ID, SearchFieldType.STRING));
    add(DEFAULTS, new SearchFieldConfigurationBean(SearchEntry.FIELD_URI, SearchFieldType.STRING));
    add(DEFAULTS, new SearchFieldConfigurationBean(SearchEntry.FIELD_SOURCE, SearchFieldType.STRING));
    add(DEFAULTS, new SearchFieldConfigurationBean(SearchEntry.FIELD_CREATOR));
    add(DEFAULTS, new SearchFieldConfigurationBean(SearchEntry.FIELD_KEYWORDS));
    add(DEFAULTS, new SearchFieldConfigurationBean(SearchEntry.FIELD_LANGUAGE, SearchFieldType.STRING));
    add(DEFAULTS, new SearchFieldConfigurationBean(SearchEntry.FIELD_SIZE, SearchFieldType.LONG));
    add(DEFAULTS, new SearchFieldConfigurationBean(SearchEntry.FIELD_TEXT));
    add(DEFAULTS, new SearchFieldConfigurationBean(SearchEntry.FIELD_TITLE));
    add(DEFAULTS, new SearchFieldConfigurationBean(SearchEntry.FIELD_TYPE, SearchFieldType.STRING));
  }

  /** @see #getFields() */
  @XmlElement(name = "field")
  private List<SearchFieldConfigurationBean> fields;

  /** @see #getFieldConfiguration(String) */
  private transient Map<String, SearchFieldConfigurationBean> fieldMap;

  /**
   * The constructor.
   */
  public SearchFieldsBean() {

    super();
  }

  /**
   * This method adds the given <code>fieldConfiguration</code> to the given <code>map</code>.
   * 
   * @param map is the {@link Map}.
   * @param fieldConfiguration is the {@link SearchFieldConfigurationBean}.
   */
  private static void add(Map<String, SearchFieldConfigurationBean> map, SearchFieldConfigurationBean fieldConfiguration) {

    String key = fieldConfiguration.getName();
    if (map.containsKey(key)) {
      throw new DuplicateObjectException(fieldConfiguration, key);
    }
    map.put(key, fieldConfiguration);
  }

  /**
   * This method gets the {@link List} of {@link SearchFieldConfigurationBean fields}.
   * 
   * @return the fields.
   */
  public List<SearchFieldConfigurationBean> getFields() {

    return this.fields;
  }

  /**
   * This method gets the {@link Map} of {@link SearchFieldConfigurationBean fields}.
   * 
   * @return the field-map.
   */
  protected Map<String, SearchFieldConfigurationBean> getFieldMap() {

    if (this.fieldMap == null) {
      if (this.fields == null) {
        return Collections.emptyMap();
      }
      Map<String, SearchFieldConfigurationBean> map = new HashMap<String, SearchFieldConfigurationBean>();
      for (SearchFieldConfigurationBean field : this.fields) {
        add(map, field);
      }
      this.fieldMap = map;
    }
    return this.fieldMap;
  }

  /**
   * @param fields is the fields to set
   */
  public void setFields(List<SearchFieldConfigurationBean> fields) {

    this.fields = fields;
    this.fieldMap = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchFieldConfigurationBean getFieldConfiguration(String name) {

    SearchFieldConfigurationBean result = getFieldMap().get(name);
    if (result == null) {
      result = DEFAULTS.get(name);
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchFieldConfigurationBean getOrCreateFieldConfiguration(String name) {

    SearchFieldConfigurationBean result = getFieldConfiguration(name);
    if (result == null) {
      result = new SearchFieldConfigurationBean(name);
    }
    return result;
  }

}
