package com.example.demo;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class StringNULLTypeAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                //空的String对象进行赋值
                return "";
            }
            return reader.nextString();
        }

        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.value("");
                return;
            }
            writer.value(value);
        }


}
