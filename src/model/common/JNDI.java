package model.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JNDI {
	public static Connection connect( ) {
		Connection conn = null;
		
		try {
			Context initContext = new InitialContext();
			Context envContext = InitialContext.doLookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/orcl");
			
			conn=ds.getConnection();
		} catch (Exception e) {
			System.out.println("connect()오류");
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void disconnect(PreparedStatement pstmt, Connection conn) {
		try {
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("disconnect() 오류");
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
}
