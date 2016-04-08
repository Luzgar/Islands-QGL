package fr.unice.polytech.qgl.qcc.database.enums;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 22/11/15.
 */
public enum Biomes {

    //Common biomes:
    
    OCEAN("OCEAN"), //plain ocean, wide open area full of unknown;
    LAKE("LAKE"), //internal lake, potentially big, with freshwater;
    BEACH("BEACH"), //beach (not always sandy);
    GRASSLAND("GRASSLAND"), //area of prairie;
    
    //Tropical biomes:
    
    MANGROVE("MANGROVE"), //super wet area, home of the mangle tree;
    TROPICAL_RAIN_FOREST("TROPICAL_RAIN_FOREST"), //hot and wet;
    TROPICAL_SEASONAL_FOREST("TROPICAL_SEASONAL_FOREST"), //less wet, but not less hot;

    //Temperate biomes

    TEMPERATE_DECIDUOUS_FOREST("TEMPERATE_DECIDUOUS_FOREST"), //classical forests with trees that lose their leaves each year;
    TEMPERATE_RAIN_FOREST("TEMPERATE_RAIN_FOREST"), //very rare biome, very wet area coupled to low temperatures;
    TEMPERATE_DESERT("TEMPERATE_DESERT"), //aride area with sparse vegetation and very few humidity;
    
    //Nordic / Mountain Biomes
    
    TAIGA("TAIGA"), //boreal forest, cold and majestuous;
    SNOW("SNOW"), //area covered with snow, wet and cold;
    TUNDRA("TUNDRA"), //arctic prairie, surviving on permanently frozen soil;
    ALPINE("ALPINE"), //rocky mountains, not always covered by snow;
    GLACIER("GLACIER"), //inhospitable area, full of ice;

    //Subtropical biomes

    SHRUBLAND("SHRUBLAND"), //prairie dominated by shrubs, such as maquis in Corsica or garrigue in Provence;
    SUB_TROPICAL_DESERT("SUB_TROPICAL_DESERT"); //very dry and inhospitable area

    private String biomeString = "";

    Biomes (String biomeString){
        this.biomeString = biomeString;
    }

    @Override
    public String toString(){
        return biomeString;
    }

    private static Map<Biomes, ArrayList<Ressource>> ressourceMap = new HashMap<>();

    //Trouve-t-on de la ressource demandée dans le biome demandé?
    public static boolean checkRessource(Biomes biome, Biomes.Ressource ressource){
        for(Ressource res : ressourceMap.get(biome)){
            if(res.equals(ressource) )
                return true;
        }
        return false;
    }

    //Pour une ressource donnée, dans quels biomes la trouve-t-on?
    public static List<Biomes> checkBiomes(Ressource ressource){
        ArrayList<Biomes> biomes = new ArrayList<>();
        for(Biomes b : ressourceMap.keySet()){
            if(checkRessource(b, ressource))
                biomes.add(b);
        }
        return biomes;
    }

    public static void fill() {
        ressourceMap.put(Biomes.OCEAN, new ArrayList<Ressource>());
        ressourceMap.put(Biomes.LAKE, new ArrayList<Ressource>());
        ressourceMap.put(Biomes.BEACH, new ArrayList<Ressource>());
        ressourceMap.put(Biomes.GRASSLAND, new ArrayList<Ressource>());

        ressourceMap.put(Biomes.MANGROVE, new ArrayList<Ressource>());
        ressourceMap.put(TROPICAL_RAIN_FOREST, new ArrayList<Ressource>());
        ressourceMap.put(TROPICAL_SEASONAL_FOREST, new ArrayList<Ressource>());

        ressourceMap.put(Biomes.TEMPERATE_DECIDUOUS_FOREST, new ArrayList<Ressource>());
        ressourceMap.put(TEMPERATE_RAIN_FOREST, new ArrayList<Ressource>());
        ressourceMap.put(TEMPERATE_DESERT, new ArrayList<Ressource>());

        ressourceMap.put(Biomes.TAIGA, new ArrayList<Ressource>());
        ressourceMap.put(Biomes.SNOW, new ArrayList<Ressource>());
        ressourceMap.put(Biomes.TUNDRA, new ArrayList<Ressource>());
        ressourceMap.put(Biomes.ALPINE, new ArrayList<Ressource>());
        ressourceMap.put(Biomes.GLACIER, new ArrayList<Ressource>());

        ressourceMap.put(Biomes.SHRUBLAND, new ArrayList<Ressource>());
        ressourceMap.put(Biomes.SUB_TROPICAL_DESERT, new ArrayList<Ressource>());
        //-------------------------AJOUTONS-LES-VALEURS------------------------------------------------
        ressourceMap.get(Biomes.OCEAN).add(Ressource.FISH);
        ressourceMap.get(Biomes.LAKE).add(Ressource.FISH);
        ressourceMap.get(Biomes.BEACH).add(Ressource.QUARTZ);
        ressourceMap.get(Biomes.GRASSLAND).add(Ressource.FUR);

        ressourceMap.get(Biomes.MANGROVE).add(Ressource.WOOD);
        ressourceMap.get(Biomes.MANGROVE).add(Ressource.FLOWER);
        ressourceMap.get(TROPICAL_RAIN_FOREST).add(Ressource.WOOD);
        ressourceMap.get(TROPICAL_RAIN_FOREST).add(Ressource.SUGAR_CANE);
        ressourceMap.get(TROPICAL_RAIN_FOREST).add(Ressource.FRUITS);
        ressourceMap.get(TROPICAL_SEASONAL_FOREST).add(Ressource.WOOD);
        ressourceMap.get(TROPICAL_SEASONAL_FOREST).add(Ressource.SUGAR_CANE);
        ressourceMap.get(TROPICAL_SEASONAL_FOREST).add(Ressource.FRUITS);

        ressourceMap.get(Biomes.TEMPERATE_DECIDUOUS_FOREST).add(Ressource.WOOD);
        ressourceMap.get(TEMPERATE_RAIN_FOREST).add(Ressource.WOOD);
        ressourceMap.get(TEMPERATE_RAIN_FOREST).add(Ressource.FUR);
        ressourceMap.get(TEMPERATE_DESERT).add(Ressource.ORE);
        ressourceMap.get(TEMPERATE_DESERT).add(Ressource.QUARTZ);

        ressourceMap.get(Biomes.TAIGA).add(Ressource.WOOD);
        ressourceMap.get(Biomes.TUNDRA).add(Ressource.FUR);
        ressourceMap.get(Biomes.ALPINE).add(Ressource.FLOWER);
        ressourceMap.get(Biomes.ALPINE).add(Ressource.ORE);
        ressourceMap.get(Biomes.GLACIER).add(Ressource.FLOWER);

        ressourceMap.get(Biomes.SHRUBLAND).add(Ressource.FUR);
        ressourceMap.get(Biomes.SUB_TROPICAL_DESERT).add(Ressource.QUARTZ);
        ressourceMap.get(Biomes.SUB_TROPICAL_DESERT).add(Ressource.ORE);


    }

    public enum Ressource{
        FISH,
        FLOWER,
        FRUITS,
        FUR,
        ORE,
        QUARTZ,
        SUGAR_CANE,
        WOOD;

        public static boolean contains(String x){
            for(Ressource r : Ressource.values()){
                if(r.toString().equals(x))
                    return true;
            }
            return false;
        }

        public static int minimalAverageRessource(Ressource res){
            if(res.equals(FISH))
                return 20;
            else if(res.equals(FLOWER))
                return 1;
            else if(res.equals(FRUITS))
                return 6;
            else if(res.equals(FUR))
                return 5;
            else if(res.equals(ORE))
                return 2;
            else if(res.equals(QUARTZ))
                return 1;
            else if(res.equals(SUGAR_CANE))
                return 10;
            else //WOOD
                return 20;
        }
    }
}
