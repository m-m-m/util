/*
 * Copyright (c) 2011-2012, Stephen Colebourne & Michael Nascimento Santos
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

import static java.time.calendrical.ChronoField.AMPM_OF_DAY;
import static java.time.calendrical.ChronoField.DAY_OF_WEEK;
import static java.time.calendrical.ChronoField.MONTH_OF_YEAR;
import static org.testng.Assert.assertEquals;

import java.time.calendrical.DateTimeField;
import java.time.format.DateTimeFormatters;
import java.time.format.DateTimeTextProvider;
import java.time.format.TextStyle;
import java.util.Locale;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test SimpleDateTimeTextProvider.
 */
@Test
public class TCKSimpleDateTimeTextProvider {

  Locale enUS = new Locale("en", "US");

  Locale ptBR = new Locale("pt", "BR");

  @BeforeMethod
  public void setUp() {

  }

  // -----------------------------------------------------------------------
  @DataProvider(name = "Text")
  Object[][] data_text() {

    return new Object[][] { { DAY_OF_WEEK, 1, TextStyle.SHORT, this.enUS, "Mon" },
        { DAY_OF_WEEK, 2, TextStyle.SHORT, this.enUS, "Tue" }, { DAY_OF_WEEK, 3, TextStyle.SHORT, this.enUS, "Wed" },
        { DAY_OF_WEEK, 4, TextStyle.SHORT, this.enUS, "Thu" }, { DAY_OF_WEEK, 5, TextStyle.SHORT, this.enUS, "Fri" },
        { DAY_OF_WEEK, 6, TextStyle.SHORT, this.enUS, "Sat" }, { DAY_OF_WEEK, 7, TextStyle.SHORT, this.enUS, "Sun" },

        { DAY_OF_WEEK, 1, TextStyle.SHORT, this.ptBR, "Seg" }, { DAY_OF_WEEK, 2, TextStyle.SHORT, this.ptBR, "Ter" },
        { DAY_OF_WEEK, 3, TextStyle.SHORT, this.ptBR, "Qua" }, { DAY_OF_WEEK, 4, TextStyle.SHORT, this.ptBR, "Qui" },
        { DAY_OF_WEEK, 5, TextStyle.SHORT, this.ptBR, "Sex" },
        { DAY_OF_WEEK, 6, TextStyle.SHORT, this.ptBR, "S\u00E1b" },
        { DAY_OF_WEEK, 7, TextStyle.SHORT, this.ptBR, "Dom" },

        { DAY_OF_WEEK, 1, TextStyle.FULL, this.enUS, "Monday" },
        { DAY_OF_WEEK, 2, TextStyle.FULL, this.enUS, "Tuesday" },
        { DAY_OF_WEEK, 3, TextStyle.FULL, this.enUS, "Wednesday" },
        { DAY_OF_WEEK, 4, TextStyle.FULL, this.enUS, "Thursday" },
        { DAY_OF_WEEK, 5, TextStyle.FULL, this.enUS, "Friday" },
        { DAY_OF_WEEK, 6, TextStyle.FULL, this.enUS, "Saturday" },
        { DAY_OF_WEEK, 7, TextStyle.FULL, this.enUS, "Sunday" },

        { DAY_OF_WEEK, 1, TextStyle.FULL, this.ptBR, "Segunda-feira" },
        { DAY_OF_WEEK, 2, TextStyle.FULL, this.ptBR, "Ter\u00E7a-feira" },
        { DAY_OF_WEEK, 3, TextStyle.FULL, this.ptBR, "Quarta-feira" },
        { DAY_OF_WEEK, 4, TextStyle.FULL, this.ptBR, "Quinta-feira" },
        { DAY_OF_WEEK, 5, TextStyle.FULL, this.ptBR, "Sexta-feira" },
        { DAY_OF_WEEK, 6, TextStyle.FULL, this.ptBR, "S\u00E1bado" },
        { DAY_OF_WEEK, 7, TextStyle.FULL, this.ptBR, "Domingo" },

        { MONTH_OF_YEAR, 1, TextStyle.SHORT, this.enUS, "Jan" },
        { MONTH_OF_YEAR, 2, TextStyle.SHORT, this.enUS, "Feb" },
        { MONTH_OF_YEAR, 3, TextStyle.SHORT, this.enUS, "Mar" },
        { MONTH_OF_YEAR, 4, TextStyle.SHORT, this.enUS, "Apr" },
        { MONTH_OF_YEAR, 5, TextStyle.SHORT, this.enUS, "May" },
        { MONTH_OF_YEAR, 6, TextStyle.SHORT, this.enUS, "Jun" },
        { MONTH_OF_YEAR, 7, TextStyle.SHORT, this.enUS, "Jul" },
        { MONTH_OF_YEAR, 8, TextStyle.SHORT, this.enUS, "Aug" },
        { MONTH_OF_YEAR, 9, TextStyle.SHORT, this.enUS, "Sep" },
        { MONTH_OF_YEAR, 10, TextStyle.SHORT, this.enUS, "Oct" },
        { MONTH_OF_YEAR, 11, TextStyle.SHORT, this.enUS, "Nov" },
        { MONTH_OF_YEAR, 12, TextStyle.SHORT, this.enUS, "Dec" },

        { MONTH_OF_YEAR, 1, TextStyle.SHORT, this.ptBR, "Jan" },
        { MONTH_OF_YEAR, 2, TextStyle.SHORT, this.ptBR, "Fev" },
        { MONTH_OF_YEAR, 3, TextStyle.SHORT, this.ptBR, "Mar" },
        { MONTH_OF_YEAR, 4, TextStyle.SHORT, this.ptBR, "Abr" },
        { MONTH_OF_YEAR, 5, TextStyle.SHORT, this.ptBR, "Mai" },
        { MONTH_OF_YEAR, 6, TextStyle.SHORT, this.ptBR, "Jun" },
        { MONTH_OF_YEAR, 7, TextStyle.SHORT, this.ptBR, "Jul" },
        { MONTH_OF_YEAR, 8, TextStyle.SHORT, this.ptBR, "Ago" },
        { MONTH_OF_YEAR, 9, TextStyle.SHORT, this.ptBR, "Set" },
        { MONTH_OF_YEAR, 10, TextStyle.SHORT, this.ptBR, "Out" },
        { MONTH_OF_YEAR, 11, TextStyle.SHORT, this.ptBR, "Nov" },
        { MONTH_OF_YEAR, 12, TextStyle.SHORT, this.ptBR, "Dez" },

        { MONTH_OF_YEAR, 1, TextStyle.FULL, this.enUS, "January" },
        { MONTH_OF_YEAR, 2, TextStyle.FULL, this.enUS, "February" },
        { MONTH_OF_YEAR, 3, TextStyle.FULL, this.enUS, "March" },
        { MONTH_OF_YEAR, 4, TextStyle.FULL, this.enUS, "April" },
        { MONTH_OF_YEAR, 5, TextStyle.FULL, this.enUS, "May" },
        { MONTH_OF_YEAR, 6, TextStyle.FULL, this.enUS, "June" },
        { MONTH_OF_YEAR, 7, TextStyle.FULL, this.enUS, "July" },
        { MONTH_OF_YEAR, 8, TextStyle.FULL, this.enUS, "August" },
        { MONTH_OF_YEAR, 9, TextStyle.FULL, this.enUS, "September" },
        { MONTH_OF_YEAR, 10, TextStyle.FULL, this.enUS, "October" },
        { MONTH_OF_YEAR, 11, TextStyle.FULL, this.enUS, "November" },
        { MONTH_OF_YEAR, 12, TextStyle.FULL, this.enUS, "December" },

        { MONTH_OF_YEAR, 1, TextStyle.FULL, this.ptBR, "Janeiro" },
        { MONTH_OF_YEAR, 2, TextStyle.FULL, this.ptBR, "Fevereiro" },
        { MONTH_OF_YEAR, 3, TextStyle.FULL, this.ptBR, "Mar\u00E7o" },
        { MONTH_OF_YEAR, 4, TextStyle.FULL, this.ptBR, "Abril" },
        { MONTH_OF_YEAR, 5, TextStyle.FULL, this.ptBR, "Maio" },
        { MONTH_OF_YEAR, 6, TextStyle.FULL, this.ptBR, "Junho" },
        { MONTH_OF_YEAR, 7, TextStyle.FULL, this.ptBR, "Julho" },
        { MONTH_OF_YEAR, 8, TextStyle.FULL, this.ptBR, "Agosto" },
        { MONTH_OF_YEAR, 9, TextStyle.FULL, this.ptBR, "Setembro" },
        { MONTH_OF_YEAR, 10, TextStyle.FULL, this.ptBR, "Outubro" },
        { MONTH_OF_YEAR, 11, TextStyle.FULL, this.ptBR, "Novembro" },
        { MONTH_OF_YEAR, 12, TextStyle.FULL, this.ptBR, "Dezembro" },

        { AMPM_OF_DAY, 0, TextStyle.SHORT, this.enUS, "AM" }, { AMPM_OF_DAY, 1, TextStyle.SHORT, this.enUS, "PM" },

    };
  }

  @Test(dataProvider = "Text", groups = { "tck" })
  public void test_getText(DateTimeField field, Number value, TextStyle style, Locale locale, String expected) {

    DateTimeTextProvider tp = DateTimeFormatters.getTextProvider();
    assertEquals(tp.getText(field, value.longValue(), style, locale), expected);
  }

}
