package FinalUltimo2;


import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EmisorRespuesta  extends Behaviour {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ClaveUltimoMensajeRecibido="mensajeRecibido";
	private static final String  ClaveMensajeTemplate="ClaveMensajeTemplate";
	
	private boolean fin;
	private int estado;	
	public EmisorRespuesta(){
		fin = false;
		estado=0;		
	}
	
	@Override
	public void action() {
			
		//Espero recibir un mensaje del tipo del template creado en emisor
		ACLMessage mensaje = myAgent.receive((MessageTemplate) getDataStore().get(ClaveMensajeTemplate));		
		if(mensaje!=null){
             getDataStore().put(ClaveUltimoMensajeRecibido, mensaje); //Almaceno el mensaje que llego
             if(mensaje.getPerformative()==ACLMessage.ACCEPT_PROPOSAL){ //si recibio un acept termina            
            	 estado=1;
            	 
             }                
            else{ //sino vuelve a proponer pero antes me debo asegurar que no se hayan cambiados los roles del emisor y receptor
            	 estado=0;
             } 
            fin = true;
         }		
		else
        {
            System.out.println(myAgent.getLocalName() +": Esperando a recibir respuesta de la propuesta de la pelicula...");
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
