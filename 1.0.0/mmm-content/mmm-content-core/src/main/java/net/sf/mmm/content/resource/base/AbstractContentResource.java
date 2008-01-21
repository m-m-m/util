/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.base;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.base.ClassAnnotation;
import net.sf.mmm.content.base.FieldAnnotation;
import net.sf.mmm.content.base.RevisionControl;
import net.sf.mmm.content.resource.api.ContentResource;
import net.sf.mmm.content.value.api.Version;
import net.sf.mmm.content.value.base.SmartId;

/**
 * This is the implementation of the abstract entity {@link ContentResource}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ClassAnnotation(id = ContentResource.CLASS_ID, name = ContentResource.CLASS_NAME, isExtendable = true, revisionControl = RevisionControl.YES)
public abstract class AbstractContentResource extends AbstractContentObject implements
    ContentResource {

  /** @see #getVersion() */
  private Version version;

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
   * @param name is the {@link #getName() name}.
   * @param parent is the {@link #getParent() parent}.
   */
  public AbstractContentResource(String name, AbstractContentResource parent) {

    super(name);
    setParent(parent);
  }

  /**
   * The constructor.
   * 
   * @param name is the {@link #getName() name}.
   * @param parent is the {@link #getParent() parent}.
   * @param id is the {@link #getId() ID}.
   */
  public AbstractContentResource(String name, AbstractContentResource parent, SmartId id) {

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
  @FieldAnnotation(id = 55)
  public Version getVersion() {

    return this.version;
  }

  /**
   * This method sets the {@link #getVersion() version}.
   * 
   * @param version the version to set.
   */
  public void setVersion(Version version) {

    if ((this.version != null) && (getRevision() > 0)) {
      // TODO: NLS + detail
      throw new IllegalArgumentException("Can NOT change version of final revision!");
    }
    this.version = version;
  }

  /**
   * {@inheritDoc}
   */
  @FieldAnnotation(id = 56)
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
   * According to {@link #getProxyTarget() proxying} and
   * {@link #getParent() inheritance} this instance may be "manipulated" to add
   * the according support. Therefore this method returns the original object
   * without manipulations. This is needed e.g. for the editor GUI.
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
