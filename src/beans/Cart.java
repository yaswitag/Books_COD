package beans;

import java.util.ArrayList;

public class Cart {

    ArrayList<BookBean> books = new ArrayList<BookBean>();

    public boolean add(BookBean newbook) {
        // if books is already in the cart then add 1 to qty 
        BookBean book = getBook(newbook.getBookid());
        if (book != null) {
            book.setQty(book.getQty() + 1);
        } else {
            books.add(newbook);
        }

        return true;
    }

    public ArrayList<BookBean> getBooks() {
        return books;
    }

    public double getTotal() {
        double total = 0;
        for (BookBean book : books) {
            total += book.getNetPrice();
        }
        return total;
    }

    public boolean remove(String bookid) {
        for (BookBean book : books) {
            if (book.getBookid().equals(bookid)) {
                books.remove(book);
                return true;
            }
        }

        return false;
    }

    public BookBean getBook(String bookid) {
        for (BookBean book : books) {
            if (book.getBookid().equals(bookid)) {
                return book;
            }
        }

        return null;
    }
}
