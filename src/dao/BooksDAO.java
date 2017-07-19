package dao;

import beans.BookBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BooksDAO {

    private static BookBean getBookFromResultSet(ResultSet rs) throws Exception {
        BookBean book = new BookBean();
        book.setBookid(rs.getString("bookid"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPublisher(rs.getString("publisher"));
        book.setPrice(rs.getDouble("price"));
        book.setDiscount(rs.getDouble("discount"));

        return book;
    }

    public static ArrayList<BookBean> search(String title) {
        ArrayList<BookBean> books = new ArrayList<BookBean>();
        try {
            Connection con = OracleDatabase.getConnection();
            PreparedStatement ps = con.prepareStatement
            		("select * from books where upper(title) like ?");
            ps.setString(1, '%' + title.toUpperCase() + '%');
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BookBean book = getBookFromResultSet(rs);
                books.add(book);
            }
            rs.close();
            ps.close();
            con.close();
            return books;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    public static BookBean getBook(String bookid) {
        try{
            Connection con = OracleDatabase.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from books where bookid = ?");
            ps.setString(1,bookid);
            ResultSet rs = ps.executeQuery();
            BookBean book = null;
            if(rs.next())
                book = getBookFromResultSet(rs);
            
            rs.close();
            ps.close();
            con.close();
            return book;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
