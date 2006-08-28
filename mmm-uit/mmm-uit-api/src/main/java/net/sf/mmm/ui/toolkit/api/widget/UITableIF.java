/* $Id$ */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.model.UITableModelIF;

/**
 * This is the interface for a table.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UITableIF extends UIWidgetIF {

    /** the type of this object */
    String TYPE = "Table";

    /**
     * This method gets the assigned model. 
     * 
     * @return the table model.
     */
    UITableModelIF getModel();

    /**
     * This method sets the model of this table. If the table already has a
     * model assigned, it will be replaced.
     * 
     * @param newModel
     *        is the new model to set.
     */
    void setModel(UITableModelIF newModel);

}
