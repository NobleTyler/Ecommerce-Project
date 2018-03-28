package tests;
import model.*;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
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
			bb = b.retrieveBookByBid(1);			//b01 querying for book by bid
			if (bb == null) {
				result += "[ERROR] b01! Book query for bid=b00001 was null!\n";
			}
			else {
				result += "[SUCCESS]b01: [Query by bid result]: " + bb.getBid() + " " + bb.getTitle() + " " + bb.getPrice() + "\n";
			}
			
			Map<Integer, BookBean> m = b.retrieveBooksByTitle("Hobbit");		//b02 querying for book by title	
			if (m.size() == 0) {
				result += "[ERROR] b02! no book with title Hobbit found!\n";
			}
			else {
				for (Entry<Integer, BookBean> entry : m.entrySet()) {
					result += "[SUCCESS]b02: [Query by title result]: " + entry.getKey() + " " + entry.getValue().getTitle() + " " + entry.getValue().getPrice() + "\n";
				}
			}
			
			if (b.retrieveBookByBid(1) == null) {		//b03 testing insertion into the database
				BookBean newBook = new BookBean(1, "Lord of the Rings", (float) 23.49);
				b.insertBook(newBook);
				
				if (b.retrieveBookByBid(2) == null) {
					result += "[ERROR] b03! book b00002 was not added to the dbs\n";
				}
			}
			else {
				result += "[SUCCESS]b03: Insertion was successful\n";
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
				result += "[ERROR] c01! no categories returned for retrieveAllCategories\n";
			}
			
			result += "[SUCCESS]c01: ";
			for (String e : cats) {
				result += e + " ";
			}
			result += "\n";
			
			// c02 test to retrieve categories for a single bid
			int bid = 2;
			cats = c.retrieveCategoriesForBook(bid);
			if (cats.isEmpty()) {
				result += "[ERROR] c02! no categories returned for " + bid + "\n";
			}
			
			result += "[SUCCESS]c02: ";
			for (String e : cats) {
				result += e + " ";
			}
			result += "\n";
			
			// c03 test to retrieve books for a single category
			String categoryName = "fiction";
			Map<Integer, BookBean> books = c.retrieveBooksForCategory(categoryName);
			if (books.isEmpty()) {
				result += "[ERROR] c03! no books returned for " + categoryName + "\n";
			}
			
			result += "[SUCCESS]c03: ";
			for (Map.Entry<Integer, BookBean> e : books.entrySet()) {
				result += e + " ";
			}
			result += "\n";
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String testAccountDAO() {
		
		AccountDAO a = new AccountDAO();
		String result = "";
		
		try {
			// a01 attempt to register user
			if (!a.registerUser("mcmaceac", "shitpass")) {
				result += "[SUCCESS]a01: Account not registered! (already registered)\n";
			}
			else {
				result += "[ERROR] a01! Account registered when account is already registered!\n";
			}
			
			// a02 attempt to log in (correct password)
			if (a.attemptLogin("mcmaceac", "shitpass")) {
				result += "[SUCCESS]a02: Login successful!\n";
			}
			else {
				result += "[ERROR] a02! Account could not log in!\n";
			}
			
			// a03 attempt to log in (incorrect password)
			if (!a.attemptLogin("mcmaceac", "badpass")) {
				result += "[SUCCESS]a03: Login unsuccessful with wrong pw!\n";
			}
			else {
				result += "[ERROR] a03! Account logged in with wrong pw!\n";
			}
			
			// a04 attempt to login with an AccountBean
			AccountBean ab = new AccountBean("testaccountbean", "blah");
			if (!a.attemptLogin(ab)) {
				result += "[SUCCESS]a04: Login unsuccessful with non existant account (AccountBean)!\n";
			}
			else {
				result += "[ERROR] a04! Account logged in with non existant account (AccountBean)!\n";
			}
			
			//a05 attempt to register account with accountbean
			ab = new AccountBean("xXBook_Slayer420Xx", "ieatbooksforbreakfast");
			if (a.attemptLogin(ab)) {
				result += "[SUCCESS]a05: Account successfully logged in! (AccountBean)\n";
			}
			else {
				result += "[ERROR] a05! Account not logged in!(AccountBean)\n";
			}
			
			//a06 attempt to register account with accountbean
			ab.setPassword("wrongpass");
			if (!a.attemptLogin(ab)) {
				result += "[SUCCESS]a06: Account not logged in with wrong pass! (AccountBean)\n";
			}
			else {
				result += "[ERROR] a06! Account successfully logged in with wrong pass!(AccountBean)\n";
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String testBookReviewDAO() {
		BookReviewDAO brd = new BookReviewDAO();
		String result = "";
		
		String review = "Eyo dragon snugglin up with some gold, pretty dope namsayn";
		float rating = 5;
		int bid = 1;
		String username = "mcmaceac";
		Date reviewDate = new Date(System.currentTimeMillis());
		BookReviewBean b = new BookReviewBean(bid, review, username, rating, reviewDate);
		
		try {
			// br01 addReview(b)
			brd.addReview(b);
			
			if (brd.retrieveBookReviews(bid).isEmpty()) {
				result += "[ERROR] br01: Review not added!\n";
			}
			else {
				result += "[SUCCESS]br01: Review successfully added!\n";
			}
			
			//br02 retrieveBookReview(bid, username)
			if (brd.retrieveBookReview(1, "mcmaceac") != null) {
				result += "[SUCCESS]br02: book review retrieved by bid, username\n";
			}
			else {
				result += "[ERROR] br02: book review not found with bid 1 and username mcmaceac\n";
			}
			
			//br03 adding a new review and then deleting to test review deletion
			b.setReviewText("Yea dragons and stuff yo");
			b.setUsername("xXBook_Slayer420Xx");
			brd.addReview(b);
			brd.removeReview(b.getBid(), b.getUsername());
			if (brd.retrieveBookReviews(bid).size() > 1) {
				result += "[ERROR] br03: failure removing review from the database\n";
			}
			else {
				result += "[SUCCESS]br03: successfully removed review from database\n";
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	public String testShoppingCartDAO() {
		ShoppingCartDAO scd = new ShoppingCartDAO();
		String result = "";
		
		try {
			int quantity = scd.getQuantity("mcmaceac", 7);
			if (quantity == 1) {
				result += "[SUCCESS]sc01: shopping cart returning correct quantity";
			}
			else {
				result += "[ERROR]sc01: shopping cart returned incorrect quantity(" + quantity + ")";
			}
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
		result += t.testAccountDAO();
		result += t.testBookReviewDAO();
		result += t.testShoppingCartDAO();
		
		System.out.println(result);
		
	}
	
}
