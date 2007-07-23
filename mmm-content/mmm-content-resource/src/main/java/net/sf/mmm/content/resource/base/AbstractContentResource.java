/* $Id: $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.base;

import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.resource.api.ContentFolder;
import net.sf.mmm.content.resource.api.ContentResource;
import net.sf.mmm.content.value.api.RevisionHistory;
import net.sf.mmm.content.value.api.Version;
import net.sf.mmm.content.value.base.SmartId;

/**
 * This is the implementation of the abstract entity {@link ContentResource}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentResource extends AbstractContentObject implements
    ContentResource {

  /** @see #getParentFolder() */
  private ContentFolder parentFolder;

  /** @see #getContentClass() */
  private ContentClass contentClass;

  /** @see #getVersion() */
  private Version version;

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
   * @param contentClass is the {@link #getContentClass() content-class}.
   */
  public AbstractContentResource(SmartId id, ContentClass contentClass) {

    super();
    setId(id);
    setContentClass(contentClass);
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
  public ContentFolder getParentFolder() {

    return this.parentFolder;
  }

  /**
   * This method sets the {@link #getParentFolder() parent folder}.<br>
   * It is an internal method and therefore protected. To move the resource
   * please use the repository API.
   * 
   * @param parent the parent to set.
   */
  protected void setParentFolder(ContentFolder parent) {

    this.parentFolder = parent;
  }

  /**
   * {@inheritDoc}
   */
  public String getPath() {

    if (getParentFolder() == null) {
      // root folder
      return PATH_SEPARATOR;
    } else {
      String parentPath = getParentFolder().getPath();
      String name = getName();
      StringBuffer path = new StringBuffer(parentPath.length() + name.length() + 1);
      path.append(parentPath);
      // only add separator if parent is NOT the root folder
      if (parentPath.length() > 1) {
        path.append(PATH_SEPARATOR);
      }
      path.append(name);
      return path.toString();
    }
  }

  /**
   * {@inheritDoc}
   */
  public RevisionHistory getRevisionHistory() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public int getRevision() {

    return getId().getRevision();
  }

  /**
   * {@inheritDoc}
   */
  public Version getVersion() {

    return this.version;
  }

  /**
   * @param version the version to set
   */
  protected void setVersion(Version version) {

    this.version = version;
  }

  /**
   * {@inheritDoc}
   */
  public ContentClass getContentClass() {

    return this.contentClass;
  }

  /**
   * This method sets the {@link #getContentClass() content-class}.
   * 
   * @param contentClass the contentClass to set
   */
  protected void setContentClass(ContentClass contentClass) {

    this.contentClass = contentClass;
  }

}
