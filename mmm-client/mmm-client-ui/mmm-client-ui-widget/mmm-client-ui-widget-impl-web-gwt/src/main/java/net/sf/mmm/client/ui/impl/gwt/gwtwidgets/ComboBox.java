/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import com.google.gwt.user.client.ui.TextBox;

/**
 * A {@link ComboBox} is a {@link TextBox} {@link #setDataList(DataList) combined} with a {@link DataList}
 * that allows to choose from predefined {@link DataList#setOptions(java.util.List) options}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ComboBox extends TextBox implements AttributeWriteDataList {

  /**
   * The constructor.
   */
  public ComboBox() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public void setDataList(DataList dataList) {

    getElement().setAttribute(HtmlConstants.ATTRIBUTE_LIST, dataList.getId());
    dataList.setOwner(this);
  }

}
