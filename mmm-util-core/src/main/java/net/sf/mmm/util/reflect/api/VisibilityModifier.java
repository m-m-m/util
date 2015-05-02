/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

/**
 * This enum contains the possible modifiers for the visibility of a java element (type, field, method or
 * constructor).
 * 
 * @see java.lang.reflect.Modifier
 * @see javax.lang.model.element.Modifier
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public enum VisibilityModifier {

  /**
   * The modifier for {@link java.lang.reflect.Modifier#isPrivate(int) private} visibility.
   * 
   * @see java.lang.reflect.Modifier#PRIVATE
   */
  PRIVATE(2),

  /**
   * The modifier for "friendly" visibility. This is the visibility of elements, that have no explicit
   * visibility modifier (are NOT public, protected or private).
   */
  FRIENDLY(0),

  /**
   * The modifier for {@link java.lang.reflect.Modifier#isProtected(int) protected} visibility.
   * 
   * @see java.lang.reflect.Modifier#PROTECTED
   */
  PROTECTED(4),

  /**
   * The modifier for {@link java.lang.reflect.Modifier#isPublic(int) public} visibility.
   * 
   * @see java.lang.reflect.Modifier#PUBLIC
   */
  PUBLIC(1);

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
   * This method gets the <code>{@link java.lang.reflect.Modifier bit-mask}</code> of this
   * visibility-modifier.
   * 
   * @return the bit-mask.
   */
  public int getBits() {

    return this.bits;
  }

  /**
   * This method gets a non-negative integer value that follows the strict order of the visibility. The
   * following equation applies:
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
   * <code>{@link java.lang.reflect.Modifier modifiers}</code>.
   * 
   * @see java.lang.Class#getModifiers()
   * @see java.lang.reflect.Method#getModifiers()
   * @see java.lang.reflect.Field#getModifiers()
   * 
   * @param modifiers are the modifiers of a java element.
   * @return the according visibility-modifier.
   */
  public static VisibilityModifier valueOf(int modifiers) {

    if ((modifiers & PUBLIC.bits) != 0) {
      return PUBLIC;
    } else if ((modifiers & PRIVATE.bits) != 0) {
      return PRIVATE;
    } else if ((modifiers & PROTECTED.bits) != 0) {
      return PROTECTED;
    } else {
      return FRIENDLY;
    }
  }

}
