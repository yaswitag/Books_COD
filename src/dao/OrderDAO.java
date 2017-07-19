package dao;

import beans.BookBean;
import beans.Cart;
import beans.OrderBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class OrderDAO {

    // returns order id on success, -1 on error
    public static int placeOrder(OrderBean order, Cart cart) {

        // place order only when cart is not empty 
        if ( cart == null ||  cart.getBooks().isEmpty())
            return -1;
            
        Connection con = null;
        int orderid = -1;
        try {
            con = OracleDatabase.getConnection();
            con.setAutoCommit(false);
            
            // get orderid from sequence 
            PreparedStatement ps = con.prepareStatement("select orderid_seq.nextval from dual");
            ResultSet rs = ps.executeQuery();
            rs.next();
            orderid = rs.getInt(1);

            // insert into ORDERS 

            ps = con.prepareStatement("insert into orders values(?,?,?,?,?,?,'n')");
            ps.setInt(1,orderid);
            ps.setString(2, order.getCustomer());
            ps.setString(3, order.getAddress());
            ps.setString(4, order.getEmail());
            ps.setString(5, order.getMobile());
            ps.setDouble(6, cart.getTotal());
            ps.executeUpdate();

            // insert into ORDERITEMS 

            ps = con.prepareStatement("insert into orderitems values(?,?,?,?,?)");
            for (BookBean b : cart.getBooks()) {
                ps.setInt(1,orderid);
                ps.setString(2, b.getBookid());
                ps.setInt(3, b.getQty());
                ps.setDouble(4, b.getPrice());
                ps.setDouble(5, b.getDiscount());
                ps.executeUpdate();
            }
            ps.close();
            con.commit();
            rs.close();
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            try {
            	orderid=-1; // indicates unsuccessful transaction 
                con.rollback();
                
            } catch (Exception e) {
            }
        }
        
        return orderid;
    }
    
    public static OrderBean getOrder(String orderid) {
        Connection con = null;
        OrderBean order = null;
        try {
            con = OracleDatabase.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from orders where orderid = ?");
            ps.setString(1, orderid);
            ResultSet rs = ps.executeQuery();
            if ( rs.next()) {
                order = getOrderFromResultSet(rs);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return order;
    }
    
    public static ArrayList<BookBean> getBooks(String orderid) {
        
        try {
            Connection con = OracleDatabase.getConnection();
            PreparedStatement ps = con.prepareStatement
                    ("select bookid, title,author,publisher, oi.price, oi.discount, qty  from orderitems oi join books b using(bookid) where orderid = ?");
            ps.setString(1, orderid);
            ResultSet rs = ps.executeQuery();
            
            ArrayList<BookBean> books  = new ArrayList<BookBean>();
            while ( rs.next()) {
                BookBean book = new BookBean();
                book.setBookid( rs.getString("bookid"));
                book.setAuthor( rs.getString("author"));
                book.setTitle( rs.getString("title"));
                book.setPublisher( rs.getString("publisher"));
                book.setPrice( rs.getDouble("price"));
                book.setDiscount( rs.getDouble("discount"));
                book.setQty( rs.getInt("qty"));
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
    
    
      private static OrderBean getOrderFromResultSet(ResultSet rs) throws Exception {
        OrderBean order = new OrderBean();
        order.setOrderid(rs.getString("orderid"));
        order.setCustomer(rs.getString("customer"));
        order.setAddress(rs.getString("address"));
        order.setEmail(rs.getString("email"));
        order.setMobile(rs.getString("mobile"));
        order.setTotal(rs.getDouble("total"));
        order.setStatus(rs.getString("status"));

        return order;
    }
}
