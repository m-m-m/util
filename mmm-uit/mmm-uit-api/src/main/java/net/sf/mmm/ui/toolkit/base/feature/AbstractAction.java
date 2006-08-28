/* $Id: AbstractAction.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.base.feature;

import net.sf.mmm.ui.toolkit.api.UIPictureIF;
import net.sf.mmm.ui.toolkit.api.feature.ActionIF;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.feature.ActionIF} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractAction implements ActionIF {

    /** the {@link #getName() name} */
    private String name;

    /** the {@link #getId() id} */
    private String id;

    /** the {@link #getStyle() style} */
    private ButtonStyle style;

    /** the {@link #getIcon() icon} */
    private UIPictureIF icon;

    /**
     * The constructor.
     */
    public AbstractAction() {

        this(null);
    }

    /**
     * The constructor.
     * 
     * @param displayName
     *        is the {@link #getName() name}.
     */
    public AbstractAction(String displayName) {

        super();
        this.style = ButtonStyle.DEFAULT;
        this.icon = null;
        this.name = displayName;
        this.id = displayName;
    }

    /**
     * This method sets the {@link #getName() name}.
     * 
     * @param newName
     *        is the new name.
     */
    public void setName(String newName) {

        this.name = newName;
        if (this.id == null) {
            this.name = newName;
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.feature.ActionIF#getName()
     * {@inheritDoc}
     */
    public String getName() {

        return this.name;
    }

    /**
     * This method sets the {@link #getId() id}.
     * 
     * @param newId
     *        is the new id to set.
     */
    public void setId(String newId) {

        this.id = newId;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.feature.ActionIF#getId()
     * {@inheritDoc}
     */
    public String getId() {

        return this.id;
    }

    /**
     * This method sets the {@link #getStyle() style}.
     * 
     * @param newStyle
     *        is the style to set.
     */
    public void setStyle(ButtonStyle newStyle) {

        this.style = newStyle;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.feature.ActionIF#getStyle()
     * {@inheritDoc}
     */
    public ButtonStyle getStyle() {

        return this.style;
    }

    /**
     * This method set the {@link #getIcon() icon}.
     * 
     * @param newIcon
     *        is the new icon to set.
     */
    public void setIcon(UIPictureIF newIcon) {

        this.icon = newIcon;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.feature.ActionIF#getIcon()
     * {@inheritDoc}
     */
    public UIPictureIF getIcon() {

        return this.icon;
    }

}