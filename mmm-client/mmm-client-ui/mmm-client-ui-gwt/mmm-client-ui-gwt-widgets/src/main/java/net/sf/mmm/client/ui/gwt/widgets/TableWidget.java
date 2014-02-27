/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteCaption;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteSummary;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;

/**
 * A {@link TableWidget} is a {@link CustomPanel} that represents a table ({@literal <table>}). You can
 * {@link #add(com.google.gwt.user.client.ui.Widget)} {@link TableRow rows} to it. It is an alternative to
 * GWTs {@link com.google.gwt.user.client.ui.HTMLTable} or {@link com.google.gwt.user.client.ui.Grid}.
 * 
 * @see #getTableBody()
 * @see #getTableHeader()
 * @see #getTableFooter()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class TableWidget extends ComplexPanel implements AttributeWriteSummary, AttributeWriteCaption {

  /** @see #getTableHeader() */
  private final TableHead tableHeader;

  /** @see #getTableBody() */
  private final TableBody tableBody;

  /** @see #getTableFooter() */
  private final TableFooter tableFooter;

  /** @see #getCaptionWidget() */
  private Caption captionWidget;

  /**
   * The constructor.
   */
  public TableWidget() {

    super();
    TableElement tableElement = Document.get().createTableElement();
    tableElement.setCellSpacing(0);
    setElement(tableElement);

    // this statement appears strange but GWT API has two elements and we need conversion
    Element element = getElement();

    // table header
    this.tableHeader = new TableHead();
    add(this.tableHeader, element);

    // table body
    this.tableBody = new TableBody();
    add(this.tableBody, element);

    // table footer
    this.tableFooter = new TableFooter();
    add(this.tableFooter, element);

  }

  /**
   * @return the table body. Add your content here using {@link TableRow}s.
   */
  public TableBody getTableBody() {

    return this.tableBody;
  }

  /**
   * @return the table header. Add your header information here using one or multiple {@link TableRow}s.
   */
  public TableHead getTableHeader() {

    return this.tableHeader;
  }

  /**
   * @return the table footer. Here you can add footer information (e.g. summaries) using one or multiple
   *         {@link TableRow}s.
   */
  public TableFooter getTableFooter() {

    return this.tableFooter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSummary() {

    return getElement().getAttribute(PROPERTY_SUMMARY.getSegment());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSummary(String summary) {

    if (summary == null) {
      getElement().removeAttribute(PROPERTY_SUMMARY.getSegment());
    } else {
      getElement().setAttribute(PROPERTY_SUMMARY.getSegment(), summary);
    }
  }

  /**
   * @return the {@link Caption} widget.
   */
  public Caption getCaptionWidget() {

    if (this.captionWidget == null) {
      this.captionWidget = new Caption();
      insert(this.captionWidget, getElement(), 0, true);
    }
    return this.captionWidget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCaption() {

    if (this.captionWidget == null) {
      return null;
    }
    return this.captionWidget.getText();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCaption(String caption) {

    getCaptionWidget().setText(caption);
  }

}
