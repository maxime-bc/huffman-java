public class ListePlein {
	
	private Arbre _tete;
	private ListePlein _reste;
	private boolean _vide; 
	
	
	
	
	
	//CONSTRUCTEUR
	public ListePlein(Arbre x, ListePlein _reste)
	{
		this._tete=x;
		this._reste=_reste;
		this._vide=false;
	}	
		
	//CONSTRUCTEUR DE LISTE VIDE
	public ListePlein()
	{
		this._reste=null;
		this._vide=true;
		
	}
	
	
	

	//revoi la tete de la liste
	public Arbre tete() {
		// TODO Auto-generated method stub
		return this._tete;
	}

	public boolean vide()
	{
		return this._vide;
	}
	
	//renvoi le reste
	public ListePlein reste() {
		// TODO Auto-generated method stub
		return  this._reste;// new ListePlein(this._reste);
	}

	
	
	
	public ListePlein prefixer(Arbre x) {
		// TODO Auto-generated method stub
		return new ListePlein(x, this);
	}

	
	//affiche la liste de maniere recursive 
	public void afficher(int cpt)
	{
		if(this._vide)
		{
			System.out.println("fin de la liste");
		}
		else
		{
			this._tete.afficher(cpt);//appelle de ma fonction afficher pour les arbres
			this._reste.afficher(cpt);
			System.out.println();

		}
	}
	
	
	
	//ajoute un arbre a la liste en le rangeant au bonne endroit selon sa frequence.
	public ListePlein ajouter(Arbre a) 
	{
	

		if(!(this._vide))
		{
			
			if(a.get_infoFreq() < this._tete.get_infoFreq())
			{
				
				return prefixer(a);
				
			}
			else
			{
				return new ListePlein(this._tete, this._reste.ajouter(a));
			}

		}
		else 
		{
			return this.prefixer(a);
		}
		
	}
	

	//rechercher dans la liste le code binaire d'un caractere selon un caractere passé en parametre 
	public String recherche(char s)
	{
		if(this.vide())
		{
			return " vide ";
		}
		else
		{
			if(this.tete().get_infoChar()==s)//si le caractere de ma tete de la liste et le meme que celui passé en parametre alors on retourne la valeur du caractere en binaire 
			{
				return this.tete().getBibi();
			}
			else
			{
				return this._reste.recherche(s);
			}
		}
	}

	
	
	
	
	

	
}
