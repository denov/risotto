package com.devbliss.risotto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RisottoParser {

    private static final String PATTERN_ITEMSTART = "^TY  -.*";
    private static final String PATTERN_ITEMEND = "^ER  -.*";
    private static final String PATTERN_ITEM = "^[A-Z][A-Z0-9]  - .*";
    private static final String PATTERN_ITEMEXTENSION = "^        .+";

    /**
     * Method to parse the contents of a RIS File.
     *
     * @param htmlContent
     * @return
     */
    public static List<RisottoItem> parseRISContent(String risContent) {
        RisottoRISContentService contentService = new RisottoRISContentService(risContent);

        List<RisottoItem> items = new ArrayList<RisottoItem>();
        while (contentService.hasNextLine()) {
            try {
                RisottoItem myItem = parseNextItem(contentService);
                if (myItem != null) {
                    items.add(myItem);
                }
            } catch (RisottoItemNotValidException exception) {
                System.out.println("The next item couldn't be parsed.");
            }
        }

        return items;
    }

    private static RisottoItem parseNextItem(RisottoRISContentService content)
            throws RisottoItemNotValidException {

        if (!content.hasNextLine())
            throw new RisottoItemNotValidException("No more lines found!");

        RisottoItem myItem = new RisottoItem();

        // find next line that matches the start of an item
        getNextItemStart(content, myItem);
        // until next line matches :
        getItemsUntilItemEnd(content, myItem);

        return myItem;
    }

    private static void getItemsUntilItemEnd(RisottoRISContentService content, RisottoItem myItem) {
        while (content.hasNextLine()) {
            String currentLine = content.peekNextLine();
            if (currentLine.trim().length() < 4) {
                continue;
            }

            if (Pattern.matches(PATTERN_ITEMEND, currentLine.trim())) {
                content.skipNextLine();
                break;
            } else if (Pattern.matches(PATTERN_ITEM, currentLine.trim())) {
                extractDataFromField(content, myItem);
            }

            content.skipNextLine();
        }
    }

    private static void getNextItemStart(RisottoRISContentService content, RisottoItem myItem) {
        while (content.hasNextLine()) {
            String currentLine = content.peekNextLine();
            if(currentLine.trim().length()<4) {
                continue;
            }

            if (Pattern.matches(PATTERN_ITEMSTART, currentLine.trim())) {
                extractDataFromField(content, myItem);
                break;
            }

            content.skipNextLine();
        }
    }

    private static void extractDataFromField(RisottoRISContentService content, RisottoItem myItem) {
        // read line
        String fieldName = getFieldName(content);
        String fieldContent = getContent(content);

        myItem.addRawFieldData(fieldName, fieldContent);

    }

    private static String getContent(RisottoRISContentService content) {
        // get content
        String line = content.getNextLine().trim();
        if (line.length() < 7)
        {
            return "";
        }

        String fieldContent = line.substring(6);

        // peek next line for continued infos
        while (content.hasNextLine()) {
            String nextLine = content.peekNextLine().trim();

            // if yes, getLine and peek another lines
            if (Pattern.matches(PATTERN_ITEMEXTENSION, nextLine)) {
                fieldContent = fieldContent + " " + nextLine;
                content.skipNextLine();
            } else {
                break;
            }
        }

        return fieldContent;
    }

    private static String getFieldName(RisottoRISContentService content) {
        // get FieldName
        String line = content.peekNextLine().trim();
        return line.substring(0, 2);
    }
}
