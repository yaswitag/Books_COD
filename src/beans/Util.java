package beans;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Util {

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static Cart getCart() {
		HttpSession session = getSession();
		Cart c = (Cart) session.getAttribute("cart");
		if (c == null) {
			c = new Cart();
			session.setAttribute("cart", c);
		}
		return c;
	}

	public static void emptyCart() {
		HttpSession session = getSession();
		session.removeAttribute("cart");
	}
}
