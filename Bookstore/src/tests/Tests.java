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
			bb = b.retrieveBook("b00001");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (bb == null) {
			result += "Book query for bid=b00001 was null!";
		}
		else {
			result = bb.getBid() + " " + bb.getTitle() + " " + bb.getPrice();
		}
		
		return result;
		
	}

	public static void main(String[] args) {
		
		Tests t = new Tests();
		String result = t.testBookDAO();
		
		System.out.println(result);
		
	}
	
}
