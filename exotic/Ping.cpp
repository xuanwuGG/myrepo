/* *********************************************************************
> File Name:     tmp.cpp
> Author:        xuanwuGG
> -----------------------------------------------------------------
> Created Time:  2023年04月24日 星期一 19时55分55秒
> Description:   
 ************************************************************************/
/*
** time:2020.12.27
** 功能:实现ping命令
** 环境：ubuntu
*/

#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<netinet/in.h>
#include<netinet/ip.h>
#include<netinet/ip_icmp.h>
#include<signal.h>
#include<arpa/inet.h>
#include<sys/time.h>
#include<string.h>
#include<netdb.h>
#include<pthread.h>


#define PACK_SIZE 4096


//使用的结构声明
char send_packet[PACK_SIZE]; //发送报文结构
char recv_packet[PACK_SIZE]; //接收报文结构
int sockfd;                  //套接字
pid_t pid;                   //进程号
int begin=0,end=0;           //记录发收的包的数量
int datalen=56;              //icmp数据长度，注意完成数据报文最小为64bytes，不是56bytes
struct timeval recv_time;    //记录某一刻的时间
struct sockaddr_in from; 


//使用的函数声明
void* send_data(void* arg);                       //发数据函数
void* recv_data(void* arg);                       //收数据函数
unsigned short get_cksum(unsigned char*,int);     //校验和计算
void time_sub(struct timeval*,struct timeval*);   //计算时间差
int pack(int pack_no);
int unpack(char* buf,int len);

//各个函数实现
//报文发送函数
void* send_data(void* arg) 
{
   int packetsize;
   int i=0;       
   for(;i<4;i++)
   {       
        packetsize=pack(i);   
       if(sendto(sockfd,send_packet,packetsize,0,(struct sockaddr*)&from,sizeof(from))<0)   //发送报文
       {
            //失败    
            perror("sendto");
            continue;
       }
       else 
       {
            begin++;               //成功后，休眠1s 
            sleep(1);
        }
    }
    return NULL;
}


//报文接收数据
void* recv_data(void* arg)
 {
      int n,fromlen;
      fromlen=sizeof(from);
      int i=0;
      while(i<4)           
      {
          if((n=recvfrom(sockfd,recv_packet,sizeof(recv_packet),0,(struct sockaddr*)&from,(socklen_t*)&fromlen))<0)     //收数据
          {
               continue;
          }
          if(unpack(recv_packet,n)==-1) //解包失败
          {
              i++;
              continue;  
          } 
          else
          {
               end++; //成功
           }
       }   
 }      


 //校验和计算
unsigned short get_cksum(unsigned char* data,int len) //求检验和
 {
  int sum=0;
  int odd=(len&0x01);
  while(len&0xfffe)
  {
   sum+=*(unsigned short*)data;
   data+=2;
   len-=2;
  }
  if(odd)
  {
   unsigned short tmp=((*data)<<8)&0xff00;
   sum+=tmp;
  }   
  sum=(sum>>16)+(sum&0xffff);
  sum+=(sum>>16);
  return ~sum; 
 }


//构建包
int pack(int pack_no)
 {
  int size;      
  struct icmp* myicmp;
  myicmp=(struct icmp*)send_packet;
  //对相应字段填充
  myicmp->icmp_type=ICMP_ECHO;
  myicmp->icmp_code=0;
  myicmp->icmp_cksum=0;
  myicmp->icmp_seq=pack_no;
  myicmp->icmp_id=pid;
  size=8+datalen; //总的icmp大小
  //将时间信息放入data字段
  struct timeval*send_time=(struct timeval*)myicmp->icmp_data;
  gettimeofday(send_time,NULL);
  //注意检验和放最后，因为数据也要进行校验
  myicmp->icmp_cksum=get_cksum((unsigned char*)send_packet,size);
  return size;
 }


//拆解包
int unpack(char* buf,int len)
{
 int ip_len;
 struct ip* myip;
 struct icmp* myicmp;
 myip=(struct ip*)buf;
 ip_len=myip->ip_hl<<2; //IP首部长度*4
 myicmp=(struct icmp*)(buf+ip_len); //获得ICMP报文
 len-=ip_len;
 if(len<8)  //ICMP固定长度为8，小于即错误
 {
  printf("icmp packet is small than 8\n");
  return -1;
 }   
// printf("i am coming\n");
 if(myicmp->icmp_type==ICMP_ECHOREPLY && myicmp->icmp_id==pid) //判断是不是想要的包
  {
//  printf("i am coming\n");          
   gettimeofday(&recv_time,NULL);
   struct timeval* sendTime=(struct timeval*)myicmp->icmp_data;
   time_sub(&recv_time,sendTime); //计算时间差放到recv_time里面
   double rtt=recv_time.tv_sec*1000+recv_time.tv_usec/1000; //以ms为单位
   //打印相关信息
   printf("%d byte from %s:icmp_seq=%u ttl=%d rtt=%fms\n",len,inet_ntoa(from.sin_addr),myicmp->icmp_seq,myip->ip_ttl,rtt);
  }
 else
    //不是想要的包
   return -1;        
}


//时间差计算
void time_sub(struct timeval* end,struct timeval* begin)
{
 end->tv_sec-=begin->tv_sec;
 end->tv_usec-=begin->tv_usec;
}


//主函数
int main(int argc,char* argv[])
{
 if(argc<2)
  {
   printf("usage:%s hostname/ip\n",argv[0]);
   exit(-1);
  }      
 if((sockfd=socket(AF_INET,SOCK_RAW,IPPROTO_ICMP))<0) //创建原始套接字
 {
  perror("socket");
  exit(-1);
 } 
 memset(&from,0,sizeof(from));

 if((from.sin_addr.s_addr=inet_addr(argv[1]))==INADDR_NONE)//如果传入的不是ip
 {
  struct hostent* hptr=NULL;
  //转换失败
  if((hptr=gethostbyname(argv[1]))==NULL)
  {
   perror("gethostbyname");
   exit(-1);
  } 
  memcpy(&from.sin_addr,hptr->h_addr,sizeof(hptr->h_addr));
 }
 else 
 {
  from.sin_addr.s_addr=inet_addr(argv[1]);
 }
 from.sin_family=AF_INET;
 pid=getpid();
 printf("ping %s,%d data in icmp packets\n",argv[1],datalen);
 pthread_t id1,id2;
 
 //创建发送函数线程
 if(pthread_create(&id1,NULL,send_data,NULL)<0)
 {
      perror("pthread_create");
      return -2;
 }
 
  //创建接收函数线程
 if(pthread_create(&id2,NULL,recv_data,NULL)<0)
 {
      perror("pthread_create");
      return -3;
 }
 
 //回收线程，防止线程泄露
 pthread_join(id1,NULL);
 pthread_join(id2,NULL);
 close(sockfd); 
 printf("ping %s is complete,%d was sended and %d was received\n",argv[1],begin,end);
 return 0; 
}   

