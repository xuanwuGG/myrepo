create trigger 订单明细触发器 on 订单明细
instead of INSERT 
AS
BEGIN
	declare @d  varchar(50);
	declare @c varchar(50);
	declare @s int;
	select @d=订单ID from  inserted;
	select @c=产品ID from inserted;
	select @s=数量 from inserted;
	insert into 订单明细(订单ID,产品ID,数量) values (@d,@c,@s);
	END
	go

create trigger 订单触发器 on 订单
instead of INSERT
AS
BEGIN
	declare @a1 int; select @a1=订单ID from inserted;
	declare @a2 datetime;
	select @a2=订购日期 from inserted;
	declare @a3 datetime;
	select @a3=发货日期 from inserted;
	declare @a4 datetime;
	select @a4=到货日期 from inserted;
	declare @a5 datetime;
	select @a5=货款确认日期 from inserted;
	declare @a6 varchar(50);
	select @a6=货主名称 from inserted;
	declare @a7 varchar(50);
	select @a7=货主地址 from inserted;
	declare @a8 varchar(50); 
	select @a8=货主城市 from inserted;
	declare @a9 varchar(50); 
	select @a9=货主地区 from inserted;
	declare @a10 varchar(50);
	select @a10=货主邮政编码 from inserted;
	declare @a11 varchar(50);
	select @a11=货主国家 from inserted;
	declare @s int;
	insert into 订单(订单ID,订购日期,发货日期,到货日期,货款确认日期,货主名称,货主地址,货主城市,货主地区,货主邮政编码,货主国家,总金额) values (@a1,@a2,@a3,@a4,@a5,@a6,@a7,@a8,@a9,@a10,@a11,0);
/*	select * from (订单明细 left join 订单 on 订单.订单ID=订单明细.订单ID)inner join 产品 on 产品.产品ID=订单明细.产品ID where 订单.订单ID=@a1;*/
	select @s=sum(数量*单价) from (订单明细 left join 订单 on 订单.订单ID=订单明细.订单ID)inner join 产品 on 产品.产品ID=订单明细.产品ID where 订单.订单ID=@a1;
	update 订单 set 总金额=@s where 订单ID=@a1;
	print'总金额为' print @s
	END
	go

create trigger 客户触发器 on 客户
instead of insert
as
begin
	declare @a1 varchar(50);
	select @a1=客户ID from inserted;
	declare @a2 varchar(50); 
	select @a2=公司名称 from inserted;
	declare @a3 varchar(50);
	select @a3=联系人姓名 from inserted;
	declare @a4 varchar(50);
	select @a4=联系人职务 from inserted;
	declare @a5 varchar(50);
	select @a5=地址 from inserted;
	declare @a6 varchar(50);
	select @a6=城市 from inserted;
	declare @a7 varchar(50);
	select @a7=地区 from inserted;
	declare @a8 varchar(50); 
	select @a8=邮政编码 from inserted;
	declare @a9 varchar(50);
	select @a9=国家 from inserted;
	declare @a10 varchar(50);
	select @a10=电话 from inserted;
	declare @a11 varchar(50); 
	select @a11=传真 from inserted;
	declare @a12 varchar(50); 
	select @a12=密码 from inserted;
	insert into 客户(客户ID,公司名称,联系人姓名,联系人职务,地址,城市,地区,邮政编码,国家,电话,传真,密码) values (@a1,@a2,@a3,@a4,@a5,@a6,@a7,@a8,@a9,@a10,@a11,@a12);
	end
	go


create trigger 库存触发器
on 产品
instead of update
as
declare @a1 int
declare @a4 int
declare @b1 int
declare @a2 nvarchar(50)
select @a1=库存量 from inserted
select @a4=库存量 from deleted
select @b1=中止 from inserted
select @a2=产品名称 from inserted
declare @a3 int
select @a3=中止 from 产品 where 产品名称=@a2
print @a1
print @a2
print @a3
print @b1
print @a4
declare @n int
select @n=@a1-@a4
print @n
if @n=0
begin
update 产品 set 中止=@b1 where 产品.产品名称=@a2
print '中止设置成功'
end
else
begin
if @a1<@a3
begin
 print '库存量不足'
 end
else
begin
update 产品 set 库存量=@a1 where 产品.产品名称=@a2
print '成功购买'
end
end
go
