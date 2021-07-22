###########################################
cd /opt/taotao-cloud/rocketmq4.9.0

wget https://apache.website-solution.net/rocketmq/4.9.0/rocketmq-all-4.9.0-bin-release.zip

zip rocketmq-all-4.9.0-bin-release.zip

# RocketMQ默认的虚拟机内存较大，启动Broker如果因为内存不足失败，需要编辑如下两个配置文件，修改JVM内存大小
vi runbroker.sh
vi runserver.sh

JAVA_OPT="${JAVA_OPT} -server -Xms256m -Xmx256m -Xmn128m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m"

# 测试RocketMQ
发送消息
  # 1.设置环境变量
  export NAMESRV_ADDR=localhost:9876
  # 2.使用安装包的Demo发送消息
  sh bin/tools.sh org.apache.rocketmq.example.quickstart.Producer

接收消息
  # 1.设置环境变量
  export NAMESRV_ADDR=localhost:9876
  # 2.接收消息
  sh bin/tools.sh org.apache.rocketmq.example.quickstart.Consumer

# rocketmq-console控制台
java -jar /Users/gongninggang/source/rocketmq-externals/rocketmq-console/target/rocketmq-console-ng-1.0.1.jar

# mqadmin使用
sh mqadmin
sh mqadmin help topicList
sh mqadmin topicList -n '172.16.3.240:9876'

##################### rocketmq.sh #############################
#!/bin/bash

function start_rocketmq() {
     nohup sh /opt/taotao-cloud/rocketmq4.9.0/bin/mqnamesrv >/opt/taotao-cloud/rocketmq4.9.0/mqnamesrv.log 2>&1 &
     nohup sh /opt/taotao-cloud/rocketmq4.9.0/bin/mqbroker -n 172.16.3.240:9876 >/opt/taotao-cloud/rocketmq4.9.0/broker.log 2>&1 &
     sleep 10
     echo " rocketmq started"
}

function stop_rocketmq() {
     sh /opt/taotao-cloud/rocketmq4.9.0/bin/mqshutdown namesrv
     sh /opt/taotao-cloud/rocketmq4.9.0/bin/mqshutdown broker
     sleep 10
     echo "rocketmq stoped"
}

case $1 in
"start")
    start_rocketmq
    ;;
"stop")
    stop_rocketmq
    ;;
"restart")
    stop_rocketmq
    sleep 15
    start_rocketmq
    ;;
*)
    echo Invalid Args!
    echo 'Usage: '$(basename $0)' start|stop|restart'
    ;;
esac

