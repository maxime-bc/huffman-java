
public class Arbre  {
	
	private char _infoChar=' ';
	private int _totFreq;
	private Arbre _filsGauche;
	private Arbre _filsDroit;
	private boolean estVide;
	private static String binaire= " ";
	private String bibi="";
	
	
	
	public int get_totFreq() {
		return _totFreq;
	}

	public void set_totFreq(int _totFreq) {
		this._totFreq = _totFreq;
	}
	public String getBibi()
	{
		return this.bibi;
	}

	
	//Constructeur (pour Huffman)
	public Arbre( Arbre filsGauche, Arbre filsDroit)
	{
		_filsGauche=filsGauche;
		_filsDroit=filsDroit;
		_totFreq=filsGauche._totFreq+filsDroit._totFreq;
		this.estVide=false;
	}
	
	//Constructeur
	public Arbre(int freq, char chara)
	{
		//this._infoFreq=freq;
		this._infoChar=chara;
		this._filsDroit= new Arbre();
		this._filsGauche=new Arbre();
		this._totFreq=freq;
		this.estVide=false;
	}
	
	//Constructeur arbre vide
	public Arbre()
	{
		this._filsDroit=null;
		this._filsGauche=null;
		this.estVide=true;
	}
	
	public boolean vide()
	{
		return this.estVide;
	}
	
	
	
	public int get_infoFreq() {
		return this._totFreq;
	}


	public char get_infoChar() {
		return _infoChar;
	}


	public Arbre filsGauche() {
		// TODO Auto-generated method stub
		return this._filsGauche;
	}


	public Arbre filsDroit() {
		// TODO Auto-generated method stub
		return this._filsDroit;
	}

	
	
	//affiche les arbres 
	public void afficher(int cpt)
	{
		cpt++;//s'incrémente a chaque fois que l'on s'enfonce dans l'arbre 
		
		if(!this.vide())
		{
			if (this._infoChar==' ')//si ce n'est pas une feuille de l'arbre (car une feuille qui contient forcelent un caractere )
			{
				System.out.println();
				System.out.println(cpt);//on affiche a quel niveau de l'arbre on est 
				System.out.println("frequences Totale = " + this.get_totFreq());
				if(!(this.filsGauche().vide()))//si le fils gauche n'est pas vide le on rajoute un 0 au chemin binaire			
					{
						this.binaire=this.binaire + "0";
					}
				System.out.println("gauche");	
				this._filsGauche.afficher(cpt);
			
				if(!(this.filsDroit().vide()))				
				{
					this.binaire= binaire.substring(0,cpt);//on ne va a droite que lorsqu'il n'y a plus rein a droite, il faut donc enlever tous les bit qui on etaient mis avant d'aller a droite 
					this.binaire=this.binaire + "1";//pui on ajoute 1 au chemin car on va a droite
				}
				System.out.println("droite");
			
				this._filsDroit.afficher(cpt);
				
				}
			else//si on est dans une feuille 
			{
				System.out.println();
				System.out.println(cpt);
				System.out.println( this.get_infoChar() + " freq: " + this.get_totFreq());
				this.bibi=this.binaire;//on affecte le chemin binaire parcouru pour arriver jusau'a cette feuille a l'attribut 'bibi' de chaque couple caractere/frequence
				System.out.println(" en binaire: " + this.binaire);
					/*System.out.println("gauche");
					this._filsGauche.afficher(cpt);
					System.out.println("droite");
					this._filsDroit.afficher(cpt);*/
					
				}
				
		}
		

		
		
	}

	
	
	
	
	
	
}
