package jeedy.p2;

/*
 * TODO 패킷에 대한 정보를 가지고 있는 객체.
 */
public class Packet {
	private int msgNo;		// 메시지번호
	private int sData;		// 시작 데이터
	private int eData;		// 끝 데이터
	private int dataGap;	// 시작 데이터와 끝 데이터의 갭
	
	private int totalData;
	
	public int getTotalData() {
		return totalData;
	}

	public void setTotalData(int totalData) {
		this.totalData = totalData;
	}

	public Packet(int[] tempPacket){
		setMsgNo(tempPacket[0]);
		setSData(tempPacket[1]);
		setEData(tempPacket[2]);
		
		setDataGap(getEData()-getSData()+1);
	}
	
	private void setDataGap(int dataGap) {
		// TODO Auto-generated method stub
		if( dataGap < 0){
			dataGap = 0;
		}
		this.dataGap = dataGap;
	}
	public int getMsgNo() {
		return msgNo;
	}
	public void setMsgNo(int msgNo) {
		this.msgNo = msgNo;
	}
	public int getSData() {
		return sData;
	}
	public void setSData(int data) {
		sData = data;
	}
	public int getEData() {
		return eData;
	}
	public void setEData(int data) {
		eData = data;
	}
	public int getDataGap(){
		return dataGap;
	}
}
