package cn.sdut.bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
public class to_other_money_sql{
   public void to_other_money(String my_number, double money, String to_number, String pay_number) throws Exception{
      try{
    	  // 加载驱动类
    	  Class.forName("com.mysql.cj.jdbc.Driver");
    	  // 建立连接
    	  Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?useSSL=false&serverTimezone=GMT","root","zhang152274");
    	  // 连接成功
    	   System.out.println("Connectinon established successfully!");
    	  Statement st = con.createStatement();//创建语句对象
    	  
    	  String sql = "select * from user_message";
    	 
    	  ResultSet rs, rt;
    	  rs = st.executeQuery(sql);
    	  
    	  int flag = 0;
    	  while(rs.next()){
    		  String pay_number1 = rs.getString("password");
    		  String id_number1 = rs.getString("id_number");
    		  String strName = rs.getString("name");
    		  Double money1 = rs.getDouble("now_money");
    		  if((my_number).equals(id_number1)||(to_number).equals(id_number1)){
    			  //System.out.println("nishahfjhasjfha");
    			  if((pay_number).equals(pay_number1)){
    				  double a = money1 - money;
    				  if(a < 0){
    					  flag = 2;
    					  break;
    				  }
    				  else {
    					  st.execute("update user_message set now_money = " + a + " where id_number= "+my_number+" ");
    					  rt = st.executeQuery("select * from user_message");
    					  while(rt.next()){
    						  String id_number2 = rt.getString("id_number");
    						  Double money2 = rt.getDouble("now_money");
    						  String pass = rt.getString("password");
    						  String old_pass = rt.getString("oldpassword");
    						  if((to_number).equals(id_number2)){
    							  a = money2 + money;
    							  st.execute("update user_message set now_money = " + a + " where id_number= "+to_number+" ");
    							  System.out.println("转账成功！");
    							  JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "转账成功！", "提示",JOptionPane.INFORMATION_MESSAGE);
    							  new log_sql(my_number, strName, "向账户"+to_number+"  转款:"+money+"元", old_pass, pass);
    							  flag = 1;
    							  break;
    						  }
    					  }
    					  if(flag == 1){
    						  break;
    					  }  
    				  }  
    			  }
    			  else {
    				  flag = 3;
    				  break;
    			  }
    		  }
    	  }
    	  if(flag == 0){
    		  System.out.println("己方或者对方账户不存在！");
    		  JOptionPane.showMessageDialog(new JFrame(), "己方或者对方账户不存在！","提示",JOptionPane.ERROR_MESSAGE);
    	  }
    	  if(flag == 2){
    		  System.out.println("余款不足！");
    		  JOptionPane.showMessageDialog(new JFrame(), "余款不足！","提示",JOptionPane.ERROR_MESSAGE);
    	  }
    	  if(flag == 3){
    		  System.out.println("密码错误！");
    		  JOptionPane.showMessageDialog(new JFrame(), "密码错误！","提示",JOptionPane.ERROR_MESSAGE);
    	  }
    	  st.close();
    	  con.close();
    } 
    catch(SQLException e){
	   e.printStackTrace();
     }
    catch(ClassNotFoundException e){
	   e.printStackTrace();
    }
    }
}


