/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.entity.api;

import net.sf.mmm.content.api.ContentClassAnnotation;
import net.sf.mmm.content.api.ContentObject;

/**
 * This is the interface for a {@link ContentObject} that is a regular
 * persistent entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ContentClassAnnotation(id = ContentEntity.CLASS_ID)
public abstract interface ContentEntity extends ContentObject {

  /**
   * The {@link net.sf.mmm.content.datatype.api.ContentId#getClassId() class-ID}
   * of the {@link net.sf.mmm.content.reflection.api.ContentClass} reflecting
   * this type.
   */
  int CLASS_ID = 10;

  /**
   * This method gets the proxy-source of this object. If the
   * {@link #getProxyTarget() proxy-target} is NOT <code>null</code>, this
   * object is a proxy on another instance of the same
   * {@link net.sf.mmm.content.reflection.api.ContentClass type}. This method
   * returns the unwrapped instance where the proxy feature is switched off.
   * 
   * @return the proxy-source of this entity or the instance itself (
   *         <code>this</code>) if this is already the unwrapped instance.
   */
  ContentEntity getProxySource();

  /**
   * This method gets the proxy-target of this object. If the proxy-target is
   * NOT <code>null</code>, this object is a proxy on another instance of the
   * same {@link net.sf.mmm.content.reflection.api.ContentClass type}. Then all
   * unset {@link net.sf.mmm.content.reflection.api.ContentField fields} are
   * "inherited" from the {@link #getProxyTarget() proxy-target}. This rule does
   * NOT apply for {@link #getId() ID}, {@link #getModificationCounter()
   * modification counter}, {@link #getModificationTimestamp() modification
   * timestamp}, {@link #getRevision() revision}, {@link #getContentClassId()
   * content class id}, and {@link #getProxyTarget() proxy target}. A proxy that
   * has no field set acts like a link in a Unix filesystem.<br>
   * <b>INFORMATION:</b><br>
   * The returned object needs to have the same
   * {@link net.sf.mmm.content.reflection.api.ContentClass type} as this
   * instance.
   * 
   * @return the proxy-target or <code>null</code> if this is a regular
   *         content-object.
   */
  ContentEntity getProxyTarget();

}
