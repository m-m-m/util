/* $Id: UIDateEditor.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swing.widget.editor;

import java.util.Date;
import java.util.Locale;

import javax.swing.JComponent;

import com.toedter.calendar.JDateChooser;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditorIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIWidget;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditorIF} interface
 * using Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDateEditor extends UIWidget implements UIDateEditorIF {

    /** the date chooser */
    private final JDateChooser dateChooser;
    
    /**
     * The constructor.
     * 
     * @param uiFactory
     * @param parentObject
     */
    public UIDateEditor(UIFactory uiFactory, UINodeIF parentObject) {

        super(uiFactory, parentObject);
        this.dateChooser = new JDateChooser();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swing.UIComponent#getSwingComponent()
     * {@inheritDoc}
     */
    @Override
    public JComponent getSwingComponent() {

        return this.dateChooser;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditorIF#setDate(java.util.Date)
     * {@inheritDoc}
     */
    public void setDate(Date newDate) {

        this.dateChooser.setDate(newDate);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditorIF#getDate()
     * {@inheritDoc}
     */
    public Date getDate() {

        return this.dateChooser.getDate();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteLocaleIF#setLocale(java.util.Locale)
     * {@inheritDoc}
     */
    public void setLocale(Locale newLocale) {

        this.dateChooser.setLocale(newLocale);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadLocaleIF#getLocale()
     * {@inheritDoc}
     */
    public Locale getLocale() {

        return this.dateChooser.getLocale();
    }

}
