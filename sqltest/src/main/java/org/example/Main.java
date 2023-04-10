package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "my.jperrotta123");
            Statement stmt = con.createStatement();

            ResultSet rs=stmt.executeQuery("SELECT * FROM actor WHERE first_name LIKE 'j%'");
            while(rs.next()) System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
        } catch (Exception e){
            System.out.println(e);
        }
    }
}