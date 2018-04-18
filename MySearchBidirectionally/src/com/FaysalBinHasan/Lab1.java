package com.FaysalBinHasan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Lab1 {

	int[][] pathTable;
	int[] parentTable;

	List<Node> nodeList;

	public static void main(String[] args) throws FileNotFoundException {
		new Lab1().start();
	}

	private void start() throws FileNotFoundException {
		Scanner input = new Scanner(new File("E:\\Eclipse Files\\MySearchBidirectionally\\input"));
		nodeList = new ArrayList<Node>();
		int totalNode = input.nextInt();
		int totalPath = input.nextInt();

		String startPoint = input.next();
		String destPoint = input.next();

		pathTable = new int[totalNode][totalNode];
		parentTable = new int[totalNode];

		for (int i = 0; i < totalPath; i++) {
			String from = input.next();
			String to = input.next();
			int pos1 = insertNode(from);
			int pos2 = insertNode(to);
			pathTable[pos1][pos2] = 1;
			pathTable[pos2][pos1] = 1;
		}

		// printNodeList(nodeList);
		// printTable(pathTable);
		// if(true)return;
		int sourceId = getNodeByName(startPoint).getId();
		int destId = getNodeByName(destPoint).getId();
		Queue<Integer> childQueu = new LinkedList<Integer>();
		childQueu.add(sourceId);
		ArrayList<Integer> visitedNodes = new ArrayList<>();

		boolean found = false;
		while (!childQueu.isEmpty() && !found) {
			int currentId = childQueu.poll();
			// System.out.println("Current Id -"+getNodeById(currentId).getName());
			if (visitedNodes.contains(currentId))
				continue;
			for (int i = 0; i < pathTable.length; i++) {
				if (pathTable[currentId][i] == 1 && !visitedNodes.contains(i)) {
					// System.out.println(getNodeById(i).getName());
					parentTable[i] = currentId;
					if (i == destId) {
						found = true;
						break;
					}
					childQueu.add(i);
				}
			}
			visitedNodes.add(currentId);
		}

		printPath(sourceId, destId);
	}

	private void printPath(int sourceId, int destId) {
		StringBuilder path = new StringBuilder();
		int currentId = destId;
		while (true) {
			path.insert(0, getNodeById(currentId).getName());
			if (currentId == sourceId)
				break;
			else
				path.insert(0, "->");
			currentId = parentTable[currentId];
		}
		System.out.println(path.toString());
		/*
		 * for (int i = 0; i < parentTable.length; i++) {
		 * System.out.println(getNodeById(i).getName()+"-Parent "+getNodeById(
		 * parentTable[i]).getName()); }
		 */
	}

	private void printNodeList(List<Node> nodeList) {
		for (Node node : nodeList) {
			System.out.println(node.getId() + " " + node.getName());
		}
	}

	private Node getNodeByName(String name) {
		for (Node node : nodeList) {
			if (node.getName().equals(name)) {
				return node;
			}
		}
		return null;
	}

	private Node getNodeById(int id) {
		for (Node node : nodeList) {
			if (node.getId() == id) {
				return node;
			}
		}
		return null;
	}

	private void printTable(int[][] pathTable) {
		for (int[] is : pathTable) {
			for (int i : is) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
	}

	private int insertNode(String nodeName) {
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.get(i).getName().equals(nodeName)) {
				return nodeList.get(i).getId();
			}
		}
		Node node = new Node(nodeList.size(), nodeName);
		nodeList.add(node);
		return node.getId();
	}

	class Node {
		private int id;
		private String name;
		private boolean visitedSource;
		private boolean visitedDest;
		private Node parent;

		public Node(int id, String name) {
			this.name = name;
			this.id = id;
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
