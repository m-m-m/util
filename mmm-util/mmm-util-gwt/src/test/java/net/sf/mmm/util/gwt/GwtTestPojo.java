/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.gwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.mmm.util.lang.api.GenericBean;
import net.sf.mmm.util.pojo.api.Pojo;
import net.sf.mmm.util.pojo.path.api.TypedProperty;

/**
 * This is a dummy pojo for testing.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class GwtTestPojo implements Pojo {

  public static final TypedProperty<Integer> PROPERTY_PORT = new TypedProperty<>("port");

  public static final TypedProperty<GenericBean<Long>> PROPERTY_GENERICBEAN = new TypedProperty<>("genericBean");

  private Integer port;

  private List<String> items;

  private Map<String, String> values;

  private GenericBean<Long> genericBean;

  public GwtTestPojo() {

    super();
    this.port = null;
    this.items = new ArrayList<String>();
  }

  public Integer getPort() {

    return this.port;
  }

  public void setPort(int port) {

    this.port = Integer.valueOf(port);
  }

  public List<String> getItems() {

    return this.items;
  }

  public void addItem(String item) {

    this.items.add(new String(item));
  }

  public String getItem(int index) {

    return new String(this.items.get(index));
  }

  public void setItem(int index, String item) {

    this.items.set(index, new String(item));
  }

  public void setItems(List<String> items) {

    this.items = items;
  }

  public int getItemCount() {

    if (this.items == null) {
      return 0;
    }
    return this.items.size();
  }

  public Map<String, String> getValues() {

    return this.values;
  }

  public void setValues(Map<String, String> values) {

    this.values = values;
  }

  public String getValue(String key) {

    return new String(this.values.get(key));
  }

  public String setValue(String key, String value) {

    return this.values.put(key, new String(value));
  }

  public GenericBean<Long> getGenericBean() {

    return this.genericBean;
  }

  public void setGenericBean(GenericBean<Long> genericPojo) {

    this.genericBean = genericPojo;
  }

}
