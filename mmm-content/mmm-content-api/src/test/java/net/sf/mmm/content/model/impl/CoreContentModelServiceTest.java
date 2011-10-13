/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.reflection.api.ContentClass;
import net.sf.mmm.content.reflection.api.ContentField;
import net.sf.mmm.content.reflection.api.ContentReflectionService;
import net.sf.mmm.content.reflection.impl.CoreContentModelService;

import org.junit.Ignore;
import org.junit.Test;

/**
 * This is the test-case for {@link CoreContentModelService}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class CoreContentModelServiceTest {

  public void checkClass(ContentReflectionService modelService, ContentClass contentClass) {

    assertNotNull(contentClass);
    assertSame(contentClass, modelService.getContentClass(contentClass.getTitle()));
    assertSame(contentClass, modelService.getContentClass(contentClass.getId()));
  }

  @Test
  @Ignore
  public void testModelService() throws Exception {

    CoreContentModelService modelService = new CoreContentModelService();
    modelService.initialize();

    ContentClass rootClass = modelService.getRootContentClass();
    checkClass(modelService, rootClass);
    assertEquals(ContentObject.CLASS_NAME, rootClass.getTitle());
    assertNotNull(rootClass.getJavaClass());
    assertTrue(ContentObject.class.isAssignableFrom(rootClass.getJavaClass()));

    ContentClass classClass = modelService.getContentClass(rootClass);
    checkClass(modelService, classClass);
    assertEquals(ContentClass.CLASS_NAME, classClass.getTitle());
    assertNotNull(classClass.getJavaClass());
    assertTrue(ContentClass.class.isAssignableFrom(classClass.getJavaClass()));
    assertTrue(rootClass.isSuperClassOf(classClass));
    assertTrue(classClass.isSubClassOf(rootClass));

    ContentField idField = rootClass.getField(ContentObject.FIELD_NAME_ID);
    assertNotNull(idField);
    assertEquals(ContentObject.FIELD_NAME_ID, idField.getTitle());
    assertSame(rootClass, idField.getDeclaringClass());

    ContentClass fieldClass = modelService.getContentClass(idField);
    checkClass(modelService, fieldClass);
    assertEquals(ContentField.CLASS_NAME, fieldClass.getTitle());
    assertNotNull(fieldClass.getJavaClass());
    assertTrue(ContentField.class.isAssignableFrom(fieldClass.getJavaClass()));
    assertTrue(rootClass.isSuperClassOf(fieldClass));
    assertTrue(fieldClass.isSubClassOf(rootClass));
  }

}
