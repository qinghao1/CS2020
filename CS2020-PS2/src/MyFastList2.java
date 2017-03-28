
public class MyFastList2 extends FixedLengthList {
    public MyFastList2(int length) {
        super(length);
    }

    public boolean add(int x) {
        m_max++;
        if (m_max < m_length) {
            for (int i = 0; i <= m_max; i++) {
                if (m_list[i] > x) {
                    for (int j = m_max; j > i; j--) {
                        m_list[j] = m_list[j - 1];
                    }
                    m_list[i] = x;
                    break;
                } else {
                    m_list[m_max] = x;
                }
            }
            return true;
        } else {
            System.out.println("Error: list length exceeded.");
            return false;
        }
    }

    private boolean searchHelper(int x, int left, int right) {
        if (left > right) {
            return false;
        } else {
            int middle = (left + right) / 2;
            if (m_list[middle] == x) {
                return true;
            } else if (m_list[middle] < x) {
                return searchHelper(x, middle + 1, right);
            } else {
                return searchHelper(x, left, middle - 1);
            }
        }
    }

    public boolean search(int x) {
        return searchHelper(x, 0, m_max);
    }
}
