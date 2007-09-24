/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.api;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.value.api.Version;

/**
 * This is the interface for a resource of the content repository. A resource is
 * a {@link net.sf.mmm.content.api.ContentObject content-object} that is
 * typically
 * {@link net.sf.mmm.content.model.api.ContentClass#isRevisionControlled() revision-controlled}.
 * <br>
 * The following resources that must always be present:
 * <ul>
 * <li>{@link ContentFolder}</li>
 * <li>{@link ContentFile}</li>
 * </ul>
 * Additional resource-types may be customized and derive from
 * {@link ContentResource}. Generic components of the project (including
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
  String CLASS_NAME = "ContentResource";

  /** the id of the {@link #getContentClass() class} reflecting this type. */
  short CLASS_ID = 20;

  /**
   * {@inheritDoc}
   */
  ContentResource getParent();

  /**
   * This method gets the proxy-target of this object. If the proxy-target is
   * NOT <code>null</code>, this object is a proxy on another instance of the
   * same {@link #getContentClass() type}. Then all fields that are NOT set
   * (that are <code>null</code>) are "inherited" from the
   * {@link #getProxyTarget() proxy-target}. This rule applies before fields
   * are inherited from the {@link #getParent() parent} and does NOT apply for
   * {@link #getId() ID} and {@link #getName() name}. A proxy with no field set
   * acts like a link in a unix filesystem.<br>
   * <b>INFORMATION:</b><br>
   * The returned object needs to have the same {@link #getContentClass() type}
   * as this instance.
   * 
   * @return the proxy-target or <code>null</code> if this is a regular
   *         content-object.
   */
  ContentResource getProxyTarget();

  /**
   * This method gets the version-information of this object (this revision).
   * 
   * @see #getRevision()
   * 
   * @return the version-information. May be <code>null</code> if this object
   *         is the latest revision.
   */
  Version getVersion();

}
