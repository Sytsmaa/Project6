import java.util.Scanner;
import edu.purdue.cs.cs180.channel.Channel;
import edu.purdue.cs.cs180.channel.ChannelException;
import edu.purdue.cs.cs180.channel.MessageListener;
import edu.purdue.cs.cs180.channel.TCPChannel;

public class Volunteer implements MessageListener
{
	private TCPChannel tcpChannel;
	
	public Volunteer(Channel channel)
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
		
		Volunteer v = new Volunteer(tc);
		v.run();
	}
	
	public void run()
	{
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Press ENTER when ready:");
		scanner.next();
		
		try
		{
			tcpChannel.sendMessage("VOLUNTEER " + tcpChannel.getID());
		}
		catch (ChannelException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Waiting for assignment...");
		
		scanner.close();
	}

	public void messageReceived(String message, int clientID)
	{
		System.out.printf("Proceed to %s", message.substring(message.indexOf(" ") + 1));
	}
}