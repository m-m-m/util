/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.feature.FileAccessIF;
import net.sf.mmm.ui.toolkit.api.widget.UIFileUploadIF;
import net.sf.mmm.ui.toolkit.base.feature.SimpleFileAccess;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;


/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIFileUploadIF} interface using
 * Swing as the UI toolkit.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIFileUpload extends UIWidget implements UIFileUploadIF {

    /**
     * This inner class implements the listener that handles the button
     * selection.
     */
    private class Listener implements ActionListener {

        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         * {@inheritDoc}
     */
        public void actionPerformed(ActionEvent e) {

            UIFileUpload.this.fileChooser.setDialogTitle(getText());
            int selection = UIFileUpload.this.fileChooser
                    .showOpenDialog(UIFileUpload.this.button);
            if (selection == JFileChooser.APPROVE_OPTION) {
                File uploadFile = UIFileUpload.this.fileChooser.getSelectedFile();
                UIFileUpload.this.access = new SimpleFileAccess(uploadFile);
                invoke(ActionType.SELECT);
            }
        }

    }

    /** the access to the uploaded data */
    private FileAccessIF access;

    /** the widget used to present the download */
    private final JButton button;

    /** the file-chooser used to select where to save the download to */
    private final JFileChooser fileChooser;

    /**
     * The constructor.
     *
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UIFileUpload(UIFactory uiFactory, UINodeIF parentObject) {

        super(uiFactory, parentObject);
        this.access = null;
        this.fileChooser = new JFileChooser();
        this.fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        // TODO: i18n
        this.button = new JButton("Upload");
        //this.button.setToolTipText("Upload " + this.access.getFilename());
        this.button.addActionListener(new Listener());
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swing.UIComponent#getSwingComponent()
     * {@inheritDoc}
     */
    public @Override JComponent getSwingComponent() {

        return this.button;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIFileUploadIF#getSelection()
     * {@inheritDoc}
     */
    public FileAccessIF getSelection() {

        return this.access;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadTextIF#getText()
     * {@inheritDoc}
     */
    public String getText() {

        return this.button.getText();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTextIF#setText(java.lang.String)
     * {@inheritDoc}
     */
    public void setText(String text) {

        this.button.setText(text);
    }

}
