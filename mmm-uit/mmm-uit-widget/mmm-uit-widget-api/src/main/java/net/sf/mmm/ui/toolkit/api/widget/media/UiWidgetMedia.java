/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.media;

import java.util.Iterator;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWritePaused;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWritePositionInSeconds;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteUrl;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteVolume;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetAtomic;
import net.sf.mmm.util.value.api.Range;

/**
 * This is the abstract interface for a {@link UiWidgetAtomic atomic widget} that represents media player. For
 * simple usage see {@link UiWidgetMediaPlayer}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetMedia extends UiWidgetAtomic, AttributeWriteUrl, AttributeWritePaused,
    AttributeWriteVolume, AttributeWritePositionInSeconds {

  /**
   * This method gets the duration of the {@link #getUrl() current media} in seconds. The duration is the
   * maximum value for the {@link #getPositionInSeconds() playback position}.
   * 
   * @return the duration in seconds.
   */
  double getDurationInSeconds();

  /**
   * @return an iterator of disjunct {@link Range}s within the media that are available in the buffer.
   *         Typically just a single range {@link Range#getMin() starting} at <code>0.0</code>
   *         {@link Range#getMax() ending} before or at the media {@link #getDurationInSeconds() duration}.
   *         However, in case of peer-to-peer technology the stream may also be available in chunks identified
   *         by multiple ranges. This method is typically used to display the buffered sections in the
   *         timer-bar of the media-player.
   */
  Iterator<Range<Double>> getBufferRanges();

}
