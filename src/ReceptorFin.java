package FinalUltimo2;


import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class ReceptorFin extends Behaviour{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 private static final String ClaveUltimoMensajeRecibido="mensajeRecibido";
	 boolean fin ;
		boolean agenteEmisor;
		int estado;
	 
	 public ReceptorFin(boolean agenteEmisor) {
			super();
			this.fin = false;
			this.agenteEmisor=agenteEmisor;
			estado=1;
			 
		}
	 
	@Override
	public void action() {		 
		//ACLMessage respuesta=(ACLMessage) getDataStore().get(ClaveUltimoMensajeRecibidorDelEmisor);
		ACLMessage respuesta=(ACLMessage) getDataStore().get(ClaveUltimoMensajeRecibido);
		System.out.println(myAgent.getLocalName() +" Acepto la pelicula "+respuesta.getContent());
		fin=true;
		
		
		/*
		//ESto es nuevo
		
		if(!agenteEmisor){
			
			 estado=0; //voy a esperar_propuesta
		}
			 
		 else{
			 estado=1; //voy a Emisor_fin
			 myAgent.doDelete();
		 }
		*/
	}

	
	protected void takeDown() {
        System.out.println("Agente "+myAgent.getLocalName()+" termino de ejecutarse.");
        try {            	 
			DFService.deregister(myAgent,myAgent.getAID());
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }

	
	@Override
	public void reset() {
		fin = false;
	};
	

	
	
	@Override
	public boolean done() {
	
	//	if(fin==true)
		//	myAgent.doDelete();
		return fin;
	}
	

	public int onEnd() {
		// TODO Auto-generated method stub
		return estado ;
		}
}
