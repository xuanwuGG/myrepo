create trigger ������ϸ������ on ������ϸ
instead of INSERT 
AS
BEGIN
	declare @d  varchar(50);
	declare @c varchar(50);
	declare @s int;
	select @d=����ID from  inserted;
	select @c=��ƷID from inserted;
	select @s=���� from inserted;
	insert into ������ϸ(����ID,��ƷID,����) values (@d,@c,@s);
	END
	go

create trigger ���������� on ����
instead of INSERT
AS
BEGIN
	declare @a1 int; select @a1=����ID from inserted;
	declare @a2 datetime;
	select @a2=�������� from inserted;
	declare @a3 datetime;
	select @a3=�������� from inserted;
	declare @a4 datetime;
	select @a4=�������� from inserted;
	declare @a5 datetime;
	select @a5=����ȷ������ from inserted;
	declare @a6 varchar(50);
	select @a6=�������� from inserted;
	declare @a7 varchar(50);
	select @a7=������ַ from inserted;
	declare @a8 varchar(50); 
	select @a8=�������� from inserted;
	declare @a9 varchar(50); 
	select @a9=�������� from inserted;
	declare @a10 varchar(50);
	select @a10=������������ from inserted;
	declare @a11 varchar(50);
	select @a11=�������� from inserted;
	declare @s int;
	insert into ����(����ID,��������,��������,��������,����ȷ������,��������,������ַ,��������,��������,������������,��������,�ܽ��) values (@a1,@a2,@a3,@a4,@a5,@a6,@a7,@a8,@a9,@a10,@a11,0);
/*	select * from (������ϸ left join ���� on ����.����ID=������ϸ.����ID)inner join ��Ʒ on ��Ʒ.��ƷID=������ϸ.��ƷID where ����.����ID=@a1;*/
	select @s=sum(����*����) from (������ϸ left join ���� on ����.����ID=������ϸ.����ID)inner join ��Ʒ on ��Ʒ.��ƷID=������ϸ.��ƷID where ����.����ID=@a1;
	update ���� set �ܽ��=@s where ����ID=@a1;
	print'�ܽ��Ϊ' print @s
	END
	go

create trigger �ͻ������� on �ͻ�
instead of insert
as
begin
	declare @a1 varchar(50);
	select @a1=�ͻ�ID from inserted;
	declare @a2 varchar(50); 
	select @a2=��˾���� from inserted;
	declare @a3 varchar(50);
	select @a3=��ϵ������ from inserted;
	declare @a4 varchar(50);
	select @a4=��ϵ��ְ�� from inserted;
	declare @a5 varchar(50);
	select @a5=��ַ from inserted;
	declare @a6 varchar(50);
	select @a6=���� from inserted;
	declare @a7 varchar(50);
	select @a7=���� from inserted;
	declare @a8 varchar(50); 
	select @a8=�������� from inserted;
	declare @a9 varchar(50);
	select @a9=���� from inserted;
	declare @a10 varchar(50);
	select @a10=�绰 from inserted;
	declare @a11 varchar(50); 
	select @a11=���� from inserted;
	declare @a12 varchar(50); 
	select @a12=���� from inserted;
	insert into �ͻ�(�ͻ�ID,��˾����,��ϵ������,��ϵ��ְ��,��ַ,����,����,��������,����,�绰,����,����) values (@a1,@a2,@a3,@a4,@a5,@a6,@a7,@a8,@a9,@a10,@a11,@a12);
	end
	go


create trigger ��津����
on ��Ʒ
instead of update
as
declare @a1 int
declare @a4 int
declare @b1 int
declare @a2 nvarchar(50)
select @a1=����� from inserted
select @a4=����� from deleted
select @b1=��ֹ from inserted
select @a2=��Ʒ���� from inserted
declare @a3 int
select @a3=��ֹ from ��Ʒ where ��Ʒ����=@a2
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
update ��Ʒ set ��ֹ=@b1 where ��Ʒ.��Ʒ����=@a2
print '��ֹ���óɹ�'
end
else
begin
if @a1<@a3
begin
 print '���������'
 end
else
begin
update ��Ʒ set �����=@a1 where ��Ʒ.��Ʒ����=@a2
print '�ɹ�����'
end
end
go
