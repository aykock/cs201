package Array;

import java.util.Arrays;

public class Queue {

    private Element[] array;

    private int first;

    private int last;

    private int N;

    public Queue(int N){
        this.N = N;
        array = new Element[N];
        first = 0;
        last = 0;
    }

    boolean isFull(){
        return (last + 1) % N == first;
    }

    public boolean isEmpty(){
        return first == last;
    }

    public void enqueue(Element element){
        if (!isFull()){
            array[last] = element;
            last = (last + 1) % N;
        }
    }

    public Element dequeue(){
        if (!isEmpty()){
            Element tmp = array[first];
            first = (first + 1) % N;
            return tmp;
        }
        return null;
    }

    public String toString(){
        String s = "";
        for (int i = first; i <last; i = (i + 1) % N){
            s += array[i].getData() + " ";
        }
        return s.trim();
    }

    public void insertArray(int[] data){
        for (int datum : data){
            enqueue(new Element(datum));
        }
    }

    /**
     * Write another constructor method which constructs a new array based queue by adding the elements in the $list$
     * of queues one by one.  So, the first $k$ elements of the original queue will be constructed with the first
     * elements of the $k$ queues in the list; the second $k$ elements of the original queue will be constructed with
     * the second elements of the $k$ queues in the list etc. The elements from queues should be recreated (not copied
     * from the queues). You are not allowed to use enqueue, dequeue, isEmpty functions.
     */
    public Queue(Queue[] list){
        int[] first_indexes = new int[list.length];
        int[] last_indexes = new int[list.length];

        for(int i = 0; i < list.length; i++){
            int first = list[i].first;
            int last = list[i].last;
            first_indexes[i] = first;
            last_indexes[i] = last;
        }

        int total_N = 0;
        for(Queue q : list){
            total_N += q.array.length;
        }

        this.N = total_N;
        this.array = new Element[N];
        this.first = 0;
        this.last = 0;

        while(!Arrays.equals(first_indexes, last_indexes)) {
            for (int j = 0; j < list.length; j++) {
                if (first_indexes[j] != last_indexes[j]) {
                    int new_data = list[j].array[first_indexes[j]].getData();
                    Element e = new Element(new_data);
                    this.array[last] = e;
                    last = (last + 1) % N;

                    first_indexes[j] = (first_indexes[j] + 1) % list[j].N;
                }

            }
        }


    }

    /**
     * Write a method which copies all the elements of the src queue and inserts to the queue at position index. You are
     * not allowed to use enqueue, dequeue, isEmpty functions. You can assume the destination queue has enough space for
     * insertion. Your method should run in ${\cal O}(N)$ time. Hint: Start by counting the number of positions to shift
     * for opening up space for the elements of src.
     */
    public void copyPaste(Queue src, int index){

        int src_length = 0;
        int m = src.first;

        while(m != src.last){
            src_length++;
            m = (m+1) % src.N;
        }

        int i = (this.last + this.N) % this.N;
        int new_index;

        this.last = (this.last + src_length) % N;

        while(true){
            new_index = (i + src_length) % this.N;
            this.array[new_index] = this.array[i];

            if(i == (this.first + index) % this.N){
                break;
            }

            i = (i - 1 + N) % N;

        }

        int tmp = 0;
        int j = src.first;

        while(tmp != src_length){
            Element new_element = new Element(src.array[j].getData());
            this.array[i] = new_element;

            j = (j+1) % src.N;
            i = (i + 1) % N;
            tmp++;
        }


    }

    /**
     * Write a method which cuts all the elements between indexes p and q from the original queue and inserts at the end
     * to the dest queue. You are not allowed to use enqueue, dequeue, isEmpty functions. You can assume the destination
     * queue has enough space for insertion. Your method should run in ${\cal O}(N)$ time.
     */
    public void cutPaste(Queue dest, int p, int q){
        int min_index = Math.min(p,q);
        int max_index = Math.max(p,q);

        int start_index = (this.first + min_index) % this.N;
        int end_index = (this.first + max_index + 1) % this.N;

        int index = start_index;
        int shift = 0;

        while(index != end_index){
            dest.array[dest.last] = this.array[index];
            dest.last = (dest.last + 1) % dest.N;

            index = (index + 1) % this.N;
            shift++;

        }

        int shifted_index = index;

        while(index != this.last){
            shifted_index = (index - shift + this.N) % this.N;
            this.array[shifted_index] = this.array[index];
            index = (index + 1) % this.N;
        }

        this.last = (this.last - shift + N) % this.N;
    }

    /**
     * Write a method which dequeues data as the $k$'th element from the first. dequeue(1) is equal to the original
     * dequeue, that is, the first element has index 1. You are not allowed to use any queue methods and any external
     * structures (arrays, queues, trees, etc). You are allowed to use attributes, constructors, getters and setters.
     */
    public Element dequeue(int k){
        Element dequeued = null;
        if(this.first == this.last){
            return dequeued;
        }
        else if(k == 1){
            dequeued = this.array[first];
            first = (first + 1) % N;
            return dequeued;
        }

        int index = this.first;
        int n = 1;

        while(n < k){
            index = (index + 1) % N;
            n++;
        }

        dequeued = this.array[index];

        while(index != (this.last - 1 + N) % N){
            int shifted_index = (index + 1) % N;
            this.array[index] = this.array[shifted_index];
            index = shifted_index;
        }

        this.last = (this.last - 1 + N) % N;

        return dequeued;
    }

    /**
     * Write a method which removes and returns the second item from the queue. Your methods should run in ${\cal O}$(1)
     * time. Do not use any class or external methods except isEmpty().
     */
    public Element dequeue2nd(){
        int second_index = (this.first + 1) % N;
        int first_index = this.first;

        Element dequeued = this.array[second_index];

        this.array[second_index] = this.array[first_index];
        this.first = (this.first + 1) % N;

        return dequeued;
    }

    /**
     * Write a function that creates and returns a new queue by removing even indexed elements from the original queue
     * and inserting into the newly created queue. The first node has index 1. You are not allowed to use any queue or
     * linked list methods, just attributes, constructors, getters and setters.
     */
    public Queue divideQueue(){
        Queue evenQueue = new Queue(this.N);


        int readIndex = this.first;
        int writeIndex = this.first;
        int count = 1;  // 1-based indexing

        while (readIndex != this.last) {
            if (count % 2 == 1) {
                // odd index → stays in this queue
                this.array[writeIndex] = this.array[readIndex];
                writeIndex = (writeIndex + 1) % N;
            } else {
                // even index → goes to new queue
                evenQueue.array[evenQueue.last] = new Element(this.array[readIndex].getData());
                evenQueue.last = (evenQueue.last + 1) % evenQueue.N;
            }
            readIndex = (readIndex + 1) % N;
            count++;
        }

        this.last = writeIndex;

        return evenQueue;
    }

    /**
     * Write a function that inserts a new element after the largest element of the queue. Write the function for array
     * implementation. You are not allowed to use any queue methods, just attributes, constructors, getters and setters.
     */
    public void insertAfterLargest (int data){
        int i = this.first;
        int largest_index = i;
        int largest = this.array[largest_index].getData();

        while(i != this.last){
            int curr = this.array[i].getData();
            if(curr > largest){
                largest = curr;
                largest_index = i;
            }
            i = (i + 1) % N;
        }

        int insert_index = (largest_index + 1) % N;

        while(i != insert_index){
            int prev_index = (i-1 + N) % N;
            this.array[i] = this.array[prev_index];
            i = (i-1 + N) % N;
        }

        Element new_element = new Element(data);
        this.array[insert_index] = new_element;
        this.last = (this.last + 1) % N;

    }

    /**
     * Write the method which removes only the odd indexed (1, 3, . . .) elements from the queue. The first element has
     * index 1. You are only allowed to use enqueue, dequeue, isEmpty functions. {\bf You should use external queue}.
     * You are not allowed to use any queue attributes such as first, last, array etc.
     */
    public void removeOddIndexed(){
        int count = 1;
        Queue external = new Queue(this.N);

        while(!this.isEmpty()){
            Element dequeued = this.dequeue();
            if(count % 2 == 0){
                external.enqueue(dequeued);
            }

            count++;
        }

        while(!external.isEmpty()){
            this.enqueue(external.dequeue());
        }
    }

    /**
     * Write a method where {$k$} is a non-negative integer representing the number of positions to rotate the queue by,
     * which is implemented using an array. After the rotation, the first element of the queue should move to the back
     * {$k$} times, and the order of the other elements should shift accordingly. You are not allowed to use the
     * enqueue(), dequeue(), or peek() methods. The solution should rotate the array in {$O(N)$} time, where {$N$} is
     * the number of elements in the queue. Assume that $k \leq N$.
     */
    public void rotateQueue(int k){
        int count = 0;
        int i = this.first;
        Element[] firstK = new Element[k];

        while(count < k){
            firstK[count] = this.array[i];
            i = (i+1) % N;
            count++;
        }

        count = 0;

        while(count < k){
            int rotate_index = (last + count) % N;
            this.array[rotate_index] = firstK[count];
            count++;
        }

        this.first = (this.first + k) % N;
        this.last = (this.last + k) % N;

    }

    /**
     * Write another constructor-like method in Array-based Queue implementation which constructs a new array based queue
     * by adding the elements in the
     * $list$ of queues one by one in zig-zag fashion. So, the first $k$ elements of the original queue will be
     * constructed with the first elements of the $k$ queues in the list; the
     * second $k$ elements of the original queue will be constructed with the
     * last elements of the $k$ queues in the list, third $k$ elements of the original queue will be
     * constructed with the second elements of the $k$ queues in the list; etc. The elements from
     * queues should be recreated (not copied from the queues). You are not
     * allowed to use enqueue, dequeue, isEmpty functions. You should solve
     * the question for array implementation. You may assume the length of
     * each queue is same and even.
     */
    public static Queue QueueZigZag(Queue[] list){

        int size = 0;
        int i = list[0].first;

        while(i != list[0].last){
            size++;
            i = (i+1) % list[0].N;
        }

        Queue zigzag = new Queue(size * list.length + 1);

        int[] first_indexes = new int[list.length];
        int[] last_indexes = new int[list.length];
        int j = 0;

        for(Queue q : list) {
            first_indexes[j] = q.first;
            last_indexes[j] = (q.last - 1 + list[0].N) % list[0].N;
            j++;
        }


        int count = 1;

        while(count <= size){
            int[] curr_indexes;
            int data;
            boolean isOdd = count % 2 == 1;

            if(isOdd){
                curr_indexes = first_indexes;
            }
            else{
                curr_indexes = last_indexes;
            }

            for(int m = 0; m < list.length; m++){
                data = list[m].array[curr_indexes[m]].getData();
                Element new_element = new Element(data);

                zigzag.array[zigzag.last] = new_element;
                zigzag.last = (zigzag.last + 1) % zigzag.N;

                if(isOdd){
                    curr_indexes[m] = (curr_indexes[m] + 1) % list[0].N;
                }
                else{
                    curr_indexes[m] = (curr_indexes[m] - 1 + list[0].N) % list[0].N;
                }
            }

            if(isOdd){
                first_indexes = curr_indexes;
            }else{
                last_indexes = curr_indexes;
            }
            count++;
        }

        return zigzag;
    }

}
