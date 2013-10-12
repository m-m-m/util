/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.adapter;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteMaximumTextLength;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteSelectionMode;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteStringTitle;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteValidationFailure;
import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.api.feature.UiFeatureEvent;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.client.ui.base.widget.adapter.AbstractUiWidgetAdapter;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterButton;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterCollapsableSection;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterImage;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterLabel;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterLink;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterSection;
import net.sf.mmm.client.ui.base.widget.menu.adapter.UiWidgetAdapterMenuItemClickable;
import net.sf.mmm.client.ui.base.widget.menu.adapter.UiWidgetAdapterMenuItemSeparator;
import net.sf.mmm.util.nls.api.ObjectDisposedException;

/**
 * This is the implementation of {@link UiWidgetAdapter} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterTest extends AbstractUiWidgetAdapter<Void> implements AttributeWriteMaximumTextLength,
    AttributeWriteValidationFailure, UiWidgetAdapterButton, UiWidgetAdapterCollapsableSection, UiWidgetAdapterImage,
    UiWidgetAdapterLabel, UiWidgetAdapterLink, UiWidgetAdapterSection, UiWidgetAdapterMenuItemClickable,
    UiWidgetAdapterMenuItemSeparator, AttributeWriteSelectionMode, AttributeWriteStringTitle {

  /** @see #getWidth() */
  private Length width;

  /** @see #getHeight() */
  private Length height;

  /** @see #dispose() */
  private boolean disposed;

  /** @see #setEnabled(boolean) */
  private boolean collapsed;

  /** @see #getValidationFailure() */
  private String validationFailure;

  /** @see #getValidationFailureSetCount() */
  private int validationFailureSetCount;

  /**
   * The constructor.
   */
  public UiWidgetAdapterTest() {

    super();
    this.disposed = false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Void createToplevelWidget() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeFromParent() {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String createAbsoluteImageUrl(String relativePath) {

    verifyNotDisposed();
    return relativePath;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEventSender(UiFeatureEvent source, UiHandlerEvent sender) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidth(Length width) {

    verifyNotDisposed();
    this.width = width;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeight(Length height) {

    verifyNotDisposed();
    this.height = height;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getWidth() {

    verifyNotDisposed();
    return this.width;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getHeight() {

    verifyNotDisposed();
    return this.height;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setStyles(String styles) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTooltip(String tooltip) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String id) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAttribute(String name, String value) {

    verifyNotDisposed();
  }

  /**
   * Verifies that this object is NOT already {@link #dispose() disposed}.
   */
  protected void verifyNotDisposed() {

    if (this.disposed) {
      throw new ObjectDisposedException(this);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    this.disposed = true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    // dummy/mock
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setVisible(boolean visible) {

    // dummy/mock
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAccessKey(char accessKey) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setImage(UiWidgetImage image) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setFocused() {

    verifyNotDisposed();
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCollapsed(boolean collapsed) {

    verifyNotDisposed();
    this.collapsed = collapsed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCollapsed() {

    verifyNotDisposed();
    return this.collapsed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUrl(String url) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAltText(String altText) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValidationFailure() {

    return this.validationFailure;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValidationFailure(String validationFailure) {

    verifyNotDisposed();
    this.validationFailure = validationFailure;
    this.validationFailureSetCount++;
  }

  /**
   * @return the number of invocations to {@link #setValidationFailure(String)} since
   *         {@link #clearValidationFailureSetCount()}.
   */
  public int getValidationFailureSetCount() {

    return this.validationFailureSetCount;
  }

  /**
   * Sets the value of {@link #getValidationFailureSetCount()} to <code>0</code>.
   */
  public void clearValidationFailureSetCount() {

    this.validationFailureSetCount = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumTextLength(int length) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectionMode(SelectionMode selectionMode) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {

    verifyNotDisposed();
  }

}
