/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * This is the test-case for {@link EnglishSingularizer}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class EnglishSingularizerTest {

  private void check(String singular, String plural) {

    assertEquals(singular, EnglishSingularizer.INSTANCE.transform(plural));
    assertEquals(singular, EnglishSingularizer.INSTANCE.transform(singular));
  }

  @Test
  public void testCharacterCase() {

    check("child", "children");
    check("CHILD", "CHILDREN");
    check("MoonChild", "MoonChildren");
  }

  @Test
  public void testSpecials() {

    check("man", "men");
    check("automaton", "automata");
    check("axis", "axes");
    check("blues", "blues");
    check("canoe", "canoes");
    check("child", "children");
    check("crisis", "crises");
    check("criterion", "criteria");
    check("die", "dice");
    check("foot", "feet");
    check("goose", "geese");
    check("louse", "lice");
    check("medium", "media");
    check("memorandum", "memoranda");
    check("man", "men");
    check("mouse", "mice");
    check("person", "people");
    check("phenomenon", "phenomena");
    check("polyhedron", "polyhedra");
    check("staff", "staves");
    check("tooth", "teeth");
    check("testis", "testes");
    check("woman", "women");
  }

  @Test
  public void testRegular() {

    check("bikini", "bikinis");
    check("kid", "kids");
    check("teddy", "teddies");
    check("house", "houses");
    check("garden", "gardens");
    check("mass", "masses");
    check("class", "classes");
    check("interface", "interfaces");
    check("type", "types");
  }

  @Test
  public void testSingular() {

    check("darkness", "darkness");
    check("mass", "mass");
    check("tennis", "tennis");
  }

}
