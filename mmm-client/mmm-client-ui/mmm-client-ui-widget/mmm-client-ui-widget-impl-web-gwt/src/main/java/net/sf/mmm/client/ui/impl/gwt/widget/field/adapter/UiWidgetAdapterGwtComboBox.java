/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import java.util.List;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterOptionsField;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAdapterOptionsField} using GWT based on a {@link TextBox} and
 * a {@link Datalist}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed value.
 */
public class UiWidgetAdapterGwtComboBox<VALUE> extends UiWidgetAdapterGwtFieldFocusWidgetBase<TextBox, VALUE, String>
    implements UiWidgetAdapterOptionsField<VALUE> {

  /** @see #createActiveWidget() */
  private static int idCounter = 1;

  /** @see #createActiveWidget() */
  private Datalist datalist;

  /** @see #setOptions(List) */
  private List<String> options;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtComboBox() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TextBox createActiveWidget() {

    TextBox combo = new TextBox();
    String datalistId = "combo" + idCounter++;
    this.datalist = new Datalist();
    this.datalist.getElement().setId(datalistId);
    if (this.options != null) {
      this.datalist.setOptions(this.options);
    }
    combo.getElement().setAttribute("list", datalistId);
    return combo;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void attachActiveWidget() {

    super.attachActiveWidget();
    getToplevelWidget().add(this.datalist);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getOptions() {

    return this.options;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOptions(List<String> options) {

    this.options = options;
    if (this.datalist != null) {
      this.datalist.setOptions(options);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValue() {

    return getActiveWidget().getText();
    // if (this.options != null) {
    // int selectedIndex = getActiveWidget().getSelectedIndex();
    // if ((selectedIndex >= 0) && (selectedIndex < this.options.size())) {
    // return this.options.get(selectedIndex);
    // }
    // }
    // return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(String value) {

    getActiveWidget().setText(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasValue<String> getWidgetAsHasValue() {

    throw new NlsIllegalStateException();
  }

  /**
   * This inner class is a {@link Widget} that represents a <code>datalist</code> for an HTML5 combobox.
   */
  protected static class Datalist extends Widget {

    /**
     * The constructor.
     */
    public Datalist() {

      super();
      setElement(Document.get().createElement("datalist"));
    }

    /**
     * @param options the textual options.
     */
    public void setOptions(List<String> options) {

      // clear potential previous options
      com.google.gwt.user.client.Element element = getElement();
      Element childElement = element.getFirstChildElement();
      while (childElement != null) {
        childElement.removeFromParent();
      }

      // create new options
      for (String opt : options) {
        OptionElement option = Document.get().createOptionElement();
        // TODO use DirectionEstimator...
        option.setText(opt);
        element.appendChild(option);
      }
    }
  }
}
