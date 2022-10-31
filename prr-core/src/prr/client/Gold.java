package prr.client;

import prr.communications.Communication;
import prr.client.Client;
import prr.client.Level;

public class Gold extends Level {
	
	private static final long serialVersionUID = 202208091753L;
	
	public Gold (Client client) {
		super(client);
	
	}
	@Override
	public void updateLevel() {
		if (this.get_client().ClientBalance() < 0){
			this.get_client().set_level(new Normal(this.get_client())); //apos realizar uma comunicacao
		}
		if ( this.get_client().ConsecutiveVideo() == 5 && this.get_client().ClientBalance() > 0 ) {
			this.get_client().set_level(new Platinum(this.get_client()));
		}
	}

	@Override
	public String ShowLevel() {
		return "Gold";
	}

	@Override
	public float cost(Communication communication) {
		if (communication.ShowTypeofCommunication().equals("TEXT")){
			if (communication.get_charnumber() < 50){
				return 10;
			}if (communication.get_charnumber() >= 50 && communication.get_charnumber() < 100){
				return 10;
			}
			return communication.get_charnumber() * 2;
		}if (communication.ShowTypeofCommunication().equals("VIDEO")){
			if (communication.get_terminalSender().AreFriends(communication.get_terminalReceiver(), communication.get_terminalSender())){
				return (communication.get_minutes() * 20)/2;
			}
			return communication.get_minutes() *20;
		}else{
			if(communication.get_terminalSender().AreFriends(communication.get_terminalReceiver(), communication.get_terminalSender())){
				return (communication.get_minutes() * 10)/2;
			}
			return communication.get_minutes() * 10;
		}
	}
}
