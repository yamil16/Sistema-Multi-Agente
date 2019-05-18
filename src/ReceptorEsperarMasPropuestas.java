package FinalUltimo2;
import jade.core.behaviours.*;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
public class ReceptorEsperarMasPropuestas extends Behaviour{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	 
	 private boolean fin;
	 private int estado;
	 private static final String  ClaveMensajeTemplate="ClaveMensajeTemplate";
	 private static final String ClaveUltimoMensajeRecibido="mensajeRecibido";

	
	public ReceptorEsperarMasPropuestas(){
		fin = false;
		 estado=1;
		
	}	 
	 
	@Override	
	public void action() {
			 
		 
		 //obtengo el mensaje template
		 MessageTemplate mensajeTemplate = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE), MessageTemplate.MatchPerformative(ACLMessage.CANCEL));
		 
		  //MessageTemplate mensajeTemplate = (MessageTemplate) getDataStore().get(ClaveMensajeTemplate);
		  //Obtiene el primer mensaje de la cola de mensajes
          ACLMessage mensaje = myAgent.receive(mensajeTemplate);		
             if (mensaje!= null)
             {
            	 getDataStore().put(ClaveMensajeTemplate, mensajeTemplate);                                          
                 getDataStore().put(ClaveUltimoMensajeRecibido, mensaje); //Almaceno el mensaje que llego                   
                 if(mensaje.getPerformative()==ACLMessage.PROPOSE) 
                	 estado=0; //analizo la propuesta
                 else
                	 estado=1;   //termino la ejecucion no hay acuerdo por parte del emisor
                 fin = true;
             }
             else
             {
                 System.out.println(myAgent.getLocalName() +": Esperando a recibir la propuesta de pelicula para ver...");
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
	
@Override
	public int onEnd() {
	// TODO Auto-generated method stub
	return estado ;
	}
}
