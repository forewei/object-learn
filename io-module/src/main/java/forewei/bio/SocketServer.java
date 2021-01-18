package forewei.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端   processon https://processon.com/diagraming/60053bd87d9c084cf9ed2089
 *
 * @auther forewei
 * @date 2021/1/18
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) {
            System.out.println("等待客户端连接！");
            // 阻塞方法
            Socket clientSocket = serverSocket.accept();
            System.out.println("有客户端连接了！");
            handler(clientSocket);
        }

    }

    private static void handler(Socket clientSocket) throws IOException {
        byte[] bytes = new byte[1024];
        System.out.println("准备Read.....");
        int read = clientSocket.getInputStream().read(bytes);
        System.out.println("Read完毕.....");
        if (read != -1) {
            System.out.println("接收到客户端的数据：" + new String(bytes, 0, read));
        }
        clientSocket.getOutputStream().write("Hello 客户端！".getBytes());
        clientSocket.getOutputStream().flush();

    }
}
