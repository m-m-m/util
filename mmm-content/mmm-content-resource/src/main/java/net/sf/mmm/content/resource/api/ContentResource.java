/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.api;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.value.api.RevisionHistory;
import net.sf.mmm.content.value.api.Version;

/**
 * This is the interface for a resource of the content repository. A resource is
 * a {@link net.sf.mmm.content.api.ContentObject content-object} that can be
 * versioned. <br>
 * There are specific resources that must always be present:
 * <ul>
 * <li>{@link ContentFolder}</li>
 * <li>{@link ContentFile}</li>
 * <li>{@link ContentDocument}</li>
 * </ul>
 * Additional resource-types may be customized and typically derive from
 * {@link ContentDocument}. Generic components of the project (including
 * plugins) need to access additional fields of a custom resource-type via
 * {@link #getValue(String)} and {@link #setValue(String, Object)}. Depending
 * on the implementation of the persistence these resource-types may NOT have a
 * specific java implementation or it is NOT used. Custom implementations are
 * free to bypass this API and directly work on the specific methods
 * (getters/setters) of custom resources if allowed by their policy (see
 * javadoc, etc.). <br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract interface ContentResource extends ContentObject {

  /** the name of the {@link #getContentClass() class} reflecting this type. */
  String CLASS_NAME = "Resource";

  /** the id of the {@link #getContentClass() class} reflecting this type. */
  short CLASS_ID = 20;

  /**
   * the separator used for the {@link #getPath() path}
   */
  String PATH_SEPARATOR = "/";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * parentFolder for generic access via {@link #getValue(String)}.
   */
  String FIELD_NAME_PARENT = "parent";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * path for generic access via {@link #getValue(String)}.
   */
  String FIELD_NAME_PATH = "path";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getRevisionHistory() revision-history} for generic access via
   * {@link #getValue(String)}.
   */
  String FIELD_NAME_REVISION_HISTORY = "revisionHistory";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getVersion() version} for generic access via
   * {@link #getValue(String)}.
   */
  String FIELD_NAME_VERSION = "version";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getRevision() revision} for generic access via
   * {@link #getValue(String)}.
   */
  String FIELD_NAME_REVISION = "revision";

  /**
   * This method gets the parent folder of this resource.
   * 
   * @return the parent or <code>null</code> if this is the root-folder.
   */
  ContentFolder getParentFolder();

  /**
   * This method gets the path to this content-object in the repository.<br>
   * The path already contains the {@link #getName() name} of this resource.
   * 
   * @return the path of the resource.
   */
  String getPath();

  /**
   * This method gets the version-information of this resource.
   * 
   * @see #getRevision()
   * 
   * @return the version-information.
   */
  Version getVersion();

  /**
   * This method gets the sequential revision number of this resource.<br>
   * 
   * @return the revision number of this resource where <code>0</code> means
   *         the latest revision.
   */
  int getRevision();

  /**
   * This method gets the history of this specific resource revision (version).
   * 
   * @return the history of this resource revision.
   */
  RevisionHistory getRevisionHistory();

}
