package restchatbot;

import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.parser.JSONParser;

public class JsonParser extends JSONParser {
    public JsonParser(String source, Global global, boolean dualFields) {
        super(source, global, dualFields);
    }


    public String getContentType() {
        return "text/json; charset=UTF-8";
    }
}
