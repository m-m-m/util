/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.panel;

import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the interface for a {@link UiWidgetAbstractButtonContainer button container} that represents a
 * <em>toolbar</em>. Such toolbar typically contains a number of
 * {@link net.sf.mmm.client.ui.api.widget.core.UiWidgetAbstractButton buttons} that have an icon instead of a
 * label. For additional features please see {@link UiWidgetAbstractButtonContainer}.<br/>
 * As a toolbar typically contains many icon buttons it has its own focus management: Only one button of the
 * toolbar is <em>active</em> and will receive the focus when you tab. Using arrow left/right keys you can
 * move the focus to the previous/next button that will then become active.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetToolbar extends UiWidgetAbstractButtonContainer, UiWidgetNative {

  /** The default {@link #getPrimaryStyle() primary style} of this widget. */
  String STYLE_PRIMARY = CssStyles.TOOLBAR;

}
