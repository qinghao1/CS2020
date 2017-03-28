import java.util.*;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

/**
 * This class extends QuestionTreeBase for students to implement the buildTree
 * and findQuery methods.
 * 
 * @author gilbert
 */
public class QuestionTree extends QuestionTreeBase {

	/**
	 * This method builds the tree of objects.
	 * 
	 * @param objects an array of QuestionObjects
	 */
	@Override
	public void buildTree(ArrayList<QuestionObject> objects){
		if (objects.size() == 0) throw new IllegalArgumentException("Empty object list provided");
		//Sort objects
		Collections.sort(objects);
		//Initialize tree with first property
		QuestionObject firstObject = objects.get(0);
		Iterator<String> dummyIterator = firstObject.propertyIterator();
		m_root = new TreeNode<String>(dummyIterator.next());
		//Build tree iteratively
		for (QuestionObject object : objects) {
			buildTreeHelper(object);
		}
	}

	/**
	 * Helper method to build tree iteratively with each element.
	 * How this works:
	 * - Starts from root of tree and checks if it has the property, and goes left or right accordingly.
	 * - Continues to go down the graph, checking at every node.
	 * - If it creates a path (there is no existing node), then it creates a node with one of its properties
	 * - Once the element runs out of properties to go down the tree, it checks to see if it is at a node or a leaf.
	 * - If it is at a leaf (no existing node), it makes its leaf.
	 * - If it is at a node, then it definitely does not have that node's property because it has no more properties.
	 * - So it keeps going left until it reaches a leaf.
	 * @param object the object to create the tree with
	 */
	private void buildTreeHelper(QuestionObject object) {
		//Initialize variables
		Iterator<String> propertyIterator = object.propertyIterator();
		TreeNode<String> currNode = m_root;
		TreeNode<String> prevNode = null;
		//Arraylist needed so that properties can be removed when encountered
		ArrayList<String> allProperties = new ArrayList<>();
		while (propertyIterator.hasNext()) {
			allProperties.add(propertyIterator.next());
		}
		//boolean value to check if it needs to create left edge or right edge
		boolean isLeftChild = false;
		//Go down tree, building nodes as needed
		while (!allProperties.isEmpty()) {
			String currProperty = allProperties.get(0);
			if (currNode == null) {
				//Build new node with property
				currNode = new TreeNode<>(currProperty);
				currNode.setParent(prevNode);
				if (prevNode != null) {
					if (isLeftChild) {
						prevNode.setLeft(currNode);
					} else {
						prevNode.setRight(currNode);
					}
				}
				//Go down right edge
				allProperties.remove(currProperty);
				prevNode = currNode;
				currNode = currNode.getRight();
				isLeftChild = false;
			} else {
				if (object.containsProperty(currNode.getValue())) {
					//Go down right edge
					allProperties.remove(currNode.getValue());
					prevNode = currNode;
					currNode = currNode.getRight();
					isLeftChild = false;
				} else {
					//Go down left edge
					prevNode = currNode;
					currNode = currNode.getLeft();
					isLeftChild = true;
				}
			}
		}
		//Then, build the leaf node
		LeafNode<String> leaf = new LeafNode<>(object.getName(), object);
		//Checks if it is at an empty node, if not keep moving left until it reaches empty node
		if (prevNode.getRight() != null) {
			TreeNode<String> tempNode = prevNode.getRight();
			while (tempNode.getLeft() != null) {
				tempNode = tempNode.getLeft();
			}
			leaf.setParent(tempNode);
			tempNode.setLeft(leaf);
		} else {
			leaf.setParent(prevNode);
			prevNode.setRight(leaf);
		}
	}

	/**
	 * This method calculates the next question to ask in the game.
	 * It finds a node in the tree that has at least n/3 objects as descendants, and
	 * at most 2n/3 objects as descendants, where n is the total number of objects in
	 * the tree.
	 *
	 * @return the next query that will eliminate at least n/3 objects in the tree
	 */
	@Override
	public Query findQuery(){
		int lowerBound = (countObjects() + 2) / 3; //equivalent to ceil(n/3)
		return findQueryHelper(m_root, 0, lowerBound);
	}

	/**
	 * Helper method to find the query, here is how it works:
	 * - We want to find the biggest node within the tree which has at most 2n/3 leaves
	 * - This means we need to go down the tree and "skip" nodes until we have
	 * 	skipped at least n/3 leaves
	 * - We will always go down the bigger node to ensure that we are skipping the smallest
	 * 	amount necessary, so that we don't accidentally skip beyond 2n/3
	 * - After we skip n/3 leaves, the rest of the leaves are definitely descendants of the
	 * 	current node, so it will have 2n/3 leaves.
	 * @param currNode the node that the algorithm is at
	 * @param skipped the number of leaves skipped
	 * @param lo the number we want to skip (i.e. n/3)
	 * @return
	 */
	private Query findQueryHelper(TreeNode<String> currNode, int skipped, int lo) {
		if (lo <= skipped) {
			return constructQuery(currNode);
		} else {
			int leftWeight = countObjects(currNode.getLeft());
			int rightWeight = countObjects(currNode.getRight());
			if (leftWeight < rightWeight) {
				return findQueryHelper(currNode.getRight(), skipped + leftWeight, lo);
			} else {
				return findQueryHelper(currNode.getLeft(), skipped + rightWeight, lo);
			}
		}
	}

	/**
	 * Method to draw the tree
	 */

	public void drawTree() {
		Graph graph = new SingleGraph("Question Tree");
		addNode(graph, m_root);
		graph.addAttribute("ui.stylesheet", "url('stylesheet.txt')");
		graph.display();
	}

	/**
	 * Recursive DFS method that adds all connected nodes to the graph
	 * @param graph the graph display
	 * @param node the node
	 */
	private void addNode(Graph graph, TreeNode<String> node) {
		Node currNode = graph.addNode(getID(node));
		if (node.getParent() == null) {
			//Root node
			currNode.setAttribute("ui.class", "root");
			currNode.setAttribute("ui.label", node.getValue() + "? " + " (ROOT)");
		} else {
			if (node.isLeaf()) {
				currNode.setAttribute("ui.label", node.getValue());
				currNode.setAttribute("ui.class", "leaf");
			} else {
				currNode.setAttribute("ui.label", node.getValue()+ "?");
			}
			if (node.getParent().getLeft() == node) {
				//Left Child
				Edge leftEdge = graph.addEdge(getID(node) + " X " + getID(node.getParent()), getID(node.getParent()), getID(node), true);
				leftEdge.setAttribute("ui.label", "No");
			} else {
				//Right Child
				Edge rightEdge = graph.addEdge(getID(node) + getID(node.getParent()), getID(node.getParent()), getID(node), true);
				rightEdge.setAttribute("ui.label", "Yes");
			}
		}
		if (node.getLeft() != null) addNode(graph, node.getLeft());
		if (node.getRight() != null) addNode(graph, node.getRight());
	}

	/**
	 * Helper method to generate unique ID for each node
	 * @param node the node to generate ID for
	 * @return the unique ID
	 */
	private String getID(TreeNode<String> node) {
		String ID = node.getValue();
		if (node.getLeft() != null) ID += node.getLeft().getValue();
		if (node.getRight() != null) ID += node.getRight().getValue();
		while(node.getParent() != null) {
			ID += node.getParent().getValue();
			node = node.getParent();
		}
		return ID;
	}
}
