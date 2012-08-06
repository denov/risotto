package com.devbliss.risotto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class RisottoParser {

    private static final Pattern PATTERN_ITEMSTART = Pattern.compile("^TY  -.*");
    private static final Pattern PATTERN_ITEMEND = Pattern.compile("^ER  -.*");
    private static final Pattern PATTERN_ITEM = Pattern.compile("^[A-Z][A-Z0-9]  -.*");
    private static final Pattern PATTERN_ITEMEXTENSION = Pattern.compile("^      .+");

    /**
     * Method to parse the contents of a RIS File into a List of RisottoItems.
     *
     * @param htmlContent
     * @return
     */
    public static List<RisottoItem> parseRISContent(Reader risReader) throws IOException {
        BufferedReader in = new BufferedReader(risReader);
        RisottoRISContentService contentService = new RisottoRISContentService(in);
        List<RisottoItem> items = new ArrayList<RisottoItem>();
        while (contentService.hasNextLine()) {
            RisottoItem myItem = parseNextItem(contentService);
            if (myItem != null) {
                myItem.normalize();
                items.add(myItem);
            }
        }

        return items;
    }

    private static RisottoItem parseNextItem(RisottoRISContentService content) throws IOException {

        if (!content.hasNextLine())
            return null;

        RisottoItem myItem = new RisottoItem();

        // find next line that matches the start of an item
        getNextItemStart(content, myItem);
        // until next line matches :
        getItemsUntilItemEnd(content, myItem);

        return myItem;
    }

    private static void getItemsUntilItemEnd(RisottoRISContentService content, RisottoItem myItem)
            throws IOException {
        while (content.hasNextLine()) {
            String currentLine = content.peekNextLine();
            if (currentLine.trim().length() < 4) {
                content.skipNextLine();
                continue;
            }

            if (PATTERN_ITEMEND.matcher(StringUtils.stripStart(currentLine, null)).matches()) {
                content.skipNextLine();
                break;
            } else if (PATTERN_ITEM.matcher(StringUtils.stripStart(currentLine, null)).matches()) {
                extractDataFromField(content, myItem);
                continue;
            }

            content.skipNextLine();
        }
    }

    private static void getNextItemStart(RisottoRISContentService content, RisottoItem myItem)
            throws IOException {
        while (content.hasNextLine()) {
            String currentLine = content.peekNextLine();
            if (currentLine.trim().length() < 4) {
                content.skipNextLine();
                continue;
            }

            if (PATTERN_ITEMSTART.matcher(StringUtils.stripStart(currentLine, null)).matches()) {
                extractDataFromField(content, myItem);
                break;
            }

            content.skipNextLine();
        }
    }

    private static void extractDataFromField(RisottoRISContentService content, RisottoItem myItem)
            throws IOException {
        // read line
        String fieldName = getFieldName(content);
        String fieldContent = getContent(content);

        myItem.addRawFieldData(fieldName, fieldContent);

    }

    private static String getContent(RisottoRISContentService content) throws IOException {
        // get content
        String line = StringUtils.stripStart(content.getNextLine(), null);
        if (line.length() < 7) {
            return "";
        }

        String fieldContent = line.substring(6);

        // peek next line for continued infos
        while (content.hasNextLine()) {
            String nextLine = content.peekNextLine();

            // if yes, getLine and peek another line
            if (PATTERN_ITEMEXTENSION.matcher(nextLine).matches()) {
                fieldContent = fieldContent + nextLine.substring(6);
                content.skipNextLine();
            } else {
                break;
            }
        }

        return fieldContent;
    }

    private static String getFieldName(RisottoRISContentService content) throws IOException {
        // get FieldName
        String line = StringUtils.stripStart(content.peekNextLine(), null);
        return line.substring(0, 2);
    }
}
