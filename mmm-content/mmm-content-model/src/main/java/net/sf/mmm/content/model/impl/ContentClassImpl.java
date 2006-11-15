/* $Id$ */
package net.sf.mmm.content.model.impl;

import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.base.AbstractContentClass;
import net.sf.mmm.content.model.base.ClassModifiersImpl;
import net.sf.mmm.content.value.api.Id;
import net.sf.mmm.content.value.impl.IdImpl;

/**
 * This is the implementation of the {@link ContentClass} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentClassImpl extends AbstractContentClass {

  /** UID for serialization */
  private static final long serialVersionUID = -5047411761519800723L;

  /** @see #NAME_ROOT */
  public static final ContentClassImpl CLASS_ROOT = new ContentClassImpl(IdImpl.ID_CLASS_ROOT,
      NAME_ROOT, null, ClassModifiersImpl.SYSTEM_ABSTRACT_UNEXTENDABLE);

  /** @see #NAME_REFLECTION */
  public static final ContentClassImpl CLASS_REFLECTION = new ContentClassImpl(
      IdImpl.ID_CLASS_REFELCTION, NAME_REFLECTION, CLASS_ROOT,
      ClassModifiersImpl.SYSTEM_ABSTRACT_UNEXTENDABLE);

  /** @see #NAME_CLASS */
  public static final ContentClassImpl CLASS_CLASS = new ContentClassImpl(IdImpl.ID_CLASS_CLASS,
      NAME_CLASS, CLASS_REFLECTION, ClassModifiersImpl.SYSTEM_FINAL);

  /** @see #NAME_FIELD */
  public static final ContentClassImpl CLASS_FIELD = new ContentClassImpl(IdImpl.ID_CLASS_FIELD,
      NAME_FIELD, CLASS_REFLECTION, ClassModifiersImpl.SYSTEM_FINAL);

  static {
    try {
      CLASS_ROOT.addSubClass(CLASS_REFLECTION);
      CLASS_REFLECTION.addSubClass(CLASS_CLASS);
      CLASS_REFLECTION.addSubClass(CLASS_FIELD);
    } catch (ContentModelException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  /**
   * The constructor.
   * 
   * @param className
   * @param parentClass
   * @param classModifiers
   * @param classId
   */
  public ContentClassImpl(Id classId, String className, ContentClass parentClass,
      ClassModifiers classModifiers) {

    super(classId, className, parentClass, classModifiers);
  }

  /**
   * @see net.sf.mmm.content.api.ContentObject#getContentClass()
   */
  public ContentClass getContentClass() {

    return CLASS_CLASS;
  }

}
