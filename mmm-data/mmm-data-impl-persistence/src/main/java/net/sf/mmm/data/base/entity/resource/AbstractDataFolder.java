/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.entity.resource;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import net.sf.mmm.data.api.entity.resource.DataEntityResource;
import net.sf.mmm.data.api.entity.resource.DataFolder;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the abstract base implementation of {@link DataFolder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@MappedSuperclass
public abstract class AbstractDataFolder extends AbstractDataEntityResource implements DataFolder {

  /** UID for serialization. */
  private static final long serialVersionUID = -9049737538380209022L;

  /** @see #getChildren() */
  private List<AbstractDataEntityResource> children;

  /**
   * The constructor.
   * 
   */
  public AbstractDataFolder() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  public long getDataClassId() {

    return DataFolder.CLASS_ID;
  }

  /**
   * {@inheritDoc}
   */
  public DataEntityResource getChild(String title) {

    for (DataEntityResource child : getChildren()) {
      if (child.getTitle().equals(title)) {
        return child;
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @OneToMany(mappedBy = "parent")
  public List<AbstractDataEntityResource> getChildren() {

    return this.children;
  }

  /**
   * @param children is the children to set
   */
  protected void setChildren(List<AbstractDataEntityResource> children) {

    this.children = children;
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public boolean isAbstract() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public boolean isCacheable() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDescendant(DataEntityResource node) {

    AbstractDataFolder parent = getParent();
    if (parent == null) {
      return false;
    } else if (this == null) {
      return true;
    } else {
      return parent.isDescendant(node);
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAncestor(DataFolder node) {

    NlsNullPointerException.checkNotNull(DataEntityResource.class, node);
    return node.isDescendant(this);
  }

}
