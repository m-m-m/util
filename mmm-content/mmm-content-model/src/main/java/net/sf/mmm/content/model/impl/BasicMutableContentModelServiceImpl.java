/* $Id$ */
package net.sf.mmm.content.model.impl;

import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.ContentModelEvent;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.api.ContentReflectionObject;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.model.api.MutableContentModelService;
import net.sf.mmm.content.model.base.AbstractContentModelService;
import net.sf.mmm.content.value.impl.IdImpl;
import net.sf.mmm.util.event.ChangeEvent;

/**
 * This is the basic implementation of the {@link MutableContentModelService}
 * interface. It does NOT persist the model.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class BasicMutableContentModelServiceImpl extends AbstractContentModelService implements
    MutableContentModelService {

  /**
   * The constructor.
   * 
   * @throws ContentModelException
   *         if the initialization failed.
   */
  public BasicMutableContentModelServiceImpl() throws ContentModelException {

    super(ContentClassImpl.CLASS_ROOT);
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentModelWriteAccess#createClass(net.sf.mmm.content.model.api.ContentClass,
   *      java.lang.String, net.sf.mmm.content.model.api.ClassModifiers)
   */
  public ContentClass createClass(ContentClass superClass, String name, ClassModifiers modifiers)
      throws ContentModelException {

    if ((!superClass.getModifiers().isExtendable()) && (!modifiers.isSystem())) {
      throw new ContentModelException("Can not extend " + superClass + "!");
    }

    IdImpl id = null;
    ContentClass newClass = new ContentClassImpl(id, name, superClass, modifiers);
    // persist here
    ((ContentClassImpl) superClass).addSubClass(newClass);
    addClass(newClass);
    ContentModelEvent event = new ContentModelEvent(newClass, ChangeEvent.Type.ADD);
    fireEvent(event);
    return newClass;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentModelWriteAccess#createField(net.sf.mmm.content.model.api.ContentClass,
   *      java.lang.String, java.lang.Class,
   *      net.sf.mmm.content.model.api.FieldModifiers)
   */
  public ContentField createField(ContentClass declaringClass, String name, Class type,
      FieldModifiers modifiers) throws ContentModelException {

    IdImpl id = null;
    ContentField newField = new ContentFieldImpl(id, name, declaringClass, type, modifiers);
    // persist here
    ((ContentClassImpl) declaringClass).addField(newField);
    fireEvent(new ContentModelEvent(newField, ChangeEvent.Type.ADD));
    return newField;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentModelWriteAccess#setDeleted(net.sf.mmm.content.model.api.ContentReflectionObject,
   *      boolean)
   */
  public void setDeleted(ContentReflectionObject classOrField, boolean newDeletedFlag)
      throws ContentModelException {

    ((AbstractContentObject) classOrField).setDeleted(newDeletedFlag);
    // persist here...
    
    // Update or Delete?
    if (classOrField.isClass()) {
      fireEvent(new ContentModelEvent((ContentClass) classOrField, ChangeEvent.Type.UPDATE));
    } else {
      fireEvent(new ContentModelEvent((ContentField) classOrField, ChangeEvent.Type.UPDATE));
    }
  }

}
