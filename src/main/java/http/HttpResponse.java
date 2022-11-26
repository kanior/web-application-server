package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private final String rootDir = "./webapp";

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private OutputStream out;
    private Map<String, String> headers = new HashMap<>();

    public HttpResponse(OutputStream out) {
        this.out = out;
    }

    public void forward(String url) {
        try {
            byte[] body = Files.readAllBytes(new File(rootDir + url).toPath());
            addHeader("Content-Length", body.length + "");

            String headerString = "HTTP/1.1 200 OK \r\n";
            if (url.endsWith(".css")) {
                addHeader("Content-Type", "text/css");
            } else if (url.endsWith(".js")) {
                addHeader("Content-Type", "application/javascript");
            } else {
                addHeader("Content-Type", "text/html;charset=utf-8");
            }
            headerString += getHeaderString();
            headerString += "\r\n";

            out.write(headerString.getBytes());
            out.write(body, 0, body.length);
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void forward(byte[] body) {
        try {
            addHeader("Content-Type", "text/html;charset=utf-8");
            addHeader("Content-Length", body.length + "");

            String headerString = "HTTP/1.1 200 OK \r\n";
            headerString += getHeaderString();
            headerString += "\r\n";

            out.write(headerString.getBytes());
            out.write(body, 0, body.length);
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void sendRedirect(String location) {
        try {
            addHeader("Location", location);

            String headerString = "HTTP/1.1 302 Found \r\n";
            headerString += getHeaderString();
            headerString += "\r\n";

            out.write(headerString.getBytes());
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    private String getHeaderString() {
        String headerString = "";
        for (String name : headers.keySet()) {
            if (headers.containsKey(name)) {
                headerString += name + ":" + headers.get(name);
            }
        }
        return headerString;
    }
}
