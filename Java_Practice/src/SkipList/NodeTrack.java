package SkipList;

//NodeTrack is a class that stores a node and its tracks
public class NodeTrack {
    public LinkedNode node;  //node in data layer
    public LinkedNode[] track;  //track[i] is the immediate predecessor of the node， which is at level i+1
}
