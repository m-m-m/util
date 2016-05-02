/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

/**
 * This is a simple, inefficient implementation of {@link net.sf.mmm.util.text.api.StringHasher} that is
 * compatible to {@link String#hashCode()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class SimpleStringHasher extends AbstractStringHasher {

  /**
   * The constructor.
   */
  public SimpleStringHasher() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public int getHashCode(char[] string, int start, int end) {

    int hash = 0;
    for (int i = start; i < end; i++) {
      hash = hash * 31 + string[i];
    }
    return hash;
  }

}
