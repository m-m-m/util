/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets.richtext;

import net.sf.mmm.client.ui.api.common.CssStyles;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * The abstract base implementation of a {@link FeatureBehavior} for a {@link #getFeature() feature} that is
 * clicked as a regular {@link Button}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
abstract class AbstractClickFeatureBehavior extends AbstractFeatureBehavior implements ClickHandler {

  /** @see #getToolbarWidget() */
  private final Button toolbarWidget;

  /**
   * The constructor.
   * 
   * @param richTextToolbar is the {@link RichTextToolbar} owning this {@link FeatureBehavior}.
   */
  public AbstractClickFeatureBehavior(RichTextToolbar richTextToolbar) {

    super(richTextToolbar);
    this.toolbarWidget = new Button(getIconMarkup());
    this.toolbarWidget.setStylePrimaryName(CssStyles.BUTTON);
    this.toolbarWidget.addStyleName(CssStyles.TOGGLE_BUTTON);
    this.toolbarWidget.setTitle(getLocalizedLabel());
    this.toolbarWidget.addClickHandler(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Button getToolbarWidget() {

    return this.toolbarWidget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEnabled() {

    return getToolbarWidget().isEnabled();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    getToolbarWidget().setEnabled(enabled);
  }

}
