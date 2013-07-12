/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterFileField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterRichTextField;
import net.sf.mmm.util.io.api.FileItem;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAdapterRichTextField} using GWT based on {@link RichTextArea}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtFileField extends UiWidgetAdapterGwtField<FileUpload, FileItem, FileItem> implements
    UiWidgetAdapterFileField, TakesValue<FileItem> {

  /** @see #createViewWidget() */
  private Anchor viewWidget;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtFileField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Focusable getWidgetAsFocusable() {

    // return getActiveWidget();
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    getActiveWidget().setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasChangeHandlers getWidgetAsHasChangeHandlers() {

    return getActiveWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TakesValue<FileItem> getWidgetAsTakesValue() {

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FileItem getValue() {

    String filename = getActiveWidget().getFilename();

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(FileItem value) {

    // TODO Auto-generated method stub
    super.setValue(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasKeyPressHandlers getWidgetAsKeyPressHandlers() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasAllFocusHandlers getWidgetAsHasAllFocusHandlers() {

    // TODO Why does GWT not support this???
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FileUpload createActiveWidget() {

    return new FileUpload();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void updateWidgetViewMode() {

    FileItem value = getValue();
    getWidgetViewMode();
    this.viewWidget.setText(value.getName());
  }

  private void onDownload() {

    // how to do download?
    // Variant 1. use a global iframe and send post request with target to iframe (hack)
    // Variant 2. use XHR2 (how to trigger download popup of browser on download callback?)
    // Server-side service shall contain the ID of the blob. Additionally a generic service argument
    // should be configurable in UiWidgetFileField that is send along. Also a regular filedownload from
    // anywhere should work. We need to work on CORS support...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget createViewWidget() {

    assert (this.viewWidget == null);
    this.viewWidget = new Anchor();
    ClickHandler handler = new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        onDownload();
      }
    };
    this.viewWidget.addClickHandler(handler);
    return this.viewWidget;
  }

  /**
   * This inner class makes {@link RichTextArea} implement {@link HasValue}.
   */
  public static class MyRichTextArea extends RichTextArea implements HasValue<String>, HasChangeHandlers {

    /**
     * The constructor.
     */
    public MyRichTextArea() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {

      // TODO Auto-generated method stub
      return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HandlerRegistration addChangeHandler(ChangeHandler handler) {

      // TODO Auto-generated method stub
      return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {

      return getHTML();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(String value) {

      setHTML(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(String value, boolean fireEvents) {

      setHTML(value);
    }

  }

}
