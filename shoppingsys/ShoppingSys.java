package config;

import java.awt.Font;
import java.awt.Image;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterAbortException;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.sql.*;
import java.util.Calendar;
import java.util.Vector;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ShoppingSys extends JFrame
{
	//static
	static int i=0;
	static int t=0;
	
	//main
	public static void main(String[] args) {
		new ShoppingSys();
   }
	//construct
	public ShoppingSys() 
	{
	    super("JieMian");
	    
		//cardlayout
	    CardLayout card=new CardLayout();
	    JPanel jp0=new JPanel();
	    
	  	//jp0 definition
  	    jp0.setLayout(card);
  	    jp0.setBounds(0,0,800,600);
	    
		//JFRAME
	    this.setResizable(false);
        this.setBounds(0, 0, 800, 600);
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);//关闭窗口
	    this.setLayout(null);
	    this.setLocationRelativeTo(null);
	    
	    //Font
	    Font font=new Font("黑体", Font.PLAIN, 32);
	    Font jFont=new Font("华文行楷",Font.BOLD,20);
	  	Font f1Font=new Font("楷体", Font.ITALIC, 18);
	    
	  	//shopping array
		String []s1= {"订单ID","顾客姓名","需求产品","需求数量"};
		String []s2= {"订单ID","委托公司","运送产品","运送地区"};
	  	String[] l1= {"产品名称","产品单价","购买数量"};
		String [][]shopStrings=new String[20][];
		String [][]shopStringsss=new String[30][];
		for(int j=0;j<30;j++)
		{
			shopStringsss[j]=new String[4];
		}
		for(int j=0;j<20;j++)
		{
			shopStrings[j]=new String[3];
		}
		
		//time
		Calendar calendar = Calendar.getInstance(); 
        Date date = new Date();
        java.util.Date date0 = new Date();
        java.sql.Date time = new java.sql.Date(date.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 ");
	    
		//instance cre(psd)
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet resultSet=null;
		{
				String sql = "ALTER table 客户 add 密码 varchar(50);";
				try {
		        	conn=Conn.getConnection();
		        	ps=conn.prepareStatement(sql);
			        ps.executeUpdate();			       
			       }catch(Exception e3){
		        	System.out.println("密码列已存在");
		        }finally {
		        	Conn.close(conn, ps, resultSet);
		        }    
        }
		
		//jpd definition
		DefaultTableModel tableModels2=new DefaultTableModel(shopStrings,s2) {
			public boolean isCellEditable(int row,int column)
			{
				return false;
			}
		};
		JTable tables2 =new JTable(tableModels2);
		JScrollPane scro3=new JScrollPane(tables2);
		JPanel jpd=new JPanel();
		JLabel biaoyuJLabel=new JLabel("XXxx"+",使命必达!");
		JButton finishButton=new JButton("已完成订单"+"(0)");
		JButton doingButton=new JButton("待完成订单"+"(0)");
		JLabel de_statusJLabel=new JLabel("状态:未签收");
		JLabel qianshouren=new JLabel("签收人:"+"XXX");
		JLabel qianshoudidian=new JLabel("签收地点:"+"XXXX");
		JLabel qinashou=new JLabel("确认签收:");
		JCheckBox querneBox=new JCheckBox("确认");
		
		//jpd declaration
		querneBox.setBounds(400,360,400,40);
		querneBox.setVisible(false);
		qinashou.setBounds(200,360,360,40);
		querneBox.setFont(new Font("宋体", Font.PLAIN, 32));
		qinashou.setFont(new Font("宋体", Font.PLAIN, 32));
		qinashou.setVisible(false);
		qianshoudidian.setBounds(480,300,130,40);
		qianshoudidian.setVisible(false);qianshoudidian.setFont(f1Font);
		qianshouren.setBounds(280,300,130,40);
		qianshouren.setVisible(false);qianshouren.setFont(f1Font);
		de_statusJLabel.setBounds(60,300,130,40);
		de_statusJLabel.setVisible(false);de_statusJLabel.setFont(f1Font);
		scro3.setBounds(60,30,660,260);
		scro3.setVisible(false);
		tables2.setRowHeight(40);
		jpd.setLayout(null);
		jpd.setBounds(0,0,800,600);
		biaoyuJLabel.setFont(new Font("华文行楷",Font.BOLD,58));
		finishButton.setFont(f1Font);
		doingButton.setFont(f1Font);
		biaoyuJLabel.setBounds(200,90,450,60);
		finishButton.setBounds(110,460,240,60);
		doingButton.setBounds(420,460,240,60);
		jpd.add(de_statusJLabel);jpd.add(qianshouren);jpd.add(qianshoudidian);jpd.add(qinashou);jpd.add(querneBox);
		jpd.add(biaoyuJLabel);jpd.add(finishButton);jpd.add(doingButton);jpd.add(scro3);jpd.add(querneBox);
		
		//jpd listener
		querneBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(querneBox.isSelected())
				{
					dialog tipDialog=new dialog("请确认是本人签收!", 's');
					tipDialog.jb1Button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							tipDialog.dispose();
						}
					});
					tipDialog.setVisible(true);
				}
			}
		});
		finishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog order=new JDialog(new JFrame(),"完成订单",true);
				order.setLayout(null);
				order.setSize(700,400);
				order.setLocationRelativeTo(null);
				DefaultTableModel tableModels2=new DefaultTableModel(shopStrings,s1) {
					public boolean isCellEditable(int row,int column)
					{
						return false;
					}
				};
				JTable tables2 =new JTable(tableModels2);
				tables2.setRowHeight(18);
				JScrollPane scros2=new JScrollPane(tables2);
				scros2.setBounds(18,5,650,300);
				JButton confirm1Button=new JButton("确认");
				confirm1Button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						order.dispose();
					}
				});
				confirm1Button.setFont(jFont);
				confirm1Button.setBounds(300,310,80,40);
				order.add(scros2);order.add(confirm1Button);
				
				order.setVisible(true);
			}
		});
		doingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				biaoyuJLabel.setVisible(false);
				qinashou.setVisible(true);
				qianshoudidian.setVisible(true);
				qianshouren.setVisible(true);
				de_statusJLabel.setVisible(true);
				querneBox.setVisible(true);
				scro3.setVisible(true);
			}
		});
		
		
		//jps definition
		JPanel jps=new JPanel();
		JLabel dian_infoJLabel=new JLabel("我的店铺信息");
		JLabel conameJLabel=new JLabel("店铺名:");
		JLabel coaddressJLabel=new JLabel("店铺地址:");
		JLabel copostJLabel=new JLabel("店铺邮编:");
		JLabel cophoneJLabel=new JLabel("店铺电话:");
		JLabel yourinfo=new JLabel("我的信息");
		JLabel yourname=new JLabel("姓名:");
		JLabel yourdegree=new JLabel("职位:");

		DefaultTableModel tableModels1=new DefaultTableModel(shopStrings,s1) {
			public boolean isCellEditable(int row,int column)
			{
				return false;
			}
		};
		JTable tables1 =new JTable(tableModels1);
		tables1.setRowHeight(26);
		JScrollPane scros1=new JScrollPane(tables1);
		JLabel statusJLabel=new JLabel("当前状态:请选中订单后执行操作");
		JLabel zhuantai=new JLabel("订单状态:暂无");
		JLabel buydate=new JLabel("购买时间:暂无");
		JLabel senddate=new JLabel("发货时间:暂无");
		JButton senButton=new JButton("点击去发货");
		JLabel proidJLabel=new JLabel("产品ID");
		JLabel pronameJLabel=new JLabel("产品名称");
		JLabel prostopJLabel=new JLabel("中止量");
		JButton setstopButton=new JButton("设置中止量");
		JTextField proidField=new JTextField("输入产品ID");
		JTextField pronameField=new JTextField("输入产品名称");
		JTextField prostopField=new JTextField("输入中止量");
		Choice stopChoice=new Choice();
		for(int i=0;i<10;i++)
		{
			stopChoice.addItem(""+i);
		}
		
		//jpf definition
		JPanel jpf=new JPanel();
		JLabel choiceJLabel=new JLabel("请选择身份");
		JLabel clientJLabel=new JLabel();
		JLabel supplyJLabel=new JLabel();
		JLabel deliverJLabel=new JLabel();
		


  	    //jp1 definition
   	   JPanel jp1=new JPanel();
   	   JLabel weLabel=new JLabel("欢迎");
   	   text accText=new text("用户名");
   	   text psdText=new text("密码");
   	   JTextField acc=new JTextField();
   	   JPasswordField psd=new JPasswordField();
   	   JButton login=new JButton("登录");
   	   JButton logup=new JButton("注册");
   	   
   	   //jp2 definition
  	   JPanel jp2=new JPanel();
  	   jp2.setLayout(null);
  	   JPanel jp21=new JPanel();
  	   jp21.setPreferredSize(new Dimension(720,1700));
  	   JScrollPane sc=new JScrollPane(jp21);
  	   text id=new text("您的客户ID(必填):");
  	   JTextField idJTextField=new JTextField("请输入五位ID英文码");
  	   text company=new text("您的公司名称:");
  	   JTextField companyJTextField=new JTextField("请以公司全称输入");
  	   text conName=new text("公司联系人姓名(必填):");
  	   JTextField conNameJTextField=new JTextField("请输入完整姓名");
  	   text conDegree=new text("该联系人职务:");
  	   text address=new text("公司地址:");
  	   JTextField addressJTextField=new JTextField("详细地址请具体到街道单元号");
  	   text PostNum=new text("邮政编码(必填):");
  	   JTextField posJTextField=new JTextField("请输入6位邮编");
  	   text country=new text("公司所在国家:");
  	   text phone=new text("公司联系电话:");
  	   JTextField phoneJTextField=new JTextField("建议添加区号");
  	   text fax=new text("公司传真:");
  	   JTextField faxField=new JTextField("非必填项");
  	   text in_psdText=new text("设置密码(必填):");
  	   JPasswordField passwordField=new JPasswordField();
  	   text confirm_psdText=new text("确认密码(必填):");
  	   JPasswordField coPasswordField=new JPasswordField();
  	   JButton in=new JButton("注册");
  	   JButton out=new JButton("取消");
  	   JButton out1=new JButton("确认");
  	   JComboBox pro=new JComboBox();
	   JComboBox city=new JComboBox();
	   JComboBox guojia=new JComboBox();
	   JPanel duoxuan=new JPanel();
	   JCheckBox xsdb=new JCheckBox("销售代表");
	   JCheckBox scjl=new JCheckBox("市场经理");
	   JCheckBox wz=new JCheckBox("物主");
	   JCheckBox jsjl=new JCheckBox("结算经理");
	   JCheckBox xsy=new JCheckBox("销售员");
	   text area=new text("地区:");
	   text degree=new text("");
	   text de=new text("");
	   
	   //jp3 definition
	    JPanel jp3=new JPanel();
	    JLabel name=new JLabel("开始选购心仪产品吧");
	    text search=new text("请在此处输入产品ID或名称");
	    JButton affirm=new JButton("确定");
	    
	    //jp4 definition
	  	JPanel jp4=new JPanel();
	    JPanel jp4a=new JPanel();
	  	JLabel label=new JLabel();
	    text productName=new text("商品名称");
		text productPrice=new text("价格");
		text productNum=new text("剩余量");
		Choice num=new Choice();
	  	ImageIcon icon=new ImageIcon("D:/c.png");
	   	JPanel jp4b=new JPanel();
	    JButton put=new JButton("加入购物车");
	    JButton buy=new JButton("立即采购");
	    JButton look=new JButton("查看我的购物车");
	    JButton retu1=new JButton("返回");
	    text shuliang=new text("数量");
	    JPanel text=new JPanel();
	    JTextArea info=new JTextArea("");
	    info.setBounds(400,90,240,110);
	    info.setFont(new Font("华文行楷",Font.BOLD,16));	   
	    info.setOpaque(false);
	    
	    //jp5 definition
	  	   JPanel jp5=new JPanel();
	  	   JButton shanchu=new JButton("删除");
	  	   jp5.setBounds(0,0,800,600);
	  	   jp5.setLayout(null);
		   DefaultTableModel tableModel=new DefaultTableModel(shopStrings,l1) {
			   public boolean isCellEditable(int row,int column)
			   {
				   return false;
			   }
		   };
		   JTable table =new JTable(tableModel);
		   table.setRowHeight(19);
	  	   JScrollPane scro=new JScrollPane(table);
	  	   JTextField title=new JTextField("我的购物车");
	  	   JTextField sum=new JTextField("总计:");
	  	   JButton jiesuan=new JButton("立即结算");
	  	   JButton retu2=new JButton("返回");
	  	   text priceJTextField=new text("0元");
	  	   
	    //Menu definition
	    JMenuBar bar=new JMenuBar();
		JMenu my=new JMenu("我的");
		JMenuItem CheckOrder=new JMenuItem("历史订单");
		JMenuItem SelfInfo=new JMenuItem("个人信息");
		JMenuItem SignOut=new JMenuItem("登出");
		
		//jpf declaration
		jpf.setLayout(null);
		jpf.setBounds(0,0,800,600);
		choiceJLabel.setFont(new Font("楷体", Font.BOLD, 50));
		choiceJLabel.setBounds(270,80,540,100);
		clientJLabel.setBounds(50,250,200,200);
		clientJLabel.setBackground(Color.black);
		clientJLabel.setOpaque(true);
		supplyJLabel.setBounds(300,250,200,200);
		supplyJLabel.setBackground(Color.black);
		supplyJLabel.setOpaque(true);
		deliverJLabel.setBounds(550,250,200,200);
		deliverJLabel.setBackground(Color.black);
		deliverJLabel.setOpaque(true);
		jpf.add(clientJLabel);jpf.add(supplyJLabel);jpf.add(deliverJLabel);jpf.add(choiceJLabel);
		
		
		//jps declaration
				tables1.setFont(new Font("华文楷体", Font.ITALIC, 10));
				jps.setBounds(0,0,800,600);
				jps.setLayout(null);
				dian_infoJLabel.setBounds(10,10,200,40);
				conameJLabel.setBounds(10,70,200,40);
				coaddressJLabel.setBounds(10,130,200,40);
				copostJLabel.setBounds(10,190,200,40);
				cophoneJLabel.setBounds(10,250,200,40);
				yourinfo.setBounds(10,290,200,40);
				yourname.setBounds(10,350,200,40);
				yourdegree.setBounds(10,410,200,40);
				scros1.setBounds(210,10,540,300);
				statusJLabel.setBounds(210,320,340,50);
				statusJLabel.setFont(jFont);
				zhuantai.setBounds(620,320,240,50);
				buydate.setBounds(210,380,340,50);
				senddate.setBounds(400,380,340,50);
				senButton.setBounds(600,380,140,40);
				setstopButton.setBounds(400,500,140,40);
				proidJLabel.setBounds(210,440,60,50);
				pronameJLabel.setBounds(360,440,80,50);
				prostopJLabel.setBounds(570,440,60,50);
				proidField.setBounds(280,445,80,40);
				pronameField.setBounds(440,445,135,40);
				stopChoice.setBounds(640,455,120,50);
				
				proidJLabel.setFont(f1Font);pronameJLabel.setFont(f1Font);prostopJLabel.setFont(f1Font); zhuantai.setFont(jFont);buydate.setFont(f1Font);senddate.setFont(f1Font);senButton.setFont(jFont);
				dian_infoJLabel.setFont(new Font("楷体", Font.PLAIN, 20));setstopButton.setFont(jFont);proidField.setFont(new Font("华文行楷", Font.ITALIC, 14));pronameField.setFont(new Font("华文行楷", Font.ITALIC, 14));
				yourinfo.setFont(new Font("楷体", Font.PLAIN, 20));
				jps.add(dian_infoJLabel);jps.add(conameJLabel);jps.add(coaddressJLabel);jps.add(copostJLabel);jps.add(cophoneJLabel);jps.add(senddate);
				jps.add(yourinfo);jps.add(yourname);jps.add(yourdegree);jps.add(scros1);jps.add(statusJLabel);jps.add(zhuantai);jps.add(buydate);
		  	    jps.add(senButton);jps.add(proidJLabel);jps.add(pronameJLabel);jps.add(prostopJLabel);jps.add(setstopButton);
				jps.add(stopChoice);jps.add(proidField);jps.add(pronameField);
				
	    
	    //jp1 declaration
	  	jp1.setBounds(0,0,800,600);
	    jp1.setLayout(null);
  	    psd.setEchoChar('*');
	    weLabel.setBounds(290,110,160,160);
	    weLabel.setFont(new Font("黑体", Font.ITALIC, 64));
  	    accText.setBounds(460,140,160,40);
	    acc.setFont(f1Font);
	    psdText.setBounds(460,250,160,40);
	    acc.setBounds(460,190,240,40);
  	    psd.setBounds(460,300,240,40);
	    login.setFont(jFont);
	    logup.setFont(jFont);
	    login.setBounds(460,350,100,40);
	    logup.setBounds(580,350,100,40);
	    jp1.add(weLabel);jp1.add(accText);jp1.add(psdText);jp1.add(acc);jp1.add(psd);jp1.add(login);jp1.add(logup);

	    //jp2 declaration
	    xsdb.setFont(f1Font);scjl.setFont(f1Font);wz.setFont(f1Font);jsjl.setFont(f1Font);xsy.setFont(f1Font);
	    duoxuan.setBounds(200,540,500,40);
	    duoxuan.setLayout(new FlowLayout());duoxuan.add(xsdb);duoxuan.add(scjl);duoxuan.add(wz);duoxuan.add(jsjl);duoxuan.add(xsy);
	    jp2.setBounds(0,0,800,600);
	    sc.setBounds(22,5,743,500);
	    sc.getVerticalScrollBar().setUnitIncrement(40);
	    jp21.setLayout(null);
	    in.setFont(jFont);
	    out.setFont(jFont);
	    in.setBounds(300,510,80,40);
	    out.setBounds(420,510,80,40);
	    out1.setBounds(360,505,80,40);
	    out1.setVisible(false);out1.setFont(jFont);
	    guojia.addItem(new String("中国"));
	    guojia.addItem(new String("美国"));
	    pro.addItem("河北省");pro.addItem("山西省");pro.addItem("内蒙古自治区");pro.addItem("黑龙江省");pro.addItem("山东省");pro.addItem("江西省");pro.addItem("福建省");pro.addItem("安徽省");pro.addItem("浙江省");pro.addItem("江苏省");pro.addItem("辽宁省");pro.addItem("吉林省");pro.addItem("新疆维吾尔自治区");pro.addItem("宁夏回族自治区");pro.addItem("青海省");pro.addItem("甘肃省");pro.addItem("陕西省");pro.addItem("西藏自治区");pro.addItem("云南省");pro.addItem("贵州省");pro.addItem("四川省");pro.addItem("海南省");pro.addItem("广西壮族自治区");pro.addItem("广东省");pro.addItem("湖南省");pro.addItem("湖北省");pro.addItem("河南省");
	    pro.addItem("北京");pro.addItem("上海");pro.addItem("天津");pro.addItem("重庆");pro.addItem("香港特别行政区");pro.addItem("澳门特别行政区");
	    pro.setBounds(40,685,140,40);
	    degree.setBounds(240,470,240,40);
	    de.setBounds(220,620,400,40);de.setVisible(false);de.setEditable(false);//de.setFont()
	    degree.setVisible(false);degree.setEditable(false);
	  	   
	  	   
	    id.setBounds(35,20,240,40);
	    id.setBorder(null);
	    company.setBounds(35,170,240,40);
	    company.setBorder(null);
	    conName.setBounds(35,320,240,40);
	    conName.setBorder(null);
	    conDegree.setBounds(35,470,240,40);
	    conDegree.setBorder(null);
	    address.setBounds(35,620,240,40);
	    address.setBorder(null);
	    country.setBounds(35,770,240,40);
	    country.setBorder(null);
	    phone.setBounds(35,920,240,40);
	    phone.setBorder(null);
	    fax.setBounds(35,1070,240,40);
	    fax.setBorder(null);
	    PostNum.setBounds(35,1220,240,40);
	    PostNum.setBorder(null);
	    in_psdText.setBorder(null);
	    in_psdText.setBounds(35,1370,240,40);
	    confirm_psdText.setBounds(35,1520,240,40);
	    confirm_psdText.setBorder(null);
	    idJTextField.setBounds(220,85,340,40);
	    idJTextField.setBorder(null);
	    idJTextField.setFont(f1Font);
	    companyJTextField.setBounds(220,235,340,40);
	    companyJTextField.setBorder(null);
	    companyJTextField.setFont(f1Font);
	    conNameJTextField.setBounds(220,385,340,40);
	    conNameJTextField.setBorder(null);
	    conNameJTextField.setFont(f1Font);
	    addressJTextField.setBounds(450,685,260,40);
	    addressJTextField.setFont(f1Font);
	    addressJTextField.setBorder(null);
	    phoneJTextField.setBounds(220,995,340,40);
	    phoneJTextField.setFont(f1Font);
	    phoneJTextField.setBorder(null);
	    faxField.setBounds(220,1145,340,40);
	    faxField.setFont(f1Font);
	    faxField.setBorder(null);
	    posJTextField.setBounds(220,1285,340,40);
	    posJTextField.setBorder(null);
	    posJTextField.setFont(f1Font);
	    passwordField.setBounds(220,1435,340,40);
	    passwordField.setBorder(null);
	    coPasswordField.setBounds(220,1585,340,40);
	    coPasswordField.setBorder(null);
	    city.setBounds(190,685,140,40);
	    area.setBounds(340,685,100,40);
	    area.setHorizontalAlignment(JTextField.LEFT);
	    area.setBackground(Color.white);
	    area.setOpaque(true);
	    guojia.setBounds(220,830,240,40);
	    pro.setFont(f1Font);city.setFont(f1Font);guojia.setFont(f1Font);
	  	   
	    jp21.add(PostNum); jp21.add(id);jp21.add(company);jp21.add(conName);jp21.add(conDegree);jp21.add(address);jp21.add(country);jp21.add(phone);jp21.add(fax);jp21.add(confirm_psdText);jp21.add(in_psdText);
	    jp21.add(passwordField);jp21.add(coPasswordField); jp21.add(posJTextField); jp21.add(faxField); jp21.add(phoneJTextField); jp21.add(idJTextField);jp21.add(companyJTextField);jp21.add(conNameJTextField);jp21.add(addressJTextField);
	    jp21.add(pro);jp21.add(city);jp21.add(guojia);jp21.add(area);jp21.add(duoxuan);jp21.add(degree);jp21.add(de);
	    jp2.add(in);jp2.add(out);jp2.add(out1);
	    jp2.add(sc);
	    
	    //jp3 declaration
  	    jp3.setBounds(0,0,800,600);
  	    jp3.setLayout(null);
  	    name.setFont(font);
  	    name.setBounds(240,50,300,50);
  	    search.setHorizontalAlignment(JTextField.LEFT);
  	    search.setEditable(true);
  	    search.setOpaque(true);
  	    search.setBounds(200,150,300,40);
  	    search.setFont(f1Font);
  	    affirm.setFont(jFont);
  	    affirm.setBounds(550,150,80,40);  	   
  	    jp3.add(name);
  	    jp3.add(search);
  	    jp3.add(affirm);
	    
	    //jp4 declaration
   	    jp4.setBounds(0,0,800,600);
   	    jp4.setLayout(null);
   	    jp4a.setLayout(null);
   	    jp4a.setBounds(0,0,800,350);
   	    label.setBounds(40, 30, 320, 280);
   	    icon.setImage(icon.getImage().getScaledInstance(320, 280,Image.SCALE_DEFAULT));
   	    label.setIcon(icon);
   	    jp4a.add(label);
 	    productName.setBounds(400, 30, 345, 90);
 	    productNum.setBounds(400, 125, 345, 90);
 	    productPrice.setBounds(400,220 , 345, 90);
 	    jp4a.add(productName);jp4a.add(productPrice);jp4a.add(productNum);
 	    jp4.add(jp4a);
 	   jp4b.setLayout(null);
   	   jp4b.setBounds(0,350,800,250);
   	   put.setFont(jFont);
   	   buy.setFont(jFont);
   	   put.setEnabled(false);
   	   buy.setEnabled(false);
   	   shuliang.setBounds(400,0,80,30);
   	   shuliang.setBorder(null);
   	   retu1.setBounds(660,110,80,60);
   	   retu1.setFont(jFont);
   	   num.setBounds(400,30,80,0);
   	   look.setBounds(550,0,190,60);
   	   look.setFont(jFont);
   	   put.setBounds(40,0,320,75);
   	   buy.setBounds(40,95,320,75);
   	   for(int i=0;i<150;i++)
   	   {
   		   num.add(""+i);
   	   }
	   text.setBounds(355,120,300,205);
   	   jp4b.add(shuliang);jp4b.add(num);jp4b.add(buy);jp4b.add(put);jp4b.add(info);jp4b.add(look);jp4b.add(retu1);jp4.add(jp4b);
	    
	    //jp5 declaration
   	   table.setRowHeight(19);
	   shanchu.setBounds(500,490,100,50);
	   shanchu.setEnabled(false);
	   shanchu.setFont(jFont);
  	   scro.setBounds(40,60,720,400);
  	   title.setBounds(310,10,170,50);
  	   title.setEditable(false);
  	   title.setFont(font);
  	   sum.setBounds(25,490,100,50);
  	   sum.setEditable(false);
  	   sum.setFont(new Font("华文行楷", Font.BOLD, 40));
  	   sum.setBorder(null);
  	   jiesuan.setBounds(300,490,150,50);
  	   jiesuan.setFont(jFont);
  	   retu2.setBounds(660,480,90,60);
  	   retu2.setFont(jFont);
  	   table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  	   priceJTextField.setBounds(130,490,150,50);
  	   priceJTextField.setBorder(null);
  	   priceJTextField.setFont(new Font(("黑体"),Font.ITALIC,40));
  	   jp5.add(retu2);jp5.add(title);jp5.add(sum);jp5.add(jiesuan);jp5.add(scro);jp5.add(shanchu);jp5.add(priceJTextField);
  	   
  	   //menu declaration
  	   my.setFont(f1Font);
  	   SelfInfo.setFont(f1Font);
  	   CheckOrder.setFont(f1Font);
  	   SignOut.setFont(f1Font);
  	   my.setVisible(false);
  	   my.add(SelfInfo);my.add(CheckOrder);my.add(SignOut); bar.add(my);this.setJMenuBar(bar);
  	   
  	   //jpf listener
  	   deliverJLabel.addMouseListener(new MouseListener() {
		public void mouseReleased(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {
			card.show(jp0, "7");
		}
	});
  	 supplyJLabel.addMouseListener(new MouseListener() {
  		public void mouseReleased(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {
			String[] da=new String[100];
			JPanel jps=new JPanel();
 			JLabel dian_infoJLabel=new JLabel("我的店铺信息");
 			JLabel conameJLabel=new JLabel("店铺名:");
 			JLabel coaddressJLabel=new JLabel("店铺地址:");
 			JLabel copostJLabel=new JLabel("店铺邮编:");
 			JLabel cophoneJLabel=new JLabel("店铺电话:");
 			JLabel yourinfo=new JLabel("您的信息");
 			JLabel yourname=new JLabel("姓名:");
 			JLabel yourdegree=new JLabel("职位:");
			Connection conn=null;
 			PreparedStatement ps=null;
 			ResultSet resultSet=null;
 			String sql="SELECT * FROM 供应商 where 供应商ID=1";
 			try
 			{
 				conn=Conn.getConnection();
 				ps=conn.prepareStatement(sql);
 				resultSet=ps.executeQuery();
 				if(resultSet.next())
 				{	
 					conameJLabel.setText("店铺名:"+resultSet.getString("公司名称"));
	 		        coaddressJLabel.setText("店铺地址:"+resultSet.getString("地址"));
	 		        copostJLabel.setText("店铺邮编:"+resultSet.getString("邮政编码"));
	 	            cophoneJLabel.setText("店铺电话:"+resultSet.getString("电话"));
	 	            yourname.setText("姓名:"+resultSet.getString("联系人姓名"));
	 	            yourdegree.setText("职位:"+resultSet.getString("联系人职务"));
 				}
				Connection conn1=null;
	 			PreparedStatement ps1=null;
	 			ResultSet resultSet1=null;
	 			String sql1="select * from 订单,订单明细,产品 where 发货日期 is null and 订单明细.订单ID=订单.订单ID and 订单明细.产品ID=产品.产品ID";
	 			try
	 			{
	 				int k=0;
	 				conn1=Conn.getConnection();
	 				ps1=conn1.prepareStatement(sql1);
	 				resultSet1=ps1.executeQuery();
	 				while(resultSet1.next())
	 				{	
	 					shopStringsss[k][0]=resultSet1.getString("订单ID");
	 					shopStringsss[k][1]=resultSet1.getString("货主名称");
	 					shopStringsss[k][2]=resultSet1.getString("产品名称");
	 					shopStringsss[k][3]=resultSet1.getString("数量");
	 					SimpleDateFormat sd = new SimpleDateFormat( "yyyy-MM-dd");
	 					Date dada=new Date();
	 					dada=resultSet1.getDate("订购日期");
	 					System.out.println(sd.format(date));
	 					da[k]=sd.format(date);
	 					k++;
	 				}
	 			}catch(Exception e7){
	 				e7.printStackTrace();
	 			}
 			}catch(Exception e6){
 				e6.printStackTrace();
 			}
 			

 			String []s1= {"订单ID","顾客姓名","需求产品","需求数量"};
 			DefaultTableModel tableModels1=new DefaultTableModel(shopStringsss,s1) {
 				public boolean isCellEditable(int row,int column)
 				{
 					return false;
 				}
 			};
 			JTable tables1 =new JTable(tableModels1);
 			tables1.setRowHeight(26);
 			JScrollPane scros1=new JScrollPane(tables1);
 			JLabel statusJLabel=new JLabel("当前状态:请选中订单后执行操作");
 			JLabel zhuantai=new JLabel("订单状态:暂无");
 			JLabel buydate=new JLabel("购买时间:暂无");
 			JLabel senddate=new JLabel("发货时间:暂无");
 			JButton senButton=new JButton("点击去发货");
 			JLabel proidJLabel=new JLabel("产品ID");
 			JLabel pronameJLabel=new JLabel("产品名称");
 			JLabel prostopJLabel=new JLabel("中止量");
 			JButton setstopButton=new JButton("设置中止量");
 			JTextField proidField=new JTextField("输入产品ID");
 			JTextField pronameField=new JTextField("输入产品名称");
 			JTextField prostopField=new JTextField("输入中止量");
 			Choice stopChoice=new Choice();
 			for(int i=0;i<10;i++)
 			{
 				stopChoice.addItem(""+i);
 			}
 			dian_infoJLabel.setFont(new Font("楷体", Font.PLAIN, 20));
 			yourinfo.setFont(new Font("楷体", Font.PLAIN, 20));
 			jps.add(dian_infoJLabel);jps.add(conameJLabel);jps.add(coaddressJLabel);jps.add(copostJLabel);jps.add(cophoneJLabel);
 			jps.add(yourinfo);jps.add(yourname);jps.add(yourdegree);jps.add(scros1);
 			//jps declaration
 			tables1.setFont(new Font("华文楷体", Font.ITALIC, 10));
 			jps.setBounds(0,0,800,600);
 			jps.setLayout(null);
 			dian_infoJLabel.setBounds(10,10,200,40);
 			conameJLabel.setBounds(10,70,200,40);
 			coaddressJLabel.setBounds(10,130,200,40);
 			copostJLabel.setBounds(10,190,200,40);
 			cophoneJLabel.setBounds(10,250,200,40);
 			yourinfo.setBounds(10,290,200,40);
 			yourname.setBounds(10,350,200,40);
 			yourdegree.setBounds(10,410,200,40);
 			scros1.setBounds(210,10,540,300);
 			statusJLabel.setBounds(210,320,340,50);
 			statusJLabel.setFont(jFont);
 			zhuantai.setBounds(620,320,240,50);
 			buydate.setBounds(210,380,340,50);
 			senddate.setBounds(400,380,340,50);
 			senButton.setBounds(600,380,140,40);
 			setstopButton.setBounds(400,500,140,40);
 			proidJLabel.setBounds(210,440,60,50);
 			pronameJLabel.setBounds(360,440,80,50);
 			prostopJLabel.setBounds(570,440,60,50);
 			proidField.setBounds(280,445,80,40);
 			pronameField.setBounds(440,445,135,40);
 			stopChoice.setBounds(640,455,120,50);
 			
 			proidJLabel.setFont(f1Font);pronameJLabel.setFont(f1Font);prostopJLabel.setFont(f1Font); zhuantai.setFont(jFont);buydate.setFont(f1Font);senddate.setFont(f1Font);senButton.setFont(jFont);
 			dian_infoJLabel.setFont(new Font("楷体", Font.PLAIN, 20));setstopButton.setFont(jFont);proidField.setFont(new Font("华文行楷", Font.ITALIC, 14));pronameField.setFont(new Font("华文行楷", Font.ITALIC, 14));
 			yourinfo.setFont(new Font("楷体", Font.PLAIN, 20));
 			jps.add(dian_infoJLabel);jps.add(conameJLabel);jps.add(coaddressJLabel);jps.add(copostJLabel);jps.add(cophoneJLabel);jps.add(senddate);
 			jps.add(yourinfo);jps.add(yourname);jps.add(yourdegree);jps.add(scros1);jps.add(statusJLabel);jps.add(zhuantai);jps.add(buydate);
 	  	    jps.add(senButton);jps.add(proidJLabel);jps.add(pronameJLabel);jps.add(prostopJLabel);jps.add(setstopButton);
 			jps.add(stopChoice);jps.add(proidField);jps.add(pronameField);
 			
 			//jps lisenter
 			senButton.addActionListener(new ActionListener() {
 				public void actionPerformed(ActionEvent e) {
 					JDialog changeDialog =new JDialog(new JFrame(),"发货设置",true);
 					changeDialog.setSize(300,200);
 					changeDialog.setLocationRelativeTo(null);
 					changeDialog.setLayout(null);
 					changeDialog.setResizable(false);
 					JLabel mon=new JLabel("月");
 					JLabel da=new JLabel("日");
 					JLabel sJLabel=new JLabel("交由");
 					JLabel s1JLabel=new JLabel("发货");
 					JLabel callJLabel=new JLabel("1123456111");
 					Choice monthChoice=new Choice();
 					Choice dayChoice=new Choice();
 					JComboBox deChoice=new JComboBox();
 					monthChoice.addItem("01");
 					monthChoice.addItem("02");
 					monthChoice.addItem("03");
 					monthChoice.addItem("04");
 					monthChoice.addItem("05");
 					monthChoice.addItem("06");
 					monthChoice.addItem("07");
 					monthChoice.addItem("08");
 					monthChoice.addItem("09");
 					monthChoice.addItem("10");
 					monthChoice.addItem("11");
 					monthChoice.addItem("12");
 					dayChoice.addItem("01");dayChoice.addItem("02");dayChoice.addItem("03");dayChoice.addItem("04");dayChoice.addItem("05");dayChoice.addItem("06");
 					dayChoice.addItem("07");dayChoice.addItem("08");dayChoice.addItem("09");dayChoice.addItem("10");
 					for(int i=10;i<31;i++)
 					{
 						dayChoice.addItem(""+i);
 					}
 					Connection conn=null;
 					PreparedStatement ps=null;
 					ResultSet rs=null;
 					String sql="select * from 运货商";
 					try
 					{
 						conn=Conn.getConnection();
 						ps=conn.prepareStatement(sql);
 						rs=ps.executeQuery();
 						while(rs.next())
 						{
 							String a=rs.getString("公司名称");
 							deChoice.addItem(a);
 						}
 					}catch(Exception e1) {
 						e1.printStackTrace();
 					}
 					JButton confirmchangeButton=new JButton("确认发货");
 					confirmchangeButton.setBounds(85,110,120,30);
 					monthChoice.setBounds(10,30,60,40);dayChoice.setBounds(105,30,60,40);deChoice.setBounds(40,60,80,30);
 					mon.setBounds(85,20,20,40);da.setBounds(175,20,40,40);sJLabel.setBounds(10,50,30,40);s1JLabel.setBounds(240,50,30,40);
 					confirmchangeButton.setFont(jFont);callJLabel.setBounds(130,50,80,40);
 					changeDialog.add(confirmchangeButton);changeDialog.add(mon);changeDialog.add(da);changeDialog.add(monthChoice);
 					changeDialog.add(dayChoice);changeDialog.add(sJLabel);changeDialog.add(deChoice);changeDialog.add(s1JLabel);changeDialog.add(callJLabel);
 					confirmchangeButton.addActionListener(new ActionListener() {
 						public void actionPerformed(ActionEvent e) {
 							int p=0;
 		 					p=tables1.getSelectedRow();
 							String a=monthChoice.getSelectedItem();
 							String b=dayChoice.getSelectedItem();
 							String d="2023"+"-"+a+"-"+b+" 00:00:00";
 							SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
 							Connection co=null;
 							PreparedStatement ps=null;
 							String sql="update 订单 set 发货日期=? where 订单ID=?";
 							try
 							{
 								Date date = sdf.parse(d);
 								long lg = date.getTime();
 								System.out.println(date);
 								co=Conn.getConnection();
 								ps=co.prepareStatement(sql);
 								ps.setDate(1,new java.sql.Date(lg));
 								ps.setString(2,shopStringsss[p][0]);
 								ps.executeUpdate();
 							}catch(Exception e2) {
 								e2.printStackTrace();
 							}
 						}
 					});
 					changeDialog.setVisible(true);
 				}
 			});
 			setstopButton.addActionListener(new ActionListener() {
 				public void actionPerformed(ActionEvent e) {
					Connection co=null;
					PreparedStatement ps=null;
					ResultSet rs=null;
					String sql="update 产品 set 中止=? where 产品名称=?";
					try
					{
						co=Conn.getConnection();
						ps=co.prepareStatement(sql);
						int k;
						k=Integer.parseInt(stopChoice.getSelectedItem());
						ps.setInt(1,k);

						ps.setString(2,pronameField.getText());
						ps.executeUpdate();

					}catch(Exception e7) {
						e7.printStackTrace();
					}finally {
				        	Conn.close(co, ps, rs);
				        }
					
				}
 			});
 			pronameField.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {}
				public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {
					if(pronameField.getText().equals("输入产品名称"))
					{
						pronameField.setText("");
					}
				}
			});
 			pronameField.addActionListener(new ActionListener() {
 				public void actionPerformed(ActionEvent e) {
 					Connection co=null;
 					PreparedStatement ps=null;
 					String sql="select * from 产品 where 产品名称=?";
 					ResultSet rs=null;
 					try
 					{
 						co=Conn.getConnection();
 						ps=co.prepareStatement(sql);
 						ps.setString(1, pronameField.getText());
 						rs=ps.executeQuery();
 						if(rs.next())
 						{
 							proidField.setText(rs.getString("产品ID"));
 						}
 					}
 					catch(Exception e8) {
 						e8.printStackTrace();
 					}finally {
				        	Conn.close(co, ps, rs);
				        }
 				}
 			});
 			proidField.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {}
				public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {
					if(proidField.getText().equals("输入产品ID"))
					{
						proidField.setText("");
					}
				}
			});
 			proidField.addActionListener(new ActionListener() {
 				public void actionPerformed(ActionEvent e) {
 					Connection co=null;
 					PreparedStatement ps=null;
 					String sql="select * from 产品 where 产品ID=?";
 					ResultSet rs=null;
 					try
 					{
 						co=Conn.getConnection();
 						ps=co.prepareStatement(sql);
 						ps.setString(1, proidField.getText());
 						rs=ps.executeQuery();
 						if(rs.next())
 						{
 							pronameField.setText(rs.getString("产品名称"));
 						}
 					}
 					catch(Exception e8) {
 						e8.printStackTrace();
 					}finally {
				        	Conn.close(co, ps, rs);
				        }
 				}
 			});
 			tables1.addMouseListener(new MouseListener() {
 				public void mouseReleased(MouseEvent e) {
 				}
 				public void mousePressed(MouseEvent e) {
 				}
 				public void mouseExited(MouseEvent e) {
 				}
 				public void mouseEntered(MouseEvent e) {
 				}
 				public void mouseClicked(MouseEvent e) {
 					int p=0;
 					p=tables1.getSelectedRow();
 					buydate.setText("购买时间:"+da[p]);
 					senddate.setText("发货时间:"+"暂未发货");
 					statusJLabel.setText("当前状态:已选中一单");
 				}
 			});
 		   jp0.add(jps,"6");
			card.show(jp0, "6");
			if(true)
			{
				dialog tip=new dialog("您有新的订单", 's');
				tip.jb1Button.setBounds(120,100,160,50);
				tip.jb1Button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tip.dispose();
					}
				});
				tip.setVisible(true);
			}

		}
		
	});
		clientJLabel.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				card.show(jp0, "1");
			}
		});
  	   
		//jps lisenter
		senButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog changeDialog =new JDialog(new JFrame(),"发货设置",true);
				changeDialog.setSize(300,200);
				changeDialog.setLocationRelativeTo(null);
				changeDialog.setLayout(null);
				changeDialog.setResizable(false);
				JLabel mon=new JLabel("月");
				JLabel da=new JLabel("日");
				JLabel sJLabel=new JLabel("交由");
				JLabel s1JLabel=new JLabel("发货");
				JLabel callJLabel=new JLabel("1123456111");
				Choice monthChoice=new Choice();
				Choice dayChoice=new Choice();
				Choice deChoice=new Choice();
				for(int i=1;i<13;i++)
				{
					monthChoice.addItem(""+i);
				}
				for(int i=1;i<32;i++)
				{
					dayChoice.addItem(""+i);
				}
				JButton confirmchangeButton=new JButton("确认发货");
				confirmchangeButton.setBounds(85,110,120,30);
				monthChoice.setBounds(10,30,60,40);dayChoice.setBounds(105,30,60,40);deChoice.setBounds(40,60,80,40);
				mon.setBounds(85,20,20,40);da.setBounds(175,20,40,40);sJLabel.setBounds(10,50,30,40);s1JLabel.setBounds(240,50,30,40);
				confirmchangeButton.setFont(jFont);callJLabel.setBounds(130,50,80,40);
				changeDialog.add(confirmchangeButton);changeDialog.add(mon);changeDialog.add(da);changeDialog.add(monthChoice);
				changeDialog.add(dayChoice);changeDialog.add(sJLabel);changeDialog.add(deChoice);changeDialog.add(s1JLabel);changeDialog.add(callJLabel);
				confirmchangeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				changeDialog.setVisible(true);
			}
		});
		setstopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		pronameField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		proidField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		tables1.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
			}
		});
 	    
  	   
	    //jp1 listener
  	   psd.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String tmp=acc.getText();
 			Connection conn=null;
 			PreparedStatement ps=null;
 			ResultSet resultSet=null;
 			int p=1;
 			String sql = "SELECT * FROM 客户 where  客户ID=?;";
 		        try {
 		        	conn=Conn.getConnection();
 		        	ps=conn.prepareStatement(sql);
 			        ps.setString(1, tmp);
 			        resultSet=ps.executeQuery();
 			       if(resultSet.next())
 			       {
 						String tmp0=acc.getText();
 						String tmp1=new String(psd.getPassword());
 						Connection conn1=null;
 						PreparedStatement ps1=null;
 						ResultSet resultSet1=null;
 						String sql1 = "SELECT * FROM 客户 where  客户ID=?;";

 						String tmp01=acc.getText();
 						String tmp11=new String(psd.getPassword());
 						Connection conn11=null;
 						PreparedStatement ps11=null;
 						ResultSet resultSet11=null;
 						String sql11 = "SELECT * FROM 客户 where  客户ID=?;";
 				        try {
 				        	conn11=Conn.getConnection();
 				        	ps11=conn11.prepareStatement(sql11);
 					        ps11.setString(1, tmp01);
 					        resultSet11=ps11.executeQuery();
 					       if(resultSet11.next())
 					       {
 					    	   try{
 					    	   {
 					    		   p=resultSet11.getString("密码").compareTo(tmp11);
 					    	   }
 					    	   
 					    	   if(p==0)
 					    	   {
 					    		   info.setText("欢迎!\n来自"+resultSet.getString("地区")+resultSet.getString("城市")+"的"+resultSet.getString("联系人姓名")+"\n现在是"+formatter.format(calendar.getTime())+"\n祝您使用愉快");
 					    		   card.show(jp0, "3");
 					   			   my.setVisible(true);
 					    	   }
 					    	   else
 					    	   {
 						    	   dialog tip=new dialog("密码错误请重试或注册", 's');
 						    	   tip.jb1Button.addActionListener(new ActionListener() {
 									public void actionPerformed(ActionEvent e) {
 										tip.dispose();
 									}
 								});
 						    	   tip.setVisible(true);				    		   
 					    	   }}catch(Exception e1){
 					    		   info.setText("欢迎!\n来自"+resultSet.getString("地区")+resultSet.getString("城市")+"的"+resultSet.getString("联系人姓名")+"\n现在是"+formatter.format(calendar.getTime())+"\n祝您使用愉快");
 					    		   card.show(jp0, "3");
 					   			   my.setVisible(true);
 					    	   }
 					       }
 					       
 					       }catch(Exception e3){
 				        	e3.printStackTrace();
 				        }finally {
 				        	Conn.close(conn11, ps11, resultSet11);
 				        }		
 							
 			       }
 			       else
 			       {
 			    	   dialog tip=new dialog("未注册的用户，请重试或注册", 's');
 			    	   tip.jb1Button.addActionListener(new ActionListener() {
 						public void actionPerformed(ActionEvent e) {
 							tip.dispose();
 						}
 					});
 			    	   tip.setVisible(true);
 			       }
 			    		       
 			       }catch(Exception e3){
 		        	e3.printStackTrace();
 		        }finally {
 		        	Conn.close(conn, ps, resultSet);
 		        }
		}
	});
  	   logup.addActionListener(new ActionListener() {
 		public void actionPerformed(ActionEvent e) {
 			card.show(jp0, "2");
 		}
 	});
   	   login.addActionListener(new ActionListener() {
 		public void actionPerformed(ActionEvent e) {
 			String tmp=acc.getText();
 			Connection conn=null;
 			PreparedStatement ps=null;
 			ResultSet resultSet=null;
 			int p=1;
 			String sql = "SELECT * FROM 客户 where  客户ID=?;";
 		        try {
 		        	conn=Conn.getConnection();
 		        	ps=conn.prepareStatement(sql);
 			        ps.setString(1, tmp);
 			        resultSet=ps.executeQuery();
 			       if(resultSet.next())
 			       {
 						String tmp0=acc.getText();
 						String tmp1=new String(psd.getPassword());
 						Connection conn1=null;
 						PreparedStatement ps1=null;
 						ResultSet resultSet1=null;
 						String sql1 = "SELECT * FROM 客户 where  客户ID=?;";

 						String tmp01=acc.getText();
 						String tmp11=new String(psd.getPassword());
 						Connection conn11=null;
 						PreparedStatement ps11=null;
 						ResultSet resultSet11=null;
 						String sql11 = "SELECT * FROM 客户 where  客户ID=?;";
 				        try {
 				        	conn11=Conn.getConnection();
 				        	ps11=conn11.prepareStatement(sql11);
 					        ps11.setString(1, tmp01);
 					        resultSet11=ps11.executeQuery();
 					       if(resultSet11.next())
 					       {
 					    	   try{
 					    	   {
 					    		   p=resultSet11.getString("密码").compareTo(tmp11);
 					    	   }
 					    	   
 					    	   if(p==0)
 					    	   {
 					    		   info.setText("欢迎!\n来自"+resultSet.getString("城市")+"的"+resultSet.getString("联系人姓名")+"\n现在是"+formatter.format(calendar.getTime())+"\n祝您使用愉快");
 					    		   card.show(jp0, "3");
 					   			   my.setVisible(true);
 					    	   }
 					    	   else
 					    	   {
 						    	   dialog tip=new dialog("密码错误请重试或注册", 's');
 						    	   tip.jb1Button.addActionListener(new ActionListener() {
 									public void actionPerformed(ActionEvent e) {
 										tip.dispose();
 									}
 								});
 						    	   tip.setVisible(true);				    		   
 					    	   }}catch(Exception e1){
 					    		   info.setText("欢迎!\n来自"+resultSet.getString("城市")+resultSet.getString("联系人姓名")+"\n现在是"+formatter.format(calendar.getTime())+"\n祝您使用愉快");
 					    		   card.show(jp0, "3");
 					   			   my.setVisible(true);
 					    	   }
 					       }
 					       
 					       }catch(Exception e3){
 				        	e3.printStackTrace();
 				        }finally {
 				        	Conn.close(conn11, ps11, resultSet11);
 				        }		
 							
 			       }
 			       else
 			       {
 			    	   dialog tip=new dialog("未注册的用户，请重试或注册", 's');
 			    	   tip.jb1Button.addActionListener(new ActionListener() {
 						public void actionPerformed(ActionEvent e) {
 							tip.dispose();
 						}
 					});
 			    	   tip.setVisible(true);
 			       }
 			    		       
 			       }catch(Exception e3){
 		        	e3.printStackTrace();
 		        }finally {
 		        	Conn.close(conn, ps, resultSet);
 		        }
 		}
 	});
   	   
   	   //jp2 listener
   	   out1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			out1.setVisible(false);
			in.setVisible(true);
			out.setVisible(true);
			idJTextField.setEditable(true);companyJTextField.setEditable(true);conName.setEditable(true);scjl.setVisible(true);xsdb.setVisible(true);wz.setVisible(true);jsjl.setVisible(true);xsy.setVisible(true);conNameJTextField.setEditable(true);guojia.setEnabled(true);addressJTextField.setVisible(true);
			pro.setVisible(true);addressJTextField.setEditable(true);guojia.setEditable(true);phoneJTextField.setEditable(true);faxField.setEditable(true);posJTextField.setEditable(true);passwordField.setVisible(true);coPasswordField.setVisible(true);city.setVisible(true);area.setVisible(true);in_psdText.setVisible(true);confirm_psdText.setVisible(true);
			degree.setVisible(false);de.setVisible(false);
			card.show(jp0, "3");
			my.setVisible(true);
		}
	});
   	in.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String s1=idJTextField.getText();
			String s2=companyJTextField.getText();
			String s3=conNameJTextField.getText();
			String s4="";
			String s5=addressJTextField.getText();;
			String s6="";
			String s7="";
			String s8=posJTextField.getText();
			String s9="";
			String s10=phoneJTextField.getText();;
			String s11=faxField.getText();
			String s12=new String(passwordField.getPassword());
			String s13=new String(coPasswordField.getPassword());
			int p=1;
			int p1=0;
			int p2=0;
			int p3=0;
			int p4=0;
			int p11=0;
			int p22=0;
			int p33=0;
			p=s12.compareTo(s13);
			p1=s1.compareTo("");
			p2=s12.compareTo("");
			p3=s8.compareTo("");
			p4=s3.compareTo("");
			p11=s1.compareTo("请输入五位ID英文码");
			p22=s3.compareTo("请输入完整姓名");
			p33=s8.compareTo("请输入6位邮编");
			//s4;
			if(xsdb.isSelected()) {s4=s4+"销售代表";};
			if(scjl.isSelected()) {if(s4==" ") {s4=s4+"市场经理";}else {s4=s4+"/"+"市场经理";}};
			if(wz.isSelected()) {if(s4==" ") {s4=s4+"物主";}else {s4=s4+"/"+"物主";}};
			if(jsjl.isSelected()) {if(s4==" ") {s4=s4+"结算经理";}else {s4=s4+"/"+"结算经理";}};
			if(xsy.isSelected()) {if(s4==" ") {s4=s4+"销售员";}else {s4=s4+"/"+"销售员";}};
			//s6;
			s6=(String)pro.getSelectedItem();
			//s7;
			s7=area.getSelectedText();
			//s9;
			s9=(String) guojia.getSelectedItem();
			
			if(p==0&&p1!=0&&p2!=0&&p3!=0&&p4!=0&&p11!=0&&p22!=0&&p33!=0)
			{
				int c2=1,c3=1,c5=1,c10=1,c11=1;
				c2=s2.compareTo("请以公司全称输入");
				c5=s5.compareTo("详细地址请具体到街道单元号");
				c10=s10.compareTo("建议添加区号");
				c11=s11.compareTo("非必填项");
				int c22=1,c33=1,c55=1,c1010=1,c1111=1;
				c22=s2.compareTo("");
				c33=s3.compareTo("");
				c55=s5.compareTo("");
				c1010=s10.compareTo("");
				c1111=s11.compareTo("");
	
				if(c2==0||c22==0) {s2=null;};
				if(c5==0||c55==0) {s5=null;};
				if(c10==0||c1010==0) {s10=null;};
				if(c11==0||c1111==0) {s11=null;};
			Connection conn=null;
			PreparedStatement ps=null;
			ResultSet resultSet=null;
			String sql="insert into 客户(客户ID,公司名称,联系人姓名,联系人职务,地址,城市,地区,邮政编码,国家,电话,传真,密码) values (?,?,?,?,?,?,?,?,?,?,?,?);";
			try {
				conn=Conn.getConnection();
	        	ps=conn.prepareStatement(sql);
        		ps.setString(1,s1);
        		ps.setString(2,s2);
           		ps.setString(3,s3);
           		ps.setString(4,s4);
           		ps.setString(5,s5);
        		ps.setString(6,s6);
        		ps.setString(7,s7);
        		ps.setString(8,s8);
        		ps.setString(9,s9);
        		ps.setString(10,s10);
        		ps.setString(11,s11);
        		ps.setString(12,s12);
        		ps.executeUpdate(); 
				dialog tip=new dialog("注册成功", 's');
		    	   tip.jb1Button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tip.dispose();
					}
				});
		    	   tip.setVisible(true);	
			}catch(Exception e3){
				dialog tip=new dialog("用户名已存在", 's');
		    	   tip.jb1Button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tip.dispose();
					}
				});
		    	   tip.setVisible(true);
	        }finally {
	        	Conn.close(conn, ps, resultSet);
	        }
			}
			else
			{
				dialog tip=new dialog("用户名或密码或邮编设置有误", 's');
		    	   tip.jb1Button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tip.dispose();
					}
				});
		    	   tip.setVisible(true);	
			}
		} 		   
  	   });
  	   out.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			card.show(jp0, "1");
		}
	});
  	   faxField.addMouseListener(new MouseListener() {
		public void mouseReleased(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {
			if(faxField.getText().equals("非必填项")) {
				faxField.setText("");
			}}
	});
  	   phoneJTextField.addMouseListener(new MouseListener() {
		public void mouseReleased(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {
			if(phoneJTextField.getText().equals("建议添加区号"))
				{
				phoneJTextField.setText("");}
				}
	});
  	   addressJTextField.addMouseListener(new MouseListener() {
		public void mouseReleased(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {
			if(addressJTextField.getText().equals("详细地址请具体到街道单元号"))
			addressJTextField.setText("");}
	});
  	   conNameJTextField.addMouseListener(new MouseListener() {
		public void mouseReleased(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {
			if(conNameJTextField.getText().equals("请输入完整姓名"))
			{
				conNameJTextField.setText("");
			}
			}
	});
  	   companyJTextField.addMouseListener(new MouseListener() {
		public void mouseReleased(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {
			if(companyJTextField.getText().equals("请以公司全称输入"))
				{
				companyJTextField.setText("");}
				}
	});
  	   idJTextField.addMouseListener(new MouseListener() {
		public void mouseReleased(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {
			if(idJTextField.getText().equals("请输入五位ID英文码")) {
				idJTextField.setText("");}
			}
	});
  	   posJTextField.addMouseListener(new MouseListener() {
		public void mouseReleased(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {
			if(posJTextField.getText().equals("请输入6位邮编")){
				posJTextField.setText("");}
			}
	});
  	   passwordField.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		}
	});
  	   posJTextField.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String tmp=posJTextField.getText();
		}
	});
  	   faxField.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String tmp=faxField.getText();
		}
	});
  	   phoneJTextField.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String tmp=phoneJTextField.getText();
		}
	});
  	   addressJTextField.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String tmp=addressJTextField.getText();
		}
	});
  	   conNameJTextField.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String tmp=conNameJTextField.getSelectedText();
		}
	});
  	   companyJTextField.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String tmp=companyJTextField.getText();
		}
	});
  	   idJTextField.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String tmp=idJTextField.getText();
		}
	});
  	   pro.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String tmpString=(String)pro.getSelectedItem();
			Connection conn=null;
			PreparedStatement ps=null;
			ResultSet resultSet=null;
			String sql = "SELECT * FROM Sheet1 where  省=?;";
		        try {
		        	conn=Conn.getConnection();
		        	ps=conn.prepareStatement(sql);
			        ps.setString(1, tmpString);
			       resultSet=ps.executeQuery();
			       city.removeAllItems();
			       System.out.println();
			       while(resultSet.next())
			       {
			    	   city.addItem(resultSet.getString("城市名"));
			    	   area.setText("地区:"+resultSet.getString("地区"));
			       }
			       
			       }catch(Exception e3){
		        	e3.printStackTrace();
		        }finally {
		        	Conn.close(conn, ps, resultSet);
		        }
		}
	});	   

  	   //jp3 listener
 	    search.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) 
			{
				search.setText("");
			}
			public void mouseClicked(MouseEvent e) 
			{
				search.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						String tmpString=search.getText();
						Connection conn=null;
						PreparedStatement ps=null;
						ResultSet resultSet=null;
						String sql = "SELECT * FROM 产品 where  产品ID=? or 产品名称=?;";
					        try {
					        	conn=Conn.getConnection();
					        	ps=conn.prepareStatement(sql);
						        ps.setString(1, tmpString);
						       ps.setString(2, tmpString);
						       resultSet=ps.executeQuery();
						        if(resultSet.next())
						        {
						        	productName.setText(resultSet.getString("产品名称"));
						        	productNum.setText("剩余："+resultSet.getString("库存量"));
						        	productPrice.setText("单价:"+resultSet.getString("单价")+"元");
						        	card.show(jp0, "4");
						        }
						        else {
						        	dialog tip1=new dialog("输入有误或产品不存在", 's');
						        	tip1.jb1Button.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											tip1.dispose();
										}
									});
									tip1.setVisible(true);
								}
					        }catch(Exception e3){
					        	e3.printStackTrace();
					        }finally {
					        	Conn.close(conn, ps, resultSet);
					        }
					}
				});
			}
		});
 	    affirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tmpString=search.getText();
				Connection conn=null;
				PreparedStatement ps=null;
				ResultSet resultSet=null;
				String sql = "SELECT * FROM 产品 where  产品ID=? or 产品名称=?;";
			        try {
			        	conn=Conn.getConnection();
			        	ps=conn.prepareStatement(sql);
				        ps.setString(1, tmpString);
				       ps.setString(2, tmpString);
				       resultSet=ps.executeQuery();
				        if(resultSet.next())
				        {
				        	productName.setText(resultSet.getString("产品名称"));
				        	productNum.setText("剩余："+resultSet.getString("库存量"));
				        	productPrice.setText("单价:"+resultSet.getString("单价")+"元");
				        	card.show(jp0, "4");
				        }
				        else {
				        	dialog tip1=new dialog("输入有误或产品不存在", 's');
				        	tip1.jb1Button.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									tip1.dispose();
								}
							});
							tip1.setVisible(true);
						}
			        }catch(Exception e3){
			        	e3.printStackTrace();
			        }finally {
			        	Conn.close(conn, ps, resultSet);
			        }
			}
		});
 	    
 	   //jp4 listener
    	   put.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				 int b=i;	
    				    if(shopStrings[0][0]==null)
    				    {	
    						shopStrings[i][0]=productName.getText();
    						String tmp=productPrice.getText().replaceAll("单价:","").replaceAll("元", "");
    						shopStrings[i][1]=tmp;
    					    shopStrings[i][2]=num.getSelectedItem();
    				    }
    					 String[] tmp1=new String[2];
    					 tmp1[0]=productName.getText();
    					 tmp1[1]=num.getSelectedItem();					
    					 int a1 = 0;
    					 for(int k=0;k<i;k++)
    					 {	
    						 a1=shopStrings[k][0].compareTo(tmp1[0]);
    						 if(a1==0)
    						 {
    							 int m = Integer.parseInt(shopStrings[k][2]);
    							 int n = Integer.parseInt(tmp1[1]);
    							 int x=m+n;
    							 shopStrings[k][2]=Integer.toString(x);
    							 i--;
    							 System.out.println(i);
    							 break;
    						 }
    					 }						    	
    					 if(a1!=0)
    					 {
    						 shopStrings[i][0]=productName.getText();
    						 String tmp=productPrice.getText().replaceAll("单价:","").replaceAll("元", "");
    						 shopStrings[i][1]=tmp;
    						 shopStrings[i][2]=num.getSelectedItem();
    					 }
    					
    					 String tmpString=search.getText();
    					Connection conn=null;
    					PreparedStatement ps=null;
    					ResultSet resultSet=null;
    					
    				        try {	
    				        	int m;
    				        	int n;
    				        	String a="";
    				        	conn=Conn.getConnection();
    			        		String sql="SELECT * FROM 产品 where  产品ID=? or 产品名称=?;";
    			        		ps=conn.prepareStatement(sql);
    			        	    ps.setString(1, tmpString);
    						    ps.setString(2, tmpString);
    						    resultSet=ps.executeQuery();
    						    if(resultSet.next())
    						    {
    				    		m = Integer.parseInt(resultSet.getString("库存量"));
    				    		n = Integer.parseInt(tmp1[1]);
    				    		a=Integer.toString(m-n);
    						    }
    				    		String sql2= "update 产品 set 库存量=? where 产品名称=?;";
    				        	PreparedStatement ps1;
    				        	ps1=conn.prepareStatement(sql2);
    			        		ps1.setString(1,a);
    			        		ps1.setString(2,tmp1[0]);
    			        		ps1.executeUpdate();
    			        		
    			        		productNum.setText("剩余:"+a);
    				        }catch(Exception e3){
    				        	e3.printStackTrace();
    				        }finally {
    				        	Conn.close(conn, ps, resultSet);
    				        }		
    				i++;
    			    dialog tip=new dialog("添加成功！", 's');
    			    tip.jb1Button.addActionListener(new ActionListener() {
    					public void actionPerformed(ActionEvent e) {
    						tip.dispose();
    					}
    				});
    			    tip.setVisible(true);
    			}
    		});
    	   	   buy.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				String tmp=productPrice.getText().replaceAll("单价:","").replaceAll("元", "");
    				System.out.println(tmp);
    				String sumString=new String("一共是"+Double.parseDouble(tmp)*Double.parseDouble(num.getSelectedItem())+"元");
    				dialog tip=new dialog(sumString, 'c');
    				tip.jb1Button.addActionListener(new ActionListener() {
    					
    					@Override
    					public void actionPerformed(ActionEvent e) {
    						tip.dispose();
    					}
    				});
    				tip.setVisible(true);
    			}
    		});
    	   	   num.addItemListener(new ItemListener() {
    			public void itemStateChanged(ItemEvent e) {
    				String tmpString=num.getSelectedItem();
    				Connection conn=null;
    				PreparedStatement ps=null;
    				ResultSet resultSet=null;
    				String sql = "SELECT * FROM 产品 where  库存量-?>中止 AND 产品名称=?;";
    				try {
    		        	conn=Conn.getConnection();
    		        	ps=conn.prepareStatement(sql);
    			        ps.setString(1, tmpString);
    			        ps.setString(2, productName.getText());
    			        resultSet=ps.executeQuery();
    			        if(resultSet.next())
    			        {
    			        	put.setEnabled(true);
    			        	buy.setEnabled(true);
    			        }
    			        else {
    			        	put.setEnabled(false);
    			        	buy.setEnabled(false);
    			        }
    					}catch(Exception e3){
    			        	e3.printStackTrace();
    			        }finally {
    			        	Conn.close(conn, ps, resultSet);
    			        }
    			}
    		});
    	   	   retu1.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				card.show(jp0, "3");
    				put.setEnabled(false);
    				buy.setEnabled(false);
    			}
    		});
  	   
    		   look.addActionListener(new ActionListener() {
    				public void actionPerformed(ActionEvent e) {
    					my.setVisible(false);
    					card.show(jp0, "5");
    					shanchu.setEnabled(false);
    					tableModel.setDataVector(shopStrings, l1);
    					int k=0;
    					double price=0;
    					int num=0;
    					int sum=0;
    					if(i==0)
    					{
    						jiesuan.setEnabled(false);
    					}
    					else {
							jiesuan.setEnabled(true);
						}
    					for(k=0;k<i;k++)
    					{
    						price=Double.valueOf(shopStrings[k][1]).intValue();
    						num=Integer.parseInt(shopStrings[k][2]);
    						sum+=price*num;
    					}
    					String sum1= String.valueOf(sum);
    					priceJTextField.setText(sum1+"元");
    				}
    			});
    		   
    	       //jp5 listener
    		   jiesuan.addActionListener(new ActionListener() {
   				
   				public void actionPerformed(ActionEvent e) {
   					
   					int id=0;
   					Connection conn=null;
   					PreparedStatement ps=null;
   					ResultSet resultSet=null;
   					String sql="SELECT TOP 1 * FROM 订单 order by 订单id desc";
   					try {
   			        	conn=Conn.getConnection();
   			        	ps=conn.prepareStatement(sql);
   				        resultSet=ps.executeQuery();
   				        if(resultSet.next())
   				        {
   				        	id=resultSet.getInt("订单ID");
   				        }
   				        id+=1;
   				        System.out.println(id);
   				        
   		        		
   		        		for(int x=0;x<i;x++)
   		        		{
   		        			int g=0;
   		        			String tmpsql="SELECT * FROM 产品 where 产品名称=?";
   		        			PreparedStatement ps3=null;
   		        			ResultSet resultSet1=null;
   		        			ps3=conn.prepareStatement(tmpsql);
   		        			ps3.setString(1,shopStrings[x][0] );
   		        			resultSet1=ps3.executeQuery();
   		        			if(resultSet1.next())
   		        			{g=resultSet1.getInt("产品ID"); }
   		        			String sql31="insert into 订单明细(订单ID,产品ID,数量) values (?,?,?);";
   		        			PreparedStatement ps21=null;
   		        			ps21=conn.prepareStatement(sql31);
   		        			ps21.setInt(1, id);
   		        			ps21.setInt(2, g);
   		        			ps21.setString(3, shopStrings[x][2]);
   		        			ps21.executeUpdate();		        			   
   		        		}
   		       			   for(int j=0;j<20;j++)
		        			   {
		        				   shopStrings[j]=new String[3];
		        			   }  
   		        		i=0;
							tableModel.setDataVector(shopStrings, l1);
							int k=0;
	    					double price=0;
	    					int num=0;
	    					int sum=0;
	    					if(i==0)
	    					{
	    						jiesuan.setEnabled(false);
	    					}
	    					for(k=0;k<i;k++)
	    					{
	    						price=Double.valueOf(shopStrings[k][1]).intValue();
	    						num=Integer.parseInt(shopStrings[k][2]);
	    						sum+=price*num;
	    					}
	    					String sum1= String.valueOf(sum);
	    					priceJTextField.setText(sum1+"元");
	    					shanchu.setEnabled(false);
		        			dialog tip=new dialog("订单创建成功!",'s');
  		        			tip.jb1Button.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										tip.dispose();
										int k=0;
				    					double price=0;
				    					int num=0;
				    					int sum=0;
				    					if(i==0)
				    					{
				    						jiesuan.setEnabled(false);
				    					}
				    					for(k=0;k<i;k++)
				    					{
				    						price=Double.valueOf(shopStrings[k][1]).intValue();
				    						num=Integer.parseInt(shopStrings[k][2]);
				    						sum+=price*num;
				    					}
				    					String sum1= String.valueOf(sum);
				    					priceJTextField.setText(sum1+"元");
				    					shanchu.setEnabled(false);
									}
								});
		        			tip.setVisible(true);
   		        		String sql3="SELECT * FROM 客户 where  客户ID=?;";
    				        PreparedStatement ps2=conn.prepareStatement(sql3);
    				        ps2.setString(1, acc.getText());
    				        resultSet=ps2.executeQuery();
    			        	System.out.println(resultSet.next());
    			    		String sql2= "insert into 订单(订单ID,订购日期,发货日期,到货日期,贷款确认日期,货主名称,货主地址,货主城市,货主地区,货主邮政编码,货主国家) values (?,?,?,?,?,?,?,?,?,?,?);";
    			        	PreparedStatement ps1=null;
    			        	ps1=conn.prepareStatement(sql2);
    		        		ps1.setInt(1,id);
    		        		ps1.setDate(2, time);
    		           		ps1.setDate(3, null);
    		           		ps1.setDate(4, null);
    		           		ps1.setDate(5, null);
    		        		ps1.setString(6,resultSet.getString("联系人姓名"));
    		        		ps1.setString(7,resultSet.getString("地址"));
    		        		ps1.setString(8,resultSet.getString("城市"));
    		        		ps1.setString(9,resultSet.getString("地区"));
    		        		ps1.setString(10,resultSet.getString("邮政编码"));
    		        		ps1.setString(11,resultSet.getString("国家"));
    		        		ps1.executeUpdate();
   						}catch(Exception e3){
   				        	e3.printStackTrace();
   				        }finally {
   				        	Conn.close(conn, ps, resultSet);
   				        }
   				}	   
   			   });

    		   shanchu.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				dialog tip=new dialog("确认删除此产品吗?",'c');
    				tip.jb1Button.addActionListener(new ActionListener() {				
    					public void actionPerformed(ActionEvent e) {
    						int k=0;
    						String[] tmp2=new String[2];
    						tmp2[0]=shopStrings[t][0];
    						tmp2[1]=shopStrings[t][2];
    						for(k=0;k<i;k++)
    						{
    							if(k>=t)
    							{
    								shopStrings[k][0]=shopStrings[k+1][0];
    								shopStrings[k][1]=shopStrings[k+1][1];
    								shopStrings[k][2]=shopStrings[k+1][2];
    							}
    						}
    						if(k>t){i--;}
    							Connection conn=null;
    							PreparedStatement ps=null;
    							ResultSet resultSet=null;
    							
    						        try {	
    						        	int m;
    						        	int n;
    						        	String a="";
    						        	conn=Conn.getConnection();
    					        		String sql="SELECT * FROM 产品 where 产品名称=?;";
    					        		ps=conn.prepareStatement(sql);
    					        	    ps.setString(1, tmp2[0]);
    								    resultSet=ps.executeQuery();
    								    if(resultSet.next())
    								    {
    						    		m = Integer.parseInt(resultSet.getString("库存量"));
    						    		n = Integer.parseInt(tmp2[1]);
    						    		a=Integer.toString(m+n);
    								    }
    						    		String sql2= "update 产品 set 库存量=? where 产品名称=?;";
    						        	PreparedStatement ps1;
    						        	ps1=conn.prepareStatement(sql2);
    					        		ps1.setString(1,a);
    					        		ps1.setString(2,tmp2[0]);
    					        		ps1.executeUpdate();			        		
    					        		productNum.setText("剩余:"+a);
    					        		dialog tip1=new dialog("删除成功!", 's');
    							        tip1.jb1Button.addActionListener(new ActionListener() {
    										public void actionPerformed(ActionEvent e) {
    											tip1.dispose();
    											tip.dispose();
    											tableModel.setDataVector(shopStrings, l1);
    											shanchu.setEnabled(false);
    											int k=0;
    					    					double price=0;
    					    					int num=0;
    					    					int sum=0;
    					    					if(i==0)
    					    					{
    					    						jiesuan.setEnabled(false);
    					    					}
    					    					for(k=0;k<i;k++)
    					    					{
    					    						price=Double.valueOf(shopStrings[k][1]).intValue();
    					    						num=Integer.parseInt(shopStrings[k][2]);
    					    						sum+=price*num;
    					    					}
    					    					String sum1= String.valueOf(sum);
    					    					priceJTextField.setText(sum1+"元");
    										}
    									});
    							        tip1.setVisible(true);
    						        }catch(Exception e3){
    						        	e3.printStackTrace();
    						        }finally {
    						        	Conn.close(conn, ps, resultSet);
    						        }	        
    					}
    				});
    				tip.jb2Button.addActionListener(new ActionListener() {
    					public void actionPerformed(ActionEvent e) {
    						tip.dispose();
    					}
    				});
    				tip.setVisible(true);
    			}
    		});
    	  	   table.addMouseListener(new MouseListener() {
    			public void mouseClicked(MouseEvent e) {
    				t=table.getSelectedRow();
    				shanchu.setEnabled(false);
    				if(i>=0&&t<i)
    				{
    					shanchu.setEnabled(true);
    				}
    				else {
    					shanchu.setEnabled(false);
					}

    			}
    			public void mousePressed(MouseEvent e) {}
    			public void mouseReleased(MouseEvent e) {}
    			public void mouseEntered(MouseEvent e) {}
    			public void mouseExited(MouseEvent e) {}
    		});
    	  	   jp2.addMouseListener(new MouseListener() {
    			public void mouseReleased(MouseEvent e) {}
    			public void mousePressed(MouseEvent e) {}
    			public void mouseExited(MouseEvent e) {}
    			public void mouseEntered(MouseEvent e) {}
    			public void mouseClicked(MouseEvent e) {
    				shanchu.setEnabled(false);
    				table.clearSelection();
    			}
    		});
    	  	   retu2.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				card.show(jp0, "4");
    				my.setVisible(true);
    			}
    		});
    	  	  
    	//menu listener
    	  	   CheckOrder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
						JDialog tip=new JDialog(new JFrame(),"我的购买历史",true);
						tip.setFont(font);
						tip.setSize(600,500);
						tip.setLocationRelativeTo(null);
						tip.setLayout(null);
						String [][]shopStringss=new String[20][];
						for(int j=0;j<20;j++)
						{
							shopStringss[j]=new String[5];
						}
						String b1 = null,b2 = null;
						Connection conn=null;
						PreparedStatement ps=null;
						ResultSet resultSet=null;
						String sql="SELECT * FROM 客户 where 客户ID=?";
						try {
							conn=Conn.getConnection();
							ps=conn.prepareStatement(sql);
							ps.setString(1,acc.getText());
							resultSet=ps.executeQuery();
							if(resultSet.next())
							{
								b1=resultSet.getString("联系人姓名");
								b2=resultSet.getString("邮政编码");
							}
							try {
								String sql1 = "SELECT * FROM 订单,订单明细,产品 where  货主名称=? and 货主邮政编码=? and 订单明细.订单ID=订单.订单ID and 订单明细.产品ID=产品.产品ID;";
								System.out.println(b1);
								Connection conn1=Conn.getConnection();
								PreparedStatement ps1=null;
								ResultSet resultSet1=null;
								conn1=Conn.getConnection();
								ps1=conn1.prepareStatement(sql1);
								ps1.setString(1,b1);
								ps1.setString(2,b2);
								resultSet1=ps1.executeQuery();
								System.out.println(b1);
								System.out.println(b2);
								int k=0;
								while(resultSet1.next())
								{
									shopStringss[k][0]=resultSet1.getString("产品名称");
									shopStringss[k][1]=resultSet1.getString("单价");
									shopStringss[k][2]=resultSet1.getString("数量");
									shopStringss[k][3]=resultSet1.getString("订购日期");
									shopStringss[k][4]=Integer.toString(resultSet1.getInt("单价")*resultSet1.getInt("数量"));
									k++;
								}
								
							}catch(Exception e4){
								e4.printStackTrace();
							}
						}catch(Exception e5) {
							e5.printStackTrace();
						}
						
						String[] l2= {"产品名称","产品单价","购买数量","购买日期","总金额"};
						DefaultTableModel tableModel=new DefaultTableModel(shopStringss,l2) {
							   public boolean isCellEditable(int row,int column)
							   {
								   return false;
							   }
						   };
					   JTable table =new JTable(tableModel);
					   table.setRowHeight(24);
				  	   JScrollPane scro=new JScrollPane(table);
				  	   scro.setBounds(25,10,540,400);
					JButton j1=new JButton("确认");
					j1.setFont(jFont);
					j1.setBounds(260,415,80,40);
					j1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							tip.dispose();
						}
					});
					tip.add(j1);tip.add(scro);
					tip.setVisible(true);
				}
			});
    	  	   SelfInfo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String s1 = null,s2 = null,s3 = null,s4=null,s5 = null,s6=null,s7 = null,s8 = null,s9 = null,s10 = null,s11 = null,s12 = null;
					String t=acc.getText();;
					Connection conn=null;
					PreparedStatement ps=null;
					ResultSet resultSet=null;
					String sql="SELECT * FROM 客户 where 客户ID=?;";
					try {
						conn=Conn.getConnection();
						ps=conn.prepareStatement(sql);
						ps.setString(1,t);
						resultSet=ps.executeQuery();
						if(resultSet.next())
						{
							s1=resultSet.getString("客户ID");
							s2=resultSet.getString("公司名称");
							s3=resultSet.getString("联系人姓名");
							s4=resultSet.getString("联系人职务");
							s5=resultSet.getString("地址");
							s6=resultSet.getString("城市");
							s7=resultSet.getString("地区");
							s8=resultSet.getString("邮政编码");
							s9=resultSet.getString("国家");
							s10=resultSet.getString("电话");
							s11=resultSet.getString("传真");
							s12=resultSet.getString("密码");
						}
						idJTextField.setText(s1); 
						companyJTextField.setText(s2);
						conNameJTextField.setText(s3);
						guojia.setSelectedItem(s9);
						addressJTextField.setText(s5);
						degree.setText(s4);
						de.setText(s7+"地区"+s6+s5);
						posJTextField.setText(s8);
						phoneJTextField.setText(s10);
						faxField.setText(s11);
						passwordField.setText(s12);
					}catch(Exception e3) {
				
					}
					out1.setVisible(true);
					in.setVisible(false);
					out.setVisible(false);
					my.setVisible(false);
					
					idJTextField.setEditable(false);companyJTextField.setEditable(false);conName.setEditable(false);scjl.setVisible(false);xsdb.setVisible(false);wz.setVisible(false);jsjl.setVisible(false);xsy.setVisible(false);conNameJTextField.setEditable(false);guojia.setEnabled(false);addressJTextField.setVisible(false);
					pro.setVisible(false);addressJTextField.setEditable(false);guojia.setEditable(false);phoneJTextField.setEditable(false);faxField.setEditable(false);posJTextField.setEditable(false);passwordField.setVisible(false);coPasswordField.setVisible(false);city.setVisible(false);area.setVisible(false);in_psdText.setVisible(false);confirm_psdText.setVisible(false);
					degree.setVisible(true);de.setVisible(true);
					card.show(jp0, "2");
				}
			});
    	   	   SignOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					card.show(jp0, "1");
					psd.setText("");
					my.setVisible(false);
					dialog tip=new dialog("已成功退出账号!",'s');
					tip.jb1Button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							tip.dispose();
						}
					});
					tip.setVisible(true);
				}
			});
   	   //add panel

       jp0.add(jpf,"0");
  	   jp0.add(jp1,"1");
	   jp0.add(jp2,"2");
  	   jp0.add(jp3,"3");
	   jp0.add(jp4,"4");
  	   jp0.add(jp5,"5");
       jp0.add(jps,"6");
       jp0.add(jpd,"7");

	    
	  	this.add(jp0);
	    this.setVisible(true);
	}
}
