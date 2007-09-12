/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.api;

import java.util.Collection;

import net.sf.mmm.content.api.ContentObject;

/**
 * This is the interface for a resource of the content repository. A resource is
 * a {@link net.sf.mmm.content.api.ContentObject content-object} that can be
 * versioned. <br>
 * There following resources that must always be present:
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
  public ContentResource getParent();

  /**
   * {@inheritDoc}
   */
  public Collection<? extends ContentResource> getChildren();

}
