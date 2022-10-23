package me.kavin.piped.utils;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import me.kavin.piped.utils.obj.search.SearchItem;

public class SearchItemSerializer extends JsonSerializer<List<SearchItem>> {

    @Override
    public void serialize(List<SearchItem> value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
        jgen.writeStartArray();
        for (SearchItem item : value) {
            jgen.writeStartObject();
            jgen.writeObjectField(item.getInfoType(), item);
            jgen.writeEndObject();    
        }
        jgen.writeEndArray();
    }

}
