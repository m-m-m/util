/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import java.lang.reflect.Modifier;

/**
 * This enum contains the possible modifiers for the visibility of a java
 * element (type, field, method or constructor).
 * 
 * @see Modifier
 * @see javax.lang.model.element.Modifier
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum VisibilityModifier {

  /**
   * The modifier for {@link Modifier#isPrivate(int) private} visibility.
   * 
   * @see javax.lang.model.element.Modifier#PRIVATE
   */
  PRIVATE(Modifier.PRIVATE),

  /**
   * The modifier for "friendly" visibility. This is the visibility of elements,
   * that have no explicit visibility modifier (are NOT public, protected or
   * private).
   */
  FRIENDLY(0),

  /**
   * The modifier for {@link Modifier#isProtected(int) protected} visibility.
   * 
   * @see javax.lang.model.element.Modifier#PROTECTED
   */
  PROTECTED(Modifier.PROTECTED),

  /**
   * The modifier for {@link Modifier#isPublic(int) public} visibility.
   * 
   * @see javax.lang.model.element.Modifier#PUBLIC
   */
  PUBLIC(Modifier.PUBLIC);

  /** @see #getBits() */
  private final int bits;

  /**
   * The constructor.
   * 
   * @param bits are the {@link #bits}.
   */
  private VisibilityModifier(int bits) {

    this.bits = bits;
  }

  /**
   * This method gets the <code>{@link Modifier bit-mask}</code> of this
   * visibility-modifier.
   * 
   * @return the bit-mask.
   */
  public int getBits() {

    return this.bits;
  }

  /**
   * This method gets a non-negative integer value that follows the strict order
   * of the visibility. The following equation applies:
   * 
   * <pre>
   * {@link #PRIVATE}.{@link #getOrder()} &lt; {@link #FRIENDLY}.{@link #getOrder()}
   * &lt; {@link #PROTECTED}.{@link #getOrder()} &lt; {@link #PUBLIC}.{@link #getOrder()}
   * </pre>
   * 
   * @return the order number of the visibility.
   */
  public int getOrder() {

    return ordinal();
  }

  /**
   * This method gets the {@link VisibilityModifier} for the given
   * <code>{@link Modifier modifiers}</code>.
   * 
   * @see java.lang.Class#getModifiers()
   * @see java.lang.reflect.Method#getModifiers()
   * @see java.lang.reflect.Field#getModifiers()
   * 
   * @param modifiers are the modifiers of a java element.
   * @return the according visibility-modifier.
   */
  public static VisibilityModifier valueOf(int modifiers) {

    if (Modifier.isPublic(modifiers)) {
      return PUBLIC;
    } else if (Modifier.isPrivate(modifiers)) {
      return PRIVATE;
    } else if (Modifier.isProtected(modifiers)) {
      return PROTECTED;
    } else {
      return FRIENDLY;
    }
  }

}
