//  Import file handling classes
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

/**
 * class HerbertLog
 * The HerbertLog class records the jobs worked by Herbert, and
 * the wages paid to Herbert, over the last period of employment.
 * The constructor opens the specified log-file, and the get(.) method
 * returns records from the file.
 *
 */
public class HerbertLog {

	/**
	 *  Public static final constants
	 */

	// Separator character used in the database file
	public static final String SEP = ":";
	// Length of each record in the database
	public static final int rLength = 18;
	// Padding character for database file
	public static final char PADDING = '.';

	/**
	 * Private state for the HerbertLog
	 */
	// Filename where the database can be found
	private String m_name = null;
	// Variable that points to the database, once opened
	private File m_file = null;
	// Variable for reading from the database file
	private RandomAccessFile m_inRAM = null;
	// Size of the database: number of available records
	private long m_numMinutes = 0;

	/**
	 * Debugging information
	 */
	// Number of "get" operations performed on the database
	// Note this is primarily for debugging.
	protected long m_numGets = 0;

	/**
	 * Constructor
	 * @param fileName File where the database can be found.
	 * The specified file must exist, and must contain records
	 * in the proper format.
	 **/
	HerbertLog(String fileName){
		// Save the filename
		m_name = fileName;
		// Next, we open the file
		try {
			// Open the file
			m_file = new File(m_name);
			m_inRAM = new RandomAccessFile(m_file, "r");

			// Calculate the number of records in the database by
			// dividing the number of characters by the length of each record
			long numChars = m_inRAM.length();
			m_numMinutes = numChars/rLength;
		} catch (IOException e) {
			System.out.println("Error opening file: " + e);
		}
	}

	/**
	 * size
	 * @return the number of records in the database
	 */
	public long numMinutes(){
		return m_numMinutes;
	}

	/**
	 * numGets : primarily for debugging
	 * @return number of times get has been called
	 */
	public long numGets(){
		return m_numGets;
	}

	/**
	 * get
	 * @param i specified the record number to retrieve, starting from 0
	 * @return the specified record, if it exists, or null otherwise
	 */
	public Record get(long i){

		// Increment the number of "get" operations
		m_numGets++;

		// Check for errors: if i is too large or too small, fail
		if (i >= numMinutes()) return null;
		if (i < 0) return null;

		// Retrieve the proper record
		try {
			// First, calculate the offset into the file, and seek to that location
			long numChars = i*rLength;
			m_inRAM.seek(numChars);

			// Next, read in rLength bytes
			// Recall that rLength is the length of one record
			byte[] entry = new byte[rLength];
			m_inRAM.read(entry);

			// Now, convert the string to a record.
			// Convert it to a string...
			String line = new String(entry);
			// .. parse the string using the record separator
			String[] tokens = line.split(SEP);
			// Every record should have 2 or 3 components
			assert(tokens.length==2 || tokens.length==3);
			// The first token is the name
			String name = tokens[0];
			// The second token is the height
			long height = Integer.parseInt(tokens[1]);
			return new Record(name, height);

		} catch (IOException e) {
			System.out.println("Error getting data from file: " + e);
		}
		// If the record wasn't found, for any reason, return null
		return null;
	}

	private TreeMap<Long, Record> tm = new TreeMap<>();
	private ArrayList<Long> starts = new ArrayList<>();

	private Record newGet(long time) {
		if (tm.containsKey(time)) {
			return tm.get(time);
		} else {
			Record rec = get(time);
			tm.put(time, rec);
			return rec;
		}
	}

	private boolean isLast(long time, Record rec) {
		if (time == numMinutes() - 1) {return true;}
		else {
			Record rec2 = newGet(time + 1);
			return !rec2.getName().equals(rec.getName());
		}
	}

	private long salaryHelper(long left, long right, Record rLeft, Record rRight) {
		if (rLeft.getName().equals(rRight.getName())) {
			if (isLast(right, rRight)) {
				starts.add(right + 1);
				return rRight.getWages();
			} else {
				return 0;
			}
		} else {
			long middleL = (left + right) / 2;
			long middleR = middleL + 1;
			Record rmR = newGet(middleR);
			return rLeft.getName().equals(rmR.getName()) ?
					salaryHelper(middleR, right, rmR, rRight) :
					salaryHelper(left, middleL, rLeft, newGet(middleL))
					+ salaryHelper(middleR, right, rmR, rRight);

		}
	}

	public long calculateSalary() {
		long time = numMinutes() - 1;
		long ans = salaryHelper(0, time, newGet(0), newGet(time));
		System.out.println("calculateSalary() numGets: " + numGets());
		return ans;
	}

	public long calculateSalary(int i) {
		long time = numMinutes() - 1;
		long ans = salaryHelper(0, time, newGet(0), newGet(time));
		return ans;
	}

	public long calculateMinimumWork(long goalIncome) {
		starts.clear();
		if (calculateSalary(0) < goalIncome) return -1;
		starts.add((long) 0);
		starts.remove(numMinutes());
		long current = 0;
		long minReq = 0;
		while (current < goalIncome) {
			current = 0;
			for (int i = 0; i < starts.size(); i++) {
				long time = starts.get(i);
				Record job = newGet(time);
				current += job.getWages();
				if (!isLast(time, job)) starts.set(i, time + 1);
			}
			minReq++;
		}
		System.out.println("calculateMinimumWork() numGets: " + numGets());
		return minReq;
	}

	public static void main(String[] args){
	}
}
