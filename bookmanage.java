package BOOK;





import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.net.InetSocketAddress;

public class bookmanage {

    public static void main(String[] args) {

        try {

            // Create server on port 8006
            HttpServer server = HttpServer.create(new InetSocketAddress(8008), 0);

            // Create endpoint
            server.createContext("/book", new BookHandler());

            // Start server
            server.start();

            System.out.println("Server running at http://localhost:8008/book");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handler class
    static class BookHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) {

            try {

                // Example URL:
                // http://localhost:8006/book?name=JavaProgramming&id=101&price=500

                String query = exchange.getRequestURI().getQuery();

                String[] params = query.split("&");

                String bookName = params[0].split("=")[1];
                int bookId = Integer.parseInt(params[1].split("=")[1]);
                int price = Integer.parseInt(params[2].split("=")[1]);

                String category;

                // Book category based on price
                if (price >= 500) {
                    category = "Premium Book";
                } else {
                    category = "Regular Book";
                }

                // Response
                String response =
                        "Book Name : " + bookName +
                        "\nBook ID : " + bookId +
                        "\nPrice : " + price +
                        "\nCategory : " + category;

                // Send response
                exchange.sendResponseHeaders(200, response.length());

                OutputStream os = exchange.getResponseBody();

                os.write(response.getBytes());

                os.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}