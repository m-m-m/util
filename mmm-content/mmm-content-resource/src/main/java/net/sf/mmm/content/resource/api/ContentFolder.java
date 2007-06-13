/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.api;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.value.api.LinkList;

/**
 * This is the interface for a resource that can contain other content-objects.
 * Like a folder in the local filesystem, this is a folder in the repository.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentFolder extends ContentResource {

  /**
   * the name of the class reflecting {@link ContentFolder}.
   */
  String CLASS_NAME = "Folder";

  /**
   * The {@link ContentObject#getName() name} of the root-
   * {@link ContentFolder folder}.
   */
  String NAME_ROOT = "";

  /** The path of the root-{@link ContentFolder folder} */
  String PATH_ROOT = ContentResource.PATH_SEPARATOR;

  /**
   * The {@link ContentObject#getName() name} of the resources-{@link ContentFolder folder}.
   * All {@link ContentResource content-resources} are located below this
   * folder.
   */
  String NAME_RESOURCES = "resources";

  /**
   * The {@link #getPath() path} of the resources-{@link ContentFolder folder}.
   * All {@link ContentResource content-resources} are located below this
   * folder.
   */
  String PATH_RESOURCES = PATH_ROOT + NAME_RESOURCES;

  /**
   * The {@link ContentObject#getName() name} of the id-{@link ContentFolder folder}.
   * 
   * @see #PATH_IDS
   */
  String NAME_IDS = "ids";

  /**
   * The {@link #getPath() path} of the id-{@link ContentFolder folder}. All
   * {@link ContentResource resources} are mirrored in this folder with the
   * {@link net.sf.mmm.content.value.api.Id#toString() name} of their
   * {@link ContentObject#getId() ID}. No {@link ContentResource resource} will
   * return a {@link #getPath() path} below this folder. This folder will
   * (usually) NOT be browseable (have an empty
   * {@link #getChildren() child-list}).
   */
  String PATH_IDS = PATH_ROOT + NAME_IDS;

  /**
   * This method gets the link-list containing all direct chilren of this
   * folder. The direct children are the content-objects that have this folder
   * as {@link ContentResource#getParent() parent-folder}.
   * 
   * @return the child resources.
   */
  LinkList getChildren();

}
