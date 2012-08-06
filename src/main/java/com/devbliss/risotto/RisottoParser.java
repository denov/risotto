package com.devbliss.risotto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class RisottoParser {

    private static final String PATTERN_ITEMSTART = "^TY  -.*";
    private static final String PATTERN_ITEMEND = "^ER  -.*";
    private static final String PATTERN_ITEM = "^[A-Z][A-Z0-9]  -.*";
    private static final String PATTERN_ITEMEXTENSION = "^      .+";

    /**
     * Method to parse the contents of a RIS File into a List of RisottoItems.
     * 
     * @param htmlContent
     * @return
     */
    public static List<RisottoItem> parseRISContent(String risContent) {
        risContent = removeBOMRelict(risContent);
        RisottoRISContentService contentService = new RisottoRISContentService(risContent);
        List<RisottoItem> items = new ArrayList<RisottoItem>();
        while (contentService.hasNextLine()) {
            try {
                RisottoItem myItem = parseNextItem(contentService);
                if (myItem != null) {
                    myItem.normalize();
                    items.add(myItem);
                }
            } catch (RisottoItemNotValidException exception) {
                System.out.println("The next item couldn't be parsed.");
            }
        }

        return items;
    }

    /**
     * Removes the BOM Relict that can result from using UTF-8 due to a java bug.
     * http://bugs.sun.com/view_bug.do?bug_id=4508058
     * 
     * @param risContent
     * @return
     */
    private static String removeBOMRelict(String risContent) {
        if (((byte) risContent.charAt(0)) == -1) {
            return risContent.substring(1);
        }

        return risContent;
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
                content.skipNextLine();
                continue;
            }

            if (Pattern.matches(PATTERN_ITEMEND, StringUtils.stripStart(currentLine, null))) {
                content.skipNextLine();
                break;
            } else if (Pattern.matches(PATTERN_ITEM, StringUtils.stripStart(currentLine, null))) {
                extractDataFromField(content, myItem);
                continue;
            }

            content.skipNextLine();
        }
    }

    private static void getNextItemStart(RisottoRISContentService content, RisottoItem myItem) {
        while (content.hasNextLine()) {
            String currentLine = content.peekNextLine();
            if (currentLine.trim().length() < 4) {
                content.skipNextLine();
                continue;
            }

            if (Pattern.matches(PATTERN_ITEMSTART, StringUtils.stripStart(currentLine, null))) {
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
        String line = StringUtils.stripStart(content.getNextLine(), null);
        if (line.length() < 7) {
            return "";
        }

        String fieldContent = line.substring(6);

        // peek next line for continued infos
        while (content.hasNextLine()) {
            String nextLine = content.peekNextLine();

            // if yes, getLine and peek another line
            if (Pattern.matches(PATTERN_ITEMEXTENSION, nextLine)) {
                fieldContent = fieldContent + nextLine.substring(6);
                content.skipNextLine();
            } else {
                break;
            }
        }

        return fieldContent;
    }

    private static String getFieldName(RisottoRISContentService content) {
        // get FieldName
        String line = StringUtils.stripStart(content.peekNextLine(), null);
        return line.substring(0, 2);
    }
}
