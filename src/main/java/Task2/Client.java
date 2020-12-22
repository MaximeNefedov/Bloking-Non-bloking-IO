package Task2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 23445);
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(socketAddress);

        try (Scanner scanner = new Scanner(System.in)) {
            final ByteBuffer buffer = ByteBuffer.allocate(2 << 10);
            String msg;
            while (true) {
                System.out.println("Введите строку, у которой хотите удалить все пробелы или \"end\" для выхода");
                msg = scanner.nextLine();
                if (msg.equals("end")) {
                    break;
                }
                socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
                Thread.sleep(2000);
                int bytesCount = socketChannel.read(buffer);
                String result = new String(buffer.array(), 0, bytesCount);
                System.out.println(result);
                buffer.clear();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            socketChannel.close();
        }

    }
}
