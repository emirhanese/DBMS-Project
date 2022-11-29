public class Science extends News{

    private String cat;

    protected Science(String category) {
        super(category);
        this.cat = "Science";
        super.reviseCategoryName(this.cat);
    }
    
    @Override
    protected void printCategory() {
        
        System.out.println("Category: " + this.cat);
    }
    
    // Overloading the printCategory function of the News class.
    protected void printCategory(int newsCount) {

        System.out.println("There are " + newsCount + " news in " + this.cat + " category");
    }
}
