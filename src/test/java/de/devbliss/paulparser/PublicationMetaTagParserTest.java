package de.devbliss.paulparser;

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
public class PublicationMetaTagParserTest {

	@Test
    public void testParsing() throws Exception {
        // TODO just cheap manual testing here, this isn't a real test
        List<RisottoItem> items = RisottoParser.parseRISContent("TY  - LOL\nTI  - title\nER  - ");
        System.out.println(items.size());
        System.out.println(items.get(0).getRawFieldData().size());

        for (String key : items.get(0).getRawFieldData().keySet()) {
            List<String> curList = items.get(0).getRawFieldData().get(key);
            for (String string : curList) {
                System.out.println(key + ":" + string);
            }
        }
	}

}
