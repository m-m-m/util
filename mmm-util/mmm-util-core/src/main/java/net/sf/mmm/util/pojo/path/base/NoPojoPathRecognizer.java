/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.base;

import net.sf.mmm.util.pojo.path.api.PojoPath;
import net.sf.mmm.util.pojo.path.api.PojoPathRecognizer;

/**
 * This is a dummy implementation of the {@link PojoPathRecognizer} that {@link #recognize(Object, PojoPath)
 * does} nothing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
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
  public void recognize(Object pojo, PojoPath currentPath) {

    // nothing to do
  }

}
