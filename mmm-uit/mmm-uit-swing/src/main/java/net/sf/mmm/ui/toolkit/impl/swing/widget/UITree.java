/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.model.UITreeModelIF;
import net.sf.mmm.ui.toolkit.api.widget.UITreeIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swing.model.TreeModelAdapter;

/**
 * This class is the implementation of the UITreeIF interface using Swing as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITree extends UIWidget implements UITreeIF {

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
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UITree(UIFactory uiFactory, UINodeIF parentObject) {

        super(uiFactory, parentObject);
        this.tree = new JTree();
        this.tree.setExpandsSelectedPaths(true);
        this.tree.setShowsRootHandles(true);
        this.scrollPanel = new JScrollPane(this.tree);
        this.scrollPanel.setMinimumSize(new Dimension(40, 40));
        this.modelAdapter = null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UITreeIF#getModel()
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public UITreeModelIF getModel() {

        if (this.modelAdapter == null) {
            return null;
        } else {
            return this.modelAdapter.getModel();
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UITreeIF#setModel(net.sf.mmm.ui.toolkit.api.model.UITreeModelIF)
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public void setModel(UITreeModelIF newModel) {

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
     *        if <code>true</code> the user can select multiple items, else
     *        ony one.
     */
    public void setMultiSelection(boolean multiSelection) {

        int mode = TreeSelectionModel.SINGLE_TREE_SELECTION;
        if (multiSelection) {
            mode = TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION;
        }
        this.tree.getSelectionModel().setSelectionMode(mode);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swing.UIComponent#getSwingComponent()
     * {@inheritDoc}
     */
    public JComponent getSwingComponent() {

        return this.scrollPanel;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swing.UIComponent#getActiveSwingComponent()
     * {@inheritDoc}
     */
    protected JComponent getActiveSwingComponent() {

        return this.tree;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadMultiSelectionFlagIF#isMultiSelection()
     * {@inheritDoc}
     */
    public boolean isMultiSelection() {

        int mode = this.tree.getSelectionModel().getSelectionMode();
        return (mode == TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UITreeIF#getSelection()
     * {@inheritDoc}
     */
    public Object getSelection() {

        TreePath selection = this.tree.getSelectionPath();
        if (selection != null) {
            return selection.getLastPathComponent();
        }
        return null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UITreeIF#getSelections()
     * {@inheritDoc}
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