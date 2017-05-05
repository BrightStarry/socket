###Socket编程DEMO
包括了NIO/AIO/Netty
    c + a + u 显示类图
    c + a + h 调用链
    准备写一个使用多线程的socket聊天程序，多人聊天.（很讲究的话，失败了，不是很讲究的话，很简单。）
---
2017年5月2日 17:12:22 
从这里开始是根据尚学堂的架构师课程，进行socket、NIO等内容的学习。
---
    同步与异步：
        如果你烧水，然后一直等待它完成，这种情况，就是同步的。
        如果烧水，然后去玩了，等它烧好了再回来，就是异步的。
    阻塞与非阻塞:
        如果你烧水，并且这时候你爸妈（其他线程）看着你烧水，这就是阻塞的。
        如果你烧水，并且这时候你爸妈可以去干其他事，这就是非阻塞的。
    (以上貌似不对)
    不过对于IO来说，阻塞好像是指操作IO时，线程需要一直等待；非阻塞是指，操作IO，可以获取准备好的数据，无需等待。
    然后同步异步，貌似是针对操作系统来说的，同步是指程序直接参与了操作系统底层的IO操作；异步是通知操作系统进行IO操作，然后直接获取到完成的IO数据。
    同步说的是程序，阻塞说的是技术，接收数据的方式（NIO是调用后，通过轮询的方式处理的，所以是非阻塞）
    本质上来说，同步异步、阻塞非阻塞不是同一种概念，同步和异步是消息的通知机制的概念，阻塞非阻塞是指进程的状态 
    
        
    伪异步：
            是通过线程池实现的。传统情况下，socket通信，每有一个socket客户端连接进来，就会开启一个新的线程去处理这个连接。
        很容易造成宕机。。。
            而引入线程池之后，限定了最大线程数以及一个阻塞队列，反正线程永远不会超过使系统宕机的阈值，
            然后每当有一个客户端连接进来，将这个socket封装成一个task（实现Runnable）,就会使用线程池启动一个它。
---
TODO 暂且再记下，等以后，一定要把Netty权威指南好好看一遍。现在为了找工作，没时间
---
NIO：NewIO，Non-BlockIO 
    NIO是同步非阻塞的，所以对于一个IO操作来说，调用IO操作的那个线程还是要等到IO操作结束才能干其他事。但是这个时候是不会占着CPU时间的。 
    
    Buffer缓冲区：
        所有基本数据类型都有缓冲区(除了Boolean)，但是最常用的是ByteBuffer,而且ByteBuffer比其他buffer多一些方法，因为通常是通过它进行一些其他操作。
        BIO中数据是写入或读取到Stream(流)中的。
        NIO所有的数据都是用Buffer处理的。
        Buffer实质是一个数组，且通常是一个字节数组。
    Channel通道:
        全双工，可以双向读写，且可二者同时进行。
        分为网络读写的SelectableChannel和文件读写的FileChannel.
        SocketChannel和ServiceSocketChannel属于SelectableChannel的子类。
    Selector选择器、多路复用器：
        提供了选择已经就绪的任务的能力。
        轮询所有注册了它的Channel，根据通道状态，执行相关操作。
        例如当Channel处于就绪状态，就会被轮询出来，然后通过SelectionKey获取就绪的Channel集合,最后进行IO操作.
        通道状态：
            1.Connect :连接状态
            2.Accept : 阻塞状态
            3.Read : 可读状态
            4.Write : 可写状态 
        且一个Selector(一个线程)可以负责无限个Channel.
        
        当Channel注册到Selector后,Selector会分配给每个Channel一个Key,后续轮询将通过key查找Channel.（注册过去的应该就是这个key）
    
    （以下为个人理解）
    打完了例子，我来理解下NIO的这个Socket通信。
        首先，ServerSocketChannel打开，然后将自己注册到Selector中，被监听阻塞事件。
        然后当客户端(SocketChannel)连接到服务端时，服务端就会被阻塞，此时Selector轮询到这个阻塞信息，进行处理。
        在处理方法中调用ServerSocketChannel.accept()方法，据我猜测，此时这个方法应该不是阻塞的，而是能直接获取刚才连接过来的客户端对象。
        然后将该客户端对象（SocketChannel）也注册到这个Selector中，被监听其读取状态。也就是当这个客户端往服务器写入数据，它的状态就变成了读取状态。
        然后就可以使用读取处理方法处理了。
    由此，也就是说，NIO所谓的同步非阻塞，是指，当监听到通道的一个状态，进行IO处理的时候，Selector线程还是只能进行这个操作，所以是同步的。但是此时，其他
    Channel还可以同服务器进行读写操作，所以是非阻塞的。
---
AIO:NIO2.0  
    AIO是异步非阻塞的。
    
    服务端:
        1.使用一个线程池（ExecutorService）创建一个AsynchronousChannelGroup（异步通道组）对象。应该是使用线程池启用多个通道。
        2.打开AsynchronousServerSocketChannel，使用异步通道组。
        3.绑定端口，然后调用accept()方法等待客户端请求（这个方法中定义了如果收到连接请求，使用哪个类（回调函数）处理），
            但是注意，因为是异步的，那么它执行这个方法，就会继续向下执行，所以需要把它阻塞在这下面（如果不希望它关闭）.
        4.收到请求，进入回调函数。此处应该是创建了一个handler类处理这个连接。
            在handler中，因为有这个服务端this的引用。所以在连接成功，继续下面的操作之前，还需要再执行一次服务端的accept()方法
            让服务端继续监听下一个请求，如此递归。
    客户端：
        打开通道，建立连接就ok了。
---
Netty:NIO框架（未使用AIO，说是可能有bug以及性能并没有提高），但它已经支持异步了。
    http://ifeve.com/netty5-user-guide/
    服务端：
        1.创建两个NIO线程组，一个用于网络事件处理(接收客户端连接),另一个进行网络读写.
        2.创建一个ServerBootstrap，配置一系列参数。
        3.创建一个实际处理数据的类。
        4.绑定端口，执行同步阻塞方法等待
        
        
    处理类：
        自定义处理类继承ChannelHandlerAdapter类，这个类实现了ChannelHandler接口。
        重写该类的channelRead()方法，进行读取操作，还有exceptionCaugh()进行异常捕获。
    
    NioEventLoopGroup是用来处理IO操作的线程事件循环器、
        第一个（例子Server类中的bossGroup）是轮询监听客户端连接的。一旦连接了。
        就将该连接注册到workGroup中，进行读写事件的轮询监听。
        
    Netty中，数据写入到缓冲区后，并不会写入到通道，必须调用flush方法，才能真正的把数据写入通道。
        这是为了防止异步操作过多的调用写入操作。
    
    handler类中：
        //释放缓冲区的数据
        ReferenceCountUtil.release(msg);
        如果有write方法，netty会自动释放，无需写出这句话。（针对一个方法中）
        
        如果想知道一个异步的写入操作是否完成，完成后关闭连接，可以添加一个监听器.(详见例子)
    
    关于运行netty的程序。
        可以直接放在web项目中，使用tomcat启动。但是这样一旦web项目停止，socket也就停止了。
        所以还可以单独打成jar，部署在服务器上。然后如果想将数据存进数据库之类的，
            可以再和web项目建立通信，也可以将dao层打成jar包，部署过去（应该是这个意思？）
    
    TCP
        是面向流的协议。也就是所有数据都是河流一样没有界限的。
        所以，假如客户端向服务端发送A、B两个包。可能会发生
        A包的部分内容和B包的全部或部分内容被一次读取——粘包。
        也可能A包过大，被分成了两次读取——拆包。
        解决方案：
            1.消息定长，例如固定每个报文200字节，如果不够，空格补位。
            2.在包尾部加特殊字符分割，例如回车等。
            3.将消息氛围消息头和消息体，消息头中包含消息总长度的字段,还可以确保安全性
    Netty中对粘包、拆包的解决方案
        1.分隔符类 DelimiterBasedFrameDecoder(自定义分隔符)，
            也有定义好了的LineBasedFrameDecoder（回车分隔） 在传输的字符末尾加上System.getProperty("line.separator")
        2.FixedLengthFrameDecoder(定长)
        3.自定义消息头和消息体。然后还有编码器和解码器。相当于使用了自定义的对象进行传输
            百度 netty 自定义协议  http://www.jianshu.com/p/ba21eb32ae97
            
    
    Nettry编解码：
        说白了就是java序列化技术。
        主流序列化编解码框架：
            google的 protobuf
            基于protobuf的Kyro
            thrift
            JBoss的 Marshalling
            MessagePack框架
        注意，使用序列化框架的时候也要考虑到读半包（粘包、拆包）问题 http://www.tuicool.com/articles/r6JBzmz
    
    如果需要传输文件，一般需要压缩成byte[]。使用一些工具类。详见GZipUtils类。另外netty自带了压缩的处理类。
    System.getProperty("java.version")可以获取各种底层信息，详见TestSystemProperty类。
    
    其实可以直接传json对象，可以解决跨语言。也可以使用自定义对象。
    
    Netty可以实现WebSocket。具体看websocket包。据说性能是最高的websocket（各类语言实现中都是）
        http://blog.csdn.net/u010739551/article/details/51423538
        感觉可以把我的毕业设计的websocket换成这个。包中的例子并不好，，看不太懂。这个网址上的稍后可以看看。
        有个问题，就是关于这种方法实现webSocket后，网上找不到半点关于获取到session的方法：
            我想到的方法是，用户登陆后，分配它一个加密了的字符（token）,然后每次发送信息，需要提交token，
            然后根据token去查询是哪个用户。
        
    Netty最佳实践：数据通信
        通信三种方式：
            1.保持长连接。如果连接较少，可以。
            2.短连接，在客户端弄个缓冲区，满了就提交一次。或者类似轮询，提交数据。无法做到实时性。
            3.特殊的长连接，就是超时（一段时间没有同信）后断开连接，然后下次客户端发起请求，再次连接。
                但是需要考虑两个因素，
                    1，如何超时关闭通道，关闭后如何再次连接  Netty自带了。
                    2.服务器宕机怎么办  可以使用spring的定时任务，定时检查是否正常运行。也可以写个bat脚本，如果不运行了，重启
    
        具体实例runtime包：
            主要就是client类中，连接后，如果连接断开，就再次连接
    
    Netty实现Http协议：
        见http包
        
    
    Netty文件上传下载：
           Netty上传文件时，会将文件分块上传，多次发送http请求。。
           这个代码真的是太多了。。先不看了。还有什么断点续传什么的。
           ChunkedWriteHandler、FileRegion
    
    Netty心跳检测：
        