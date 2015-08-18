package ba.bitcamp.s14d02.github;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import org.avaje.agentloader.AgentLoader;

import ba.bitcamp.s14d02.github.models.Product;
import ba.bitcamp.s14d02.github.models.Purchase;
import ba.bitcamp.s14d02.github.models.User;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;

public class ShopApplication {

	// statički blok se izvršava prilikom učitavanja klase u memoriju, dakle
	// prije pokretanja programa
	static {
		System.out.println("foo");
		// radimo "enhancement" svih klasa u navedenom package-u
		AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent",
				"debug=1;packages=ba.bitcamp.s14d02.github.models.**");
		System.out.println("foobaae");
	}

	private static EbeanServer server = Ebean.getServer("h2");

	public static void main(String[] args) {

		
		System.out.println("Press 1 if you want to create product.");
		System.out.println("Press 2 if you want to create user.");
		System.out.println("Press 3 if you want to create purchase.");
		System.out.println("Press 4 if you want to see all users.");

		Scanner input = new Scanner(System.in);

		int option = input.nextInt();
		if (option == 1) {
			Product monitor = createProduct();
		} else if (option == 2) {
			User first = createUser();
		} else if (option == 3) {

			//TODO implementiranje proizvodas
			// Purchase firstUserPurchasedMonitor = CreatePurchase(first,
			// monitor);
		}  
		
		getAllUsers();

		input.close();

		
	}

	private static Purchase createPurchase(User first, Product monitor) {
		/* Nova kupovina: ubaciti u transakciju */
		Purchase firstUserPurchasedMonitor = new Purchase();
		firstUserPurchasedMonitor.setUser(first);
		firstUserPurchasedMonitor.setProduct(monitor);

		first.setBalance(first.getBalance().subtract(monitor.getPrice()));
		monitor.setQuantity(monitor.getQuantity() - 1);

		Ebean.save(firstUserPurchasedMonitor);
		return firstUserPurchasedMonitor;
	}

	private static Product createProduct() {
		// kreiranje novog zapisa o proizvodu
		Product monitor = new Product();
		monitor.setTitle("Monitor, Dell 28\"");
		monitor.setPrice(new BigDecimal("399.99"));
		monitor.setQuantity(0);

		Ebean.save(monitor);
		return monitor;
	}

	private static User createUser() {
		// kreiranje novog zapisa o korisniku u bazu
		User first = new User();
		first.setFullName("Mujo Mujčinović");
		first.setEmail("mujo.mujcinovic@bitcamp.ba");
		first.setBalance(new BigDecimal(0));

		Ebean.save(first);
		return first;
	}
	
	/**
	 * Prints all users from table user.
	 */
	private static void getAllUsers() {
		List<User> users = Ebean.find(User.class).findList();
		 
		for (User u : users) {
			System.out.println(u);			
		}
	}

}
