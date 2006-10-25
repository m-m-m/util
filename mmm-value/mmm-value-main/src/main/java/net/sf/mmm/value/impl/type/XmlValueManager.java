/* $ Id: $ */
package net.sf.mmm.value.impl.type;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import net.sf.mmm.xml.DomUtil;
import net.sf.mmm.value.api.ValueManagerIF;
import net.sf.mmm.value.api.ValueParseException;
import net.sf.mmm.value.api.ValueParseStringException;
import net.sf.mmm.value.base.AbstractValueManager;
import net.sf.mmm.xml.api.XmlException;
import net.sf.mmm.xml.api.XmlWriterIF;

/**
 * This is the {@link ValueManagerIF manager} for
 * {@link org.w3c.dom.Document xml} values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlValueManager extends AbstractValueManager<Element> {

  /** the {@link ValueManagerIF#getName() "logical name"} of the managed value */
  public static final String VALUE_NAME = "Xml";

  /** the {@link ValueManagerIF#getValueType() type} of the managed value */
  private static final Class<Element> VALUE_TYPE = Element.class;

  /**
   * The constructor.
   */
  public XmlValueManager() {

    super();
  }

  /**
   * @see net.sf.mmm.value.api.ValueManagerIF#getValueType()
   * 
   */
  public Class<Element> getValueType() {

    return VALUE_TYPE;
  }

  /**
   * @see net.sf.mmm.value.api.ValueManagerIF#parse(java.lang.String)
   * 
   */
  public Element parse(String valueAsString) throws ValueParseException {

    try {
      // TODO: this may cause encoding problems...
      return DomUtil.parseDocument(new InputSource(new StringReader(valueAsString)))
          .getDocumentElement();
    } catch (XmlException e) {
      throw new ValueParseStringException(valueAsString, VALUE_TYPE, VALUE_NAME, e);
    }
  }

  /**
   * @see net.sf.mmm.value.api.ValueManagerIF#parse(org.w3c.dom.Element)
   * 
   */
  @Override
  public Element parse(Element valueAsXml) throws ValueParseException {

    checkXml(valueAsXml);
    Element childElement = DomUtil.getFirstElement(valueAsXml.getChildNodes());
    // create deep-copy in new XML document...
    Document copyDocument = DomUtil.createDocument();
    return (Element) copyDocument.importNode(childElement, true);
  }

  /**
   * @see net.sf.mmm.value.api.ValueManagerIF#toString(Object)
   * 
   */
  @Override
  public String toString(Element value) {

    try {
      Writer writer = new StringWriter();
      DomUtil.writeXml(value, writer, false);
      return writer.toString();
    } catch (XmlException e) {
      return "toString failed for " + value;
    }
  }

  /**
   * @see AbstractValueManager#toXmlValue(XmlWriterIF, Object)
   * 
   */
  @Override
  protected void toXmlValue(XmlWriterIF xmlWriter, Element value) throws XmlException {

    DomUtil.toXml(xmlWriter, value);
  }

  /**
   * @see net.sf.mmm.value.api.ValueManagerIF#getName()
   * 
   */
  public String getName() {

    return VALUE_NAME;
  }

}
