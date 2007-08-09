/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.gui.view.content.impl;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.impl.AdvancedDummyClassResolver;
import net.sf.mmm.content.model.impl.BasicContentModelService;
import net.sf.mmm.content.model.impl.DummyClassResolver;
import net.sf.mmm.gui.model.content.impl.ContentReflectionModelManagerImpl;
import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.model.UITreeModel;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

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
    BasicContentModelService modelService = new BasicContentModelService();
    DummyClassResolver resolver = new AdvancedDummyClassResolver();
    resolver.initialize();
    modelService.setClassResolver(resolver);
    modelService.initialize();
    

    final ContentReflectionModelManagerImpl modelManager = new ContentReflectionModelManagerImpl();
    modelManager.setContentModelService(modelService);
    modelManager.initialize();
    UITreeModel<ContentClass> classModel = modelManager.getContentClassTreeModel();
    
    // IdService idService = new DummyIdService();
    // modelService.setIdService(idService);
    ContentModelEditorImpl modelEditor = new ContentModelEditorImpl();
    modelEditor.setContentClassTreeModel(classModel);
    modelEditor.setContentModelService(modelService);
    modelEditor.setFieldTableManager(modelManager);

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
