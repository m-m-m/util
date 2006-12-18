/* $Id: $ */
package net.sf.mmm.content.model.impl;

import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.api.ContentReflectionObject;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.model.api.Modifiers;
import net.sf.mmm.content.model.base.ClassModifiersImpl;
import net.sf.mmm.content.model.base.FieldModifiersImpl;
import net.sf.mmm.content.value.api.Id;
import net.sf.mmm.content.value.impl.IdImpl;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentModelInitializer {

  /**
   * The constructor.
   */
  private ContentModelInitializer() {

  }

  /**
   * This method adds a new field to the content-model.
   * 
   * @param contentClass
   * @param id
   * @param name
   * @param type
   * @param modifiers
   * @throws ContentModelException
   */
  private static void addField(ContentClassImpl contentClass, int id, String name, Class type,
      FieldModifiers modifiers) throws ContentModelException {

    ContentField field = new ContentFieldImpl(new IdImpl(id, IdImpl.CLASS_ID_FIELD), name,
        contentClass, type, modifiers);
    contentClass.addField(field);
  }

  /**
   * 
   * @throws ContentModelException
   */
  public static void initializeModel() throws ContentModelException {

    // fields of root-class
    addField(ContentClassImpl.CLASS_ROOT, 0, ContentClass.FIELD_NAME_ID, Id.class,
        FieldModifiersImpl.SYSTEM_FINAL_READONLY);
    // name can only be modified via repository.rename
    addField(ContentClassImpl.CLASS_ROOT, 1, ContentClass.FIELD_NAME_NAME, String.class,
        FieldModifiersImpl.SYSTEM_FINAL_READONLY);
    addField(ContentClassImpl.CLASS_ROOT, 2, ContentClass.FIELD_NAME_CLASS, ContentClass.class,
        FieldModifiersImpl.SYSTEM_FINAL_READONLY);

    // fields of reflection-object
    addField(ContentClassImpl.CLASS_REFLECTION, 3, ContentReflectionObject.FIELD_NAME_MODIFIERS,
        Modifiers.class, FieldModifiersImpl.SYSTEM_FINAL_READONLY);

    // fields of class
    addField(ContentClassImpl.CLASS_CLASS, 4, ContentReflectionObject.FIELD_NAME_MODIFIERS,
        ClassModifiers.class, FieldModifiersImpl.SYSTEM_FINAL_READONLY);

    // fields of field
    addField(ContentClassImpl.CLASS_FIELD, 5, ContentReflectionObject.FIELD_NAME_MODIFIERS,
        FieldModifiers.class, FieldModifiersImpl.SYSTEM_FINAL_READONLY);

    ContentClass testClass = new ContentClassImpl(new IdImpl(0, 5), "Test",
        ContentClassImpl.CLASS_ROOT, ClassModifiersImpl.NORMAL);
    ContentClassImpl.CLASS_ROOT.addSubClass(testClass);
  }
}
