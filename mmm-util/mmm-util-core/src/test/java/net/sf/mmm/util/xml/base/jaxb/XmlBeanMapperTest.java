/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base.jaxb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

import junit.framework.Assert;
import net.sf.mmm.test.ExceptionHelper;

import org.junit.Test;

/**
 * This is the test-case for {@link XmlBeanMapper}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.1
 */
@SuppressWarnings("all")
public class XmlBeanMapperTest {

  /**
   * Tests {@link XmlBeanMapper#saveXml(Object, java.io.OutputStream)} and
   * {@link XmlBeanMapper#loadXml(java.io.InputStream, Object)}.
   */
  @Test
  public void testSaveAndLoadXml() throws Exception {

    XmlBeanMapper<XmlBean> mapper = new XmlBeanMapper<XmlBeanMapperTest.XmlBean>(XmlBean.class);
    mapper.initialize();
    XmlBean bean = new XmlBean();
    bean.nodes = new XmlNode[2];
    bean.nodes[0] = new XmlNode();
    bean.nodes[0].id = "root";
    bean.nodes[1] = new XmlNode();
    bean.nodes[1].id = "child";
    bean.nodes[1].parent = bean.nodes[0];
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    mapper.saveXml(bean, outputStream);
    byte[] xmlBytes = outputStream.toByteArray();
    // String xml = new String(xmlBytes, "ASCII");
    // System.out.println(xml);
    ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlBytes);
    XmlBean loadedBean = mapper.loadXml(inputStream, "test-case");
    Assert.assertNotNull(loadedBean);
    Assert.assertNotNull(loadedBean.nodes);
    Assert.assertEquals(2, loadedBean.nodes.length);
    Assert.assertNotNull(loadedBean.nodes[0]);
    Assert.assertEquals("root", loadedBean.nodes[0].id);
    Assert.assertNull(loadedBean.nodes[0].parent);
    Assert.assertNotNull(loadedBean.nodes[1]);
    Assert.assertEquals("child", loadedBean.nodes[1].id);
    Assert.assertSame(loadedBean.nodes[0], loadedBean.nodes[1].parent);
  }

  @Test
  public void testLoadXmlWithInvalidIds() {

    String xml = "<?xml version='1.0'?><bean><node id='duplicate-id'/>"
        + "<node id='duplicate-id' parent='undefined-parent'/>"
        + "<node id='root' parent='unknown-parent'/></bean>";
    XmlBeanMapper<XmlBean> mapper = new XmlBeanMapper<XmlBeanMapperTest.XmlBean>(XmlBean.class);
    mapper.initialize();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes());
    try {
      XmlBean loadedBean = mapper.loadXml(inputStream, "test-case");
      ExceptionHelper.failExceptionExpected();
    } catch (Exception e) {
      String message = e.getMessage();
      Assert.assertTrue(message.contains("duplicate-id"));
      Assert.assertTrue(message.contains("undefined-parent"));
      Assert.assertTrue(message.contains("unknown-parent"));
    }
  }

  @XmlRootElement(name = "bean")
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class XmlBean {

    @XmlElement(name = "node")
    private XmlNode[] nodes;
  }

  public static class XmlNode {

    @XmlID
    @XmlAttribute(name = "id")
    private String id;

    @XmlIDREF
    @XmlAttribute(name = "parent")
    private XmlNode parent;

  }

}
