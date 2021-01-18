package forewei.bio;

import java.io.IOException;
import java.net.Socket;

/**
 * 客户端
 * @auther forewei
 * @date 2021/1/18
 */
public class SocketClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9000);
        // 向服务端发送数据
        socket.getOutputStream().write("Hello 服务端".getBytes());
        socket.getOutputStream().flush();
        System.out.println("向服务端发送消息结束！");
        byte[] bytes = new byte[1024];
        // 接收服务端传回的消息
        socket.getInputStream().read(bytes);
        System.out.println("接收服务端的消息：" + new String(bytes));
        socket.close();
    }
}
