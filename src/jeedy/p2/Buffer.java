package jeedy.p2;

import java.util.*;

/*
 * TODO 서버객체에 넘겨받은 패킷을 가지고 최소 버퍼 크기를 결정한다.
 */
public class Buffer {
	public static Buffer Instance;
	
	private int minBufferSize;	//최소 버퍼 사이즈
	private Map<Integer, Message> messages;
	private Buffer(){}
	
	public static Buffer getInstance(){
		if(Instance == null){
			Instance = new Buffer();	
		}
		return Instance;
	}

	public void packetsEnterBuffer(List<Packet> packets){
		Packet packet = null;
		Message message = null;
		messages = new HashMap<Integer,Message>();
		
		for(int i=0; i<packets.size(); i++){
			packet = packets.get(i);
			
			message = messages.get(Integer.valueOf(packet.getMsgNo()));
			if(message == null){
				message = new Message(packet);
				messages.put(Integer.valueOf(packet.getMsgNo()), message);
			}
			
			if(packet.getSData() == (message.getNowLastedDataPoint()+1)){
				message.pastPacket(packet);
			}else{
				message.pushPacket(packet);
				setMinBufferSize(message);
			}			
		}
		
		printBufferInformation();
	}

	private void printBufferInformation() {
		// TODO Auto-generated method stub
		Message message;
		
		Collection<Message> collection = messages.values();
		Iterator<Message> i = collection.iterator();
		
		System.out.print("\n");
		while(i.hasNext()){
			message = i.next();
			message.printNotEmprtyMessage();
			message.printWattingPacket();
		}
	}

	private void setMinBufferSize(Message message){
		int msgBufCap = message.getUseBufferCapacitys();
		if(msgBufCap > this.minBufferSize){
			this.minBufferSize = msgBufCap;
		}
		
		if(minBufferSize < 10){
			this.minBufferSize = 10;
		}
	}
	
	public int getMinBufferSize() {
		return minBufferSize;
	}
	
	public void bufferReset(){
		this.minBufferSize = 0;
		this.messages = null;
	}
}
