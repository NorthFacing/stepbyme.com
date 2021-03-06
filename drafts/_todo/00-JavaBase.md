
# Java基础

## 1、Collection

Collection是单列集合集合框架的根接口，也是List，Set，Queue的父接口。

### 1.1、Map

键值对形式存储，键值对以Entry类型的对象实例形式存在。



下面主要看HashMap

#### 1.1.1、HashMap 几个常量
初始容器大小：DEFAULT_INITIAL_CAPACITY = 16;  
最大容量大小：MAXIMUM_CAPACITY = 1 << 30;  
负载因子（扩容点）：DEFAULT_LOAD_FACTOR = 0.75f;  

在这里提到了两个参数：初始容量，加载因子。这两个参数是影响HashMap性能的
重要参数，其中容量表示哈希表中桶的数量，初始容量是创建哈希表时的容量，加
载因子是哈希表在其容量自动增加之前可以达到多满的一种尺度，它衡量的是一个
散列表的空间的使用程度，负载因子越大表示散列表的装填程度越高，反之愈小。
对于使用链表法的散列表来说，查找一个元素的平均时间是O(1+a)，因此如果负载
因子越大，对空间的利用更充分，然而后果是查找效率的降低；如果负载因子太小，
那么散列表的数据将过于稀疏，对空间造成严重浪费。系统默认负载因子为0.75，
一般情况下我们是无需修改的。


#### 1.1.2、HashMap 容器大小是2的指数级
```
  public static void main(String[] args) {
    int cap = 6;
    for (int i = 0; i < 10; i++) {
      System.out.println
          ("数值 i：" + i
              + ", 二进制：" + Integer.toBinaryString((cap - 1))
              + " & " + Integer.toBinaryString(i)
              + " => " + (i & (cap - 1))
              + " = "
              + Integer.toBinaryString(i & (cap - 1)));
    }
  }
```
key经过hash后，可以取模来进行放入数组，也不会出现越界的情况，
之所以没有使用取模，而是按位与的形式，是因为计算机的二进制运算效率比取模效率高。
如果Map的大小不是2的进制，我们设置为7
7的二进制是：111，（length-1）大小是6，按位与是和6进行，6的二进制是：110
结果如下，有些数组中的位置没有被设置，有些重复了，一是导致空间浪费，同时增加了碰撞的几率。

#### 1.1.3、HashMap 扩容
在添加属性的时候，每次都会判断一下是否需要扩容，若果达到了阀值（用到负载因子），
则进行扩容，扩容的时候会重新new一个table出来，然后新老数据数据进行转换，
调用transfer方法，transfer方法通过循环遍历的形式记性数据的“交接”，
注意一点，while里面的代码会造车在多线程并发下put出现死循环情况，如果涉及到
多线程put情况，不要使用HashMap。

#### 1.1.4、底层实现
HashMap是基于哈希表的Map接口的非同步实现。此实现提供所有可选的映射操作，并允许
使用null值和null键。
此类不保证映射的顺序，特别是不保证该顺序恒久不变。

HashMap的数据结构：数组 + 链表。首先使用数组对Key进行散列，如果此位置已经有元素，
就使用链表挂在此元素节点的后面。元素节点对象是entry，保持对value的引用。

#### equals 和 hashCode
equals为true时，hashCode肯定相等；
但是hashCode相等时，equals未必为true。
这也就是HashMap散列Key的时候出现位置被占用，然后使用链表挂载后续数据的原因。

#### 1.1.5、ConcurrentHashMap
在ConcurrentHashMap中采用了一种粒度更细的加锁机制，可以称为分段锁，
在这种锁机制下，允许任意数量的读线程并发地访问map，并且执行读操作的
线程和写操作的线程也可以并发的访问map，同时允许一定数量的写操作线程
并发地修改map，所以它可以在并发环境下实现更高的吞吐量。

#### fail-fast 机制

#### 如果让你来做HashMap扩容，如何实现在不影响读写的情况下扩容？
来自：https://www.zhihu.com/question/53673888

#### 1.1.6、WeakHashMap 和 HashMap 区别
WeakHashMap 的工作与 HashMap类似，但是使用弱引用作为key，意思是当key对象没有任何引用时，
key、value将会被回收。

#### 1.1.7、ArrayList 和 LinkedList 区别
ArrayList 底层的数据结构是数组，支持随机访问，
LinkedList底层实现是双向循环链表，不支持随机访问。
使用下标进行访问，他们的时间复杂度分别是 O(1) 和 O(n)

另外，ArrayList的初始化大小是10。（相对于HashMap的初始化大小是16=2^4）

#### foreach与正常for循环效率对比
直接for循环效率最高，其次是迭代器和 ForEach操作。 作为语法糖，其实 ForEach 编译成 字节码之后，使用的是迭代器实现的，反编译后，testForEach方法如下：
```
public static void testForEach(List list) {
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        Object t = iterator.next();
        Object obj = t;
    }
}
```
可以看到，只比迭代器遍历多了生成中间变量这一步，因为性能也略微下降了一些。


####  HashMap原理机制自问自答
来自：http://blog.csdn.net/wisgood/article/details/16342343

#### HashMap扩容机制、线程安全
来自：http://blog.csdn.net/u010558660/article/details/50926227

#### TreeMap

有序集合，是红黑树算法的实现

## 2、面向对象的三大特性

* 封装
* 继承
* 多态
  - 引用多态
  - 方法多态

继承：
类单继承，接口多继承

封装：
将内部属性对外不可见，只提供供外部访问的接口；以及基本数据类型的自动拆装箱

多态：
允许不同对象对同一消息做出不同的响应。

引用多态：父类的引用可以指向本类对象，也可以指向子类对象（牵扯出类型转换的问题），
引用多态的实现条件是继承（继承父类或者实现接口）

方法多态：子类的方法可以覆盖父类的方法，可以存在相同名称不同参数的方法，分别是覆写和重载

好处：
可替换性，可扩充性

虚拟机如何实现多态的：
动态绑定技术（dynamic binding），执行期间判断所引用的对象的实际类型，根据
实际类型调用对应的方法。

## 6 类和对象

### 6.1、不可变对象
不可变对象是指对象一旦被创建，状态就不能再改变。任何修改都会创建一个新的对象，
比如String，Integer以及其他包装类。

#### 6.1.1、能否创建一个包含可变对象的不可变对象
可以，但是要注意不要共享可变对象的引用。如果需要变化时，就返回原对象的一个拷贝。

### 6.2、克隆

首先，java.lang.Cloneable只是一个标示性接口，并不包含任何方法。
其次，clone()方法是在Object中定义的，并且此方法是一个本地方法，意味着它是由 C或者C++或者其他本地语言实现的。

#### 浅克隆 和 深克隆
浅克隆复制原对象的基本类型属性，大会直接饮用原对象的饮用对象。
深克隆会将所有的属性和方法都进行复制。

## 5、接口和抽象类
从JDK8开始接口支持默认实现方法，更多的可以从语义上进行区分：
接口定义一组规范，抽象类提取共有的方法。

| 比较 | 抽象类 | 接口|
| ---  | --- | --- |
|默认方法 | 抽象类可以有默认实现方法 | JDK8之前，不能含有默认实现方法|
|实现方式|通过extends继承抽象类，如果子类不是抽象类，需要提供父类生命的方法的实现|通过implements来实现接口，需要提供接口中所有声明的实现|
|构造器|抽象类中可以有构造器|接口不能有构造器|
|访问修饰符|可以有public，protected，default等修饰|只有默认public，不能使用其他修饰|
|多继承|只能存在一个父类|可以实现多个接口|
|添加新方法|添加新方法可以提供默认实现，不需要修改子类现有代码|如果添加新方法，JDK8之前的版本需要子类中进行实现|

## 8、基本数据类型

### 8.1、java中各类型占用的字节

首先，JVM中，基本数据类型的长度是固定的，与平台无关。

类型|字节数|位数
---|---|---
byte|1|8
short|2|16
char|2|16
int|4|32
float|4|32
long|8|64
double|8|64

### 8.2、3 * 0.1 == 0.3
返回 false，因为浮点数不能完全精确的表示出来，比如
```
System.out.println(3*0.1);
# 结果：0.30000000000000004
```

### 8.3、a=a+b 和 a+=b 区别
如果两个整形相加（比如byte，short，int）a+b操作会将a、b的类型提升为int之后进行计算，b+=a就不需要（显示）进行类型转换，但是相同的数值，两种计算方法的结果并不相同（待解决）：

```
byte a = 127;
byte b = 127;
b = (byte) (a + b);
System.out.println(b);
b += a;
System.out.println(b);

# 结果
-2
125
```

### 8.4、int和Integer区别

Integer是int的封装类，占用更多内存，因为除了基本数据之外，还要存储对象的元数据。

### 8.5、String，StringBuffer和StringBuilder区别
String，字符串常量，如果修改会产生新的对象，触发垃圾回收  
StringBuffer，字符串变量，线程安全  
StringBuilder，字符串变量，非线程安全

### 8.6、Java中的 ++ 是否线程安全？
非线程安全，它涉及到多个指令，如读取变量值、增加，然后存储回内存，这个过程
可能会出现多个线程交叉。


# 实现一个生产者消费者队列
两种方案：
* 使用阻塞队列（BlockingQueue）
* 使用wait-notify


JDK7提供了7个阻塞队列。分别是

* ArrayBlockingQueue ：一个由数组结构组成的有界阻塞队列。
* LinkedBlockingQueue ：一个由链表结构组成的有界阻塞队列。
* PriorityBlockingQueue ：一个支持优先级排序的无界阻塞队列。
* DelayQueue：一个使用优先级队列实现的无界阻塞队列。
* SynchronousQueue：一个不存储元素的阻塞队列。
* LinkedTransferQueue：一个由链表结构组成的无界阻塞队列。
* LinkedBlockingDeque：一个由链表结构组成的双向阻塞队列。

参考：
http://blog.csdn.net/monkey_d_meng/article/details/6251879
http://www.infoq.com/cn/articles/java-blocking-queue


# 加密

* 对称加密
    - DES算法（DES密钥长度不够）
    - 3DES算法
    - TDEA算法
    - Blowfish算法
    - RC5算法
    - IDEA算法。
* 非对称加密
    - RSA算法（公匙和密匙）
* hash加密
    - md5加密是典型应用

# 4、IO 相关

问题来源：
https://www.zhihu.com/search?type=content&q=java+%E9%9D%A2%E8%AF%95

## 3、IO流

字节流：InputStream,OutputStream => FileInputStream，OutInputStream
字符流：reader,writer => FileReader,FileWriter

字节流主要处理二进制文件，比如图片，压缩包等等；
字符流主要处理中文文字，一个字符占用两个字节。


## BIO，NIO，AIO（NIO2）
IO包和NIO包的内容相对来说不是很多，首先NIO模型要熟悉，特别是其中的selector
一定要非常清楚它的职责和实现原理。其实NIO的核心是IO线程池，一定要记住这个关
键点。有的时候，面试官可能也会问你IO包的设计模式（装饰器模式），为什么要这样
设计？有的面试官还会问你有没有更好的设计，这个时候如果你不知道请果断说自己现
在的水平有限，想不出来更好的设计，千万不要信口开河，随意YY。

同步 & 异步：
同步IO和异步IO，如何区分呢？首先一个IO操作其实分成了两个步骤：发起IO请求和
实际的IO操作，同步IO和异步IO的区别就在于第二个步骤是否阻塞，如果实际的IO读
写阻塞请求进程，那么就是同步IO，因此阻塞IO、非阻塞IO、IO复用、信号驱动IO都
是同步IO，如果不阻塞，而是操作系统帮你做完IO操作再将结果返回给你，那么就是
异步IO。
同步指的是用户进程触发IO操作并等待或者轮询的去查看IO操作是否就绪。
异步是指用户进程触发IO操作以后便开始做自己的事情，而当IO操作已经完成的时候
会得到IO完成的通知（异步的特点就是通知）。
同步和异步的概念描述的是用户线程与内核的交互方式：同步是指用户线程发起IO请
求后需要等待或者轮询内核IO操作完成后才能继续执行；而异步是指用户线程发起IO
请求后仍继续执行，当内核IO操作完成后会通知用户线程，或者调用用户线程注册的
回调函数。

阻塞 & 非阻塞：
阻塞IO和非阻塞IO的区别在于第一步，发起IO请求是否会被阻塞，如果阻塞直到完成
那么就是传统的阻塞IO，如果不阻塞，那么就是非阻塞IO。
所谓阻塞方式的意思是指, 当试图对该文件描述符进行读写时, 如果当时没有东西可
读,或者暂时不可写, 程序就进入等待 状态, 直到有东西可读或者可写为止。
非阻塞状态下, 如果没有东西可读, 或者不可写, 读写函数马上返回, 而不会等待。
阻塞和非阻塞的概念描述的是用户线程调用内核IO操作的方式：阻塞是指IO操作需要
彻底完成后才返回到用户空间；而非阻塞是指IO操作被调用后立即返回给用户一个状
态值，无需等到IO操作彻底完成。

BIO，同步阻塞式IO，简单理解：一个连接一个线程
NIO，同步非阻塞IO，简单理解：一个请求一个线程
AIO，异步非阻塞IO，简单理解：一个有效请求一个线程

BIO
在JDK1.4之前，用Java编写网络请求，都是建立一个ServerSocket，然后，客户端
建立Socket时就会询问是否有线程可以处理，如果没有，要么等待，要么被拒绝。
即：一个连接，要求Server对应一个处理线程。

NIO
在Java里的由来，在JDK1.4及以后版本中提供了一套API来专门操作非阻塞I/O，我们
可以在java.nio包及其子包中找到相关的类和接口。由于这套API是JDK新提供的I/O
API，因此，也叫New I/O，这就是包名nio的由来。这套API由三个主要的部分组成：
缓冲区（Buffers）、通道（Channels）和非阻塞I/O的核心类组成。在理解NIO的时
候，需要区分，说的是New I/O还是非阻塞IO,New I/O是Java的包，NIO是非阻塞IO
概念。这里讲的是后面一种。
NIO本身是基于事件驱动思想来完成的，其主要想解决的是BIO的大并发问题。NIO基
于Reactor，当socket有流可读或可写入socket时，操作系统会相应的通知引用程序
进行处理，应用再将流读取到缓冲区或写入操作系统。也就是说，这个时候，已经不
是一个连接就要对应一个处理线程了，而是有效的请求，对应一个线程，当连接没有
数据时，是没有工作线程来处理的。

AIO
与NIO不同，当进行读写操作时，只须直接调用API的read或write方法即可。这两种
方法均为异步的，对于读操作而言，当有流可读取时，操作系统会将可读的流传入
read方法的缓冲区，并通知应用程序；对于写操作而言，当操作系统将write方法传
递的流写入完毕时，操作系统主动通知应用程序。即可以理解为，read/write方法都
是异步的，完成后会主动调用回调函数。

参考：
http://www.cnblogs.com/fanzhidongyzby/p/4098546.html

## select/poll、epoll之间的区别
select/poll：遍历注册器上注册的所有fd（socket文件描述符），如果准备就绪进行读写；
epoll：使用事件驱动方式代替顺序扫描，性能更高，是select的升级解决方案。



## 



## 大数据架构解决方案

http://www.iteye.com/topic/1128561


## 参考
https://zhuanlan.zhihu.com/p/23533393
https://www.zhihu.com/question/50211894

