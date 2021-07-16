---
layout:     post
title:      MySQL - 复制
date:       2021-07-14 00:24:57 +0800
postId:     2021-07-14-00-24-57
categories: [database]

---

## MySQL 主从复制的作用

复制解决的基本问题是让一台服务器的数据与其他服务器保持同步，一台主库的数据可以同步到多台备
库上，备库本身也可以被配置成另外一台服务器的主库。主库和备库之间可以有多种不同的组合方式。

复制解决的问题：数据分布、负载均衡、备份、高可用性和故障切换、MySQL 升级测试。

## 两种复制方式

### 基于行的复制
### 基于语句的复制

基于语句的复制也称为逻辑复制，从 MySQL 3.23 版本就已存在， 基于行的复制方式在 5.1 版本才被加进来。
这两种方式都是通过在主库上记录二进制日志、在备库重放日志的方式来实现异步的数据复制。因此同一
时刻备库的数据可能与主库 存在不一致，并且无法包装主备之间的延迟。

MySQL 复制大部分是向后兼容的，新版本的服务器可以作为老版本服务器的备库，但是老版本不能作为新版本
服务器的备库，因为它可能无法解析新版本所用的新特性或语法，另外所使用的二进制文件格式也可能不同。

## 主从复制的步骤

1) 在主库上把数据更改记录到二进制日志中
2) 备库将主库的日志复制到自己的中继日志中
3) 备库 读取中继日志中的事件，将其重放到备库数据之上

第一步是在主库上记录二进制日志，每次准备提交事务完成数据更新前，主库将数据更新的事件记录到 
二进制日志中。MySQL 会按事务提交的顺序而非每条语句的执行顺序来记录二进制日志，在记录二进 
制日志后，主库会告诉存储引擎可以提交事务了。

下一步，备库将主库的二进制日志复制到其本地的中继日志中。备库首先会启动一个工作的 IO 线程， 
IO 线程跟主库建立一个普通的客户端连接，然后在主库上启动一个特殊的二进制转储线程，这个线程会 
读取主库上二进制日志中的事件。它不会对事件进行轮询。如果该线程追赶上了主库将进入睡眠状态，直到
主库发送信号量通知其有新的事件产生时才会被唤醒，备库 IO 线程会将接收到的事件记录到中继日志中。

备库的 SQL 线程执行最后一步，该线程从中继日志中读取事件并在备库执行，从而实现备库数据的更新。
当 SQL 线程追赶上 IO 线程时，中继日志通常已经在系统缓存中，所以中继日志的开销很低。SQL 线程
执行的时间也可以通过配置选项来决定是否写入其自己的二进制日志中。

## 参考资料

* [test](test.html)