import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class hospital {
	
	public static boolean insertPatient = true;
	public static boolean insertDoctor = false;

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		 
		 String uid = "your uid";

//		 System.out.print("password: ");
		 String pword = "your password";
		    
		 String url = "your database url";

		 // Load the Oracle JDBC driver

		 DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

		    // Connect to the database
		    
		 Connection conn =
		      DriverManager.getConnection (url, uid, pword);
		 
		 boolean done = false;
		 
		 while(done == false) {
			
			 System.out.println("Please make a selection: ");
				System.out.println("1) Add a patient ");
				System.out.println("2) Add a doctor");
				System.out.println("3) Add a department");
				System.out.println("4) Add procedure");
				System.out.println("5) Add medication");
				System.out.println("6) Add interaction record");
				System.out.println("7) Generate patient health record");
				System.out.println("8) View procedures offered by department");
				System.out.println("9) List all of a doctors procedures");
				System.out.println("0) Quit Program ");
				System.out.println();
				
				Scanner option = new Scanner(System.in);
				String options = option.next();
				
				switch(options) {
					case "1":
						insertPatient = true;
						insertDoctor = false;
						addPerson(conn);
						break;
				
					case "2":
						insertDoctor = true;
						insertPatient = false;
						addPerson(conn);
						break;
				
					case "3":
						addDepartment(conn);						
						break;
						
					case "4":
						addProcedure(conn);
						break;
				
					case "5":
						addMedication(conn);
						break;
				
					case "6":
						addInteractionRecord(conn);
						break;
					
					case "7":
						patientRecords(conn);
						break;
					
					case "8":
						departmentProcedures(conn);
						break;
					
					case "9":
						doctorProcedures(conn);
						break;
				
					case "0":
						done = true;
						option.close();
					
					
				
				}
			
			
		}
		 
		 conn.close();
		 


	}
	
	public static void addPerson(Connection conn) throws SQLException {
		
		PreparedStatement pstmt =
			      conn.prepareStatement ("INSERT INTO PERSON(SSN, LNAME, FNAME, MINITIAL, SEX, BDATE, PHONENUM, CITY, STATE, ADDRESS, ZIP) " +
			      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		int done = 1;
		String ssnRegex = "^\\d{9}$";
		String patientRegex = "^[pP]\\d{8}$";
		String doctorRegex = "^D\\d{8}$";
		String ssn = "";
		String pID = "";
		String dID = "";
		String department = "";
		String pDoc = "";
		String sDoc = "";
		String condition = "";
		
		while (done != 0) {
		      System.out.println("\nEnter ssn: ");
		      ssn = getString();
		      boolean ssnValid = false;
		      while (ssnValid == false) { 
		    	  
		    	  if (ssn.matches(ssnRegex)) {
		    		  ssnValid = true;
		    	  }
		    	  else {
		    		  System.out.println("SSN must be 9 characters long and only contain digits");
		    		  ssn = getString();
		    	  }
		      }
		     
		      
		      System.out.println("\nEnter lasts name: ");
		      String lName = getString();
		      System.out.println("\nEnter first name: ");
		      String fName = getString();
		      System.out.println("\nEnter middle initial: ");
		      String mInitial = getString();
		      System.out.println("\nEnter sex: ");
		      String sex = getString();
		      System.out.println("\nEnter birth date: ");
		      String bDate = getString();
		      System.out.println("\nEnter phone number: ");
		      String phone = getString();
		      System.out.println("\nEnter city: ");
		      String city = getString();
		      System.out.println("\nEnter state: ");	      
		      String state = getString();
		      System.out.println("\nEnter address: ");
		      String address = getString();
		      System.out.println("\nEnter zip: ");
		      String zip = getString();
		      
		      if(insertPatient == true) {
		    	  
			      System.out.println("\nEnter Patient ID Starting with a P: ");
			      pID = getString();	
			      boolean patientValid = false;
			      while (patientValid == false) {  
			    	  
			    	  if (pID.matches(patientRegex)) {
			    		  patientValid = true;
			    	  }
			    	  else {
			    		  System.out.println("Patient ID must start with P followed by 8 digits");
			    		  pID = getString();
			    	  }
			      }
			      
			      System.out.println("\nEnter primary doctor: ");
			      pDoc = getString();
			      System.out.println("\nEnter secondary doctor if valid: ");
			      sDoc = getString();
			      System.out.println("\nEnter patient condition: ");
			      condition = getString();
		      }
		      
		      if (insertDoctor == true) {
		    	  
		    	  System.out.println("\nEnter doctor ID: ");
			      dID = getString();
			      boolean doctorValid = false;
			      while (doctorValid == false) {  
			    	  
			    	  if (dID.matches(doctorRegex)) {
			    		  doctorValid = true;
			    	  }
			    	  else {
			    		  System.out.println("Doctor ID must start with D followed by 8 digits");
			    		  dID = getString();
			    	  }
			      }
			      System.out.println("\nEnter department: ");
			      department = getString();
			        
		      }
		      
		      try {
			      pstmt.setString(1, ssn);
			      pstmt.setString(2, lName);
			      pstmt.setString(3, fName);
			      pstmt.setString(4, mInitial);
			      pstmt.setString(5, sex);
			      pstmt.setString(6, bDate);
			      pstmt.setString(7, phone);
			      pstmt.setString(8, city);  
			      pstmt.setString(9, state);
			      pstmt.setString(10, address);
			      pstmt.setString(11, zip);
		      int NumRows = pstmt.executeUpdate();
		      System.out.println("\n" + NumRows + " row(s) inserted into person");
		      }
		      catch(SQLIntegrityConstraintViolationException E) {
		    	  System.out.println("Cannot insert null values please try again");
		      }
		      
		      if (insertPatient == true) {
			    	addPatient(conn, pID, pDoc, sDoc, ssn, condition);
			  }
			    
			  if (insertDoctor == true) {
				    addDoctor(conn, dID, department, ssn);
			  }
			  
			  done = getInt();
			  
		} // while done  	  
		
	}
	
	public static void addPatient(Connection conn, String pID, String pDoc, String sDoc, String ssn, String condition) throws SQLException {
		
		
		
		try {
			PreparedStatement pstmt =
				      conn.prepareStatement ("INSERT INTO PATIENT(PATIENTID, PRIMARYDOCTOR, SECONDARYDOCTOR, PSSN, PCONDITION) " +
				      "VALUES (?, ?, ?, ?, ?)");
			
			pstmt.setString(1, pID);
			pstmt.setString(2, pDoc);
			pstmt.setString(3, sDoc);
			pstmt.setString(4, ssn);
			pstmt.setString(5, condition);
		
			int NumRows = pstmt.executeUpdate();
			System.out.println("\n" + NumRows + " row(s) inserted patient");
			System.out.println("\nHit 0 for exit, " +
					"or enter any other number for another insert: ");
		
		}
		catch(SQLIntegrityConstraintViolationException E) {
			System.out.println("tried to insert a null value or a patient id that already exists");
			String q = ("DELETE from PERSON where ssn='" + ssn + "'");
			Statement stmt = conn.createStatement ();
			ResultSet rset = stmt.executeQuery(q);
			int Rows = stmt.executeUpdate(q);
			System.out.println("\n" + Rows + " row(s) deleted person");
		}
		

		
		      	    
		
	}
	
	public static void addDoctor(Connection conn, String dID, String department, String ssn) throws SQLException {

		

		try {
			
			PreparedStatement pstmt =
					conn.prepareStatement ("INSERT INTO DOCTOR(DOCTORID, DEPARTMENT, DSSN) " +
							"VALUES (?, ?, ?)");
			
			pstmt.setString(1, dID);
			pstmt.setString(2, department);
			pstmt.setString(3, ssn);
			
			int NumRows = pstmt.executeUpdate();
			System.out.println("\n" + NumRows + " row(s) inserted into doctor");
			System.out.println("\nHit 0 for exit, " +
					"or enter any other number for another insert: ");
		}
		catch(SQLIntegrityConstraintViolationException E) {
			System.out.println("tried to insert a null value or a doctor id that already exists");
			String q = ("DELETE from Person where ssn='" + ssn + "'");
			Statement stmt = conn.createStatement ();
			ResultSet rset = stmt.executeQuery(q);
			int Rows = stmt.executeUpdate(q);
			System.out.println("\n" + Rows + " row(s) deleted person");
		}


		



	}
	
	public static void addInteractionRecord(Connection conn) throws SQLException {

		PreparedStatement pstmt =
				conn.prepareStatement ("INSERT INTO INTERACTION_RECORD(Interaction_id, patID, Proc_time) " +
						"VALUES (?, ?, ?)");
		int done = 1;
		String patID = "";
		String interaction_id = "";
		String proc_time = "";
		

		while (done != 0) {	
			
			System.out.println("\nEnter Interaction ID: ");
			interaction_id = getString();
			
			System.out.println("\nEnter patientID: ");
			patID = getString();

			System.out.println("\nEnter time of procedure: ");
			proc_time = getString();

			try {
				pstmt.setString(1, interaction_id);
				pstmt.setString(2, patID);
				pstmt.setString(3, proc_time);
				
	
				int NumRows = pstmt.executeUpdate();
				System.out.println("\n" + NumRows + " row(s) inserted");
	
				System.out.println("\nHit 0 for exit, " +
						"or enter any other number for another insert: ");
			}
			catch(SQLIntegrityConstraintViolationException E) {
		    	  System.out.println("Cannot insert null values please try again");
		    }
			done = getInt();
		} // while done
		
	}
	
	public static void addProcedure(Connection conn) throws SQLException {

		PreparedStatement pstmt =
				conn.prepareStatement ("INSERT INTO PROCEDURE(procedureNum, pName, pTime, pDate, Description, Note, offeringDepartment, durationOfProcedure, medName) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		int done = 1;
		String patientID = "";
		String doctorID="";
		String procedureNumber = "";
		String Pname = "";
		String Ptime = "";
		String Pdate = "";
		String description = "";
		String note = "";
		String offeringDepartment = "";
		String duration = "";
		String medName = "";
		String patientRegex = "^[pP]\\d{8}$";

		while (done != 0) {

			//prompt for patient ID to add to the end of this function
			System.out.println("\nEnter patientID: ");
			patientID = getString();
			
			boolean patientValid = false;
		    while (patientValid == false) {  
		    	  
		    	if (patientID.matches(patientRegex)) {
		    		  patientValid = true;
		    	}
		    	else {
		    		System.out.println("Patient ID must start with P followed by 8 digits");
		    		patientID = getString();
		    	}
		    }

			System.out.println("\nEnter procedure number: ");
			procedureNumber = getString();

			System.out.println("\nEnter Procedure name: ");
			Pname = getString();

			System.out.println("\nEnter time of procedure: ");
			Ptime = getString();

			System.out.println("\nEnter Procedure date: ");
			Pdate = getString();

			System.out.println("\nEnter description: ");
			description = getString();

			System.out.println("\nEnter note: ");
			note = getString();


			System.out.println("\nEnter offering department: ");
			offeringDepartment = getString();

			System.out.println("\nEnter procedure duration: ");
			duration = getString();

			System.out.println("\nEnter med name: ");
			medName = getString();
			
			System.out.println("Enter doctor ID: ");
			doctorID = getString();

			try {
				pstmt.setString(1, procedureNumber);
				pstmt.setString(2, Pname);
				pstmt.setString(3, Ptime);
				pstmt.setString(4, Pdate);
				pstmt.setString(5, description);
				pstmt.setString(6, note);
				pstmt.setString(7, offeringDepartment);  
				pstmt.setString(8, duration);
				pstmt.setString(9, medName);
				int NumRows = pstmt.executeUpdate();
				System.out.println("\n" + NumRows + " row(s) inserted");
	
				System.out.println("\nHit 0 for exit, " +
						"or enter any other number for another insert: ");
			}
			catch(SQLIntegrityConstraintViolationException E) {
		    	  System.out.println("Cannot insert null values please try again");
		      }
			
			addPatientProcedure(conn, procedureNumber,  patientID);
			addDoctorProcedure(conn, procedureNumber,  doctorID);
			done = getInt();
		} // while done
		
		

	}
	
	public static void addPatientProcedure(Connection conn, String procedureNumber, String patientID) throws SQLException {

		PreparedStatement pstmt =
				conn.prepareStatement ("INSERT INTO PATIENTPROCEDURE(patientID, procedureNum) " +
						"VALUES (?, ?)");
		
		try {
			pstmt.setString(1, patientID);
			pstmt.setString(2, procedureNumber);
	
			int NumRows = pstmt.executeUpdate();
			System.out.println("\n" + NumRows + " row(s) inserted");
		}
		catch(SQLIntegrityConstraintViolationException E) {
	    	  System.out.println("Cannot insert null values please try again");
	      }
		

	}
	
	public static void addDoctorProcedure(Connection conn, String procedureNumber, String doctorID) throws SQLException {

		PreparedStatement pstmt =
				conn.prepareStatement ("INSERT INTO DOCTORPROCEDURE(procNum, doctorID) " +
						"VALUES (?, ?)");
		
		try {
			pstmt.setString(1, procedureNumber);
			pstmt.setString(2, doctorID);
	
			int NumRows = pstmt.executeUpdate();
			System.out.println("\n" + NumRows + " row(s) inserted");
		}
		catch(SQLIntegrityConstraintViolationException E) {
	    	  System.out.println("Cannot insert null values please try again");
	      }
		

	}
	
	
	
	public static void patientRecords(Connection conn) throws SQLException {
		String patientRegex = "^[pP]\\d{8}$";
		System.out.print("Enter patientID: ");
	    String v = getString();
	    boolean patientValid = false;
	    while (patientValid == false) {  
	    	  
	    	if (v.matches(patientRegex)) {
	    		patientValid = true;
	    	}
	    	else {
	    		System.out.println("Patient ID must start with P followed by 8 digits");
	    		v = getString();
	    	}
	     }
		
		String q = "select FNAME, LNAME, ADDRESS, PRIMARYDOCTOR, PHONENUM " +
	               "from PERSON, PATIENT " +
	               "where patientID= " + "'" + v + "'" + "and ssn=Pssn";
		Statement stmt = conn.createStatement ();
		ResultSet rset = stmt.executeQuery(q);

	    System.out.println("\n");

	      // Iterate through the result

	    while (rset.next ()) {
	        String fname = rset.getString("FNAME");
	        String lname = rset.getString("LNAME");
	        String address = rset.getString("ADDRESS");
	        String pdoc = rset.getString("PRIMARYDOCTOR");   
	        String phone = rset.getString("PHONENUM");
	        System.out.println ("First name: " + fname);
	        System.out.println ("Last name: " + lname);
	        System.out.println ("Address: " + address);
	        System.out.println ("Primary Doctor: " + pdoc);
	        System.out.println ("Phone number: " + phone);
	    }
	    try {
		    procedureRecords(conn, v);
		    interactionsRecords(conn, v);
		    medicationRecords(conn, v);
		    System.out.println("\n");
	    }
	    catch(SQLIntegrityConstraintViolationException E) {
	    	  System.out.println("Cannot insert null values please try again");
	      }
		
	}
	
	public static void procedureRecords(Connection conn, String v) throws SQLException {

        String q = "select pName, pDate, Description" +
                " from procedure P1, patientProcedure P2" +
                 " where P1.procedureNum = P2.procedureNum and P2.patientID= " + "'" + v + "'";
        Statement stmt = conn.createStatement ();
        ResultSet rset = stmt.executeQuery(q);

        System.out.println("\n");

        System.out.println("Procedures information");

          // Iterate through the result

        while (rset.next ()) {
            String pName = rset.getString("pName");
            String pDate = rset.getString("pDate");
            String description = rset.getString("Description");
            System.out.println ("Procedure name : " + pName);
            System.out.println ("Date of procedure: " + pDate);
            System.out.println ("Description: " + description);
            System.out.println("\n------------------------");


        }

    }
	
	public static void interactionsRecords(Connection conn, String v) throws SQLException {

        String q = "select interaction_id, proc_time" +
                   " from interaction_record IR" +
                   " where IR.patID =" + "'" + v + "'" ;

        Statement stmt = conn.createStatement ();
        ResultSet rset = stmt.executeQuery(q);

        System.out.println("\n");

        System.out.println("Interaction record information");

          // Iterate through the result

        while (rset.next ()) {
            String interaction_id = rset.getString("interaction_id");
            String proc_time = rset.getString("proc_time");
//            String pDate = rset.getString("pDate");
//            String description = rset.getString("description");
            System.out.println ("Interaction ID: " + interaction_id);
            System.out.println ("Procedure Time: " + proc_time);
            System.out.println("\n------------------------");
//            System.out.println ("Procedure Date: " + pDate);
//            System.out.println ("Description: " + description);

        }

    }
	
	public static void medicationRecords(Connection conn, String v) throws SQLException {
		
		String q = "select medication_prescribed.unique_name, medicationDate" + 
				" from medication_prescribed, patientMedication" + 
				" where medication_prescribed.unique_name = patientMedication.unique_name and patientMedication.patientID= " + "'" + v + "'";

		Statement stmt = conn.createStatement ();
		ResultSet rset = stmt.executeQuery(q);

	    System.out.println("\n");
	    
	    System.out.println("Medication Information");

	      // Iterate through the result

	    while (rset.next ()) {
	        String unique_name = rset.getString("unique_name");
	        String medicationDate = rset.getString("medicationDate");
	        System.out.println ("Medication name: " + unique_name);
	        System.out.println ("Date of medication: " + medicationDate);
	    }
		
	}
	
	public static void departmentProcedures(Connection conn) throws SQLException {
		try {
	        System.out.print("Enter unique office number of department: ");
	        String v = getString();
	
	        String q =  "select pName,pDate,pTime, description " +
	                    " from PROCEDURE, DEPARTMENT" +
	                    " where offeringDepartment='" + v + "'" + " and office_no='" + v + "'";
	        Statement stmt = conn.createStatement ();
	        ResultSet rset = stmt.executeQuery(q);
	
	        System.out.println("\n");
	
	          // Iterate through the result
	        System.out.println("Procedures done by department: ");
	
	        while (rset.next ()) {
	            String pName = rset.getString("pName");
	            String pDate = rset.getString("pDate");
	            String pTime = rset.getString("pTime");
	            String description = rset.getString("description");
	
	
	
	            System.out.println ("Procedure Name: " + pName);
	            System.out.println ("Procedure Date: " + pDate);
	            System.out.println ("Procedure Time: " + pTime);
	            System.out.println ("Description: " + description);
	        }
	
	        System.out.println("\n");
		}
		catch(SQLIntegrityConstraintViolationException E) {
	    	  System.out.println("Cannot insert null values please try again");
	      }
    }
	
	public static void doctorProcedures(Connection conn) throws SQLException {
		System.out.print("Enter doctor ID: ");
	    String v = getString();
		
		String q = 	"SELECT pName, pDate, pTime, description " +
					"FROM PROCEDURE P, DOCTORPROCEDURE DP " +
					"WHERE P.procedureNum = DP.procNum and DP.doctorID = " + "'" + v + "'";
		
		Statement stmt = conn.createStatement ();
		ResultSet rset = stmt.executeQuery(q);

	    System.out.println("\n");

	      // Iterate through the result
	    System.out.println("Procedures done by doctor: ");

	    while (rset.next ()) {
	        String pName = rset.getString("pName");
	        String pDate = rset.getString("pDate");
	        String pTime = rset.getString("pTime");
	        String description = rset.getString("description");   
	       
	  
	        
	        System.out.println ("Procedure Name: " + pName);
	        System.out.println ("Procedure Date: " + pDate);
	        System.out.println ("Procedure Time: " + pTime);
	        System.out.println ("Description: " + description);
	    }
	    
	    System.out.println("\n");
		
	}
	
	
	
	public static void addMedication(Connection conn) throws SQLException {

		PreparedStatement pstmt =
				conn.prepareStatement ("INSERT INTO MEDICATION_PRESCRIBED(unique_name, medicationDate, description, manufacturer) " +
						"VALUES (?, ?, ?, ?)");
		int done = 1;
		String patientID = "";
		String unique_name = "";
		String medicationDate = "";
		String description = "";
		String manufacturer = "";

		while (done != 0) {	
			System.out.println("\nEnter patientID: ");
			patientID = getString();

			System.out.println("\nEnter medicine name: ");
			unique_name = getString();

			System.out.println("\nEnter date of medication: ");
			medicationDate = getString();

			System.out.println("\nEnter medicine description: ");
			description = getString();

			System.out.println("\nEnter medicine manufacturer: ");
			manufacturer = getString();

			pstmt.setString(1, unique_name);
			pstmt.setString(2, medicationDate);
			pstmt.setString(3, description);
			pstmt.setString(4, manufacturer);

			int NumRows = pstmt.executeUpdate();
			addPatientMedication(conn, patientID, unique_name);
			System.out.println("\n" + NumRows + " row(s) inserted");

			System.out.println("\nHit 0 for exit, " +
					"or enter any other number for another insert: ");
					
			done = getInt();
		} // while done
		
		
	}
	
	public static void addPatientMedication(Connection conn, String patientID, String unique_name) throws SQLException {

		PreparedStatement pstmt =
				conn.prepareStatement ("INSERT INTO PATIENTMEDICATION( patientID, unique_name) " +
						"VALUES (?, ?)");
		

		pstmt.setString(1, patientID);
		pstmt.setString(2, unique_name);

		int NumRows = pstmt.executeUpdate();
		System.out.println("\n" + NumRows + " row(s) inserted");

		System.out.println("\nHit 0 for exit, " +
				"or enter any other number for another insert: ");
		

	}
	
	public static void addDepartment(Connection conn) throws SQLException {

		PreparedStatement pstmt =
				conn.prepareStatement ("INSERT INTO DEPARTMENT(Office_no, dCode, docID, dPhone, dName, dHead) " +
						"VALUES (?, ?, ?, ?, ?, ?)");
		int done = 1;
		String office_no = "";
		String dCode = "";
		String docID = "";
		String dPhone = "";
		String dName = "";
		String dHead = "";
		String officeRegex = "^\\d[1-5]$";
		

		while (done != 0) {	
			
			System.out.println("\nEnter unique office number: ");
			office_no = getString();
			
		     
			
			
			System.out.println("\nEnter department code: ");
			dCode = getString();

			System.out.println("\nEnter doctor ID of leading doctor: ");
			docID = getString();
					
			System.out.println("\nEnter department phone: ");
			dPhone = getString();
			
			System.out.println("\nEnter department name: ");
			dName = getString(); 
			
			System.out.println("\nEnter department head: ");
			dHead = getString();

			
			pstmt.setString(1, office_no);
			pstmt.setString(2, dCode);
			pstmt.setString(3, docID);
			pstmt.setString(4, dPhone);
			pstmt.setString(5, dName);
			pstmt.setString(6, dHead);
			

			int NumRows = pstmt.executeUpdate();
			System.out.println("\n" + NumRows + " row(s) inserted");

			System.out.println("\nHit 0 for exit, " +
					"or enter any other number for another insert: ");
			
			done = getInt();
			
		} // while done
		
	}

	
	
	public static String getString() {
		try {
		    StringBuffer buffer = new StringBuffer();
		    int c = System.in.read();
		    while (c != '\n' && c != -1) {
			  buffer.append((char)c);
			  c = System.in.read();
		    }
		    return buffer.toString().trim();
		}
		catch (IOException e){return "";}
	    }

	  public static int getInt() 

	  {
	      String s= getString();
	      return Integer.parseInt(s);
	  }

}