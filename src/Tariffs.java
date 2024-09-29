public enum Tariffs {
    SMALL_BOX(10),
    MEDIUM_BOX(15),
    LARGE_BOX(20),
    SMALL_BOX_QUICK(13),
    MEDIUM_BOX_QUICK(18),
    LARGE_BOX_QUICK(25);

    private int price;

    private Tariffs(int price){
        this.price = price;
    }
    public int getPrice(){
        return price;
    }
    public static void PrintInfoShop(){
        String leftAlignFormat = "| %-7s | %-40s | %-10.10s |%n";

        System.out.format("+---------+------------------------------------------+------------+%n");
        System.out.format("| Тариф   | Описание                                 | Цена       |%n");
        System.out.format("+---------+------------------------------------------+------------+%n");
        System.out.format(leftAlignFormat, "1", "Маленькая коробка", Tariffs.SMALL_BOX.getPrice());
        System.out.format(leftAlignFormat, "2", "Средняя коробка", Tariffs.MEDIUM_BOX.getPrice());
        System.out.format(leftAlignFormat, "3", "Большая коробка", Tariffs.LARGE_BOX.getPrice());
        System.out.format(leftAlignFormat, "4", "Маленькая коробка (быстрая доставка)", Tariffs.SMALL_BOX_QUICK.getPrice());
        System.out.format(leftAlignFormat, "5", "Средняя коробка (быстрая доставка)", Tariffs.MEDIUM_BOX_QUICK.getPrice());
        System.out.format(leftAlignFormat, "6", "Большая коробка (быстрая доставка)", Tariffs.LARGE_BOX_QUICK.getPrice());
        System.out.format("+---------+------------------------------------------+------------+%n");


    }
}
