package jeedy.p2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/*
 * TODO 클라이언트의 입력을 받는 객체. 
 * 이번프로젝트는 네트워크를 가상으로 해서 구현해야 하기 때문에 클라이언트의 입력은 콘솔에 의해 입력된다.
 */
public class Client {
	public static Client Instance;
	BufferedReader br;
	
	private Client(){
		br = new BufferedReader(new InputStreamReader( System.in));
	}
	
	public static Client getInstance(){
		if(Instance == null){
			Instance = new Client();
		}
		return Instance;
	}
	
	private String[] consolInput(){
		String sValue;
		String[] sValues;
		try{
			sValue = br.readLine();
			sValues = sValue.trim().split(" ");
			
		}catch(IOException io){
			io.printStackTrace();
			System.out.println( "#예외 발생#다시입력하세요 #");
			sValues = consolInput();
		}
		return sValues;
	}
	
	private int[] settingNM( int maxN, int maxM){
		int[] numbers = new int[2];
		String[] sValues;
		try{
			System.out.println( "#["+numbers.length+"]개 정수를 입력하세요#");
			sValues = consolInput();
			
			for( int i=0; i<2; i++){
//				System.out.println(" sValues["+i+"] = "+sValues[i]);
				numbers[i] = Integer.parseInt(sValues[i]);
			}
			
			if((numbers[0] == 0 && numbers[1] == 0)){
				return null;
			}

			if((numbers[0] < 1 || maxN < numbers[0])
				|| (numbers[1] < 1 || maxM < numbers[1])){
				throw new Exception("기준 값의 범위를 벗어났습니다.(1<n<5, 1<m<1000, n=0 && m=0 : 종료)");
			}
			
		}catch(NumberFormatException nfe){
			System.out.println("정수만 입력하세요");
			numbers = settingNM( maxN, maxM);
		}catch(ArrayIndexOutOfBoundsException aie){
			numbers = settingNM( maxN, maxM);
		}catch(Exception e){
			System.out.println(e.toString());
			numbers = settingNM( maxN, maxM);
		}
		return numbers;
	}
	
	private int[] settingMessageCapacitys(int maxN){
		int[] msgCapacitys = new int[maxN];
	
		String[] sValues;
		try{
			System.out.println( "#["+maxN+"]개 메시지 용량을 입력하세요#");
			sValues = consolInput();
			
			for( int i=0; i<maxN; i++){
//				System.out.println(" sValues["+i+"] = "+sValues[i]);
				msgCapacitys[i] = Integer.parseInt(sValues[i]);
			}
			for( int i=0; i<msgCapacitys.length; i++){
//				System.out.println( "msgCapacitys["+i+"] = "+msgCapacitys[i]);
				if(msgCapacitys[i]<0){
					throw new Exception("기준 값의 범위를 벗어났습니다.(0 미만의 바이트는 입력할 수 없습니다.)");
				}
			}
			
		}catch(NumberFormatException nfe){
			System.out.println("정수만 입력하세요");
			msgCapacitys = settingMessageCapacitys( maxN);
		}catch(ArrayIndexOutOfBoundsException aie){
			msgCapacitys = settingMessageCapacitys( maxN);
		}catch(Exception e){
			System.out.println(e.toString());
			msgCapacitys = settingMessageCapacitys( maxN);
		}
		return msgCapacitys;
	}
	
	private Packet settingPacket(int packatNo, int maxN, int[] messageCapacitys){
		Packet packet = null;
		int[] tempPacket = new int[3];
		
		String[] sValues;
		try{
			System.out.println( "# "+packatNo+" 패킷 정보를 입력하세요#");
			sValues = consolInput();
			
			for( int i=0; i<3; i++){
//				System.out.println(" sValues["+i+"] = "+sValues[i]);
				tempPacket[i] = Integer.parseInt(sValues[i]);
			}
			for( int i=0; i<tempPacket.length; i++){
//				System.out.println( "msgCapacitys["+i+"] = "+msgCapacitys[i]);
				if(tempPacket[i] < 0){
					throw new Exception("기준 값의 범위를 벗어났습니다.(0 미만의 바이트는 입력할 수 없습니다.)");
				}
			}
			
			packet = new Packet(tempPacket);
			
			int msgNo = packet.getMsgNo();
			
			if(msgNo > maxN){
				throw new Exception("메시지 넘버를 잘못 기입하셨습니다. ("+tempPacket[0]+"의 메시지는 없습니다."+maxN+"이하의 메시지 넘버를 입력하세요.)");
			}else if((packet.getSData()) > (packet.getEData())){
				throw new Exception("패킷 정보를 잘못 기입하셨습니다. 시작바이트는 끝바이트보다 큰 값이 와서는 안됩니다.");
			}else if(packet.getSData() > messageCapacitys[msgNo-1]
			        || packet.getEData() > messageCapacitys[msgNo-1]){
				throw new Exception("메시지 용량을 초과 기입하셨습니다. (메시지 "+msgNo+"의 용량은 "+messageCapacitys[msgNo-1]+"입니다.)");
			}
			packet.setTotalData(messageCapacitys[msgNo-1]);
		}catch(NumberFormatException nfe){
			System.out.println("정수만 입력하세요");
			packet = settingPacket(packatNo, maxN, messageCapacitys);
		}catch(ArrayIndexOutOfBoundsException aie){
			System.out.println("패킷정보가 부족합니다. (메시지 번호, 시작바이트, 끝바이트 순으로 입력하세요.)\n");
			packet = settingPacket(packatNo, maxN, messageCapacitys);
		}catch(Exception e){
			System.out.println(e.toString());
			packet = settingPacket(packatNo, maxN, messageCapacitys);
		}
		return packet;
	}
	
	private List<Packet> getPackets(int maxN,int maxM, int[] messageCapacitys){
		List<Packet> packets = new ArrayList<Packet>();
		
		for(int i=0; i<maxM; i++){
			packets.add(settingPacket(i, maxN, messageCapacitys));
		}
		
		return packets;
	}
	
	public List<Packet> testCase(){
		int[] numbers = settingNM( 5, 1000);
		if(numbers == null){
			return null;
		}
		
		int[] messageCapacitys = settingMessageCapacitys(numbers[0]);
		List<Packet> packets = getPackets(numbers[0], numbers[1], messageCapacitys);
		
//		Packet packet;
//		for(int i =0; i<packets.size(); i++){
//			packet = packets.get(i);
//			System.out.println("packet["+i+"] value : "
//					+"msgNo = "+packet.getMsgNo()
//					+", sData = "+packet.getSData()
//					+", eData = "+packet.getEData());
//		}
		return packets;
	}
	
	public void stop(){
		// TODO Auto-generated method stub
//		System.out.println("IO stream 객체 닫기");
		try{
			br.close();
		}catch(Exception e){}
	}
	
//	public static void main(String[] args)
//	{
//		Client c = new Client();	
//		c.run();
//		c.stop();
//	}
}
