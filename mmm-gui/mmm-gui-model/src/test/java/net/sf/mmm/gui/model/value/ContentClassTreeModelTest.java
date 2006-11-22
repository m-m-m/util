/* $Id$ */
package net.sf.mmm.gui.model.value;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.MutableContentModelService;
import net.sf.mmm.content.model.impl.BasicMutableContentModelServiceImpl;
import net.sf.mmm.content.model.impl.ContentModelInitializer;
import net.sf.mmm.gui.model.content.ContentClassTableManager;
import net.sf.mmm.gui.model.content.ContentClassTreeModel;
import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIPanel;
import net.sf.mmm.ui.toolkit.api.composite.UISplitPanel;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.event.UIActionListener;
import net.sf.mmm.ui.toolkit.api.widget.UITable;
import net.sf.mmm.ui.toolkit.api.widget.UITree;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentClassTreeModelTest {

  /**
   * The constructor.
   * 
   */
  public ContentClassTreeModelTest() {

    super();
  }

  /**
   * The main method to run this class.
   * 
   * @param args
   *        are the command-line arguments.
   */
  public static void main(String[] args) throws Exception {

    UIFactory uiFactory = new UIFactorySwing();
    MutableContentModelService modelService = new BasicMutableContentModelServiceImpl();
    ContentClassTreeModel classModel = new ContentClassTreeModel();
    classModel.setModelService(modelService);
    classModel.initialize();

    ContentModelInitializer.initializeModel();

    /*
     * ValueService valueService = new StaticValueServiceImpl(); ValueTypeModel
     * valueTypeModel = new ValueTypeModel();
     * valueTypeModel.setValueService(valueService);
     * valueTypeModel.initialize(); ValueTypeWidgetFactory widgetFactory = new
     * ValueTypeWidgetFactory(); widgetFactory.setModel(valueTypeModel);
     * UIComboBox<ValueManager> combo =
     * widgetFactory.createValueTypeCombo(uiFactory);
     */

    final UITree<ContentClass> tree = uiFactory.createTree(false, classModel);
    UISplitPanel splitPanel = uiFactory.createSplitPanel(Orientation.HORIZONTAL);
    splitPanel.setTopOrLeftComponent(tree);
    UIFrame frame = uiFactory.createFrame("test");
    UIPanel panel = uiFactory.createPanel(Orientation.HORIZONTAL);
    final ContentClassTableManager tableFactory = new ContentClassTableManager();
    tableFactory.setContentModelService(modelService);
    final UITable table = uiFactory.createTable(false, tableFactory.getTableModel(modelService
        .getRootClass()));
    tree.addActionListener(new UIActionListener() {

      public void invoke(UINode source, ActionType action) {

        if (action == ActionType.SELECT) {
          ContentClass contentClass = tree.getSelection();
          table.setModel(tableFactory.getTableModel(contentClass));
        }
      }
    });
    splitPanel.setBottomOrRightComponent(table);
    frame.setComposite(splitPanel);
    frame.setSize(600, 400);
    frame.centerWindow();
    frame.setVisible(true);
    splitPanel.setDividerPosition(0.5);
  }

}
