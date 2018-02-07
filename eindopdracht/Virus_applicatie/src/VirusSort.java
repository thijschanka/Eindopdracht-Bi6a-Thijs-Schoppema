import java.util.Comparator;

/**
 * @author Thijs Schoppema
 * @klas BIN-2a
 * @Studentennumeer 580144
 * @Datum 07-02-2018
 */
class IdComparator implements Comparator<Virus> {
    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     * @Funtie sorteert op basis van ID
     */
    @Override
    public int compare(Virus a, Virus b) {
        if (a.getId() < b.getId()) {
        	return -1;
        } else if (a.getId() == b.getId()) {
        	return 0;
        } else if (a.getId() < b.getId()) {
        	return 1;
        } return 0;
    }
}

class NumHostsComparator implements Comparator<Virus> {
    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     * @Funtie sorteert op basis van aantal hosts
     */
    @Override
    public int compare(Virus a, Virus b) {
    	if (a.getHostList().size() < b.getHostList().size()) {
        	return -1;
        } else if (a.getHostList().size() == b.getHostList().size()) {
        	return 0;
        } else if (a.getHostList().size() < b.getHostList().size()) {
        	return 1;
        } return 0;
    }
}

class ClassificatieComparator implements Comparator<Virus> {
    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     * @Funtie sorteert op basis van classificatie
     */
    @Override
    public int compare(Virus a, Virus b) {
    	String c = a.getClassificatie().toLowerCase();
    	String d = b.getClassificatie().toLowerCase();
    	return c.compareTo(d);
    }
}

class SoortComparator implements Comparator<Virus> {
    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     * @Funtie sorteert op basis van classificatie
     */
    @Override
    public int compare(Virus a, Virus b) {
    	String c = a.getSoort().toLowerCase();
    	String d = b.getSoort().toLowerCase();
    	return c.compareTo(d);
    }
}

class HostComparator implements Comparator<String> {
    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     * @Funtie sorteert op basis van classificatie
     */
    @Override
    public int compare(String a, String b) {
    	Integer c = Integer.parseInt(a.split(" \\(")[0]);
    	Integer d = Integer.parseInt(b.split(" \\(")[0]);
    	return c.compareTo(d);
    }
}