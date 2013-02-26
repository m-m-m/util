/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.model.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.api.reflection.DataReflectionService;
import net.sf.mmm.data.impl.reflection.CoreDataReflectionService;

import org.junit.Ignore;
import org.junit.Test;

/**
 * This is the test-case for {@link CoreDataReflectionService}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class CoreContentModelServiceTest {

  public void checkClass(DataReflectionService modelService, DataClass contentClass) {

    assertNotNull(contentClass);
    assertSame(contentClass, modelService.getDataClass(contentClass.getTitle()));
    assertSame(contentClass, modelService.getDataClass(contentClass.getId()));
  }

  @Test
  @Ignore
  public void testModelService() throws Exception {

    CoreDataReflectionService modelService = new CoreDataReflectionService();
    modelService.initialize();

    DataClass rootClass = modelService.getRootDataClass();
    checkClass(modelService, rootClass);
    assertEquals(DataObject.CLASS_TITLE, rootClass.getTitle());
    assertNotNull(rootClass.getJavaClass());
    assertTrue(DataObject.class.isAssignableFrom(rootClass.getJavaClass()));

    DataClass classClass = modelService.getDataClass(rootClass);
    checkClass(modelService, classClass);
    assertEquals(DataClass.CLASS_TITLE, classClass.getTitle());
    assertNotNull(classClass.getJavaClass());
    assertTrue(DataClass.class.isAssignableFrom(classClass.getJavaClass()));
    assertTrue(rootClass.isSuperClassOf(classClass));
    assertTrue(classClass.isSubClassOf(rootClass));

    DataField idField = rootClass.getField(DataObject.FIELD_NAME_ID);
    assertNotNull(idField);
    assertEquals(DataObject.FIELD_NAME_ID, idField.getTitle());
    assertSame(rootClass, idField.getDeclaringClass());

    DataClass fieldClass = modelService.getDataClass(idField);
    checkClass(modelService, fieldClass);
    assertEquals(DataField.CLASS_TITLE, fieldClass.getTitle());
    assertNotNull(fieldClass.getJavaClass());
    assertTrue(DataField.class.isAssignableFrom(fieldClass.getJavaClass()));
    assertTrue(rootClass.isSuperClassOf(fieldClass));
    assertTrue(fieldClass.isSubClassOf(rootClass));
  }

}
