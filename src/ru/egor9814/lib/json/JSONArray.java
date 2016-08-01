package ru.egor9814.lib.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by egor9814 on 30.07.2016.
 */
public class JSONArray {

    final List<Object> values;
    public JSONArray(List<Object> values) {
        this.values = new ArrayList<>(values);
    }
    public JSONArray(Object[] values){
        this.values = Arrays.asList(values);
    }
    public JSONArray(int size){
        this.values = new ArrayList<>();
        for(int i = 0; i < size; i++){
            values.add(null);
        }
    }
    public JSONArray(){
        this(0);
    }

    void set(int index, Object value){
        values.set(index, value);
    }

    public void put(int index, Object value){
        values.add(index, value);
    }
    public void put(Object value){
        values.add(value);
    }

    public int length(){
        return values.size();
    }

    public Object get(int index){
        return values.get(index);
    }

    public List<Object> getValues() {
        return values;
    }

    public Object[] toArray(){
        List<Object> list = getValues();
        return list.toArray(new Object[list.size()]);
    }


    private boolean has(int index){
        return index >= 0 && index < length();
    }

    public int optInt(int index, int defValue){
        return has(index) ? (int)get(index) : defValue;
    }
    public int optInt(int index){
        return optInt(index, 0);
    }

    public double optDouble(int index, double defValue){
        return has(index) ? (int)get(index) : defValue;
    }
    public double optDouble(int index){
        return optDouble(index, 0);
    }

    public boolean optBoolean(int index, boolean defValue){
        return has(index) ? (boolean)get(index) : defValue;
    }
    public boolean optBoolean(int index){
        return optBoolean(index, false);
    }

    public String optString(int index, String defValue){
        return has(index) ? (String)get(index) : defValue;
    }
    public String optString(int index){
        return optString(index, null);
    }

    public Object optObject(int index, Object defValue){
        return has(index) ? get(index) : defValue;
    }
    public Object optObject(int index){
        return optObject(index, null);
    }

    public JSONArray optJSONArray(int index, JSONArray defValue){
        return has(index) ? (JSONArray)get(index) : defValue;
    }
    public JSONArray optJSONArray(int index){
        return optJSONArray(index, new JSONArray());
    }

    public JSONObject optJSONObject(int index, JSONObject defValue){
        return has(index) ? (JSONObject)get(index) : defValue;
    }
    public JSONObject optJSONObject(int index){
        return optJSONObject(index, new JSONObject());
    }


    @Override
    public String toString() {
        String result = "";
        for(Object v : values){
            result += ", " + v;
        }
        result = result.length() == 0 ? result : result.substring(1);
        return "[" + result + "]";
    }

}
