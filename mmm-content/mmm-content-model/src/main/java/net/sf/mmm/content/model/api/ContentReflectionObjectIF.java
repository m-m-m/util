/* $Id$ */
package net.sf.mmm.content.model.api;

import net.sf.mmm.content.api.ContentObjectIF;

/**
 * This is the interface for an object reflecting the content model. It can be
 * eighter a class or a field.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentReflectionObjectIF extends ContentObjectIF {

  /**
   * This method gets the modifiers of this object. The modifiers are a set of
   * flags.
   * 
   * @see ContentClassIF#getModifiers()
   * @see ContentFieldIF#getModifiers()
   * 
   * @return the objects modifiers.
   */
  ModifiersIF getModifiers();

  /**
   * This method determines if this is a {@link ContentClassIF content-class} or
   * a {@link ContentFieldIF content-field}. It is allowed to cast this object
   * according to the result of this method.
   * 
   * @return <code>true</code> if this is a
   *         {@link ContentClassIF content-class}, <code>false</code> if this
   *         is a {@link ContentFieldIF content-field}.
   */
  boolean isClass();

  /**
   * The deleted-flag is inherited so {@link ContentObjectIF#isDeleted()} will
   * return <code>true</code> if a super-classes is marked as deleted. <br>
   * This method gets the deleted flag of this class or field. The method does
   * not inherit the flag from super-classes or extended fields.
   * 
   * @see ContentObjectIF#isDeleted()
   * 
   * @return the delted flag.
   */
  boolean isDeletedFlagSet();

}
