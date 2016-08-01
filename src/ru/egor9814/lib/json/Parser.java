package ru.egor9814.lib.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egor9814 on 30.07.2016.
 */
class Parser {

    private static final Token EOF = new Token(TokenType.EOF, "", -1, -1);

    private final List<Token> tokens;
    private final int size;
    private Expression parsedExpression;
    private int pos;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        size = tokens.size();
    }

    public Expression parse(){
        if(parsedExpression == null) {
            parsedExpression = object();
        }
        return parsedExpression;
    }


    private Expression value(){
        if(lookMatch(0, TokenType.INTEGER)){
            return new ValueExpression(Integer.parseInt(consume(TokenType.INTEGER).getText()));
        }
        if(lookMatch(0, TokenType.DOUBLE)){
            return new ValueExpression(Double.parseDouble(consume(TokenType.DOUBLE).getText()));
        }
        if(lookMatch(0, TokenType.TRUE)){
            consume(TokenType.TRUE);
            return new ValueExpression(true);
        }
        if(lookMatch(0, TokenType.FALSE)){
            consume(TokenType.FALSE);
            return new ValueExpression(false);
        }
        if(lookMatch(0, TokenType.TEXT)){
            return new ValueExpression(consume(TokenType.TEXT).getText());
        }
        if(lookMatch(0, TokenType.LBRACKET)){
            return array();
        }
        if(lookMatch(0, TokenType.LBRACE)){
            return object();
        }
        throw new IllegalStateException(get(0).getPosition() + "; Unknown value type: " + get(0).getType());
    }

    private Expression array(){
        List<Expression> values = new ArrayList<>();
        consume(TokenType.LBRACKET);
        while(!match(TokenType.RBRACKET)){
            values.add(value());
            match(TokenType.COMMA);
        }
        return new ArrayExpression(values);
    }

    private Expression object(){
        List<String> keys = new ArrayList<>();
        List<Expression> values = new ArrayList<>();
        consume(TokenType.LBRACE);
        while(!match(TokenType.RBRACE)){
            keys.add(consume(TokenType.TEXT).getText());
            consume(TokenType.COLON);
            values.add(value());
            match(TokenType.COMMA);
        }
        return new ObjectExpression(keys, values);
    }


    private Token consume(TokenType type){
        final Token current = get(0);
        if(type != current.getType()) throw new IllegalStateException("Token " + current + " doesn't match " + type);
        pos++;
        return current;
    }
    private boolean match(TokenType type) {
        final Token current = get(0);
        if (type != current.getType()) return false;
        pos++;
        return true;
    }
    private boolean lookMatch(int pos, TokenType type){
        return get(pos).getType() == type;
    }
    private Token get(int relativePosition){
        final int position = pos + relativePosition;
        if (position >= size) return EOF;
        return tokens.get(position);
    }
}
