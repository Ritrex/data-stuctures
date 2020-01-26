package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de
 * la lista, eliminar elementos de la lista, comprobar si un
 * elemento está o no en la lista, y otras operaciones básicas.</p>
 *
 * <p>Las instancias de la clase Lista implementan la interfaz
 * {@link Iterator}, por lo que el recorrerlas es muy sencillo:</p>
 *
<pre>
    for (String s : l)
        System.out.println(s);
</pre>
 *
 * <p>Además, se le puede pedir a una lista una instancia de {@link
 * IteradorLista} para recorrerla en ambas direcciones.</p>
 */
public class Lista<T> implements Iterable<T> {

    /* Clase Nodo privada para uso interno de la clase Lista. */
    private class Nodo<T> {
        public T elemento;
        public Nodo<T> anterior;
        public Nodo<T> siguiente;

        public Nodo(T elemento) {
            this.elemento = elemento;
        }
	}
    

    /* Clase Iterador privada para iteradores. */
    private class Iterador<T> implements IteradorLista<T> {

        /* La lista a iterar. */
        Lista<T> lista;
        /* Elemento anterior. */
        private Lista<T>.Nodo<T> anterior;
        /* Elemento siguiente. */
        private Lista<T>.Nodo<T> siguiente;

        /* El constructor recibe una lista para inicializar su
         * siguiente con la cabeza. */
        public Iterador(Lista<T> lista) {
			this.lista = lista;
			siguiente = lista.cabeza;
			anterior = null;
        }

        /* Existe un siguiente elemento, si el siguiente no es
         * nulo. */
        @Override public boolean hasNext() {
			return siguiente != null;
        }

        /* Regresa el elemento del siguiente, a menos que sea nulo,
         * en cuyo caso lanza la excepción
         * NoSuchElementException. */
        @Override public T next() throws NoSuchElementException{
			if(siguiente == null)
			throw new NoSuchElementException();
			anterior = siguiente;
			siguiente = siguiente.siguiente;
			return anterior.elemento;
        }

        /* Existe un elemento anterior, si el anterior no es
         * nulo. */
        @Override public boolean hasPrevious() {
			return anterior != null;
        }

        /* Regresa el elemento del anterior, a menos que sea nulo,
         * en cuyo caso lanza la excepción
         * NoSuchElementException. */
        @Override public T previous()throws NoSuchElementException{
			if(anterior == null)
			throw new NoSuchElementException();
			siguiente = anterior;
			anterior = anterior.anterior;
			return siguiente.elemento;
        }

        /* No implementamos el método remove(); sencillamente
         * lanzamos la excepción UnsupportedOperationException. */
        @Override public void remove() {
            throw new UnsupportedOperationException();
        }

        /* Mueve el iterador al inicio de la lista; después de
         * llamar este método, y si la lista no es vacía, hasNext()
         * regresa verdadero y next() regresa el primer elemento. */
        @Override public void start() {
			siguiente = lista.cabeza;
			anterior = null;
        }

        /* Mueve el iterador al final de la lista; después de llamar
         * este método, y si la lista no es vacía, hasPrevious()
         * regresa verdadero y previous() regresa el último
         * elemento. */
        @Override public void end() {
			anterior = lista.rabo;
			siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo<T> cabeza;
    /* Último elemento de la lista. */
    private Nodo<T> rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista.
     * @return la longitud de la lista, el número de elementos que
     * contiene.
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no
     * tiene elementos, el elemento a agregar será el primero y
     * último.
     * @param elemento el elemento a agregar.
     */
    public void agregaFinal(T elemento) {
		Nodo<T> nodo = new Nodo<T>(elemento);
		if(rabo == null){
			cabeza = rabo = nodo;
			}else{
				rabo.siguiente = nodo;
				nodo.anterior = rabo;
				rabo = nodo;
				}
				longitud++;
			}

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no
     * tiene elementos, el elemento a agregar será el primero y
     * último.
     * @param elemento el elemento a agregar.
     */
    public void agregaInicio(T elemento) {
		Nodo<T> nodo = new Nodo<T>(elemento);
		if(cabeza == null){
			cabeza = rabo = nodo;
			}else{
				cabeza.anterior = nodo;
				nodo.siguiente = cabeza;
				cabeza = nodo;
				}
				longitud ++;
			}

    /**
     * Elimina un elemento de la lista. Si el elemento no está
     * contenido en la lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    public void elimina(T elemento) {
		Nodo<T> nodo = buscaNodo(elemento, cabeza);
		if(nodo == null){
			return;}
			if(cabeza == rabo) {
				cabeza = rabo = null;
				} else if (nodo == cabeza) {
					cabeza = cabeza.siguiente;
					cabeza.anterior = null;
					} else if(nodo == rabo) {
						rabo = rabo.anterior;
						rabo.siguiente = null;
						} else { 
							nodo.anterior.siguiente = nodo.siguiente;
							nodo.siguiente.anterior = nodo.anterior;
							}
							longitud--;
							}

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        if(cabeza == rabo){
			T borrado = cabeza.elemento;
			cabeza = rabo = null;
			longitud --;
			return borrado;
		}else{
			if(longitud != 0){
				T borrado = cabeza.elemento;
				cabeza = cabeza.siguiente;
				cabeza.anterior = null;
				longitud --;
				return borrado;
			}
		}
		throw new NoSuchElementException();
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
		if(cabeza == rabo){
        T borrado = rabo.elemento;
        cabeza = rabo = null;
        longitud --;
        return borrado;
	}else{
		if(longitud != 0){
		T borrado = rabo.elemento;
		rabo = rabo.anterior;
		rabo.siguiente = null;
		longitud --;
		return borrado;
		}
		}
		throw new NoSuchElementException();
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la
     * lista.
     * @return <tt>true</tt> si <tt>elemento</tt> está en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public boolean contiene(T elemento) {
		return buscaNodo(elemento, cabeza) != null;
    }
    

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar
     *         el método.
     */
    
    public Lista<T> reversa() {
        Lista<T> nuevaLista = new Lista<T>();
        if(cabeza == null){
        return null;
        }else{
			Nodo<T> nodo = cabeza;
			while(nodo != null){
				nuevaLista.agregaInicio(nodo.elemento);
				nodo = nodo.siguiente;
			}
		}return nuevaLista;
        
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos
     * elementos que la lista que manda llamar el método, en el
     * mismo orden.
     * @return una copiad de la lista.
     *
    */
    public Lista<T> copia() {
        Lista<T> nuevaLista = new Lista<T>();
        if(cabeza == null){
			return null;
		}else{
			Nodo<T> nodo = cabeza;
			while(nodo != null){
				nuevaLista.agregaFinal(nodo.elemento);
				nodo = nodo.siguiente;
			}
		}return nuevaLista;
    }
    
    
    /**
     * Limpia la lista de elementos. El llamar este método es
     * equivalente a eliminar todos los elementos de la lista.
     */
    public void limpia() {
		cabeza = rabo = null; 
		longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() throws NoSuchElementException {
		if(cabeza == null)
		throw new NoSuchElementException();
		T elemento = cabeza.elemento;
		return elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el último elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo()throws NoSuchElementException {
		if(rabo == null)
		throw new NoSuchElementException();
		T elemento = rabo.elemento;
		return elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista, si
     *         <em>i</em> es mayor o igual que cero y menor que el
     *         número de elementos en la lista.
     * @throws ExcepcionIndiceInvalido si el índice recibido es
     *         menor que cero, o mayor que el número de elementos en
     *         la lista menos uno.
     */
    public T get(int i) {
     if (i < 0 || i >= getLongitud())
            throw new ExcepcionIndiceInvalido("Indice invalido");
        return obtener(cabeza, i, 0);
    }

   /*metodo auxiliar*/
    private T obtener(Nodo<T> nodo, int i, int j) {
	if (nodo == null)
	  throw new ExcepcionIndiceInvalido("Indice invalido");
	if (i == j)
	    return nodo.elemento;
	return obtener(nodo.siguiente, i, ++j);
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si
     *         el elemento no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        int i= 0;
		return indiceR(cabeza, elemento, i);
    }
    /*metodo auxiliar*/
    private int indiceR(Nodo<T> nodo, T elemento, int i){
		   if(nodo==null)
				return -1;
		   if(nodo.elemento.equals(elemento))
				return i;
			   i ++;
		   return indiceR(nodo.siguiente, elemento, i);
           
	}

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param o el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la lista es igual al objeto
     *         recibido; <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)o;
	return nodosIguales(cabeza, lista.cabeza); 
    }
    
    /*metodo auxiliar */
    private boolean nodosIguales(Nodo primero,Nodo segundo) {
	if(primero == null && segundo == null)
	    return true;
	if(primero == null || segundo == null)
	    return false;
	return primero.elemento.equals(segundo.elemento) && 
	    nodosIguales(primero.siguiente, segundo.siguiente);
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
	if (cabeza == null)
			return "[]";
	return "[" + cabeza.elemento.toString() + nodoCadena
			(cabeza.siguiente);
    }
    
    /*metodo auxiliar*/
	private String nodoCadena(Nodo nodo) {
		if(nodo == null)
		return "]";
		return ", " + nodo.elemento.toString() + nodoCadena
		(nodo.siguiente);
}

    /**
     * Regresa un iterador para recorrer la lista.
     * @return un iterador para recorrer la lista.
     */
    @Override public Iterator<T> iterator(){
        return iteradorLista();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas
     * direcciones.
     * @return un iterador para recorrer la lista en ambas
     * direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador<T>(this);
    }
    
    /*metodo auxiliar */
	private Nodo<T> buscaNodo(T elemento, Nodo<T> nodo){
		if(nodo == null)
		return null;
		if(nodo.elemento.equals(elemento))
		return nodo;
		return buscaNodo(elemento, nodo.siguiente);
		}



/**
     * Regresa una copia de la lista recibida, pero ordenada. La
     * lista recibida tiene que contener nada más elementos que
     * implementan la interfaz {@link Comparable}.
     * @param l la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
        Lista<T> mergeSort(Lista<T> l) {
			Lista<T> resultado = new Lista<T>();
			Lista<T> izquierda = new Lista<T>();
			Lista<T> derecha = new Lista<T>();
			Lista<T>.Nodo<T> nodo = l.cabeza;
			if(l.getLongitud() < 2)
				return l.copia();
			int medio = l.getLongitud() / 2;
			for(int i = 0; i < medio; i++){
				izquierda.agregaFinal(nodo.elemento);
				nodo = nodo.siguiente;
			}
			for(int j = medio; j < l.getLongitud(); j++){
				derecha.agregaFinal(nodo.elemento);
				nodo = nodo.siguiente;
			}
			izquierda = mergeSort(izquierda);
			derecha = mergeSort(derecha);
			resultado = mezcla(izquierda, derecha);
			return resultado;
		}
		
		/*metodo auxiliar*/
	private static <T extends Comparable<T>> Lista<T> mezcla(Lista<T> izquierda, Lista<T> derecha){
		Lista<T> l= new Lista<T>();
		Lista<T>.Nodo<T> nodoizq= izquierda.cabeza;
		Lista<T>.Nodo<T> nododer= derecha.cabeza;
		while(nodoizq != null && nododer!=null){
			if(nodoizq.elemento.compareTo(nododer.elemento) < 0){ 
				l.agregaFinal(nodoizq.elemento);
				nodoizq = nodoizq.siguiente;
				}else{ 
					l.agregaFinal(nododer.elemento);
					nododer = nododer.siguiente;
					} 
					}while(nodoizq != null){ 
						l.agregaFinal(nodoizq.elemento); 
						nodoizq = nodoizq.siguiente;
						} while(nododer != null){ 
							l.agregaFinal(nododer.elemento); 
							nododer=nododer.siguiente; 
							} return l;
						}
    
    /**
     * Busca un elemento en una lista ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la
     * interfaz {@link Comparable}, y se da por hecho que está
     * ordenada.
     * @param l la lista donde se buscará.
     * @param e el elemento a buscar.
     * @return <tt>true</tt> si e está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public static <T extends Comparable<T>>
        boolean busquedaLineal(Lista<T> l, T e) {
			return l.contiene(e);
		}

}
