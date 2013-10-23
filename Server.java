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
	
	//add new volunteers/requesters to the lists and then remove peoples
	//figure out a way to get location and id
	//figure out what datatypes for lists
	public void messageReceived(String message, int clientID)
	{
		if ("REQUEST".equals(message.substring(0, message.indexOf(" "))))
		{
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
			else
				requesters.add(new Request(message.substring(message.indexOf(" ") + 1), clientID));
		}
		else if ("VOLUNTEER".equals(message.substring(0, message.indexOf(" "))))
		{
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
			else
				volunteers.add(clientID);
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