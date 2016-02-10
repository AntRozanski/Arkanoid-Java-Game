package Model.Upgrade;

public abstract class Upgrade
{
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public abstract void start();

	public abstract boolean isOver();
	
}
