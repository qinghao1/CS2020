
public class MyFastList extends FixedLengthList {
    public MyFastList(int length) {
        super(length);
    }

    public boolean search(int x) {
        for (int i = 0; i <= m_max; i++) {
            if (m_list[i] == x) {
                m_list[i] = m_list[0];
                m_list[0] = x;
                return true;
            }
        }
        return false;
    }
}
