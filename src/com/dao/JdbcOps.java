package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class JdbcOps {
	//Why have you made them constants?
	final static String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	final static String ORACLE_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	final static String ORACLE_USERNAME = "db";
	final static String ORACLE_PASSWORD = "Newuser123";
	
	//Decide on why you've kept these variables as static?
	private static String insert_query = "INSERT INTO LTIEMP VALUES(?,?,?,?)";
	private static String update_amount = "UPDATE LTIEMP SET amt = (amt + (amt*0.05)) WHERE rno = ?";
	private static String find_by_id = "SELECT * FROM LTIEMP WHERE rno = ?";
	private static String delete_record = "DELETE FROM LTIEMP WHERE rno = ?";
	private static String select_all = "SELECT * FROM LTIEMP";
			
	private Statement s;
	private PreparedStatement ps;
	
	public static String getInsert_query() {
		return insert_query;
	}

	public static void setInsert_query(String insert_query) {
		JdbcOps.insert_query = insert_query;
	}

	public static String getUpdate_amount() {
		return update_amount;
	}

	public static void setUpdate_amount(String update_amount) {
		JdbcOps.update_amount = update_amount;
	}

	public static String getFind_by_id() {
		return find_by_id;
	}

	public static void setFind_by_id(String find_by_id) {
		JdbcOps.find_by_id = find_by_id;
	}

	public static String getDelete_record() {
		return delete_record;
	}

	public static void setDelete_record(String delete_record) {
		JdbcOps.delete_record = delete_record;
	}

	public static String getSelect_all() {
		return select_all;
	}

	public static void setSelect_all(String select_all) {
		JdbcOps.select_all = select_all;
	}

	public Statement getS() {
		return s;
	}

	public PreparedStatement getPs() {
		return ps;
	}


	//Module for creating connection
	public Connection openConnection() {
		Connection con = null;
		try {
			Class.forName(ORACLE_DRIVER);
			con = DriverManager.getConnection(ORACLE_URL, ORACLE_USERNAME, ORACLE_PASSWORD);
		} catch (SQLException e) {
			System.out.println("Some error related to establishing connection -> " + e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found -> " + e);
			e.printStackTrace();
		}
		return con;
	}
	
	//Module for saving/inserting data
	public int insertValues(int id, String name, String pwd, double amt) {
		Connection con = openConnection();
		int i = 0;
		try {
			ps = con.prepareStatement(getInsert_query());
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setString(3, pwd);
			ps.setDouble(4, amt);
			i = ps.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error, used prepareStatement" + e);
			e.printStackTrace();
		}	
		return i;
	}
	
	//Module for updating amount
	public int updateAmount(int id) {
		Connection con = openConnection();
		int i = 0;
		try {
			ps = con.prepareStatement(getUpdate_amount());
			ps.setInt(1, id);
			i = ps.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error, used ps.executeUpdate() " + id);
			e.printStackTrace();
		}
		return i;
	}
	
	//Module for searching a particular user by id
	//Try adding collection here.. pass that collection to 
	//the caller class and then pass it to a display function
	public void searchEmployee(int id) {
		try {
			Connection con = openConnection();
			ps = con.prepareStatement(getFind_by_id());
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i = 1; i <= rsmd.getColumnCount(); i++) {
				System.out.print(rsmd.getColumnName(i) + "\t");
			}
			System.out.println("\n-------------------------------------------------------------");
			while(rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getDouble(4));
			}
			con.close();
		} catch (SQLException e) {
			System.out.println("Error, used ResultSet rs, ps.executeQuery()");
			e.printStackTrace();
		}
	}
	
	//Module for Deleting data
	public int deleteRow(int id) {
		Connection con = openConnection();
		int i = 0;
		try {
			ps = con.prepareStatement(getDelete_record());
			ps.setInt(1, id);
			i = ps.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.out.println("Some error occured while deleting on the given id: " + id);
			e.printStackTrace();
		}
		return i;
	}
	
	//Module for Displaying all data
	public void displayAll() {
		Connection con = openConnection();
		try {
			s = con.createStatement();
			ResultSet rs = s.executeQuery(getSelect_all());
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i = 1; i <= rsmd.getColumnCount(); i++) {
				System.out.print(rsmd.getColumnName(i) + "\t");
			}
			System.out.println("\n-------------------------------------------------------------");
			while(rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getDouble(4));
			}	
		} catch (SQLException e) {
			System.out.println("Error, used Statement, createStatement, executeQuery(str)");
			e.printStackTrace();
		}
	}
}
