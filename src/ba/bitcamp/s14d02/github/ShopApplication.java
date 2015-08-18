package ba.bitcamp.s14d02.github;

import java.math.BigDecimal;
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
		// radimo "enhancement" svih klasa u navedenom package-u
		AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent",
				"debug=1;packages=ba.bitcamp.s14d02.github.models.**");
	}

	private static EbeanServer server = Ebean.getServer("h2");

	public static void main(String[] args) {

		System.out.println("Press 1 if you want to create product.");
		System.out.println("Press 2 if you want to create user.");
		System.out.println("Press 3 if you want to create purchase.");

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

	/**
	 * "createProduct" method creates a new product. User is asked to insert product title, price and quantity.
	 * @return
	 */
	private static Product createProduct() {
		// kreiranje novog zapisa o proizvodu
		Product monitor = new Product();
		Scanner in = new Scanner(System.in);
		System.out.println("Enter product title");
		String title = in.nextLine();
		System.out.println("Enter price");
		String price = in.nextLine();
		System.out.println("Enter quantity");
		Integer quantity = in.nextInt();
		monitor.setTitle(title);
		monitor.setPrice(new BigDecimal(price));
		monitor.setQuantity(quantity);
		in.close();
		Ebean.save(monitor);
		return monitor;
	}

	/**
	 * Method creates a new user. User is asked to insert email address, full name and balance.
	 * @return
	 */
	private static User createUser() {
		// kreiranje novog zapisa o korisniku u bazu
		User first = new User();
		Scanner in = new Scanner(System.in);
		System.out.println("Enter email: ");
		String email = in.nextLine();
		System.out.println("Enter full name: ");
		String name = in.nextLine();
		System.out.println("Enter balance: ");
		String balance = in.nextLine();
		first.setFullName(name);
		first.setEmail(email);
		first.setBalance(new BigDecimal(balance));
		in.close();
		Ebean.save(first);
		return first;
	}
}
