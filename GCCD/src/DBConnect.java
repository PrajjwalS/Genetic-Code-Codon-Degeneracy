import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnect {
    static Connection connection = null;
    public static Connection connect(){

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:/home/saurabh/IdeaProjects/Genetic-Code-Codon-Degeneracy/GCCD/codon.db");


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static void addEntry(String genName,String genSeq,Long genSeqLen,String aaSeq,Long aaSeqLen,String remarks){
        PreparedStatement ps = null;
        try{
            String insertQuery = "INSERT INTO GeneInfo(GEN_NAME,GEN_SEQ,GEN_SEQ_LEN,AA_SEQ,AA_SEQ_LEN,REMARKS)" +
                    "VALUES(?,?,?,?,?,?)";
            ps = connection.prepareStatement(insertQuery);
            ps.setString(1,genName);
            ps.setString(2,genSeq);
            ps.setLong(3,genSeqLen);
            ps.setString(4,aaSeq);
            ps.setLong(5,aaSeqLen);
            ps.setString(6,remarks);
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
/*


















    Connection connection = null;
                        try{
                                String path = "/home/saurabh/IdeaProjects/Genetic-Code-Codon-Degeneracy/GCCD/gene.db";
                                connection = DriverManager.getConnection("jdbc:sqlite:"+path);
                                Statement statement = connection.createStatement();
                                statement.execute("CREATE TABLE IF NOT EXISTS Gene"+
                                "(GEN_NAME VARCHAR, GEN_SEQ VARCHAR, GEN_SEQ_LEN INTEGER(10),AA_SEQ VARCHAR, AA_SEQ_LEN INTEGER(10), REMARKS VARCHAR)"
                                );

                                String insert = "INSERT INTO Gene(GEN_NAME,GEN_SEQ,GEN_SEQ_LEN,AA_SEQ,AA_SEQ_LEN,REMARKS)" +
                                "VALUES(?,?,?,?,?,?)";
                                PreparedStatement pSt = connection.prepareStatement(insert);
                                pSt.setString(1,record.geneName);
                                pSt.setString(2,record.geneSeq);
                                pSt.setLong(3,record.geneSeqLength);
                                pSt.setString(4,record.AASeq);
                                pSt.setLong(5,record.AASeqLength);
                                pSt.setString(6,record.remark);


                                }catch (Exception e){
                                e.printStackTrace();
                                }
                                */