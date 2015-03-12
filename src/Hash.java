import java.io.*;
import java.util.*;
import java.io.FileNotFoundException;

public class Hash {
	Node[] heap = new Node[100];
	int count = 1;
	int numHeap = 0;
	Node newNode;
	Hashtable<Integer, String> hash = new Hashtable<Integer, String>();
	Hashtable<Integer, String> seatHash = new Hashtable<Integer, String>();

	class Node {
		int priority;
		int id;

		public Node(int x, int y) {
			id = x;
			priority = y;
		}

	}

	public Hash(String file, String file2) throws FileNotFoundException {
		Scanner scan = new Scanner(new File(file));
		String na;
		int p;
		while (scan.hasNextLine()) {
			p = scan.nextInt();
			na = scan.next() + scan.nextLine();
			hash.put(p, na);
		}

		heap(file2);
		System.out.println("Final Heap:");
		for (int i = 1; i < count; i++) {
			System.out.println(i + " ("+ heap[i].id + ", " + heap[i].priority+") ");
		}
	}

	public void heap(String file) throws FileNotFoundException {
		Scanner scan1 = new Scanner(new File(file));
		while (scan1.hasNextLine()) {
			char operation = scan1.next().charAt(0);
			int priority;
			int oldPriority;
			int id;
			String seat;
			switch (operation) {
			case 'I':
			case 'i':
				id = scan1.nextInt();
				priority = scan1.nextInt();
				System.out.println("Processing: " + operation + " " + id + " "
						+ priority);
				Node newNode = new Node(id, priority);
				insert(newNode);
				numHeap++;
				break;
			case 'C':
			case 'c':
				id = scan1.nextInt();
				oldPriority = scan1.nextInt();
				priority = scan1.nextInt();
				System.out.println("Processing: "+ operation +" "+ id + " " + oldPriority+ " "+ priority);
				changePriority(id, priority);
				break;
			case 'A':
			case 'a':
				seat = scan1.nextLine();
				System.out.println("Processing: " + operation + " " + seat);
				System.out.println(hash.get(heap[1].id) + " (Donor: "+ heap[1].id +", Priority: "+ heap[1].priority+") assigned "+ seat);
				assignSeat(seat);
				numHeap--;
				// grab highest priority from heap and pop it and add it to a
				// hash
				// when assigning seats, maybe add it to a hash<donor id, seat>
				break;
			case 'S':
			case 's':
				// check hashmap (id, seat) to see if it has been assigned seat
				id = scan1.nextInt();
				System.out.println("Processing: " + operation + " " + id);
				if (seatHash.get(id) != null) {
					System.out.println("Donor " + id
							+ " has been assigned seat " + seatHash.get(id));
				} else
					System.out.println("No seat assigned for Donor " + id);
				break;
			case 'B':
			case 'b':
				System.out.println("Processing B: Undergraduate Skip this");
				scan1.nextLine();
				break;
			case 'L':
			case 'l':
				System.out.println("Processing " + operation);
				System.out.println("Donors that have been assigned a seat:");
				Enumeration<Integer> names = seatHash.keys();
				while (names.hasMoreElements()) {
					int name = names.nextElement();
					System.out.println(hash.get(name) + seatHash.get(name));
				}

				break;
			default:
				System.out.println("not a valid operation");
				break;
			}
		}
	}

	public void insert(Node n) {
		newNode = n;
		heap[count] = newNode;
		maxHeap(count++);
	}

	public void maxHeap(int index) {
		int parent = (index) / 2;
		Node child = heap[index];
		while (index >= 2 && heap[parent].priority < child.priority) {
			heap[index] = heap[parent];
			index = parent;
			parent = (parent) / 2;
		}
		heap[index] = child;
	}

	public void assignSeat(String s) {
		Node top = heap[1];
		heap[1] = heap[numHeap];
		heap[numHeap] = null;
		count--;
		for (int i = 1; i < numHeap; i++) {
			maxHeap(i);
		}
		seatHash.put(top.id, s);
	}

	public void changePriority(int who,int np) {
		for (int i = 1; i < count - 1; i++) {
			if (heap[i].id == who) {
				heap[i].priority = np;
				for (int j = 1; j < numHeap; j++) {
					maxHeap(j);
				}
			} else {
				// System.out.println("There is no user associated with that ID");
			}
		}
	}
}