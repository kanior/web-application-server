package http;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class RequestLineTest {

    @Test
    public void create_method() {
        RequestLine line = new RequestLine("GET /index.html HTTP/1.1");
        assertEquals(HttpMethod.GET, line.getMethod());
        assertEquals("/index.html", line.getPath());

        line = new RequestLine("POST /index.html HTTP/1.1");
        assertEquals(HttpMethod.POST, line.getMethod());
        assertEquals("/index.html", line.getPath());
    }

    @Test
    public void create_path_and_params() {
        RequestLine line = new RequestLine("GET /user/create?userId=kanior&password=pass HTTP/1.1");
        assertEquals(HttpMethod.GET, line.getMethod());
        assertEquals("/user/create", line.getPath());

        Map<String, String> parameters = line.getParameters();
        assertEquals(2, parameters.size());
    }

}