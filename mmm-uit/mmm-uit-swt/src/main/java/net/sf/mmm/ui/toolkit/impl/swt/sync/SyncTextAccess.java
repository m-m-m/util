/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;

/**
 * This class is used for synchron access on a SWT
 * {@link org.eclipse.swt.widgets.Text}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncTextAccess extends AbstractSyncControlAccess {

    /**
     * operation to set the
     * {@link org.eclipse.swt.widgets.Text#setText(String) text} of the text.
     */
    private static final String OPERATION_SET_TEXT = "setText";

    /**
     * operation to get the {@link org.eclipse.swt.widgets.Text#getText() text}
     * of the text.
     */
    private static final String OPERATION_GET_TEXT = "getText";

    /**
     * operation to set the
     * {@link org.eclipse.swt.widgets.Text#setEditable(boolean) editable-flag}
     * of the text.
     */
    private static final String OPERATION_SET_EDITABLE = "setEditable";

    /**
     * operation to set the
     * {@link org.eclipse.swt.widgets.Text#setTextLimit(int) text-limit} of the
     * text.
     */
    private static final String OPERATION_SET_TEXT_LIMIT = "setTextLimit";

    /**
     * operation to set the
     * {@link org.eclipse.swt.widgets.Text#setEchoChar(char) echo-char} of the
     * text.
     */
    private static final String OPERATION_SET_ECHO_CHAR = "setEchoChar";

    /** the text to access */
    private Text textField;

    /** the text of this text */
    private String text;

    /** the editable flag */
    private boolean editable;

    /** the maximum character count for the text */
    private int textLimit;

    /** the echo character or '\0' for normal */
    private char echoChar;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is used to do the synchonization.
     * @param swtStyle
     *        is the {@link Widget#getStyle() style} of the text.
     */
    public SyncTextAccess(UIFactory uiFactory, int swtStyle) {

        super(uiFactory, swtStyle);
        this.textField = null;
        this.text = "";
        this.editable = true;
        this.textLimit = Text.LIMIT;
        this.echoChar = 0;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncWidgetAccess#getSwtObject()
     * {@inheritDoc}
     */
    @Override
    public Text getSwtObject() {

        return this.textField;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncWidgetAccess#performSynchron(String)
     * {@inheritDoc}
     */
    @Override
    protected void performSynchron(String operation) {

        if (operation == OPERATION_SET_TEXT) {
            this.textField.setText(this.text);
        } else if (operation == OPERATION_GET_TEXT) {
            this.text = this.textField.getText();
        } else if (operation == OPERATION_SET_EDITABLE) {
            this.textField.setEditable(this.editable);
        } else if (operation == OPERATION_SET_TEXT_LIMIT) {
            this.textField.setTextLimit(this.textLimit);
        } else if (operation == OPERATION_SET_ECHO_CHAR) {
            this.textField.setEchoChar(this.echoChar);
        } else {
            super.performSynchron(operation);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncWidgetAccess#createSynchron()
     * {@inheritDoc}
     */
    @Override
    protected void createSynchron() {

        this.textField = new Text(getParent(), getStyle());
        this.textField.setEditable(this.editable);
        this.textField.setTextLimit(this.textLimit);
        this.textField.setEchoChar(this.echoChar);
        if ((this.text != null) && (this.text.length() > 0)) {
            this.textField.setText(this.text);
        }
        super.createSynchron();
    }

    /**
     * This method gets the {@link org.eclipse.swt.widgets.Text#getText() text}
     * of the text.
     * 
     * @return the text of this text or <code>null</code> if no text is set.
     */
    public String getText() {

        assert (checkReady());
        invoke(OPERATION_SET_TEXT);
        return this.text;
    }

    /**
     * This method sets the
     * {@link org.eclipse.swt.widgets.Text#setText(String) text} of the text.
     * 
     * @param buttonText
     *        is the text to set.
     */
    public void setText(String buttonText) {

        assert (checkReady());
        this.text = buttonText;
        invoke(OPERATION_SET_TEXT);
    }

    /**
     * This method gets the
     * {@link org.eclipse.swt.widgets.Text#getEditable() editable-status} of the
     * text.
     * 
     * @return the selection-status.
     */
    public boolean isEditable() {

        return this.editable;
    }

    /**
     * This method sets the
     * {@link org.eclipse.swt.widgets.Text#setEditable(boolean) editable-status}
     * of the text.
     * 
     * @param selection
     *        is the selection-status to set.
     */
    public void setEditable(boolean selection) {

        assert (checkReady());
        this.editable = selection;
        invoke(OPERATION_SET_EDITABLE);
    }

    /**
     * This method gets the
     * {@link org.eclipse.swt.widgets.Text#getTextLimit() text-limit} of the
     * text.
     * 
     * @return the maximum character count for the text.
     */
    public int getTextLimit() {

        return this.textLimit;
    }

    /**
     * This method sets the
     * {@link org.eclipse.swt.widgets.Text#setTextLimit(int) text-limit} of the
     * text.
     * 
     * @param limit
     *        is the maximum character count for the text.
     */
    public void setTextLimit(int limit) {

        assert (checkReady());
        this.textLimit = limit;
        invoke(OPERATION_SET_TEXT_LIMIT);
    }

    /**
     * This method gets the
     * {@link org.eclipse.swt.widgets.Text#getEchoChar() echo-character} of the
     * text.
     * 
     * @return the echo character or '\0' for normal echoing.
     */
    public char getEchoChar() {

        return this.echoChar;
    }

    /**
     * This method sets the
     * {@link org.eclipse.swt.widgets.Text#setEchoChar(char) echo-character} of
     * the text.
     * 
     * @param echoCharacter
     *        is the echo character or '\0' for normal echoing.
     */
    public void setEchoChar(char echoCharacter) {

        assert (checkReady());
        this.echoChar = echoCharacter;
        invoke(OPERATION_SET_ECHO_CHAR);
    }

}
