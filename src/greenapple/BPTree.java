package greenapple;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;

public class BPTree implements Serializable{
	Node root;
	int n;

	public BPTree() throws IOException {
		Properties prop = new Properties();
		InputStream inputStream = new FileInputStream("config/DBApp.properties");
		prop.load(inputStream);
		n=Integer.parseInt(prop.getProperty("BPlusTreeN"));
		root = new LeafNode(n);
	}

	public Object search(Comparable c) {
		return searchHelper(root, c);
	}
	public void update(Comparable old, Comparable news){
		Pair p  = (Pair) search(old);
		remove(old);
		insert(news,p);
		
	}

	
	private Object searchHelper(Node root2, Comparable c) {
		if (root2 instanceof LeafNode) {
			return ((LeafNode) root2).searchleaf(c);
		} else {
			Node pointer = (Node) root2.getPointers().get(root2.search(c));
			return searchHelper(pointer, c);
		}
	}
	public void insert(Comparable c, Pair p){
		insertHelper(root, c, p);
	}
	public void insertHelper(Node root2, Comparable c, Pair p) {
		
		if (root2 instanceof LeafNode) {
			if (!((LeafNode) root2).insert(c, p)) {
				LeafNode right = new LeafNode(n);
				LeafNode left = new LeafNode(n);
				int ceil = root2.getMinimumKeys();
				int i = 0;
				boolean inserted = false;
				
				for (; i < root2.getKeys().size(); i++) {
					if(root2.getKeys().get(i).compareTo(c)>0){
						break;
					}
					
				}
				root2.getKeys().add(i, c);
				root2.getPointers().add(i,p);
				
				for(int j =0;j<ceil;j++){
					right.insert(root2.getKeys().get(root2.getKeys().size()-1),root2.getPointers().get(root2.getPointers().size()-2));
					root2.getPointers().remove(root2.getPointers().size()-2);
					root2.getKeys().remove(root2.getKeys().size()-1);
				}
				
				left=(LeafNode) root2;
				Comparable min = goLeft(right);
				right.setRightSibling(root2.getRightSibling());
				left.setRightSibling(right);
				left.setLeftSibling(root2.getLeftSibling());
				right.setLeftSibling(left);
				if(right.getRightSibling()!=null)
				right.getRightSibling().setLeftSibling(right);
				if(left.getLeftSibling()!=null)
					left.getLeftSibling().setRightSibling(left);
					
				left.setParent(root2.getParent());
				right.setParent(root2.getParent());
				if (right.getParent() == null) {
					InnerNode parent = new InnerNode(n);
					root = parent;
					parent.insert(right.getKeys().get(0), left, right);
					left.setParent(parent);
					right.setParent(parent);
				} else {
					
					insertAbove(right.getKeys().get(0),left, right);
				}
			}
		} else {
			Node pointer = (Node) root2.getPointers().get(root2.search(c));
			
			insertHelper(pointer, c, p);
		}
	}

	private void insertAbove(Comparable c,Node left, Node right) {
		// TODO Auto-generated method stub
		InnerNode parent = (InnerNode) left.getParent();
		
		if (!parent.insert(c, left, right)) {
			left.setParent(null);
			right.setParent(null);
			InnerNode right1 = new InnerNode(n);
			InnerNode left1 = new InnerNode(n);
			int ceil = parent.getMinimumKeys();
			int i = 0;
			
			for (; i < parent.getKeys().size(); i++) {
				if(parent.getKeys().get(i).compareTo(c)>0){
					break;
				}
				
			}
			parent.getKeys().add(i, c);
			parent.getPointers().set(i,left);
			parent.getPointers().add(i + 1, right);
			
			for(int j =0;j<parent.getMinimumKeys();j++){
				right1.insert(parent.getKeys().get(parent.getKeys().size()-1), parent.getPointers().get(parent.getPointers().size()-2),parent.getPointers().get(parent.getPointers().size()-1));
				Node node =(Node) parent.getPointers().remove(parent.getPointers().size()-1);
				if(node==left){
					left.setParent(right1);
				}
				if(node==right){
					right.setParent(right1);
				}
				parent.getKeys().remove(parent.getKeys().size()-1);
			}
			
			parent.getPointers().remove(parent.getPointers().size()-1);
			parent.getKeys().remove(parent.getKeys().size()-1);
			left1=parent;
			
			Comparable min = goLeft(right1);
			if(left.getParent()==null){
				left.setParent(left1);
			}
			if(right.getParent()==null){
				right.setParent(left1);
			}
			setparents(left1);
			setparents(right1);
			right1.setRightSibling(parent.getRightSibling());
			left1.setRightSibling(right1);
			left1.setLeftSibling(parent.getLeftSibling());
			right1.setLeftSibling(left1);
			if(right.getRightSibling()!=null)
				right.getRightSibling().setLeftSibling(right);
				if(left.getLeftSibling()!=null)
					left.getLeftSibling().setRightSibling(left);
			left1.setParent(parent.getParent());
			right1.setParent(parent.getParent());
			if (right1.getParent() == null) {
				InnerNode parent1 = new InnerNode(n);
				root = parent1;
				parent1.insert(min, left1, right1);
				left1.setParent(parent1);
				right1.setParent(parent1);
			} else {
				insertAbove(min,left1, right1);
			}
			
			
			

		}
	}
	private void setparents(Node left) {
		// TODO Auto-generated method stub
		for(int i =0;i<left.getPointers().size();i++){
			((Node) left.getPointers().get(i)).setParent(left);
		}
		
	}

	public Comparable goLeft(Object n){
		if(n instanceof LeafNode){
			return ((Node) n).getKeys().get(0);
			
		}else{
			return goLeft(((Node) n).getPointers().get(0));
		}
	}
	public void remove(Comparable c){
		removeHelper(root,c);
	}
	
	private void removeHelper(Node root2, Comparable c) {
		// TODO Auto-generated method stub
		if(root2 instanceof LeafNode){
			if(((LeafNode) root2).getKeys().indexOf(c)==-1) //added in case of no such key in the tree
				return;
			if(!((LeafNode) root2).delete(c)){
				if(root2.getRightSibling()!=null&&root2.getRightSibling().getKeys().size()>root2.getRightSibling().getMinimumKeys()){
					Comparable borrowed = root2.getRightSibling().getKeys().get(0);
					((LeafNode) root2).insert(borrowed, root2.getRightSibling().getPointers().get(0));
					((LeafNode) root2.getRightSibling()).delete(borrowed);
					removeAbove(borrowed, root2.getRightSibling().getKeys().get(0),root2.getRightSibling().getParent());
					((LeafNode) root2).delete(c);
				}else{
					if(root2.getLeftSibling()!=null&&root2.getLeftSibling().getKeys().size()>root2.getLeftSibling().getMinimumKeys()){
						Comparable borrowed = root2.getLeftSibling().getKeys().get(root2.getLeftSibling().getKeys().size()-1);
						((LeafNode) root2).insert(borrowed, root2.getLeftSibling().getPointers().get(root2.getLeftSibling().getPointers().size()-2));
						((LeafNode) root2.getLeftSibling()).delete(borrowed);
						removeAbove(root2.getKeys().get(1), root2.getKeys().get(0),root2.getParent());
						((LeafNode) root2).delete(c);
					}else{//merge 
						if(root2.getLeftSibling()!=null){
							for(int i =0;i<root2.getKeys().size();i++){
								if(root2.getKeys().get(i).compareTo(c)!=0){
									((LeafNode) root2.getLeftSibling()).insert(root2.getKeys().get(i),root2.getPointers().get(i));
								}
							}
							root2.getLeftSibling().setRightSibling(root2.getRightSibling());
							if(root2.getRightSibling()!=null){
							root2.getRightSibling().setLeftSibling(root2.getLeftSibling());
							}
							//new
							//if(root2.getLeftSibling().getParent()!=root2.getParent()){
								
							//}
							
						}else{
							for(int i =0;i<root2.getKeys().size();i++){
								if(root2.getKeys().get(i).compareTo(c)!=0){
									((LeafNode) root2.getRightSibling()).insert(root2.getKeys().get(i),root2.getPointers().get(i));
								}
							}
							root2.getRightSibling().setLeftSibling(root2.getLeftSibling());
							
					
					}
						removeAboveMerge(root2.getParent(),root2);
				}
				
				}}
		}else{
			Node pointer = (Node) root2.getPointers().get(root2.search(c));
			
			removeHelper(pointer, c);
		}
	}

	private void removeAboveMerge(Node parent, Node root2) {
		if(this.root==parent){
			int index = parent.getPointers().indexOf(root2);
			if(parent.getKeys().size()>1){
				
				if(index!=0)
					parent.getKeys().remove(index-1);
				else 
					parent.getKeys().remove(index);
				parent.getPointers().remove(root2);
				return;
			}else{
				if(index==0){
					this.root=(Node) parent.getPointers().get(1);
					((Node) parent.getPointers().get(1)).setParent(null);
				}else{
					this.root=(Node) parent.getPointers().get(0);
					((Node) parent.getPointers().get(0)).setParent(null);
				}
				return;
			}
		}
		if(parent!=null){
		if(parent.getKeys().size()>parent.getMinimumKeys()){
			int index = parent.getPointers().indexOf(root2);
			if(index!=0)
				parent.getKeys().remove(index-1);
			else 
				parent.getKeys().remove(index);
			parent.getPointers().remove(root2);
		}
		else if(parent.getLeftSibling()!=null && parent.getLeftSibling().getKeys().size()>parent.getLeftSibling().getMinimumKeys()){
			int index = parent.getPointers().indexOf(root2);
			if(index!=0)
				parent.getKeys().remove(index-1);
			else 
				parent.getKeys().remove(index);
			parent.getPointers().remove(root2);
			Comparable first = parent.getLeftSibling().getKeys().remove(parent.getLeftSibling().getKeys().size()-1);
			Node pointerFirst = (Node) parent.getLeftSibling().getPointers().remove(parent.getLeftSibling().getPointers().size()-1);
			//parent.getKeys().add(0, first);
			parent.getPointers().add(0,pointerFirst);
			Comparable tmp = ((Node) parent.getPointers().get(1)).getKeys().get(0);
			parent.getKeys().add(0, tmp);
			if(index==0){
				tmp = root2.getKeys().get(0);
			}
			removeAbove(tmp,((Node)parent.getPointers().get(0)).getKeys().get(0) , parent.getParent());
			
		}
		else if(parent.getRightSibling()!=null && parent.getRightSibling().getKeys().size()>parent.getRightSibling().getMinimumKeys()){
			int index = parent.getPointers().indexOf(root2);
			if(index!=0)
				parent.getKeys().remove(index-1);
			else 
				parent.getKeys().remove(index);
			parent.getPointers().remove(root2);
			//return here for testing
			Comparable first = parent.getRightSibling().getKeys().remove(0);
			Node pointerFirst = (Node) parent.getRightSibling().getPointers().remove(0);
			//parent.getKeys().add(0, first);
			parent.getPointers().add(parent.getPointers().size()-1,pointerFirst);
			Comparable tmp = pointerFirst.getKeys().get(0);
			parent.getKeys().add(tmp);
			removeAbove(tmp,((Node)parent.getRightSibling().getPointers().get(0)).getKeys().get(0) , parent.getRightSibling().getParent());
		}
		else {
			//merge
			int index = parent.getPointers().indexOf(root2);
			if(index!=0)
				parent.getKeys().remove(index-1);
			else 
				parent.getKeys().remove(index);
			parent.getPointers().remove(root2);
			
			if(parent.getLeftSibling()!=null){
				parent.getLeftSibling().getKeys().add(((Node) parent.getPointers().get(0)).getKeys().get(0));
				for(int i =0;i<parent.getKeys().size();i++){
					parent.getLeftSibling().getKeys().add( parent.getKeys().get(i));

				}
				for(int i =0;i<parent.getPointers().size();i++){
					parent.getLeftSibling().getPointers().add( parent.getPointers().get(i));

				}
				parent.getLeftSibling().setRightSibling(parent.getRightSibling());
				if(parent.getRightSibling()!=null){
				parent.getRightSibling().setLeftSibling(parent.getLeftSibling());
				}
				
			}else{
				parent.getRightSibling().getKeys().add(0,((Node) parent.getRightSibling().getPointers().get(0)).getKeys().get(0));
				for(int i =parent.getKeys().size()-1;i>=0;i--){
					parent.getRightSibling().getKeys().add( 0,parent.getKeys().get(i));

				
				}
				for(int i =parent.getPointers().size()-1;i>=0;i--){
					parent.getRightSibling().getPointers().add( 0,parent.getPointers().get(i));

				}
				parent.getRightSibling().setLeftSibling(parent.getLeftSibling());
				
		
		}
			removeAboveMerge(parent.getParent(),parent);
			
		}
	 }
	}
	private void removeAbove(Comparable old, Comparable replace, Node parent) {
		// TODO Auto-generated method stub
		if(parent!=null){
		int index=parent.getKeys().indexOf(old);
		if(index!=-1){
			parent.getKeys().set(index, replace);
		}else{
			removeAbove(old,replace,parent.getParent());
		}}
		
	}

	public void display(){
		System.out.println(root);
		ArrayList a=new ArrayList();
		ArrayList nextLevel=new ArrayList();
		a.addAll(root.getPointers());
		for(int i =0;i<a.size();i++)
		{
			Object node=a.get(i);
			System.out.print(node+" ");
			if(node instanceof Node){
				nextLevel.addAll(((Node) node).getPointers());
				if(node instanceof LeafNode)
				nextLevel.remove(nextLevel.size()-1);
			}
			if((i+1)>=a.size()){
				a.addAll(nextLevel);
				nextLevel= new ArrayList();
				System.out.println();
			}
		}
	}
	
	
	

}
