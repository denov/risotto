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

package com.devbliss.risotto;

import java.io.BufferedReader;
import java.io.IOException;

public class RisottoRISContentService {

    static final String EOF = "EOF";

    int currentLine;
    String nextLine;

    BufferedReader reader;

    public RisottoRISContentService(BufferedReader reader) {
        this.currentLine = 0;
        this.reader = reader;
        this.nextLine = null;
    }

    /**
     * Removes the BOM Relict that can result from using UTF-8 due to a java bug.
     * http://bugs.sun.com/view_bug.do?bug_id=4508058
     *
     * @param risContent
     * @return
     */
    private String removeBOMRelict(String risContent) {
        if (((byte) risContent.charAt(0)) == -1) {
            return risContent.substring(1);
        }

        return risContent;
    }

    public boolean hasNextLine() throws IOException {
        if (nextLine != null) {
            return true;
        }

        nextLine = reader.readLine();

        if (currentLine == 0) {
            nextLine = removeBOMRelict(nextLine);
        }

        if (nextLine == null) {
            return false;
        }

        return true;
    }

    public String getNextLine() throws IOException {
        if (hasNextLine()) {
            currentLine++;
            String currentLine = nextLine;
            nextLine = null;
            return currentLine;
        }

        return EOF;
    }

    public String peekNextLine() throws IOException {
        if (hasNextLine()) {
            return nextLine;
        }

        return EOF;
    }

    public void skipNextLine() throws IOException {
        if (hasNextLine()) {
            this.nextLine = null;
            this.currentLine++;
        }
    }

}
