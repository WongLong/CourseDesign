package Question_1;

import java.io.Serializable;

//hfm±àÂë½Úµã
public class CodeNode<E> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	E e;
	String codeStr;
	
	public CodeNode(E e, String codeStr){
		this.e = e;
		this.codeStr = codeStr;
	}

}
