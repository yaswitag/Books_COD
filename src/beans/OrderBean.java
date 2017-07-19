package beans;

import dao.OrderDAO;
import java.util.ArrayList;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@ManagedBean
@RequestScoped
public class OrderBean {

    private String orderid, customer, address, email, mobile, status, message;
    private boolean found = false; 

    public boolean getFound() {
    	return found;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    private ArrayList<BookBean> books;
    private double total;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<BookBean> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<BookBean> books) {
        this.books = books;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        switch (status) {
            case "n":
                return "New";
            case "p":
                return "Processed";
            case "d":
                return "Dispatched";
            case "c":
                return "Completed";
            case "f":
                return "Failed";
            default:
                return "Unknown";

        }
    }

    public String confirm() {

        int orderid = OrderDAO.placeOrder(this, Util.getCart());

        if (orderid > 0) {
            this.orderid = String.valueOf(orderid);
            sendMail();
            Util.emptyCart();
            return "ordercompleted";
        } else {
            return "placeorder";  // 
        }
    }

    public void getDetails(ActionEvent evt) {
        OrderBean order = OrderDAO.getOrder(orderid);
        if (order != null) {
            // copy data into current Order Bean
            customer = order.getCustomer();
            address = order.getAddress();
            email = order.getEmail();
            mobile = order.getMobile();
            status = order.getStatus();
            total = order.getTotal();
            books = OrderDAO.getBooks(orderid);
            found = true; 
        }
        else{
        	found = false; 
            message = "Sorry! Order Id not found!";
        }

    }
    
    
     public String getMailBody() {
        Cart cart = Util.getCart();
        StringBuilder body = new StringBuilder("Dear " + customer + ",<p/>");
        body.append("Thank you for shopping at BooksForCOD.Com." + "<p/>");
        body.append(String.format("Your order id : <b>%s</b><p/>", orderid));
        body.append("<h4>Books Ordered</h4><table border='1' width='80%' cellpadding='2px'><tr style='background-color:red;color:white'><th>Book</th><th>Quantity</th><th>You Pay</th></tr>");
        
        for (BookBean book : cart.getBooks()) {
            body.append(String.format("<tr><td>%s</td><td style='text-align:center'>%d</td><td style='text-align:center'>%.2f</td></tr>", book.getTitle(), book.getQty(), book.getNetPrice()));
        }

        body.append(String.format("<tr><td colspan='2' style='text-align:center'>Total</td><td style='text-align:center'><b>%.2f</b></td></tr> </table>", cart.getTotal()));

        body.append("<p/>Yours Sincerely, <br/> <b>Team BooksForCod.Com<b><p/>");

        return body.toString();

    }

    // send mail with order details to the given mail id
    public void sendMail() {

        try {
            Properties props = System.getProperties();
            // Get a Session object
            Session session = Session.getDefaultInstance(props, null);

            // construct the message
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("admin@booksforcod.com"));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            msg.setDataHandler(new DataHandler(getMailBody(), "text/html"));
            msg.setSubject("Order Confirmation");
            // send message
            Transport.send(msg);
        } catch (Exception ex) {
            System.out.println("OrderBean.sendMail() --> " + ex.getMessage());

        }
    }
}
