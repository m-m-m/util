/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.entity.resource;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import net.sf.mmm.data.api.entity.resource.DataEntityResource;
import net.sf.mmm.data.api.entity.resource.DataEntityResourceView;
import net.sf.mmm.data.api.entity.resource.DataFolder;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.impl.entity.AbstractDataEntity;

/**
 * This is the implementation of the abstract entity
 * {@link DataEntityResourceView}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Entity
@DataClassAnnotation(id = DataEntityResourceView.CLASS_ID, title = DataEntityResourceView.CLASS_TITLE)
public abstract class AbstractDataEntityResource extends AbstractDataEntity implements
    DataEntityResource {

  /** UID for serialization. */
  private static final long serialVersionUID = 1983465184282662205L;

  /** @see #getParent() */
  private DataFolderImpl parent;

  /**
   * The constructor.
   */
  public AbstractDataEntityResource() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @ManyToOne
  @JoinColumn(name = "PARENT_ID", nullable = true)
  public DataFolderImpl getParent() {

    return this.parent;
  }

  /**
   * @param parent the parent to set
   */
  public void setParent(DataFolder parent) {

    this.parent = (DataFolderImpl) parent;
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public String getPath() {

    StringBuilder buffer = new StringBuilder();
    getPath(buffer);
    return buffer.toString();
  }

  /**
   * @see #getPath()
   * 
   * @param buffer is the {@link StringBuilder} where to append the path to.
   */
  @Transient
  protected void getPath(StringBuilder buffer) {

    if (this.parent != null) {
      this.parent.getPath(buffer);
    }
    if (buffer.length() > 1) {
      buffer.append(PATH_SEPARATOR);
    }
    buffer.append(getTitle());
  }

}
