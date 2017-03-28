import static org.junit.Assert.*;

import org.junit.Test;

public class BinarySearchTest {

	@Test
	public void testSearch() {
		int[] testArray = {1, 2, 3, 3, 3};
		int x = 3;
		assertEquals("Basic Search Test", 2, BinarySearch.search(testArray, x));
	}

	@Test
	public void testSearchRange() throws Exception {
		int[] testArray = {1, 2, 3, 3, 3};
		int x = 3;
		int[] expected = {2, 4};
		assertArrayEquals("Basic Search Range Test", expected, BinarySearch.searchRange(testArray, x));
	}
}
