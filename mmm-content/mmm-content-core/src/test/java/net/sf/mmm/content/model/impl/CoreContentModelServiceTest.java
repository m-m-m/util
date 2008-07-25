/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.ContentModelService;

/**
 * This is the test-case for {@link CoreContentModelService}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class CoreContentModelServiceTest {

  public void checkClass(ContentModelService modelService, ContentClass contentClass) {

    assertNotNull(contentClass);
    assertSame(contentClass, modelService.getContentClass(contentClass.getName()));
    assertSame(contentClass, modelService.getContentClass(contentClass.getId()));
  }

  @Test
  @Ignore
  public void testModelService() throws Exception {

    CoreContentModelService modelService = new CoreContentModelService();
    modelService.initialize();

    ContentClass rootClass = modelService.getRootContentClass();
    checkClass(modelService, rootClass);
    assertEquals(ContentObject.CLASS_NAME, rootClass.getName());
    assertNotNull(rootClass.getJavaClass());
    assertTrue(ContentObject.class.isAssignableFrom(rootClass.getJavaClass()));

    ContentClass classClass = rootClass.getContentClass();
    checkClass(modelService, classClass);
    assertEquals(ContentClass.CLASS_NAME, classClass.getName());
    assertNotNull(classClass.getJavaClass());
    assertTrue(ContentClass.class.isAssignableFrom(classClass.getJavaClass()));
    assertTrue(rootClass.isSuperClassOf(classClass));
    assertTrue(classClass.isSubClassOf(rootClass));

    ContentField fieldId = rootClass.getField("id");
    assertNotNull(fieldId);
    assertEquals("id", fieldId.getName());
    assertSame(rootClass, fieldId.getDeclaringClass());

    ContentClass fieldClass = fieldId.getContentClass();
    checkClass(modelService, fieldClass);
    assertEquals(ContentField.CLASS_NAME, fieldClass.getName());
    assertNotNull(fieldClass.getJavaClass());
    assertTrue(ContentField.class.isAssignableFrom(fieldClass.getJavaClass()));
    assertTrue(rootClass.isSuperClassOf(fieldClass));
    assertTrue(fieldClass.isSubClassOf(rootClass));

  }

}
