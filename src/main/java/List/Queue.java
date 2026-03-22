package List;

public class Queue {

    protected Node first;
    protected Node last;

    public Queue() {
        first = null;
        last = null;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void enqueue(Node newNode) {
        if (isEmpty()) {
            first = newNode;
        } else {
            last.setNext(newNode);
        }
        last = newNode;
    }

    public Node dequeue() {
        Node result = first;
        if (!isEmpty()) {
            first = first.getNext();
            if (isEmpty()) {
                last = null;
            }
        }
        return result;
    }

    public void insertArray(int[] data) {
        for (int datum : data) {
            enqueue(new Node(datum));
        }
    }

    public String toString() {
        if (isEmpty()) {
            return "";
        }
        String s = "" + first.getData();
        Node tmp = first.getNext();
        while (tmp != null) {
            s += " " + tmp.getData();
            tmp = tmp.getNext();
        }
        return s.trim();
    }

    /**
     * Write another constructor method which constructs a new list based queue by concatenating all elements in the list
     * of queues in order. The elements from queues should be recreated (not copied from the queues). You are not
     * allowed to use enqueue, dequeue, isEmpty functions.
     */
    public Queue(Queue[] list) {
        first = null;
        last = null;

        for (Queue q : list) {
            Node tmp = q.first;
            while (tmp != null) {
                Node new_node = new Node(tmp.getData());
                if (this.first == null) {
                    this.first = new_node;
                } else {
                    this.last.setNext(new_node);
                }
                this.last = new_node;

                tmp = tmp.getNext();
            }
        }
    }

    /**
     * Write a method which dequeues data as the $k$'th element from the first. dequeue(1) is equal to the original
     * dequeue, that is, the first element has index 1. You are not allowed to use any queue methods and any external
     * structures (arrays, queues, trees, etc). You are allowed to use attributes, constructors, getters and setters.
     */
    public Node dequeue(int k) {
        int index = 1;
        Node prev = null;
        Node tmp = this.first;

        while (index < k) {
            prev = tmp;
            tmp = tmp.getNext();
            index++;
        }

        Node dequeued = tmp;

        if (prev == null) {
            this.first = this.first.getNext();
            if (this.first == null) {
                this.last = null;
            }
        } else if (tmp.getNext() == null) {
            prev.setNext(null);
            this.last = prev;
        } else {
            prev.setNext(tmp.getNext());
        }

        return dequeued;
    }

    /**
     * Write a function that creates and returns a new queue by removing even indexed elements from the original queue
     * and inserting into the newly created queue. The first node has index 1. You are not allowed to use any queue or
     * linked list methods, just attributes, constructors, getters and setters.
     */
    public Queue divideQueue() {
        Queue even_indexed = new Queue();
        int index = 1;
        Node prev = null;
        Node tmp = this.first;

        while (tmp != null) {
            if (index % 2 == 0) {
                Node next = tmp.getNext();
                tmp.setNext(null);

                if (even_indexed.first == null) {
                    even_indexed.first = tmp;
                } else {
                    even_indexed.last.setNext(tmp);
                }
                even_indexed.last = tmp;

                prev.setNext(next);

                if (prev.getNext() == null) {
                    this.last = prev;
                }

                tmp = prev.getNext();
            } else {
                prev = tmp;
                tmp = tmp.getNext();
            }
            index++;
        }

        return even_indexed;
    }

    /**
     * Write a method which constructs an array of list based queues by dividing the original queue into k equal parts.
     * The first, second, $\ldots$, $k$'th element of the original queue will be the first element of the first, second,
     * $\ldots$, $k$'th output queues, etc. The elements of the output queues should be recreated (not copied from the
     * original queue). You are not allowed to use enqueue, dequeue, isEmpty functions.
     */
    public Queue[] divideQueue(int k) {
        Queue[] queue_list = new Queue[k];

        for (int i = 0; i < k; i++) {
            queue_list[i] = new Queue();
        }

        Node tmp = this.first;
        int index = 1;

        while (tmp != null) {
            int list_index = (((index % k) - 1 + k) % k);
            Node new_node = new Node(tmp.getData());

            Queue curr_queue = queue_list[list_index];

            if (curr_queue.first == null) {
                curr_queue.first = new_node;
            } else {
                curr_queue.last.setNext(new_node);
            }
            curr_queue.last = new_node;

            tmp = tmp.getNext();
            index++;
        }

        return queue_list;
    }

    /**
     * Write a method where the method returns the minimum number in a queue. Do not use any class or external methods
     * except isEmpty().
     */
    public int minimum() {
        Node tmp = this.first;
        int min = this.first.getData();

        while (tmp != null) {
            int curr = tmp.getData();
            if (curr < min) {
                min = curr;
            }
            tmp = tmp.getNext();
        }
        return min;
    }

    /**
     * Write a method that returns the maximum number in a queue.
     */
    public int maximum() {
        Node tmp = this.first;
        int max = this.first.getData();

        while (tmp != null) {
            int curr = tmp.getData();
            if (curr > max) {
                max = curr;
            }
            tmp = tmp.getNext();
        }
        return max;
    }

    /**
     * Write a method which removes all elements in the queues in the $list$ from the original queue. You are not
     * allowed to use enqueue, dequeue, isEmpty functions.
     */
    public void removeAll(Queue[] list) {
        for (Queue q : list) {
            Node tmp2 = q.first;

            while (tmp2 != null) {
                Node prev = null;
                Node tmp = this.first;

                while (tmp != null) {
                    if (tmp2.getData() == tmp.getData()) {
                        Node next = tmp.getNext();

                        if (prev != null) {
                            prev.setNext(next);
                        } else {
                            this.first = next;
                        }

                        if (next == null) {
                            this.last = prev;
                        }

                        if (this.first == null) {
                            this.last = null;
                        }

                        break;
                    }
                    prev = tmp;
                    tmp = tmp.getNext();
                }
                tmp2 = tmp2.getNext();
            }
        }
    }

    /**
     * Write a method given a queue that is implemented as a linked list. The method should reverse the order of
     * elements in the queue without using the queue’s enqueue(), dequeue(), or peek() methods. You must directly
     * manipulate the underlying linked list of the queue to achieve the reversal.
     */
    public void reverseQueue() {
        Node head = this.first;
        Node tail = this.last;

        Node prev = null;
        Node tmp = this.first;
        Node next = tmp.getNext();

        while (tmp != null) {
            tmp.setNext(prev);

            prev = tmp;
            tmp = next;

            if (tmp != null) {
                next = tmp.getNext();
            }
        }

        this.first = tail;
        this.last = head;


    }

    /**
     * Write a method which simulates how the queue goes in an hypothetical country, which we all aware of.
     * {\em indexOfNonbribers} is an increasing order sorted array showing the indexes of the people who have not bribed
     * the officer in the queue. The method will send these people to the end of the queue in their respective order.
     * The first element in the queue has the index 0.
     */
    public void thisMustChange(int[] indexOfNonbribers) {
        Queue nonbribers = new Queue();
        Node tmp = this.first;
        Node prev = null;
        int i = 0;

        for (int index : indexOfNonbribers) {

            while (tmp != null) {
                if (i == index) {

                    Node next = tmp.getNext();
                    if (prev != null) {
                        prev.setNext(next);
                    } else {
                        this.first = next;
                    }

                    if (this.first == null) {
                        this.last = null;
                    }

                    if (next == null) {
                        this.last = prev;
                    }

                    tmp.setNext(null);
                    nonbribers.enqueue(tmp);
                    tmp = next;
                    i++;
                    break;

                } else {
                    prev = tmp;
                    tmp = tmp.getNext();
                    i++;
                }
            }
        }

        tmp = nonbribers.first;

        while (tmp != null) {
            Node next = tmp.getNext();
            this.enqueue(tmp);
            tmp = next;
        }

    }

    /**
     * Write a method that returns the second maximum number in a queue. Write the method for the
     * linked list implementation.
     */
    public int secondMaximum() {
        int max = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;
        Node tmp = this.first;

        while (tmp != null) {
            if (tmp.getData() >= max) {
                secondMax = max;
                max = tmp.getData();
            } else if (tmp.getData() > secondMax) {
                secondMax = tmp.getData();
            }
            tmp = tmp.getNext();
        }
        return secondMax;
    }
}
