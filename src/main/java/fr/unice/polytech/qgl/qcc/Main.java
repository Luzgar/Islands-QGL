package fr.unice.polytech.qgl.qcc;


public class Main {

    public static void main(String[] args) throws Exception {

        WeekRunner week46 = new WeekRunner(new String[]{"1000", "WOOD", "300", "QUARTZ", "10", "FLOWER"}, 7000, 15, "week46", 0xE2D3535222C0D8B2L, 1, 1, "EAST");
        WeekRunner week47 = new WeekRunner(new String[]{"1000", "WOOD", "300", "QUARTZ", "10", "FLOWER"}, 7000, 15, "week47", 0x7C86C8F0AE471824L, 1, 1, "EAST");
        WeekRunner week48 = new WeekRunner(new String[]{"2000", "WOOD", "30", "GLASS", "1000", "FUR"}, 10000, 3, "week48", 0x7099D003D471C6D9L, 152, 159, "NORTH");
        WeekRunner week49 = new WeekRunner(new String[]{"5000", "WOOD", "300", "GLASS", "1000", "SUGAR_CANE"}, 10000, 3, "week49", 0x19ABF6AA7B22F38BL, 152, 159, "NORTH");
        WeekRunner week50 = new WeekRunner(new String[]{"1000", "WOOD", "300", "GLASS", "5000", "FUR"}, 10000, 3, "week50", 0xBE3EF65BEF2BD459L, 1, 1, "EAST");
        WeekRunner week51 = new WeekRunner(new String[]{"3000", "WOOD", "100", "GLASS", "3000", "FUR"}, 10000, 25, "week51", 0x9B4937D7A7783C0EL, 1, 1, "SOUTH");
        WeekRunner week52 = new WeekRunner(new String[]{"4000", "WOOD", "100", "GLASS", "1000", "FUR"}, 10000, 25, "week52", 0x6C3E51CEEFA93D5FL, 1, 1, "EAST");
        WeekRunner week53 = new WeekRunner(new String[]{"5000", "WOOD", "100", "GLASS", "80", "FLOWER"}, 10000, 25, "week53", 0x68C81A2529ACAFF3L, 1, 1, "SOUTH");
        WeekRunner week01 = new WeekRunner(new String[]{"3000", "WOOD", "800", "QUARTZ", "80", "FLOWER", "1000", "PLANK"}, 15000, 4, "week01", 0x8E80C1B12F0B7C40L, 1, 1, "EAST");
        WeekRunner week02 = new WeekRunner(new String[]{"5000", "WOOD", "200", "QUARTZ", "40", "FLOWER", "50", "GLASS"}, 15000, 4, "week02", 0x71CE0F48848F322DL, 1, 1, "EAST");
        WeekRunner week03 = new WeekRunner(new String[]{"5000", "WOOD", "200", "QUARTZ", "40", "FLOWER", "50", "GLASS"}, 20000, 15, "week03", 0xC0ECF96E85B08EFEL, 1, 1, "SOUTH");
        WeekRunner week04 = new WeekRunner(new String[]{"4000", "WOOD", "100", "GLASS", "1000", "FUR"}, 10000, 25, "week04", 0x6C3E51CEEFA93D5FL, 1, 1, "EAST");
        WeekRunner week05 = new WeekRunner(new String[]{"7000", "WOOD", "100", "GLASS", "2000", "FUR", "5000", "PLANK"}, 20000, 25, "week05", 0x3EAC7E4FF2A31F33L, 1, 1, "SOUTH");
        WeekRunner week06 = new WeekRunner(new String[]{"15000", "WOOD", "100", "GLASS"}, 20000, 25, "week06", 0x1DB7EB420E31D98AL, 1, 159, "NORTH");
        WeekRunner week07 = new WeekRunner(new String[]{"15000", "WOOD", "30", "LEATHER", "100", "GLASS"}, 20000, 25, "week07", 0xEC4F4F3A4630034BL, 1, 159, "NORTH");
        WeekRunner week08 = new WeekRunner(new String[]{"10000", "WOOD", "300", "LEATHER", "50", "GLASS"}, 20000, 15, "week08", 0xAE6942D415A29E7FL, 1, 159, "NORTH");
        WeekRunner week09 = new WeekRunner(new String[]{"10000", "WOOD", "300", "LEATHER", "50", "GLASS"}, 20000, 15, "week09", 0xDE577ABFB68A7E38L, 1, 1, "EAST");
        WeekRunner week10 = new WeekRunner(new String[]{"50", "RUM", "300", "INGOT", "500", "QUARTZ", "10000", "WOOD"}, 20000, 15, "week10", 0xDE05C9B2CE32BF74L, 159, 159, "NORTH");
        WeekRunner week11 = new WeekRunner(new String[]{"10000", "WOOD", "100", "QUARTZ","300", "LEATHER", "50", "ORE"}, 20000, 15, "week11", 0xC70825E4D144AA25L, 1, 1, "SOUTH");


        week46.run();
        week47.run();
        week48.run();
        week49.run();
        week50.run();
        week51.run();
        week52.run();
        week53.run();
        week01.run();
        week02.run();
        week03.run();
        week04.run();
        week05.run();
        week06.run();
        week07.run();
        week08.run();
        week09.run();
        week10.run();
        week11.run();
    }
}