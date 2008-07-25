/* $Id: $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.impl;

import net.sf.mmm.util.contenttype.base.AbstractContentType;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.contenttype.api.ContentType} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentTypeImpl extends AbstractContentType<ContentTypeImpl> {

  /**
   * The constructor.
   */
  public ContentTypeImpl() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param parent
   */
  public ContentTypeImpl(ContentTypeImpl parent) {

    super(parent);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTechnicalParent(ContentTypeImpl technicalParent) {

    // TODO Auto-generated method stub
    super.setTechnicalParent(technicalParent);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAbstract(boolean isAbstract) {

    // TODO Auto-generated method stub
    super.setAbstract(isAbstract);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDefaultExtension(String defaultExtension) {

    // TODO Auto-generated method stub
    super.setDefaultExtension(defaultExtension);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String id) {

    // TODO Auto-generated method stub
    super.setId(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMimetype(String mimetype) {

    // TODO Auto-generated method stub
    super.setMimetype(mimetype);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String setProperty(String key, String value) {

    // TODO Auto-generated method stub
    return super.setProperty(key, value);
  }

}
