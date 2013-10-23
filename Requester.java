import java.util.Scanner;
import edu.purdue.cs.cs180.channel.Channel;
import edu.purdue.cs.cs180.channel.ChannelException;
import edu.purdue.cs.cs180.channel.MessageListener;
import edu.purdue.cs.cs180.channel.TCPChannel;

public class Requester implements MessageListener
{
	private TCPChannel tcpChannel;
	
	public Requester(Channel channel)
	{
		tcpChannel = (TCPChannel)channel;
	}
	
	public static void main(String[] args)
	{
		TCPChannel tc = null;
		
		try
		{
			tc = new TCPChannel(args[0], Integer.parseInt(args[1]));
		}
		catch (ChannelException e)
		{
			e.printStackTrace();
		}
		
		Requester r = new Requester(tc);
		r.run();
	}
	
	public void run()
	{
		Scanner scanner = new Scanner(System.in);
		boolean isNumber = false;
		int location = 0;
		
		while (!isNumber)
		{
			System.out.println("1. CL50 - Class of 1950 Lecture Hall");
			System.out.println("2. EE - Electrical Engineering Building");
			System.out.println("3. LWSN - Lawson Computer Science Building");
			System.out.println("4. PMU - Purdue Memorial Union");
			System.out.println("5. PUSH - Purdue University Student Health Center");
			System.out.println("Enter your location (1-5):");
			
			try
			{
				location = scanner.nextInt();
				isNumber = true;
			}
			catch (Exception e)
			{
				System.out.println("Please only enter numbers.");
			}
		}
		
		String message = "";
		
		switch (location)
		{
		case 1:
			message = "REQUEST CL50";
			break;
		case 2:
			message = "REQUEST EE";
			break;
		case 3:
			message = "REQUEST LWSN";
			break;
		case 4:
			message = "REQUEST PMU";
			break;
		case 5:
			message = "REQUEST PUSH";
			break;
		default:
			System.exit(0);
		}
			
		try
		{
			tcpChannel.sendMessage(message);
		}
		catch (ChannelException e)
		{
			e.printStackTrace();
		}
			
		System.out.println("Waiting for volunteer...");
		
		scanner.close();
	}
	
	public void messageReceived(String message, int clientID)
	{
		System.out.printf("Volunteer %s assigned and will arrive shortly.", message.substring(message.indexOf(" ") + 1));
		run();
	}
}