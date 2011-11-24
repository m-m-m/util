/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity;


/**
 * This is the interface for a {@link DataEntity} that has a
 * {@link #getClassifier() classifier}. Such classifier allows to distinguish
 * multiple instances of an entity in a multiple relation. In other words if a
 * {@link DataEntity} links to a list of {@link DataEntityClassified} objects,
 * then these linked objects can be distinguished by the
 * {@link #getClassifier() classifier}.<br/>
 * As an example a contact can have multiple addresses. If an address is a
 * {@link DataEntityClassified} these addresses can have
 * {@link #getClassifier() classifiers} such as "private", "business", etc.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataEntityClassified extends DataEntity {

  /**
   * This method gets the classifier of this entity. Typically the classifiers
   * are predefined by a {@link net.sf.mmm.data.api.DataSelectionList}.
   * 
   * @return the classifier of this entity.
   */
  String getClassifier();

}
