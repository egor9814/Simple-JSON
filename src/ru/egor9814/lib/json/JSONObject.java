package ru.egor9814.lib.json;

import java.util.*;

/**
 * Created by egor9814 on 30.07.2016.
 */
public class JSONObject {

    private class PriorityValue {
        final int priority;
        final Object value;
        public PriorityValue(int priority, Object value) {
            this.priority = priority;
            this.value = value;
        }
    }
    private final Map<String, PriorityValue> pairs;
    public JSONObject() {
        pairs = new HashMap<>();
    }
    public JSONObject(String source) throws JSONException {
        this();
        parse(source);
    }
    private int priority = 0;


    public void put(String key, Object value){
        pairs.put(key, new PriorityValue(priority++, value));
    }

    public Object get(String key){
        return pairs.get(key).value;
    }

    public boolean has(String key){
        return pairs.containsKey(key);
    }

    public List<String> keys(){
        String[] keys = new ArrayList<>(pairs.keySet()).toArray(new String[pairs.keySet().size()]);
        int[] priority = new int[keys.length];
        for(int i = 0; i < keys.length; i++){
            priority[i] = pairs.get(keys[i]).priority;
        }
        selectionSort(keys, priority);
        return new ArrayList<>(Arrays.asList(keys));
    }
    private void selectionSort(String[] keys, int[] priority) {
        int i, j, minIndex, tmpP;
        String tmp;
        int n = keys.length;
        for (i = 0; i < n - 1; i++) {
            minIndex = i;
            for (j = i + 1; j < n; j++)
                if (priority[j] < priority[minIndex])
                    minIndex = j;
            if (minIndex != i) {
                tmp = keys[i];
                keys[i] = keys[minIndex];
                keys[minIndex] = tmp;

                tmpP = priority[i];
                priority[i] = priority[minIndex];
                priority[minIndex] = tmpP;
            }
        }
    }

    public List<Object> values(){
        return new ArrayList<>(pairs.values());
    }

    public int size(){
        return pairs.size();
    }

    public Object remove(String key){
        return pairs.remove(key);
    }


    public int optInt(String key, int defValue){
        return has(key) ? (int)get(key) : defValue;
    }
    public int optInt(String key){
        return optInt(key, 0);
    }

    public double optDouble(String key, double defValue){
        return has(key) ? (int)get(key) : defValue;
    }
    public double optDouble(String key){
        return optDouble(key, 0);
    }

    public boolean optBoolean(String key, boolean defValue){
        return has(key) ? (boolean)get(key) : defValue;
    }
    public boolean optBoolean(String key){
        return optBoolean(key, false);
    }

    public String optString(String key, String defValue){
        return has(key) ? (String)get(key) : defValue;
    }
    public String optString(String key){
        return optString(key, null);
    }

    public Object optObject(String key, Object defValue){
        return has(key) ? get(key) : defValue;
    }
    public Object optObject(String key){
        return optObject(key, null);
    }

    public JSONArray optJSONArray(String key, JSONArray defValue){
        return has(key) ? (JSONArray)get(key) : defValue;
    }
    public JSONArray optJSONArray(String key){
        return optJSONArray(key, new JSONArray());
    }

    public JSONObject optJSONObject(String key, JSONObject defValue){
        return has(key) ? (JSONObject)get(key) : defValue;
    }
    public JSONObject optJSONObject(String key){
        return optJSONObject(key, new JSONObject());
    }


    @Override
    public String toString() {
        return toString(4);
    }

    public String toString(String tab){
        String result = "";
        for(Map.Entry<String, PriorityValue> entry : pairs.entrySet()){
            String value = entry.getValue().value.toString();
            if(entry.getValue().value instanceof String) value = "\"" + value + "\"";
            result += "\n,\"" + entry.getKey() + "\": " + value;
        }
        result = result.length() == 0 ? result : result.substring(2);
        Scanner in = new Scanner(new String(result.getBytes()));
        result = "";
        while(in.hasNextLine()){
            result += "\n" + tab + in.nextLine();
        }
        return "{" + result + "\n}";
    }
    public String toString(int tabSize){
        if(tabSize == 4) return toString("\t");
        else {
            String tab = "";
            for(int i = 0; i < tabSize; i++) tab += " ";
            return toString(tab);
        }
    }

    private void parse(String in) throws JSONException {
        try{
            Lexer lexer = new Lexer(in);
            Parser parser = new Parser(lexer.tokenize());
            Expression expression = parser.parse();
            JSONObject object = (JSONObject)expression.eval();
            for(String key : object.keys()){
                this.put(key, object.get(key));
            }
        } catch(Exception e){
            throw new JSONException(e.getMessage());
        }
    }

}
