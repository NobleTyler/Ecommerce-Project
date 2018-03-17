package tests;
import model.*;

import java.sql.SQLException;
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
			bb = b.retrieveBookByBid("b00001");			//querying for book by bid
			if (bb == null) {
				result += "ERROR! Book query for bid=b00001 was null!\n";
			}
			else {
				result += "[Query by bid result]: " + bb.getBid() + " " + bb.getTitle() + " " + bb.getPrice() + "\n";
			}
			
			Map<String, BookBean> m = b.retrieveBooksByTitle("Hobbit");		//querying for book by title	
			if (m.size() == 0) {
				result += "ERROR! no book with title Hobbit found!\n";
			}
			else {
				for (Entry<String, BookBean> entry : m.entrySet()) {
					result += "[Query by title result]: " + entry.getKey() + " " + entry.getValue().getTitle() + " " + entry.getValue().getPrice() + "\n";
				}
			}
			
			if (b.retrieveBookByBid("b00002") == null) {		//testing insertion into the database
				BookBean newBook = new BookBean("b00002", "Lord of the Rings", (float) 23.49);
				b.insertBook(newBook);
				
				if (b.retrieveBookByBid("b00002") == null) {
					result += "ERROR! book b00002 was not added to the dbs\n";
				}
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}

	public static void main(String[] args) {
		
		Tests t = new Tests();
		String result = t.testBookDAO();
		
		System.out.println(result);
		
	}
	
}
