package Array.Heap;

import List.LinkedList;
import List.Node;

public class MaxHeap extends Heap{

    public MaxHeap(int N) {
        super(N);
    }

    protected void percolateDown(int no){
        int left, right;
        left = 2 * no + 1;
        right = 2 * no + 2;
        while ((left < count && array[no].getData() < array[left].getData()) ||
                (right < count && array[no].getData() < array[right].getData())){
            if (right >= count || array[left].getData() > array[right].getData()){
                swapNode(no, left);
                no = left;
            } else {
                swapNode(no, right);
                no = right;
            }
            left = 2 * no + 1;
            right = 2 * no + 2;
        }
    }

    protected void percolateUp(int no){
        int parent;
        parent = (no - 1) / 2;
        while (parent >= 0 && array[parent].getData() < array[no].getData()){
            swapNode(parent, no);
            no = parent;
            parent = (no - 1) / 2;
        }
    }

    public void update(int k, int newValue){
        int oldValue = array[k].getData();
        array[k].setData(newValue);
        if (oldValue > newValue){
            percolateDown(k);
        } else {
            percolateUp(k);
        }
    }

    /**
     * Given the index of a heap node in a binary max heap, write the method in {\bf MaxHeap} class that returns the
     * indexes of its ascendants (parent, grandparent, grandgrandparent, $\ldots$) of this node. The array should contain
     * only that many items not more not less.
     */
    public int[] ascendants(int index){

        if(index == 0){
            return new int[0];
        }

        int size = 0;

        int curr = index;

        while(curr > 0){
            curr = (curr - 1) / 2;
            size++;
        }

        int[] ascendants = new int[size];
        int pos = size - 1;

        curr = index;

        while(curr > 0){
            curr = (curr - 1) / 2;

            ascendants[pos] = curr;
            pos--;
        }

        return ascendants;
    }

    /**
     * Given the index of a heap node in a max-heap, write a method that determines if the subtree rooted from the node
     * with index satisfies the heap-order property. Do not use any class or external methods.
     */
    public boolean heapOrder(int index){
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if(left >= count){
            return true;
        }

        if(right < count){
            if((array[index].getData() < array[left].getData()) || (array[index].getData() < array[right].getData())){
                return false;
            }
            return heapOrder(left) && heapOrder(right);
        }
        else{
            if(array[index].getData() < array[left].getData()){
                return false;
            }
            return heapOrder(left);
        }
    }

    /**
     * Write the method in {\bf MaxHeap} class that returns the shortest distance between two nodes in the heap with
     * indexes index1 and index2. Generate the ascendant lists of the nodes with index1 and index2 (You can assume the
     * tree depth is smaller than 100). Compare those lists to solve the problem.
     */
    public int shortestDistanceBetWeenNodes(int index1, int index2){
        LinkedList list1 = new LinkedList();
        LinkedList list2 = new LinkedList();

        int curr = index1;

        while(curr > 0){
            curr = (curr - 1) / 2;
            Node new_node = new Node(curr);
            list1.insertFirst(new_node);
        }
        list1.insertLast(new Node(index1));

        curr = index2;

        while(curr > 0){
            curr = (curr - 1) / 2;
            Node new_node = new Node(curr);
            list2.insertFirst(new_node);
        }
        list2.insertLast(new Node(index2));

        Node tmp1 = list1.getHead();
        Node tmp2 = list2.getHead();

        int lca_depth = 0;

        while(tmp1 != null && tmp2 != null && tmp1.getData() == tmp2.getData()){
            lca_depth++;

            tmp1 = tmp1.getNext();
            tmp2 = tmp2.getNext();
        }

        tmp1 = list1.getHead();
        int depth1 = 0;

        while(tmp1 != null){
            depth1++;
            tmp1 = tmp1.getNext();
        }

        tmp2 = list2.getHead();
        int depth2 = 0;

        while(tmp2 != null){
            depth2++;
            tmp2 = tmp2.getNext();
        }

        return (depth1 - lca_depth) + (depth2 - lca_depth);
    }

    /**
     * Write the method in MaxHeap class that returns the third maximum number in the heap. Your method should run in
     * {\cal O}(1) time. You are not allowed to use any class method except getData.
     */
    public int third(){
        return 0;
    }

}
