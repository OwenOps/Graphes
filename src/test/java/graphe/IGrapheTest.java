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
			g.peupler(g31a);
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
//		List<String> som1 = new ArrayList<>();
//		for (int i = 1; i <= 10; i++) {
//			som1.add(Integer.toString(i));
//		}
//		assertEquals(som1,g.getSommets());
		assertEquals("5", a.getSource());
		assertEquals("7", a.getDestination());
	}

	/*void testImportation(IGraphe g) {
		Arc a = GraphImporter.importer("src/test/java/graphe/grapheImporter/g-100-5.txt", g);
		List<String> som1 = new ArrayList<>();
		for (int i = 1; i <= 100; i++) {
			som1.add(Integer.toString(i));
		}
		assertEquals(som1,g.getSommets());
		assertEquals("1-12(5), 1-21(4), 1-3(1), 1-30(3), 1-31(4), 1-35(4), 1-48(1), 1-5(3), 1-52(5), 1-57(2), " +
				"1-6(5), 1-68(3), 1-69(5), 1-7(1), 1-71(4), 1-79(3), 10-11(2), 10-17(2), 10-19(3), 10-38(4), 10-5(2), 10-85(4), " +
				"100-37(4), 11-17(3), 11-81(1), 12-21(2), 12-24(2), 12-5(2), 12-52(2), 13-49(2), 13-6(2), 13-9(4), 14-22(2), 14-77(1), " +
				"15-99(3), 16-1(2), 16-44(5), 16-5(4), 17:, 18-1(1), 18-6(2), 19-23(1), 19-26(4), 2-1(1), 2-28(4), 2-3(3), 2-5(3), 2-76(2), 2-8(1), 20-15(2), " +
				"21-29(1), 21-47(4), 22:, 23-10(3), 24-21(5), 25-1(4), 25-6(3), 26-10(3), 27-2(2), 27-8(2), 28-63(2), 28-64(3), 28-76(3), 28-90(2), 28-97(2), 29-12(4), 3-14(1), 3-15(1), " +
				"3-20(1), 3-32(1), 3-4(4), 3-42(5), 3-43(3), 3-51(5), 3-57(3), 3-7(2), 3-77(4), 3-80(5), 30-65(1), 30-71(1), 31-16(5), 31-83(1), 31-96(1), 32:, 33-3(1), 33-32(4), 33-80(5), " +
				"34-6(2), 34-9(1), 35-74(4), 35-9(1), 36-21(3), 36-29(5), 37-6(2), 38-17(3), 39-1(5), 39-16(5), 4-1(3), 4-43(4), 40-11(1), 40-6(4), 41-37(5), 42-1(5), 43:, 44-1(5), 44-48(4), " +
				"45-40(2), 46-1(1), 46-44(3), 47-12(4), 48:, 49-6(2), 5-19(2), 5-28(4), 5-37(4), 5-41(1), 5-6(5), 50-11(1), 50-70(1), 51-14(4), 52-69(2), 53-32(2), 53-60(3), 53-66(3), 53-7(5), " +
				"54-17(5), 54-38(2), 55-44(2), 55-48(4), 56-3(1), 56-8(3), 57-73(3), 58-15(4), 58-20(5), 58-59(1), 59-15(4), 6-10(3), 6-100(5), 6-11(1), 6-45(2), 6-50(5), 6-62(2), 60-66(5), 60-7(5), " +
				"60-94(5), 61-27(4), 61-8(3), 62-37(3), 62-84(2), 63-5(1), 63-86(4), 63-95(5), 64-5(4), 65-9(5), 66:, 67-1(3), 67-25(2), 68-44(4), 69-79(1), 7-14(4), 7-22(3), 7-32(4), 70-6(5), 71:, " +
				"72-1(4), 72-3(5), 73-1(3), 74-88(3), 75-44(2), 75-68(1), 76-90(1), 77:, 78-2(4), 78-5(2), 79:, 8-15(3), 8-3(5), 80:, 81-10(3), 82-11(1), 82-50(3), 83-16(3), 84-6(5), 85-38(2), 86-28(4), " +
				"87-28(2), 87-76(4), 88-9(5), 89-44(4), 89-48(4), 9-1(1), 9-30(3), 9-6(3), 9-74(2), 90-97(5), 91-3(3), 91-32(2), 91-93(3), 92-30(5), 92-71(4), 93-3(1), 94-66(2), 95-5(2), 96-16(5), 97:, " +
				"98-1(1), 98-9(4), 99-20(4)", g.toString());

		assertEquals(List.of("3", "5", "6", "7", "12", "21", "30", "31", "35", "48", "52", "57", "68", "69", "71", "79"), g.getSucc("1"));
		assertEquals(List.of("1", "3", "5", "8", "28", "76"), g.getSucc("2"));
		assertEquals(List.of("4", "7", "14", "15", "20", "32", "42", "43", "51", "57", "77", "80"), g.getSucc("3"));
		assertEquals(List.of("1", "43"), g.getSucc("4"));
		assertEquals(List.of("6", "19", "28", "37", "41"), g.getSucc("5"));
		assertEquals(List.of("10", "11", "45", "50", "62", "100"), g.getSucc("6"));
		assertEquals(List.of("14", "22", "32"), g.getSucc("7"));
		assertEquals(List.of("27", "56", "61"), g.getSucc("8"));
		assertEquals(List.of("9", "13", "34", "65"), g.getSucc("9"));
		assertEquals(List.of("5", "10", "17", "19", "38", "85"), g.getSucc("10"));
		assertEquals(List.of("17", "81"), g.getSucc("11"));
		assertEquals(List.of("5", "12", "21", "24", "52"), g.getSucc("12"));
		assertEquals(List.of("6", "9", "49"), g.getSucc("13"));
		assertEquals(List.of("14", "77"), g.getSucc("14"));
		assertEquals(List.of("99"), g.getSucc("15"));
		assertEquals(List.of("1", "5", "44"), g.getSucc("16"));
		assertEquals(List.of(), g.getSucc("17"));
		assertEquals(List.of("6"), g.getSucc("18"));
		assertEquals(List.of("23", "26"), g.getSucc("19"));
		assertEquals(List.of("29", "47"), g.getSucc("21"));
		assertEquals(List.of("10"), g.getSucc("23"));
		assertEquals(List.of("21"), g.getSucc("24"));
		assertEquals(List.of("1", "6"), g.getSucc("25"));
		assertEquals(List.of("10"), g.getSucc("26"));
		assertEquals(List.of("2", "8"), g.getSucc("27"));
		assertEquals(List.of("64", "63", "76", "90", "97"), g.getSucc("28"));
		assertEquals(List.of("4"), g.getSucc("32"));
		assertEquals(List.of("3", "32", "80"), g.getSucc("33"));
		assertEquals(List.of("9", "6"), g.getSucc("34"));
		assertEquals(List.of("9", "74"), g.getSucc("35"));
		assertEquals(List.of("21", "29"), g.getSucc("36"));
		assertEquals(List.of("6"), g.getSucc("37"));
		assertEquals(List.of("17"), g.getSucc("38"));
		assertEquals(List.of("1", "16"), g.getSucc("39"));
		assertEquals(List.of("6", "11"), g.getSucc("40"));
		assertEquals(List.of("37"), g.getSucc("41"));
	}*/

	@Test
	void importer() throws NumberFormatException, FileNotFoundException {
		for (IGraphe g : graphes) {
			testImportation(g);
		}
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
