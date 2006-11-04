/* $Id$ */
package net.sf.mmm.content.model.api;

import net.sf.mmm.util.event.EventListener;
import net.sf.mmm.util.event.EventSourceIF;

/**
 * This is the interface for a service that provides access to the content
 * model. <br>
 * The content model is used to reflect the different types of
 * {@link net.sf.mmm.content.api.ContentObjectIF content-object}s. It is used
 * for generic access to the content (UI for rendering editors, persistence for
 * O/R mapping, etc.).
 * 
 * @see net.sf.mmm.content.model.api.ContentClassIF
 * 
 * An implementation of this service may make assumptions (about the
 * implementations of the
 * {@link net.sf.mmm.content.api.ContentObjectIF content-object}. This means
 * that it is allowed to cast the implementations and bypass the API if
 * neccessary.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentModelServiceIF extends ContentModelReadAccessIF {

  /** the unique identifier of this service */
  String SERVICE_ID = ContentModelServiceIF.class.getName();

  /** the root tag-name of the content-model XML representation */
  String XML_TAG_ROOT = "model";

  /**
   * the tag-name of a {@link ContentClassIF content-class} in the content-model
   * XML representation
   */
  String XML_TAG_CLASS = ContentClassIF.NAME_CLASS;

  /**
   * the tag-name of a {@link ContentClassIF content-class} in the content-model
   * XML representation
   */
  String XML_ATR_CLASS_NAME = ContentFieldIF.NAME_NAME;

  /**
   * the tag-name of a {@link ContentFieldIF content-field} in the content-model
   * XML representation
   */
  String XML_TAG_FIELD = ContentClassIF.NAME_FIELD;

  /**
   * This method gets the event registrar where listeners can be registered so
   * they receive events about changes of the content model. <br>
   * 
   * @see ContentModelWriteAccessIF
   * 
   * Instead of extending the {@link EventSourceIF} interface an instance of the
   * interface is returned by this method. This gives more flexibility e.g. to
   * avoid multi-inheritance problems. An implementation of this service can
   * still directly implement the {@link EventSourceIF} interface and return
   * <code>this</code> by the current method.
   * 
   * @return the event registrar.
   */
  EventSourceIF<ContentModelEvent, EventListener<ContentModelEvent>> getEventRegistrar();

}
