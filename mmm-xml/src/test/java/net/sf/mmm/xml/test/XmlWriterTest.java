/* $ Id: $ */
package net.sf.mmm.xml.test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import net.sf.mmm.xml.XmlUtil;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class XmlWriterTest {

  /**
   * The constructor.
   * 
   */
  public XmlWriterTest() {

    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @param args
   */
  public static void main(String[] args) throws IOException {

    Writer w = XmlUtil.createXmlAttributeWriter(new OutputStreamWriter(System.out));
    w.write("<&>abc\"def<ghi>");
    w.flush();
  }

}
