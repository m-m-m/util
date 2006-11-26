/* $Id$ */
package net.sf.mmm.content.model.impl;

import javax.annotation.Resource;

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
import net.sf.mmm.content.persistence.api.IdService;
import net.sf.mmm.content.value.api.Id;
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

  /** @see #setIdService(IdService) */
  private IdService idService;

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
   * This method sets the idService.
   * 
   * @param idService
   *        is the idService to set.
   */
  @Resource
  public void setIdService(IdService idService) {

    this.idService = idService;
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

    Id id = this.idService.createId(ContentClassImpl.CLASS_CLASS);
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

    // validation
    ContentField field = declaringClass.getField(name);
    if (field != null) {
      if (field.getModifiers().isFinal()) {
        throw new ContentModelException("Can not extend final field: " + field + "!");
      }
      if (!field.getFieldType().isAssignableFrom(type)) {
        throw new ContentModelException("Can not extend " + field + " with incompatible type "
            + type.getName() + "!");
      }
    }
    if (modifiers.isSystem()) {
      throw new ContentModelException("User can not create system field: " + name + "!");      
    }
    // creation
    Id id = this.idService.createId(ContentClassImpl.CLASS_FIELD);
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
