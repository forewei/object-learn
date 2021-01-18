package forewei.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * nio非阻塞代码示例
 *
 * 总结：如果连接数太多的话，会有大量的无效遍历，假如有10000个连接，其中只有1000个连接有写数据，但是由于其他9000个连接并没有断开，
 * 我们还是要每次轮询遍历一万次，其中有十分之九的遍历都是无效的，这显然不是一个让人很满意的状态。
 * @auther forewei
 * @date 2021/1/18
 */
public class NioServer {
    static List<SocketChannel> channelList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // 创建NIO ServerSocketChannel,与BIO类似
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        // 设置ServerSocketChannel为非阻塞
        serverSocketChannel.configureBlocking(false);
        System.out.println("服务启动成功！");

        while (true) {
            // 非阻塞模式accept方法不会阻塞,否则会阻塞
            // NIO的非阻塞是由操作系统内部实现的,底层调用了linux内核的accept函数
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                System.out.println("连接成功！");
                // 设置socketChannel为非阻塞
                socketChannel.configureBlocking(false);
                // 保存socket连接到list中
                channelList.add(socketChannel);
            }
            Iterator<SocketChannel> iterator = channelList.iterator();
            while (iterator.hasNext()) {
                SocketChannel sc = iterator.next();
                ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                // 非阻塞模式read方法不会阻塞，否则会阻塞
                int len = sc.read(byteBuffer);
                if (len > 0) {
                    System.out.println("接收消息：" + new String(byteBuffer.array()));
                } else if (len == -1) {//如果客户端断开,把socket从list移除
                    iterator.remove();
                    System.out.println("客户端断开连接");
                }

            }
        }


    }
}
