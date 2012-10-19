/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.media;

/**
 * This is the implementation of {@link MediaDescriptorBean} as a simple java bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class VideoDescriptorBean extends MediaDescriptorBean implements VideoDescriptor {

  /** @see #getSubtitleUrl() */
  private String subtitleUrl;

  /**
   * The constructor.
   */
  public VideoDescriptorBean() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSubtitleUrl() {

    return this.subtitleUrl;
  }

}
