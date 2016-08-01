package ru.egor9814.lib.json;

/**
 * Created by egor9814 on 30.07.2016.
 */
class Token {

    private final TokenType type;
    private final String text;
    private final int row, col;

    public Token(TokenType type, String text, int row, int col) {
        this.type = type;
        this.text = text;
        this.row = row;
        this.col = col;
    }

    public TokenType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getPosition(){
        return "[" + row + ":" + col + "]";
    }

    @Override
    public String toString() {
        return type.name() + " " + getPosition() + " " + getText();
    }
}
