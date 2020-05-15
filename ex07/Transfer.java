
// COMPLETE
//
// "Transfer" threads select a random amount of money in [0, max_amount]
// and transfer it to a random account in the bank.
//

public class Transfer {
	public Transfer(Bank bank, int from, int max_amount) {
		bank_ = bank;
		from_ = from;
		max_ = max_amount;
	}

	// COMPLETE

	private Bank bank_;
	private int from_;
	private int max_;
}