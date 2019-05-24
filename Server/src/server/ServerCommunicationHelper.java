/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jay.dave
 */
public class ServerCommunicationHelper {

    public Map<String, String> dictionary;

    public ServerCommunicationHelper() {
        dictionary = new HashMap<String, String>();
    }

    public String getField(String fieldName) {

        return this.dictionary.get(fieldName);
    }

public void unpackageFields(String packedFields) {
       StringBuilder builder = new StringBuilder();

       String fieldName = null;
       char fieldNameFirstChar = 'c';
       boolean inFieldCount = true;
       boolean inFieldName = false;
       boolean inFieldLength = false;
       int fieldCount = 0;
       int fieldLength = 0;
       int valueIndex = 0;
       int globalIndex = 0;

       while (globalIndex < packedFields.length()) {
           char c = packedFields.charAt(globalIndex++);

           if (inFieldCount) {
               if (c == '|') {
                   fieldCount = Integer.parseInt(builder.toString());
                   builder = new StringBuilder();
                   inFieldCount = false;
                   inFieldName = true;
               } else {
                   builder.append(c);
               }
           }
           else {
               if (inFieldName) {
                   if (c == '|') {
                       fieldName = builder.toString();
                       builder = new StringBuilder();
                       inFieldName = false;
                       inFieldLength = true;
                   } else {
                       builder.append(c);
                   }
               } else {
                   if (inFieldLength) {
                       if (c == '|') {
                           fieldLength = Integer.parseInt(builder.toString());
                           builder = new StringBuilder();
                           valueIndex = 0;
                           inFieldLength = false;
                       } else {
                           builder.append(c);
                       }
                   } else {
                       valueIndex++;
                       if (valueIndex <= fieldLength) {
                           builder.append(c);
                           //valueIndex++;
                       } else {
                           
                           
                           String value = builder.toString();
                           dictionary.put(fieldName, value);
                           builder = new StringBuilder();
                           builder.append(c);

                           inFieldName = true;
                       }
                   }
               }
           }
       }
   }
}
