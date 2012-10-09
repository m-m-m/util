/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.media;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteStringTitle;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteUrl;

/**
 * This is the abstract base implementation of {@link MediaDescriptor} as java bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class MediaDescriptorBean implements MediaDescriptor, AttributeWriteUrl, AttributeWriteStringTitle {

  /** @see #getUrl() */
  private String url;

  /** @see #getTitle() */
  private String title;

  /**
   * The constructor.
   */
  public MediaDescriptorBean() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUrl() {

    return this.url;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUrl(String url) {

    this.url = url;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return this.title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {

    this.title = title;
  }

}
