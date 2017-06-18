package greenapple;

import java.io.IOException;

public class Test {
	public static void main(String[] args) throws IOException {
		BPTree t = new BPTree();
		t.insert(1, new Pair(8, 15));
		t.insert(2, new Pair(7, 15));
		t.insert(3, new Pair(5, 15));
		t.insert(4, new Pair(6, 15));
		t.insert(14, new Pair(3, 15));
		t.insert(16, new Pair(2, 15));
		t.insert(23, new Pair(23, 15));
		t.insert(32, new Pair(9, 15));
//		t.insert(26, new Pair(4, 15));
		
		t.insert(65, new Pair(11, 15));
		t.insert(66, new Pair(11, 15));
		t.insert(67, new Pair(11, 15));
		t.insert(90, new Pair(10, 15));
		
//		t.insert(40, new Pair(15, 15));
		t.insert(5, new Pair(20, 15));
		t.insert(18, new Pair(18, 15));
//		t.insert(17, new Pair(17, 15));
		t.insert(120, new Pair(21, 15));
		t.insert(220, new Pair(220, 15));
		t.display();
//		t.remove(32);
//		t.display();
//		t.remove(23);
		t.display();
		t.remove(18);
		t.insert(40, new Pair(11, 15));
		t.insert(41, new Pair(11, 15));
		t.insert(42, new Pair(11, 15));
		t.insert(88, new Pair(11, 15));
		t.insert(100, new Pair(11, 15));
		t.insert(68, new Pair(11, 15));
		t.insert(69, new Pair(11, 15));
		t.remove(120);
		
		t.display();
		System.out.println(t.search(6));
//		System.out.println("__________________________________________________");
//		t.remove(18);
////		t.remove(24);
//		t.display();
	}
}
