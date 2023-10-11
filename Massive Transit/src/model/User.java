package model;

public class User {
	private String name;
	private String password;
	private int wallet;
	private Ticket[] ticketHistory;
	private Subscription[] subscriptions;
	private User[][] relationships;

	public static final int MAX_TICKETS = 50;
	public static final int MAX_SUBSCRIPTION = 30;
	public static final int MAX_RELATIONSHIPSY = 2;

	public User(String name, String password) {
		this.name = name;
		this.password = password;
		ticketHistory = new Ticket[MAX_TICKETS];
		subscriptions = new Subscription[MAX_SUBSCRIPTION];
		setRelationships(new User[MAX_RELATIONSHIPSY][]);
	}

	public void setWallet(int wallet) {
		this.wallet = wallet;
	}

	public void setTicket(Ticket ticket) {
		for (int i = 0; i < ticketHistory.length; i++) {
			if (ticketHistory[i] == null) {
				ticketHistory[i] = ticket;
				break;
			}
		}
	}

	public void setRelationships(User[][] relationships) {
		this.relationships = relationships;
	}

	public void setNewSubscription(Subscription subscription) {
		for (int i = 0; i < this.subscriptions.length; i++) {
			if (this.subscriptions[i] == null) {
				this.subscriptions[i] = subscription;
				break;
			}
		}
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

	public User[][] getRelationships() {
		return relationships;
	}

	public Subscription[] getSubscriptions() {
		return subscriptions;
	}

	public void deleteSubscription(int subscriptionArrayNumber) {
		if (subscriptionArrayNumber < subscriptions.length) {
			subscriptions[subscriptionArrayNumber] = null;
		}
	}
}
