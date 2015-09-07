<!DOCTYPE html>
<%@ 
	page contentType="text/html" pageEncoding="UTF-8" language="java"
	import="java.sql.*,java.io.*,java.util.*" session="true" 
%>
<%
	Connection conn = null;
        
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineshop?user=root");
	PreparedStatement psSelectRecord=null;
	ResultSet rsSelectRecord=null;
	String sqlSelectRecord=null;
   
	sqlSelectRecord ="SELECT * FROM onlineshop.products";
	psSelectRecord=conn.prepareStatement(sqlSelectRecord);
	rsSelectRecord=psSelectRecord.executeQuery();
%>
<html>
	<head>
		<title>OnlineShop</title>
	</head>
	<body>
 		<center>
     		<h1>View Products from Database</h1>
     		<form method=POST action="Add">
 			<table>
 				<tr>
				   <td bgcolor="#33CCCC">Product Name</td>
				   <td bgcolor="#33CCCC">Product Description</td>
				   <td bgcolor="#33CCCC">Price</td>
				</tr>
				<%
				int cnt=1;
				while(rsSelectRecord.next()){
					%>
				   	<tr>
				   		<td>
				   			<INPUT type="Radio" name="Product" value=<%=rsSelectRecord.getString("Product")%>>
							<FONT><%=rsSelectRecord.getString("Product")%></FONT>
				   		</td>
					   	<td>
					   		<%=rsSelectRecord.getString("ProductDescription")%>
					   	</td>
					   	<td>
					   		<%=rsSelectRecord.getString("ProductPrice") %>
					   	</td>
				   	<tr>
				  <%
				   	cnt++;
				  }
				  %>
				  <tr>
				  	<td></td>
				  	<td>
				  		<input type="Radio" name="Product" value="Cart">
				  		<font>Go to cart</font>
				  	</td>
				  </tr>
  			</table>
  				<input type="Submit" value="Submit">
  			</form>
	</body>
</html>
<%
try{
	if(psSelectRecord!=null)
		psSelectRecord.close();
          
	if(rsSelectRecord!=null)
		rsSelectRecord.close();      
          
	if(conn!=null)
		conn.close();
}
catch(Exception e){
	e.printStackTrace(); 
}
%>
