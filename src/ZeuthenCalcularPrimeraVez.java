package FinalUltimo2;

import java.util.Vector;

import FinalUltimo2.Ontologia.IsMyZeuthen;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ZeuthenCalcularPrimeraVez extends Behaviour{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ClaveUltimoMensajeRecibido="mensajeRecibido";
	private static final String ClaveUltimoMensajeZeuthen="mensajeZeuthen";
	private static final String  ClaveMensajeTemplateZeuthen="ClaveMensajeTemplateZeuthen";
	private boolean fin;
	//private IsMyZeuthen zeuthen;
	private int posZeuthenEnviado;
	private Vector<Float> zeuthen;
	public ZeuthenCalcularPrimeraVez(Vector<Float> zeuthen) {
		// TODO Auto-generated constructor stub
		//zeuthen=new IsMyZeuthen(0);
		this.zeuthen=zeuthen;
		posZeuthenEnviado=0;
		
	}

	@Override
	public void action() {
		 System.out.println(myAgent.getLocalName() +": Preparandose para calcular zeuthen primera vez ");  
	   
			
		 if(posZeuthenEnviado==6)
			 posZeuthenEnviado=0;
		 
		 float value=this.zeuthen.get(posZeuthenEnviado);
		 posZeuthenEnviado++;
		 IsMyZeuthen zeuthenEnviar= new IsMyZeuthen(value);		 
		 
		ACLMessage mensajeRecibido=(ACLMessage) getDataStore().get(ClaveUltimoMensajeRecibido);			
	     ACLMessage mensaje = mensajeRecibido.createReply();
	     mensaje.setPerformative(ACLMessage.INFORM);
	     
	     try {
				myAgent.getContentManager().fillContent(mensaje, zeuthenEnviar);// new Action((AID)mensaje.getAllReceiver().next(),(Concept) zeuthenEnviar));
			} catch (CodecException | OntologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	     
	    
         myAgent.send(mensaje);         
         
         //Lo almaceno
         getDataStore().put(ClaveUltimoMensajeZeuthen, mensaje);
         
         //Creo el mensaje template con la performativa infor
     	MessageTemplate mensajeTemplate = MessageTemplate.and(
	        		MessageTemplate.MatchConversationId(mensaje.getConversationId()),MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		//MessageTemplate mensajeTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);     
		 getDataStore().put(ClaveMensajeTemplateZeuthen, mensajeTemplate);
		// TODO Auto-generated method stub
		 fin=true;
		
	}
	@Override
	public void reset() {
		fin = false;
	};
	
	
	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return fin;
	}

}
