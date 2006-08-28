/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.widget.editor;

import java.util.Date;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

import net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditorIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.custom.MyDateEditor;
import net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncControlAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncCompositeAccess;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UIWidget;


/**
 * TODO This type ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDateEditor extends UIWidget implements UIDateEditorIF {

    /** */
    private final SyncCompositeAccess syncAccess;
    
    /**
     * The constructor.
     *
     * @param uiFactory
     * @param parentObject
     */
    public UIDateEditor(UIFactory uiFactory, UISwtNode parentObject) {

        super(uiFactory, parentObject);
        this.syncAccess = new SyncCompositeAccess(uiFactory, SWT.DEFAULT);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#getSyncAccess()
     * {@inheritDoc}
     */
    @Override
    public AbstractSyncControlAccess getSyncAccess() {
    
        return this.syncAccess;
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditorIF#setDate(java.util.Date)
     * {@inheritDoc}
     */
    public void setDate(Date newDate) {

    // TODO Auto-generated method stub

    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditorIF#getDate()
     * {@inheritDoc}
     */
    public Date getDate() {

        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadLocaleIF#getLocale()
     * {@inheritDoc}
     */
    public Locale getLocale() {

        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteLocaleIF#setLocale(java.util.Locale)
     * {@inheritDoc}
     */
    public void setLocale(Locale newLocale) {

        // TODO Auto-generated method stub
        
    }

}
