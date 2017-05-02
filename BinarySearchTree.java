/*
  This is an implementation of Binary Search Tree in Java.
  Functions implemented : 
    1. Add 
	2. Contains 
	3. Traversals (Inorder, Postorder, Preorder) 
	4. Delete 
	5. Minimum 
	6. Maximum
	7. Successor 
*/

import java.io.IOException;

public class BinarySearchTree {

	static Node root;

	static class Node {
		int data;
		Node left;
		Node right;
		Node parent;

		public Node(int data) {
			this.data = data;
			this.parent = null;
			this.left = null;
			this.right = null;
		}
	}

	// Following function 'insert' adds node to the existing binary search tree
	// If value to be added is less than or equal to the traversing node,
	// traverse to the left else traverse to the right.
	// Do this until you encounter leaf node.
	// After you reach the leaf, add the new node at appropriate position i.e.
	// to the left or right.
	public void insert(int data) {
		Node nodeToBeAdded = new Node(data);
		Node traverseNode = root;
		Node parentOfNewNode = null;
		while (traverseNode != null) {
			parentOfNewNode = traverseNode;
			if (data < traverseNode.data) {
				traverseNode = traverseNode.left;
			} else {
				traverseNode = traverseNode.right;
			}
		}
		nodeToBeAdded.parent = parentOfNewNode;
		if (parentOfNewNode == null) {
			root = nodeToBeAdded;
		} else if (nodeToBeAdded.data <= parentOfNewNode.data) {
			parentOfNewNode.left = nodeToBeAdded;
		} else {
			parentOfNewNode.right = nodeToBeAdded;
		}
	}

	// Returns the minimum element in the subtree rooted at 'node'
	public Node getMin(Node node) {
		Node traverseNode = node;
		while (traverseNode.left != null) {
			traverseNode = traverseNode.left;
		}
		return traverseNode;
	}

	// Returns the maximum element in the subtree rooted at 'node'
	public Node getMax(Node node) {
		Node traverseNode = node;
		while (traverseNode.right != null) {
			traverseNode = traverseNode.right;
		}
		return traverseNode;
	}

	public boolean contains(int value) {
		if (searchNode(value) != null) {
			return true;
		} else {
			return false;
		}
	}

	// Returns the node wanted if it is present, otherwise returns null
	public Node searchNode(int value) {
		Node traverseNode = root;
		while (traverseNode != null) {
			if (traverseNode.data == value) {
				return traverseNode;
			}

			if (value < traverseNode.data) {
				traverseNode = traverseNode.left;
			} else {
				traverseNode = traverseNode.right;
			}
		}
		return null;
	}

	// Deletes the node from the tree.
	/*
	 * Case 1 : If node z has no left child, we replace z by its right child,
	 * which may or may not be null. When the right child is null, then this
	 * case deals with the situation when z has no children. - We simply remove
	 * the node by modifying its parent to replace with null as its child. If z
	 * has only one child, replace z with that child.
	 * 
	 * Case 2 : Node z has both left and a right child. We find the successor y
	 * of z lying in the right subtree of z and has no left child. splice y out
	 * of its current location and replace z with y. ---- Case 2.1 : if y is z's
	 * right child, replace z by y leaving y's right child alone ---- Case 2.2 :
	 * if y lies within z's right subtree but is not z's right child, then first
	 * replace y by its own right child, and then replace z by y.
	 * 
	 */
	public void delete(int value) {
		Node nodeToBeDeleted = searchNode(value);
		if (nodeToBeDeleted.left == null) // Case 1
		{
			transplant(nodeToBeDeleted, nodeToBeDeleted.right);
		} else if (nodeToBeDeleted.right == null) // Case 1
		{
			transplant(nodeToBeDeleted, nodeToBeDeleted.left);
		} else // Case 2
		{
			Node replaceNode = getMin(nodeToBeDeleted.right);

			// Case 2.2
			if (!(replaceNode == nodeToBeDeleted.right)) {
				transplant(replaceNode, replaceNode.right);
				replaceNode.right = nodeToBeDeleted.right;
				replaceNode.right.parent = replaceNode;
			}
			transplant(nodeToBeDeleted, replaceNode);
			replaceNode.left = nodeToBeDeleted.left;
			replaceNode.left.parent = replaceNode;
		}
	}

	/*
	 * Inorder traversal : Left - Root- Right
	 */
	public void inOrder(Node node) {
		if (node.left != null) {
			inOrder(node.left);
		}
		System.out.println(node.data);
		if (node.right != null) {
			inOrder(node.right);
		}

	}

	/*
	 * Preorder traversal : Root - Left- Right
	 */
	public void preOrder(Node node) {
		System.out.println(node.data);
		if (node.left != null) {
			preOrder(node.left);
		}

		if (node.right != null) {
			preOrder(node.right);
		}

	}

	/*
	 * Postorder traversal : Left - Right - Root
	 */
	public void postOrder(Node node) {
		if (node.left != null) {
			postOrder(node.left);
		}

		if (node.right != null) {
			postOrder(node.right);
		}
		System.out.println(node.data);

	}

	/*
	 * Transplant (u,v): Replaces subtree rooted at node with the subtree rooted
	 * at node v node u's parent becomes node v's parent and u's patent end up
	 * having v as its appropriate child.
	 */

	public void transplant(Node u, Node v) {
		if (u.parent == null) {
			root = v;
			v.parent = null;
		} else if (u == u.parent.left) {
			u.parent.left = v;
		} else {
			u.parent.right = v;
		}
		if (v != null) {
			v.parent = u.parent;
		}
	}

	/*
	 * returns the successor of node Successor of node x is the node with the
	 * smallest key greater than x.key Case 1 : node x's right subtree is non
	 * empty : then successor of x is leftmost node in x's right subtree Case 2
	 * : Node x's right subtree is empty. then successor y of node x is the
	 * lowest ancestor of x whose left child is also an ancestor of x. i.e. go
	 * up the tree until we find node that is left child of its parent
	 */
	public Node successor(int value) {
		Node targetNode = searchNode(value);
		Node successor = null;

		// Case 1 :
		if (targetNode.right != null) {
			return getMin(targetNode.right);
		}
		// Case 2 :
		successor = targetNode.parent;
		while (targetNode == successor.right && successor != null) {
			targetNode = successor;
			successor = successor.parent;
		}
		return successor;
	}

	public static void main(String args[]) throws IOException {
		BinarySearchTree node = new BinarySearchTree();
		node.insert(10);
		node.insert(40);
		node.insert(50);
		node.insert(5);
		node.insert(15);
		node.insert(32);
		node.insert(33);

		System.out.println("Inorder Traversal");
		System.out.println("-----------------------------");
		node.inOrder(root);
		System.out.println("-----------------------------");
		System.out.println();
		System.out.println("Preorder Traversal");
		System.out.println("-----------------------------");
		node.preOrder(root);
		System.out.println("-----------------------------");
		System.out.println();
		System.out.println("Postorder Traversal");
		System.out.println("-----------------------------");
		node.postOrder(root);
		System.out.println("-----------------------------");
		System.out.println();

		System.out.println("Contains 5 ? Answer : " + node.contains(5));
		System.out.println("------------------------------");
		System.out.println("Contains 200 ? Answer : " + node.contains(200));
		System.out.println("------------------------------");
		System.out.println("Minimum value : " + node.getMin(root).data);
		System.out.println("------------------------------");
		System.out.println("Mximum value : " + node.getMax(root).data);
		System.out.println("------------------------------");
		System.out.println("Successor of 5 is : " + node.successor(5).data);
		System.out.println();
		System.out.println("------------------------------");
		System.out.println("Deleted node is : 10");
		node.delete(40);
		System.out.println("------------------------------");
		System.out.println("Tree after deleting the node :");
		System.out.println();
		node.inOrder(root);

	}

}
