abstract class News{ // main purpose of making abstract class is because we do not want to create objects using this class directly.
    
    private WebScraper webScraper = new WebScraper();

    private String category;
    private int viewsCounter;
    private LinkedList<String> titles = new LinkedList<>(); // Title of the news.
    private LinkedList<String> links = new LinkedList<>(); // Link to the news' page.
    private LinkedList<String> images = new LinkedList<>(); // Link to the images of the news.

    protected News(String category) {
        this.category = category;
        this.viewsCounter = 0;
        
        // Assign the fetched data to ArrayLists.
        this.assignTitlesAndLinks();
    }

    // Fetch the related category's news' titles and links.
    private void assignTitlesAndLinks() {

        this.webScraper.fetchNews(this.category);
        this.titles = this.webScraper.getTitles();
        this.links = this.webScraper.getLinks();
        this.images = this.webScraper.getImages();
    }

    protected String getDetailedText(String url) {

        return this.webScraper.fetchDetailedText(url);
    }

    protected String getCategory() {

        return this.category;
    }

    protected void reviseCategoryName(String adjustedName) {

        this.category = adjustedName;
    }

    protected int getViewsCounter() {

        return this.viewsCounter;
    }

    protected void increaseViewsCounter() {

        this.viewsCounter++;
    }

    protected LinkedList<String> getTitles() {

        return this.titles;
    }

    protected LinkedList<String> getLinks() {

        return this.links;
    }

    protected LinkedList<String> getImages() {

        return this.images;
    }

    // This function prints the news' titles.
    protected void printTitles() {

        this.titles.printList();
    }

    // This function prints the news' links.
    protected void printLinks() {

        this.links.printList();
    }

    protected void printImages() {

        this.images.printList();
    }

    // Method to be overloaded and overrided.
    protected void printCategory() {

    }
}
