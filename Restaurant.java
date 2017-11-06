import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
	
	private Table[] tables;
	private HashMap<String, Double> menu;
	private Queue<Group> line;
	private double revenue = 0;
	
	public Restaurant(Table[] t, HashMap<String, Double> m)
	{
		tables = t;
		menu = m;
		line = new LinkedBlockingQueue<Group>();
	}
	
	public void addGroup(Group g1)
	{
		line.add(g1);
		checkTables();
	}
	
	public boolean checkTables()
	{
		for(Group g : line)
		{
			Table t = getTable(g.getNumOfPeople());
			if( t != null)
			{
				System.out.println(g.getName() + ", your table is ready.");
				line.remove(g);
				t.filledUp(g);
				return true;
			}
			else
				System.out.println("Sorry, " + g.getName() + " , there are unfortunately no tables avaiable for your right now.");
		}
		
		return false;
			
	}
	
	private Table getTable(int numOfCustomers)
	{
		for(int x = 0; x < tables.length; x++)
		{
			if(numOfCustomers == tables[x].getCapacity() && tables[x].getAvailability())
			{
				
				return tables[x];
			}
		}
		return null;
	}
	
	public void emptyTable(Group g1)
	{
		
		revenue += g1.getFullPrice();
		
		for(int x = 0; x < tables.length; x++)
		{
			if(tables[x].getGroup() != null && tables[x].getGroup().equals(g1))
			{
				System.out.println("Bye, " + tables[x].getGroup().getName() + " please come again!");
				tables[x].emptyTable();
			}
		}
		
		checkTables();
	}
	
	public double getRevenue()
	{
		
		return revenue;
		
	}
	
	public boolean allFilledUp()
	{
		for(Table t : tables)
		{
			if(t.getAvailability())
				return false;
		}
		return true;
	}
	

}
