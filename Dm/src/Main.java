//import javax.swing.plaf.synth.SynthSeparatorUI;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		ListePlein l = new ListePlein() ;//créer une la liste qui contiendra tous les arbres reduits a leur racine(tous les caracteres)
        
		//un tableau contenant tous les caractere du texte.
		char [][] tab = {{'e',6050},
                         {'a',3555},
                         {'i',3295},
                         {'s',3255},
                         {'n',3195},
                         {'r',3035},
                         {'t',2960},
                         {'o',2510},
                         {'l',2480},
                         {'u',2245},
                         {'d',1835},
                         {'c',1590},
                         {'m',1310},
                         {'p',1245},
                         {'g',615},
                         {'b',570},
                         {'v',556},
                         {'h',555},
                         {'f',554},
                         {'.',415},
                         {'\'',380},
                         {'q',325},
                         {'y',230},
                         {'x',189},
                         {'j',171},
                         {'k',145},
                         {'w',85},
                         {'z',75},
                         {'é',233},
                         {'ô',244},
                         {'â',226},
                         {',',44},
                         {'à',224},
                         {'è',232}};        
        
        

		//ajouter tous les caractères dans la liste
        for(int i=0 ; i<tab.length ; i++)
        {
            Arbre a = new Arbre(tab[i][1],tab[i][0] );
            l = l.ajouter(a);
            
        }
		
		
		//le texte à encoder
		String txt="Les grandes personnes m'ont conseillé de laisser de côté les dessins de serpents boas ouverts ou fermés, et de m'intéresser plutôt à la géographie, à l'histoire, au calcul et à la grammaire. C'est ainsi que j'ai abandonné, à l'âge de six ans, une magnifique carrière de peintre.";
		
		
			
	/*	
		Arbre e=new Arbre(6050,'e');
		Arbre a=new Arbre(3555,'a');
		Arbre i=new Arbre(3295,'i');
		Arbre s=new Arbre(3255,'s');
		Arbre n=new Arbre(3195,'n');
		Arbre r=new Arbre(3035,'r');
		Arbre t=new Arbre(2960,'t');
		Arbre o=new Arbre(2510,'o');
		Arbre l=new Arbre(2480,'l');
		Arbre u=new Arbre(2245,'u');
		Arbre d=new Arbre(1835,'d');
		Arbre c=new Arbre(1590,'c');
		Arbre m=new Arbre(1310,'m');
		Arbre p=new Arbre(1245,'p');
		Arbre g=new Arbre(615,'g');
		Arbre b=new Arbre(570,'b');
		Arbre v=new Arbre(556,'v');
		Arbre h=new Arbre(555,'h');
		Arbre f=new Arbre(554,'f');
		Arbre q=new Arbre(325,'q');
		Arbre y=new Arbre(230,'y');
		Arbre x=new Arbre(189,'x');
		Arbre j=new Arbre(171,'j');
		Arbre k=new Arbre(145,'k');
		Arbre w=new Arbre(85,'w');
		Arbre z=new Arbre(78,'z');
		//w=w.ajout(k);
		ListePlein liste= new ListePlein().ajouter(a).ajouter(i).ajouter(e).ajouter(s).ajouter(t).ajouter(n).ajouter(r).ajouter(o).ajouter(l).ajouter(u).ajouter(d).ajouter(c).ajouter(m).ajouter(p).ajouter(g).ajouter(b).ajouter(v).ajouter(h).ajouter(f).ajouter(q).ajouter(y).ajouter(x).ajouter(j).ajouter(k).ajouter(w).ajouter(z);
		//System.out.println("frequence : " + e.get_infoFreq()+ " charactere : " + e.get_infoChar());
		
		/*l= l.ajouter(a)
		l=l.ajouter(e);
		l=l.ajouter(b);*/
		
		
		int cpt=0;//compteur pour savoir a quel niveau de l'arbre on se situe.
		
		
		l.afficher(cpt);//afficher la liste
		
		System.out.println();
		Arbre huf= new Arbre();//créer un arbre qui sera l'arbre de huffman
		
		
		huf= Huffman(l);
		System.out.println("fin de Huffman");
		
		huf.afficher(cpt);//on affiche l'arbre de huffman
		System.out.println();
	
		
		
		

		//System.out.println();
		//liste.afficher();*/
		String txtBinaire="";//créer un string qui contiendra le text final (avec les valeurs ds caracteres en bit)
		System.out.println();
		System.out.println();
		

		//permet d'ajouter aux texte final les valeurs binaire des caracteres
		for(int i=0; i<txt.length();i++)
		{
			txtBinaire= txtBinaire + " " + l.recherche(java.lang.Character.toLowerCase(txt.charAt(i)));//toLower pour mettre en minuscule les majuscules
		}
		
		System.out.println();
		System.out.println();
		
		System.out.println(txtBinaire);//afficher le txt final
	}
	
	
	public static Arbre Huffman(ListePlein l) //créer l'arbre de huffman
	{
		while(!(l.reste().vide()))
		{
			
				Arbre arbre= new Arbre(l.tete(), l.reste().tete());//créee run arbre avec las deux premieres valeurs de la liste 
				System.out.println();
				
				l=l.reste().reste();//e,nlever les deux premiere valeur de la liste
				l=l.ajouter(arbre);//ajouter l'arbre crée a la liste
		
			
			
			
		}
		return l.tete();
		
		
	}

}
