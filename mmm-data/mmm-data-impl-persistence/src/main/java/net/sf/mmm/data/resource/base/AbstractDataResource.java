/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.resource.base;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldAnnotation;
import net.sf.mmm.data.base.AbstractDataObject;
import net.sf.mmm.data.resource.api.ContentResource;
import net.sf.mmm.util.resource.api.DataResource;

/**
 * This is the implementation of the abstract entity {@link DataResource}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = ContentResource.CLASS_ID, title = ContentResource.CLASS_NAME, isExtendable = true)
public abstract class AbstractDataResource extends AbstractDataObject implements DataResource {

  /** TODO: javadoc. */
  private static final long serialVersionUID = 1983465184282662205L;

  /** @see #getProxyTarget() */
  private AbstractDataResource proxyTarget;

  /** @see #getParent() */
  private AbstractDataResource parent;

  /**
   * The constructor.
   */
  public AbstractDataResource() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param name is the {@link #getTitle() name}.
   * @param parent is the {@link #getParent() parent}.
   */
  public AbstractDataResource(String name, AbstractDataResource parent) {

    super(name);
    setParent(parent);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataResource getParent() {

    return this.parent;
  }

  /**
   * @param parent the parent to set
   */
  private void setParent(AbstractDataResource parent) {

    this.parent = parent;
  }

  /**
   * {@inheritDoc}
   */
  @DataFieldAnnotation(id = 56)
  public final AbstractDataResource getProxyTarget() {

    return this.proxyTarget;
  }

  /**
   * @param proxyTarget the proxyTarget to set
   */
  public void setProxyTarget(AbstractDataResource proxyTarget) {

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
  public AbstractDataResource getRawObject() {

    return this;
  }

  public static abstract class AbstractDataResourceModifier {

    /**
     * Sets the <code>{@link DataObject#getParent() parent}</code> of the given
     * <code>resource</code>.
     * 
     * @param resource is the resource to modify.
     * @param parent is the {@link DataObject#getParent() parent} to set.
     */
    protected void setContentResourceParent(AbstractDataResource resource,
        AbstractDataResource parent) {

      resource.setParent(parent);
    }
  }

}
