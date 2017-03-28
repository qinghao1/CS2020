import org.junit.Test;

import static org.junit.Assert.*;

public class MyFastListTest {
    /**
     * @param length - length of the list to create
     * @return a MoveToFrontList object to test
     */
    MyFastList getMTFList(int length) {
        return new MyFastList(length);
    }

    /**
     * Test add method with simple example
     */
    @Test
    public void testAdd()
    {
        MyFastList testObj = getMTFList(3);
        testObj.add(10);
        assertEquals("AddTest", "10", testObj.toString());
        testObj.add(12);
        assertEquals("AddTest", "10 12", testObj.toString());
    }

    /**
     * Test search method with an item not in the list
     */
    @Test
    public void searchTest1()
    {
        MyFastList testList = getMTFList(5);
        testList.add(0);
        testList.add(100);
        testList.add(1000);
        testList.add(13);
        testList.add(4);

        boolean result = testList.search(2500);

        String expected = "0 4 13 100 1000";
        assertEquals("SearchTest1", false, result);
        assertEquals("SearchTest1", expected, testList.toString());
    }

    /**
     * Test search method with an item in the list
     */
    @Test
    public void searchTest2()
    {
        MyFastList testList = getMTFList(5);
        testList.add(3);
        testList.add(15);
        testList.add(25);
        testList.add(1000);
        testList.add(1500);

        boolean result = testList.search(1500);
        String expected1 = "3 15 25 1000 1500";
        assertEquals("SearchTest2", true, result);
        assertEquals("SearchTest2", expected1, testList.toString());
    }

    /**
     * Test errors
     */
    @Test
    public void errorTest1()
    {
        try
        {
            MyFastList testList = getMTFList(1);
            testList.add(0);
            testList.add(1);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            assertTrue(false);
        }
        catch(NullPointerException e)
        {
            assertTrue(false);
        }
        catch(Exception e)
        {
            // Ok if they throw a specific type of exception
        }
    }
}