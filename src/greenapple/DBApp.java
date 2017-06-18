package greenapple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings("serial")
class DBAppException extends Exception {

	public DBAppException(String string) {
		super(string);
		// TODO Auto-generated constructor stub
	}

}

@SuppressWarnings("serial")
class DBEngineException extends Exception {

	public DBEngineException(String string) {
		super(string);
		// TODO Auto-generated constructor stub
	}

}
class Table implements Serializable{
	String name;
	String primaryKey;
	int numberPages;
	private String keyType;
	private int numberRows;
	private Hashtable<String,BPTree> indices;
	public Hashtable<String, BPTree> getIndices() {
		return indices;
	}
	public Table(String name, String primaryKey,String type) throws IOException{
	this.primaryKey=primaryKey;
	indices=new Hashtable<String,BPTree>();
	indices.put(primaryKey, new BPTree());
	this.name=name;
	this.keyType=type;
	numberPages = 0;
	numberRows=0;
	}
	public int getNumberPage(){
		return numberPages;
	}
	public int increase(){
		return ++numberPages;
	}
	public String getName(){
		return name;
	}
	public String getPrimaryKey(){
		return primaryKey;
	}
	public int getNumberRows() {
		return numberRows;
	}
	public void setNumberRows() {
		this.numberRows++;
	}
	public String getKeyType() {
		return keyType;
	}
	
}
@SuppressWarnings("serial")
class Pair implements Serializable{
	private Object key;
	private Object value;
	public Pair(Object key,Object value){
		this.setKey(key);
		this.setValue(value);
	}
	public Object getKey() {
		return key;
	}
	public void setKey(Object key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "<"+key+","+value+">";
	}
}
@SuppressWarnings("serial")
class Page implements Serializable{
	private Row [] rows;
	private boolean [] deleted;
	 Integer index;
	 Integer max;
	public Row [] getRows(){
		return rows;
	}
	public Page(int maximumRows){
		max=maximumRows;
		setDeleted(new boolean[maximumRows]);
		rows = new Row[max];
		index=0;
	}
	public void add(Row r){
		
		rows[index++]=r;
	}
	public boolean isFull(){
		return (index==max);
	}
	public String toString(){
		
		return Arrays.toString(rows);
	}
	public boolean [] getDeleted() {
		return deleted;
	}
	public void setDeleted(boolean [] deleted) {
		this.deleted = deleted;
	}
}
@SuppressWarnings("serial")
class Row implements Serializable{
	Pair tuple[];
	public Pair[] getTuple() {
		return tuple;
	}
	public void setTuple(Pair[] tuple) {
		this.tuple = tuple;
	}
	int  index;
	public Row(String tableName){
		int n= getColumnN(tableName);
		tuple= new Pair[n];
		index=0;
	}
	public void add(Pair p){
		
		tuple[index++]=p;
	}
	public int getColumnN(String name){
		try {
			BufferedReader br = new BufferedReader(new FileReader("data/metadata.csv"));
			String str="";
			int i=0;
			while((str=br.readLine())!=null){
				String table=str.split(",")[0];
				if(table.equals(name)){
					i++;
				}
						
						
			}br.close();
			return i;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return 0;
	}
	@Override
	public String toString() {
		return Arrays.toString(tuple);}
	
}
public class DBApp {
FileWriter fileWriter=null;
Hashtable <String,Table> tables;
int maximumRows;
	public void init( ){
		
    	try {
			fileWriter= new FileWriter("data/metadata.csv");
			tables= new Hashtable<String,Table>();
			fileWriter.append("Table Name, Column Name, Column Type, Key, Indexed, References\n");
			Properties prop = new Properties();
			InputStream inputStream = new FileInputStream("config/DBApp.properties");
			prop.load(inputStream);
			maximumRows=Integer.parseInt(prop.getProperty("MaximumRowsCountinPage"));
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("classes/greenapple/t@ble$.class"),false));
	   		oos.writeObject(tables);
	    	oos.close();
    	}catch(IOException e){
    		System.out.println("Choose a different path");
    }}
	
    public void createTable(String strTableName,    Hashtable<String,String> htblColNameType, 
                            Hashtable<String,String> htblColNameRefs, String strKeyColName)  throws DBAppException{
    	if(checkTableName(strTableName)){
    		throw new DBAppException("The Table Name already exists");
    	}
    	
    	Iterator<Map.Entry<String, String>>  it = htblColNameType.entrySet().iterator(); 
    	 
    	
    	String type="";
			while(it.hasNext()){
				String some="";

	    		Map.Entry<String,String> entry=it.next();
	    		try {
					some+=strTableName+","+entry.getKey()+",";
					
					
					
					switch(entry.getValue()){
					case "Integer": some+="java.lang.Integer,";
					type="java.lang.Integer";break;
					case "String" : some+="java.lang.String,";
					type="java.lang.String";break;
					case "Date": some+="java.util.Date,";
					type="java.util.Date";break;
					case "Double": some+="java.lang.Double,";
					type="java.lang.Double";break;
					case "Boolean": some+="java.lang.Boolean,";
					type="java.lang.Boolean";break;
					default: throw new DBAppException("The Data Base doesn't support "+entry.getValue());
					
					}
					if(strKeyColName.equals(entry.getKey())){
					some+="True,True,";
					}else{
						some+="False,False,";
					}
					boolean isRef=false;
					Iterator<Map.Entry<String, String>>  ref = htblColNameRefs.entrySet().iterator();
					while(ref.hasNext()){
						
						Map.Entry<String,String> entryRef=ref.next();
						
						if(entryRef.getKey().equals(entry.getKey())){
							some+=entryRef.getValue().toString()+"\n";
							isRef=true;
							if(!checkRef(entryRef.getValue(),entry.getValue())){
								throw new DBAppException("The Table or the Column you are try to reference doesn't exist or doesn't have the same type");
							}
							break;
						}
					}
					if(!isRef){
						some+="null\n";
					}
					fileWriter.append(some);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    	}
		
			
			try {
			
				
				fileWriter.append(strTableName+",TouchDate,java.util.Date,false,false,null\n");
				Table table = new Table(strTableName,strKeyColName,type);
				tables.put(strTableName, table);
				fileWriter.flush();
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("classes/greenapple/t@ble$.class"),false));
		   		oos.writeObject(tables);
		    	oos.close();
				
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
			}
			
    }
    	
    	
    

    public void createIndex(String strTableName, String strColName)  throws DBAppException{
    	Table table = tables.get(strTableName);
    	Hashtable<String, BPTree> index = table.getIndices();
    	try {
			BPTree b=new BPTree();
			 int i = table.getNumberPage();
				ObjectInputStream ois;
				try {for(int n =1;n<=i;n++){
					ois = new ObjectInputStream(new FileInputStream(new File("classes/greenapple/"+strTableName+n+".class")));
				Page p = (Page)ois.readObject();
				ois.close();
				for(int j = 0;j<p.getRows().length;j++){
					Row row=p.getRows()[j];
					if(row!=null&&p.getDeleted()[j]!=true){
						for(Pair tuple:row.getTuple()){
							
								if(strColName.equals(tuple.getKey()))
									b.insert((Comparable)tuple.getValue(), new Pair(n,j));
					}}
										
				}
				
				}
				index.put(strColName, b);
				ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(new File("classes/greenapple/t@ble$.class"),false));
		   		oos2.writeObject(tables);
		    	oos2.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    public void insertIntoTable(String strTableName, Hashtable<String,Object> htblColNameValue)  throws DBAppException{
    Table table = tables.get(strTableName);
    String primaryKey=table.getPrimaryKey();
    boolean flag = false;
    	if(!checkTableName(strTableName)){
    		throw new DBAppException("The Table Name doesn't exist!");
    	}
    	
    	Row row = new Row(strTableName);
    Iterator<Map.Entry<String, Object>>  ref = htblColNameValue.entrySet().iterator();
	while(ref.hasNext()){
		
		Map.Entry<String,Object> entry=ref.next();
		if(entry.getKey().equals(primaryKey)){
			if(entry.getValue()==null){
			throw new DBAppException("The Primary key can't be null");
		}else{ 
			flag=true;
		}}
		Pair p = new Pair(entry.getKey(),entry.getValue());
		
		if(!checkColumn(strTableName,entry.getKey(),entry.getValue())){
			throw new DBAppException("The column doesn't exist or wrong type");
		}
		BPTree b =table.getIndices().get(entry.getKey());
		if(b!=null){
			if(table.getNumberPage()==0){
			
				b.insert((Comparable)entry.getValue(), new Pair(1,table.getNumberRows()%maximumRows));
		}else{
			b.insert((Comparable)entry.getValue(), new Pair(table.getNumberPage(),table.getNumberRows()%maximumRows));
		}
			}
    	row.add(p);
    } if(!flag){
    	throw new DBAppException("You must insert a primary key");
    }
	Pair pair = new Pair("TouchDate",Calendar.getInstance().getTime());
    row.add(pair);
	
    int n = table.getNumberPage();
    ObjectInputStream ois;
    
	try {Page p;
		if(n==0||table.getNumberRows()%maximumRows==0){
		p= new Page(maximumRows);
		n=table.increase();
		table.setNumberRows();
		
		}
		else{
		ois = new ObjectInputStream(new FileInputStream(new File("classes/greenapple/"+strTableName+n+".class")));
		p = (Page)ois.readObject();
		table.setNumberRows();
		
	    ois.close();}
		
		p.add(row);
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(new File("classes/greenapple/t@ble$.class"),false));
   		oos2.writeObject(tables);
    	oos2.close();
	    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("classes/greenapple/"+strTableName+n+".class"),false));
   		oos.writeObject(p);
    	oos.close();
    	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    	
    
    }
    public boolean checkRef(String ref, String type){
    	switch(type){
		case "Integer": type="java.lang.Integer";break;
		case "String" : type="java.lang.String";break;
		case "Date": type="java.util.Date";break;
		case "Double": type="java.lang.Double";break;
		case "Boolean": type="java.lang.Boolean";break;
		
		
		}
    	
    	
    	ref=ref.replace('.', ',');
    	String str =ref.split(",")[0];
    	
    	String name=ref.split(",")[1];
    	try{
    	BufferedReader br = new BufferedReader(new FileReader("data/metadata.csv") );
    	String t="";
   
    	while((t=br.readLine())!=null){
    		String [] temp =t.split(",");
    		if(temp[0].equals(str)&&name.equals(temp[1])&&type.equals(temp[2])){
    			
    			return true;
    		}
    		
    		}}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return false;
    }
    public boolean checkTableName(String strTableName){
    	try{
    	BufferedReader br = new BufferedReader(new FileReader("data/metadata.csv") );
    	String t="";
    	while((t=br.readLine())!=null){
    		
    		if(t.split(",")[0].equals(strTableName)){
    			return true;
    			
    		}
    	}
    
    	br.close();}
    	catch(Exception e){
    		
    		e.printStackTrace();
    	}
    	
    	return false;
    }
    public boolean checkColumn(String tblName, String name, Object value){
    	try{
        	BufferedReader br = new BufferedReader(new FileReader("data/metadata.csv") );
        	String t="";
        	String type="";
        	if(value instanceof Integer){
        		type="java.lang.Integer";
        	}else{if(value instanceof Double){
        		type="java.lang.Double";
        	}else{
        		if(value instanceof String){
            		type="java.lang.String";
        		}else{
        			if(value instanceof Boolean){
                		type="java.lang.Boolean";}
        			else{
        				if(value instanceof Date){
        	        		type="java.util.Date";
        			}else{
        				return false;
        			}
        		}
        	}}}
        	while((t=br.readLine())!=null){
        		String [] temp =t.split(",");
        		if(temp[0].equals(tblName)&&name.equals(temp[1])&&type.equals(temp[2])){
        			
        			return true;
        		}
        	}
        
        	br.close();}
        	catch(Exception e){
        		
        		e.printStackTrace();
        	}
    	return false;
    	
    }
//assume that strKey is the value of the primary key of the row we want to update
    public void updateTable(String strTableName, Object strKey,
                            Hashtable<String,Object> htblColNameValue)  throws DBAppException{
    	if(!checkTableName(strTableName)){
    		throw new DBAppException(strTableName+" doesn't exist in the database.");
    	}
    	ObjectInputStream ois1;
		Table table = null;
		try {
			ois1 = new ObjectInputStream(new FileInputStream(new File("classes/greenapple/t@ble$.class")));
			table = tables.get(strTableName);
			ois1.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	
		Object key=null;
	
		if(strKey instanceof Integer){
    		key=(Integer) strKey;
    	}else{if(strKey instanceof Double){
    		key=(Double) strKey;
    	}else{
    		if(strKey instanceof String){
        		key=(String) strKey;
    		}}}
    	
    	Pair [] r= new Pair[htblColNameValue.size()];
    	Iterator<Map.Entry<String, Object>>  ref = htblColNameValue.entrySet().iterator();
    	int k =0;
    	while(ref.hasNext()){
    		
    		Map.Entry<String,Object> entry=ref.next();
    		Pair p = new Pair(entry.getKey(),entry.getValue());
    		if(!checkColumn(strTableName,entry.getKey(),entry.getValue())){
    			throw new DBAppException("The column doesn't exist or wrong type");
    		}
    		r[k++]=p;
    		
    	}BPTree b = table.getIndices().get(table.getPrimaryKey());
    	Pair pair = (Pair) b.search((Comparable) strKey);
		ObjectInputStream ois;
		try {
			if(pair!=null){
			ois = new ObjectInputStream(new FileInputStream(new File("classes/greenapple/"+table.getName()+pair.getKey()+".class")));
			Page p = (Page)ois.readObject();
			ois.close();
			boolean flag = true;
			Row row=p.getRows()[(int) pair.getValue()];
  			
  			
  					for(int h=0;h<row.getTuple().length;h++){
  						Pair tuple=row.getTuple()[h];
  		  				for(Pair cond:r){
  		  					if(cond.getKey().equals(tuple.getKey())){
  		  						
  		  						BPTree b2= table.getIndices().get(cond.getKey());
  		  						if(b!=null){
  		  							b.update((Comparable)tuple.getValue(), (Comparable) cond.getValue());
  		  						}
  		  					p.getRows()[(int) pair.getValue()].getTuple()[h] =new Pair(tuple.getKey(),cond.getValue());
  		  					}
  		  					
  		  				}
  		  				if(tuple.getKey().equals("TouchDate")){
  		  				p.getRows()[(int) pair.getValue()].getTuple()[h] =new Pair(tuple.getKey(),Calendar.getInstance().getTime());
  		  				flag=true;
  		  				}
  		  			}
  					if(flag){
  						ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("classes/greenapple/"+table.getName()+pair.getKey()+".class"),false));
	  			   		oos.writeObject(p);
	  			    	oos.close();
	  			    	return;
  					}
  		  			
  				}
  			
  		
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		} catch (ClassNotFoundException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
    }


    public void deleteFromTable(String strTableName, Hashtable<String,Object> htblColNameValue, 
                                String strOperator) throws DBEngineException{
    	if(!checkTableName(strTableName)){
    		throw new DBEngineException(strTableName+" doesn't exist in the database.");
    	}
    	
    	Pair [] r= new Pair[htblColNameValue.size()];
    	Iterator<Map.Entry<String, Object>>  ref = htblColNameValue.entrySet().iterator();
    	int i =0;
    	while(ref.hasNext()){
    		
    		Map.Entry<String,Object> entry=ref.next();
    		Pair p = new Pair(entry.getKey(),entry.getValue());
    		if(!checkColumn(strTableName,entry.getKey(),entry.getValue())){
    			throw new DBEngineException("The column doesn't exist or wrong type");
    		}
    		r[i++]=p;
    		
    	}
    	if(strOperator.equals("AND")){
    		 deleteWithAnd(strTableName,r);
    	}else{
    		 deleteWithOr(strTableName,r);
    	}
    	
                             
    }
		
    private void deleteWithOr(String strTableName, Pair[] r) {
		// TODO Auto-generated method stub
    	ObjectInputStream ois1;
		Table table = null;
		try {
			ois1 = new ObjectInputStream(new FileInputStream(new File("classes/greenapple/t@ble$.class")));
			table = tables.get(strTableName);
			ois1.close();
			BPTree b=null;
			for(Pair pair:r){
				b=table.getIndices().get(pair.getKey());
				if(b!=null){
				deleteIndexed(table,b,pair.getValue());
				}else{
					 int i = table.getNumberPage();
						ObjectInputStream ois;
						for(int n =1;n<=i;n++){
							ois = new ObjectInputStream(new FileInputStream(new File("classes/greenapple/"+strTableName+n+".class")));
						Page p = (Page)ois.readObject();
						ois.close();
						for(int j = 0;j<p.getRows().length;j++){
							Row row=p.getRows()[j];
							if(row!=null)
							for(Pair tuple:row.getTuple()){
								
									if(pair.getKey().equals(tuple.getKey())&&pair.getValue().equals(tuple.getValue())){
										for(Pair pair1:row.getTuple()){
											
											BPTree b1 =table.getIndices().get(pair1.getKey());
											if(b1!=null){
												
												b1.remove((Comparable) pair1.getValue());
											}}
										p.getDeleted()[j] =true;
							}
								}
							}
						ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("classes/greenapple/"+strTableName+n+".class"),false));
				   		oos.writeObject(p);
				    	oos.close();
				    	
							
						}
						
						}
						
						
				
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	   
		
		

		
	}

	private void deleteWithAnd(String strTableName, Pair[] r) {
		// TODO Auto-generated method stub
		ObjectInputStream ois1;
		Table table = null;
		try {
			ois1 = new ObjectInputStream(new FileInputStream(new File("classes/greenapple/t@ble$.class")));
			table = tables.get(strTableName);
			ois1.close();
			BPTree b=null;
			for(Pair pair:r){
				b=table.getIndices().get(pair.getKey());
				if(b!=null){
				deleteIndexedAnd(table,b,pair.getValue(),r);
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		
	    int i = table.getNumberPage();
		ObjectInputStream ois;
		try {for(int n =1;n<=i;n++){
			ois = new ObjectInputStream(new FileInputStream(new File("classes/greenapple/"+strTableName+n+".class")));
		Page p = (Page)ois.readObject();
		ois.close();
		for(int j =0;j<p.getRows().length;j++){
			Row row=p.getRows()[j];
			boolean flag=true;
			if(row==null)continue;
			for(Pair tuple:row.getTuple()){
				for(Pair cond:r){
					if(cond.getKey().equals(tuple.getKey())&&!cond.getValue().equals(tuple.getValue()))
				        flag=false;
					break;
				}
				if(flag==false){
					break;
				}
			}
			if(flag==true)
				p.getDeleted()[j] =true;
			for(Pair tuple:row.getTuple()){
				BPTree b =table.getIndices().get(tuple);
				if(b!=null){
					b.remove((Comparable) tuple.getValue());
				}
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("classes/greenapple/"+strTableName+n+".class"),false));
	   		oos.writeObject(p);
	    	oos.close();
	    	
		}
		
		}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

	private void deleteIndexedAnd(Table table, BPTree b, Object value, Pair[] r) {
		// TODO Auto-generated method stub
		Pair pair = (Pair) b.search((Comparable) value);
		ObjectInputStream ois;
		
			if(pair!=null){
			try {
				ois = new ObjectInputStream(new FileInputStream(new File("classes/greenapple/"+table.getName()+pair.getKey()+".class")));
				Page p = (Page)ois.readObject();
				ois.close();
				Row row = p.getRows()[(int) pair.getValue()];
				boolean flag=true;
				for(Pair tuple:row.getTuple()){
					for(Pair cond:r){
						if(cond.getKey().equals(tuple.getKey())&&!cond.getValue().equals(tuple.getValue()))
					        flag=false;
						break;
					}
					if(flag==false){
						break;
					}
				}
				if(flag==true){
					p.getDeleted()[(int) pair.getValue()]=true;
					for(Pair pair1:row.getTuple()){
						BPTree b1 =table.getIndices().get(pair1.getKey());
						if(b1!=null){
							b1.remove((Comparable) pair1.getValue());
						}
					}
					ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(new File("classes/greenapple/t@ble$.class"),false));
			   		oos2.writeObject(tables);
			    	oos2.close();
			    	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("classes/greenapple/"+table.getName()+pair.getKey()+".class"),false));
			   		oos.writeObject(p);
			    	oos.close();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	}

	private void deleteIndexed(Table table, BPTree b, Object value) {
		// TODO Auto-generated method stub
		Pair pair = (Pair) b.search((Comparable) value);
		ObjectInputStream ois;
		
			if(pair!=null){
			try {
				ois = new ObjectInputStream(new FileInputStream(new File("classes/greenapple/"+table.getName()+pair.getKey()+".class")));
				Page p = (Page)ois.readObject();
				ois.close();
				p.getDeleted()[(int) pair.getValue()]=true;
				for(Pair pair1:p.getRows()[(int) pair.getValue()].getTuple()){
					BPTree b1 =table.getIndices().get(pair1.getKey());
					if(b1!=null){
						b1.remove((Comparable) pair1.getValue());
					}}
				
				ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(new File("classes/greenapple/t@ble$.class"),false));
		   		oos2.writeObject(tables);
		    	oos2.close();
		    	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("classes/greenapple/"+table.getName()+pair.getKey()+".class"),false));
		   		oos.writeObject(p);
		    	oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	}

	public Iterator<Row> selectFromTable(String strTable,  Hashtable<String,Object> htblColNameValue, 
                                    String strOperator) throws DBEngineException{
    	if(!checkTableName(strTable)){
    		throw new DBEngineException(strTable+" doesn't exist in the database.");
    	}
    	
    	Pair [] r= new Pair[htblColNameValue.size()];
    	Iterator<Map.Entry<String, Object>>  ref = htblColNameValue.entrySet().iterator();
    	int i =0;
    	while(ref.hasNext()){
    		
    		Map.Entry<String,Object> entry=ref.next();
    		Pair p = new Pair(entry.getKey(),entry.getValue());
    		if(!checkColumn(strTable,entry.getKey(),entry.getValue())){
    			throw new DBEngineException("The column doesn't exist or wrong type");
    		}
    		r[i++]=p;
    		
    	}
    	
    	if(strOperator.equals("AND")){
    		return (Iterator<Row>) selectWithAnd(strTable,r).iterator();
    	}else{
    		return (Iterator<Row>) selectWithOr(strTable,r).iterator();
    	}
    }

	private ArrayList<Row> selectWithOr(String strTableName, Pair[] r) {
		// TODO Auto-generated method stub
		ObjectInputStream ois1;
		Table table = null;
		ArrayList<Row> result=new ArrayList<Row>();
		try {
			ois1 = new ObjectInputStream(new FileInputStream(new File("classes/greenapple/t@ble$.class")));
			table = tables.get(strTableName);
			ois1.close();
			
			
			for(Pair pair:r){
				BPTree b=table.getIndices().get(pair.getKey());
				if(b!=null){
					selectIndexedOr(table,b,pair.getValue(),r,result);
					
				}else{
					int i = table.getNumberPage();
					ObjectInputStream ois;
					try {for(int n =1;n<=i;n++){
						ois = new ObjectInputStream(new FileInputStream(new File("classes/greenapple/"+strTableName+n+".class")));
					Page p = (Page)ois.readObject();
					ois.close();
					for(int k=0;k<p.getRows().length;k++){
						Row row=p.getRows()[k];
						if(row!=null&&p.getDeleted()[k]!=true){
							
						for(Pair tuple:row.getTuple()){
								if(pair.getKey().equals(tuple.getKey())&&pair.getValue().equals(tuple.getValue())){
							       result.add(row);
							       
							       
							      }
						}
					}
					}
					
					}
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		
	    		return result;
		

	
	}

	private void selectIndexedOr(Table table, BPTree b, Object value, Pair[] r, ArrayList<Row> result) {
		// TODO Auto-generated method stub
		Pair pair = (Pair) b.search((Comparable) value);
		ObjectInputStream ois;
		
		
			if(pair!=null){
			try {
				ois = new ObjectInputStream(new FileInputStream(new File("classes/greenapple/"+table.getName()+pair.getKey()+".class")));
				Page p = (Page)ois.readObject();
				ois.close();
				Row row=p.getRows()[(int) pair.getValue()];
				result.add(row);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}}

	private ArrayList<Row> selectWithAnd(String strTableName, Pair[] r) {
		// TODO Auto-generated method stub
		ObjectInputStream ois1;
		Table table = null;
		try {
			ois1 = new ObjectInputStream(new FileInputStream(new File("classes/greenapple/t@ble$.class")));
			table = tables.get(strTableName);
			ois1.close();
			BPTree b=null;
			for(Pair pair:r){
				b=table.getIndices().get(pair.getKey());
				if(b!=null){
					return selectIndexedAnd(table,b,pair.getValue(),r);
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		ArrayList<Row> result=new ArrayList<Row>();
	    int i = table.getNumberPage();
		ObjectInputStream ois;
		try {for(int n =1;n<=i;n++){
			ois = new ObjectInputStream(new FileInputStream(new File("classes/greenapple/"+strTableName+n+".class")));
		Page p = (Page)ois.readObject();
		ois.close();
		for(int k=0;k<p.getRows().length;k++){
			Row row=p.getRows()[k];
			boolean flag=true;
			if(row==null||p.getDeleted()[k]==true){
				
				continue;}
			for(Pair tuple:row.getTuple()){
				for(Pair cond:r){
					if(cond.getKey().equals(tuple.getKey())&&!cond.getValue().equals(tuple.getValue()))
				        flag=false;
					break;
				}
				if(flag==false){
					break;
				}
			}
			if(flag==true)
			result.add(row);
		}
		
		}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		

	}

	private ArrayList<Row> selectIndexedAnd(Table table, BPTree b, Object value, Pair[] r) {
		// TODO Auto-generated method stub
		Pair pair = (Pair) b.search((Comparable) value);
		ObjectInputStream ois;
		try {
			if(pair!=null){
			ois = new ObjectInputStream(new FileInputStream(new File("classes/greenapple/"+table.getName()+pair.getKey()+".class")));
			Page p = (Page)ois.readObject();
			ois.close();
			boolean flag = true;
			Row row=p.getRows()[(int) pair.getValue()];
			for(Pair tuple:row.getTuple()){
				for(Pair cond:r){
					if(cond.getKey().equals(tuple.getKey())&&!cond.getValue().equals(tuple.getValue()))
				        {flag=false;
					break;}
				}
				if(flag==false){
					break;
				}
			}
			if(flag==true){
			ArrayList<Row> list= new ArrayList<Row>();
			list.add(row);
			return list;
			}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Row>();
		
	}


	


}
