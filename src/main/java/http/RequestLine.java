package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestLine {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private HttpMethod method;
    private String path;
    private Map<String, String> parameters = new HashMap<>();

    public RequestLine(String requestLine) {
        log.debug("request line : {}", requestLine);
        String[] tokens = requestLine.split(" ");
        if (tokens.length != 3) {
            throw new IllegalArgumentException(requestLine + "이 형식에 맞지 않습니다.");
        }
        method = HttpMethod.valueOf(tokens[0]);

        if (method == HttpMethod.POST) {
            path = tokens[1];
            return;
        }

        int idx = tokens[1].indexOf("?");
        if (idx == -1) {
            path = tokens[1];
        } else {
            path = tokens[1].substring(0, idx);
            parameters = HttpRequestUtils.parseQueryString(tokens[1].substring(idx + 1));
        }
    }

    public HttpMethod getMethod() {
        return method;
    }
    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
