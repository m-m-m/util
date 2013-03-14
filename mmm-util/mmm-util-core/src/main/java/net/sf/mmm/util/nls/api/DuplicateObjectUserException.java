/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

/**
 * This is a {@link DuplicateObjectException} that represents a user failure (is NOT {@link #isTechnical()
 * technical}).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class DuplicateObjectUserException extends DuplicateObjectException {

  /** UID for serialization. */
  private static final long serialVersionUID = -2872400478804617675L;

  /**
   * The constructor.
   * 
   * @param object is the object that was rejected because it is a duplicate.
   */
  public DuplicateObjectUserException(Object object) {

    super(object);
  }

  /**
   * The constructor.
   * 
   * @param object is the object that was rejected because it is a duplicate.
   * @param key is the key the object could NOT be associated with because it already leads to another object.
   */
  public DuplicateObjectUserException(Object object, Object key) {

    super(object, key);
  }

  /**
   * The constructor.
   * 
   * @param object is the object that was rejected because it is a duplicate.
   * @param key is the key the object could NOT be associated with because it already leads to another object.
   * @param existing is the object that is already registered for the given <code>key</code>.
   */
  public DuplicateObjectUserException(Object object, Object key, Object existing) {

    super(object, key, existing);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTechnical() {

    return false;
  }

}
