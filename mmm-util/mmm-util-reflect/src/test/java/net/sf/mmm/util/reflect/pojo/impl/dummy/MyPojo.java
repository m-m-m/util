/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl.dummy;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a dummy pojo for testing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class MyPojo extends AbstractPojo {

  private Integer port;

  private Boolean flag;

  private List<String> items;

  private String string;

  private String privateString;

  public MyPojo() {

    super();
    this.port = null;
    this.flag = null;
    this.items = new ArrayList<String>();
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

    this.items.add(item);
  }

  public void setItems(List<String> items) {

    this.items = items;
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

}
