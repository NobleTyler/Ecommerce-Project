package tests;
import model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import bean.*;

public class Tests {
	
	public Tests() {
		
	}
	
	public String testBookDAO() {
		
		BookDAO b = new BookDAO();
		BookBean bb = null;
		String result = "";
		
		try {
			bb = b.retrieveBookByBid("b00001");			//b01 querying for book by bid
			if (bb == null) {
				result += "ERROR b01! Book query for bid=b00001 was null!\n";
			}
			else {
				result += "[Query by bid result]: " + bb.getBid() + " " + bb.getTitle() + " " + bb.getPrice() + "\n";
			}
			
			Map<String, BookBean> m = b.retrieveBooksByTitle("Hobbit");		//b02 querying for book by title	
			if (m.size() == 0) {
				result += "ERROR b02! no book with title Hobbit found!\n";
			}
			else {
				for (Entry<String, BookBean> entry : m.entrySet()) {
					result += "[Query by title result]: " + entry.getKey() + " " + entry.getValue().getTitle() + " " + entry.getValue().getPrice() + "\n";
				}
			}
			
			if (b.retrieveBookByBid("b00002") == null) {		//b03 testing insertion into the database
				BookBean newBook = new BookBean("b00002", "Lord of the Rings", (float) 23.49);
				b.insertBook(newBook);
				
				if (b.retrieveBookByBid("b00002") == null) {
					result += "ERROR b03! book b00002 was not added to the dbs\n";
				}
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	public String testCategoryDAO() {
		String result = "";
		CategoryDAO c = new CategoryDAO();
		
		try {
			// c01 test to retrieve all categories
			List<String> cats = c.retrieveAllCategories();
			
			if (cats.isEmpty()) {
				result += "ERROR c01! no categories returned for retrieveAllCategories\n";
			}
			for (String e : cats) {
				result += e + " ";
			}
			result += "\n";
			
			// c02 test to retrieve categories for a single bid
			String bid = "b00002";
			cats = c.retrieveCategoriesForBook(bid);
			if (cats.isEmpty()) {
				result += "ERROR c02! no categories returned for " + bid + "\n";
			}
			for (String e : cats) {
				result += e + " ";
			}
			result += "\n";
			
			// c03 test to retrieve books for a single category
			String categoryName = "fiction";
			List<String> books = c.retrieveBooksForCategory(categoryName);
			if (books.isEmpty()) {
				result += "ERROR c03! no books returned for " + categoryName + "\n";
			}
			for (String e : books) {
				result += e + " ";
			}
			result += "\n";
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public static void main(String[] args) {
		
		Tests t = new Tests();
		String result = t.testBookDAO();
		result += t.testCategoryDAO();
		
		System.out.println(result);
		
	}
	
}
