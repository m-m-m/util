/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.resource.base;

import net.sf.mmm.data.api.ContentClassAnnotation;
import net.sf.mmm.data.api.ContentFieldAnnotation;
import net.sf.mmm.data.api.ContentObject;
import net.sf.mmm.data.base.AbstractContentObject;
import net.sf.mmm.data.datatype.api.ContentId;
import net.sf.mmm.data.resource.RevisionControl;
import net.sf.mmm.data.resource.api.ContentResource;

/**
 * This is the implementation of the abstract entity {@link ContentResource}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ContentClassAnnotation(id = ContentResource.CLASS_ID, title = ContentResource.CLASS_NAME, isExtendable = true, revisionControl = RevisionControl.YES)
public abstract class AbstractContentResource extends AbstractContentObject implements
    ContentResource {

  /** TODO: javadoc. */
  private static final long serialVersionUID = 1983465184282662205L;

  /** @see #getProxyTarget() */
  private AbstractContentResource proxyTarget;

  /** @see #getParent() */
  private AbstractContentResource parent;

  /**
   * The constructor.
   */
  public AbstractContentResource() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param name is the {@link #getTitle() name}.
   * @param parent is the {@link #getParent() parent}.
   */
  public AbstractContentResource(String name, AbstractContentResource parent) {

    super(name);
    setParent(parent);
  }

  /**
   * The constructor.
   * 
   * @param name is the {@link #getTitle() name}.
   * @param parent is the {@link #getParent() parent}.
   * @param id is the {@link #getContentId() ID}.
   */
  public AbstractContentResource(String name, AbstractContentResource parent, ContentId id) {

    super(name, id);
    setParent(parent);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractContentResource getParent() {

    return this.parent;
  }

  /**
   * @param parent the parent to set
   */
  private void setParent(AbstractContentResource parent) {

    this.parent = parent;
  }

  /**
   * {@inheritDoc}
   */
  @ContentFieldAnnotation(id = 56)
  public final AbstractContentResource getProxyTarget() {

    return this.proxyTarget;
  }

  /**
   * @param proxyTarget the proxyTarget to set
   */
  public void setProxyTarget(AbstractContentResource proxyTarget) {

    this.proxyTarget = proxyTarget;
  }

  /**
   * This method gets the raw instance of this object.<br>
   * According to {@link #getProxyTarget() proxying} and {@link #getParent()
   * inheritance} this instance may be "manipulated" to add the according
   * support. Therefore this method returns the original object without
   * manipulations. This is needed e.g. for the editor GUI.
   * 
   * @return the raw object.
   */
  public AbstractContentResource getRawObject() {

    return this;
  }

  public static abstract class AbstractContentResourceModifier extends
      AbstractContentObjectModifier {

    /**
     * Sets the <code>{@link ContentObject#getParent() parent}</code> of the
     * given <code>resource</code>.
     * 
     * @param resource is the resource to modify.
     * @param parent is the {@link ContentObject#getParent() parent} to set.
     */
    protected void setContentResourceParent(AbstractContentResource resource,
        AbstractContentResource parent) {

    }
  }

}
