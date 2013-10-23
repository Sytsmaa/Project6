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
		tcpChannel.setMessageListener(this);
		
		System.out.println("Press ENTER when ready:");
		new Scanner(System.in).nextLine();
		
		try
		{
			tcpChannel.sendMessage("VOLUNTEER " + tcpChannel.getID());
		}
		catch (ChannelException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Waiting for assignment...");
	}

	public void messageReceived(String message, int clientID)
	{
		System.out.printf("Proceed to %s\n", message.substring(message.indexOf(" ") + 1));
	}
}