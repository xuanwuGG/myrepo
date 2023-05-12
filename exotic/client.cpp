/* ************************************************************************
> File Name:     client.cpp
> Author:        xuanwuGG
> -----------------------------------------------------------------
> Created Time:  2023年04月22日 星期六 20时34分27秒
> Description:   
 ************************************************************************/
#include<iostream>
#include<string.h>
#include<stdlib.h>
#include<unistd.h>
#include<arpa/inet.h>
#include<sys/socket.h>
int main()
{
    int sock=socket(AF_INET,SOCK_STREAM,0);//创建套接字
    struct sockaddr_in serv_addr;
    memset(&serv_addr,0,sizeof(serv_addr));
    serv_addr.sin_family=AF_INET;
    serv_addr.sin_addr.s_addr=inet_addr("127.0.0.1");
    serv_addr.sin_port=htons(1234);//创建特定端口和IP地址的服务器，以便客户端向其请求服务
    connect(sock,(struct sockaddr*)&serv_addr,sizeof(serv_addr));//连接服务器
    char con[99];
    read(sock,con,sizeof(con)-1);
    printf("来自服务器的回复：%s/n",con);
    close(sock);
    return 0;
}

