import java.util.ArrayList;
import edu.purdue.cs.cs180.channel.Channel;
import edu.purdue.cs.cs180.channel.ChannelException;
import edu.purdue.cs.cs180.channel.MessageListener;
import edu.purdue.cs.cs180.channel.TCPChannel;

public class Server implements MessageListener
{
	private ArrayList<Request> requesters = new ArrayList<Request>();
	private ArrayList<Integer> volunteers = new ArrayList<Integer>();
	private TCPChannel tcpChannel;
	
	public Server(Channel channel)
	{
		tcpChannel = (TCPChannel)channel;
	}
	
	public static void main(String[] args)
	{
		Server s = new Server(new TCPChannel(Integer.parseInt(args[0])));
	}
	
	public void messageReceived(String message, int clientID)
	{
		if ("REQUEST".equals(message.substring(0, message.indexOf(" "))))
		{
			requesters.add(new Request(message.substring(message.indexOf(" ") + 1), clientID));
			
			if (volunteers.size() != 0)
			{
				try
				{
					tcpChannel.sendMessage("LOCATION " + requesters.get(0).location, volunteers.get(0));
				}
				catch (ChannelException e)
				{
					e.printStackTrace();
				}
				
				try
				{
					tcpChannel.sendMessage("VOLUNTEER " + volunteers.get(0), requesters.get(0).id);
				}
				catch (ChannelException e)
				{
					e.printStackTrace();
				}
				
				volunteers.remove(0);
				requesters.remove(0);
			}
		}
		else if ("VOLUNTEER".equals(message.substring(0, message.indexOf(" "))))
		{
			volunteers.add(clientID);
			
			if (requesters.size() != 0)
			{
				try
				{
					tcpChannel.sendMessage("LOCATION " + requesters.get(0).location, volunteers.get(0));
				}
				catch (ChannelException e)
				{
					e.printStackTrace();
				}
				
				try
				{
					tcpChannel.sendMessage("VOLUNTEER " + volunteers.get(0), requesters.get(0).id);
				}
				catch (ChannelException e)
				{
					e.printStackTrace();
				}
				
				volunteers.remove(0);
				requesters.remove(0);
			}
		}
	}
}

class Request
{
	public String location;
	public int id;
	
	public Request(String location, int id)
	{
		this.location = location;
		this.id = id;
	}
}