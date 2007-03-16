/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.gui.view.content.impl;

import org.junit.Test;

import net.sf.mmm.content.model.impl.AbstractMutableContentModelService;
import net.sf.mmm.content.model.impl.ConfiguredModelService;
import net.sf.mmm.content.persistence.api.IdService;
import net.sf.mmm.content.persistence.base.DummyIdService;
import net.sf.mmm.gui.model.content.impl.ContentClassFieldTableManagerImpl;
import net.sf.mmm.gui.model.content.impl.ContentClassTreeModel;
import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

import junit.framework.TestCase;


/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentModelEditorImplTest extends TestCase {

  /**
   * The constructor.
   */
  public ContentModelEditorImplTest() {

    super();
  }
  
  @Test
  public void testFoo() {
    // nothing so far...
  }

  public static void main(String[] args) throws Exception {

    // static initialization
    UIFactory uiFactory = new UIFactorySwing();
    AbstractMutableContentModelService modelService = new ConfiguredModelService();
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
