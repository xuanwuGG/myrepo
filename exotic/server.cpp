/* ************************************************************************
> File Name:     server.cpp
> Author:        xuanwuGG
> -----------------------------------------------------------------
> Created Time:  2023年04月22日 星期六 19时24分29秒
> Description:socket套接字就是利用三元组［ｉｐ，协议，端口］解决网络通信的中间件工具，有两种数据传输方式，SOCK_STREAM:面向连接的数据传输方式，SOCK_DGRAM:无连接的数据传输方式   
 ************************************************************************/
#include<stdlib.h>
#include<iostream>
#include<sys/socket.h>
#include<arpa/inet.h>
#include<netinet/in.h>
#include<cstring>
#include<string>
#include<unistd.h>
int main()
{
    int server_sock = socket(AF_INET,SOCK_STREAM,IPPROTO_TCP);//创建套接字,使用TCP传输协议k
    struct sockaddr_in serv_addr;//创建ＩＰ地址与端口，以便之后和套接字bind
    memset(&serv_addr,0,sizeof(serv_addr));//将serv_addr全部内容置０
    serv_addr.sin_family=AF_INET;//使用IPV4地址，AF_INET6是IPV6地址
    serv_addr.sin_addr.s_addr=inet_addr("127.0.0.1");//该函数返回一个地址（如果符合地址大小的话），127...是本机地址
    serv_addr.sin_port= htons(1234);//创建端口，htons函数将主机字节顺序返回成TCP/IP网络字节顺序的值
    bind(server_sock,(struct sockaddr*)&serv_addr,sizeof(serv_addr));//将套接字和IP，端口绑定
    listen(server_sock,5);//监听客户端是否有请求
    struct sockaddr_in client_addr;
    socklen_t client_size=sizeof(client_addr);
    int client_sock=accept(server_sock,(struct sockaddr*)&client_addr,&client_size);
    char s[]="波坡摸佛";
    write(client_sock,s,sizeof(s));
}
