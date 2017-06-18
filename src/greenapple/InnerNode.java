package greenapple;

public class InnerNode extends Node {
	public InnerNode(int n) {
		super();
		setMaximumKeys(n);
		
		setMinimumKeys((int) Math.ceil((n + 1) / 2.0) - 1);

	}

	@Override
	public int search(Comparable c) {
		// TODO Auto-generated method stub
		int i = 0;
		for (; i < getKeys().size(); i++) {
			if ((getKeys().get(i).compareTo(c)) > 0) {
				break;
			}
		}
		return i;
	}

	public boolean insert(Comparable c, Object left, Object right) {
		if (getKeys().size() < getMaximumKeys()) {
			int i = 0;
			for (; i < getKeys().size(); i++) {
				if (getKeys().get(i).compareTo(c) > 0) {
					break;
				}
			}
			getKeys().add(i, c);
			if (getKeys().size() == 1) {
				getPointers().add(i, left);
				getPointers().add(i + 1, right);
				
			} else {

				if (i == 0) {
					getPointers().add(i, left);
					getPointers().set(i+1,right);
					
				} else {
					
					getPointers().set(i,left);//MOshkela hena
					getPointers().add(i + 1, right);
				
					}

			}

			return true;
		}
		return false;
	}
	
}
