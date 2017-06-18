package greenapple;

import java.util.Hashtable;
import java.util.Iterator;

public class DBAppTest {
	@SuppressWarnings("rawtypes")
	public static void main(String [] args) throws DBAppException, DBEngineException {
		// create a new DBApp
		DBApp myDB = new DBApp();
//
//		// initialise it
		myDB.init();
		System.out.println("The data base is created");
//		// creating table "Faculty"
//
//		Hashtable<String, String> fTblColNameType = new Hashtable<String, String>();
//		fTblColNameType.put("ID", "Integer");
//		fTblColNameType.put("Name", "String");
//
//		Hashtable<String, String> fTblColNameRefs = new Hashtable<String, String>();
//
//		myDB.createTable("Faculty", fTblColNameType, fTblColNameRefs, "ID");
//
//		// creating table "Major"
//
//		Hashtable<String, String> mTblColNameType = new Hashtable<String, String>();
//		mTblColNameType.put("ID", "Integer");
//		mTblColNameType.put("Name", "String");
//		mTblColNameType.put("Faculty_ID", "Integer");
//
//		Hashtable<String, String> mTblColNameRefs = new Hashtable<String, String>();
//		mTblColNameRefs.put("Faculty_ID", "Faculty.ID");
//
//		myDB.createTable("Major", mTblColNameType, mTblColNameRefs, "ID");
//
//		// creating table "Course"
//
//		Hashtable<String, String> coTblColNameType = new Hashtable<String, String>();
//		coTblColNameType.put("ID", "Integer");
//		coTblColNameType.put("Name", "String");
//		coTblColNameType.put("Code", "String");
//		coTblColNameType.put("Hours", "Integer");
//		coTblColNameType.put("Semester", "Integer");
//		coTblColNameType.put("Major_ID", "Integer");
//
//		Hashtable<String, String> coTblColNameRefs = new Hashtable<String, String>();
//		coTblColNameRefs.put("Major_ID", "Major.ID");
//
//		myDB.createTable("Course", coTblColNameType, coTblColNameRefs, "ID");
//
//		// creating table "Student"
//
//		Hashtable<String, String> stTblColNameType = new Hashtable<String, String>();
//		stTblColNameType.put("ID", "Integer");
//		stTblColNameType.put("First_Name", "String");
//		stTblColNameType.put("Last_Name", "String");
//		stTblColNameType.put("GPA", "Double");
//		stTblColNameType.put("Age", "Integer");
//
//		Hashtable<String, String> stTblColNameRefs = new Hashtable<String, String>();
//
//		myDB.createTable("Student", stTblColNameType, stTblColNameRefs, "ID");
//
//		// creating table "Student in Course"
//
//		Hashtable<String, String> scTblColNameType = new Hashtable<String, String>();
//		scTblColNameType.put("ID", "Integer");
//		scTblColNameType.put("Student_ID", "Integer");
//		scTblColNameType.put("Course_ID", "Integer");
//
//		Hashtable<String, String> scTblColNameRefs = new Hashtable<String, String>();
//		scTblColNameRefs.put("Student_ID", "Student.ID");
//		scTblColNameRefs.put("Course_ID", "Course.ID");
//
//		myDB.createTable("Student_in_Course", scTblColNameType, scTblColNameRefs, "ID");
//
//		// insert in table "Faculty"
//
//		Hashtable<String,Object> ftblColNameValue1 = new Hashtable<String,Object>();
//		ftblColNameValue1.put("ID", Integer.valueOf( "1" ) );
//		ftblColNameValue1.put("Name", "Media Engineering and Technology");
//		myDB.insertIntoTable("Faculty", ftblColNameValue1);
//
//		Hashtable<String,Object> ftblColNameValue2 = new Hashtable<String,Object>();
//		ftblColNameValue2.put("ID", Integer.valueOf( "2" ) );
//		ftblColNameValue2.put("Name", "Management Technology");
//		myDB.insertIntoTable("Faculty", ftblColNameValue2);
//		for(int i=0;i<1000;i++)
//		{
//				Hashtable<String,Object> ftblColNameValueI = new Hashtable<String,Object>();
//				ftblColNameValueI.put("ID", Integer.valueOf( (""+(i+2)) ) );
//				ftblColNameValueI.put("Name", "f"+(i+2));
//				myDB.insertIntoTable("Faculty", ftblColNameValueI);
//			}
//		
//		// insert in table "Major"
//
//		Hashtable<String,Object> mtblColNameValue1 = new Hashtable<String,Object>();
//		mtblColNameValue1.put("ID", Integer.valueOf( "1" ) );
//		mtblColNameValue1.put("Name", "Computer Science & Engineering");
//		mtblColNameValue1.put("Faculty_ID", Integer.valueOf( "1" ) );
//		myDB.insertIntoTable("Major", mtblColNameValue1);
//
//		Hashtable<String,Object> mtblColNameValue2 = new Hashtable<String,Object>();
//		mtblColNameValue2.put("ID", Integer.valueOf( "2" ));
//		mtblColNameValue2.put("Name", "Business Informatics");
//		mtblColNameValue2.put("Faculty_ID", Integer.valueOf( "2" ));
//		myDB.insertIntoTable("Major", mtblColNameValue2);
//
//		for(int i=0;i<1000;i++)
//		{
//			Hashtable<String,Object> mtblColNameValueI = new Hashtable<String,Object>();
//			mtblColNameValueI.put("ID", Integer.valueOf( (""+(i+2) ) ));
//			mtblColNameValueI.put("Name", "m"+(i+2));
//			mtblColNameValueI.put("Faculty_ID", Integer.valueOf( (""+(i+2) ) ));
//			myDB.insertIntoTable("Major", mtblColNameValueI);
//		}
//
//
//		// insert in table "Course"
//
//		Hashtable<String,Object> ctblColNameValue1 = new Hashtable<String,Object>();
//		ctblColNameValue1.put("ID", Integer.valueOf( "1" ) );
//		ctblColNameValue1.put("Name", "Data Bases II");
//		ctblColNameValue1.put("Code", "CSEN 604");
//		ctblColNameValue1.put("Hours", Integer.valueOf( "4" ));
//		ctblColNameValue1.put("Semester", Integer.valueOf( "6" ));
//		ctblColNameValue1.put("Major_ID", Integer.valueOf( "1" ));
//		myDB.insertIntoTable("Course", ctblColNameValue1);
//
//		Hashtable<String,Object> ctblColNameValue2 = new Hashtable<String,Object>();
//		ctblColNameValue2.put("ID", Integer.valueOf( "1" ) );
//		ctblColNameValue2.put("Name", "Data Bases II");
//		ctblColNameValue2.put("Code", "CSEN 604");
//		ctblColNameValue2.put("Hours", Integer.valueOf( "4" ) );
//		ctblColNameValue2.put("Semester", Integer.valueOf( "6" ) );
//		ctblColNameValue2.put("Major_ID", Integer.valueOf( "2" ) );
//		myDB.insertIntoTable("Course", ctblColNameValue2);
//
//		for(int i=0;i<1000;i++)
//		{
//			Hashtable<String,Object> ctblColNameValueI = new Hashtable<String,Object>();
//			ctblColNameValueI.put("ID", Integer.valueOf( ( ""+(i+2) )));
//			ctblColNameValueI.put("Name", "c"+(i+2));
//			ctblColNameValueI.put("Code", "co "+(i+2));
//			ctblColNameValueI.put("Hours", Integer.valueOf( "4" ) );
//			ctblColNameValueI.put("Semester", Integer.valueOf( "6" ) );
//			ctblColNameValueI.put("Major_ID", Integer.valueOf( ( ""+(i+2) )));
//			myDB.insertIntoTable("Course", ctblColNameValueI);
//		}
//
//		// insert in table "Student"
//
//		for(int i=0;i<1000;i++)
//		{
//			Hashtable<String,Object> sttblColNameValueI = new Hashtable<String,Object>();
//			sttblColNameValueI.put("ID", Integer.valueOf( ( ""+i ) ) );
//			sttblColNameValueI.put("First_Name", "FN"+i);
//			sttblColNameValueI.put("Last_Name", "LN"+i);
//			sttblColNameValueI.put("GPA", Double.valueOf( "0.7" ) ) ;
//			sttblColNameValueI.put("Age", Integer.valueOf( "20" ) );
//			myDB.insertIntoTable("Student", sttblColNameValueI);
//		//changed it to student instead of course
//		}
//		System.out.println("Fininshed inserting");
//
//		// deleting
//		
//		
//		Hashtable<String,Object> stblColNameValue = new Hashtable<String,Object>();
//		stblColNameValue.put("ID", Integer.valueOf( "55" ) );
//		//stblColNameValue.put("Age", Integer.valueOf( "20" ) );
//
//		long startTime = System.currentTimeMillis();
//		myDB.deleteFromTable("Student", stblColNameValue,"OR");
//		long endTime   = System.currentTimeMillis();
//		long totalTime = endTime - startTime;
//		System.out.println(totalTime);
//		
//		
//		
//		// selecting
//
//
//		//Hashtable<String,Object> stblColNameValue = new Hashtable<String,Object>();
//		//stblColNameValue.put("ID", Integer.valueOf( "550" ) );
//		//stblColNameValue.put("Age", Integer.valueOf( "20" ) );
//
//		//long startTime = System.currentTimeMillis();
//		Iterator myIt = myDB.selectFromTable("Student", stblColNameValue,"OR");
//		//long endTime   = System.currentTimeMillis();
//		//long totalTime = endTime - startTime;
//		//System.out.println(totalTime);
//		while(myIt.hasNext()) {
//			System.out.println(myIt.next());
//		}
//		System.out.println("After the delete");
//
//		// feel free to add more tests
//        Hashtable<String,Object> stblColNameValue3 = new Hashtable<String,Object>();
//		stblColNameValue3.put("Name", "m7");
//		stblColNameValue3.put("Faculty_ID", Integer.valueOf( "7" ) );
//
//        long startTime2 = System.currentTimeMillis();
//		Iterator myIt2 = myDB.selectFromTable("Major", stblColNameValue3,"AND");
//		long endTime2   = System.currentTimeMillis();
//		long totalTime2 = endTime2 - startTime2;
//		System.out.println(totalTime2);
//		while(myIt2.hasNext()) {
//			System.out.println(myIt2.next());
//		}
//		//creating Faux data
		Hashtable<String,String> fTblColNameType = new Hashtable<String, String>();
		fTblColNameType.put("ID", "String");
		fTblColNameType.put("Name", "String");

		Hashtable<String,String>  fTblColNameRefs = new Hashtable<String, String>();

		myDB.createTable("csStudent", fTblColNameType, fTblColNameRefs, "ID");

		for(int i=0;i<10;i++)
			{
				Hashtable<String,Object> sttblColNameValueI = new Hashtable<String,Object>();
				sttblColNameValueI.put("ID", ""+i );
				sttblColNameValueI.put("Name", "FN"+i);
				myDB.insertIntoTable("csStudent", sttblColNameValueI);
			
			}
		Hashtable<String,Object> stblColNameValue = new Hashtable<String,Object>();
			stblColNameValue.put("ID", "0" );
			stblColNameValue.put("Name", "FN3" );
		Iterator myIt = myDB.selectFromTable("csStudent", stblColNameValue,"OR");
			
			while(myIt.hasNext()) {
				System.out.println(myIt.next());
			}
			myDB.deleteFromTable("csStudent", stblColNameValue, "OR");
			System.out.println("Delete");
			Hashtable<String,Object> stblColNameValue2 = new Hashtable<String,Object>();
			stblColNameValue2.put("Name", "FN0" );
			stblColNameValue2.put("ID", "3" );
		myIt = myDB.selectFromTable("csStudent", stblColNameValue2,"OR");
			
			while(myIt.hasNext()) {
				System.out.println(myIt.next());
			}
			Hashtable<String,Object> update=new Hashtable<String,Object>();
					update.put("Name", "FN5000" );
			myDB.updateTable("csStudent","5" ,update);
			stblColNameValue2 = new Hashtable<String,Object>();
			System.out.println("Update");
			stblColNameValue2.put("ID", "5" );
			myIt = myDB.selectFromTable("csStudent", stblColNameValue2,"OR");
			while(myIt.hasNext()) {
				System.out.println(myIt.next());
			}
			myDB.createIndex("csStudent", "Name");
			System.out.println("Index on Name created");
			stblColNameValue2 = new Hashtable<String,Object>();
			stblColNameValue2.put("Name", "FN20" );
			myIt = myDB.selectFromTable("csStudent", stblColNameValue2,"OR");
			while(myIt.hasNext()) {
				System.out.println(myIt.next());
			}
//		
//		
//		 stblColNameValue = new Hashtable<String,Object>();
//		stblColNameValue.put("ID", Integer.valueOf( "0" ) );
//		
//
//		//long startTime = System.currentTimeMillis();
//		 myIt = myDB.selectFromTable("csStudent", stblColNameValue,"AND");
//		//long endTime   = System.currentTimeMillis();
//		//long totalTime = endTime - startTime;
//		//System.out.println(totalTime);
//		while(myIt.hasNext()) {
//			System.out.println(myIt.next());
//		}
//		System.out.println("The Second");
//		Hashtable<String,Object> stblColNameValue1 = new Hashtable<String,Object>();
//		stblColNameValue1.put("ID", Integer.valueOf( "1" ) );
//		stblColNameValue1.put("Name","FN5");
//
//		//long startTime = System.currentTimeMillis();
//		Iterator myIt1 = myDB.selectFromTable("csStudent", stblColNameValue1,"OR");
//		//long endTime   = System.currentTimeMillis();
//		//long totalTime = endTime - startTime;
//		//System.out.println(totalTime);
//		while(myIt1.hasNext()) {
//			System.out.println(myIt1.next());
//		}
//		Hashtable<String,Object> stblColNameValue2 = new Hashtable<String,Object>();
//		stblColNameValue2.put("Name","FN5");
//		myDB.deleteFromTable("csStudent", stblColNameValue2,"OR");
//		System.out.println("After the delete");
//		 myIt1 = myDB.selectFromTable("csStudent", stblColNameValue1,"OR");
//		//long endTime   = System.currentTimeMillis();
//		//long totalTime = endTime - startTime;
//		//System.out.println(totalTime);
//		while(myIt1.hasNext()) {
//			System.out.println(myIt1.next());}
//	
//	
//	myDB.updateTable("csStudent",new Integer(1), stblColNameValue2);
//System.out.println("After the update");
//	myIt1 = myDB.selectFromTable("csStudent", stblColNameValue1,"OR");
//	//long endTime   = System.currentTimeMillis();
//	//long totalTime = endTime - startTime;
//	//System.out.println(totalTime);
//	while(myIt1.hasNext()) {
//		System.out.println(myIt1.next());}
//
//	//update  will update the age of the student with id=550 to 23 
//
//
//	stblColNameValue = new Hashtable<String,Object>();
//	stblColNameValue.put("Age", Integer.valueOf( "23" ) );
//	myDB.updateTable("Student",new Integer(550),stblColNameValue);
//	myIt1 = myDB.selectFromTable("Student", stblColNameValue,"OR");
//	//long endTime   = System.currentTimeMillis();
//	//long totalTime = endTime - startTime;
//	//System.out.println(totalTime);
//	while(myIt1.hasNext()) {
//		System.out.println(myIt1.next());}


	}
	
}
