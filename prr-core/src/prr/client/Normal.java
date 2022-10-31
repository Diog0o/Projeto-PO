package prr.client;

import prr.communications.Communication;
import prr.client.Client;
import prr.client.Level;

public class Normal extends Level {
	
	private static final long serialVersionUID = 202208091753L;

	public Normal(Client client) {
		super(client);
	
	}
	@Override
	public void updateLevel() {
		if (this.get_client().ClientBalance() > 500){
			this.get_client().set_level(new Gold(this.get_client())); //apos realizar um pagamento
		}
	}

	@Override
	public String ShowLevel() {
		return "Normal";
	}

	@Override
	public float cost(Communication communication) {
		if (communication.ShowTypeofCommunication().equals("TEXT")){
			if (communication.get_charnumber() < 50){
				return 10;
			}if (communication.get_charnumber() >= 50 && communication.get_charnumber() < 100){
				return 16;
			}
			return communication.get_charnumber() * 2;
		}if (communication.ShowTypeofCommunication().equals("VIDEO")){
			if (communication.get_terminalSender().AreFriends(communication.get_terminalReceiver(), communication.get_terminalSender())){
				return (communication.get_minutes() * 30)/2;
			}
			return communication.get_minutes() *30;
		}else{
			if(communication.get_terminalSender().AreFriends(communication.get_terminalReceiver(), communication.get_terminalSender())){
				return (communication.get_minutes() * 20)/2;
			}
			return communication.get_minutes() * 20;
		}
	}
	
}
