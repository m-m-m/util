/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.feature;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Shell;

import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.UINodeRenamed;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.event.UIActionListener;
import net.sf.mmm.ui.toolkit.api.feature.Action;
import net.sf.mmm.ui.toolkit.base.feature.AbstractAction;
import net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIFactoryRenamed#createPrintAction(UiElement) print-action}
 * for SWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PrintAction extends AbstractAction implements UIActionListener {

  /** the component to be printed */
  private final AbstractUIComponent component;

  /**
   * The constructor.
   * 
   * @param printComponent is the component to be printed by this action.
   * @param actionName is the {@link Action#getName() name} of the print action.
   * @param jobName is the name of the print job.
   */
  public PrintAction(AbstractUIComponent printComponent, String actionName, String jobName) {

    super(actionName);
    setId(jobName);
    this.component = printComponent;
  }

  /**
   * {@inheritDoc}
   */
  public UIActionListener getActionListener() {

    return this;
  }

  /**
   * {@inheritDoc}
   */
  public void invoke(UINodeRenamed source, ActionType action) {

    if (action == ActionType.SELECT) {
      Shell shell = this.component.getSyncAccess().getSwtObject().getShell();
      PrintDialog printDialog = new PrintDialog(shell);
      PrinterData printerData = printDialog.open();
      if (printerData != null) {
        // printerData.name = getId();
        if (printerData.printToFile) {
          // TODO: open filechooser...
          printerData.fileName = "printing.out";
        }
        Printer printer = new Printer(printerData);
        GC gc = new GC(printer);
        if (printer.startJob(getId())) {
          printer.startPage();
          // this.component.getSwtControl().
          printer.endPage();
          printer.endJob();
        } else {
          // TODO: problem starting print job...
        }
        gc.dispose();
        printer.dispose();
      }
    }
  }

}
