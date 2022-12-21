// C++ program to Get Level of a 
// node in a Binary Tree 
#include <iostream>
#include <list>
#include <vector>
#include <map>
#include <exception>
#include <set>
using namespace std;

/* A tree node structure */
struct Node {
	int value;     // hodnota uzla
	Node* smaller; // uzol 'smaller' a jeho nasledovnici maju hodnotu mensiu ako 'value'
	Node* greater; // uzol 'greater' a jeho nasledovnici maju hodnotu vacsiu ako 'value'

	explicit Node(int value = 0, Node* smaller = nullptr, Node* greater = nullptr)
		: value(value)
		, smaller(smaller)
		, greater(greater)
	{
	}
};

// Binarny vyhladavaci strom
struct BinarySearchTree {
	Node* root; // koren stromu

	explicit BinarySearchTree(Node* root = nullptr)
		: root(root)
	{
	}
};

/* Helper function for getLevel().
It returns level of the data if data is
present in tree, otherwise returns 0.*/
int getLevelUtil(struct Node* node,
	int data, int level)
{
	if (node == NULL)
		return 0;

	if (node->value == data)
		return level;

	int downlevel = getLevelUtil(node->smaller,
		data, level + 1);
	if (downlevel != 0)
		return downlevel;

	downlevel = getLevelUtil(node->greater,
		data, level + 1);
	return downlevel;
}

/* Returns level of given data value */
int getLevel(struct Node* node, int data)
{
	return getLevelUtil(node, data, 0);
}

/* Utility function to create a
new Binary Tree node */
//struct node* newNode(int data)
//{
//	struct node* temp = new struct node;
//	temp->data = data;
//	temp->left = NULL;
//	temp->right = NULL;
//
//	return temp;
//}

unsigned depth(const BinarySearchTree* tree, int value) {
	Node* uzol = tree->root;
	return getLevel(uzol, value);
}

Node* addNode(Node* curr_node, const int val) {
	if (curr_node) {
		if (val < curr_node->value) {
			curr_node->smaller = addNode(curr_node->smaller, val);
		}
		else if (val > curr_node->value) {
			curr_node->greater = addNode(curr_node->greater, val);
		}
		return curr_node;
	}
	else {
		return new Node{ val,nullptr,nullptr };
	}
}

void addNode(BinarySearchTree* bst, const int val) {
	bst->root = addNode(bst->root, val);
}

BinarySearchTree* createBST(const initializer_list<int>& list) {
	BinarySearchTree* bst{ new BinarySearchTree{} };
	for (int i : list) {
		addNode(bst, i);
	}
	return bst;
}

int main()
{
	BinarySearchTree* bst{ createBST({20,10,70,0,15,50,80,18,55}) };
	cout << depth(bst, 0);
	
	return 0;
}

// This code is contributed 
// by Akanksha Rai 

//queue<Node*> rad;
//list<int> hodnoty;
//rad.push(tree->root);
//while (!rad.empty())
//{
//	Node* temp = rad.front();
//	rad.pop();
//
//	if (temp->smaller)
//		hodnoty.push_back(temp->smaller->value);
//
//	if (temp->greater)
//		hodnoty.push_back(temp->greater->value);
//
//	if (temp->smaller != nullptr)
//		rad.push(temp->smaller);
//	if (temp->greater != nullptr)
//		rad.push(temp->greater);
//}
//hodnoty.sort();
//return hodnoty; */