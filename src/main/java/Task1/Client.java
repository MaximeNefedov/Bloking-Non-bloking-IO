package Task1;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
//    способ взаимодействия Blocking был выбран из-за того, что нам
//    в контексте этой задачи принципиально важно гарантированно сразу
//    получить полный ответ от сервера (например, может возникнуть такая ситуация, что
//    слишком большое число Фибоначчи будет превышать размер буфера и клиентская сторона получит
//    на текущей итерации невалидное значение)
//    Также, мы не собираемся
//    проводить никакой параллельной работы во время вычислений, а просто ждем итоговое значение
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 23444);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Введите число Фибоначчи или \"end\" для выхода");
                String number = scanner.nextLine();
                if (number.equals("end")) {
                    break;
                }
                String correctedNumber = number.replaceAll(("[\\D]+"), "");
                if (correctedNumber.isEmpty()) {
                    System.out.println("Некорректный ввод");
                } else {
                    out.println(correctedNumber);
                    System.out.println(correctedNumber + "-ое число Фибоначчи равно: " + in.readLine());
                }
            }
        }
    }
}
