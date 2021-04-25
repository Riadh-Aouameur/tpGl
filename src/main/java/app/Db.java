package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Pert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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

}