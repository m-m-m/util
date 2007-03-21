/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.util.event.EventListener;
import net.sf.mmm.util.event.EventSource;

/**
 * This is the interface for a service that provides access to the content
 * model. <br>
 * The content model is used to reflect the different types of
 * {@link net.sf.mmm.content.api.ContentObject content-object}s. It is used
 * for generic access to the content (UI for rendering editors, persistence for
 * O/R mapping, etc.).
 * 
 * @see net.sf.mmm.content.model.api.ContentClass
 * 
 * An implementation of this service may make assumptions (about the
 * implementations of the
 * {@link net.sf.mmm.content.api.ContentObject content-object}. This means
 * that it is allowed to cast the implementations and bypass the API if
 * necessary.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentModelService extends ContentModelReadAccess {

  /** the root tag-name of the content-model XML representation */
  String XML_TAG_ROOT = "model";

  /**
   * the tag-name of a {@link ContentClass content-class} in the content-model
   * XML representation
   */
  String XML_TAG_CLASS = ContentClass.CLASS_NAME;

  /**
   * the tag-name of a {@link ContentClass content-class} in the content-model
   * XML representation
   */
  String XML_ATR_CLASS_NAME = ContentObject.FIELD_NAME_NAME;

  /**
   * the tag-name of a {@link ContentField content-field} in the content-model
   * XML representation
   */
  String XML_TAG_FIELD = ContentField.CLASS_NAME;

  /**
   * This method gets the event registrar where listeners can be registered so
   * they receive events about changes of the content model. <br>
   * 
   * @see ContentModelWriteAccess
   * 
   * Instead of extending the {@link EventSource} interface an instance of the
   * interface is returned by this method. This gives more flexibility e.g. to
   * avoid multi-inheritance problems. An implementation of this service can
   * still directly implement the {@link EventSource} interface and return
   * <code>this</code> by the current method.
   * 
   * @return the event registrar.
   */
  EventSource<ContentModelEvent, EventListener<ContentModelEvent>> getEventRegistrar();
  
}
