package Graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Graph { // non-direction graph
    private int count_vertex; // number of vertex
    private LinkedList<Integer>[] adj_list; // adjacency list
    private int final_tgt_vertex = -1;

    public Graph(int count_vertex) {
        this.count_vertex = count_vertex;
        adj_list = new LinkedList[count_vertex];
        for (int i=0; i<count_vertex; ++i) {
            adj_list[i] = new LinkedList<>();
        }
    }

    public void addEdge(int s, int t) { // s<->t & t<->s
        adj_list[s].add(t);
        adj_list[t].add(s);
    }

    public void bfs(int src_vertex, int tgt_vertex){
        if(src_vertex==tgt_vertex){
            System.out.println("The source vertex is the target vertex.");
            return;
        }
        //initialize final_tgt_vertex for print_recursive()
               final_tgt_vertex = tgt_vertex;
        //initialize the visited array, reached queue, prev array
        boolean[] visited = new boolean[count_vertex];  //array of visited vertex
        visited[src_vertex] = true; //mark the start vertex as visited
        Queue<Integer> reached = new LinkedList<>(); //queue of reached vertex. enqueue when visited the vertex for the first time, dequeue when visited all the adjacent vertex
        reached.add(src_vertex); //enqueue the start vertex
        int[] prev = new int[count_vertex]; //record the previous vertex which arrive the current vertex for the first time
        for(int i=0; i<count_vertex; ++i){
            prev[i] = -1;
        }
        //bfs
        while(!reached.isEmpty()){ //while the queue is not empty or the target vertex is not found
            int cur_vertex= reached.poll(); //dequeue the vertex
            for(int i=0;i<adj_list[cur_vertex].size();++i){ //scan all the adjacent vertex of the current vertex
                int adj_vertex = adj_list[cur_vertex].get(i);
                if(!visited[adj_vertex]){  //the adjacent vertex is not visited,
                    prev[adj_vertex] = cur_vertex;
                    if(adj_vertex==tgt_vertex){  //found the target vertex
                        print_recursive(prev,src_vertex,tgt_vertex);
                        return;
                    }
                    visited[adj_vertex] = true; //find a new vertex
                    reached.add(adj_vertex); //enqueue the new vertex
                }
            }
        }
        //if the target vertex is not found
        System.out.println("No path found from "+src_vertex+" to "+tgt_vertex);
    }

    public class BooleanWrapper{
        private Boolean value;
        //ctor
        public BooleanWrapper(Boolean value){
            this.value = value;
        }
    }

    public void dfs(int src_vertex, int tgt_vertex){
        //initialize the visited array, prev array (no queue)
        BooleanWrapper found = new BooleanWrapper(Boolean.FALSE); //flag of whether the target vertex is found.
        boolean[] visited = new boolean[count_vertex];  //array of visited vertex, mark the visited[vertex] as true when the vertex is visited for the first time
        visited[src_vertex] = true; //mark the start vertex as visited
        int[] prev = new int[count_vertex]; //record the previous vertex which arrive the current vertex for the first time
        for(int i=0; i<count_vertex; ++i){
            prev[i] = -1;
        }
        //dfs
        __recursive_dfs(found,src_vertex,tgt_vertex,visited,prev); //pass found as reference by Boolean object
        if(!found.value){
            System.out.println("No path found from "+src_vertex+" to "+tgt_vertex);
        }else{
            print_recursive(prev,src_vertex,tgt_vertex);
        }
    }

    public void __recursive_dfs(BooleanWrapper found, int src_vertex, int tgt_vertex, boolean[] visited, int[] prev){
        if(found.value)return;
        if(src_vertex==tgt_vertex){
            found.value = Boolean.TRUE;
            return;
        }
        for(int i=0;i<adj_list[src_vertex].size();++i){ //scan all the adjacent vertex of the current vertex
            int adj_vertex = adj_list[src_vertex].get(i);
            if(!visited[adj_vertex]){  //the adjacent vertex is not visited,
                prev[adj_vertex] = src_vertex;
                visited[adj_vertex] = true; //mark the current vertex as visited
                __recursive_dfs(found,adj_vertex,tgt_vertex,visited,prev);
            }
        }


    }

    public void print(int[] prev, int src_vertex, int tgt_vertex){
       int cur_vertex = tgt_vertex;
       ArrayList<Integer> path = new ArrayList<>();
       path.add(cur_vertex);
       while(cur_vertex!=-1){
              cur_vertex = prev[cur_vertex];
              if(cur_vertex!=-1)path.add(cur_vertex);
       }
       Collections.reverse(path); //reverse the path to be forwards
       System.out.println("The path from "+src_vertex+" to "+tgt_vertex+" is: ");
       for(int i=0;i<path.size();++i){
            System.out.print(path.get(i));
            if(i!=path.size()-1){
                System.out.print("->");
            }
       }
    }

    //recursion can be controlled by global variable final_tgt_vertex
    public void print_recursive(int[] prev, int src_vertex, int tgt_vertex){
        if(prev[tgt_vertex]!=-1 && tgt_vertex!=src_vertex){
            print_recursive(prev,src_vertex,prev[tgt_vertex]);
        }
//        System.out.print(tgt_vertex+"->");
        System.out.print(tgt_vertex);
        if(final_tgt_vertex!=tgt_vertex){
            System.out.print("->");
        }
        else{
            System.out.println();
        }
    }


    public static void main(String[] args) {
        Graph g = new Graph(8);
        g.addEdge(0, 1);
        g.addEdge(0, 3);
        g.addEdge(1, 2);
        g.addEdge(1, 4);
        g.addEdge(3, 4);
        g.addEdge(4, 5);
        g.addEdge(2,5);
        g.addEdge(4,6);
        g.addEdge(5,7);
        g.addEdge(6,7);
//       g.bfs(0, 7);
        g.dfs(0,7);
    }
}

