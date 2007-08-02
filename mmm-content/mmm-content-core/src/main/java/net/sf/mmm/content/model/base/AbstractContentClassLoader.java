/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

/**
 * This is the abstract base implementation of the {@link ContentClassLoader}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentClassLoader implements ContentClassLoader {

  /** @see #getContentModelService() */
  private final AbstractMutableContentModelService contentModelService;

  /**
   * The constructor.
   * 
   * @param contentModelService is the
   *        {@link #getContentModelService() content-model service}.
   */
  public AbstractContentClassLoader(AbstractMutableContentModelService contentModelService) {

    super();
    this.contentModelService = contentModelService;
  }

  /**
   * This method gets the content-model service.
   * 
   * @return the content-model service.
   */
  protected AbstractMutableContentModelService getContentModelService() {

    return this.contentModelService;
  }
  
}
