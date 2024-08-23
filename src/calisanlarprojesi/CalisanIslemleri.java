package calisanlarprojesi;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



public class CalisanIslemleri {
    private Connection con = null;
    private PreparedStatement preparedStatement = null;
    private Statement statement = null;
    
    public void calisanSil(int id){
        String sorgu = "Delete From calisanlar where id = ?";
        
        try {
            
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void calisanGuncelle(int id, String ad, String soyad, String departman, String maas){
        String sorgu = "Update calisanlar set ad = ?, soyad = ?, departman = ?, maas = ? where id = ?";
        
        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, departman);
            preparedStatement.setString(4, maas);
            preparedStatement.setInt(5, id);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
            
           
        
    }
    
    public void calisanEkle(String ad, String soyad, String departman, String maas){
        String sorgu = "Insert Into calisanlar (ad,soyad,departman,maas) VALUES(?,?,?,?)";
        try {
            preparedStatement = con.prepareStatement(sorgu);
            
            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, departman);
            preparedStatement.setString(4, maas);
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }

}
    
    public ArrayList<Calisan> calisanlariGetir(){
        ArrayList<Calisan> cikti = new ArrayList<Calisan>();
        try {
            statement = con.createStatement();
            String sorgu = "Select * From calisanlar";
            ResultSet rs = statement.executeQuery(sorgu);
            
            while(rs.next()){
                int id = rs.getInt("id");
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String departman = rs.getString("departman");
                String maas = rs.getString("maas");
                
                cikti.add(new Calisan(id,ad,soyad,departman,maas));
            }
            return cikti;
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean girisYap(String kullanici_adi, String parola){
        String sorgu = "Select * From adminler where username = ? and password = ?";
        
        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setString(1, kullanici_adi);
            preparedStatement.setString(2, parola);
            
            ResultSet rs = preparedStatement.executeQuery();
             return rs.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public CalisanIslemleri(){
        String url = "jdbc:mysql://" + Database.host + ":" + Database.port + "/" + Database.db_ismi +"?useUnicode=true&characterEncoding=utf8";

        try {
        // MySQL JDBC Driver'ını yükleyin
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Bağlantıyı kurun
        con = DriverManager.getConnection(url, Database.kullanici_adi, Database.parola);
        System.out.println("Bağlantı başarılı!");

        } catch (ClassNotFoundException ex) {
            System.err.println("Driver bulunamadı!");
        } catch (SQLException ex) {
            System.err.println("Bağlantı başarısız: " + ex.getMessage());
        }
    }

}
