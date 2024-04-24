package SkipList;

public class LinkedNode {
    public int val; //value
    public LinkedNode next;
    public LinkedNode down;
    public LinkedNode(int val){
        this.val = val;
        this.next = null;
        this.down = null;
    }
}
