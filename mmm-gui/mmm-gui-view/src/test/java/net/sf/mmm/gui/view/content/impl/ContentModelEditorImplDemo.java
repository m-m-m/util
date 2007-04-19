/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.gui.view.content.impl;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.ConfigurationDocument;
import net.sf.mmm.configuration.api.access.ConfigurationAccess;
import net.sf.mmm.configuration.base.access.ConfigurationFactory;
import net.sf.mmm.configuration.impl.access.resource.ResourceAccess;
import net.sf.mmm.configuration.impl.format.xml.dom.XmlFactory;
import net.sf.mmm.content.model.base.ClassModifiersImpl;
import net.sf.mmm.content.model.base.FieldModifiersImpl;
import net.sf.mmm.content.model.impl.AbstractMutableContentModelService;
import net.sf.mmm.content.model.impl.ConfiguredModelService;
import net.sf.mmm.content.value.impl.IdImpl;
import net.sf.mmm.content.value.impl.VersionImpl;
import net.sf.mmm.gui.model.content.impl.ContentClassFieldTableManagerImpl;
import net.sf.mmm.gui.model.content.impl.ContentClassTreeModel;
import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.value.api.ValueService;
import net.sf.mmm.value.impl.StaticValueServiceImpl;
import net.sf.mmm.value.impl.ValueServiceImpl;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentModelEditorImplDemo {

  /**
   * The constructor.
   */
  public ContentModelEditorImplDemo() {

    super();
  }

  public static void main(String[] args) throws Exception {

    // static initialization
    UIFactory uiFactory = new UIFactorySwing();
    ConfiguredModelService modelServiceImpl = new ConfiguredModelService();
    ConfigurationAccess access = new ResourceAccess("net/sf/mmm/content/model/ContentModel.xml");
    ConfigurationFactory factory = new XmlFactory();
    ConfigurationDocument doc = factory.create(access);
    Configuration configuration = doc.getConfiguration();
    ValueServiceImpl valueServiceImpl = new StaticValueServiceImpl();
    valueServiceImpl.addManager(new IdImpl.Manager());
    valueServiceImpl.addManager(new ClassModifiersImpl.Manager());
    valueServiceImpl.addManager(new FieldModifiersImpl.Manager());
    valueServiceImpl.addManager(new VersionImpl.Manager());
    ValueService valueService = valueServiceImpl;
    modelServiceImpl.setValueService(valueService);
    modelServiceImpl.setConfiguration(configuration);
    modelServiceImpl.initialize();
    AbstractMutableContentModelService modelService = modelServiceImpl;
    //IdService idService = new DummyIdService();
    //modelService.setIdService(idService);
    ContentClassTreeModel classModel = new ContentClassTreeModel();
    classModel.setModelService(modelService);
    classModel.initialize();
    ContentClassFieldTableManagerImpl tableManager = new ContentClassFieldTableManagerImpl();
    tableManager.setContentModelService(modelService);
    ContentModelEditorImpl modelEditor = new ContentModelEditorImpl();
    modelEditor.setContentClassTreeModel(classModel);
    modelEditor.setContentModelService(modelService);
    modelEditor.setFieldTableManager(tableManager);
    
    UIComposite modelEditorView = modelEditor.create(uiFactory);
    UIFrame frame = uiFactory.createFrame("Model Editor");
    frame.setComposite(modelEditorView);
    frame.setSize(800, 600);
    frame.centerWindow();
    frame.setVisible(true);
    while (frame.isVisible()) {
      uiFactory.getDisplay().dispatch();
    }
    System.exit(0);
  }
  
}
