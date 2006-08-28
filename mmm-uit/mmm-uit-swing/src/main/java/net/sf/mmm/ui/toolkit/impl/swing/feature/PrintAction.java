/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.feature;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JComponent;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF;
import net.sf.mmm.ui.toolkit.api.feature.ActionIF;
import net.sf.mmm.ui.toolkit.api.window.MessageType;
import net.sf.mmm.ui.toolkit.api.window.UIWindowIF;
import net.sf.mmm.ui.toolkit.base.feature.AbstractAction;
import net.sf.mmm.ui.toolkit.impl.swing.UIComponent;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIFactoryIF#createPrintAction(UIComponentIF) print-action}
 * for SWING.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PrintAction extends AbstractAction implements UIActionListenerIF, Printable {

    /** the component to be printed */
    private final UIComponent component;

    /**
     * The constructor.
     * 
     * @param printComponent
     *        is the component to be printed by this action.
     * @param actionName
     *        is the {@link ActionIF#getName() name} of the print action.
     * @param jobName
     *        is the name of the print job.
     */
    public PrintAction(UIComponent printComponent, String actionName, String jobName) {

        super(actionName);
        setId(jobName);
        this.component = printComponent;

    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.feature.ActionIF#getActionListener()
     * {@inheritDoc}
     */
    public UIActionListenerIF getActionListener() {

        return this;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF#invoke(net.sf.mmm.ui.toolkit.api.UINodeIF,
     *      net.sf.mmm.ui.toolkit.api.event.ActionType)
     * {@inheritDoc}
     */
    public void invoke(UINodeIF source, ActionType action) {

        if (action == ActionType.SELECT) {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setJobName(getId());
            job.setPrintable(this);
            boolean doPrint = job.printDialog();
            if (doPrint) {
                System.out.println("Printing...");
                try {
                    job.print();
                } catch (PrinterException e) {
                    UIWindowIF window = this.component.getParentWindow();
                    if (window != null) {
                        window.showMessage("Printing failed!", "Error", MessageType.ERROR, e);
                    } else {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
     * {@inheritDoc}
     */
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        } else {
            ((Graphics2D) graphics).translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            JComponent c = this.component.getSwingComponent();
            boolean doubleBuffered = c.isDoubleBuffered();
            if (doubleBuffered) {
                c.setDoubleBuffered(false);
            }
            c.paint(graphics);
            if (doubleBuffered) {
                c.setDoubleBuffered(true);
            }
            return PAGE_EXISTS;
        }
    }

}
