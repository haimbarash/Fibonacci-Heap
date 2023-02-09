/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap
{
	private HeapNode heapRoot = null;
	private HeapNode heapMin = null;
	private int size = 0;
	private int markedNodes = 0;
	public int treeCount = 0;
	private static int totalLinkCounter = 0;
	private static int totalCutCounter = 0;
	public FibonacciHeap() {
	}

    /**
     * public boolean isEmpty()
     *
     * Returns true if and only if the heap is empty.
     *
     * COMPLEXITY: O(1)
     */
    public boolean isEmpty()
    {
        return heapRoot == null;
    }

    /**
     * public HeapNode insert(int key)
     *
     * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
     * The added key is assumed not to already belong to the heap.
     *
     * Returns the newly created node.
     * COMPLEXITY: O(1)
     */
    public HeapNode insert(int key)
    {
    	HeapNode newNode = new HeapNode(key);
        if(heapRoot == null) { // insert to an empty Heap
        	newNode.nextNode = newNode;
        	newNode.prevNode = newNode;
        	this.heapMin = newNode;
        }
        else { //Heap is not empty
        	newNode.nextNode = heapRoot;
        	newNode.prevNode = heapRoot.prevNode;
        	heapRoot.prevNode.nextNode = newNode;
        	heapRoot.prevNode = newNode;
        	if(this.heapMin.key > newNode.key)
        		this.heapMin = newNode;
        }
    	this.heapRoot = newNode; // define newNode as the new heapRoot
    	size++;
    	treeCount++;
		return newNode;
    }

    /**
     * public void deleteMin()
     *
     * Deletes the node containing the minimum key.
     * 
     * COMPLEXITY: O(n)
     * AMORTIZED: O(log(n))
     */
    public void deleteMin()
    {
    	if(this.heapMin==null) // empty heap
    		return;
    	//if the minimum node (deleted node) has no children, delete the minimum node from the heap
    	if(this.heapMin.rank==0) {
    		if(this.heapMin.nextNode == this.heapMin) { // the only node in the heap
    			this.heapMin = null;
    			this.heapRoot = null;
    			size--;
    			treeCount--;
    			return;
    		}
    		else {
            	if(this.heapMin==this.heapRoot) // heapMin is the root of the heap
            		this.heapRoot=this.heapMin.nextNode;
            	// pointers swapping
    			this.heapMin.nextNode.prevNode = this.heapMin.prevNode;
        		this.heapMin.prevNode.nextNode = this.heapMin.nextNode;
    		}
    	}
        // if deleted node has children
        if(this.heapMin.rank>0) {
        	HeapNode currentNode=this.heapMin.childNode;
        	for(int i=0; i<this.heapMin.rank; i++) { // delete parent pointer from deleted node's children
        		currentNode.parent=null;
        		if(currentNode.mark==1) {
        			currentNode.mark=0;
        			markedNodes--;
        		}
        		currentNode = currentNode.nextNode;
        	}
        	if(this.heapMin.prevNode==this.heapMin) // if the deleted node is the only node in the first layer of the heap
        		this.heapRoot=this.heapMin=this.heapMin.childNode;
        	else{//Insert the children of the deleted node into the heap at the same location where the deleted node was previously positioned
        		// pointers swapping
        		HeapNode deletedMinFirstChild = this.heapMin.childNode;
        		this.heapMin.prevNode.nextNode = deletedMinFirstChild;
        		this.heapMin.nextNode.prevNode = deletedMinFirstChild.prevNode;
        		deletedMinFirstChild.prevNode.nextNode = this.heapMin.nextNode;
        		deletedMinFirstChild.prevNode = this.heapMin.prevNode;
        		if(this.heapMin==this.heapRoot)
        			this.heapRoot=deletedMinFirstChild;
        	}
        }
        this.heapMin = heapMin.nextNode;
        this.consolidation();
        size--;
    }
    /**
     * private void consolidation()
     *
     * Rearrange the heap after deleteMin.
     * After this operation, the list will have the same structure as a binomial heap,
     * with the trees ordered by their rank values, from the lowest to the highest.
     * 
     * COMPLEXITY: O(n)
     * AMORTIZED: O(log(n))
     *
     */
    private void consolidation() {
		int maxPossibleRank = (int) Math.ceil(Math.log10(size)/Math.log10(2)); // round up of the max possible rank in the heap
		if(maxPossibleRank == 0)
			return;
		HeapNode[] rankedNodes= new HeapNode[maxPossibleRank+1];// every cell in the array holds a node with the same rank as the cell index
		HeapNode lastLoopNode = this.heapRoot.prevNode; //
		HeapNode currentNode = this.heapRoot;
		HeapNode nextNode = null;
		boolean loop = true;
		while(loop) { // Iterates over all the nodes in the first layer of the heap
			if(currentNode==lastLoopNode) // Last node in the tree, last loop iteration
				loop=false;
			else
				nextNode=currentNode.nextNode; // Store a pointer to the next node in the tree for the next iteration
			int currentRank = currentNode.rank;
			while(rankedNodes[currentRank]!=null) { // Connecting 2 binomial trees with the same order
				HeapNode sameRankNode = rankedNodes[currentRank];
				rankedNodes[currentRank] = null;
				if(currentNode.key>sameRankNode.key)
					currentNode = fibHeapLink(sameRankNode,currentNode);
				else
					fibHeapLink(currentNode, sameRankNode);
				currentRank++;
			}
			rankedNodes[currentRank]=currentNode;
			if(loop)
				currentNode = nextNode;
		}
		this.heapMin =null;
		treeCount=0;
		for (HeapNode rankedNode : rankedNodes) { // Iterates over the array rankedNodes and sort the heap by node rank value
			if (rankedNode != null) {
				if (this.heapMin == null) { // first node in the array, the node with the lowest rank
					this.heapMin = rankedNode;
					this.heapRoot = rankedNode;
					this.heapRoot.nextNode = this.heapRoot.prevNode = this.heapRoot;
					treeCount++;
				} else {
					meldNodeAtHeapEnd(rankedNode);
					if (rankedNode.key < this.heapMin.key)
						this.heapMin = rankedNode;
				}
			}
		}
		
	}
    /**
     * public void meldNodeAtHeapEnd (HeapNode nextNode)
     *
     * Melds nextNode with the current heap, nextNode is added at the end of the first layer of the heap
     *
     * COMPLEXITY: O(1)
     * 
     */
	private void meldNodeAtHeapEnd(HeapNode nextNode) {
		HeapNode thisHeapTail = this.heapRoot.prevNode;	
		thisHeapTail.nextNode = nextNode;
		nextNode.prevNode = thisHeapTail;
		nextNode.nextNode = this.heapRoot;
        this.heapRoot.prevNode=nextNode;
        treeCount++;
	}
    /**
     * public void meldNode (HeapNode nextNode)
     *
     * Melds nextNode with the current heap, nextNode is added at the end of the first layer of the heap
     * 
     * COMPLEXITY: O(1)
     *
     */
	private void meldNodeAtHeapStart(HeapNode firstNode) {
		HeapNode heapTail = this.heapRoot.prevNode;
		firstNode.nextNode=this.heapRoot;
		heapRoot.prevNode = firstNode;
		firstNode.prevNode=heapTail;
		heapTail.nextNode = firstNode;
        this.heapRoot=firstNode;
        treeCount++;
	}
    /**
     * public void fibHeapLink(HeapNode smallKeyNode, HeapNode bigKeyNode)
     *
     * @pre: smallKeyNode and bigKeyNode are roots of binomial tree with the same rank.
     * @pre: smallKeyNode.key < bigKeyNode.key
     * 
     * Adds bigKeyNode to be the first child of smallKeyNode
     * 
     * COMPLEXITY: O(1)
     *
     */

	private HeapNode fibHeapLink(HeapNode smallKeyNode, HeapNode bigKeyNode) {
		//smallKeyNode.mark=0;
		totalLinkCounter++;
		bigKeyNode.parent=smallKeyNode;
		if(smallKeyNode.rank==0) { //small key node has no children
			bigKeyNode.nextNode = bigKeyNode.prevNode = bigKeyNode;
		}
		else {//small key node has children - add bigKeyNode to smallKeyNode children list
			bigKeyNode.nextNode = smallKeyNode.childNode;
			bigKeyNode.prevNode = smallKeyNode.childNode.prevNode;
			smallKeyNode.childNode.prevNode.nextNode = bigKeyNode;
			smallKeyNode.childNode.prevNode = bigKeyNode;
		}
		smallKeyNode.childNode = bigKeyNode;
		smallKeyNode.rank++;

		return smallKeyNode;
	}

	/**
     * public HeapNode findMin()
     *
     * Returns the node of the heap whose key is minimal, or null if the heap is empty.
     * 
     * COMPLEXITY: O(1)
     *
     */
    public HeapNode findMin()
    {
        return heapMin;
    }

    /**
     * public void meld (FibonacciHeap heap2)
     *
     * Melds heap2 with the current heap.
     * 
     * COMPLEXITY: O(1)
     *
     */
    public void meld (FibonacciHeap heap2)
    {
    	if(heap2.isEmpty())
    		return;
    	if(this.isEmpty()){
    		this.heapMin =heap2.heapMin;
    		this.heapRoot =heap2.heapRoot;
    	}
    	else { // this.heap && heap2 are not empty
    		HeapNode thisHeapTail = this.heapRoot.prevNode;	
    		HeapNode heap2Tail = heap2.heapRoot.prevNode;
    		//thisHeapTail- the tail of this heap
    		//heap2Tail- the tail of heap2
    		// pointers update:
    		thisHeapTail.nextNode = heap2.heapRoot;
    		heap2.heapRoot.prevNode = thisHeapTail;
    		heap2Tail.nextNode = this.heapRoot;
    		this.heapRoot.prevNode=heap2Tail;
    		if(heap2.heapMin.key<this.heapMin.key)
    			this.heapMin=heap2.heapMin;
    	}
    		this.size += heap2.size; // size field update
    		this.markedNodes += heap2.markedNodes;
    		this.treeCount += heap2.treeCount;
    }

    /**
     * public int size()
     *
     * Returns the number of elements in the heap.
     * 
     * COMPLEXITY: O(1)
     *
     */
    public int size()
    {
        return this.size;
    }

    /**
     * public int[] countersRep()
     *
     * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
     * (Note: The size of the array depends on the maximum order of a tree.)
     * 
     * Complexity: O(n)
     *
     */
    public int[] countersRep()
    {
    	if(this.isEmpty())
    		return new int[0];
        int maxRank = 0;
        HeapNode currentNode = this.heapRoot;
        do{ // loop over all heap root brothers and find the maximum rank tree
        	maxRank = Math.max(maxRank, currentNode.rank);
        	currentNode=currentNode.nextNode;
        }while(currentNode!=this.heapRoot);
        int[] rankCounterArray = new int[maxRank+1];
        do{ // loop over all heap root brothers. for every tree rank, add 1 to the relevant cell in rankCounterArray
        	rankCounterArray[currentNode.rank]++;
        	currentNode=currentNode.nextNode;
        }while(currentNode!=this.heapRoot);
        return rankCounterArray;
    }

    /**
     * public void delete(HeapNode x)
     *
     * Deletes the node x from the heap.
     * It is assumed that x indeed belongs to the heap.
     * 
     * COMPLEXITY: O(n)
     * AMORTIZED: O(log(n))
     *
     */
    public void delete(HeapNode x)
    {
    	int delta =x.key - this.heapMin.key;
    	decreaseKey(x, delta);
    	deleteMin();
    }

    /**
     * public void decreaseKey(HeapNode x, int delta)
     *
     * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
     * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
     * 
     * COMPLEXITY: O(log(n))
     * AMORTIZED: O(1)
     * 
     */
    public void decreaseKey(HeapNode x, int delta)
    {
        x.key = x.key - delta; // Update node key value
        HeapNode xParent = x.parent;
        if(xParent!=null && x.key<=xParent.key) { // if the node is not a root and causing a violation in the binomial-tree order
        	cut(x);
        	cascadingCut(xParent);        	
        }
        if(x.key<=this.heapMin.key) {
        	this.heapMin = x;
        }
    }
    /**
     * public void cascadingCut(HeapNode x)
     *
     * CascadingCut function is attempted on the node x.
     * If xParent is unmarked, it is marked during the function. 
     * If xParent is already marked, x is cut from his parent, and CascadingCut is recursively called again.
     * 
     * COMPLEXITY: O(log(n))
     * AMORTIZED: O(1)
     * 
     */

    private void cascadingCut(HeapNode x) {
    	HeapNode xParent = x.parent;
    	if(xParent!=null) {
    		if(x.mark==0) {
    			x.mark=1;
    			this.markedNodes++;
    		}    		
    		else {
    			cut(x);
    			cascadingCut(xParent);
    		}
    	}
		
	}
    /**
     * public void cut(HeapNode x, HeapNode xParent)
     *
     * cut function removes x from the child list of xParent and then add x to the root list of the heap.
     * 
     * COMPLEXITY: O(1)
     * 
     */

	private void cut(HeapNode x) {
		totalCutCounter++;
		HeapNode xParent = x.parent;
		// remove x from the child list of xParent:
		if(x==x.nextNode) // x is the only child of his parent
			xParent.childNode = null;
		else {
			if(xParent.childNode == x) { // x is the first child in his parent child list
				xParent.childNode = xParent.childNode.nextNode;
			}
			x.prevNode.nextNode = x.nextNode;
			x.nextNode.prevNode = x.prevNode;
		}
		meldNodeAtHeapStart(x);
		x.parent = null;
		if(x.mark==1) {
			x.mark = 0;
			this.markedNodes--;
		}		
		xParent.rank--;		
	}

	/**
     * public int nonMarked()
     *
     * This function returns the current number of non-marked items in the heap
     * 
     * COMPLEXITY: O(1)
     */
    public int nonMarked()
    {
		return this.size - this.markedNodes;
    }

    /**
     * public int potential()
     *
     * This function returns the current potential of the heap, which is:
     * Potential = #trees + 2*#marked
     *
     * In words: The potential equals to the number of trees in the heap
     * plus twice the number of marked nodes in the heap.
     * 
     * COMPLEXITY: O(1)
     */
    public int potential()
    {
		return treeCount +2* this.markedNodes;
    }

    /**
     * public static int totalLinks()
     *
     * This static function returns the total number of link operations made during the
     * run-time of the program. A link operation is the operation which gets as input two
     * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
     * tree which has larger value in its root under the other tree.
     * 
     * COMPLEXITY: O(1)
     */
    public static int totalLinks()
    {
        return totalLinkCounter;
    }

    /**
     * public static int totalCuts()
     *
     * This static function returns the total number of cut operations made during the
     * run-time of the program. A cut operation is the operation which disconnects a subtree
     * from its parent (during decreaseKey/delete methods).
     * 
     * COMPLEXITY: O(1)
     */
    public static int totalCuts()
    {
        return totalCutCounter;
    }

    /**
     * public static int[] kMin(FibonacciHeap H, int k)
     *
     * This static function returns the k-smallest elements in a Fibonacci heap that contains a single tree.
     * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
     *
     * ###CRITICAL### : you are NOT allowed to change H.
     * 
     * COMPLEXITY: O(k*deg(H))
     */
    public static int[] kMin(FibonacciHeap H, int k)
    {
        int[] kMinArr = new int[k]; // Returned array
        if(k>0) {
        	FibonacciHeap candidatesFibHeap = new FibonacciHeap();
        	HeapNode newNode = candidatesFibHeap.insert(H.findMin().key); // Add the root of the tree to candidatesFibHeap 
        	HeapNode currentMin;
        	HeapNode currentMinChild;
        	HeapNode currentMinNextChild;
        	newNode.refNode=H.findMin(); // Store a reference pointer to the node in the original tree
        	for(int i=0;i<k;i++) {
        		currentMin = candidatesFibHeap.findMin();
        		currentMinNextChild = currentMinChild = currentMin.refNode.childNode; // The child of the current node in the original tree (H heap)
        		if(currentMinChild!=null) // Add the children of the current minimum node from the original tree to the candidates Fibonacci heap
        			do {
        				newNode = candidatesFibHeap.insert(currentMinNextChild.key);
        				newNode.refNode=currentMinNextChild;
        				currentMinNextChild = currentMinNextChild.nextNode;        			
        			} while(currentMinChild!=currentMinNextChild);
        		kMinArr[i]=currentMin.key;
        		candidatesFibHeap.deleteMin();        		
        	}
        }
        return kMinArr;
    }

    /**
     * public class HeapNode
     */
    public static class HeapNode{

        public int key;
        public int rank = 0; // represents the number of children
        public int mark = 0;
        public HeapNode parent; // pointer to parent HeapNode
        public HeapNode nextNode; // pointer to next brother HeapNode
        public HeapNode prevNode; // pointer to previous brother HeapNode
        public HeapNode childNode; // pointer to first children HeapNode
        public HeapNode refNode; // use for kMin function

        public HeapNode(int key) {
            this.key = key;
            this.nextNode=this;
            this.prevNode=this;
        }

        public int getKey() {
            return this.key;
        }
        //this functions use for HeapPrinter and tester class

		public int getRank() {
			return this.rank;
		}

		public boolean getMarked() {
			return this.mark != 0;
		}

		public HeapNode getNext() {
			return this.nextNode;
		}

		public HeapNode getChild() {
			return this.childNode;
		}
		public HeapNode getParent() {
			return this.parent;
		}
		public HeapNode getPrev() {
			return this.prevNode;
		}
    }

	public HeapNode getFirst() { // Attempts on this. heap return heapRoot.
		return this.heapRoot;
	}
}
