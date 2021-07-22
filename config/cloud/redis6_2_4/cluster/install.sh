###########################################
# 集群模式
Redis集群(Redis Cluster)是从 Redis 3.0 开始引入的分布式存储方案。集群由多个节点(Node)组成，Redis 的数据分布在这些节点中。

集群中的节点分为主节点和从节点，只有主节点负责读写请求和集群信息的维护，从节点只进行主节点数据和状态信息的复制。

作用

  Redis集群的作用有下面几点：

  数据分区：突破单机的存储限制，将数据分散到多个不同的节点存储；

  负载均衡：每个主节点都可以处理读写请求，提高了并发能力；

  高可用：集群有着和哨兵模式类似的故障转移能力，提升集群的稳定性；

原理

  数据分区规则

  衡量数据分区方法的标准有两个重要因素：1) 是否均匀分区; 2)增减节点对数据分布的影响;

  由于哈希算法具有随机性，可以保证数据均匀分布，因此Redis集群采用哈希分区的方式对数据进行分区，哈希分区就是对数据的特征值进行哈希，然后根据哈希值决定数据放在哪里。

常见的哈希分区有：

  哈希取余：

  计算key的hash值，对节点数量做取余计算，根据结果将数据映射到对应节点；但当节点增减时，系统中所有数据都需要重新计算映射关系，引发大量数据迁移；

  一致性哈希：

  将hash值区间抽象为一个环形，节点均匀分布在该环形之上，然后根据数据的key计算hash值，在该hash值所在的圆环上的位置延顺时针行走找到的第一个节点的位置，该数据就放在该节点之上。相比于哈希取余，一致性哈希分区将增减节点的影响限制为相邻节点。

  例：在AB节点中新增一个节点E时，因为B上的数据的key的hash值在A和B所在的hash区间之内，因此只有C上的一部分数据会迁移到B节点之上；同理如果从BCD中移除C节点，由于C上的数据的key的hash值在B和C所在的hash区间之内，因此C上的数据顺时针找到的第一个节点就是D节点，因此C的数据会全部迁移到D节点之上。但当节点数量较少的时候，增删节点对单个节点的影响较大，会造成数据分布不均，如移除C节点时，C的数据会全部迁移到D节点上，此时D节点拥有的数据由原来的1/4变成现在的1/2，相比于节点A和B来说负载更高。

带虚拟节点的一致性哈希 (Redis集群)：

Redis采用的方案，在一致性哈希基础之上，引入虚拟节点的概念，虚拟节点被称为槽(slot)。Redis集群中，槽的数量为16384。

槽介于数据和节点之间，将节点划分为一定数量的槽，每个槽包含哈希值一定范围内的数据。由原来的hash-->node 变为 hash-->slot-->node。

当增删节点时，该节点所有拥有的槽会被重新分配给其他节点，可以避免在一致性哈希分区中由于某个节点的增删造成数据的严重分布不均。

通信机制

  在上面的哨兵方案中，节点被分为数据节点和哨兵节点，哨兵节点也是redis服务，但只作为选举监控使用，只有数据节点会存储数据。
  而在Redis集群中，所有节点都是数据节点，也都参与集群的状态维护。

  在Redis集群中，数据节点提供两个TCP端口，在配置防火墙时需要同时开启下面两类端口：

    普通端口：即客户端访问端口，如默认的6379；

    集群端口：普通端口号加10000，如6379的集群端口为16379，用于集群节点之间的通讯；

  集群的节点之间通讯采用Gossip协议，节点根据固定频率(每秒10次)定时任务进行判断，当集群状态发生变化，如增删节点、槽状态变更时，会通过节点间通讯同步集群状态，使集群收敛。

  集群间发送的Gossip消息有下面五种消息类型：

    MEET：在节点握手阶段，对新加入的节点发送meet消息，请求新节点加入当前集群，新节点收到消息会回复PONG消息；

    PING：节点之间互相发送ping消息，收到消息的会回复pong消息。ping消息内容包含本节点和其他节点的状态信息，以此达到状态同步；

    PONG：pong消息包含自身的状态数据，在接收到ping或meet消息时会回复pong消息，也会主动向集群广播pong消息；

    FAIL：当一个主节点判断另一个主节点进入fail状态时，会向集群广播这个消息，接收到的节点会保存该消息并对该fail节点做状态判断；

    PUBLISH：当节点收到publish命令时，会先执行命令，然后向集群广播publish消息，接收到消息的节点也会执行publish命令

集群限制

  由于Redis集群中数据分布在不同的节点上，因此有些功能会受限：

  db库：单机的Redis默认有16个db数据库，但在集群模式下只有一个db0；

  复制结构：上面的复制结构有树状结构，但在集群模式下只允许单层复制结构；

  事务/lua脚本：仅允许操作的key在同一个节点上才可以在集群下使用事务或lua脚本；(使用Hash Tag可以解决)

  key的批量操作：如mget,mset操作，只有当操作的key都在同一个节点上才可以执行；(使用Hash Tag可以解决)

  keys/flushall：只会在该节点之上进行操作，不会对集群的其他节点进行操作；

Hash Tag:

  上面介绍集群限制的时候，由于key被分布在不同的节点之上，因此无法跨节点做事务或lua脚本操作，但我们可以使用hash tag方式解决。

  hash tag：当key包含{}的时候，不会对整个key做hash，只会对{}包含的部分做hash然后分配槽slot；因此我们可以让不同的key在同一个槽内，这样就可以解决key的批量操作和事务及lua脚本的限制了；

  但由于hash tag会将不同的key分配在相同的slot中，如果使用不当，会造成数据分布不均的情况，需要注意。

集群参数优化

  cluster_node_timeout：默认值为15s。

  影响ping消息接收节点的选择，值越大对延迟容忍度越高，选择的接收节点就越少，可以降低带宽，但会影响收敛速度。应该根据带宽情况和实际要求具体调整。

  影响故障转移的判定，值越大越不容易误判，但完成转移所消耗的时间就越长。应根据网络情况和实际要求具体调整。

  cluster-require-full-coverage

  为了保证集群的完整性，只有当16384个槽slot全部分配完毕，集群才可以上线，但同时，若主节点发生故障且故障转移还未完成时，原主节点的槽不在任何节点中，集群会处于下线状态，影响客户端的使用。

  该参数可以改变此设定：

  no:  表示当槽没有完全分配时，集群仍然可以上线；

  yes: 默认配置，只有槽完全分配，集群才可以上线。


#  搭建集群
从Redis5之后我们就可以直接使用redis-cli --cluster命令自动部署Redis集群了，所以本篇也直接使用该方式搭建集群。

机器上使用三主三从的方式部署Redis集群：

cp /opt/taotao-cloud/redis-6.2.4/redis.conf redis_7001.conf
cp /opt/taotao-cloud/redis-6.2.4/redis.conf redis_7002.conf
cp /opt/taotao-cloud/redis-6.2.4/redis.conf redis_7003.conf
cp /opt/taotao-cloud/redis-6.2.4/redis.conf redis_7004.conf
cp /opt/taotao-cloud/redis-6.2.4/redis.conf redis_7005.conf
cp /opt/taotao-cloud/redis-6.2.4/redis.conf redis_7006.conf

daemonize yes
port 7000
bind 172.16.3.240
requirepass taotao-cloud
masterauth taotao-cloud

# 在 REDIS CLUSTER 配置模块下开启以下配置
# 开启redis集群支持
cluster-enabled yes
# 集群配置文件，redis首次启动时会在redis.conf所在的文件夹下自动创建该文件，注意这里的node-7000.conf要根据实例启动的端口号自行修改
cluster-config-file /opt/taotao-cloud/redis-6.2.4/cluster/node-7000.conf
# pidfile的端口号也需要根据实际启动的端口号自行修改
pidfile /opt/taotao-cloud/redis-6.2.4/pid/redis_7000.pid
# 请求超时时间
cluster-node-timeout 15000


部署集群需要先启动各个节点的服务，此时这些节点都没加到集群中，使用redis-cli --cluster create xxx命令创建集群：

bin/redis-cli --cluster create 127.0.0.1:6381 127.0.0.1:6382 127.0.0.1:6383 127.0.0.1:6391 127.0.0.1:6392 127.0.0.1:6393 --cluster-replicas 1#这里的--cluster-replicas表示每个主节点有几个副本节点









