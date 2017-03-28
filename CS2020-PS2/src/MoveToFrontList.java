/**
 * MoveToFrontList
 * Description: 
 * CS2020 2012
 * 
 * You need to create a constructor and implement search.
 */


/**
 * 
 */
import java.util.*;

public class MoveToFrontList extends FixedLengthList {

    public MoveToFrontList(int length) {
        super(length);
    }

    public boolean search(int x) {
        for (int i = 0; i <= m_max; i++) {
            if (m_list[i] == x) {
                for (int j = i; j > 0; j--) {
                    m_list[j] = m_list[j - 1];
                }
                m_list[0] = x;
                return true;
            }
        }
        return false;
    }
}
