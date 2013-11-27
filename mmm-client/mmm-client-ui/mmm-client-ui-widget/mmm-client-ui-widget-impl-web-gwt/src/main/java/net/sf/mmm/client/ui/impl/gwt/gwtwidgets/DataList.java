/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import java.util.Collections;
import java.util.List;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteOptions;
import net.sf.mmm.util.gwt.api.GwtUtil;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.user.client.ui.Widget;

/**
 * A {@link DataList} is a {@link Widget} that represents a <code>datalist</code> for an HTML5 combobox.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DataList extends Widget implements AttributeWriteOptions<String> {

  /** The owner of this {@link DataList}. */
  private AttributeWriteDataList owner;

  /** @see #getOptions() */
  private List<String> options;

  /**
   * The constructor.
   */
  public DataList() {

    super();
    setElement(Document.get().createElement(HtmlConstants.TAG_DATALIST));
  }

  /**
   * @param id is the new ID to set.
   */
  public void setId(String id) {

    getElement().setId(id);
    if (this.owner != null) {
      this.owner.setDataList(this);
    }
  }

  /**
   * @return the ID of this Datalist. A unique ID is automatically created if no ID has been
   *         {@link #setId(String) set} manually.
   */
  public String getId() {

    return GwtUtil.getInstance().getId(this);
  }

  /**
   * @param owner is the {@link AttributeWriteDataList} owning this {@link DataList}.
   */
  public void setOwner(AttributeWriteDataList owner) {

    this.owner = owner;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getOptions() {

    if (this.options == null) {
      return Collections.emptyList();
    }
    return this.options;
  }

  /**
   * @param options the textual options.
   */
  public void setOptions(List<String> options) {

    // clear potential previous options
    @SuppressWarnings("deprecation")
    com.google.gwt.user.client.Element element = getElement();
    Element childElement = element.getFirstChildElement();
    while (childElement != null) {
      childElement.removeFromParent();
      childElement = element.getFirstChildElement();
    }

    // create new options
    for (String opt : options) {
      OptionElement option = Document.get().createOptionElement();
      // TODO use DirectionEstimator...
      option.setText(opt);
      element.appendChild(option);
    }
    this.options = options;
  }

}
