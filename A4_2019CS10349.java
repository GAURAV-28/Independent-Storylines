import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Map;


class Edge{
    String source;
    String target;
    int weight;

    public Edge(String source, String target, int weight){
        this.source = source;
        this.target = target;
        this.weight = weight;
    }
}

class Graph{
    HashMap<String, LinkedList<Edge>> adjList = new HashMap<>();
    HashMap<String, Integer> index = new HashMap<>();
    HashMap<String, Integer> total = new HashMap<>();
    ArrayList<ArrayList<String>> DFS_array = new ArrayList<>();

    public Graph(ArrayList<String> vertices){
        for(int i=0; i<vertices.size(); i++){
            String v = vertices.get(i);
            LinkedList<Edge> list = new LinkedList<>();
            adjList.put(v, list);
            index.put(v, i);
        }
    }

    public void addEdge(String source, String target, int weight){
        Edge ef = new Edge(source, target, weight);
        Edge eb = new Edge(target, source, weight);
        LinkedList<Edge> list_s = adjList.get(source);
        LinkedList<Edge> list_t = adjList.get(target);
        list_s.addFirst(ef);
        list_t.addFirst(eb);
        adjList.put(source, list_s);
        adjList.put(target, list_t);

    }
    public HashMap<String,Integer> occurance(){
        for(String source : adjList.keySet()){
            int tot = 0;
            LinkedList<Edge> list = this.adjList.get(source);
            for(Edge e : list){
                tot+=e.weight;
            }
            total.put(source, tot);
        }
        return total;
    }

    public ArrayList<ArrayList<String>> DFS(){
        int vertices = adjList.size();
        boolean [] visited = new boolean[vertices];
        

        for (Map.Entry<String, LinkedList<Edge>> entry : adjList.entrySet()) {
            String source = entry.getKey();
            if(!visited[index.get(source)]){
                //int c=0;
                ArrayList<String> comp = new ArrayList<>();
                DFSUtil(source, visited, comp);
                //DFS_map.put(c, comp);
                DFS_array.add(comp);
                //System.out.println("----------------");
            }
        }

    return DFS_array; 
        
    }

    public void DFSUtil(String source, boolean[] visited, ArrayList<String> comp){

        visited[index.get(source)]=true;
        //c++;
        comp.add(source);
        //System.out.println(source);
        LinkedList<Edge> list = adjList.get(source);
        for(int i = 0; i <list.size() ; i++) {
            Edge e = list.get(i);
            String target = e.target;
            if(!visited[index.get(target)]) DFSUtil(target,visited,comp);
        }
    }



}

public class A4_2019CS10349{
    public static void main(String[] args) throws Exception {
        ArrayList<String> vertices = new ArrayList<>();
        String ll;
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            while((ll=br.readLine()) != null){
                ArrayList<String> sp = customSplit(ll);
                vertices.add(sp.get(1).replace("\"", ""));
            }
            br.close();
        } catch (IOException e) {
           e.printStackTrace();
        }

        vertices.remove(vertices.get(0));
        Graph graph = new Graph(vertices);
        String k;
        int edge_count=0;
        try {
            BufferedReader brr = new BufferedReader(new FileReader(args[1]));
            while((k=brr.readLine()) != null){

                ArrayList<String> ed = customSplit(k);
                if(edge_count!=0){
                 
                    int we = Integer.parseInt(ed.get(2));
                    graph.addEdge(ed.get(0).replace("\"", ""), ed.get(1).replace("\"", ""), we);
                    
                }
                edge_count++;
            }
            brr.close();
        } catch (IOException e) {
           e.printStackTrace();
        }

        

        try{
        switch (args[2]) {
            case "average":
                average(edge_count-1,vertices.size());
                break;

            case "rank":
                HashMap<String,Integer> bb = graph.occurance();
                ArrayList<String> out = vertices;
                quicksort(out, 0, out.size()-1, bb);
                int i;
                if(out.size()!=0){
                    for(i=0;i<out.size()-1;i++) System.out.print(out.get(i)+",");
                    System.out.println(out.get(i));
                }

                break;

            case "independent_storylines_dfs":
                ArrayList<ArrayList<String>> arr = graph.DFS();
                ArrayList<ArrayList<String>> ans = new ArrayList<>();
                for(ArrayList<String> entry : arr){
                    sim_sort(entry,0,entry.size()-1);
                    ans.add(entry);
                }
                array_sort(ans,0,ans.size()-1);
                for(ArrayList<String> entry : ans){
                    for(i=0; i<entry.size()-1;i++) System.out.print(entry.get(i)+",");
                    System.out.println(entry.get(i));
                }
                break;
            default:
                //throw new Exception();
        }
    } catch(ArrayIndexOutOfBoundsException e){
        e.printStackTrace();
    }



    }

    private static void average(int e, int v){
        if(e>=0) {
            double ans = (double) 2*e/v;
            double round = (double) Math.round(ans*100.00)/100.00;
            System.out.println(String.format("%.2f", round));
        }
    }

    private static ArrayList<String> customSplit(String s){
        ArrayList<String> str = new ArrayList<String>();
        boolean InsideComma = false;
        int start=0;
        for(int i=0; i<s.length()-1; i++)
        {
            if(s.charAt(i)=='"') InsideComma=!InsideComma;

            if(s.charAt(i)==',' && !InsideComma){
                str.add(s.substring(start,i));
                start = i+1;                
            }   
        }
        str.add(s.substring(start));
        return str;
    }   

    private static void swap(ArrayList<String> elements, int i, int j){
        String temp= elements.get(i);
        elements.set(i, elements.get(j));
        elements.set(j, temp);
    }
    
    private static int partition1(ArrayList<String> elements, int beg, int end, HashMap<String,Integer> map){
        
        int random = end - (int) Math.random()*(end-beg);
        int last= random;
        swap(elements, random, end);
        end--;
        
        while(beg<=end){
            if(map.get(elements.get(beg))>map.get(elements.get(last))){
                beg++;
            } 
            else if(map.get(elements.get(beg))<map.get(elements.get(last))) {
                swap(elements, beg, end);
                end--;
            }
            else{
                if(elements.get(beg).compareTo(elements.get(last))>0){
                    beg++;
                }
                else{
                    swap(elements, beg, end);
                    end--;
                }
            }
        }
        swap(elements, beg, last);
        
        return beg;
    }
    
    private static void quicksort(ArrayList<String> elements, int beg, int end, HashMap<String,Integer> map){
        if(beg>=end) return;
        if(beg<0) return;
        if(end>elements.size()-1) return;
        
        int pivot = partition1(elements, beg, end, map);
        quicksort(elements, beg, pivot-1, map);
        quicksort(elements, pivot+1, end, map);
    }

    //--------------------------------------------------------------------------------------------------//
    private static int partition2(ArrayList<String> elements, int beg, int end){
        int random = end - (int) Math.random()*(end-beg);
        int last= random;
        swap(elements, random, end);
        end--;
        
        while(beg<=end){
            if(elements.get(beg).compareTo(elements.get(last))>0){
                beg++;
            }
            else{
                swap(elements, beg, end);
                end--;
            }
        }
        swap(elements, beg, last);
        return beg;
    }

    private static void sim_sort(ArrayList<String> elements, int beg, int end){
        if(beg>=end) return;
        if(beg<0) return;
        if(end>elements.size()-1) return;
        
        int pivot = partition2(elements, beg, end);
        //System.out.println("p"+pivot);
        sim_sort(elements, beg, pivot-1);
        sim_sort(elements, pivot+1, end);
    }
    //--------------------------------------------------------------------------------------------------//

    private static void listswap(ArrayList<ArrayList<String>> elements, int i, int j){
        //Method to swap 2 elements in an arraylist
        ArrayList<String> temp= elements.get(i);
        elements.set(i, elements.get(j));
        elements.set(j, temp);
    }

    private static int partition3(ArrayList<ArrayList<String>> elements, int beg, int end){
        int random = end - (int) Math.random()*(end-beg);
        int last= random;
        listswap(elements, random, end);
        end--;
        
        while(beg<=end){
            if(elements.get(beg).size()>elements.get(last).size()){
                beg++;
            }
            else if(elements.get(beg).size() < elements.get(last).size()){
                listswap(elements, beg, end);
                end--;
            }
            else{
                if(elements.get(beg).get(0).compareTo(elements.get(last).get(0))>0){
                    beg++;
                }
                else{
                    listswap(elements, beg, end);
                    end--;
                }
            }
        }
        listswap(elements, beg, last);
        return beg;
    }

    private static void array_sort(ArrayList<ArrayList<String>> elements, int beg, int end){
        if (elements.isEmpty()) return;
        if(beg>=end) return;
        if(beg<0) return;
        if(end>elements.size()-1) return;
        
        int pivot = partition3(elements, beg, end);
        array_sort(elements, beg, pivot-1);
        array_sort(elements, pivot+1, end);
    }
    

}