/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteImage;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteLabel;
import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;

/**
 * This is the abstract interface for an {@link UiWidgetActive regular atomic widget} that has a
 * {@link #getLabel() label} and is {@link #click() clickable}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetClickable extends UiWidgetActive, UiFeatureClick, AttributeWriteLabel,
    AttributeWriteImage<UiWidgetImage> {

  /**
   * {@inheritDoc}
   */
  @Override
  void setImage(UiWidgetImage image);

}
