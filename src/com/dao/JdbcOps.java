//AUTHOR: Dhruv Bindoria

package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;

import jdbcapp.LtiEmp;

public class JdbcOps {
	//Made these constants because I don't want any other application/class to change these global settings as they are fixed and universal
	final static String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver"; //Note that I've used Oracle 11g Express Edition
	final static String ORACLE_URL = "jdbc:oracle:thin:@localhost:1521:xe"; //Used ojdbc6.jar
	final static String ORACLE_USERNAME = "db"; //This the workspace. Note: "system" is a different workspace and "db" is a user created workspace in oracle 11g.
	final static String ORACLE_PASSWORD = "Newuser123"; //I've kept password common for all, because it is too much convenient...
	
	//I feel it's a good practice to put queries into variables which can be changed if need later in time with help of getter and setter methods.
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

	//Module for creating connection
	public Connection openConnection() {
		Connection con = null;
		try {
			Class.forName(ORACLE_DRIVER); //Driver gets loaded..
			con = DriverManager.getConnection(ORACLE_URL, ORACLE_USERNAME, ORACLE_PASSWORD); //Connection gets established..
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
	public int insertValues(ArrayList<LtiEmp> lst) {
		//Type Casting is required if the argument of this method was of Collection type..
		//ArrayList<LtiEmp> al = (ArrayList<LtiEmp>)lst;
		Connection con = openConnection();
		int i = 0;
		try {
			ps = con.prepareStatement(getInsert_query()); //The query here is dynamic query, means values will be taken by user a runtime. Hence, prepareStatement() is used.
			ps.setInt(1, lst.get(0).getRno());
			ps.setString(2, lst.get(0).getUname());
			ps.setString(3, lst.get(0).getPass());
			ps.setDouble(4, lst.get(0).getAmt());
			i = ps.executeUpdate(); //On executing DML queries, the row count of the change-affected row(s) is returned. Hence, executeUpdate() is used.
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
			ResultSet rs = ps.executeQuery(); //On executing DQL queries, the respective record(s) are returned, so we need rs variable of type ResultSet to store it. Hence executeUpdate() is used
			ResultSetMetaData rsmd = rs.getMetaData(); //For fetching out the meta data and the column name and numbers..
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
			s = con.createStatement(); //This is a static type query, means no user input required. Hence createStatemnet is used
			ResultSet rs = s.executeQuery(getSelect_all());
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
			System.out.println("Error, used Statement, createStatement, executeQuery(str)");
			e.printStackTrace();
		}
	}
}
