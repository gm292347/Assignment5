package simpledatabase;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Join extends Operator{

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuplesa;
	ArrayList<Tuple> tuplesb;
	int aIndex = 0;
	int bIndex = 0;
	int x;
	int y;
	
	
	//Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
//		System.out.println(joinPredicate);
		newAttributeList = new ArrayList<Attribute>();
		tuplesa = new ArrayList<Tuple>();
		tuplesb = new ArrayList<Tuple>();
		
	}

	
	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	//The record after join with two tables
	@Override
	public Tuple next(){
		Tuple a = leftChild.next();
		Tuple b = rightChild.next();

		
		if ( a != null || b != null){
			int aSize = a.col.length;
			int bSize = b.col.length;
			x = 0;
			y = 0;
			outerloop:
			while (x < aSize ) {
				while(y < bSize ) {
//					System.out.println( x + " " +y );
//					System.out.println(x + a.getAttributeName(x) + "/ " +b.getAttributeName(y));
//					System.out.println(a.getAttributeValue(i).toString() + b.getAttributeValue(j).toString());
					if (a.getAttributeName(x).equals(b.getAttributeName(y))){
						break outerloop;
					}
					y++;
				}
				x++;
			}
		}
//		System.out.println(x);
//		System.out.println(y);
//		a = leftChild.next();
//		System.out.println(Arrays.deepToString(a.col2));
//		a = leftChild.next();
//		System.out.println(Arrays.deepToString(a.col2));
//		a = leftChild.next();
//		System.out.println(Arrays.deepToString(a.col2));
		
//		System.out.println(Arrays.deepToString(b.col2));
//		b = leftChild.next();
//		System.out.println(Arrays.deepToString(b.col2));

//		a = leftChild.next();
//		a = leftChild.next();
		int i = 0;
		
		
//		System.out.println(Arrays.deepToString(a.col));
		while (a != null) {
			tuplesa.add(new Tuple(String.join(",", a.col),String.join(",", a.col1),String.join(",", a.col2)));
			tuplesa.get(i).setAttributeName();
			tuplesa.get(i).setAttributeType();
			tuplesa.get(i).setAttributeValue();
			a = leftChild.next();		
			i++;
		}
//		System.out.println(Arrays.deepToString(tuplesa.get(0).col2));
//		System.out.println(Arrays.deepToString(tuplesa.get(1).col2));
//		System.out.println(Arrays.deepToString(tuplesa.get(2).col2));
		i = 0;
		while (!(b == null)) {
//			System.out.println(Arrays.deepToString(b.col2));
			tuplesb.add(new Tuple(String.join(",", b.col),String.join(",", b.col1),String.join(",", b.col2)));
			tuplesb.get(i).setAttributeName();
			tuplesb.get(i).setAttributeType();
			tuplesb.get(i).setAttributeValue();
			b = rightChild.next();
			i++;
		}
//		System.out.println(i);
		
//		System.out.println(Arrays.deepToString(tuplesb.get(0).col2));
//		System.out.println(Arrays.deepToString(tuplesb.get(1).col2));
//		System.out.println(Arrays.deepToString(tuplesb.get(2).col2));
		int count = 0;
		
		
		while (bIndex < tuplesb.size()){
//			System.out.println(aIndex);
			
			while (aIndex < tuplesa.size()){
//				System.out.println(bIndex);
				a = tuplesa.get(aIndex);
				b = tuplesb.get(bIndex);
//				System.out.println(bIndex);
//				System.out.println(b.getAttributeValue(y).toString());
//				System.out.println(Arrays.deepToString(tuplesb.get(0).col2));
//				System.out.println(Arrays.deepToString(tuplesb.get(1).col2));
//				System.out.println(Arrays.deepToString(tuplesb.get(2).col2));
//				System.out.println(Arrays.deepToString(tuplesb.get(3).col2));
//				System.out.println(Arrays.deepToString(tuplesa.get(0).col2));
//				System.out.println(Arrays.deepToString(tuplesa.get(1).col2));
//				System.out.println(Arrays.deepToString(tuplesb.get(2).col2));
//				System.out.println(Arrays.deepToString(tuplesb.get(3).col2));
//				System.out.println(tuples1.get(0).getAttributeValue(y));
//				System.out.println(tuples1.get(1).getAttributeValue(y));
//				System.out.println(tuples1.get(2).getAttributeValue(y));
//				System.out.println(tuples1.get(3).getAttributeValue(y));
				aIndex++;
//				System.out.println(b.getAttributeName(0));
//				System.out.println(a.getAttributeValue(x).toString());
//				System.out.println(b.getAttributeValue(y).toString());
				if (a.getAttributeValue(x).equals(b.getAttributeValue(y))){
					
					String[] tempname = concatenate(a.col,b.col);
					String[] tempType = concatenate(a.col1,b.col1);
					String[] tempData = concatenate(a.col2,b.col2);
					Tuple tempTuple = new Tuple(String.join(",", tempname),String.join(",", tempType),String.join(",", tempData));
					tempTuple.setAttributeName();
					tempTuple.setAttributeType();
					tempTuple.setAttributeValue();
					while (i < tempTuple.getAttributeList().size()){
						Attribute temp = tempTuple.getAttributeList().get(i);
						 newAttributeList.add(temp);
						 i++;
					}
					return tempTuple;
					
				}
				
			}
			bIndex++;
			if (aIndex == tuplesa.size()){
				aIndex = 0;
			}
//			System.out.println(a + "");
//			System.out.println(a.getAttributeValue(x).toString());
			
		}
		return null;
	}
	
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public String[] concatenate(String[] a,String[] b){
		int aLen = a.length;
		int bLen = b.length;
		String[] c = (String[]) Array.newInstance(a.getClass().getComponentType(), aLen+ bLen);
		
		System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);

	    return c;
	}
	public ArrayList<Attribute> getAttributeList(){
		if(joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return(newAttributeList);
	}
	public String getTypeName(Type a){
		String typeName = a.type.toString();
		String temp = typeName.substring(0, 1) + typeName.substring(1, typeName.length());
		return temp; 
		
	}
	public static void main(String args[]){
		Table a = new Table("Student");
		Table b = new Table("CourseEnroll");
		Join c = new Join(a,b,"CourseEnroll");
		Tuple d = c.next();
		while (d != null){
			System.out.println(Arrays.deepToString(d.col2));
			d = c.next();
			System.out.println(d + "");
		}
		

	}

}