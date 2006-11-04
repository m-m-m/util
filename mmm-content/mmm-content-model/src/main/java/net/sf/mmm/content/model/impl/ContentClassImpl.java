/* $Id$ */
package net.sf.mmm.content.model.impl;

import net.sf.mmm.content.model.api.ClassModifiersIF;
import net.sf.mmm.content.model.api.ContentClassIF;
import net.sf.mmm.content.model.base.AbstractContentClass;
import net.sf.mmm.content.model.base.ClassModifiers;
import net.sf.mmm.content.value.api.IdIF;
import net.sf.mmm.content.value.impl.IdImpl;

/**
 * This is the implementation of the {@link ContentClassIF} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentClassImpl extends AbstractContentClass {

  /** UID for serialization */
  private static final long serialVersionUID = -5047411761519800723L;

  /** @see #NAME_ROOT */
  public static final ContentClassImpl CLASS_ROOT = new ContentClassImpl(NAME_ROOT, null,
      ClassModifiers.SYSTEM_ABSTRACT_UNEXTENDABLE, IdImpl.ID_CLASS_ROOT);

  /** @see #NAME_REFLECTION */
  public static final ContentClassImpl CLASS_REFLECTION = new ContentClassImpl(NAME_REFLECTION,
      CLASS_ROOT, ClassModifiers.SYSTEM_ABSTRACT_UNEXTENDABLE, IdImpl.ID_CLASS_REFELCTION);

  /** @see #NAME_CLASS */
  public static final ContentClassImpl CLASS_CLASS = new ContentClassImpl(NAME_CLASS,
      CLASS_REFLECTION, ClassModifiers.SYSTEM_FINAL, IdImpl.ID_CLASS_CLASS);

  /** @see #NAME_FIELD */
  public static final ContentClassImpl CLASS_FIELD = new ContentClassImpl(NAME_FIELD,
      CLASS_REFLECTION, ClassModifiers.SYSTEM_FINAL, IdImpl.ID_CLASS_FIELD);

  /** @see #getId() */
  private final IdImpl id;

  /**
   * The constructor.
   * 
   * @param className
   * @param parentClass
   * @param classModifiers
   * @param classId
   */
  public ContentClassImpl(String className, ContentClassIF parentClass,
      ClassModifiersIF classModifiers, IdImpl classId) {

    super(className, parentClass, classModifiers);
    this.id = classId;
  }

  /**
   * @see net.sf.mmm.content.api.ContentObjectIF#getContentClass()
   */
  public ContentClassIF getContentClass() {

    return CLASS_CLASS;
  }

  /**
   * @see net.sf.mmm.content.api.ContentObjectIF#getId()
   */
  public IdIF getId() {

    return this.id;
  }

}
