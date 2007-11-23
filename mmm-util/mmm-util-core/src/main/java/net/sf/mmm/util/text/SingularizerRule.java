/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class SingularizerRule {

  /**
   * @see Singularizer#toSingular(String)
   * 
   * @param plural
   * @param pluralLowerCase
   * @return
   */
  public abstract String toSingular(String plural, String pluralLowerCase);

}
