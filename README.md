# Fibonacci-Heap

Hey everyone, this repository is dedicated for the implementation and complexity analysis of a Fibonacci Heap, a highly efficient data structure for prioritization and minimum-oriented operations. 

The Fibonacci Heap is a type of heap data structure that combines the efficiency of a binary heap with the ability to merge nodes efficiently.

The repository implements the Fibonacci Heap for integers in a clear and readable manner, making it an ideal resource for students, researchers, and practitioners who are looking to learn about or implement the data structure.

The code is well-documented and easy to follow, with comments explaining the key concepts and algorithms used in the implementation.

In order to test and demonstrate the correct functioning of the code, please see my heapPrinter repository which adds the ability to print a heap.

heapPrinter: https://github.com/haimbarash/heapPrinter

Whether you are looking to understand the Fibonacci Heap in depth or implement it for a specific use case, this repository is an excellent resource that will help you achieve your goals. So, if you want to dive into the world of efficient data structures, this repository is definitely worth a look!

***It's important to keep in mind that the strength of Fibonacci heap lies in its amortized time complexity, whereas the time complexity listed in this documentation pertains to the worst-case scenario.***

<img src=https://user-images.githubusercontent.com/112472485/217954581-3595f3ca-6d3c-459e-9a53-0e4846e7a88b.png width="400">


The repository contains two main classes: HeapNode and FibonacciHeap.

## The HeapNode class:
Represents an individual node in the Fibonacci heap. It has the following functions:

The constructor function initializes the node with a key value.
* getKey() returns the key value of the node.
* getRank() returns the rank value of the node.
* getMarked() returns whether the node has been marked (when a child has been cut from it).
* getNext() returns a pointer to the next node in the children list of a node.
* getChild() returns a pointer to the first child node in the child list of a node.
* getParent() returns a pointer to the node's parent.
* getPrev() returns a pointer to the previous node in the children list of a node.

## The FibonacciHeap class:
Implements the Fibonacci heap data structure. It has the following functions:

The constructor function initializes the Fibonacci heap with default values.
* isEmpty() returns true if the heap is empty and false otherwise. Complexity: O(1).
* insert(int key) creates a new node with the specified key value and adds it to the heap. Complexity: O(1).
* deleteMin() deletes the node with the minimum key value in the heap. Complexity: O(n) worst case.
* consolidation() reorganizes the heap structure to maintain a binomial heap structure. Complexity: O(n) worst case.
* meldNodeAtHeapEnd(HeapNode nextNode) adds the specified node to the end of the list of the heap roots. Complexity: O(1).
* meldNodeAtHeapStart(HeapNode firstNode) adds the specified node to the top of the list of the heap roots. Complexity: O(1).
* fibHeapLink(HeapNode smallKeyNode, HeapNode bigKeyNode) combines two binomial trees with the same rank into a single tree with a rank of +1. Complexity: O(1).
* findMin(): Returns the node with the minimum key value in the heap. Complexity: O(1).
* meld (FibonacciHeap heap2): Merges the current heap with another heap, heap2. The nodes in heap2's root list are added to the end of the current heap's root list.
The function updates pointers and class fields if necessary, and has a time complexity of O(1).
* size(): Returns the number of elements in the heap. Complexity: O(1).
* countersRep(): Returns an array of counters showing the number of trees in the heap with rank i. The function loops over the nodes in the root list of the heap to find the maximum rank value, and then initializes an array of counters with the maximum rank value +1. Another loop is then performed over the root list to increment the value in the i-th cell of the array by 1, for each root with rank i. Complexity: O(n).
* delete(HeapNode x): Deletes node x from the heap. The function calculates the difference in key values between node x and the minimum node in the heap, and then calls the decreaseKey function with node x and the calculated delta value. At this point, the heap contains two nodes with the same minimum value, and the heapMin pointer points to node x. The function then calls the deleteMin function, which deletes node x. The time complexity of the function is O(n) in the worst case.
* decreaseKey(HeapNode x, int delta): Decreases the key value of node x in the tree. The function updates the key value of node x, and if the heap conditions are violated (i.e. the node is not a root and has a key value less than its parent's key), the cut function is called to disconnect node x from its parent. The cascadingCut function is then called on the parent node of x to perform cascading cuts of marked nodes, or to mark x's parent as a node that lost a son. The time complexity of the function is O(log(n)).
* cascadingCut(HeapNode x): This function starts a cascading cut process in the heap starting from node x, updating the node's mark field if necessary. It works as follows: the function receives node x, which has just lost a child. If x is not marked and not in the root list of the heap, its mark field is updated to indicate it has lost a child. If x is already marked (lost a child previously), the node is cut from its parent by calling the cut function, and then the cascadingCut function is called recursively with the parent node of x. The complexity of this function is O(log(n)), as it performs a contant number of operations, calls the cut function which operates in O(1), and makes a recursive call to itself, climbing up the binomial tree and potentially reaching the root of the tree, with a height of the maximum binomial tree in the heap being O(log(n)).
* cut(HeapNode x): The function removes node x from its parent's children list. It works by checking if x is the only child of its parent. If so, the parent's list of children is set to null. If not, x is removed from the list of children by changing pointers, reducing the rank value of x's parent by 1, and adding x to the top of the root list of the heap by calling the meldNodeAtHeapStart function. The complexity of this function is O(1) as it calls the meldNodeAtHeapStart function which operates in O(1) and performs a contant number of operations.
* nonMarked(): The function returns the number of unmarked nodes in the heap. The object of type FibonacciHeap keeps track of the number of marked nodes in the heap through a markedNodes instance variable that is updated when necessary during deleteMin, cascadingCut, meld, and cut operations. To find the number of unmarked nodes, the markedNodes value is subtracted from the size value, which stores the number of nodes in the heap (using an instance variable called size). The complexity of this function is O(1) as it performs a contant number of operations.
* potential(): This function returns the value of the potential function of the tree at its current state. The value is equal to the number of trees in the root list + 2 * number of marked nodes in the tree. An object from FibonacciHeap type keeps track of the number of trees in the root list using a treeCount instance variable. In order to retrieve the potential value of the heap, the value stored in the treeCount field is returned + 2 * markedNodes. The complexity of this function is O(1) as it performs a contant number of operations.
* totalLinks(): This is a static function that returns the total number of connection operations performed during the program run. A FibonacciHeap object keeps track of the number of connection operations using a static variable totalLinkCounter. To retrieve the total number of connections, the value stored in totalLinkCounter is returned. Complexity: O(1).
* totalCuts(): This is a static function that returns the total number of cutting operations performed during the program run. A FibonacciHeap object keeps track of the number of cutting operations through a static variable totalCutCounter. To retrieve the total number of cuts, the value stored in totalCutCounter is returned. Complexity: O(1).
* kMin(FibonacciHeap H, int k): This static function get a heap H and an integer k. The heap H contains only a single tree with in it's roots list. The function returns an array of the k smallest keys in the heap H. The function creates a candidatesFibHeap heap to store the potential minimum values and an array of size k to store the minimum values. The function starts by adding the root of H to the candidatesFibHeap and pointing the refNode to the original node in the list H. Then, in a loop that runs k times, the minimum value is retrieved from the candidatesFibHeap, added to the array, and its children from the original tree are added to the candidatesFibHeap. The minimum value is then deleted from the candidatesFibHeap. This loop continues k times, and the completed array with the k minimum values is returned. The time complexity of this function is O(k * deg(H)), where deg(H) is the maximum number of children each node in the tree H have.
* getFirst(): This function returns a pointer to the first node in the root list of the heap. The function simply returns the heapRoot pointer, and its time complexity is O(1).

<img src=https://user-images.githubusercontent.com/112472485/217943704-11ba65c6-8b8e-4f1d-9511-0f0ca325c118.png width="700">

Thanks for reading.

                                                                                                                            
                                                                                                                            
