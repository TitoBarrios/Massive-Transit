package model;

public class User {
	private String name;
	private String password;
	private int wallet;
	private Ticket[] ticketHistory;
	private Subscription[] subscription;

	public static final int MAX_TICKETS = 50;
	public static final int MAX_SUBSCRIPTION = 30;

	public User(String name, String password) {
		this.name = name;
		this.password = password;
		ticketHistory = new Ticket[MAX_TICKETS];
		subscription = new Subscription[MAX_SUBSCRIPTION];
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

	public void setNewSubscription(Subscription subscription) {
		for (int i = 0; i < this.subscription.length; i++) {
			if (this.subscription[i] == null) {
				this.subscription[i] = subscription;
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

	public Subscription[] getSubscription() {
		return subscription;
	}

	public void deleteSubscription(int subscriptionArrayNumber) {
		if (subscriptionArrayNumber < subscription.length) {
			subscription[subscriptionArrayNumber] = null;
		}
	}
}
