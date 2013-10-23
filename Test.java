public class Test
{
	public static void main(String[] args)
	{
		Server.main(new String[]{"8000"});
		Requester.main(new String[]{"localhost", "8000"});
		Volunteer.main(new String[]{"localhost", "8000"});
	}
}