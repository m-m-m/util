/*
 * Copyright (c) 2008-2012, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.time.format;

import static java.time.calendrical.ChronoField.DAY_OF_MONTH;
import static java.time.calendrical.ChronoField.MONTH_OF_YEAR;
import static java.time.calendrical.ChronoField.YEAR;
import static org.testng.Assert.assertEquals;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.calendrical.DateTimeBuilder;
import java.time.calendrical.DateTimeField;
import java.time.format.DateTimeFormatSymbols;
import java.time.format.DateTimeParseContext;
import java.util.Locale;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test DateTimeParseContext.
 */
@Test
public class TCKDateTimeParseContext {

  private DateTimeFormatSymbols symbols;

  private DateTimeParseContext context;

  @BeforeMethod(groups = { "tck" })
  public void setUp() {

    this.symbols = DateTimeFormatSymbols.of(Locale.GERMANY);
    this.context = new DateTimeParseContext(Locale.GERMANY, this.symbols);
  }

  // -----------------------------------------------------------------------
  @Test(groups = { "tck" })
  public void test_constructor() throws Exception {

    assertEquals(this.context.getSymbols(), this.symbols);
    assertEquals(this.context.getLocale(), Locale.GERMANY);
    assertEquals(this.context.getParsed().size(), 0);
  }

  @Test(expectedExceptions = NullPointerException.class, groups = { "tck" })
  public void test_constructor_nullLocale() throws Exception {

    new DateTimeParseContext(null, DateTimeFormatSymbols.STANDARD);
  }

  @Test(expectedExceptions = NullPointerException.class, groups = { "tck" })
  public void test_constructor_nullSymbols() throws Exception {

    new DateTimeParseContext(Locale.GERMANY, null);
  }

  // -----------------------------------------------------------------------
  @Test(groups = { "tck" })
  public void test_caseSensitive() throws Exception {

    assertEquals(this.context.isCaseSensitive(), true);
    this.context.setCaseSensitive(false);
    assertEquals(this.context.isCaseSensitive(), false);
  }

  // -----------------------------------------------------------------------
  @Test(groups = { "tck" })
  public void test_subSequenceEquals() throws Exception {

    assertEquals(this.context.subSequenceEquals("ABBA", 0, "abba", 0, 4), false);
    assertEquals(this.context.subSequenceEquals("ABBA", 0, "ABBA", 1, 3), false);
    assertEquals(this.context.subSequenceEquals("ABBA", 0, "AB", 0, 4), false);
    assertEquals(this.context.subSequenceEquals("AB", 0, "ABBA", 0, 4), false);

    this.context.setCaseSensitive(false);
    assertEquals(this.context.subSequenceEquals("ABBA", 0, "abba", 0, 4), true);
    assertEquals(this.context.subSequenceEquals("ABBA", 0, "abba", 1, 3), false);
    assertEquals(this.context.subSequenceEquals("ABBA", 0, "ab", 0, 4), false);
    assertEquals(this.context.subSequenceEquals("AB", 0, "abba", 0, 4), false);
  }

  // -----------------------------------------------------------------------
  @Test(groups = { "tck" })
  public void test_strict() throws Exception {

    assertEquals(this.context.isStrict(), true);
    this.context.setStrict(false);
    assertEquals(this.context.isStrict(), false);
  }

  // -----------------------------------------------------------------------
  @Test(groups = { "tck" })
  public void test_getParsed_DateTimeRule_null() throws Exception {

    assertEquals(this.context.getParsed((DateTimeField) null), null);
  }

  @Test(groups = { "tck" })
  public void test_getParsed_DateTimeRule_notPresent() throws Exception {

    assertEquals(this.context.getParsed(DAY_OF_MONTH), null);
  }

  // -------------------------------------------------------------------------
  @Test(groups = { "tck" })
  public void test_getParsed_Class_null() throws Exception {

    assertEquals(this.context.getParsed((Class<?>) null), null);
  }

  @Test(groups = { "tck" })
  public void test_getParsed_Class_notPresent() throws Exception {

    assertEquals(this.context.getParsed(LocalDate.class), null);
  }

  // -----------------------------------------------------------------------
  @Test(groups = { "tck" })
  public void test_setParsed() throws Exception {

    assertEquals(this.context.getParsed(LocalDate.class), null);

    this.context.setParsed(LocalDate.of(2010, 6, 30));
    assertEquals(this.context.getParsed(LocalDate.class), LocalDate.of(2010, 6, 30));

    this.context.setParsed(LocalDate.of(2010, 9, 23));
    assertEquals(this.context.getParsed(LocalDate.class), LocalDate.of(2010, 6, 30)); // first chosen
  }

  @Test(expectedExceptions = NullPointerException.class, groups = { "tck" })
  public void test_setParsed_null() throws Exception {

    this.context.setParsed(null);
  }

  // -------------------------------------------------------------------------
  @Test(groups = { "tck" })
  public void test_setParsedField() throws Exception {

    assertEquals(this.context.getParsed(YEAR), null);
    assertEquals(this.context.getParsed(MONTH_OF_YEAR), null);

    this.context.setParsedField(YEAR, 2008);
    assertEquals(this.context.getParsed(YEAR), Long.valueOf(2008));
    assertEquals(this.context.getParsed(MONTH_OF_YEAR), null);

    this.context.setParsedField(MONTH_OF_YEAR, 6);
    assertEquals(this.context.getParsed(YEAR), Long.valueOf(2008));
    assertEquals(this.context.getParsed(MONTH_OF_YEAR), Long.valueOf(6));

    this.context.setParsedField(YEAR, 2000);
    assertEquals(this.context.getParsed(YEAR), Long.valueOf(2008)); // first chosen
    assertEquals(this.context.getParsed(MONTH_OF_YEAR), Long.valueOf(6));
  }

  @Test(expectedExceptions = NullPointerException.class, groups = { "tck" })
  public void test_setParsedField_null() throws Exception {

    this.context.setParsedField(null, 2008);
  }

  // -------------------------------------------------------------------------
  @Test(groups = { "tck" })
  public void test_toBuilder() throws Exception {

    this.context.setParsedField(YEAR, 2008);
    this.context.setParsedField(MONTH_OF_YEAR, 6);

    DateTimeBuilder builder = this.context.toBuilder();
    Map<DateTimeField, Long> fields = builder.getFieldValueMap();
    assertEquals(fields.size(), 2);
    assertEquals(fields.get(YEAR), Long.valueOf(2008));
    assertEquals(fields.get(MONTH_OF_YEAR), Long.valueOf(6));
  }

  // -----------------------------------------------------------------------
  @Test(groups = { "tck" })
  public void test_toString() throws Exception {

    this.context.setParsedField(YEAR, 2008);
    this.context.setParsedField(MONTH_OF_YEAR, 6);
    this.context.setParsed(ZoneOffset.ofHours(16));

    String str = this.context.toString();
    assertEquals(str.contains("MonthOfYear 6"), true);
    assertEquals(str.contains("Year 2008"), true);
    assertEquals(str.contains("+16:00"), true);
  }

}
