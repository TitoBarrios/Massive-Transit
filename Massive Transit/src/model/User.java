package model;

public class User {
	public static final int MAX_TICKETS = 50;
	public static final int MAX_SUBSCRIPTIONS = 30;
	public static final int MAX_RELATIONSHIPS = 50;

	private User[] relationships;
	private Subscription[] subscriptions;
	private Ticket[] ticketHistory;
	private String name;
	private String password;
	private int wallet;

	public User(String name, String password) {
		this.name = name;
		this.password = password;
		ticketHistory = new Ticket[MAX_TICKETS];
		subscriptions = new Subscription[MAX_SUBSCRIPTIONS];
		relationships = new User[MAX_RELATIONSHIPS];
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

	public void addTicket(Ticket ticket) {
		for (int i = 0; i < ticketHistory.length; i++) {
			if (ticketHistory[i] == null) {
				ticketHistory[i] = ticket;
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

	public User[] getRelationships() {
		return relationships;
	}

	public void setRelationships(User[] relationships) {
		this.relationships = relationships;
	}

	public Subscription[] getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(Subscription[] subscriptions) {
		this.subscriptions = subscriptions;
	}

	public Ticket[] getTicketHistory() {
		return ticketHistory;
	}

	public void setTicketHistory(Ticket[] ticketHistory) {
		this.ticketHistory = ticketHistory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setWallet(int wallet) {
		this.wallet = wallet;
	}

	public int getWallet() {
		return wallet;
	}
}
