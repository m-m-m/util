/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.path.base;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.NamespaceContext;

import net.sf.mmm.util.xml.path.api.XmlPath;

/**
 * This is the implementation of the {@link XmlPath} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlPathImpl extends AbstractXmlSelector implements XmlPath {

  /** @see #getSegment(int) */
  private final List<AbstractXmlPathSegment> segmentList;

  /**
   * The constructor.
   * 
   * @param namespaceContext is the
   *        {@link #getNamespaceContext() namespace-context}.
   */
  public XmlPathImpl(NamespaceContext namespaceContext) {

    super(namespaceContext);
    this.segmentList = new ArrayList<AbstractXmlPathSegment>();
  }

  /**
   * {@inheritDoc}
   */
  public AbstractXmlPathSegment getSegment(int index) {

    return this.segmentList.get(index);
  }

  /**
   * {@inheritDoc}
   */
  public int getSegmentCount() {

    return this.segmentList.size();
  }

  /**
   * This method adds the given <code>segment</code> to this path.
   * 
   * @param segment is the segment to add.
   */
  public void addSegment(AbstractXmlPathSegment segment) {

    this.segmentList.add(segment);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void toString(StringBuilder stringBuilder) {

    boolean first = true;
    for (AbstractXmlPathSegment segment : this.segmentList) {
      if (first) {
        first = false;
      } else {
      //  stringBuilder.append('/');
      }
      segment.toString(stringBuilder);
    }
  }

}
