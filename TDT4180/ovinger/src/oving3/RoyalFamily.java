package oving3;

import java.util.ArrayList;

import acm.program.ConsoleProgram;

public class RoyalFamily extends ConsoleProgram {
	
	ArrayList<Person> royalFamilyMembers = new ArrayList<Person>();
	
	private void createRoyalFamily() {
		Person harald = new Person();
		harald.name = "Harald";
		Person sonja = new Person();
		sonja.name = "Sonja";
		Person haakon = new Person();
		haakon.name = "Haakon";
		haakon.addParents(harald, sonja);
		Person metteMarit = new Person();
		metteMarit.name = "Mette-Marit";
		metteMarit.addParents(harald, sonja);
		Person martha = new Person();
		martha.name = "Märtha";
		martha.addParents(harald, sonja);
		Person ari = new Person();
		ari.name = "Ari";
		ari.addParents(harald, sonja);
		Person ingridAlexandra = new Person();
		ingridAlexandra.name = "Ingrid Alexandra";
		ingridAlexandra.addParents(haakon, metteMarit);
		Person sigurdMagnus = new Person();
		sigurdMagnus.name = "Sigurd Magnus";
		sigurdMagnus.addParents(haakon, metteMarit);
		Person maudAngelica = new Person();
		maudAngelica.name = "Maud Angelica";
		maudAngelica.addParents(ari, martha);
		Person leahIsadora = new Person();
		leahIsadora.name = "Leah Isadora";
		leahIsadora.addParents(ari, martha);
		Person emmaTaluah = new Person();
		emmaTaluah.name = "Emma Taluah";
		emmaTaluah.addParents(ari, martha);
		
		
		royalFamilyMembers.add(harald);
		royalFamilyMembers.add(sonja);
		royalFamilyMembers.add(haakon);
		royalFamilyMembers.add(metteMarit);
		royalFamilyMembers.add(martha);
		royalFamilyMembers.add(ari);
		royalFamilyMembers.add(ingridAlexandra);
		royalFamilyMembers.add(sigurdMagnus);
		royalFamilyMembers.add(maudAngelica);
		royalFamilyMembers.add(leahIsadora);
		royalFamilyMembers.add(emmaTaluah);
	}
	
	private void printRoyalFamily() {
		for (Person member : royalFamilyMembers) {
			println(member);
		}
	}
	
	public void run() {
		createRoyalFamily();
		printRoyalFamily();
	}
}
