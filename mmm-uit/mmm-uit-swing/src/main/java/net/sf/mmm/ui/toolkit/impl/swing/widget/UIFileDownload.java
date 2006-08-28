/* $Id: UIFileDownload.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.feature.FileAccessIF;
import net.sf.mmm.ui.toolkit.api.widget.UIFileDownloadIF;
import net.sf.mmm.ui.toolkit.base.feature.FileAccessUtil;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIFileDownloadIF} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIFileDownload extends UIWidget implements UIFileDownloadIF {

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

            UIFileDownload.this.fileChooser.setDialogTitle(getText());
            int selection = UIFileDownload.this.fileChooser
                    .showSaveDialog(UIFileDownload.this.button);
            if (selection == JFileChooser.APPROVE_OPTION) {
                FileAccessUtil.save(UIFileDownload.this.access, UIFileDownload.this.fileChooser
                        .getSelectedFile(), getParentWindow());
            }
        }

    }

    /** the access to the downloadable data */
    private final FileAccessIF access;

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
     * @param fileAccess
     *        gives access to the data that is offered for download.
     */
    public UIFileDownload(UIFactory uiFactory, UINodeIF parentObject, FileAccessIF fileAccess) {

        super(uiFactory, parentObject);
        this.access = fileAccess;
        this.fileChooser = new JFileChooser();
        this.fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        // this.fileChooser.setMultiSelectionEnabled(false);
        this.fileChooser.setSelectedFile(new File(this.access.getFilename()));
        // TODO: i18n
        this.button = new JButton("Save");
        this.button.setToolTipText("Save " + this.access.getFilename());
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
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTextIF#setText(java.lang.String)
     * {@inheritDoc}
     */
    public void setText(String text) {

        this.button.setText(text);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadTextIF#getText()
     * {@inheritDoc}
     */
    public String getText() {

        return this.button.getText();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

}
