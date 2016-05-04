/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transformer.base;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;
import net.sf.mmm.util.resource.base.ClasspathResource;

/**
 * This is the {@link TestCase} for {@link StringTransformerChainXmlParser}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class StringTransformerChainTest {

  @Test
  public void testUnmarshall() throws Exception {

    JAXBContext context = JAXBContext.newInstance(StringTransformerList.class);
    try (InputStream inStream = new ClasspathResource(StringTransformerList.class, ".xml", true).openStream()) {
      StringTransformerList chainList = (StringTransformerList) context.createUnmarshaller().unmarshal(inStream);
      Map<String, StringTransformerChain> chainMap = new HashMap<String, StringTransformerChain>();
      for (StringTransformerChain chain : chainList.getTransformers()) {
        chainMap.put(chain.getId(), chain);
      }
      StringTransformerChain defaultChain = chainMap.get("default-transformer");
      Assert.assertNotNull(defaultChain);
      Assert.assertEquals("/readme", defaultChain.transform("/readme.txt"));
      Assert.assertEquals("/readme.xml", defaultChain.transform("/readme.xml"));
      Assert.assertEquals("/trash", defaultChain.transform("/trash"));
      StringTransformerChain extendedChain = chainMap.get("extended-transformer");
      Assert.assertNotNull(extendedChain);
      Assert.assertEquals("/garbarge/foo", extendedChain.transform("/trash/foo"));
      Assert.assertEquals("/dummy/dummy/dummy", extendedChain.transform("/foobar/foo/bar"));
    }
  }
}
