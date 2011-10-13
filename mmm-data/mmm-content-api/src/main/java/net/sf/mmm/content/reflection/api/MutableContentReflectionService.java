/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.api;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.reflection.api.access.ContentReflectionWriteAccess;
import net.sf.mmm.util.component.base.ComponentSpecification;
import net.sf.mmm.util.event.api.EventListener;
import net.sf.mmm.util.event.api.EventSource;

/**
 * This interface extends the {@link ContentReflectionService} interface with
 * methods to edit the content-model (reflection).<br>
 * <b>ATTENTION:</b><br>
 * Please note that an {@link #isEditable() editable} content-model requires
 * that the persistent-stores of the content also support this feature. So take
 * care when you configure the components of your system.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface MutableContentReflectionService extends ContentReflectionService,
    ContentReflectionWriteAccess {

  /**
   * This method determines if this service provides a content-model that is
   * editable at runtime.
   * 
   * @return <code>true</code> if the model is editable.
   */
  boolean isEditable();

  /**
   * This method gets the event registrar where listeners can be registered so
   * they receive events about changes of the content model. <br>
   * 
   * @see ContentReflectionWriteAccess
   * 
   *      Instead of extending the {@link EventSource} interface an instance of
   *      the interface is returned by this method. This gives more flexibility
   *      e.g. to avoid multi-inheritance problems. An implementation of this
   *      service can still directly implement the {@link EventSource} interface
   *      and return <code>this</code> by the current method.
   * 
   * @return the event registrar.
   */
  EventSource<ContentReflectionEvent<ContentObject>, EventListener<ContentReflectionEvent<ContentObject>>> getEventRegistrar();

}
