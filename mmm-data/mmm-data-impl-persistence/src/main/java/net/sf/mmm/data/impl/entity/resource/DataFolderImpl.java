/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.entity.resource;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import net.sf.mmm.data.api.entity.resource.DataEntityResource;
import net.sf.mmm.data.api.entity.resource.DataFolder;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the implementation of {@link DataFolder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Entity(name = DataFolder.CLASS_TITLE)
@DataClassAnnotation(id = DataFolder.CLASS_ID)
@DiscriminatorValue("" + DataFolder.CLASS_ID)
public class DataFolderImpl extends AbstractDataEntityResource implements DataFolder {

  /** UID for serialization. */
  private static final long serialVersionUID = -9049737538380209022L;

  /** @see #getChildren() */
  private List<DataEntityResource> children;

  /**
   * The constructor.
   */
  public DataFolderImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  protected long getStaticDataClassId() {

    return DataFolder.CLASS_ID;
  }

  /**
   * {@inheritDoc}
   */
  @Override
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
  @Override
  @OneToMany(mappedBy = "parent", targetEntity = AbstractDataEntityResource.class)
  public List<DataEntityResource> getChildren() {

    if (this.children == null) {
      this.children = new ArrayList<DataEntityResource>();
    }
    return this.children;
  }

  /**
   * @param children is the children to set
   */
  protected void setChildren(List<DataEntityResource> children) {

    this.children = children;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  public boolean isSelectable() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectable(boolean isAbstract) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDescendant(DataEntityResource node) {

    DataFolderImpl parent = getParent();
    if (parent == null) {
      return (this == node);
    } else {
      return parent.isDescendant(node);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAncestor(DataFolder node) {

    NlsNullPointerException.checkNotNull(DataEntityResource.class, node);
    return node.isDescendant(this);
  }

}
