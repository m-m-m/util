/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.media;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteImage;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteMaximized;
import net.sf.mmm.client.ui.api.widget.UiWidgetReal;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;

/**
 * This is the interface for a {@link UiWidgetMedia media-player widget} that plays videos (movies). <br/>
 * <b>NOTE:</b><br/>
 * This widget is just the video-screen itself without controls. For simple usage see
 * {@link UiWidgetMediaPlayer}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetMediaVideo extends UiWidgetMedia, AttributeWriteMaximized, AttributeWriteImage<UiWidgetImage>,
    UiWidgetReal {

  // nothing to add...

}
