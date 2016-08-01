package ru.egor9814.lib.json;

/**
 * Created by egor9814 on 30.07.2016.
 */
enum TokenType {

    INTEGER, DOUBLE, TRUE, FALSE, TEXT,

    LBRACE, // {
    RBRACE, // }
    LBRACKET, // [
    RBRACKET, // ]
    COLON, // :
    COMMA, // ,

    EOF

}
