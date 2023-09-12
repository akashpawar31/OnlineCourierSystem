package Online_AttendeanceManagmentSystem;

import java.util.*;
import java.util.stream.Collectors;

class User {
    private String username;
    private String password;
    private boolean isAdmin;

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public int hashCode() {
		return Objects.hash(isAdmin, password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return isAdmin == other.isAdmin && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}

    

   
}

class Product {
    private int productId;
    private String name;
    private double price;

    public Product(int productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, price, productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(name, other.name)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& productId == other.productId;
	}

    

    
}

class Order {
    private int orderId;
    private User user;
    private List<Product> products;
    private boolean isDelivered;

    public Order(int orderId, User user, List<Product> products) {
        this.orderId = orderId;
        this.user = user;
        this.products = products;
        this.isDelivered = false;
    }

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public boolean isDelivered() {
		return isDelivered;
	}

	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}

    
}

class CourierService {
    private List<User> users = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private int orderIdCounter = 1;
    private User currentUser;

    public void initializeData() {
       
    	users.add(new User("admin", "admin123", true));
        users.add(new User("user1", "password123", false));
        
        products.add(new Product(1, "Product A", 10.99));
        products.add(new Product(2, "Product B", 15.99));
        products.add(new Product(3, "Product C", 20.99));
    }

    public boolean login(String username, String password) {
       
    	User userToLogin = users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (userToLogin != null) {
            currentUser = userToLogin;
            return true;
        } else {
            return false;
        }
    }

    public void placeOrder(List<Product> products) {
    	if (currentUser != null && !products.isEmpty()) {
            Order order = new Order(orderIdCounter++, currentUser, products);
            orders.add(order);
            System.out.println("Order placed successfully!");
        } else {
            System.out.println("Order placement failed.");
        }
    }

    public void viewOrders() {
    	 if (currentUser != null) {
             List<Order> userOrders = orders.stream()
                     .filter(order -> order.getUser().getUsername().equals(currentUser.getUsername()))
                     .collect(Collectors.toList());

             if (!userOrders.isEmpty()) {
                 System.out.println("Your Orders:");
                 for (Order order : userOrders) {
                     System.out.println("Order ID: " + order.getOrderId() + ", Status: " +
                             (order.isDelivered() ? "Delivered" : "Pending"));
                 }
             } else {
                 System.out.println("You have no orders yet.");
             
         }
    	 }
    }
    	    public void addProduct(Product product) {
    	        products.add(product);
    	    }

    	    public void addUser(User user) {
    	        users.add(user);
    	    }

    	    public void markOrderAsDelivered(int orderId) {
    	        Order orderToMark = orders.stream()
    	                .filter(order -> order.getOrderId() == orderId)
    	                .findFirst()
    	                .orElse(null);

    	        if (orderToMark != null) {
    	            orderToMark.isDelivered();
    	            System.out.println("Order marked as delivered.");
    	        } else {
    	            System.out.println("Order not found.");
    	        }
    	    }
    	
    	






  
}

public class OnlineCourierSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourierService courierService = new CourierService();
        courierService.initializeData();

        System.out.println("Welcome to the Online Courier System!");
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Login");
            System.out.println("2. Place an Order");
            System.out.println("3. View Orders");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter your username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();
                    if (courierService.login(username, password)) {
                        System.out.println("Login successful!");
                    } else {
                        System.out.println("Login failed. Please check your credentials.");
                    }
                    break;

                case 2:
                    if (courierService.login(null, null)) {
                        
                    	
                    } else {
                        System.out.println("Please log in to place an order.");
                    }
                    break;

                case 3:
                    if (courierService.login(null, null)) {
                       
                        courierService.viewOrders();
                    } else {
                        System.out.println("Please log in to view orders.");
                    }
                    break;

                case 4:
                    System.out.println("Thank you for using the Online Courier System. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }
}
