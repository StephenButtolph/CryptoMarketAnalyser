package main;

import java.util.ArrayList;
import java.util.LinkedList;

import utils.IteratorUtils;

public class Main {
	public static void main(String[] args) {
		ArrayList<LinkedList<Integer>> stuff = new ArrayList<>();
		LinkedList<Integer> stack1 = new LinkedList<>();
		LinkedList<Integer> stack2 = new LinkedList<>();

		stuff.add(stack1);
		stuff.add(stack2);
		
		stack1.push(2);
		stack1.push(1);
		stack1.push(0);
		
		stack1.pop();

		stack2.push(4);
		stack2.push(3);
		
		for(Integer i : IteratorUtils.flatten(stuff)) {
			System.out.println(i);
		}
	}
}
