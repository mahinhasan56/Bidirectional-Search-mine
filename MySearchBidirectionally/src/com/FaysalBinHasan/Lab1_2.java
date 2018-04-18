package com.FaysalBinHasan;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;


public class Lab1_2 {

	int[][] pathTable;

	List<Node> nodeList; 

	public static void main(String[] args) throws FileNotFoundException {
		new Lab1_2().start();
	}

	private void start() throws FileNotFoundException {
		Scanner input=new Scanner(new File("E:\\Eclipse Files\\MySearchBidirectionally\\input"));
		nodeList=new ArrayList<Node>();
		int totalNode=input.nextInt();
		int totalPath=input.nextInt();

		String startPoint=input.next();
		String destPoint=input.next();

		pathTable=new int[totalNode][totalNode];
		int[] srcParentTable=new int[totalNode];
		int[] destParentTable=new int[totalNode];

		for (int i = 0; i < totalPath; i++) {
			String from=input.next();
			String to=input.next();
			int pos1=insertNode(from);
			int pos2=insertNode(to);
			pathTable[pos1][pos2]=1;
			pathTable[pos2][pos1]=1;
		}

		//printNodeList(nodeList);
		//printTable(pathTable);
		//if(true)return;
		int sourceId=getNodeByName(startPoint).getId();
		int destId=getNodeByName(destPoint).getId();
		Queue<Integer> sourceChildQueu=new LinkedList<Integer>();
		Queue<Integer> destChildQueu=new LinkedList<Integer>();

		sourceChildQueu.add(sourceId);
		destChildQueu.add(destId);
		ArrayList<Integer> visitedSrcNodes=new ArrayList<>();
		ArrayList<Integer> visitedDestNodes=new ArrayList<>();

		boolean found=false;
		while ((!sourceChildQueu.isEmpty() || !destChildQueu.isEmpty())  && !found) {
			int currentSrcId=sourceChildQueu.poll();
			int currentDestId=destChildQueu.poll();
			System.out.println("Current Src Id -"+getNodeById(currentSrcId).getName());
			System.out.println("Current Dest Id -"+getNodeById(currentDestId).getName());

			if(!visitedSrcNodes.contains(currentSrcId)) {
				for (int i = 0; i < pathTable.length; i++) {
					if(pathTable[currentSrcId][i]==1 && !visitedSrcNodes.contains(i)){
						//System.out.println(getNodeById(i).getName());
						srcParentTable[i]=currentSrcId;
						if(i==destId){
							found=true;
							break;
						}
						sourceChildQueu.add(i);
					}
				}
				visitedSrcNodes.add(currentSrcId);
			}
			if(visitedSrcNodes.contains(currentDestId) || sourceChildQueu.contains(currentDestId)){
				System.out.println("Overlaping node "+getNodeById(currentDestId).getName());

				printPath(srcParentTable,sourceId,currentDestId);
				printPath(destParentTable,destId,currentDestId);
				
				break;
			}
			if(!visitedDestNodes.contains(currentDestId)) {
				for (int i = 0; i < pathTable.length; i++) {
					if(pathTable[currentDestId][i]==1 && !visitedDestNodes.contains(i)){
						//System.out.println(getNodeById(i).getName());
						destParentTable[i]=currentDestId;
						if(i==sourceId){
							found=true;
							break;
						}
						destChildQueu.add(i);
					}
				}
				visitedDestNodes.add(currentDestId);
			}
		}

	}

	private void printPath(int[] table,int sourceId, int destId) {
		StringBuilder path=new StringBuilder();
		int currentId=destId;
		while(true){
			path.insert(0,getNodeById(currentId).getName());
			if(currentId==sourceId) break;
			else path.insert(0, "->");
			currentId=table[currentId];
		}
		System.out.println(path.toString());
		/*		for (int i = 0; i < parentTable.length; i++) {
			System.out.println(getNodeById(i).getName()+"-Parent "+getNodeById(parentTable[i]).getName());
		}*/
	}

	private void printNodeList(List<Node> nodeList) {
		for (Node node : nodeList) {
			System.out.println(node.getId()+" "+node.getName());
		}
	}

	private Node getNodeByName(String name){
		for (Node node : nodeList) {
			if(node.getName().equals(name)){
				return node;
			}
		}
		return null;
	}

	private Node getNodeById(int id){
		for (Node node : nodeList) {
			if(node.getId()==id){
				return node;
			}
		}
		return null;
	}

	private void printTable(int[][] pathTable) {
		for (int[] is : pathTable) {
			for (int i : is) {
				System.out.print(i+" ");
			}
			System.out.println();
		}
	}

	private int insertNode(String nodeName) {
		for (int i = 0; i < nodeList.size(); i++) {
			if(nodeList.get(i).getName().equals(nodeName)){
				return nodeList.get(i).getId();
			}
		}
		Node node=new Node(nodeList.size(),nodeName);
		nodeList.add(node);
		return node.getId();
	}

	class Node{
		private int id;
		private String name;
		private boolean visitedSource;
		private boolean visitedDest;
		private Node parent;

		public Node(int id,String name) {
			this.name = name;
			this.id=id;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}
