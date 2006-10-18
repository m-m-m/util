/* $Id$ */
package net.sf.mmm.content.validator.base;


import net.sf.mmm.content.validator.api.ValueValidatorIF;
import net.sf.mmm.xml.XmlException;
import net.sf.mmm.xml.api.XmlWriterIF;


/**
 * This is the abstract base implementation of the value validator interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractValueValidator implements ValueValidatorIF {

    /**
     * The constructor.
     */
    public AbstractValueValidator() {

        super();
    }

    /**
     * @see net.sf.mmm.xml.api.XmlSerializableIF#toXml(XmlWriterIF)
     */
    public void toXml(XmlWriterIF xmlWriter) throws XmlException {
        
        xmlWriter.writeStartElement(XML_TAG_VALIDATOR);
        xmlWriter.writeAttribute(XML_ATR_VALIDATOR_TYPE, getClass().toString());
        xmlWriter.writeEndElement(XML_TAG_VALIDATOR);
    }

}
