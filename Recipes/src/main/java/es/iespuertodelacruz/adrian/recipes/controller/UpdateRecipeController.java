package es.iespuertodelacruz.adrian.recipes.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.JFXButton;
import es.iespuertodelacruz.adrian.recipes.dao.RecipeDAO;
import es.iespuertodelacruz.adrian.recipes.model.Recipe;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Adrián Rodríguez Fuentes
 */
public class UpdateRecipeController implements Initializable {

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtAuthor;

    @FXML
    private Spinner<Integer> spnPreparationTime;
    @FXML
    private Spinner<Integer> spnNumberDiners;
    @FXML
    private TextArea txaIngredients;
    @FXML
    private TextArea txaSteps;
    @FXML
    private TextField txtImage;
    @FXML
    private ComboBox<String> cmbTypeRecipe;
    @FXML
    private Slider sliderCalories;
    @FXML
    private Label lblcalories;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        spnNumberDiners.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        spnPreparationTime.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1));
        cmbTypeRecipe.getItems().addAll("Entrante", "Primero", "Segundo", "Postres");

        sliderCalories.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arge, Number arg1, Number arg2) {
                int temperatura = (int) sliderCalories.getValue();
                lblcalories.setText(Integer.toString(temperatura) + " kcl");
            }
        });
    }

    public void setRecipe(Recipe recipe) {
        txtTitle.setText(recipe.getTitle());
        txtAuthor.setText(recipe.getAuthor());
        spnNumberDiners.getValueFactory().setValue(recipe.getNumberDiners());
        spnPreparationTime.getValueFactory().setValue(recipe.getPreparationTime());
        sliderCalories.setValue(recipe.getCalories());
        lblcalories.setText(recipe.getCalories() + " kcl");
        cmbTypeRecipe.getSelectionModel().select(recipe.getTypeRecipe());
        String ingredients = "";
        for (int j = 0; j < recipe.getIngredients().size(); j++) {
            ingredients += recipe.getIngredients().get(j) + ",";
        }
        ingredients = ingredients.substring(0, ingredients.length() - 1);
        txaIngredients.setText(ingredients);

        String steps = "";
        for (int j = 0; j < recipe.getSteps().size(); j++) {
            steps += recipe.getSteps().get(j) + ",";
        }
        steps = steps.substring(0, steps.length() - 1);
        txaSteps.setText(steps);
        txtImage.setText(recipe.getImage());

    }

    @FXML
    private void uploadImage(MouseEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        File selectedFile = fileChooser.showOpenDialog(null);

        File archivo = selectedFile;
        Path pathFile = null;
        if (selectedFile != null) {
            pathFile = selectedFile.toPath();
            txtImage.setText(pathFile.toString());

        }
    }

    @FXML
    private void save(MouseEvent event) {
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        int preparationTime = spnPreparationTime.getValue();
        int numberDiners = spnNumberDiners.getValue();
        String ingredients = txaIngredients.getText();
        String steps = txaSteps.getText();
        String image = txtImage.getText();
        String typeRecipe = cmbTypeRecipe.getValue();
        double calories = (double) sliderCalories.getValue();

        if (title != null && !title.trim().isEmpty()
                && author != null && !author.trim().isEmpty()
                && ingredients != null && !ingredients.trim().isEmpty()
                && steps != null && !steps.trim().isEmpty()
                && image != null && !image.trim().isEmpty()
                && typeRecipe != null && !typeRecipe.trim().isEmpty()) {
            RecipeDAO recipeDAO = new RecipeDAO();
            Recipe recipe = new Recipe(title, author, typeRecipe, Arrays.asList(ingredients.split(",")), Arrays.asList(steps.split(",")), numberDiners, preparationTime, calories, image);
            recipeDAO.update(recipe);

            Stage stage = (Stage) txtTitle.getScene().getWindow();
            stage.close();
        }
    }

}
