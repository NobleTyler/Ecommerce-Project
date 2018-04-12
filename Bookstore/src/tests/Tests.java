package tests;
import model.*;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
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
			if (a.attemptLogin("mcmaceac", "1")) {
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
			ab = new AccountBean("mcmaceac", "1");
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
			b.setUsername("mcmaceac");
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
			//sc01
			scd.addItemToCart("mcmaceac", 7);
			int quantity = scd.getQuantity("mcmaceac", 7);
			if (quantity == 1) {
				result += "[SUCCESS]sc01: shopping cart returning correct quantity\n";
			}
			else {
				result += "[ERROR]sc01: shopping cart returned incorrect quantity(" + quantity + ")\n";
			}
			scd.clearCart("mcmaceac");
			
			//sc02 quantity for username that doesn't exist
			quantity = scd.getQuantity("NON-EXISTANT USERNAME", 1);
			if (quantity == 0) {
				result += "[SUCCESS]sc02: shopping cart returning correct quantity\n";
			}
			else {
				result += "[ERROR]sc02: shopping cart returned incorrect quantity(" + quantity + ")\n";
			}
			
			//sc03 quantity for bid that doesnt exist
			quantity = scd.getQuantity("mcmaceac", 43242414);
			if (quantity == 0) {
				result += "[SUCCESS]sc03: shopping cart returning correct quantity\n";
			}
			else {
				result += "[ERROR]sc03: shopping cart returned incorrect quantity(" + quantity + ")\n";
			}
			
			//sc04 inserting into shoppingcart
			scd.addItemToCart("mcmaceac", 1);		
			quantity = scd.getQuantity("mcmaceac", 1);
			if (quantity == 1) {
				result += "[SUCCESS]sc04: shopping cart returning correct quantity\n";
			}
			else {
				result += "[ERROR]sc04: shopping cart returned incorrect quantity(" + quantity + ")\n";
			}
			scd.removeItemFromCart("mcmaceac", 1);
			
			//sc05 inserting multiple of the same item
			scd.addItemToCart("mcmaceac", 8);
			scd.addItemToCart("mcmaceac", 8);
			scd.addItemToCart("mcmaceac", 8);
			scd.addItemToCart("mcmaceac", 8);
			quantity = scd.getQuantity("mcmaceac", 8);
			if (quantity == 4) {
				result += "[SUCCESS]sc05: shopping cart returning correct quantity\n";
			}
			else {
				result += "[ERROR]sc05: shopping cart returned incorrect quantity(" + quantity + ")\n";
			}
			
			//sc06 testing removing an item from shopping cart
			scd.removeItemFromCart("mcmaceac", 8);
			quantity = scd.getQuantity("mcmaceac", 8);
			if (quantity == 0) {
				result += "[SUCCESS]sc06: shopping cart returning correct quantity, item removed\n";
			}
			else {
				result += "[ERROR]sc06: shopping cart returned incorrect quantity(" + quantity + ")\n";
			}
			
			//sc07 testing removing a certain quantity of an item from shopping cart
			scd.addItemToCart("mcmaceac", 2);
			scd.addItemToCart("mcmaceac", 2);
			scd.addItemToCart("mcmaceac", 2);
			scd.addItemToCart("mcmaceac", 2);
			scd.addItemToCart("mcmaceac", 2);
			scd.removeItemFromCart("mcmaceac", 2, 2);
			quantity = scd.getQuantity("mcmaceac", 2);
			if (quantity == 3) {
				result += "[SUCCESS]sc07: shopping cart returning correct quantity, 2 items removed\n";
			}
			else {
				result += "[ERROR]sc07: shopping cart returned incorrect quantity(" + quantity + ")\n";
			}
			scd.removeItemFromCart("mcmaceac", 2);		//removing entire item so future testing works
			scd.clearCart("mcmaceac");
			
			//sc08 retrieving an entire cart
			scd.addItemToCart("mcmaceac", 1);
			scd.addItemToCart("mcmaceac", 2);
			scd.addItemToCart("mcmaceac", 2);
			scd.addItemToCart("mcmaceac", 8);
			Map<Integer, Integer> cart = scd.retrieveCartItems("mcmaceac");
			if (cart.size() == 3 && cart.get(1) == 1 && cart.get(2) == 2 && cart.get(8) == 1) {
				result += "[SUCCESS]sc08: shopping cart retrieved in map form!\n";
			}
			else {
				result += "[ERROR]sc08: shopping cart was not retrieved!\n";
				result += "cart.get(1): " + cart.get(1) + "\n";
				result += "cart.get(2): " + cart.get(2) + "\n";
				result += "cart.get(8): " + cart.get(8) + "\n";
				result += "cart.size(): " + cart.size() + "\n";
			}
			scd.clearCart("mcmaceac");
			
			//sc09 clearing an entire cart
			scd.addItemToCart("mcmaceac", 1);
			scd.addItemToCart("mcmaceac", 2);
			scd.addItemToCart("mcmaceac", 2);
			scd.addItemToCart("mcmaceac", 8);
			scd.clearCart("mcmaceac");
			if (scd.retrieveCartItems("test").size() == 0) {
				result += "[SUCCESS]sc09: shopping cart cleared successfully!\n";
			}
			else {
				result += "[ERROR]sc09: shopping cart was not cleared!\n";
			}
			
			//sc10 testing cart total price
			scd.addItemToCart("mcmaceac", 1);		
			scd.addItemToCart("mcmaceac", 2);
			scd.addItemToCart("mcmaceac", 2);
			scd.addItemToCart("mcmaceac", 8);
			float price = scd.getCartTotalPrice("mcmaceac");
			if (price == 79.48f) {
				result += "[SUCCESS]sc10: shopping cart total correct!\n";
			}
			else {
				result += "[ERROR]sc10: shopping cart total incorrect: " + price + "\n";
			}
			scd.clearCart("mcmaceac");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String testAddressDAO() {
		AddressDAO a = new AddressDAO();
		String result = "";
		
		try {
			AddressBean ab = new AddressBean("mcmaceac", "Matthew MacEachern", "435 Moon St.", "Aurora", "ON", "L4GM56");
			a.addAddress(ab);
			//ad01 testing retrieve and add address
			if (a.retrieveAddress("mcmaceac") != null) {
				result += "[SUCCESS]ad01: address added!\n";
			}
			else {
				result += "[ERROR]ad01: address not added!\n";
			}
			
			//ad02 testing update and retrieve
			ab = new AddressBean("mcmaceac", "Matthew MacEachern", "35 Moon St.", "Aurora", "ON", "L4G4M5");
			a.updateAddress(ab);
			if (a.retrieveAddress("mcmaceac").getStreet().equals("35 Moon St.")) {
				result += "[SUCCESS]ad02: address updated!\n";
			}
			else {
				result += "[ERROR]ad02: address not updated!\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String testPODAOs() {
		POContainer pc = new POContainer();
		ShoppingCartDAO scd = new ShoppingCartDAO();
		String result = "";
		
		try {
			String username = "mcmaceac";
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			String id = currentTime.toString() + username;
			POBean pb = new POBean(id, "MacEachern", "Matthew", username, POBean.PROCESSED, currentTime);
			
			scd.addItemToCart(username, 1);
			scd.addItemToCart(username, 2);
			scd.addItemToCart(username, 4);
			
			pc.addProductOrder(pb, scd.retrieveCartItems(username));
			
			//po01 adding a cart of size 3 to a product order
			if (pc.getPOItems(id).size() == 3) {
				result += "[SUCCESS]po01: adding a cart of size 3 to a product order\n";
			}
			else {
				result += "[ERROR]po01: adding a cart of size 3 to a product order\n";
			}
			
			//po02 testing retrieving by month
			int marchPO = pc.getPOByMonth(3).size();
			if (marchPO == 0) {
				result += "[SUCCESS]po02: testing retrieving by month (3)\n";
			}
			else {
				result += "[ERROR]po02: testing retrieving by month (3) returned " + marchPO + "\n";
			}
			
			//po02 testing retrieving by month
			int aprilPO = pc.getPOByMonth(4).size();
			if (aprilPO > 0) {
				result += "[SUCCESS]po02: testing retrieving by month (4)\n";
			}
			else {
				result += "[ERROR]po02: testing retrieving by month (4) returned " + aprilPO + "\n";
			}
			
			scd.clearCart(username);
		}
		catch (SQLException e) {
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
		result += t.testAddressDAO();
		result += t.testPODAOs();
		
		System.out.println(result);
		
	}
	
}
