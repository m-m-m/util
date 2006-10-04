/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.model.UITableModelIF;
import net.sf.mmm.ui.toolkit.api.widget.UITableIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swing.model.TableModelAdapter;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UITableIF} interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITable extends UIWidget implements UITableIF {

    /** the native Swing table */
    private final JTable table;
    
    /** the swing scroll pane */
    private final JScrollPane scrollPanel;

    /** the table model adapter */
    private TableModelAdapter modelAdapter;
    
    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UITable(UIFactory uiFactory, UINodeIF parentObject) {

        super(uiFactory, parentObject);
        this.table = new JTable();
        this.table.setShowGrid(true);
        this.table.setColumnModel(new DefaultTableColumnModel());
        this.scrollPanel = new JScrollPane(this.table);
        this.scrollPanel.setMinimumSize(new Dimension(40, 40));
        this.modelAdapter = null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swing.UIComponent#getSwingComponent()
     * 
     */
    @Override
    public JComponent getSwingComponent() {

        return this.scrollPanel;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * 
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UITableIF#getModel()
     * 
     */
    public UITableModelIF getModel() {

        if (this.modelAdapter == null) {
            return null;
        } else {
            return this.modelAdapter.getModel();            
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UITableIF#setModel(net.sf.mmm.ui.toolkit.api.model.UITableModelIF)
     * 
     */
    public void setModel(UITableModelIF newModel) {

        if (this.modelAdapter != null) {
            this.modelAdapter.dispose();
        }
        this.modelAdapter = new TableModelAdapter(newModel);
        this.table.setModel(this.modelAdapter);
        this.table.createDefaultColumnsFromModel();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swing.UIComponent#getActiveSwingComponent()
     * 
     */
    @Override
    protected JComponent getActiveSwingComponent() {

        return this.table;
    }

}
