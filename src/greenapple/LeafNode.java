package greenapple;

public class LeafNode extends Node {
	public LeafNode(int n){
		super();
		getPointers().add(null);
		setMaximumKeys(n);
		setMinimumKeys((int)Math.floor((n+1)/2.0));
		
	}

	public Pair searchleaf(Comparable c) {
		// TODO Auto-generated method stub
		
		int index = binarySearch(c);
		if(index!=-1){
			return (Pair) getPointers().get(index);
		}
		return null;
	}
	public boolean insert(Comparable c,Object pointer){
		if(getKeys().size()<getMaximumKeys()){
			int i =0;
			for(;i<getKeys().size();i++){
				if(getKeys().get(i).compareTo(c)>0){
					break;
				}
				
			}
			getKeys().add(i,c);
			getPointers().add(i,pointer);
			return true;
		}
		return false;
	}
	
	@Override
	public void setRightSibling(Node rightSibling) {
		// TODO Auto-generated method stub
		super.setRightSibling(rightSibling);
		getPointers().set(getPointers().size()-1,rightSibling);
	}

	public boolean delete(Comparable c){
		if((getKeys().size())>getMinimumKeys()){
			int index = getKeys().indexOf(c);
		if(getKeys().remove(c)){
			getPointers().remove(index);
			
		}
		return true;
		}
		return false;
	}

	@Override
	public int search(Comparable c) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	

}
