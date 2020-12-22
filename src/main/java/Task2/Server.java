package Task2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("127.0.0.1", 23445));

        while (true) {
            SocketChannel socketChannel = serverChannel.accept();
            ByteBuffer buffer = ByteBuffer.allocate(2 << 10);

            while (socketChannel.isConnected()) {
                int bytesCount = socketChannel.read(buffer);
                if (bytesCount == -1) {
                    break;
                }
                String msg = new String(buffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                buffer.clear();
                String result = msg.replaceAll(" ", "").trim();
                socketChannel.write(ByteBuffer.wrap(result.getBytes(StandardCharsets.UTF_8)));
            }
        }
    }
}
