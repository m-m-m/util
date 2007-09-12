/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.base;

import java.util.Collection;
import java.util.Collections;

import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.base.ClassAnnotation;
import net.sf.mmm.content.base.FieldAnnotation;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.resource.api.ContentResource;
import net.sf.mmm.content.value.base.SmartId;

/**
 * This is the implementation of the abstract entity {@link ContentResource}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ClassAnnotation(id = ContentResource.CLASS_ID, name = ContentResource.CLASS_NAME, isExtendable = true)
public abstract class AbstractContentResource extends AbstractContentObject implements
    ContentResource {
  
  /**
   * The constructor.
   */
  public AbstractContentResource() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param id is the {@link #getId() ID}.
   * @param name is the {@link #getName() name}.
   */
  public AbstractContentResource(SmartId id, String name) {

    super(id);
    setName(name);
  }

  /**
   * The constructor.
   * 
   * @param name is the {@link #getName() name} of the resource.
   */
  public AbstractContentResource(String name) {

    super();
    setName(name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ContentClass getContentClass() {

    // TODO:
    // use static instance of content-model service?
    // use instance member?
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @FieldAnnotation(id = 55)
  public abstract ContentResource getParent();

  /**
   * {@inheritDoc}
   */
  @Override
  @FieldAnnotation(id = 56)
  public Collection<? extends ContentResource> getChildren() {

    return Collections.emptyList();
  }

}
