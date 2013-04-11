/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.link;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface LinkManager {

  // /**
  // * This method creates a new {@link Link}.
  // *
  // * @param source is the linking {@link DataEntity}.
  // * @param field is the {@link DataField} ...
  // * @param target is the linked {@link Link#getTarget() target entity}.
  // * @param classifier is the {@link Link#getClassifier() classifier}.
  // * @return the new {@link Link}.
  // */
  // <TARGET extends DataEntity> Link<TARGET> createLink(DataEntity source,
  // DataField<DataEntity, LinkList<TARGET>> field, TARGET target, String
  // classifier);

  /**
   * This method gets the inverse of a
   * {@link net.sf.mmm.data.api.link.Link#getClassifier() classifier}.
   * 
   * @param classifier is the
   *        {@link net.sf.mmm.data.api.link.Link#getClassifier() classifier}.
   * @return the inverse classifier. If the classifier is NOT registered, the
   *         given <code>classifier</code> is returned.
   */
  String getInverseClassifier(String classifier);

  /**
   * This method gets the inverse of a
   * {@link net.sf.mmm.data.api.link.Link#getClassifier() classifier}.
   * 
   * @param classifier is the
   *        {@link net.sf.mmm.data.api.link.Link#getClassifier() classifier}.
   * @return the inverse classifier or <code>null</code> if the classifier is
   *         unknown.
   */
  String getInverseClassifierStrict(String classifier);

}
