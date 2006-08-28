/* $Id$ */
package net.sf.mmm.xml.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import net.sf.mmm.xml.NlsResourceBundle;
import net.sf.mmm.xml.XmlUtil;
import net.sf.mmm.xml.api.XmlException;
import net.sf.mmm.xml.base.AbstractXmlWriter;

/**
 * This is an implementation of the {@link net.sf.mmm.xml.api.XmlWriterIF}
 * interface that writes the produced XML directly to an output stream or a
 * writer.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class OutputXmlWriter extends AbstractXmlWriter {

    /** the writer where to write the xml to */
    private final PrintWriter printWriter;

    /** a view on the writer that escapes characters reserved in XML */
    private final Writer escapingWriter;

    /** this flag tells if the current open element is completed ('>' written) */
    private boolean openElementCompleted;

    /** the characters used for indentation or <code>null</code> for no indent */
    private final String indent;

    /** the current indent or <code>null</code> for no indent */
    private String currentIndent;

    /**
     * The constructor.
     * 
     * @param writer
     *        is the writer where the xml is written to.
     * @param indentation
     *        is the string used for a single indentation step or
     *        <code>null</code> for no indentation (unformatted single line
     *        output). Typical indentations are <code>" "</code>,
     *        <code>"  "</code>, or <code>"\t"</code>.
     */
    public OutputXmlWriter(Writer writer, String indentation) {

        super();
        PrintWriter pw;
        if (writer instanceof PrintWriter) {
            pw = (PrintWriter) writer;
        } else {
            pw = new PrintWriter(writer);
        }
        this.printWriter = pw;
        this.openElementCompleted = true;
        this.indent = indentation;
        this.currentIndent = null;
        this.escapingWriter = XmlUtil.createXmlAttributeWriter(writer);
    }

    /**
     * This constructor initially writes an XML header.
     * 
     * @param writer
     *        is the writer where the xml is written to.
     * @param indentation
     *        is the string used for a single indentation step or
     *        <code>null</code> for no indentation (unformatted single line
     *        output). Typical indentations are <code>" "</code>,
     *        <code>"  "</code>, or <code>"\t"</code>.
     * @param encoding
     *        is the encoding written to the xml header.
     */
    public OutputXmlWriter(Writer writer, String indentation, String encoding) {

        this(writer, indentation);
        writeXmlHeader("1.0", encoding, null);
    }

    /**
     * The constructor. It will write an XML header containing the given
     * encoding.
     * 
     * @param writer
     *        is the writer where the xml is written to. This writer must ensure
     *        that the specified encoding is used.
     * @param indentation
     *        is the string used for a single indentation step or
     *        <code>null</code> for no indentation (unformatted single line
     *        output). Typical indentations are <code>" "</code>,
     *        <code>"  "</code>, or <code>"\t"</code>.
     * @param encoding
     *        is the encoding written to the xml header.
     * @param xmlVersion
     *        is the explicit xml version written to the xml header. Legal
     *        values are "1.0" or "1.1".
     * @param standalone
     *        the value of the standalone flag.
     */
    public OutputXmlWriter(Writer writer, String indentation, String encoding, String xmlVersion,
            boolean standalone) {

        this(writer, indentation);
        this.writeXmlHeader(xmlVersion, encoding, Boolean.valueOf(standalone));
    }

    /**
     * The constructor.
     * 
     * @param outputStream
     *        is the output stream where the xml is written to.
     * @param indentation
     *        is the string used for a single indentation step or
     *        <code>null</code> for no indentation (unformatted single line
     *        output). Typical indentations are " ", " ", " ", or "\t".
     */
    public OutputXmlWriter(OutputStream outputStream, String indentation) {

        this(new PrintWriter(outputStream), indentation);
    }

    /**
     * The constructor. It will produce an XML header containing the given
     * encoding.
     * 
     * @param outputStream
     *        is the output stream where the xml is written to.
     * @param indentation
     *        is the string used for a single indentation step or
     *        <code>null</code> for no indentation (unformatted single line
     *        output). Typical indentations are " ", " ", " ", or "\t".
     * @param encoding
     *        is the eoncoding used for writing and is also set as encoding in
     *        the xml header.
     * @throws UnsupportedEncodingException
     *         if the given encoding is not supported.
     */
    public OutputXmlWriter(OutputStream outputStream, String indentation, String encoding)
            throws UnsupportedEncodingException {

        this(new OutputStreamWriter(outputStream, encoding), indentation, encoding);
    }

    /**
     * The constructor. It will produce an XML header containing the given
     * encoding and the specified xml version and standalone flag.
     * 
     * @param outputStream
     *        is the output stream where the xml is written to.
     * @param indentation
     *        is the string used for a single indentation step or
     *        <code>null</code> for no indentation (unformatted single line
     *        output). Typical indentations are " ", " ", " ", or "\t".
     * @param encoding
     *        is the charsetname used to encode special charaters. It will also
     *        be written to the encoding attribute of the xml header.
     * @param xmlVersion
     *        is the explicit xml version written to the xml header. Legal
     *        values are "1.0" or "1.1".
     * @param standalone
     *        is a flag telling if the XML document comes standalone.
     * @throws UnsupportedEncodingException
     *         if the given encoding is not supported.
     */
    public OutputXmlWriter(OutputStream outputStream, String indentation, String encoding,
            String xmlVersion, boolean standalone) throws UnsupportedEncodingException {

        this(new OutputStreamWriter(outputStream, encoding), indentation, encoding, xmlVersion,
                standalone);
    }

    /**
     * This method writes the XML header.
     * 
     * @param xmlVersion
     *        is the explicit xml version written to the xml header. Legal
     *        values are "1.0" or "1.1".
     * @param encoding
     *        is the encoding written to the xml header. A value of
     *        <code>null</code> is allowed and will cause the encoding
     *        attribute be omitted in the header.
     * @param standalone
     *        is the flag telling if the xml comes standalone or depends on
     *        TODO. A value of <code>null</code> is allowed and will cause the
     *        standalone attribute be omitted in the header.
     */
    protected void writeXmlHeader(String xmlVersion, String encoding, Boolean standalone) {

        this.printWriter.print("<?xml version=\"");
        this.printWriter.print(xmlVersion);
        if (encoding != null) {
            this.printWriter.print("\" encoding=\"");
            this.printWriter.print(encoding);
        }
        if (standalone != null) {
            this.printWriter.print("\" standalone=\"");
            this.printWriter.print(standalone);
        }
        this.printWriter.print("\"?>");
        if (this.indent != null) {
            this.printWriter.println();
        }
    }

    /**
     * This method closes the current element if it is still open.
     * 
     * @throws XmlException
     *         if the serialization produced an I/O error.
     */
    protected void completeOpenElement() throws XmlException {

        if (!this.openElementCompleted) {
            this.printWriter.write('>');
            if (this.currentIndent != null) {
                this.printWriter.println();
                this.printWriter.write(this.currentIndent);
            }
            this.openElementCompleted = true;
        }
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeStartElement(java.lang.String,
     *      java.lang.String, java.lang.String)
     * {@inheritDoc}
     */
    public void writeStartElement(String localName, String namespacePrefix, String namespaceUri)
            throws XmlException {

        completeOpenElement();
        if (this.indent != null) {
            if (this.currentIndent == null) {
                this.currentIndent = "";
            }
            this.currentIndent += this.indent;
        }
        this.printWriter.write('<');
        if (namespacePrefix != null) {
            this.printWriter.write(namespacePrefix);
            this.printWriter.write(':');
        }
        this.printWriter.write(localName);
        if (namespaceUri != null) {
            this.printWriter.write(" xmlns");
            if (namespacePrefix != null) {
                this.printWriter.write(':');
                this.printWriter.write(namespacePrefix);
            }
            this.printWriter.write("=\"");
            this.printWriter.write(namespaceUri);
            this.printWriter.write('"');
        }
        this.openElementCompleted = false;
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeAttribute(java.lang.String,
     *      java.lang.String, java.lang.String)
     * {@inheritDoc}
     */
    public void writeAttribute(String localName, String value, String namespacePrefix)
            throws XmlException {

        try {
            if (this.openElementCompleted) {
                throw new XmlException(NlsResourceBundle.ERR_INVALID_XML);
            }
            this.printWriter.write(' ');
            if (namespacePrefix != null) {
                this.printWriter.write(namespacePrefix);
                this.printWriter.write(':');
            }
            this.printWriter.write(localName);
            this.printWriter.write("=\"");
            this.escapingWriter.write(value);
            this.printWriter.write('"');
        } catch (IOException e) {
            throw new XmlIOException(e);
        }
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeNamespaceDeclaration(java.lang.String,
     *      java.lang.String)
     * {@inheritDoc}
     */
    public void writeNamespaceDeclaration(String namespacePrefix, String namespaceUri)
            throws XmlException {

        if (namespacePrefix == null) {
            writeAttribute(XmlUtil.NAMESPACE_PREFIX_XMLNS, namespaceUri);
        } else {
            writeAttribute(namespacePrefix, namespaceUri, XmlUtil.NAMESPACE_PREFIX_XMLNS);
        }
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeEndElement(java.lang.String,
     *      java.lang.String)
     * {@inheritDoc}
     */
    public void writeEndElement(String localName, String namespacePrefix) throws XmlException {

        if (this.currentIndent != null) {
            int len = this.currentIndent.length() - this.indent.length();
            if (len < 0) {
                throw new XmlException("More closing tags than opening tags!");
            }
            this.currentIndent = this.currentIndent.substring(0, len);
        }
        if (this.openElementCompleted) {
            if (this.currentIndent != null) {
                this.printWriter.println();
                this.printWriter.write(this.currentIndent);
            }
            this.printWriter.write("</");
            if (namespacePrefix != null) {
                this.printWriter.write(namespacePrefix);
                this.printWriter.write(':');
            }
            this.printWriter.write(localName);
            this.printWriter.write('>');
        } else {
            this.printWriter.write("/>");
            this.openElementCompleted = true;
        }
        this.printWriter.flush();
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeCharacters(java.lang.String)
     * {@inheritDoc}
     */
    public void writeCharacters(String text) throws XmlException {

        try {
            completeOpenElement();
            this.escapingWriter.write(text);
        } catch (IOException e) {
            // TODO:
            throw new XmlException("", e);
        }
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeCData(java.lang.String)
     * {@inheritDoc}
     */
    public void writeCData(String text) throws XmlException {

        completeOpenElement();
        this.printWriter.write("<![CDATA[");
        if (text.contains("]]>")) {
            text = text.replaceAll("]]>", "]]&gt;");
        }
        this.printWriter.write(text);            
        this.printWriter.write("]]>");
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeComment(java.lang.String)
     * {@inheritDoc}
     */
    public void writeComment(String comment) throws XmlException {

        completeOpenElement();
        // TODO encoding...
        this.printWriter.write("<!--");
        this.printWriter.write(comment);
        this.printWriter.write("-->");
        // indent
        if (this.currentIndent != null) {
            this.printWriter.println();
            this.printWriter.write(this.currentIndent);
        }
    }

}