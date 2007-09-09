/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import java.util.Map;

import net.sf.mmm.content.entities.AbstractContentDocument;
import net.sf.mmm.content.resource.api.ContentDocument;
import net.sf.mmm.content.resource.api.ContentFile;
import net.sf.mmm.content.resource.api.ContentFolder;
import net.sf.mmm.content.resource.api.ContentResource;
import net.sf.mmm.content.value.api.Blob;
import net.sf.mmm.content.value.api.MutableBlob;
import net.sf.mmm.content.value.api.Version;
import net.sf.mmm.util.reflect.MappedClassResolver;

/**
 * This is the
 * {@link net.sf.mmm.content.model.api.ContentModelService content-model-service}
 * with the defaults required for the resource-model.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ResourceContentModelService extends CoreContentModelService {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeJavaClassMap(Map<String, Class> name2javaClass) {

    super.initializeJavaClassMap(name2javaClass);
    name2javaClass.put(ContentResource.CLASS_NAME, ContentResource.class);
    name2javaClass.put(ContentFile.CLASS_NAME, ContentFile.class);
    name2javaClass.put(ContentFolder.CLASS_NAME, ContentFolder.class);
    name2javaClass.put(ContentDocument.CLASS_NAME, AbstractContentDocument.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeClassResolver(MappedClassResolver mappedClassResolver) {

    super.initializeClassResolver(mappedClassResolver);
    mappedClassResolver.addClassMapping(Blob.VALUE_NAME, MutableBlob.class);
    mappedClassResolver.addClassMapping(Version.VALUE_NAME, Version.class);
  }

}
