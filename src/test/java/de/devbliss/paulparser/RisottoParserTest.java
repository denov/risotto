/*
 * Copyright 2013, devbliss GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package de.devbliss.paulparser;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.Test;

import com.devbliss.risotto.RisottoItem;
import com.devbliss.risotto.RisottoParser;

/**
 * This test makes sure that the parser works with common scientific journals.
 *
 * Please extend the files at core.parsing.testdata at will.
 *
 */
public class RisottoParserTest {

    public String TESTDATA_DIRECTORY = "src/test/resources/";

    public RisottoParser parser;

    public RisottoParserTest() {
        parser = new RisottoParser();
    }

    @Test
    public void testParsing() throws Exception {

        // make sure the common sense extraction works:
        List<RisottoItem> items =
                parser.parseRISContent(new InputStreamReader(new FileInputStream(
                        TESTDATA_DIRECTORY + "example.ris"), "UTF-8"));

        assertEquals(items.size(), 3);
        assertEquals(items.get(0).getRawFieldData().size(), 11);
        assertEquals(items.get(1).getRawFieldData().size(), 9);
        assertEquals(items.get(2).getRawFieldData().size(), 6);

        assertEquals(0, items.get(0).getSecondaryAuthors().size());
        assertEquals(0, items.get(0).getTertiaryAuthors().size());
        assertEquals(0, items.get(0).getSecondaryAuthors().size());
        assertEquals(0, items.get(0).getTertiaryTitles().size());
        assertEquals("DOI:10xxxxxxxx", items.get(0).getDoi());
        assertEquals("12/05/1977", items.get(0).getDate());
        assertEquals("keyword", items.get(0).getKeywords().get(0));
        assertEquals("keeword", items.get(0).getKeywords().get(1));
        assertEquals("BOOK", items.get(0).getTypeOfItem());
        assertEquals("P. Sherman", items.get(0).getAuthors().get(0));
        assertEquals("S. Mueller", items.get(0).getAuthors().get(1));
        assertEquals("Handbibliothek der Fortran Programmierung", items.get(0).getTitle());
        assertEquals(
                "Enthalten sind die vorzüglichsten Beispiele kunstvoller Fortran Programmierung.",
                items.get(0).getSecondaryTitles().get(0));
        assertEquals(
                "Java Programmierer wurden in einer sechs-wöchigen Studie zur Fortran-Programmierung gezwungen und die dabei entstehenden Kunstwerke aufwändig restauriert, interpretiert und analysiert.",
                items.get(0).getAbstractDescription());

        assertEquals("BOOK", items.get(0).getRawFieldData("TY").get(0));
        assertEquals("P. Sherman", items.get(0).getRawFieldData("AU").get(0));
        assertEquals("S. Mueller", items.get(0).getRawFieldData("AU").get(1));
        assertEquals("Handbibliothek der Fortran Programmierung", items.get(0).getTitle());
        assertEquals("Handbibliothek der Fortran Programmierung", items.get(0)
                .getRawFieldData("T1").get(0));
        assertEquals(
                "Enthalten sind die vorzüglichsten Beispiele kunstvoller Fortran Programmierung.",
                items.get(0).getRawFieldData("T2").get(0));
        assertEquals(
                "Java Programmierer wurden in einer sechs-wöchigen Studie zur Fortran-Programmierung gezwungen und die dabei entstehenden Kunstwerke aufwändig restauriert, interpretiert und analysiert.",
                items.get(0).getRawFieldData("AB").get(0));
        assertEquals("1977", items.get(0).getRawFieldData("PY").get(0));
        assertEquals("London", items.get(0).getRawFieldData("CY").get(0));
        assertEquals("Fortran rocks!", items.get(0).getRawFieldData("PB").get(0));
        assertEquals("DOI:10xxxxxxxx", items.get(0).getRawFieldData("DO").get(0));

        assertEquals(0, items.get(1).getSecondaryAuthors().size());
        assertEquals(0, items.get(1).getTertiaryAuthors().size());
        assertEquals(0, items.get(1).getSecondaryAuthors().size());
        assertEquals(0, items.get(1).getTertiaryTitles().size());
        assertEquals("DOI:20xxxxxxxx", items.get(1).getDoi());
        assertEquals(null, items.get(1).getDate());
        assertEquals(0, items.get(1).getKeywords().size());
        assertEquals("BOOK", items.get(1).getTypeOfItem());
        assertEquals("M. Bankman", items.get(1).getAuthors().get(0));
        assertEquals("Heidi van der Saar", items.get(1).getAuthors().get(1));
        assertEquals("Erlang for Dummies", items.get(1).getTitle());
        assertEquals("Erlang for those who can't code.", items.get(1).getSecondaryTitles().get(0));
        assertEquals(
                "Erlang is the future of programming. Forget about all those wacky languages as Java, Ruby or Scala.",
                items.get(1).getAbstractDescription());

        assertEquals("BOOK", items.get(1).getRawFieldData("TY").get(0));
        assertEquals("M. Bankman", items.get(1).getRawFieldData("AU").get(0));
        assertEquals("Heidi van der Saar", items.get(1).getRawFieldData("AU").get(1));
        assertEquals("Erlang for Dummies", items.get(1).getRawFieldData("TI").get(0));
        assertEquals("Erlang for those who can't code.", items.get(1).getRawFieldData("T2").get(0));
        assertEquals(
                "Erlang is the future of programming. Forget about all those wacky languages as Java, Ruby or Scala.",
                items.get(1).getRawFieldData("AB").get(0));
        assertEquals("2012", items.get(1).getRawFieldData("PY").get(0));
        assertEquals("Berlin", items.get(1).getRawFieldData("CY").get(0));
        assertEquals("For Dummies", items.get(1).getRawFieldData("PB").get(0));
        assertEquals("DOI:20xxxxxxxx", items.get(1).getRawFieldData("DO").get(0));

        assertEquals(0, items.get(2).getSecondaryAuthors().size());
        assertEquals(0, items.get(2).getTertiaryAuthors().size());
        assertEquals(0, items.get(2).getSecondaryAuthors().size());
        assertEquals(0, items.get(2).getTertiaryTitles().size());
        assertEquals(null, items.get(2).getDoi());
        assertEquals(null, items.get(2).getDate());
        assertEquals(0, items.get(2).getKeywords().size());
        assertEquals("BOOK", items.get(2).getTypeOfItem());
        assertEquals("Y. Mom", items.get(2).getAuthors().get(0));
        assertEquals("K. Fong", items.get(2).getAuthors().get(1));
        assertEquals("Kurzübersicht Innere Medizin der Spitzmaus", items.get(2).getTitle());
        assertEquals(0, items.get(2).getSecondaryTitles().size());
        assertEquals(null, items.get(2).getAbstractDescription());

        assertEquals("BOOK", items.get(2).getRawFieldData("TY").get(0));
        assertEquals("Y. Mom", items.get(2).getRawFieldData("AU").get(0));
        assertEquals("K. Fong", items.get(2).getRawFieldData("AU").get(1));
        assertEquals("Kurzübersicht Innere Medizin der Spitzmaus", items.get(2).getRawFieldData(
                "TI").get(0));
        assertEquals("1914", items.get(2).getRawFieldData("PY").get(0));
        assertEquals("Las Vegas", items.get(2).getRawFieldData("CY").get(0));
        assertEquals("Spitzmausverlag", items.get(2).getRawFieldData("PB").get(0));

    }

    @Test
    public void testBrokenParsing() throws Exception {

        List<RisottoItem> items =
                parser.parseRISContent(new InputStreamReader(new FileInputStream(
                        TESTDATA_DIRECTORY + "broken.ris"), "UTF-8"));

        assertEquals(2, items.size());
        assertEquals(3, items.get(0).getRawFieldData().size());
        assertEquals(2, items.get(1).getRawFieldData().size());

        assertEquals("BOOK", items.get(0).getRawFieldData("TY").get(0));
        assertEquals("single defective entries, should be read somehow", items.get(0)
                .getRawFieldData("AU").get(0));
        assertEquals("Defective for Dummies", items.get(0).getRawFieldData("TI").get(0));

        assertEquals("BOOK", items.get(1).getRawFieldData("TY").get(0));
        assertEquals("no endtag, EOF instead, should work", items.get(1).getRawFieldData("PB").get(
                0));
    }

}
