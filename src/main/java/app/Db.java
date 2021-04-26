package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Pert;

import java.sql.*;

/**
 *
 * @author Riadh
 */
public class Db {
    private Connection con ;
    private Statement st ;
    private ResultSet rs ;
    public Db(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3305/pert?autoReconnect=true&&useSSL=false","root","1234");
            st = con.createStatement();

        }catch(Exception ex){
            System.out.println("Errer : " + ex);

        }




    }


    public Pert getPert(int id){
        Pert pert = null;

        try {
            System.out.println("records from Db");
            String query = "SELECT * FROM T1 where id="+id;
            rs = st.executeQuery(query);
            while(rs.next()){


                String  name= rs.getString("name");
                String tasks= rs.getString("tasks") ;

               pert = new Pert(id,name,tasks);

            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        System.out.println("records from Db");

        return  pert;



    }

    public ObservableList<Pert> getPert(){
        ObservableList <Pert>observableList = FXCollections.observableArrayList();

        try {
            System.out.println("records from Db");
            String query = "SELECT * FROM T1";
            rs = st.executeQuery(query);
            while(rs.next()){

                   Integer id = rs.getInt("id");
                String  name= rs.getString("name");
                String tasks= rs.getString("tasks") ;

            Pert   pert = new Pert(id,name,tasks);
            observableList.add(pert);

            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        System.out.println("records from Db");

        return  observableList;



    }

    public Pert InsertPert(Pert pert)  {
        PreparedStatement pstmt;
        int id = 0;
        String query = "insert into t1(name,tasks)values(?,?)";
        try {
            pstmt = (PreparedStatement) con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, pert.getName());
            pstmt.setString(2,pert.getTasks());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if(rs != null && rs.next()){
                id= rs.getInt(1);
                pert.setId(id);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }




        return pert;

    }

    public void delete(int id){
        System.out.println("test");
        String sql = "DELETE FROM t1 WHERE id="+id;
        try {

            st.executeUpdate(sql);
        }catch(SQLException e) {
            System.out.println("Error");

        }



    }

}