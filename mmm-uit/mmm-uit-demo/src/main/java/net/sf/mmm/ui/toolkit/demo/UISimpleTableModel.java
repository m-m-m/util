/* $Id$ */
package net.sf.mmm.ui.toolkit.demo;

import net.sf.mmm.ui.toolkit.api.event.EventType;
import net.sf.mmm.ui.toolkit.api.event.UITableModelListenerIF;
import net.sf.mmm.ui.toolkit.api.model.UIMutableTableModelIF;
import net.sf.mmm.ui.toolkit.base.model.UIAbstractTableModel;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UISimpleTableModel extends UIAbstractTableModel<String> implements
        UIMutableTableModelIF<String> {

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
     * @see net.sf.mmm.ui.toolkit.api.model.UITableModelIF#getColumnCount()
     * 
     */
    public int getColumnCount() {

        return this.columnNames.length;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UITableModelIF#getRowCount()
     * 
     */
    public int getRowCount() {

        return this.cells.length;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UITableModelIF#getCellValue(int,
     *      int)
     * 
     */
    public String getCellValue(int rowIndex, int columnIndex) {

        return this.cells[rowIndex][columnIndex];
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UITableModelIF#getColumnName(int)
     * 
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
     *
     */
    public void initColumnNames() {
        
        for (int col = 0; col < this.columnNames.length; col++) {
            String letter = "" + (char)('A' + col); 
            setColumnName(col, letter);
        }
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIMutableTableModelIF#setCellValue(int,
     *      int, Object)
     * 
     */
    public void setCellValue(int rowIndex, int columnIndex, String value) {

        this.cells[rowIndex][columnIndex] = value;
        fireChangeEvent(EventType.UPDATE, rowIndex, rowIndex, columnIndex);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIMutableTableModelIF#setColumnName(int, java.lang.String)
     * 
     */
    public void setColumnName(int columnIndex, String name) {

        this.columnNames[columnIndex] = name;
        fireChangeEvent(EventType.UPDATE, -1, 0, columnIndex);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.model.UIAbstractTableModel#handleListenerException(net.sf.mmm.ui.toolkit.api.event.UITableModelListenerIF, java.lang.Throwable)
     * 
     */
    @Override
    protected void handleListenerException(UITableModelListenerIF listener, Throwable t) {

        t.printStackTrace();        
    }
}
