package graphe;

import static org.junit.jupiter.api.Assertions.*;
import Arc.Arc;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Representation.GrapheHHAdj;
import Representation.GrapheLAdj;
import Representation.GrapheLArcs;
import Representation.GrapheMAdj;
import org.junit.jupiter.api.Test;

class IGrapheTest {
	private IGraphe[] graphes = {
			new GrapheLArcs(), new GrapheLAdj(),
			new GrapheMAdj(), new GrapheHHAdj()
	};
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

	private String g31a = ""       // arcs non tries
			+ "D-C(5), D-E(3), D-B(3), "
			+ "E-G(3), E-C(1), E-H(7), "
			+ "I-H(10), "
			+ "J:,"
			+ "G-B(2), G-F(1), "
			+ "F:, "
			+ "H-G(2), H-F(4), "
			+ "A-C(2), A-D(1), "
			+ "B-G(3), "
			+ "C-H(2) ";

	@Test
	void exo3_1Maths() {
		for (IGraphe g : graphes) {
			g.peupler(g31);
			tester3_1(g);
			testerFonctionOterArc(g);
			testerFonctionOterSommet(g);
			testerAutre(g);
		}
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

	void testImportation(IGraphe g) {
		Arc a = GraphImporter.importer("src/test/java/graphe/grapheImporter/g-10-1.txt", g);
		assertEquals("1-3(5), "
						+ "10-3(3), 2-1(5), 2-3(5), 2-5(4), "
						+ "3-4(4), 3-5(4), 4-10(1), 4-2(1), 4-7(3), "
						+ "5-9(4), 6-2(3), 6-3(4), 7-3(2),"
						+ " 8-2(4), 8-6(1), 9-2(4)",
				g.toString());
		assertEquals("5", a.getSource());
		assertEquals("7", a.getDestination());
	}

	@Test
	void importer() throws NumberFormatException, FileNotFoundException {
		for (IGraphe g : graphes)
			testImportation(g);
	}

	void testerFonctionOterArc(IGraphe g) {
		List<String> som1 = List.of("A","B","C","D","E","F","G","H","I","J");
		List<String> som5 = List.of("A:","B:","C:","D:","E:","F:","G:","H:","I:","J:");
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
		g.oterArc("G","F");
		String fina = "A:, B:, C:, D:, E:, F:, G:, H:, I:, J:";
		assertEquals(fina,g.toString());
	}

	void testerFonctionOterSommet(IGraphe g) {
		List<String> som4 = List.of("F");
		List<String> sommets_exp = List.of("B","C","D","E","F","G","H","I","J");
		List<String> sommets_exp2 = List.of("B","E","F","H","J");
		g.oterSommet("A");
		assertEquals(sommets_exp,g.getSommets());
		g.oterSommet("I");
		g.oterSommet("C");
		g.oterSommet("D");
		g.oterSommet("G");
		assertEquals(sommets_exp2,g.getSommets());
		g.oterSommet("G");
		g.oterSommet("C");
		g.oterSommet("H");
		g.oterSommet("I");
		g.oterSommet("J");
		g.oterSommet("Z");
		g.oterSommet("B");
		g.oterSommet("E");
		assertEquals(som4, g.getSommets());
	}
	void testerAutre(IGraphe g) {
		List<String> sommets_exp = List.of("A","B","C","D","E","F","G","H","I","J");
		List<String> sommets_exp2 = List.of("Z");
		List<String> sommets_exp3 = new ArrayList<>();
		g.ajouterArc("Z","Z",4);
		g.ajouterArc("Z","V", 10);
		g.ajouterArc("Z","A",50);
		g.ajouterArc("A","Z",30);
		assertEquals(10,g.getValuation("Z","V"));
		assertEquals(4,g.getValuation("Z","Z"));
		assertEquals(50,g.getValuation("Z","A"));
		assertEquals(30,g.getValuation("A","Z"));
		assertEquals(-1,g.getValuation("A","W"));
		assertEquals(-1,g.getValuation("",""));

		g.ajouterSommet("z");
		g.oterSommet("W");
		assertEquals(sommets_exp2, g.getSucc("A"));
		assertFalse(g.contientArc("A",""));
		g.oterSommet("X");
		g.oterSommet("Z");
		assertEquals(sommets_exp3, g.getSucc("$"));
		assertEquals(-1,g.getValuation("$","A"));
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
