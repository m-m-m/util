/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.core;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteImage;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteLabel;
import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.widget.UiWidgetActive;
import net.sf.mmm.client.ui.api.widget.UiWidgetReal;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegularAtomic;

/**
 * This is the interface for an {@link UiWidgetRegularAtomic regular atomic widget} that represents a
 * <em>button</em>.<br/>
 * Here you can see an example (with {@link #setLabel(String) label} "Click me"):
 * 
 * <pre>
 * <button type="button">Click me</button>
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetButton extends UiWidgetRegularAtomic, UiWidgetActive, UiFeatureClick, AttributeWriteLabel,
    AttributeWriteImage<UiWidgetImage>, UiWidgetReal {

  /** The default {@link #getPrimaryStyle() primary style} of this widget. */
  String PRIMARY_STYLE = "Button";

}
