/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

/**
 * This is an {@link ObjectNotFoundException} that represents a user failure (is NOT {@link #isTechnical() technical}).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class ObjectNotFoundUserException extends ObjectNotFoundException {

  private static final long serialVersionUID = 336435569781263073L;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected ObjectNotFoundUserException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param object is a description (e.g. the classname) of the object that was required but could NOT be found.
   */
  public ObjectNotFoundUserException(Object object) {

    super(object);
  }

  /**
   * The constructor.
   *
   * @param object is a description (e.g. the classname) of the object that was required but could NOT be found.
   * @param key is the key to the required object.
   */
  public ObjectNotFoundUserException(Object object, Object key) {

    super(object, key);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param object is a description (e.g. the classname) of the object that was required but could NOT be found.
   */
  public ObjectNotFoundUserException(Throwable nested, Object object) {

    super(nested, object);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param object is a description (e.g. the classname) of the object that was required but could NOT be found.
   * @param key is the key to the required object.
   */
  public ObjectNotFoundUserException(Throwable nested, Object object, Object key) {

    super(nested, object, key);
  }

  @Override
  public boolean isTechnical() {

    return false;
  }

}
