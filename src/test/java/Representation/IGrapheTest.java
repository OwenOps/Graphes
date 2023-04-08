package Representation;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import Interface.*;
import Arc.Arc;

class IGrapheTest {
	// graphe de l'exercice 3.1 du poly de maths
	// avec en plus un noeud isole : J
	private String g31 = 
			"A-C(2), A-D(1), "
			+ "B-G(3), "
			+ "C-H(2), "
			+ "D-B(3), D-C(5), D-E(3), "
			+ "E-C(1), E-G(3), E-H(7), "
			+ "F:, "
			+ "G-B(2), G-F(1), "
			+ "H-F(4), H-G(2), "
			+ "I-H(10), "
			+ "J:";
	
	private String g31a = ""       // melangee
			+ "D-C(5), D-E(3), D-B(3), "
			+ "E-G(3), E-C(1), E-H(7), "
			+ "I-H(10), "
			+ "J:,"
			+ "G-B(2), G-F(1), "
			+ "F:, "
			+ "H-G(2), H-F(4), "
			+ "A-C(2), A-D(1), "
			+ "B-G(3), "
			+ "C-H(2) "
;
	
	@Test
	void exo3_1Maths() {
		GrapheLAdj gla = new GrapheLAdj(g31a); // on cree le graphe sans ordre particulier
		tester3_1(gla);
		testerFonctionOterArc(gla);
		testerFonctionOterSommet(gla);
		testerAutre(gla);

		GrapheLArcs glarcs = new GrapheLArcs(g31a);
		tester3_1(glarcs);
		testerFonctionOterArc(glarcs);
		testerFonctionOterSommet(glarcs);
		testerAutre(glarcs);

		GrapheHHAdj hha = new GrapheHHAdj(g31a);
		tester3_1(hha);
		testerFonctionOterArc(hha);
		testerFonctionOterSommet(hha);
		testerAutre(hha);

		GrapheMAdj gma = new GrapheMAdj(g31a);
		tester3_1(gma);
		testerFonctionOterArc(gma);
		testerFonctionOterSommet(gma);
		testerAutre(gma);
	}
	
	void tester3_1(IGraphe g) {
		List<String> sommets_exp = List.of("A","B","C","D","E","F","G","H","I","J");
		List<String> sommets = new ArrayList<String>(g.getSommets()); // pas forcement triee
		Collections.sort(sommets);
		assertEquals(sommets_exp, sommets);
		assertTrue(g.contientSommet("C"));
		assertFalse(g.contientSommet("c"));
		assertTrue(g.contientArc("C","H"));
		assertFalse(g.contientArc("H","C"));

		assertEquals(7,g.getValuation("E", "H"));
		List<String> successeurs = new ArrayList<String>(g.getSucc("D")); // pas forcement triee
		Collections.sort(successeurs);
		assertEquals(List.of("B","C", "E"), successeurs);
		assertEquals(g31, g.toString());
		
		g.ajouterSommet("A"); // ne fait rien car A est deja present
		assertEquals(g31, g.toString());
		assertThrows(IllegalArgumentException.class,  
				() -> g.ajouterArc("G", "B", 1));		// deja present
		g.oterSommet("X"); // ne fait rien si le sommet n'est pas present
		assertEquals(g31, g.toString());
		assertThrows(IllegalArgumentException.class,
				() -> g.oterArc("X", "Y"));  // n'existe pas
		
		assertThrows(IllegalArgumentException.class,
				() -> g.ajouterArc("A", "B", -1)); // valuation negative
	}
	
	@Test
	void importer() throws NumberFormatException, FileNotFoundException {
		System.out.println("SAE graphes");
		IGraphe g = new GrapheLAdj();
		IGraphe g2 = new GrapheLArcs();
		IGraphe g3 = new GrapheHHAdj();
		IGraphe g4 = new GrapheMAdj();

		Arc a = GraphImporter.importer("src/test/java/Representation/grapheImporter/g-10-1.txt", g3);
		assertEquals(g3.toString(), "1-3(5), "
				+ "10-3(3), 2-1(5), 2-3(5), 2-5(4), "
				+ "3-4(4), 3-5(4), 4-10(1), 4-2(1), 4-7(3), "
				+ "5-9(4), 6-2(3), 6-3(4), 7-3(2),"
				+ " 8-2(4), 8-6(1), 9-2(4)");
		assertEquals("5", a.getSource());
		assertEquals("7", a.getDestination());
	}

	void testerFonctionOterArc(IGraphe g) {
		List<String> som1 = List.of("A","B","C","D","E","F","G","H","I","J");
		List<String> som2 = List.of();
		List<String> som3 = List.of("F");
		g.oterArc("A","C");
		g.oterArc("A","D");
		g.oterArc("B","G");
		g.oterArc("C","H");
		g.oterArc("D","B");
		g.oterArc("D","C");
		g.oterArc("D","E");
		g.oterArc("E","C");
		g.oterArc("E","G");
		g.oterArc("E","H");
		g.oterArc("G","B");
		g.oterArc("H","F");
		g.oterArc("H","G");
		g.oterArc("I","H");

		//Il reste comme Arc G-F(1)
		assertEquals(som2,g.getSucc("A"));
		assertEquals(som2,g.getSucc("J"));
		assertEquals(som3,g.getSucc("G"));
		assertEquals(som2,g.getSucc("F"));
		assertEquals(-1,g.getValuation("A","C"));
		assertEquals(-1,g.getValuation("H","I"));
		assertEquals(1,g.getValuation("G","F"));
	}

	void testerFonctionOterSommet(IGraphe g) {
		List<String> som4 = List.of("F");
		g.oterSommet("A");
		g.oterSommet("B");
		g.oterSommet("C");
		g.oterSommet("D");
		g.oterSommet("E");
		g.oterSommet("G");
		g.oterSommet("C");
		g.oterSommet("H");
		g.oterSommet("I");
		g.oterSommet("J");
		g.oterSommet("Z");
		assertEquals(som4, g.getSommets());
	}
	void testerAutre(IGraphe g) {
		List<String> sommets_exp = List.of("A","B","C","D","E","F","G","H","I","J");
		g.ajouterArc("Z","Z",4);
		g.ajouterArc("Z","V", 10);
		g.ajouterSommet("z");
		g.oterSommet("W");
		g.getSucc("A");
		g.contientArc("A","");
		g.oterSommet("X");
		g.oterSommet("Z");
		g.getSucc("$");
		g.getValuation("$", "A");
		g.getValuation("Z", "V");
		g.contientSommet("");
		g.contientSommet("$");
		assertThrows(IllegalArgumentException.class,
				() -> g.oterArc("$", "A"));  // n'existe pas
		assertThrows(IllegalArgumentException.class,
				() -> g.oterArc("A", "B"));
		g.ajouterArc("P", "Z", 6);
		List<String> som = List.of("Z");
		assertEquals(som,g.getSucc("P"));
		g.oterArc("P", "Z");
		g.oterSommet("F");
		g.oterSommet("V");
		g.oterSommet("Z");
		g.oterSommet("z");
	}
}
