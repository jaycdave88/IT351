/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jay.dave
 */
public class ClientCommunicationHelper {

    private Map<String, String> dictionary;

    public ClientCommunicationHelper() {
        dictionary = new HashMap<String, String>();
    }

    public void addField(String fieldName, String value) {
        this.dictionary.put(fieldName, value);
    }

    public String packageFields() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.dictionary.size());
        sb.append("|");

        for (Map.Entry<String, String> entry : this.dictionary.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            sb.append(key);
            sb.append("|");
            sb.append(value.length());
            sb.append("|");
            sb.append(value);
            
        }
        return sb.toString();
    }

}
