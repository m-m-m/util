/* $Id: PrintAction.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swt.feature;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Shell;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF;
import net.sf.mmm.ui.toolkit.api.feature.ActionIF;
import net.sf.mmm.ui.toolkit.base.feature.AbstractAction;
import net.sf.mmm.ui.toolkit.impl.swt.UIComponent;


/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIFactoryIF#createPrintAction(UIComponentIF) print-action}
 * for SWT.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PrintAction extends AbstractAction implements UIActionListenerIF {

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
     * @see net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF#invoke(net.sf.mmm.ui.toolkit.api.UINodeIF, net.sf.mmm.ui.toolkit.api.event.ActionType)
     * {@inheritDoc}
     */
    public void invoke(UINodeIF source, ActionType action) {

        if (action == ActionType.SELECT) {
            Shell shell = this.component.getSyncAccess().getSwtObject().getShell();
            PrintDialog printDialog = new PrintDialog(shell);
            PrinterData printerData = printDialog.open();
            if (printerData != null) {
                //printerData.name = getId();
                if (printerData.printToFile) {
                    //TODO: open filechooser...
                    printerData.fileName = "printing.out";
                }
                Printer printer = new Printer(printerData);
                GC gc = new GC(printer);
                if (printer.startJob(getId())) {
                    printer.startPage();
                    //this.component.getSwtControl().                    
                    printer.endPage();
                    printer.endJob();
                } else {
                    //TODO: problem starting print job...
                }
                gc.dispose();
                printer.dispose();
            }
        }
    }

}
