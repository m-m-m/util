/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteHtmlId;

import com.google.gwt.user.client.ui.TextBox;

/**
 * A {@link ComboBox} is a {@link TextBox} {@link #setDataList(DataList) combined} with a {@link DataList}
 * that allows to choose from predefined {@link DataList#setOptions(java.util.List) options}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ComboBox extends TextBox implements AttributeWriteDataList, AttributeWriteHtmlId {

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

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return getElement().getId();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String id) {

    getElement().setId(id);
  }

}
