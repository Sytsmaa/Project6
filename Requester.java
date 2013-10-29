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
		tcpChannel.setMessageListener(this);
		run();
	}
	
	public static void main(String[] args)
	{
		int port = 0;
		
		try
		{
			port = Integer.parseInt(args[1]);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		TCPChannel tc = null;
		
		try
		{
			tc = new TCPChannel(args[0], port);
		}
		catch (ChannelException e)
		{
			e.printStackTrace();
		}
		
		Requester r = new Requester(tc);
	}
	
	public void run()
	{
		boolean isNumber = false;
		boolean isValid = false;
		int location = 0;
		String message = "";
		Scanner scanner = new Scanner(System.in);
		
		while (!isValid)
		{
			while (!isNumber)
			{
				isNumber = true;
				System.out.println("1. CL50 - Class of 1950 Lecture Hall");
				System.out.println("2. EE - Electrical Engineering Building");
				System.out.println("3. LWSN - Lawson Computer Science Building");
				System.out.println("4. PMU - Purdue Memorial Union");
				System.out.println("5. PUSH - Purdue University Student Health Center");
				System.out.print("Enter your location (1-5): ");
				
				if (!scanner.hasNextLine())
				{
					scanner.close();
					return;
				}
				
				try
				{
					location = Integer.parseInt(scanner.nextLine());
				}
				catch (Exception e)
				{
					System.out.println("Invalid number format. Please try again.");
					isNumber = false;
				}
			}
			
			isValid = true;
			
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
					System.out.println("Invalid input. Please try again.");
					isValid = false;
					isNumber = false;
			}
		}
		
		scanner.close();
		
		try
		{
			tcpChannel.sendMessage(message);
		}
		catch (ChannelException e)
		{
			e.printStackTrace();
		}
			
		System.out.println("Waiting for volunteer...");
	}
	
	public void messageReceived(String message, int clientID)
	{
		System.out.printf("Volunteer %s assigned and will arrive shortly.\n", 
				message.substring(message.indexOf(" ") + 1));
		run();
	}
}