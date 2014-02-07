package jeedy.p2;

import java.util.List;

/*
 * TODO 클라이언트의 입력을 받고 버퍼에 넘겨주는 객체.(Controller 역활)
 * 1. 클라이언트에서 패킷을 넘겨주면
 * 2. 생성된 패킷 정보를 Buffer 객체에 넘긴다.
 */
public class Server {
	Client client;
	Buffer buffer;
	
	public Server() {
		client = Client.getInstance();
		buffer = Buffer.getInstance();
	}
	
	public void run(){
		int i = 1;
		List<Packet> packets;
		
		while((packets = client.testCase()) != null){
			buffer.packetsEnterBuffer(packets);
			
			System.out.println("\nCase "+i+" : "+buffer.getMinBufferSize()+"\n\n");
			
			buffer.bufferReset();
			i++;
		}

		client.stop();
		System.out.println("종료합니다.");
	}
	
	public static void main(String[] args){
		new Server().run();
	}
}
