package simpledatabase;
import java.util.ArrayList;

public class Projection extends Operator{
	
	ArrayList<Attribute> newAttributeList;
	private String attributePredicate;


	public Projection(Operator child, String attributePredicate){
		
		this.attributePredicate = attributePredicate;
		this.child = child;
		newAttributeList = new ArrayList<Attribute>();
		
	}
	
	
	/**
     * Return the data of the selected attribute as tuple format
     * @return tuple
     */
	@Override
	public Tuple next(){
	 int i = 0;
	 Tuple tempRow = child.next();
	 while (tempRow != null) {
		 i = 0;
		 while (i < tempRow.getAttributeList().size()){
		 	if (attributePredicate.equals(tempRow.getAttributeName(i))){
				 String tempType = tempRow.getAttributeType(i).type.toString();
				 tempType = tempType.substring(0, 1) + tempType.substring(1, tempType.length()).toLowerCase(); 
				 Tuple tempTuple = new Tuple(attributePredicate,tempType,tempRow.getAttributeValue(i).toString());
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
			 i++;
		 }
		 tempRow = child.next();
	 }
	 return null;
		
	}
		

	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}
	public static void main(String args[]){
		Table a = new Table("Student");
		a.next();
		

	}

}