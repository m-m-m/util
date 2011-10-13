/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.resource.api;

import java.util.List;

import net.sf.mmm.data.api.ContentFieldAnnotation;
import net.sf.mmm.data.api.ContentObject;

/**
 * This is the interface for a resource of the content repository. A resource is
 * a {@link net.sf.mmm.data.api.ContentObject content-object} that is
 * typically
 * {@link net.sf.mmm.data.reflection.api.ContentClass#isRevisionControlled()
 * revision-controlled}. <br>
 * The following resources that must always be present:
 * <ul>
 * <li>{@link ContentFolder}</li>
 * <li>{@link ContentFile}</li>
 * </ul>
 * Additional resource-types may be customized and derive from
 * {@link ContentResource}. Generic components of the project (including
 * plugins) need to access additional fields of a custom resource-type via the
 * content-model (see {@link net.sf.mmm.data.reflection.api.ContentField}).
 * Depending on the implementation of the persistence these resource-types may
 * NOT have a specific java implementation or it is NOT used. Custom
 * implementations are free to bypass this API and directly work on the specific
 * methods (getters/setters) of custom resources if allowed by their policy (see
 * javadoc, etc.). <br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface ContentResource extends ContentObject {

  /**
   * The name of the {@link net.sf.mmm.data.reflection.api.ContentClass}
   * reflecting this type.
   */
  String CLASS_NAME = "ContentResource";

  /**
   * The ID of the {@link net.sf.mmm.data.reflection.api.ContentClass}
   * reflecting this type.
   */
  short CLASS_ID = 20;

  /**
   * the separator used for the {@link #getPath() path}
   */
  String PATH_SEPARATOR = "/";

  /**
   * This method gets the parent of this object. This will typically be the
   * {@link #isFolder() folder} containing the object.
   * 
   * @return the parent or <code>null</code> if the {@link ContentObject} has no
   *         parent. The root
   *         {@link net.sf.mmm.data.resource.api.ContentFolder folder} is
   *         indicated by returning <code>null</code> as parent.
   */
  @ContentFieldAnnotation(id = 4)
  ContentResource getParent();

  /**
   * This method gets the path to this content-object in the repository.<br>
   * The path already contains the {@link #getTitle() name} of this resource.
   * 
   * @return the path of the resource.
   */
  @ContentFieldAnnotation(id = 3, isTransient = true, isFinal = true)
  String getPath();

  /**
   * This method gets the {@link List} containing all direct children of this
   * object. If this object is NOT a {@link #isFolder() folder} the result will
   * always be an {@link List#isEmpty() empty} {@link List}.<br>
   * Typically the direct children are the {@link ContentObject}s that have this
   * object as {@link #getParent() parent}.<br>
   * <b>ATTENTION:</b><br>
   * For some specific entities this method may return children that do NOT have
   * this object as {@link #getParent()}. E.g. a <code>SearchFolder</code> may
   * be a virtual folder that performs a dynamic search returning arbitrary
   * objects from this method that themselves reside in different, physical
   * folders.
   * 
   * @return the child resources.
   */
  @ContentFieldAnnotation(id = 5, inverseRelationFieldId = 4)
  List<? extends ContentObject> getChildren();

  // /**
  // * This method gets the version-information of this object (this revision).
  // *
  // * @see #getRevision()
  // *
  // * @return the version-information. May be <code>null</code> if this object
  // * is the latest revision.
  // */
  // Version getVersion();

}
