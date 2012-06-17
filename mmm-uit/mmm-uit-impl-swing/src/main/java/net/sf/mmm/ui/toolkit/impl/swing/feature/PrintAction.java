/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.feature;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JComponent;

import net.sf.mmm.ui.toolkit.api.common.MessageType;
import net.sf.mmm.ui.toolkit.api.event.UiEventListener;
import net.sf.mmm.ui.toolkit.api.event.UiEventType;
import net.sf.mmm.ui.toolkit.api.feature.UiAction;
import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.api.view.window.UiWindow;
import net.sf.mmm.ui.toolkit.base.feature.AbstractAction;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UiFactory#createPrintUiAction(UiElement)
 * print-action} for SWING.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PrintAction extends AbstractAction implements UiEventListener, Printable {

  /** the component to be printed */
  private final AbstractUiElement<JComponent> component;

  /**
   * The constructor.
   * 
   * @param printComponent is the component to be printed by this action.
   * @param actionName is the {@link UiAction#getName() name} of the print
   *        action.
   * @param jobName is the name of the print job.
   */
  public PrintAction(AbstractUiElement<JComponent> printComponent, String actionName, String jobName) {

    super(actionName);
    setId(jobName);
    this.component = printComponent;

  }

  /**
   * {@inheritDoc}
   */
  public UiEventListener getActionListener() {

    return this;
  }

  /**
   * {@inheritDoc}
   */
  public void onEvent(UiNode source, UiEventType action) {

    if (action == UiEventType.CLICK) {
      PrinterJob job = PrinterJob.getPrinterJob();
      job.setJobName(getId());
      job.setPrintable(this);
      boolean doPrint = job.printDialog();
      if (doPrint) {
        System.out.println("Printing...");
        try {
          job.print();
        } catch (PrinterException e) {
          UiWindow window = this.component.getParentWindow();
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
   * {@inheritDoc}
   */
  public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

    if (pageIndex > 0) {
      return NO_SUCH_PAGE;
    } else {
      ((Graphics2D) graphics).translate(pageFormat.getImageableX(), pageFormat.getImageableY());
      JComponent c = this.component.getAdapter().getDelegate();
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
