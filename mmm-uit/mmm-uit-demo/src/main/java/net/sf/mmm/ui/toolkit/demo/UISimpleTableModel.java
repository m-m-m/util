/* $Id$ */
package net.sf.mmm.ui.toolkit.demo;

import net.sf.mmm.ui.toolkit.api.event.EventType;
import net.sf.mmm.ui.toolkit.api.event.UITableModelListener;
import net.sf.mmm.ui.toolkit.api.model.UIMutableTableModel;
import net.sf.mmm.ui.toolkit.base.model.AbstractUITableModel;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UISimpleTableModel extends AbstractUITableModel<String> implements
        UIMutableTableModel<String> {

    /** */
    private final String[][] cells;

    /** */
    private final String[] columnNames;

    /**
     * The constructor.
     * 
     * @param rowCount
     * @param columnCount
     */
    public UISimpleTableModel(int rowCount, int columnCount) {

        super();
        this.cells = new String[rowCount][columnCount];
        this.columnNames = new String[columnCount];
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UITableModel#getColumnCount()
     */
    public int getColumnCount() {

        return this.columnNames.length;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UITableModel#getRowCount()
     */
    public int getRowCount() {

        return this.cells.length;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UITableModel#getCellValue(int,
     *      int)
     */
    public String getCellValue(int rowIndex, int columnIndex) {

        return this.cells[rowIndex][columnIndex];
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UITableModel#getColumnName(int)
     */
    public String getColumnName(int columnIndex) {

        return this.columnNames[columnIndex];
    }

    /**
     * 
     */
    public void initCells() {

        for (int col = 0; col < this.columnNames.length; col++) {
            String s = getColumnName(col);
            if (s == null) {
                s = "" + (char)('A' + col);
            }
            for (int row = 0; row < this.cells.length; row++) {
                this.cells[row][col] = s + row;
            }
        }
    }

    /**
     * 
     */
    public void initColumnNames() {
        
        for (int col = 0; col < this.columnNames.length; col++) {
            String letter = "" + (char)('A' + col); 
            setColumnName(col, letter);
        }
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIMutableTableModel#setCellValue(int,
     *      int, Object)
     */
    public void setCellValue(int rowIndex, int columnIndex, String value) {

        this.cells[rowIndex][columnIndex] = value;
        fireChangeEvent(EventType.UPDATE, rowIndex, rowIndex, columnIndex);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIMutableTableModel#setColumnName(int, java.lang.String)
     */
    public void setColumnName(int columnIndex, String name) {

        this.columnNames[columnIndex] = name;
        fireChangeEvent(EventType.UPDATE, -1, 0, columnIndex);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.model.AbstractUITableModel#handleListenerException(net.sf.mmm.ui.toolkit.api.event.UITableModelListener, java.lang.Throwable)
     */
    @Override
    protected void handleListenerException(UITableModelListener listener, Throwable t) {

        t.printStackTrace();        
    }
}
