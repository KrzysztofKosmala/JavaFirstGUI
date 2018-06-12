package smmk.projekt;

import java.sql.*;



public class DataBase {
   

   
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        private String queryAll="select * from employees";
        
        
        public DataBase() throws SQLException {
            polacz();
        }

        private void polacz() throws SQLException {
        try {
            // 1. Get a connection to database
            myConn = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net/sql7242628", "sql7242628" , "kFAI7a8jXA");
            
            // 2. Create a statement
            myStmt = myConn.createStatement();
            
            // 3. Execute SQL query
            myRs = myStmt.executeQuery(queryAll);
            
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }

    

        }
        
        public void deletePerson(int ID){
            try {
                String sql = "DELETE FROM employees WHERE id = ?";
                PreparedStatement delete = myConn.prepareStatement(sql);
                delete.setInt(1, ID);
                delete.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            };
        }
        
        public void addPerson(int ID, String last_name, String first_name, String email, String department, double salary){
            try {
                String sql = "INSERT INTO `employees` (`id`, `last_name`,`first_name`,`email`, `department`, `salary`) VALUES (?,?,?, ?, ?,?);";
                PreparedStatement addPerson = myConn.prepareStatement(sql);
                
                addPerson.setInt(1, ID);
                addPerson.setString(2, last_name);
                addPerson.setString(3, first_name);
                addPerson.setString(4, email);
                addPerson.setString(5, department);
                addPerson.setDouble(6, salary);

                addPerson.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            };
        }
        
        public void editPerson(int ID, String last_name, String first_name, String email, String department, double salary){
            try {
                String sql = "UPDATE `employees` SET `id`=?, `last_name`=?,`first_name`=?,`email`=?, `department`=?, `salary`=? WHERE id=?;";
                PreparedStatement editPerson = myConn.prepareStatement(sql);
                
                editPerson.setInt(1, ID);
                editPerson.setString(2, last_name);
                editPerson.setString(3, first_name);
                editPerson.setString(4, email);
                editPerson.setString(5, department);
                editPerson.setDouble(6, salary);
                editPerson.setInt(7, ID);
                editPerson.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            };
        }
        
        public void show() {
            try {
               
                myRs = myStmt.executeQuery(queryAll);
                while (myRs.next()) {
                    System.out.println(myRs.getString("last_name") + ", " + myRs.getString("first_name") + "," + myRs.getString("id"));
                }
            }
            catch (Exception exc) {
                exc.printStackTrace();
            }

            }
        
        public void closeDataBase() throws SQLException {
                if (myRs != null) {
                    myRs.close();
                }
                
                if (myStmt != null) {
                    myStmt.close();
                }
                
                if (myConn != null) {
                    myConn.close();
                }
            }
        
        public Object[][] getEmployees(){
            String sql = "SELECT COUNT(*) FROM employees";
            String sql2 = "SELECT * FROM employees";
            Statement stmt = null;
            int size = 0;
            try {
                stmt = myConn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    size = rs.getInt(1);                
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            
            Object[][] data = new Object[size][6];
            try {
                stmt = myConn.createStatement();
                ResultSet rs = stmt.executeQuery(sql2);
                
                for(int i=0; rs.next(); i++){
                    data[i][0] = rs.getInt(1);
                    data[i][1] = rs.getString(2).trim();
                    data[i][2] = rs.getString(3).trim();
                    data[i][3] = rs.getString(4).trim();
                    data[i][4] = rs.getString(5).trim();
                    data[i][5] = rs.getDouble(6);
                    
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            
            return data;
        }

}
