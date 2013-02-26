/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.access.DataReflectionReadAccess;
import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for a service that provides access to the content-model (reflection). <br>
 * The content model is used to reflect the different types of {@link net.sf.mmm.data.api.DataObjectView
 * content-object}s (also called entities). It is used for generic access to the content (UI for rendering
 * editors, persistence for O/R mapping, etc.).
 * 
 * @see net.sf.mmm.data.api.reflection.DataClass
 * 
 *      An implementation of this service may make assumptions (about the implementations of the
 *      {@link net.sf.mmm.data.api.DataObjectView content-object}. This means that it is allowed to cast the
 *      implementations and bypass the API if necessary.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface DataReflectionService extends DataReflectionReadAccess {

  /** The location of the content-model. */
  String XML_MODEL_LOCATION = "net/sf/mmm/content/model/ContentModel.xml";

  /** the root tag-name of the content-model XML representation */
  String XML_TAG_ROOT = "content-model";

  /**
   * the tag-name of a {@link DataClass content-class} in the content-model XML representation
   */
  String XML_TAG_CLASS = "class";

  /**
   * the tag-name of a {@link DataClass content-class} in the content-model XML representation
   */
  String XML_ATR_NAME = DataObject.FIELD_NAME_TITLE;

  /**
   * the tag-name of a {@link DataField content-field} in the content-model XML representation
   */
  String XML_TAG_FIELD = "field";

}
