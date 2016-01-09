/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.dummy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.mmm.util.pojo.path.api.TypedProperty;

/**
 * This is a dummy pojo for testing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class MyPojo extends AbstractPojo {

  public static final TypedProperty<Integer> PROPERTY_PORT = new TypedProperty<Integer>("port");

  public static final TypedProperty<Long> PROPERTY_GENERICPOJO_ELEMENT = new TypedProperty<Long>("element",
      "genericPojo");

  private Integer port;

  private Boolean flag;

  private List<String> items;

  private String string;

  private String privateString;

  private String privateProperty;

  private Map<String, String> values;

  private GenericPojo<Long> genericPojo;

  public MyPojo() {

    super();
    this.port = null;
    this.flag = null;
    this.items = new ArrayList<String>();
    this.privateString = "privateString";
  }

  public Integer getPort() {

    return this.port;
  }

  public void setPort(int port) {

    this.port = Integer.valueOf(port);
  }

  public Boolean hasFlag() {

    return this.flag;
  }

  public void setFlag(boolean newFlag) {

    this.flag = Boolean.valueOf(newFlag);
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

  public void setRenamedProperty(String value) {

    this.string = value;
  }

  public String getRenamedProperty() {

    return this.string;
  }

  public String getPrivateString() {

    return new String(this.privateString);
  }

  private String getPrivateProperty() {

    return this.privateProperty;
  }

  private void setPrivateProperty(String privateProperty) {

    this.privateProperty = privateProperty;
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

  @Override
  public String getName() {

    return super.getName().toString();
  }

  public GenericPojo<Long> getGenericPojo() {

    return this.genericPojo;
  }

  public void setGenericPojo(GenericPojo<Long> genericPojo) {

    this.genericPojo = genericPojo;
  }

}
