package Array;

import List.LinkedList;
import List.Node;

public class DisjointSet {
    private Set[] sets;
    private int count;

    public DisjointSet(int count){
        sets = new Set[count];
        for (int i = 0; i < count; i++){
            sets[i] = new Set(i, i);
        }
        this.count = count;
    }

    public DisjointSet(int[] elements, int count){
        sets = new Set[count];
        for (int i = 0; i < count; i++){
            sets[i] = new Set(elements[i], i);
        }
        this.count = count;
    }

    public void union(int index1, int index2){
        int x = findSetIterative(index1);
        int y = findSetIterative(index2);
        if (sets[x].getDepth() < sets[y].getDepth()){
            sets[x].setParent(y);
        } else {
            sets[y].setParent(x);
            if (sets[x].getDepth() == sets[y].getDepth()){
                sets[x].incrementDepth();
            }
        }
    }

    public int findSetRecursive(int index){
        int parent = sets[index].getParent();
        if (parent != index){
            return findSetRecursive(parent);
        }
        return parent;
    }

    public int findSetIterative(int index){
        int parent = sets[index].getParent();
        while (parent != index){
            index = parent;
            parent = sets[index].getParent();
        }
        return parent;
    }

    public int setCount(){
        int total = 0;
        for (int i = 0; i < count; i++){
            if (findSetRecursive(i) == i){
                total++;
            }
        }
        return total;
    }

    /**
     * Given in the index of a set as {\em current}, write a recursive method that returns the ascendants (including also
     * current) of that set as an array. You are not allowed to use any class method except getParent.
     */
    public int[] ascendants1(int current){
        int parent = sets[current].getParent();

        if(parent == current){
            int[] root = new int[1];
            root[0] = parent;
            return root;
        }

        int[] ascendants = ascendants1(parent);

        int[] fullAscendants = new int[ascendants.length + 1];

        fullAscendants[0] = current;

        for(int i = 0; i < ascendants.length; i++){
            fullAscendants[i+1] = ascendants[i];
        }

        return fullAscendants;
    }

    /**
     * Given the index of a set, write a non-recursive function that returns the ancestors (itself, parent, grandparent,
     * etc.). The size of the returning array should be as much as needed.
     */
    public int[] ascendants2(int index){
        int depth = 1;

        int curr = index;

        while(curr != this.sets[curr].getParent()){
            curr = this.sets[curr].getParent();
            depth++;
        }

        int[] ascendants = new int[depth];

        curr = index;

        for(int i = 0; i < ascendants.length; i++) {
            ascendants[i] = curr;
            curr = this.sets[curr].getParent();
        }


        return ascendants;
    }

    /**
     * You are given a set of equalities such as
     * 0=9
     * 1=2
     * 3=5
     * 5=7
     * 9=4
     * 5=4
     * 6=8
     * where numbers correspond to variables. When the equalities are combined, we get
     * 0=9=4=3=5=7
     * 1=2
     * 6=8
     * 3 equalities. Write the function that finds the number of equalities when combined where $N$ represents the number
     * of variables, left and right represent the left and right parts of the equalities.
     */
    public static int combine(int N, int[] left, int[] right){
        DisjointSet disjointSet = new DisjointSet(N);

        for(int i = 0; i < left.length; i++){
            disjointSet.union(left[i], right[i]);
        }

        return disjointSet.setCount();
    }

    /**
     * Given in the index of a set as {\em current}, write a recursive method that returns the descendants of that set to
     * the array list.
     */
    public int[] descendants1(int current){

        int descendant_count = 0;

        for(int i = 0; i < this.count; i++){
            if(this.sets[i].getParent() == current && i != current){
                descendant_count += 1 + descendants1(i).length;
            }
        }

        int[] descendants = new int[descendant_count];

        int pos = 0;

        for(int i = 0; i < this.count; i++){
            if(this.sets[i].getParent() == current && i != current){
                descendants[pos] = i;
                pos++;

                int[] childDescendants = descendants1(i);

                for(int j = 0; j < childDescendants.length; j++){
                    descendants[pos] = childDescendants[j];
                    pos++;
                }
            }
        }

        return descendants;
    }

    /**
     * Given in the index of a set as {\em current}, write a non-recursive method that returns the descendants of that set to
     * the array list.
     */
    public int[] descendants2(int current){

        int[] queue = new int[this.count];
        int head = 0;
        int tail = 1;
        queue[0] = current;
        int descCount = 0;

        while(head < tail){
            int curr = queue[head];
            head++;

            for(int i = 0; i < this.count; i++){
                if(this.sets[i].getParent() == curr && i != curr){
                    queue[tail] = i;
                    tail++;
                    descCount++;
                }
            }
        }

        int[] descendants = new int[descCount];

        for(int i = 0; i < descendants.length;i++){
            descendants[i] = queue[i + 1];
        }

        return descendants;
    }

    /**
     * Write the method which returns the indexes of all sets in the disjoint set where a set with index $index$ is in
     * that set.
     */
    public int[] getSetWithIndex(int index){

        int tmp = index;
        while(tmp != this.sets[tmp].getParent()){
            tmp = this.sets[tmp].getParent();
        }

        int[] queue = new int[this.count];
        queue[0] = tmp;
        int head = 0;
        int tail = 1;

        while(head < tail){
            int curr = queue[head];
            head++;

            for(int i = 0; i < this.count; i++){
                if(this.sets[i].getParent() == curr && i != curr){
                    queue[tail] = i;
                    tail++;
                }
            }
        }

        int[] allSets = new int[tail];

        for(int i = 0; i < allSets.length; i++){
            allSets[i] = queue[i];
        }



        return allSets;
    }

    /**
     * Given the index of a set, write a method that returns the indexes of its grandchildren as a linked list. Do not
     * use any class or external methods.
     */
    public LinkedList grandChildren(int index){
        LinkedList grandChildren = new LinkedList();

        for(int i = 0; i < this.count; i++){
            if(this.sets[i].getParent() == index && i != index){
                for(int j = 0; j < this.count; j++){
                    if(this.sets[j].getParent() == i && j != i){
                        grandChildren.insertLast(new Node(j));
                    }
                }
            }
        }


        return grandChildren;
    }

    /**
     * Write a method that returns true when the given disjoint set is valid, that is from every node $n$, when the
     * ascendants are traversed, no circularity is observed (that is you do not encounter the node $n$ again).
     */
    public boolean isValid(){

        for(int i = 0; i < this.count; i++){
            int parent = this.sets[i].getParent();
            while(parent != this.sets[parent].getParent()){
                parent = this.sets[parent].getParent();
                if(parent == i){
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Write the method in {\bf DisjointSet} class that calculates the number of triplet disjoint sets in a disjoint set
     * structure. A disjoint set is a triplet, if the number of sets in that disjoint set is 3. Do not use any class or
     * external methods.
     */
    public int numberOfTriplets(){
        int[] disjointSets = new int[count];
        int head = 0;
        int tail = 0;

        for(int i = 0; i < this.count; i++){
            if(this.sets[i].getParent() == i){
                disjointSets[tail] = i;
                tail++;
            }
        }

        int numberOfTriples = 0;

        for(int i = 0; i < tail; i++){
            int curr = disjointSets[i];

            int[] fullSet = new int[this.count];
            int head2 = 0;
            int tail2 = 1;
            fullSet[0] = curr;

             while(head2 < tail2){
                 int curr2 = fullSet[head2];
                 head2++;

                 for(int j = 0; j < this.count; j++){
                     if(this.sets[j].getParent() == curr2 && j != curr2){
                         fullSet[tail2] = j;
                         tail2++;
                     }
                 }
             }

            if(tail2 == 3){
                numberOfTriples++;
            }
        }
        return numberOfTriples;
    }

    /**
     * In an unknown world, there are people (zombie) who bites other people (may or may not be zombie). One is converted
     * to a zombie, if he/she was bitten by some other zombie. Given who bites whom in two arrays, identify the number of
     * survivors, that is the number of people who hasn't been bitten yet. Write the static method where whoBites[i] has
     * bitten whoWasBitten[i] correspondingly. Count represents the number of people in the beginning. Size represents
     * the size of the arrays whoBites and whoWasBitten. You are only allowed to use one external Disjoint Set.
     */
    public static int numberOfSurvivors(int count, int[] whoBites, int[] whoWasBitten){
        DisjointSet disjointSet = new DisjointSet(count);

        for(int i = 0; i < whoBites.length; i++){
            disjointSet.union(whoBites[i], whoWasBitten[i]);
        }

        boolean[] isInfected = new boolean[count];

        for(int i = 0; i < whoBites.length; i++){
            int infectedSetIndex = disjointSet.findSetIterative(whoBites[i]);
            isInfected[infectedSetIndex] = true;
        }

        int survivors = 0;

        for(int i = 0; i < disjointSet.count; i++){
            int setIndex = disjointSet.findSetIterative(i);
            if(!isInfected[setIndex]){
                survivors++;
            }
        }

        return survivors;
    }

    /**
     * Write a function that merges three sets given their indexes. You can use {\em findSet} method, but not the
     * original {\em union} method. Merge the sets such that the resulting merged set will have the minimum depth.
     * Update also the depth if needed.
     */
    public void union2(int index1, int index2, int index3){
        int x = findSetIterative(index1);
        int y = findSetIterative(index2);
        int z = findSetIterative(index3);
        boolean isGreater = true;

        if(this.sets[x].getDepth() > this.sets[y].getDepth()){
            this.sets[y].setParent(x);
        }
        else if(this.sets[x].getDepth() == this.sets[y].getDepth()){
            this.sets[y].setParent(x);
            this.sets[x].incrementDepth();
        }
        else{
            this.sets[x].setParent(y);
            isGreater = false;
        }

       int union1 = x;
       if(!isGreater){
           union1 = y;
       }

        if(this.sets[union1].getDepth() > this.sets[z].getDepth()){
            this.sets[z].setParent(union1);
        }
        else if(this.sets[union1].getDepth() == this.sets[z].getDepth()){
            this.sets[z].setParent(union1);
            this.sets[union1].incrementDepth();
        }
        else{
            this.sets[union1].setParent(z);
        }
    }

    /**
     * Write the method that merges $N$ sets given their indexes in the indexList. You should use findSet and the
     * original unionOfSets method. Merge the sets such that the resulting merged set will have the minimum depth. Use
     * an algorithm that sorts the sets according to their depths.
     */
    public void unionOfSets(int[] indexList){
        int[] setIndexes = new int[indexList.length];

        for(int i = 0; i < indexList.length; i++){
            setIndexes[i] = this.findSetIterative(indexList[i]);
        }

        int[] sortedSetIndexes = new int[setIndexes.length];

        int pos = 0;

        while(pos < setIndexes.length){
            int max = setIndexes[0];
            for(int i = 0; i < setIndexes.length; i++){
                if(this.sets[setIndexes[i]].getDepth() > this.sets[setIndexes[max]].getDepth()){
                    max = setIndexes[i];
                }
            }
        }
    }

    /**
     * Given the index of a set $S$, write a method that unmerges (creates disjoint sets of) it. After unmerging, the
     * direct children of $S$ and $S$ itself will be disjoint sets themselves. You don't need to modify the depths. Do
     * not use any class or external methods.
     */
    public void unmerge(int index){

        for(int i = 0; i < count; i++){
            if(this.sets[i].getParent() == index){
                this.sets[i].setParent(i);
            }
        }
    }

    /**
     * Given the index of a set, write a recursive function that finds the value of that set. The value of a node is 1 +
     * maximum value of its children. Any node with no children has value 1. You are not allowed to use any class method
     * except getParent.
     */
    public int value(int index){
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < this.count; i++){
            if(this.sets[i].getParent() == index && i != index){
                int childValue = value(i);
                if(childValue > max){
                    max = childValue;
                }
            }
        }

        if(max == Integer.MIN_VALUE){
            return Math.max(1, index);
        }

        return 1 + max;
    }

    /**
     Write the method in {\bf DisjointSet} class which returns the number of sets that have same height as the set with given
     index. The height of an element is calculated by the number of nodes visited when we traverse the tree starting from that set and continue
     through the parent links until the top. Do not use any class or external methods.
     */
    public int sameHeightSets(int index) {
        int curr = index;
        int parent;
        int height = 1;

        while(true){
            parent = this.sets[curr].getParent();

            if(curr == parent){
                break;
            }
            height++;
            curr = parent;
        }

        int same_height = 0;

        for(int i = 0; i < this.count; i++){
            curr = i;
            parent = this.sets[curr].getParent();
            int curr_height = 1;

            while(curr != parent){
                curr = parent;
                parent = this.sets[parent].getParent();
                curr_height++;
            }

            if(curr_height == height){
                same_height++;
            }
        }

        return same_height;
    }

    /**
     Write the method in {\bf DisjointSet} class which returns count of sets where direct children's~(not including grandchildren) total data value is equal
     to set's parent's data value. Root node's parent is itself and a leaf node does have any children. Do not use any class or external
     methods.
     */
    public int childrenParentEqual() {
        int childParentEqualSetCount = 0;
        for(int i = 0; i < this.count; i++){
            int total = 0;
            int parent_index = this.sets[i].getParent();
            int parent_data = this.sets[parent_index].getData();

            for(int j = 0; j < this.count; j++){
                if(this.sets[j].getParent() == i && i != j){
                    total += this.sets[j].getData();
                }
            }

            if(parent_data == total){
                childParentEqualSetCount++;
            }
        }

        return childParentEqualSetCount;
    }
}
