import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Thijs Schoppema
 * @klas BIN-2a
 * @Studentennumeer 580144
 * @Datum 07-02-2018
 */
class VirusLogica {
	
	static BufferedReader inFile;
	static URL url;
    static InputStream is = null;
    static HashMap<String, Set<Integer>> host_virus;
    static HashMap<Integer, Virus> id_virus;
    static Set<Virus> virusenHost1, virusenHost2, intersection;

	/**
	 * @param a, bepalend of URL of bestand wordt geselecteerd
	 * @param b, de URL of absoluut path van het bestand
	 * @throws IOException 
	 * @returnt map, dit is een HashMap met daarin 2 Hashmaps, een daarvin heeft de host namen met als values de ID's van virusen, de 2e heeft de ID's van virusen met als values de Objecten virusen
	 * @functie leest de URL pagina of het bestand, en slaat de benodigde data op in het Virus object, en de 2 Hashmaps
	 */
	protected static Map<String, HashMap> readFile (int a, String b) throws IOException, NumberFormatException {
		
		if (a == 0) {
			
			url = new URL(b);
			is = url.openStream();
	        inFile = new BufferedReader(new InputStreamReader(is));
	        
		} else inFile = new BufferedReader(new FileReader(b));
		
        String line = inFile.readLine();            
        host_virus = new HashMap <String, Set<Integer>> ();
        id_virus = new HashMap<Integer, Virus>();
        
        Virus v;
        String[] buffer;
        String host;
        Set<Integer> idSet;
        
        while ((line = inFile.readLine()) != null) {
        	
            buffer = line.split("\t");

            if (buffer.length > 7) {
            	if (!(id_virus.containsKey(Integer.parseInt(buffer[0])))) {
            		
            		v = new Virus();
            		v.setId(Integer.parseInt(buffer[0]));
	            	v.setSoort(buffer[1]);
	            	v.setClassificatie(buffer[2]);
	            	id_virus.put(Integer.parseInt(buffer[0]), v);
	            	
	            	if (buffer.length == 12) {
	            		v.setPmid(buffer[10]);
	            	}
            	} else {
            		v = id_virus.get(Integer.parseInt(buffer[0]));
            		if (buffer.length == 12) {
	            		v.setPmid(buffer[10]);
	            	}
            	}
            	
            	host = buffer[7] + " ("+ buffer[8] + ")";
            	v.setHostList(host);
            	
            	if (host_virus.containsKey(host)) {
            		
            		idSet = host_virus.get(host);
            		idSet.add(Integer.parseInt(buffer[0]));
            		host_virus.put(host, idSet);
            	} else {
            		
            		idSet = new HashSet();
            		idSet.add(Integer.parseInt(buffer[0]));
            		host_virus.put(host, idSet);
            	}
            }
        }
        
        inFile.close();
        if (a == 0) is.close();
        
        Map<String, HashMap> map = new HashMap();
        map.put("host_virus",host_virus);
        map.put("id_virus",id_virus);
        
        return map;
	}

	/**
	 * @param hostID1
	 * @param hostID2
	 * @param viralClass
	 * @return map, dit is een Hashmap met daarin 3 sets gevult met de Objecten Virus. deze 3 sets staan voor host1, host2 en de intersectie ertussen
	 * @functie deze functie haalt per host uit de hashmap host_virus de sets met virusen op. deze virusen controleert hij op class(ssdna/ssrna etc) en komt vervolgens met de intersectie tussen beide sets
	 */
	protected static Map<String, Set<Virus>> krijgVirusen(String hostID1, String hostID2, String viralClass) {
				
		virusenHost1 = new HashSet<Virus>();
		virusenHost2 = new HashSet<Virus>();
		
		Set<Integer> virusenID = null;
		int id;
		Virus v;
		String clas;
		viralClass = viralClass.toLowerCase();
		
		for(int i = 0; i < 2; i++) {
			
			if (i == 0) virusenID = host_virus.get(hostID1);
			else if (i == 1) virusenID = host_virus.get(hostID2);
			
			for(Iterator<Integer> it = virusenID.iterator(); it.hasNext();) {
				
				id = it.next();
				v = id_virus.get(id);
				clas = v.getClassificatie().toLowerCase();
				
				if(viralClass.equals("none") || clas.contains(viralClass) ||
				   viralClass.equals("satellites or virophages") && (clas.contains("satellites") || clas.contains("virophages"))) {
					
					if (i == 0) {
						virusenHost1.add(v);
					}
					if(i == 1) {
						virusenHost2.add(v);
					}
				} else if (viralClass.equals("others") && !(clas.contains("dsdna") | clas.contains("ssdna") | clas.contains("ssrna") | clas.contains("dsrna") | clas.contains("retro")
						   | clas.contains("satellites") | clas.contains("virophages") | clas.contains("viroid"))) {
					
					if (i == 0) {
						virusenHost1.add(v);
					}
					if(i == 1) {
						virusenHost2.add(v);
					}
				}
			}
		}
		
		Map<String, Set<Virus>> map = new HashMap();
		map.put("ID1", virusenHost1);
		map.put("ID2", virusenHost2);
		intersection = new HashSet<Virus>(virusenHost1);
		intersection.retainAll(virusenHost2);
		map.put("Intersectoin", intersection);
		
		return map;
	}
}