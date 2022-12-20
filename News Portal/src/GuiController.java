import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiController {

    private Business business = new Business("business");
    private Health health = new Health("health");
    private Technology technology = new Technology("technology");
    private Science science = new Science("science_and_environment");
    private Entertainment entertainment = new Entertainment("entertainment_and_arts");

    private ArrayList<Label> newsLabels = new ArrayList<>();
    private Stack<Object> history = new Stack<>();
    private BinarySearchTree bst = new BinarySearchTree();
    private ArrayList<String> viewedNews = new ArrayList<>();
    private Object category = null;

    private Database db = null;
    private User loggedUser;

    private Label currentNewsLabel = null;

    @FXML
    private Button healthButton;

    @FXML
    private Button favoriteButton;

    @FXML
    private Button historyButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Label detailedText;

    @FXML
    private Button backButton;

    @FXML
    private VBox buttonsBox;

    @FXML
    private Label imageLabel;

    @FXML
    private VBox newsBox;

    @FXML
    private Button entertainmentButton;

    @FXML
    private Button scienceButton;

    @FXML
    private AnchorPane newsPane;

    @FXML
    private AnchorPane detailedTextPane;

    @FXML
    private AnchorPane newsDetailPane;

    @FXML
    private ScrollPane newsDetailScrollPane;

    @FXML
    private ScrollPane newsScrollPane;

    @FXML
    private Button technologyButton;

    @FXML
    private Button businessButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button loginButton1;

    @FXML
    private Button returnButton;

    @FXML
    private Button registerButton1;

    @FXML
    private Button returnButton1;

    @FXML
    private Pane mainMenu;

    @FXML
    private Pane loginMenu;

    @FXML
    private Pane registerMenu;

    @FXML
    private Pane homeCategoryPane;

    @FXML
    private StackPane homeNewsPane;

    @FXML
    private StackPane menuPage;

    @FXML
    private TextField registerNameField;

    @FXML
    private TextField registerSurnameField;

    @FXML
    private TextField registerMailField;

    @FXML
    private TextField registerAgeField;

    @FXML
    private TextField registerUsernameField;

    @FXML
    private PasswordField registerPassField;

    @FXML
    private PasswordField loginPassField;

    @FXML
    private TextField loginUsernameField;

    @FXML
    private Button postCommentButton;

    @FXML
    private Button showCommentsButton;

    @FXML
    private ScrollPane commentsScrollPane;

    @FXML
    private AnchorPane commentsPane;

    @FXML
    void entertainmentButtonClicked(MouseEvent event) {

        if (this.newsPane.isVisible()) {
            this.newsPane.setVisible(false);
            this.clearLabels();
        }

        this.entertainmentButton.setDisable(true);
        checkButtonState(this.entertainmentButton);
        LinkedList<String> imageLinks = entertainment.getImages();
        LinkedList<String> newsTitles = entertainment.getTitles();
        this.setImageAndTitle(imageLinks, newsTitles);
        this.adjustSize(imageLinks.length());
        this.category = entertainment;
        this.newsPane.setVisible(true);
    }

    @FXML
    void healthButtonClicked(MouseEvent event) {

        if (this.newsPane.isVisible()) {
            this.newsPane.setVisible(false);
            this.clearLabels();
        }

        this.healthButton.setDisable(true);
        checkButtonState(this.healthButton);
        LinkedList<String> imageLinks = health.getImages();
        LinkedList<String> newsTitles = health.getTitles();
        this.setImageAndTitle(imageLinks, newsTitles);
        this.adjustSize(imageLinks.length());
        this.category = health;
        this.newsPane.setVisible(true);
    }

    @FXML
    void scienceButtonClicked(MouseEvent event) {

        if (this.newsPane.isVisible()) {
            this.newsPane.setVisible(false);
            this.clearLabels();
        }

        this.scienceButton.setDisable(true);
        checkButtonState(this.scienceButton);
        LinkedList<String> imageLinks = science.getImages();
        LinkedList<String> newsTitles = science.getTitles();
        this.setImageAndTitle(imageLinks, newsTitles);
        this.adjustSize(imageLinks.length());
        this.category = science;
        this.newsPane.setVisible(true);
    }

    @FXML
    void technologyButtonClicked(MouseEvent event) {

        if (this.newsPane.isVisible()) {
            this.newsPane.setVisible(false);
            this.clearLabels();
        }

        this.technologyButton.setDisable(true);
        checkButtonState(this.technologyButton);
        LinkedList<String> imageLinks = technology.getImages();
        LinkedList<String> newsTitles = technology.getTitles();
        this.setImageAndTitle(imageLinks, newsTitles);
        this.adjustSize(imageLinks.length());
        this.category = technology;
        this.newsPane.setVisible(true);
    }

    @FXML
    void businessButtonClicked(MouseEvent event) {

        if (this.newsPane.isVisible()) {
            this.newsPane.setVisible(false);
            this.clearLabels();
        }

        this.businessButton.setDisable(true);
        checkButtonState(this.businessButton);
        LinkedList<String> imageLinks = business.getImages();
        LinkedList<String> newsTitles = business.getTitles();
        this.setImageAndTitle(imageLinks, newsTitles);
        this.adjustSize(imageLinks.length());
        this.category = business;
        this.newsPane.setVisible(true);
    }

    @FXML
    void backButtonClicked(MouseEvent event) {
        if(this.commentsScrollPane.isVisible()) {
            this.commentsScrollPane.setVisible(false);
            this.showCommentsButton.setDisable(false);
            this.detailedTextPane.setVisible(true);
        }

        else {
            this.history.push(this.titleLabel.getText());
            this.history.push(this.detailedText.getText());
            this.history.push(this.imageLabel.getGraphic());

            if (!viewedAlready(this.titleLabel.getText())) {

                this.viewedNews.add(this.titleLabel.getText());
                ((News) this.category).increaseViewsCounter();
                this.bst.updateTree();
            }

            historyButtonController();

            this.newsDetailScrollPane.setVisible(false);
            this.newsPane.setVisible(true);
        }
    }

    @FXML
    void favoriteButtonClicked(MouseEvent event) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Favorite Category");
        alert.setHeaderText("Your favorite news category is:  " + this.bst.getMostViewedCategory(this.bst.root));
        alert.setContentText("This message shows the most read news category by you.");
        alert.showAndWait();
    }

    @FXML
    void historyButtonClicked(MouseEvent event) {

        this.imageLabel.setGraphic(null);
        this.detailedText.setText("");
        this.titleLabel.setText("");

        this.imageLabel.setGraphic((Node)history.pop());
        this.detailedText.setText((String)history.pop());
        this.titleLabel.setText((String)history.pop());
        historyButtonController();
        this.detailedTextPane.prefHeightProperty().bind(this.detailedText.prefHeightProperty());
        this.newsPane.setVisible(false);
        this.newsDetailScrollPane.setVisible(true);
    }

    private void clearLabels() {

        for (int i = 0; i < this.newsLabels.size(); i++) {

            this.newsLabels.get(i).setText("");
            this.newsLabels.get(i).setGraphic(null);
        }
    }

    private void postComment(MouseEvent event) {

        Stage newWindow = new Stage();
        newWindow.setHeight(500);
        newWindow.setWidth(500);
        newWindow.setTitle("Post Comment");
        newWindow.getIcons().add(new Image("file:icons/post-comment.png"));

        AtomicBoolean postStatus = new AtomicBoolean(false);

        Label title = new Label("Enter your comment in the box.");
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        Button button = new Button("Post Comment");
        button.setDisable(true);
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            button.setDisable((textArea.getText().startsWith(" ") || textArea.getText().startsWith("\n")) ||
                    textArea.getText().length() == 0);
        });

        button.setOnAction(e -> {
            postStatus.set(db.postComment(textArea.getText(), db.getNewsId(currentNewsLabel.getText())));
            if(postStatus.get()) {
                processSuccessful("Comment has been posted successfully.");
                db.insertIntoUserComment(loggedUser.getId(), db.getCommentId(textArea.getText()));
            }
            newWindow.close();
        });
        VBox container = new VBox(title, textArea, button);

        container.setSpacing(15);
        container.setPadding(new Insets(25));
        container.setAlignment(Pos.CENTER);

        newWindow.setScene(new Scene(container));

        newWindow.show();
    }

    private void processSuccessful(String message) {

        Alert alert = new Alert(AlertType.INFORMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:icons/success.png"));
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.show();
    }

    private void showComments(MouseEvent event) {
       this.showCommentsButton.setDisable(true);
       String newsTitle = currentNewsLabel.getText();
       int newsId = db.getNewsId(newsTitle);
       List<Comment> comments = db.getComments(newsId);

       if(comments.isEmpty())
           commentsPane.setPrefHeight(582.0);

       else
           buildCommentsPage(comments);

       this.detailedTextPane.setVisible(false);
       this.commentsScrollPane.setVisible(true);
    }

    private void buildCommentsPage(List<Comment> comments) {
        if(!commentsPane.getChildren().isEmpty())
            commentsPane.getChildren().clear();

        VBox vbox = new VBox();
        vbox.setSpacing(65);
        vbox.heightProperty().addListener((observable, oldValue, newValue) -> {
            commentsPane.setPrefHeight(Double.parseDouble(newValue.toString()));
        });

        for (Comment value : comments) {
            HBox hbox = new HBox();
            hbox.setPadding(new Insets(20, 20, 20, 20));
            hbox.setSpacing(35);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setStyle("-fx-background-color:rgb(240, 219, 219);");

            Comment comment = value;
            int commentID = comment.getCommentId();
            String commentText = comment.getCommentText();
            Timestamp datePosted = comment.getDatePosted();
            User user = db.getUserByCommentID(commentID);

            Label nameLabel = new Label();
            nameLabel.setGraphic(new ImageView("file:icons/user.png"));
            nameLabel.setText(user.getName() + " " + user.getSurname());
            nameLabel.setGraphicTextGap(5);
            nameLabel.setContentDisplay(ContentDisplay.TOP);

            Label commentLabel = new Label();
            commentLabel.setText(commentText);
            commentLabel.setWrapText(true);
            commentLabel.setTextAlignment(TextAlignment.JUSTIFY);
            commentLabel.setMaxWidth(400);
            commentLabel.setContentDisplay(ContentDisplay.RIGHT);

            Label dateLabel = new Label();
            dateLabel.setText(datePosted.toString());
            dateLabel.setContentDisplay(ContentDisplay.RIGHT);

            hbox.getChildren().addAll(nameLabel, commentLabel, dateLabel);
            vbox.getChildren().add(hbox);
        }

        commentsPane.getChildren().add(vbox);
    }
    private void setImageAndTitle(LinkedList<String> imageLinks, LinkedList<String> newsTitles) {

        for (int i = 0; i < imageLinks.length(); i++) {

            String url = imageLinks.get(i);
            Image image = new Image(url);
            ImageView view = new ImageView(image);
            view.setFitHeight(190);
            view.setFitWidth(285);
            this.newsLabels.get(i).setGraphic(view);
            this.newsLabels.get(i).setContentDisplay(ContentDisplay.LEFT);
            this.newsLabels.get(i).setText(newsTitles.get(i));
            this.newsLabels.get(i).setGraphicTextGap(90);
        }

        sortNewsLabels(imageLinks);
    }

    // Bubble sort algorithm to sort news according to their titles.
    private void sortNewsLabels(LinkedList<String> imageLinks) {

        for(int i = 0; i < imageLinks.length() - 1; i++) {

            for(int j = 0; j < imageLinks.length() - i - 1; j++) {

                if((this.newsLabels.get(j)).getText().compareTo(((this.newsLabels.get(j + 1))).getText()) > 0) {

                    String tempText = this.newsLabels.get(j).getText();
                    ImageView tempGraphic = (ImageView) this.newsLabels.get(j).getGraphic();
                    this.newsLabels.get(j).setGraphic((ImageView)this.newsLabels.get(j + 1).getGraphic());
                    this.newsLabels.get(j).setText(this.newsLabels.get(j + 1).getText());
                    this.newsLabels.get(j + 1).setGraphic(tempGraphic);
                    this.newsLabels.get(j + 1).setText(tempText);
                }
            }
        }
    }

    private void mouseEntered(MouseEvent event) {

        Label label = (Label) event.getSource();
        label.setTextFill(Color.rgb(26, 13, 190));
        label.setCursor(Cursor.HAND);

    }

    private void mouseExited(MouseEvent event) {

        Label label = (Label) event.getSource();
        label.setTextFill(Color.BLACK);
    }

    private void adjustSize(int newsCount) {

        this.newsPane.setPrefSize(1280, (newsCount * 200.0) + ((newsCount - 1) * 15));
    }

    private boolean viewedAlready(String newsTitle) {

        return this.viewedNews.contains(newsTitle);
    }

    private void checkButtonState(Button clickedButton) {

        List<Node> buttons = this.buttonsBox.getChildren().stream().collect(Collectors.toList());

        for(Node button : buttons) {

            if((Button) button == clickedButton) 
                continue;

            ((Button) button).setDisable(false);
        }
    }

    private void loginMenuCheck() {

        if(this.loginUsernameField.getLength() > 0 && this.loginPassField.getLength() > 0) {
            this.loginButton1.setDisable(false);
        }

        else
            this.loginButton1.setDisable(true);
    }

    private void registerMenuCheck() {

        if(this.registerNameField.getLength() > 0 && this.registerSurnameField.getLength() > 0 && this.registerMailField.getLength() > 0
        && this.registerAgeField.getLength() > 0 && this.registerUsernameField.getLength() > 0 && this.registerPassField.getLength() > 0) {
            this.registerButton1.setDisable(false);
        }

        else
            this.registerButton1.setDisable(true);
    }

    private void login(MouseEvent event) {

        String username = this.loginUsernameField.getText();
        String pass = this.loginPassField.getText();

        loggedUser = db.authenticateUser(username, pass);

        if(loggedUser != null) {
            processSuccessful("Congratulations ! You've successfully logged in to News Portal.");
            this.business.setViewsCounter(db.getReadNewsAmountByCategory(loggedUser, this.business.getCategory()));
            this.entertainment.setViewsCounter(db.getReadNewsAmountByCategory(loggedUser, this.entertainment.getCategory()));
            this.health.setViewsCounter(db.getReadNewsAmountByCategory(loggedUser, this.health.getCategory()));
            this.science.setViewsCounter(db.getReadNewsAmountByCategory(loggedUser, this.science.getCategory()));
            this.technology.setViewsCounter(db.getReadNewsAmountByCategory(loggedUser, this.technology.getCategory()));
            this.bst.updateTree();
            loggedUser.setFavoriteCategory(this.bst.getMostViewedCategory(this.bst.root));
            db.updateUsersFavoriteCategory(loggedUser);
            this.menuPage.setVisible(false);
            this.homeCategoryPane.setVisible(true);
            this.homeNewsPane.setVisible(true);
            this.loginMenu.setVisible(false);
            this.mainMenu.setVisible(true);
        }

        else {
            processFailed("An error occurred while logging in !");
        }
    }

    private void processFailed(String message) {

        Alert alert = new Alert(AlertType.NONE);
        alert.setAlertType(AlertType.ERROR);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:icons/error.png"));
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.show();
    }

    private void register(MouseEvent event) {

        boolean registered;

        String name = this.registerNameField.getText();
        String surname = this.registerSurnameField.getText();
        String email = this.registerMailField.getText();
        int age = Integer.parseInt(this.registerAgeField.getText());
        String username = this.registerUsernameField.getText();
        String password = this.registerPassField.getText();

        User userToRegister = new User(name, surname, email, age, username, password);
        registered = db.addUser(userToRegister);

        if(registered) {
            processSuccessful("Congratulations! You have been successfully created your account.");
        }

        else {

            processFailed("There is already an account registered with this information.");
        }
    }

    private void labelClicked(MouseEvent event) {
        this.imageLabel.setGraphic(null);
        this.detailedText.setText("");
        this.titleLabel.setText("");

        Label label = (Label) event.getSource();
        currentNewsLabel = label;
        String title = label.getText();
        int index = ((News)this.category).getTitles().indexOf(title);

        String url = ((News)this.category).getImages().get(index);
        Image image = new Image(url);
        ImageView view = new ImageView(image);
        view.setFitHeight(227);
        view.setFitWidth(354);

        String link = ((News)this.category).getLinks().get(index);
        String text = ((News)this.category).getDetailedText(link);

        this.imageLabel.setGraphic(view);
        this.detailedText.setText(text);
        this.titleLabel.setText(title);
        this.detailedTextPane.prefHeightProperty().bind(this.detailedText.prefHeightProperty());
        this.newsPane.setVisible(false);
        this.newsDetailScrollPane.setVisible(true);
        db.insertReadNews(title, url, text, ((News) this.category).getCategory());
        db.insertIntoUserNews(loggedUser.getId(), db.getNewsId(title));
    }

    private void hisfavMouseEntered(MouseEvent event) {

        Button button = (Button) event.getSource();
        button.setStyle(button.getStyle().replace("black", "red"));
    }

    private void hisfavMouseExited(MouseEvent event) {

        Button button = (Button) event.getSource();
        button.setStyle(button.getStyle().replace("red", "black"));
    }

    private void historyButtonController() {

        if(this.history.isEmpty()) {

            this.historyButton.setDisable(true);
        }

        else {

            this.historyButton.setDisable(false);
        }
    }

    private void exitButtonClicked(MouseEvent event) {

        Platform.exit();
        System.exit(1);
    }

    private void loginButtonClicked(MouseEvent event) {

        this.mainMenu.setVisible(false);
        this.loginMenu.setVisible(true);
        this.loginUsernameField.textProperty().addListener((observable, oldValue, newValue) -> this.loginMenuCheck());
        this.loginPassField.textProperty().addListener((observable, oldValue, newValue) -> this.loginMenuCheck());
    }

    private void registerButtonClicked(MouseEvent event) {

        this.mainMenu.setVisible(false);
        this.registerMenu.setVisible(true);
        this.registerNameField.textProperty().addListener((observable, oldValue, newValue) -> this.registerMenuCheck());
        this.registerSurnameField.textProperty().addListener((observable, oldValue, newValue) -> this.registerMenuCheck());
        this.registerMailField.textProperty().addListener((observable, oldValue, newValue) -> this.registerMenuCheck());
        this.registerAgeField.textProperty().addListener((observable, oldValue, newValue) -> this.registerMenuCheck());
        this.registerUsernameField.textProperty().addListener((observable, oldValue, newValue) -> this.registerMenuCheck());
        this.registerPassField.textProperty().addListener((observable, oldValue, newValue) -> this.registerMenuCheck());
    }

    private void returnButtonClicked(MouseEvent event) {

        Button clickedButton = (Button)event.getSource();
        if(clickedButton.getId().equals("returnButton")) {
            this.loginUsernameField.setText(null);
            this.loginPassField.setText(null);
            this.loginMenu.setVisible(false);
            this.mainMenu.setVisible(true);
        }

        else {
            this.registerNameField.setText(null);
            this.registerSurnameField.setText(null);
            this.registerMailField.setText(null);
            this.registerAgeField.setText(null);
            this.registerUsernameField.setText(null);
            this.registerPassField.setText(null);
            this.registerMenu.setVisible(false);
            this.mainMenu.setVisible(true);
        }
    }

    @FXML
    void initialize() {

        db = Database.getDb();

        this.bst.insert(this.business);
        this.bst.insert(this.entertainment);
        this.bst.insert(this.health);
        this.bst.insert(this.science);
        this.bst.insert(this.technology);

        this.newsPane.setVisible(false);
        this.newsDetailScrollPane.setVisible(false);

        for (Node child : newsBox.getChildren()) {

            Pane pane = (Pane) child;
            Label label = (Label) pane.getChildren().get(0);
            this.newsLabels.add(label);
            label.setOnMouseEntered(this::mouseEntered);
            label.setOnMouseExited(this::mouseExited);
            label.setOnMouseClicked(this::labelClicked);
        }

        Image favoriteImage = new Image("file:icons/favorite.png");
        Image historyImage = new Image("file:icons/history.png");

        this.favoriteButton.setGraphic(new ImageView(favoriteImage));
        this.historyButton.setGraphic(new ImageView(historyImage));

        this.favoriteButton.setTooltip(new Tooltip("Show the most viewed category."));
        this.historyButton.setTooltip(new Tooltip("Back to the recently viewed news."));

        this.favoriteButton.setOnMouseEntered(this::hisfavMouseEntered);
        this.historyButton.setOnMouseEntered(this::hisfavMouseEntered);
        this.favoriteButton.setOnMouseExited(this::hisfavMouseExited);
        this.historyButton.setOnMouseExited(this::hisfavMouseExited);
        this.historyButton.setOnMouseClicked(this::historyButtonClicked);

        this.historyButton.setDisable(true);

        this.registerAgeField.textProperty().addListener((observable, oldValue, newValue) -> {  
            if (!newValue.matches("\\d*")) {
                this.registerAgeField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        this.exitButton.setOnMouseClicked(this::exitButtonClicked);
        this.loginButton.setOnMouseClicked(this::loginButtonClicked);
        this.registerButton.setOnMouseClicked(this::registerButtonClicked);
        this.returnButton.setOnMouseClicked(this::returnButtonClicked);
        this.returnButton1.setOnMouseClicked(this::returnButtonClicked);
        this.registerButton1.setOnMouseClicked(this::register);
        this.loginButton1.setOnMouseClicked(this::login);
        this.postCommentButton.setOnMouseClicked(this::postComment);
        this.showCommentsButton.setOnMouseClicked(this::showComments);
    }

}
