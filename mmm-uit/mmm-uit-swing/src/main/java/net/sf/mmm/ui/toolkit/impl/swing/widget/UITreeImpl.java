/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.model.UITreeModel;
import net.sf.mmm.ui.toolkit.api.widget.UITree;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.model.TreeModelAdapter;

/**
 * This class is the implementation of the UITree interface using Swing as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITreeImpl extends AbstractUIWidget implements UITree {

  /** the empty selection */
  private static final Object[] EMPTY_SELECTION = new Object[0];

  /** the swing scroll pane */
  private final JScrollPane scrollPanel;

  /** the unwrapped swing tree */
  private final JTree tree;

  /** the tree model adapter */
  private TreeModelAdapter modelAdapter;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   */
  public UITreeImpl(UIFactorySwing uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
    this.tree = new JTree();
    this.tree.setExpandsSelectedPaths(true);
    this.tree.setShowsRootHandles(true);
    this.scrollPanel = new JScrollPane(this.tree);
    this.scrollPanel.setMinimumSize(new Dimension(40, 40));
    this.modelAdapter = null;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   * 
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UITree#getModel()
   * 
   */
  @SuppressWarnings("unchecked")
  public UITreeModel getModel() {

    if (this.modelAdapter == null) {
      return null;
    } else {
      return this.modelAdapter.getModel();
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UITree#setModel(net.sf.mmm.ui.toolkit.api.model.UITreeModel)
   * 
   */
  @SuppressWarnings("unchecked")
  public void setModel(UITreeModel newModel) {

    if (this.modelAdapter != null) {
      this.modelAdapter.dispose();
    }
    this.modelAdapter = new TreeModelAdapter(newModel);
    this.tree.setModel(this.modelAdapter);
  }

  /**
   * This method sets the selection mode of this tree.
   * 
   * @param multiSelection -
   *        if <code>true</code> the user can select multiple items, else ony
   *        one.
   */
  public void setMultiSelection(boolean multiSelection) {

    int mode = TreeSelectionModel.SINGLE_TREE_SELECTION;
    if (multiSelection) {
      mode = TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION;
    }
    this.tree.getSelectionModel().setSelectionMode(mode);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   * 
   */
  public JComponent getSwingComponent() {

    return this.scrollPanel;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getActiveSwingComponent()
   * 
   */
  protected JComponent getActiveSwingComponent() {

    return this.tree;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadMultiSelectionFlag#isMultiSelection()
   * 
   */
  public boolean isMultiSelection() {

    int mode = this.tree.getSelectionModel().getSelectionMode();
    return (mode == TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UITree#getSelection()
   * 
   */
  public Object getSelection() {

    TreePath selection = this.tree.getSelectionPath();
    if (selection != null) {
      return selection.getLastPathComponent();
    }
    return null;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UITree#getSelections()
   * 
   */
  public Object[] getSelections() {

    TreePath[] selections = this.tree.getSelectionPaths();
    if ((selections != null) && (selections.length > 0)) {
      Object[] result = new Object[selections.length];
      for (int i = 0; i < selections.length; i++) {
        result[i] = selections[i].getLastPathComponent();
      }
      return result;
    } else {
      return EMPTY_SELECTION;
    }
  }

}
