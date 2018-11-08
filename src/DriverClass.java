import java.util.Scanner;
import com.dao.JdbcOps;

public class DriverClass {

	public static void main(String[] args) {
		int choice, i;
		boolean again;
		Scanner sc = new Scanner(System.in);
		JdbcOps jo = new JdbcOps();
		do {
			System.out.println("Enter number to perform corresponding action on the LTIEMP table: ");
			System.out.println("1. Insert Values");
			System.out.println("2. Update Something");
			System.out.println("3. Search by Id");
			System.out.println("4. Delete Something");
			System.out.println("5. Display All");
			choice = sc.nextInt();
			switch(choice) {
			case 1: 
				System.out.println("Insert ID, NAME, PASSWORD, AMOUNT");
				i = jo.insertValues(sc.nextInt(), sc.next(), sc.next(), sc.nextDouble());
				break;
			
			case 2:
				System.out.println("Increase salary by 5%, enter an Employee Id: ");
				i = jo.updateSalary(sc.nextInt());
				break;
			
			case 3:
				System.out.println("Search by Id: ");
				i = jo.searchEmployee(sc.nextInt());
				break;
				
			case 4:
				System.out.println("Delete data of employee id: ");
				i = jo.deleteRow(sc.nextInt());
				break;
				
			case 5:
				System.out.println("Displaying all records..");
				i = jo.displayAll();
				break;
				
			default: 
				System.out.println("I think you've given a wrong choice..");
				continue skipvalidation;
			}
			
			if(i>0)
				System.out.println(i + "record(s) updated.. ");
			else
				System.out.println("Oops, something went wrong..");
			
			skipvalidation: 
			System.out.println("Would you like to continue? \n1 for yes, 0 for no");
			again = sc.hasNextBoolean();
		}while(again);	
	}
}
