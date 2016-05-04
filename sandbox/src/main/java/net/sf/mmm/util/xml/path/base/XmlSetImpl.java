/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.path.base;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.NamespaceContext;

import net.sf.mmm.util.xml.path.api.XmlSet;

/**
 * This is the implementation of the {@link XmlSet} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlSetImpl extends AbstractXmlSelector implements XmlSet {

  private final Conjunction conjunction;

  private final List<AbstractXmlSelector> selectorList;

  /**
   * The constructor.
   *
   * @param namespaceContext is the {@link #getNamespaceContext() namespace-context}.
   * @param conjunction is the {@link #getConjunction() conjunction}.
   */
  public XmlSetImpl(NamespaceContext namespaceContext, Conjunction conjunction) {

    super(namespaceContext);
    assert (conjunction != null);
    this.conjunction = conjunction;
    this.selectorList = new ArrayList<AbstractXmlSelector>();
  }

  @Override
  public Conjunction getConjunction() {

    return this.conjunction;
  }

  @Override
  public AbstractXmlSelector getSelector(int index) {

    return this.selectorList.get(index);
  }

  @Override
  public int getSelectorCount() {

    return this.selectorList.size();
  }

  /**
   * This method adds the given {@code selector} to this {@link XmlSet}.
   *
   * @param selector is the selector to add.
   */
  public void addSelector(AbstractXmlSelector selector) {

    this.selectorList.add(selector);
  }

  @Override
  public void toString(StringBuilder stringBuilder) {

    String conjunctionString;
    switch (this.conjunction) {
      case UNION:
        conjunctionString = " | ";
        break;
      case INTERSECTION:
        conjunctionString = " & ";
        break;
      default:
        // this is actually an internal error!
        conjunctionString = " ?!? ";
        break;
    }
    boolean first = true;
    for (AbstractXmlSelector selector : this.selectorList) {
      if (first) {
        first = false;
      } else {
        stringBuilder.append(conjunctionString);
      }
      selector.toString(stringBuilder);
    }
  }

}
