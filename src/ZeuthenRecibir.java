package FinalUltimo2;


import FinalUltimo2.Ontologia.IsMyZeuthen;
import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ZeuthenRecibir extends Behaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean fin;
	private boolean emisor;
	private int estado;
	private static final String ClaveUltimoMensajeZeuthen="mensajeZeuthen";
	private static final String ClaveUltimoMensajeZeuthenRecibido="mensajeZeuthenRecibido";
	private static final String  ClaveMensajeTemplateZeuthen="ClaveMensajeTemplateZeuthen";
		
	
	public ZeuthenRecibir(boolean emisor) {
		fin=false;
		estado=0;
		this.emisor=emisor;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() {
		 float valueEnviado=-1;
		 float valueRecibido=-1;		 	
		 MessageTemplate mensajeTemplate=  (MessageTemplate) getDataStore().get(ClaveMensajeTemplateZeuthen);
		 ACLMessage mensajeRecibido = myAgent.receive(mensajeTemplate);  		  
		   
         if (mensajeRecibido!= null) 
         {    
        	getDataStore().put(ClaveUltimoMensajeZeuthenRecibido, mensajeRecibido);        	
        	 ACLMessage mensajeEnviado=(ACLMessage) getDataStore().get(ClaveUltimoMensajeZeuthen);
        	 
        	 ContentElement ceENVIADO;
        	 ContentElement ceRECIBIDO;
        	 
        	 try {
        		ceENVIADO=myAgent.getContentManager().extractContent(mensajeEnviado);			
        		IsMyZeuthen smENVIADO= (IsMyZeuthen)ceENVIADO;   //((Action)ce).getAction();
        		ceRECIBIDO=myAgent.getContentManager().extractContent(mensajeRecibido);			
        		IsMyZeuthen smRECIBIDO= (IsMyZeuthen)ceRECIBIDO;   //((Action)ce).getAction();
        		valueEnviado=smENVIADO.getValue();
        		valueRecibido=smRECIBIDO.getValue();
 				
 			} catch (CodecException | OntologyException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
        	 
        	 
        	 
        	// valueEnviado=mensajeEnviado.getContent();
        	// valueRecibido=mensajeRecibido.getContent();
        	 
        	 
        	// if(valueEnviado.compareTo(valueRecibido)<0){
        	
        	 
        	 if(valueEnviado < valueRecibido){
        		 estado=0;
        	 }
        	 else
        	 if (valueEnviado > valueRecibido)
        		 estado=1;
        	 else{
	        	 if(emisor){
	        		 if(valueEnviado < valueRecibido)
	        			 estado=0; //envio mensaje
	        		 else
	        			 estado=1; //espero propuesta
	        	 }
	        	 else{
	        		 if(valueEnviado < valueRecibido)
	        			 estado=1; //espero propuesta
	        		 else
	        			 estado=0; //envio mensaje
	        	 }
        	 }
        		 
             fin = true;             
         }
         else
         {        	 
             System.out.println(myAgent.getLocalName() +": Esperando a recibir zeuthen de la pelicula...");
              block();                 
         }		
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return fin;
	}
	
	@Override
	public void reset() {
		fin = false;
	};
	
	
	public int onEnd() {
		// TODO Auto-generated method stub
		return estado ;
		}

}
