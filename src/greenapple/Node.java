package greenapple;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Node implements Serializable {
private ArrayList <Comparable> keys;
private ArrayList <Object> pointers;
private int maximumKeys;
private int minimumKeys;
private Node leftSibling;
private Node rightSibling;
private Node parent;
public ArrayList<Comparable> getKeys(){
	return keys;
}
public ArrayList<Object> getPointers(){
	return pointers;
}
public Node(){
	keys=new ArrayList<Comparable>();
	pointers=new ArrayList<Object>();
	
}
public abstract int search(Comparable c);
public int getMinimumKeys() {
	return minimumKeys;
}
public int binarySearch(Comparable c){
	int min=0;
	int max=keys.size()-1;
	
	while(min<=max){
		int middle =(int) ((max+min)/2);
		if((keys.get(middle)).compareTo(c)==0){
			return middle;
		}else{
			if((keys.get(middle)).compareTo(c)<0){
				min=middle+1;
			}else{
				max=middle-1;
			}
		}
		
	}
	return -1;
}
public void setMinimumKeys(int minimumKeys) {
	this.minimumKeys = minimumKeys;
}
public int getMaximumKeys() {
	return maximumKeys;
}
public void setMaximumKeys(int maximumKeys) {
	this.maximumKeys = maximumKeys;
}
public Node getLeftSibling() {
	return leftSibling;
}
public void setLeftSibling(Node leftSibling) {
	this.leftSibling = leftSibling;
}
public Node getRightSibling() {
	return rightSibling;
}
public void setRightSibling(Node rightSibling) {
	this.rightSibling = rightSibling;
}
public Node getParent() {
	return parent;
}
public void setParent(Node parent) {
	this.parent = parent;
}
public String toString(){
	String result1="";
	
	for(Comparable key :getKeys()){
		result1+=key.toString()+" ";
	}
	
	return result1+"#";
	
}
}