package beans;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@SessionScoped
public class CartBean {

	public ArrayList<BookBean> getBooks() {
		Cart c = Util.getCart();
		return c.getBooks();
	}

	public void deleteBook(ActionEvent evt) {
		Cart cart = Util.getCart();
		String bookid = Util.getRequest().getParameter("bookid");
		System.out.println(bookid + " is being removed from cart");
		cart.remove(bookid);

	}

	public double getTotal() {
		Cart cart = Util.getCart();
		return cart.getTotal();
	}

}
