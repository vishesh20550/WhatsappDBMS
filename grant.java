import java.sql.*;
import java.util.Scanner;


public class grant {

    static Connection con;
    public static void main(String[] args) throws SQLException {

        con = null;
        try {
            
            String url1 = "jdbc:mysql://103.252.116.130:3306/Whatsapp";
            String user = "root";
            String password = "DBMSProject123";
 
            con = DriverManager.getConnection(url1, user, password);
            if (con != null) {
                System.out.println("Connected to the database test1");
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
        // createuser();
        login();
    }

    public static void createuser() throws SQLException {
        PreparedStatement p = con.prepareStatement("select * from auth_table");
        ResultSet rs = p.executeQuery();
        while (rs.next()) {    
            Integer name = rs.getInt("User_id");
            String password = rs.getString("password");
            PreparedStatement cuser = con.prepareStatement("create user '" + Integer.toString(name) +"'@'103.252.116.130' IDENTIFIED BY '" +password+"';");
            cuser.execute();
            String query1 = "Grant insert,delete on Whatsapp.chats to '"+Integer.toString(name)+"'@'103.252.116.130'WITH GRANT OPTION;";
            String query2 = "Grant insert,update,delete on Whatsapp.chatlist to '"+Integer.toString(name)+"'@'103.252.116.130'WITH GRANT OPTION;";
            String query3 = "Grant insert,update,delete on Whatsapp.auth_table to '"+Integer.toString(name)+"'@'103.252.116.130'WITH GRANT OPTION;";
            String query4 = "Grant insert,delete on Whatsapp.groupchats to '"+Integer.toString(name)+"'@'103.252.116.130'WITH GRANT OPTION;";
            String query5 = "Grant insert,update,delete on Whatsapp.grouplist to '"+Integer.toString(name)+"'@'103.252.116.130'WITH GRANT OPTION;";
            String query6 = "Grant insert,update,delete on Whatsapp.hasgrouprelation to '"+Integer.toString(name)+"'@'103.252.116.130'WITH GRANT OPTION;";
            String query7 = "Grant insert,delete on Whatsapp.groupmessageid to '"+Integer.toString(name)+"'@'103.252.116.130'WITH GRANT OPTION;";
            String query8 = "Grant insert,update on Whatsapp.groupmessageid to '"+Integer.toString(name)+"'@'103.252.116.130'WITH GRANT OPTION;";
            for(int i =1;i<9;i++){
                PreparedStatement permission1 = con.prepareStatement(query1);
                PreparedStatement permission2 = con.prepareStatement(query2);
                PreparedStatement permission3 = con.prepareStatement(query3);
                PreparedStatement permission4 = con.prepareStatement(query4);
                PreparedStatement permission5 = con.prepareStatement(query5);
                PreparedStatement permission6 = con.prepareStatement(query6);
                PreparedStatement permission7 = con.prepareStatement(query7);
                PreparedStatement permission8 = con.prepareStatement(query8);
                permission1.execute();
                permission3.execute();
                permission2.execute();
                permission4.execute();
                permission5.execute();
                permission6.execute();
                permission7.execute();
                permission8.execute();}
        }
    }
    public static void login() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter id");
        String id = sc.next();
        System.out.println("Enter pass");
        String pass = sc.next();
        Connection con1 = null;
        try {
            
            String url1 = "jdbc:mysql://103.252.116.130:3306/Whatsapp";
            String user = id;
            String password = pass;

            con1 = DriverManager.getConnection(url1, user, password);
            System.out.println("hello1");
            System.out.println(con);
            if (con1 != null) {
                // here the code will be added for specific users
                System.out.println("worked");
            }
            else{
                System.out.println("Wrong Input!!Try Again");
                login();
            }
        }catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }

    }
    
    
    // have to take input for the different values
    public static void addchat(int message_id, String message,int senderid,int recieverid) throws SQLException { 
        PreparedStatement p = con.prepareStatement("Insert Into chats(messageid,message,senttime,seentime,senderid,recieverid) value(?,?,?,?,?,?)");
        p.setInt(1, message_id);
        p.setString(2,message);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        p.setTimestamp(3, timestamp);
        p.setTimestamp(4, null);
        p.setInt(5, senderid);
        p.setInt(6, recieverid);
        p.executeUpdate();
    }

    public static void chattseen(int senderid,int recieverid) throws SQLException {
        PreparedStatement p = con.prepareStatement("Update chats set seentime = ? where senderid = ? and recieverid = ?");
        p.setInt(2, senderid);
        p.setInt(3,recieverid);
        Timestamp seentime = new Timestamp(System.currentTimeMillis());
        p.setTimestamp(1, seentime);
        p.executeUpdate();
    }

    public static void deletechat(int senderid,int recieverid) throws SQLException {
        PreparedStatement p = con.prepareStatement("Update chats set message = 'this message was deleted' where senderid = ? and recieverid = ?");
        p.setInt(1, senderid);
        p.setInt(2,recieverid);
        p.executeUpdate();
    }

    public static void getprofile() throws SQLException {
        PreparedStatement p = con.prepareStatement("Select * form userlist");
        ResultSet rs = p.executeQuery();
        while(rs.next()){
            Integer id = rs.getInt("Userid");
            String phnumber = rs.getString("phonenumber");
            String name = rs.getString("name");
            String about = rs.getString("about");
            // add code for profile photo and display
        }
    }

    public static void getgroupprofile() throws SQLException {
        PreparedStatement p = con.prepareStatement("Select * form grouplist");
        ResultSet rs = p.executeQuery();
        while(rs.next()){
            Integer id = rs.getInt("Userid");
            String phnumber = rs.getString("phonenumber");
            String name = rs.getString("name");
            String about = rs.getString("about");
            // add code for profile photo
        }
    }

    public static void addgroupchat(int message_id, String message,int senderid,int recieverid) throws SQLException { 
        PreparedStatement p = con.prepareStatement("Insert Into chats(groupmessageid,senttime,message,senderid) value(?,?,?,?)");
        p.setInt(1, message_id);
        p.setString(3,message);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        p.setTimestamp(2, timestamp);

        p.setInt(4, senderid);

        p.executeUpdate();
    }



}
