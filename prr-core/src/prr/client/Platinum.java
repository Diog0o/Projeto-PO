package prr.client;

import prr.communications.Communication;
import prr.client.Client;
import prr.client.Level;

public class Platinum extends Level {
	
	private static final long serialVersionUID = 202208091753L;
	
	public Platinum (Client client) {
		super(client);
	
	}
	@Override
	public void updateLevel() {
		if (this.get_client().ConsecutiveText() == 2 && this.get_client().ClientBalance() > 0 ){
			this.get_client().set_level(new Gold(this.get_client()));
		}if (this.get_client().ClientBalance() <0){ //apos um pagamento
			this.get_client().set_level(new Normal(this.get_client()));
		}
	}

	@Override
	public String ShowLevel() {
		return "Platinum";
	}

	@Override
	public float cost(Communication communication) {
		if (communication.ShowTypeofCommunication().equals("TEXT")){
			if (communication.get_charnumber() < 50){
				return 0;
			}if (communication.get_charnumber() >= 50 && communication.get_charnumber() < 100){
				return 4;
			}
			return 4;
		}if (communication.ShowTypeofCommunication().equals("VIDEO")){
			if (communication.get_terminalSender().AreFriends(communication.get_terminalReceiver(), communication.get_terminalSender())){
				return (communication.get_minutes() * 10)/2;
			}
			return communication.get_minutes() *10;
		}else{
			if(communication.get_terminalSender().AreFriends(communication.get_terminalReceiver(), communication.get_terminalSender())){
				return (communication.get_minutes() * 10)/2;
			}
			return communication.get_minutes() * 10;
		}
	}
}

