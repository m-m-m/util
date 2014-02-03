/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets.richtext;

import net.sf.mmm.client.ui.api.common.RichTextFeature;

import com.google.gwt.event.dom.client.ClickEvent;

/**
 * This is the implementation of {@link FeatureBehavior} for {@link RichTextFeature#ORDERED_LIST}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
class FeatureBehaviorOrderedList extends AbstractSimpleClickFeatureBehavior {

  /**
   * The constructor.
   * 
   * @param richTextToolbar is the {@link RichTextToolbar} owning this {@link FeatureBehavior}.
   */
  public FeatureBehaviorOrderedList(RichTextToolbar richTextToolbar) {

    super(richTextToolbar);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RichTextFeature getFeature() {

    return RichTextFeature.ORDERED_LIST;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onClick(ClickEvent event) {

    getFormatter().insertOrderedList();
  }

}
