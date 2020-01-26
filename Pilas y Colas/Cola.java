package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     */
    @Override public void mete(T elemento){
		Nodo<T> nodo = new Nodo<T>(elemento);
		if(cabeza == null){
		cabeza = rabo = nodo;
	}else{
	rabo.siguiente = nodo;
	rabo = nodo;
    }
    elementos ++;
}
}
