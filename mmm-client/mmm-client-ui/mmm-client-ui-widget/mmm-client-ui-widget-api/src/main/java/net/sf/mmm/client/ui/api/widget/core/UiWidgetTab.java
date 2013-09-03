/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.core;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteImage;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteLabel;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.UiWidgetSingleMutableComposite;

/**
 * This is the interface for a {@link UiWidgetSingleMutableComposite single composite widget} that represents
 * a single <code>tab</code>. In order to
 * {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetTabPanel#addChild(UiWidgetTab) add} a something like a
 * {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetPanel panel} to a
 * {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetTabPanel tab panel}, you need to create an
 * {@link UiWidgetTab} {@link #getChild() containing} that
 * {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetTabPanel panel} and add it to the
 * {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetTabPanel tab panel}.
 * 
 * @see net.sf.mmm.client.ui.api.widget.panel.UiWidgetTabPanel
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetTab extends UiWidgetSingleMutableComposite<UiWidgetRegular>, AttributeWriteLabel,
    AttributeWriteImage<UiWidgetImage>, UiWidgetNative {

  /** The default {@link #getPrimaryStyle() primary style} of this widget. */
  String STYLE_PRIMARY = "Tab";

}
