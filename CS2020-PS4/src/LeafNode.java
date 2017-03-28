/**
 * This is a node class that is designed specifically to be a leaf of the tree.
 * It cannot have any children and it is used to hold the object name. 
 * It has one additional field m_object that stores the object.
 * 
 * @author gilbert
 *
 * @param <KType> the type of leaf value
 */
public class LeafNode<KType> extends TreeNode<KType>{
	
	// The question object being stored in this node
	QuestionObject m_object = null;

	/**
	 * Constructor
	 * 
	 * @param value name of object
	 * @param object the QuestionObject itself
	 */
	public LeafNode(KType value, QuestionObject object){
		super();
		m_value = value;
		m_object = object;
	}
	
	/**
	 * This method prevents a child from being added to a Leaf Node
	 */
	@Override
	public void setLeft(TreeNode<KType> child){
		throw new UnsupportedOperationException("Cannot add a child to a leaf.");
	}

	/**
	 * This method prevents a child from being added to a Leaf Node
	 */
	@Override
	public void setRight(TreeNode<KType> child){
		throw new UnsupportedOperationException("Cannot add a child to a leaf.");
	}
	
	/**
	 * This method checks if the node is a leaf node.
	 * It should always return true but we perform some additional error
	 * checking, just in case.
	 */
	@Override
	public boolean isLeaf(){
		if (getLeft() != null){
			throw new IllegalStateException("Leaf is not a leaf.");
		}
		if (getRight() != null){
			throw new IllegalStateException("Leaf is not a leaf.");
		}
		return true;
	}

	/**
	 * This method returns the QuestionObject associated with this leaf.
	 * 
	 * @return QuestionObject associated with this leaf.
	 */
	public QuestionObject getObject(){
		return m_object;
	}
	
}
