#基础镜像使用
FROM java:8
#作者
MAINTAINER Meta39
#指定临时文件目录为tmp。在主机/var/lib/docker目录下创建了一个临时文件并链接到容器的/tmp
VOLUME /tmp
#将jar包添加到容器中并更改为相应的名称
ADD demo.jar demo.jar
#运行jar包
ENTRYPOINT ["nohup","java","-jar","/demo.jar","&"]
#暴露端口
EXPOSE 81