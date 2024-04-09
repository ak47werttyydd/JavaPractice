package LinkedQueue;

public class _LinkedNode {
    private int data;
    private _LinkedNode nextNode;
    public _LinkedNode(int inputData){
        this.data=inputData;
        this.nextNode=null;
    }
    public _LinkedNode(){
        this.data=0;
        this.nextNode=null;
    }

    public int getData(){
        return data;
    }

    public void setData(int inputData){
        data=inputData;
    }

    public void setNextNode(_LinkedNode nextNode){
        this.nextNode=nextNode;
    }

    public _LinkedNode getNextNode(){
        return nextNode;
    }
}
