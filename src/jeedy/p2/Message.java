package jeedy.p2;

import java.util.*;

public class Message {
	private int msgNo;						// 메시지 번호
	private int nowLastedDataPoint;			// 현재까지 버퍼링을 통과한 마지막 데이터
	private int useBufferCapacitys;			// 현재 사용중인 버퍼용량
	
	private int totalData;					// 메시지의 총 용량
	private List<Packet> waittingPackets;	// 대기중인 패킷들
	

	public Message(Packet packet){
		super();
		this.msgNo = packet.getMsgNo();
		this.totalData = packet.getTotalData();
		waittingPackets = new ArrayList<Packet>();
	}
	
	public void pushPacket(Packet packet){
		useBufferCapacitys += packet.getDataGap();
		waittingPackets.add(packet);
	}
	public void popPacket(){
		Packet packet;
		for(int i=0; i < waittingPackets.size() ; i++){
			packet = waittingPackets.get(i);
			if(packet.getSData() == (nowLastedDataPoint+1)){
				packet = waittingPackets.remove(i);
				useBufferCapacitys =- packet.getDataGap();

				pastPacket(packet);				
			}
		}
	}

	public int getMsgNo() {
		return msgNo;
	}

	public void pastPacket(Packet packet) {
		this.nowLastedDataPoint = packet.getEData();
		popPacket();
	}

	public int getNowLastedDataPoint() {
		return nowLastedDataPoint;
	}

	public int getUseBufferCapacitys() {
		return useBufferCapacitys;
	}

	public void printWattingPacket() {
		// TODO Auto-generated method stub
		Packet packet;
		
		if( (waittingPackets != null) && (0 < waittingPackets.size())){
			System.out.println("# 패킷 정보가 불안정하여 통과하지 못한 패킷이 있습니다.#");
			System.out.println("# "+msgNo+"메시지 : 통과하지 못한  패킷들#");
			for(int i=0; i<waittingPackets.size(); i++){
				packet = waittingPackets.get(i);
				
				System.out.println("packet ["+i+"] " +
						"msgNo = "+packet.getMsgNo()+
						", sData = "+packet.getSData()+
						", eData = "+packet.getEData());
			}
		}else{
			//System.out.println("# "+msgNo+"메시지 준비중인 패킷이 없습니다.#");
		}
	}

	public void printNotEmprtyMessage() {
		// TODO Auto-generated method stub
		if(nowLastedDataPoint < totalData){
			System.out.println("# "+msgNo+"메시지는 "+(totalData-nowLastedDataPoint)+"데이터가 정상적으로 들어오지 않았습니다.#");
			System.out.println("마지막으로 버퍼링을 통과한 데이터는 "+nowLastedDataPoint+"까지 입니다.");
		}
	}
}
