package model_tests;

import model.Product;
import model.Product.WeightCategory;
import model.Product.Unit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductTests {
    private Product product;

    @BeforeAll
    public void setUp(){
        product = new Product("test", 1.4, 1.5, WeightCategory.ONE, Unit.KG);
    }

    // something weird going on here, 2nd test should fail but doesn't
    @Test
    public void weightCategoryShouldBeSetToFive(){
        product.setWeightCategory(WeightCategory.FIVE);

        assertEquals(5, product.getWeightCategory());
       // System.out.println(product.getWeightCategory() + " at " + System.nanoTime());
    }

    @Test
    public void weightCategoryShouldBeOne(){

       // System.out.println(product.getWeightCategory()  + " at " + System.nanoTime());
        assertEquals(1, product.getWeightCategory());
    }




    @AfterAll
    public void tearDown(){
        product = null;
    }
}
