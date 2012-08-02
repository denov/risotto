package com.devbliss.risotto;

public class RisottoRISContentService {

    static final String EOF = "EOF";

    int currentLine;
    String[] lines;

    public RisottoRISContentService(String content) {
        this.currentLine = 0;
        this.lines = content.split("\\r?\\n");
    }

    public boolean hasNextLine() {
        return currentLine < lines.length;
    }

    public String getNextLine() {
        if (hasNextLine()) {
            String line = lines[currentLine];
            currentLine++;
            return line;
        }

        return EOF;
    }

    public String peekNextLine() {
        if (hasNextLine()) {
            return lines[currentLine];
        }

        return EOF;
    }

    public void reset() {
        currentLine = 0;
    }

    public void skipNextLine() {
        this.currentLine++;
    }

}
