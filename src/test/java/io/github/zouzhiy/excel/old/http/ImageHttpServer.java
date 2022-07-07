/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.zouzhiy.excel.old.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.compress.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zouzhiy
 * @since 2022/7/6
 */
public class ImageHttpServer {

    private final static ImageHttpServer IMAGE_HTTP_SERVER = new ImageHttpServer();

    public static ImageHttpServer getInstance() {
        return IMAGE_HTTP_SERVER;
    }

    private boolean start = false;

    public void start() {
        if (start) {
            return;
        }
        HttpServer httpServer;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(18080), 0);
            httpServer.createContext("/jpg1.jpg", new ImageHandler());
            httpServer.setExecutor(Executors.newSingleThreadExecutor());
            httpServer.start();
            start = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static class ImageHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            OutputStream outputStream = httpExchange.getResponseBody();

            String exportTemplateFileName = "jpg1.jpg";
            String exportTemplateFilePath = "statics/image/" + exportTemplateFileName;
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(exportTemplateFilePath);
            assert inputStream != null;
            byte[] bytes = IOUtils.toByteArray(inputStream);

            Headers responseHeaders = httpExchange.getResponseHeaders();
            responseHeaders.set("Content-Disposition", "inline; filename*=UTF-8''" + exportTemplateFileName);
            responseHeaders.set("Content-Type", "image/jpeg");
            httpExchange.sendResponseHeaders(200, bytes.length);
            outputStream.write(bytes);
            outputStream.close();
        }
    }

    public static void main(String[] args) {
        ImageHttpServer.getInstance().start();

        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
