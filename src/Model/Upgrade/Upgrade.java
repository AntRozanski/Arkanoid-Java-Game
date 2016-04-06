package Model.Upgrade;

public class Upgrade
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

	public Upgrade(String name)
	{
		this.name = name;
	}

	public boolean inTypeOf(Upgrade upgr)
	{
		if (upgr.getName() == this.getName())
			return true;
		else if ((this.name == "BiggerRacketUpgrade" || this.name == "SmallerRacketUpgrade")
				&& (upgr.getName() == "BiggerRacketUpgrade" || upgr.getName() == "SmallerRacketUpgrade"))
			return true;
		else 
			return false;
	}

}
