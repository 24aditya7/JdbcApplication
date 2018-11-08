import java.util.Scanner;
import com.dao.JdbcOps;

public class DriverClass {

	public static void main(String[] args) {
		int choice, i;
		String again;
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
				if(i>0)
					System.out.println(i + " record(s) updated.. ");
				else
					System.out.println("Oops, something went wrong..");
				break;
			
			case 2:
				System.out.println("Increase salary by 5%, enter an Employee Id: ");
				i = jo.updateAmount(sc.nextInt());
				if(i>0)
					System.out.println(i + " record(s) updated.. ");
				else
					System.out.println("Oops, something went wrong..");
				break;
			
			case 3:
				System.out.println("Search by Id: ");
				jo.searchEmployee(sc.nextInt());
				break;
				
			case 4:
				System.out.println("Delete data of employee id: ");
				i = jo.deleteRow(sc.nextInt());
				if(i>0)
					System.out.println(i + " record(s) updated.. ");
				else
					System.out.println("Oops, something went wrong..");
				break;
				
			case 5:
				System.out.println("Displaying all records..");
				jo.displayAll();
				break;
				
			default: 
				System.out.println("I think you've given a wrong choice..");
			}
			System.out.println("Would you like to continue? \ny for yes, n for no");
			again = sc.next();
		}while(again.equals("y"));	
		System.out.println("Thank you..");
	}
}
