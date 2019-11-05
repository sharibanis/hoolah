package hoolah;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class TransactionAnalyser {
	static Transaction transaction = null;
	static HashMap<String, Transaction> transactions = null;
	static ArrayList<String> revTransactions = null;
	static String dataFile;
	static Date fromDate;
	static Date toDate;
	static String merchant;
	
	public static void main(String[] args) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
			if (args == null) {
				System.err.println("No arguments: "+ args +"\nExiting.");
				System.exit(1);
			}
			if (args.length != 4) {
				System.err.println("Invalid arguments: "+ args.toString() +"\nExiting.");
				System.exit(1);
			} else {
				dataFile = args[0];
				fromDate = dateFormat.parse(args[1]);
				toDate = dateFormat.parse(args[2]);
				merchant = args[3];
			}
			File file = new File(dataFile);
			Scanner scanner = new Scanner(file);
			String line;
			String[] tokens;
			transactions = new HashMap<String, Transaction>();
			line = scanner.nextLine(); //header
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				tokens = line.split(",");
				transaction = new Transaction();
				transaction.setID(tokens[0].trim());
				transaction.setDate(dateFormat.parse(tokens[1].trim()));
				transaction.setAmount(new BigDecimal(tokens[2].trim()));
				transaction.setMerchant(tokens[3].trim());
				transaction.setType(tokens[4].trim());
				if (tokens.length == 6)
					transaction.setRelatedTransaction(tokens[5].trim());
				transactions.put(tokens[0].trim(), transaction);
			}
			scanner.close();
			revTransactions = new ArrayList<String>();
			Iterator it = transactions.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry)it.next();
				transaction = (Transaction)entry.getValue();
				if (transaction.getType().equalsIgnoreCase("REVERSAL")) {
					it.remove();
					revTransactions.add(transaction.getRelatedTransaction());
				}
			}
			for (String tx : revTransactions) {
				transactions.remove(tx);
			}
			it = transactions.entrySet().iterator();
			int numTransactions = 0;
			BigDecimal averageTransactionValue = new BigDecimal("0.00");
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry)it.next();
				transaction = (Transaction)entry.getValue();
				if (transaction.getMerchant().equalsIgnoreCase(merchant)
					&& transaction.getDate().after(fromDate)
					&& transaction.getDate().before(toDate)) {
					numTransactions++;
					averageTransactionValue = averageTransactionValue.add(transaction.getAmount());
				}
			}
			averageTransactionValue = averageTransactionValue.divide(new BigDecimal(numTransactions), 2, RoundingMode.HALF_EVEN);
			System.out.println("Number of transactions = " + numTransactions);
			System.out.println("Average Transaction Value = " + averageTransactionValue);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
