/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.media;

import net.sf.mmm.client.ui.api.attribute.AttributeWritePaused;
import net.sf.mmm.client.ui.api.handler.action.UiHandlerActionPreviousNext;
import net.sf.mmm.client.ui.api.media.AudioDescriptor;
import net.sf.mmm.client.ui.api.media.VideoDescriptor;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the abstract interface for a {@link UiWidgetComposite composite widget} that represents a
 * media-player. It can both play audio or video medias and has controls for the end-user (volume, time-bar,
 * fullscreen, etc.).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetMediaPlayer extends UiWidgetComposite<UiWidget>, AttributeWritePaused, UiWidgetNative {

  /**
   * This method makes the <em>previous</em> and <em>next</em> buttons visible and connects them with the
   * given <code>handler</code>.
   * 
   * @param handler is the {@link UiHandlerActionPreviousNext}.
   */
  void setPreviousNextHandler(UiHandlerActionPreviousNext handler);

  /**
   * This method loads the audio track identified by the given {@link AudioDescriptor} into this media-player.
   * If the media-player is currently playing )(not {@link #isPaused() paused}), the new media will
   * automatically be played. Use {@link #setPaused(boolean)} before to avoid this.
   * 
   * @param descriptor is the {@link AudioDescriptor} identifying the given track.
   */
  void setMediaAudio(AudioDescriptor descriptor);

  /**
   * This method loads the video stream identified by the given {@link VideoDescriptor} into this
   * media-player. If the media-player is currently playing )(not {@link #isPaused() paused}), the new media
   * will automatically be played. Use {@link #setPaused(boolean)} before to avoid this.
   * 
   * @param descriptor is the {@link VideoDescriptor} identifying the video stream.
   */
  void setMediaVideo(VideoDescriptor descriptor);

}
