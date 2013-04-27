/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.core;

import net.sf.mmm.client.ui.api.aria.role.RoleHeading;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteLabel;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegularAtomic;

/**
 * This is the interface for a {@link UiWidgetRegularAtomic regular atomic widget} that represents a
 * <em>section</em>. A section is a widget that displays a short text in a single line. The text is supposed
 * to be a title that explains the content below. This is an alternative to
 * {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetBorderPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetSection extends UiWidgetRegularAtomic, AttributeWriteLabel, UiWidgetNative {

  /** The default {@link #getPrimaryStyle() primary style} of this widget. */
  String PRIMARY_STYLE = "Section";

  /**
   * {@inheritDoc}
   */
  @Override
  RoleHeading getAriaRole();

}
