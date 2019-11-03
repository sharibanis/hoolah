package hoolah;

import org.junit.Test;
import hoolah.TransactionAnalyser;

public class TransactionAnalyserTest {
	TransactionAnalyser ta = new TransactionAnalyser();
	static String dataFile;
	static String fromDate;
	static String toDate;
	static String merchant;
	
	@Test
	public void testMain() {
		dataFile = "C:\\Users\\shari\\eclipse-workspace\\hoolah\\src\\main\\resources\\data.csv";
		fromDate = "20/08/2018 12:00:00";
		toDate = "20/08/2018 13:00:00";
		merchant = "Kwik-E-Mart";
		String[] args = {dataFile, fromDate, toDate, merchant};
		TransactionAnalyser.main(args);
	}
}
