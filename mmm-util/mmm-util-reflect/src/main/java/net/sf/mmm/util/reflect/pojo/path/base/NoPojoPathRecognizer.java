/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.base;

import net.sf.mmm.util.reflect.pojo.path.api.PojoPathRecognizer;

/**
 * This is a dummy implementation of the {@link PojoPathRecognizer} that
 * {@link #recognize(Object) does} nothing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class NoPojoPathRecognizer implements PojoPathRecognizer {

  /** The singleton instance of this class. */
  public static final NoPojoPathRecognizer INSTANCE = new NoPojoPathRecognizer();

  /**
   * The constructor.
   */
  public NoPojoPathRecognizer() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public void recognize(Object pojo) {

  }

}
