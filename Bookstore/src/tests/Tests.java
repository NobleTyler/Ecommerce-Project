package tests;
import model.*;

import java.sql.SQLException;

import bean.*;

public class Tests {
	
	public Tests() {
		
	}
	
	public String testBookDAO() {
		
		BookDAO b = new BookDAO();
		BookBean bb = null;
		String result = "";
		
		try {
			bb = b.retrieveBook("b00001");			//querying for book by bid
			BookBean newBook = new BookBean("b00002", "Lord of the Rings", (float) 23.49);
			b.insertBook(newBook);
			
			if (b.retrieveBook("b00002") == null) {
				result += "ERROR! book b00002 was not added to the table";
			}
			else {
				result += "Book b00002 successfully added to the database";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (bb == null) {
			result += "ERROR! Book query for bid=b00001 was null!";
		}
		else {
			result += bb.getBid() + " " + bb.getTitle() + " " + bb.getPrice();
		}
		
		return result;
		
	}

	public static void main(String[] args) {
		
		Tests t = new Tests();
		String result = t.testBookDAO();
		
		System.out.println(result);
		
	}
	
}
