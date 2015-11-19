package simpledatabase;
import java.util.ArrayList;
import java.util.Arrays;

public class Selection extends Operator{
	
	ArrayList<Attribute> attributeList;
	String whereTablePredicate;
	String whereAttributePredicate;
	String whereValuePredicate;

	
	public Selection(Operator child, String whereTablePredicate, String whereAttributePredicate, String whereValuePredicate) {
		this.child = child;
		this.whereTablePredicate = whereTablePredicate;
		this.whereAttributePredicate = whereAttributePredicate;
		this.whereValuePredicate = whereValuePredicate;
		System.out.println(whereTablePredicate + whereAttributePredicate + whereValuePredicate);
		attributeList = new ArrayList<Attribute>();

	}
	
	public int getAttributeNameIndex(Tuple t,String name){
		int i = 0;
		while (i < t.col.length){
			if (t.col[i].equals(name)){
				return i;
			}
			i++;
		}
		return -1;
	}
	
	/**
     * Get the tuple which match to the where condition
     * @return the tuple
     */
	@Override
	public Tuple next(){
		Tuple tempRow = child.next();
		while (tempRow != null){
			
//			System.out.println(tempRow + "");

//			System.out.println(tempRow + "");
			int index = getAttributeNameIndex(tempRow,whereAttributePredicate);
			if (tempRow.equals(null)) {
//				System.out.println(tempRow + "");
				return null;
			}
			else if (index == -1){
				return tempRow;
			}
			
			if (tempRow.getAttributeValue(index).equals(whereValuePredicate)){
//				System.out.println(tempRow.getAttributeValue(index));

				String tempType = tempRow.getAttributeType(index).type.toString();
				 tempType = tempType.substring(0, 1) + tempType.substring(1, tempType.length()).toLowerCase(); 
				 Tuple tempTuple = new Tuple(String.join(",", tempRow.col),String.join(",", tempRow.col1),String.join(",", tempRow.col2));
				 tempTuple.setAttributeName();
				 tempTuple.setAttributeType();
				 tempTuple.setAttributeValue();
				 int i = 0;
				 while (i < tempTuple.getAttributeList().size()){
					 Attribute temp = tempTuple.getAttributeList().get(i);
					 attributeList.add(temp);
					 i++;
				}
				 return tempTuple;
			}
			tempRow = child.next();
			
			
		}
		
		return null;
		
			
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		
		return(child.getAttributeList());
	}
	public static void main(String args[]){
		Table a = new Table("Student");
		Table b = new Table("CourseEnroll");
		Operator selection = new Selection(a,"CourseEnroll","courseID","\"COMP2021\"");
		Operator selection2 = new Selection(b,"CourseEnroll","courseID","\"COMP2021\"");
		Operator join = new Join(selection,selection2,"");
		join.next();
		
	}

	
}