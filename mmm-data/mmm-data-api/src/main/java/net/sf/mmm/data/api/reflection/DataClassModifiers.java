/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

/**
 * This is the interface for the modifiers of a
 * {@link net.sf.mmm.data.api.reflection.DataClass content-class}.
 * 
 * @see net.sf.mmm.data.api.reflection.DataClass#getModifiers()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataClassModifiers extends DataModifiers {

  /**
   * the name of the root tag.
   */
  String XML_TAG_ROOT = "ClassModifiers";

  /**
   * the attribute for the {@link #isAbstract() abstract-flag}.
   */
  String XML_ATR_ABSTRACT = "abstract";

  /**
   * the attribute for the {@link #isExtendable() extendable-flag}.
   */
  String XML_ATR_EXTENDABLE = "extendable";

  /**
   * This method determines if the class is abstract and no
   * {@link net.sf.mmm.data.api.DataObject instance} can be created directly
   * from this class.
   * 
   * @return <code>true</code> if the class is abstract.
   */
  boolean isAbstract();

  /**
   * This method determines if the class can be
   * {@link net.sf.mmm.data.api.reflection.access.DataReflectionWriteAccess#createDataClass(DataClass, String, DataClassModifiers)
   * extended} by the user.<br>
   * A {@link DataModifiers#isFinal() final} class is NOT extendable. In advance
   * a {@link DataModifiers#isSystem() system} class that is NOT
   * {@link DataModifiers#isFinal() final} can have
   * {@link DataClass#getSubClasses() sub-classes} but may NOT be extendable.
   * These {@link DataClass#getSubClasses() sub-classes} will also be
   * {@link DataModifiers#isSystem() system} classes. In that case this method
   * returns <code>false</code> even though {@link #isFinal()} will return
   * <code>false</code>.
   * 
   * @return <code>true</code> if the class can be extended by the user,
   *         <code>false</code> otherwise.
   */
  boolean isExtendable();

}
