import Interface.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import Representation.GrapheLAdj;
import Representation.GrapheLArcs;
import org.junit.jupiter.api.Test;

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
	
//	@Test
//	void exo3_1Maths() {
//		GrapheLArcs gla = new GrapheLArcs(g31);
//		tester3_1(gla);
//		testerFonctionOterArc(gla);
//	}

	@Test
	void exo3_1Maths2() {
		GrapheLAdj gla2 = new GrapheLAdj(g31);
		tester3_1(gla2);
		testerFonctionOterSommet(gla2);
//		testerFonctionOterArc(gla2);
	}
	
	void tester3_1(IGraphe g) {
		List<String> sommets = List.of("A","B","C","D","E","F","G","H","I","J");
		assertEquals(sommets, g.getSommets());
		System.out.println(g.getSommets());
		assertTrue(g.contientSommet("C"));
		assertFalse(g.contientSommet("c"));
		assertTrue(g.contientArc("C","H"));
		assertFalse(g.contientArc("H","C"));
		assertEquals(7,g.getValuation("E", "H"));
		assertEquals(List.of("B","C", "E"), g.getSucc("D"));
		assertEquals(g31, g.toString());

		//Illegal Argument
//		g.ajouterArc("A","C",2);
	}

	void testerFonctionOterSommet(IGraphe g) {
		List<String> som1 = List.of("A","B","C","D","E","F","G","H","I","J");
		List<String> som2 = List.of("A","D","E","F","G","H","I","J");
		List<String> som3 = List.of("J");
		List<String> som4 = List.of("F");
		List<String> som5 = List.of();
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
		System.out.println(g.getSommets());

//		Tester illegal Argument
//		g.oterArc("G", "H");


		//Il reste comme Arc G-F(1)
		assertEquals(som2,g.getSucc("A"));
		assertEquals(som2,g.getSucc("J"));
		assertEquals(som3,g.getSucc("G"));
		assertEquals(som2,g.getSucc("F"));
		assertEquals(-1,g.getValuation("A","C"));
		assertEquals(-1,g.getValuation("H","I"));
		assertEquals(1,g.getValuation("G","F"));
	}

}
