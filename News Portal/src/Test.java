public class Test {
    
    public static void main(String[] args) {
        
        Entertainment entertainment = new Entertainment("entertainment_and_arts");
        entertainment.printCategory();
        System.out.println("-------------------------------------------------------------------------------------------------------");
        entertainment.printCategory(entertainment.getTitles().length());
        System.out.println("-------------------------------------------------------------------------------------------------------");
        entertainment.printTitles();
        System.out.println("-------------------------------------------------------------------------------------------------------");
        entertainment.printLinks();
        System.out.println("-------------------------------------------------------------------------------------------------------");
        entertainment.printImages();
    }
}
