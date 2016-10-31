/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.lang;

import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * This is the test of {@link StringListProperty}.
 *
 * @author hohwille
 */
public class StringListPropertyTest extends Assertions {

  /** Test of {@link StringListProperty#contains(String)}. */
  @Test
  public void testContains() {

    StringListProperty property = new StringListProperty("stringList", null);
    property.set("|a|bc|d|");
    assertThat(property.contains("a")).isTrue();
    assertThat(property.contains("b")).isFalse();
    assertThat(property.contains("bc")).isTrue();
    assertThat(property.contains("d")).isTrue();
    assertThat(property.contains("e")).isFalse();
  }

  /** Test of {@link StringListProperty#getValueAsList()}. */
  @Test
  public void testGetValueAsList() {

    StringListProperty property = new StringListProperty("stringList", null);
    property.set("|a|bc|d|");
    assertThat(property.getValueAsList()).containsExactly("a", "bc", "d");
  }

  /** Test of {@link StringListProperty#setValueAsCollection(java.util.Collection)}. */
  @Test
  public void testSetValueAsCollection() {

    StringListProperty property = new StringListProperty("stringList", null);
    property.setValueAsCollection(Arrays.asList("a", "bc", "d"));
    assertThat(property.getValue()).isEqualTo("|a|bc|d|");
  }

  /** Test of {@link StringListProperty#getValueAsCsv(String)}. */
  @Test
  public void testGetValueAsCsv() {

    StringListProperty property = new StringListProperty("stringList", null);
    property.set("|a|bc|d|");
    assertThat(property.getValueAsCsv(", ")).isEqualTo("a, bc, d");
  }

  /** Test of {@link StringListProperty#setValueAsCsv(String, char, boolean)}. */
  @Test
  public void testSetValueAsCsv() {

    StringListProperty property = new StringListProperty("stringList", null);
    property.setValueAsCsv("a, bc, d");
    assertThat(property.getValue()).isEqualTo("|a|bc|d|");
    property.setValueAsCsv("a, bc, d", ',', false);
    assertThat(property.getValue()).isEqualTo("|a| bc| d|");
  }

  /** Test of {@link StringListProperty#add(String, boolean)}. */
  @Test
  public void testAdd() {

    StringListProperty property = new StringListProperty("stringList", null);
    assertThat(property.add("a", true)).isTrue();
    assertThat(property.add("bc", true)).isTrue();
    assertThat(property.add("d", true)).isTrue();
    assertThat(property.getValue()).isEqualTo("|a|bc|d|");
    assertThat(property.add("a", true)).isFalse();
    assertThat(property.add("b", true)).isTrue();
    assertThat(property.getValue()).isEqualTo("|a|bc|d|b|");
    assertThat(property.add("a", false)).isTrue();
    assertThat(property.getValue()).isEqualTo("|a|bc|d|b|a|");
  }

  /** Test of {@link StringListProperty#remove(String)}. */
  @Test
  public void testRemove() {

    StringListProperty property = new StringListProperty("stringList", null);
    property.set("|a|bc|d|");
    assertThat(property.remove("b")).isFalse();
    assertThat(property.getValue()).isEqualTo("|a|bc|d|");
    assertThat(property.remove("a")).isTrue();
    assertThat(property.getValue()).isEqualTo("|bc|d|");
    assertThat(property.remove("d")).isTrue();
    assertThat(property.getValue()).isEqualTo("|bc|");
    assertThat(property.remove("e")).isFalse();
    assertThat(property.remove("bc")).isTrue();
    assertThat(property.getValue()).isEmpty();
  }

}
