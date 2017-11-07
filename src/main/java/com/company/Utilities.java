package com.company;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
/**
 * Created by slan on 11/7/2017.
 */
public class Utilities {

    public static JsonNode mapJsonToMessage(byte[] bytes) {


        ObjectMapper mapper = new ObjectMapper();

        JsonFactory factory = mapper.getFactory();
        JsonParser parser = null;
        JsonNode node = null;
        try {
            parser = factory.createParser(new String(bytes));
            node = mapper.readTree(parser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;

    }

}
