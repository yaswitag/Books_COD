package beans;

import dao.BooksDAO;
import java.util.ArrayList;
import java.util.Objects;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;


@ManagedBean
@SessionScoped
public class BookBean {
    private String bookid, title,author,publisher;
    private int qty = 1;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }
    private double price,discount;
    private ArrayList<BookBean> books;
    
    public BookBean() {
    }

    public ArrayList<BookBean> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<BookBean> books) {
        this.books = books;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
//        System.out.println(title);
        this.title = title;
    }
    
    public void search(ActionEvent evt) {
        System.out.println("Searching with" + title);
        books = BooksDAO.search(title);
    }
    
    public double getNetPrice() {
        double netprice = price  - (price * discount/100);
        return netprice * qty;
    }

    @Override
    public boolean equals(Object obj) {
        final BookBean other = (BookBean) obj;
        if (!Objects.equals(this.bookid, other.bookid)) {
            return false;
        }
        return true;
    }
    
    public String addToCart() {
        Cart cart = Util.getCart();
        String bookid = Util.getRequest().getParameter("bookid");
        // System.out.println(bookid + " is being added to cart");
        BookBean b  = BooksDAO.getBook(bookid);
        cart.add(b);
        return "cart";
    }
    
}
