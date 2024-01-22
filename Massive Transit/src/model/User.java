package model;

public class User {
	private String name;
	private String password;
	private int wallet;
	private Ticket[] ticketHistory;
	private Subscription[] subscriptions;
	private User[] relationships;

	public static final int MAX_TICKETS = 50;
	public static final int MAX_SUBSCRIPTIONS = 30;
	public static final int MAX_RELATIONSHIPS = 50;

	public User(String name, String password) {
		this.name = name;
		this.password = password;
		ticketHistory = new Ticket[MAX_TICKETS];
		subscriptions = new Subscription[MAX_SUBSCRIPTIONS];
		relationships = new User[MAX_RELATIONSHIPS];
	}

	public void addTicket(Ticket ticket) {
		for (int i = 0; i < ticketHistory.length; i++) {
			if (ticketHistory[i] == null) {
				ticketHistory[i] = ticket;
				break;
			}
		}
	}

	public void addRelationship(User user) {
		for (int i = 0; i < relationships.length; i++) {
			if (relationships[i] == null) {
				relationships[i] = user;
				break;
			}
		}
	}

	public void addSubscription(Subscription subscription) {
		for (int i = 0; i < this.subscriptions.length; i++) {
			if (this.subscriptions[i] == null) {
				this.subscriptions[i] = subscription;
				break;
			}
		}
	}

	public void deleteRelationship(int relationshipArrayNumber) {
		relationships[relationshipArrayNumber] = null;
	}

	public void deleteSubscription(int subscriptionArrayNumber) {
		subscriptions[subscriptionArrayNumber] = null;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setWallet(int wallet) {
		this.wallet = wallet;
	}

	public void setTicketHistory(Ticket[] ticketHistory) {
		this.ticketHistory = ticketHistory;
	}

	public void setRelationships(User[] relationships) {
		this.relationships = relationships;
	}

	public void setSubscriptions(Subscription[] subscriptions) {
		this.subscriptions = subscriptions;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public int getWallet() {
		return wallet;
	}

	public Ticket[] getTicketHistory() {
		return ticketHistory;
	}

	public User[] getRelationships() {
		return relationships;
	}

	public Subscription[] getSubscriptions() {
		return subscriptions;
	}
}
