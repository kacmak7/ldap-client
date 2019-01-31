package temp_delme;

import java.util.SortedSet;
import java.util.TreeSet;

import org.parafia.model.Family;
import org.parafia.model.PriestlyVisit;

public class Test {
	public static void main(String[] args) {
		SortedSet<PriestlyVisit> visits = new TreeSet<PriestlyVisit>();
		Family family = new Family();
		
		PriestlyVisit vis1 = new PriestlyVisit();
		vis1.setFamily(family);
		vis1.setId(1l);
		vis1.setDate("02-2000");
		vis1.setRemarks("");
		visits.add(vis1);
		
		PriestlyVisit vis2 = new PriestlyVisit();
		vis2.setFamily(new Family());
		vis2.setId(2l);
		vis2.setDate("14-12-2000");
		vis2.setRemarks("qweqew");
		visits.add(vis2);
		
		for (PriestlyVisit visit : visits) {
			System.out.println(visit);
		}
		
		System.out.println(visits.size());
			
	}
}
