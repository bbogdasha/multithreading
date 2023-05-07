package section5.part1;

import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CryptoPrices extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        double width = 300;
        double height = 250;

        stage.setTitle("Crypto prices");

        GridPane grid = createGrid();
        Map<String, Label> cryptoLabels = createCryptoPriceLabels();

        addLabelsToGrid(cryptoLabels, grid);

        Rectangle background = createBackgroundRectangle(width, height);

        root.getChildren().add(background);
        root.getChildren().add(grid);

        stage.setScene(new Scene(root, width, height));

        PricesContainer pricesContainer = new PricesContainer();
        PriceUpdater priceUpdater = new PriceUpdater(pricesContainer);

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (pricesContainer.getLockObject().tryLock()) {
                    try {
                        Label bitcoinLabel = cryptoLabels.get("BTC");
                        bitcoinLabel.setText(String.valueOf(pricesContainer.getBitcoinPrice()));

                        Label etherLabel = cryptoLabels.get("ETH");
                        etherLabel.setText(String.valueOf(pricesContainer.getEtherPrice()));

                        Label rippleLabel = cryptoLabels.get("XRP");
                        rippleLabel.setText(String.valueOf(pricesContainer.getRipplePrice()));
                    } finally {
                        pricesContainer.getLockObject().unlock();
                    }
                }
            }
        };

        animationTimer.start();

        priceUpdater.start();

        stage.show();
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        return grid;
    }

    private Map<String, Label> createCryptoPriceLabels() {
        Label bitcoinPriceLabel = new Label("0");
        bitcoinPriceLabel.setId("BTC");

        Label etherPriceLabel = new Label("0");
        etherPriceLabel.setId("ETH");

        Label ripplePriceLabel = new Label("0");
        ripplePriceLabel.setId("XRP");

        Map<String, Label> cryptoLabelsMap = new HashMap<>();
        cryptoLabelsMap.put("BTC", bitcoinPriceLabel);
        cryptoLabelsMap.put("ETH", etherPriceLabel);
        cryptoLabelsMap.put("XRP", ripplePriceLabel);

        return cryptoLabelsMap;
    }

    private void addLabelsToGrid(Map<String, Label> labels, GridPane grid) {
        int row = 0;

        for (Map.Entry<String, Label> entry : labels.entrySet()) {
            String cryptoName = entry.getKey();
            Label nameLabel = new Label(cryptoName);
            nameLabel.setTextFill(Color.BLUE);
            nameLabel.setOnMousePressed(mouseEvent -> nameLabel.setTextFill(Color.RED));
            nameLabel.setOnMouseReleased(mouseEvent -> nameLabel.setTextFill(Color.BLUE));

            grid.add(nameLabel, 0, row);
            grid.add(entry.getValue(), 1, row);

            row++;
        }
    }

    private Rectangle createBackgroundRectangle(double width, double height) {
        Rectangle background = new Rectangle(width, height);

        FillTransition fillTransition = new FillTransition(Duration.millis(2000), background, Color.LIGHTGREEN, Color.LIGHTBLUE);
        fillTransition.setCycleCount(Timeline.INDEFINITE);
        fillTransition.setAutoReverse(true);
        fillTransition.play();
        return background;
    }

    public static class PricesContainer {

        private Lock lockObject = new ReentrantLock();

        private double bitcoinPrice;
        private double etherPrice;
        private double ripplePrice;

        public Lock getLockObject() {
            return lockObject;
        }

        public double getBitcoinPrice() {
            return bitcoinPrice;
        }

        public void setBitcoinPrice(double bitcoinPrice) {
            this.bitcoinPrice = bitcoinPrice;
        }

        public double getEtherPrice() {
            return etherPrice;
        }

        public void setEtherPrice(double etherPrice) {
            this.etherPrice = etherPrice;
        }

        public double getRipplePrice() {
            return ripplePrice;
        }

        public void setRipplePrice(double ripplePrice) {
            this.ripplePrice = ripplePrice;
        }
    }

    public static class PriceUpdater extends Thread {

        private final String MAIN_URL = "https://api.coingecko.com/api/v3/simple/price?ids=%s&vs_currencies=usd";
        private final String BITCOIN_URL = String.format(MAIN_URL, "bitcoin");
        private final String ETHER_URL = String.format(MAIN_URL, "ethereum");
        private final String RIPPLE_URL = String.format(MAIN_URL, "ripple");

        private PricesContainer pricesContainer;

        public PriceUpdater(PricesContainer pricesContainer) {
            this.pricesContainer = pricesContainer;
        }

        @Override
        public void run() {

            while (true) {
                pricesContainer.getLockObject().lock();

                try {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    pricesContainer.setBitcoinPrice(getPrice(BITCOIN_URL));
                    pricesContainer.setEtherPrice(getPrice(ETHER_URL));
                    pricesContainer.setRipplePrice(getPrice(RIPPLE_URL));
                } finally {
                    pricesContainer.getLockObject().unlock();
                }

                try {
                    Thread.sleep(12000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static Double getPrice(String url) {
        String jsonContent = GetURL.getUrlContent(url);
        String price = jsonContent.substring(jsonContent.lastIndexOf(":") + 1, jsonContent.length() - 3);
        System.out.println(price);
        return Double.parseDouble(price);
    }
}
