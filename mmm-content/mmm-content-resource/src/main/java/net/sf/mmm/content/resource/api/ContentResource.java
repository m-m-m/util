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
 * There are two specific resources that must always be present:
 * {@link ContentFolder folder}) and {@link ContentFile file}. Any other
 * resource-class may be customized. In this context folders are often called
 * <i>collection</i> and files (and other resources) are called <i>document</i>.
 * <br>
 * A resource has various fields that can be read (via
 * {@link ContentResource#getFieldValue(String)}) and written (via
 * {@link ContentResource#setFieldValue(String, Object)}). Generic components
 * of the project (including plugins) need to use this official API to access
 * resources. Like java objects a resource can be reflected via
 * {@link net.sf.mmm.content.api.ContentObject#getContentClass()}. Custom
 * implementations are free to bypass this API and directly work on the
 * implementation if allowed by their policy (see javadoc, etc.). <br>
 * One implementation may wrap java bean-like classes as ContentClass and direct
 * object instances of those classes as {@link ContentResource} objects. On the
 * other hand another implementation may use dynamic containers (a la
 * {@link java.util.Map}) as {@link ContentResource}and re-implement the OO
 * world.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentResource extends ContentObject {

  /** the name of the {@link #getContentClass() class} reflecting this type. */
  String CLASS_NAME = "Resource";

  /**
   * the separator used for the {@link #getPath() path}
   */
  String PATH_SEPARATOR = "/";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * parentFolder for generic access via {@link #getFieldValue(String)}.
   */
  String FIELD_NAME_PARENT = "parent";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * path for generic access via {@link #getFieldValue(String)}.
   */
  String FIELD_NAME_PATH = "path";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getMetaData() metadata} for generic access via
   * {@link #getFieldValue(String)}.
   */
  String FIELD_NAME_METADATA = "metadata";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getRevisionHistory() revision-history} for generic access via
   * {@link #getFieldValue(String)}.
   */
  String FIELD_NAME_REVISION_HISTORY = "revisionHistory";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getVersion() version} for generic access via
   * {@link #getFieldValue(String)}.
   */
  String FIELD_NAME_VERSION = "version";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getRevision() revision} for generic access via
   * {@link #getFieldValue(String)}.
   */
  String FIELD_NAME_REVISION = "revision";

  /**
   * This method gets the parent object of this content-object.
   * 
   * @return the parent or <code>null</code> if this is the root-folder.
   */
  ContentFolder getParent();

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
