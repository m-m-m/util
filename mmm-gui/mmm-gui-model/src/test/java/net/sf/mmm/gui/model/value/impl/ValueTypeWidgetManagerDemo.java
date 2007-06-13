/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.gui.model.value.impl;

import net.sf.mmm.gui.model.value.impl.ValueTypeListModel;
import net.sf.mmm.gui.model.value.impl.ValueTypeWidgetManager;
import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UISlicePanel;
import net.sf.mmm.ui.toolkit.api.widget.UIComboBox;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.value.api.ValueManager;
import net.sf.mmm.value.api.ValueService;
import net.sf.mmm.value.impl.StaticValueServiceImpl;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueTypeWidgetManagerDemo {

  /**
   * The constructor.
   */
  public ValueTypeWidgetManagerDemo() {

    super();
  }

  /**
   * The main method to run this class.
   * 
   * @param args are the command-line arguments.
   */
  public static void main(String[] args) {

    UIFactory uiFactory = new UIFactorySwing();
    ValueService valueService = new StaticValueServiceImpl();
    ValueTypeListModel valueTypeModel = new ValueTypeListModel();
    valueTypeModel.setValueService(valueService);
    valueTypeModel.initialize();
    ValueTypeWidgetManager widgetFactory = new ValueTypeWidgetManager();
    widgetFactory.setModel(valueTypeModel);
    UIComboBox<ValueManager> combo = widgetFactory.createValueTypeCombo(uiFactory);
    UIFrame frame = uiFactory.createFrame("test");
    UISlicePanel panel = uiFactory.createPanel(Orientation.HORIZONTAL);
    panel.addComponent(combo);
    frame.setComposite(panel);
    frame.setVisible(true);
  }

}
