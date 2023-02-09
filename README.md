# Fibonacci-Heap

Hey everyone, this repository is dedicated for the implementation and complexity analysis of a Fibonacci Heap, a highly efficient data structure for prioritization and minimum-oriented operations. 

The Fibonacci Heap is a type of heap data structure that combines the efficiency of a binary heap with the ability to merge nodes efficiently.

The repository implements the Fibonacci Heap for integers in a clear and readable manner, making it an ideal resource for students, researchers, and practitioners who are looking to learn about or implement the data structure.

The code is well-documented and easy to follow, with comments explaining the key concepts and algorithms used in the implementation.

In order to test and demonstrate the correct functioning of the code, please see my heapPrinter repository which adds the ability to print a heap.

heapPrinter: https://github.com/haimbarash/heapPrinter

Whether you are looking to understand the Fibonacci Heap in depth or implement it for a specific use case, this repository is an excellent resource that will help you achieve your goals. So, if you want to dive into the world of efficient data structures, this repository is definitely worth a look!

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
* isEmpty() returns true if the heap is empty and false otherwise.
* insert(int key) creates a new node with the specified key value and adds it to the heap.
* deleteMin() deletes the node with the minimum key value in the heap.
* consolidation() reorganizes the heap structure to maintain a binomial heap structure.
* meldNodeAtHeapEnd(HeapNode nextNode) adds the specified node to the top of the list of heap roots.
* meldNodeAtHeapStart(HeapNode firstNode) adds the specified node to the end of the list of heap roots.
* fibHeapLink(HeapNode smallKeyNode, HeapNode bigKeyNode) combines two binomial trees with the same rank into a single tree with a rank of +1.

## Complexity analysis:
The operations in the FibonacciHeap class have the following complexities:

The constructor and isEmpty functions have a complexity of O(1).
The insert function has a complexity of O(1).
The deleteMin function has a complexity of O(n) in the worst case, where n is the number of nodes in the heap.
The consolidation function has a complexity of O(n) in the worst case.
The meldNodeAtHeapEnd and meldNodeAtHeapStart functions have a complexity of O(1).
The fibHeapLink function has a complexity of O(1).

<img src=https://user-images.githubusercontent.com/112472485/217943704-11ba65c6-8b8e-4f1d-9511-0f0ca325c118.png width="700">

                                                                                                                            
                                                                                                                            
