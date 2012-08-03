package de.devbliss.paulparser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
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

	@Test
    public void testParsing() throws Exception {

        String file =
                FileUtils.readFileToString(new File(TESTDATA_DIRECTORY + "example.ris"), "UTF-8");
        // System.out.println(file);
        // make sure the common sense extraction works:
        List<RisottoItem> items = RisottoParser.parseRISContent(file);

        assertEquals(items.size(), 3);
        assertEquals(items.get(0).getRawFieldData().size(), 9);
        assertEquals(items.get(1).getRawFieldData().size(), 9);
        assertEquals(items.get(2).getRawFieldData().size(), 6);

        assertEquals(items.get(0).getRawFieldData("TY").get(0), "BOOK");
        assertEquals(items.get(0).getRawFieldData("AU").get(0), "P. Sherman");
        assertEquals(items.get(0).getRawFieldData("AU").get(1), "S. Mueller");
        assertEquals(items.get(0).getRawFieldData("TI").get(0),
                "Handbibliothek der Fortran Programmierung");
        assertEquals(items.get(0).getRawFieldData("T2").get(0),
                "Enthalten sind die vorzüglichsten Beispiele kunstvoller Fortran Programmierung.");
        assertEquals(
                items.get(0).getRawFieldData("AB").get(0),
                "Java Programmierer wurden in einer sechs-wöchigen Studie zur Fortran-Programmierung gezwungen und die dabei entstehenden Kunstwerke aufwändig restauriert, interpretiert und analysiert.");
        assertEquals(items.get(0).getRawFieldData("PY").get(0), "1977");
        assertEquals(items.get(0).getRawFieldData("CY").get(0), "London");
        assertEquals(items.get(0).getRawFieldData("PB").get(0), "Fortran rocks!");
        assertEquals(items.get(0).getRawFieldData("DO").get(0), "DOI:10xxxxxxxx");

        assertEquals(items.get(1).getRawFieldData("TY").get(0), "BOOK");
        assertEquals(items.get(1).getRawFieldData("AU").get(0), "M. Bankman");
        assertEquals(items.get(1).getRawFieldData("AU").get(1), "Heidi van der Saar");
        assertEquals(items.get(1).getRawFieldData("TI").get(0), "Erlang for Dummies");
        assertEquals(items.get(1).getRawFieldData("T2").get(0), "Erlang for those who can't code.");
        assertEquals(
                items.get(1).getRawFieldData("AB").get(0),
                "Erlang is the future of programming. Forget about all those wacky languages as Java, Ruby or Scala.");
        assertEquals(items.get(1).getRawFieldData("PY").get(0), "2012");
        assertEquals(items.get(1).getRawFieldData("CY").get(0), "Berlin");
        assertEquals(items.get(1).getRawFieldData("PB").get(0), "For Dummies");
        assertEquals(items.get(1).getRawFieldData("DO").get(0), "DOI:20xxxxxxxx");

        assertEquals(items.get(2).getRawFieldData("TY").get(0), "BOOK");
        assertEquals(items.get(2).getRawFieldData("AU").get(0), "Y. Mom");
        assertEquals(items.get(2).getRawFieldData("AU").get(1), "K. Fong");
        assertEquals(items.get(2).getRawFieldData("TI").get(0),
                "Kurzübersicht Innere Medizin der Spitzmaus");
        assertEquals(items.get(2).getRawFieldData("PY").get(0), "1914");
        assertEquals(items.get(2).getRawFieldData("CY").get(0), "Las Vegas");
        assertEquals(items.get(2).getRawFieldData("PB").get(0), "Spitzmausverlag");

    }

    // @Test
    public void testBrokenParsing() throws Exception {

        String file =
                FileUtils.readFileToString(new File(TESTDATA_DIRECTORY + "broken.ris"), "UTF-8");
        System.out.println(file);
        // make sure the common sense extraction works:
        List<RisottoItem> items = RisottoParser.parseRISContent(file);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(items.size());

        for (RisottoItem risottoItem : items) {
            System.out.println(risottoItem.getRawFieldData().size());

            for (String key : risottoItem.getRawFieldData().keySet()) {
                List<String> curList = risottoItem.getRawFieldData().get(key);
                for (String string : curList) {
                    System.out.println(key + ":" + string);
                }
            }

        }

    }

}
