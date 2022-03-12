import database.MyConnection;
import entity.*;
import org.junit.jupiter.api.*;
import repository.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryTest {
    private CustomerRepository customerRepository;
    private Customer customer;

    @BeforeAll
    public static void beforeAll() {
    }

    @BeforeEach
    public void beforeEach() throws SQLException {
        customerRepository = new CustomerRepository();

    }

    @Test
    public void connectionTest() {
        assertDoesNotThrow(() -> MyConnection.connection);
    }

    @Test
    public void saveTest() {
        //arrange
        customer = new Customer("hassan", "mohseni", "tehran");

        //act
        customerRepository.save(customer);

        //assert
        Customer loadedCustomer = customerRepository.findById(customer.getId());

        assertNotNull(loadedCustomer);
        assertEquals("mohseni", customer.getPassword());
    }

    @Test
    public void updateTest() {
        //arrange
        customer = new Customer("hassan", "mohseni", "tehran");
        customerRepository.save(customer);

        //act
        customer.setAddress("teh");
        customerRepository.update(customer);

        //assert
        Customer loadedCustomer = customerRepository.findById(customer.getId());
        assertEquals("teh", loadedCustomer.getAddress());
    }

    @Test
    public void deleteTest() {
        //arrange
        customer = new Customer("hassan", "mohseni", "tehran");
        Integer customerId = customerRepository.save(customer);

        //act

        customerRepository.delete(customer.getId());

        //assert
        Customer loadedCustomer = customerRepository.findById(customerId);
        assertNull(loadedCustomer);
    }


    @Test
    public void findByIdTest() {
        //arrange
        customer = new Customer("hassan", "mohseni", "tehran");

        //act
        customerRepository.save(customer);
        customerRepository.findById(customer.getId());
        //assert
        List<Customer> accountList = customerRepository.findAll();
        assertAll(
                () -> assertEquals(customer.getId(), accountList.get(0).getId()),
                () -> assertEquals(customer.getUsername(), accountList.get(0).getUsername()),
                () -> assertEquals(customer.getPassword(), accountList.get(0).getPassword())
        );
    }

    @Test
    public void findAllTest() {
        //arrange
        customer = new Customer("hassan", "mohseni", "tehran");
        Customer customer2 = new Customer("hassan2", "mohseni2", "tehran");


        //act
        customerRepository.save(customer);
        customerRepository.save(customer2);

        //assert
        assertEquals(2, customerRepository.findAll().size());
    }

    @Test
    public void findOrderByUserIdTest() {
        //arrange
        customer = new Customer("hassan", "mohseni", "tehran");
        customerRepository.save(customer);
        ShoppingCardRepository shoppingCardRepository = new ShoppingCardRepository();
        OrderRepository orderRepository = new OrderRepository();
        CategoryRepository categoryRepository = new CategoryRepository();
        ProductRepository productRepository = new ProductRepository();
        ShoppingCard shoppingCard1 = new ShoppingCard(Date.valueOf("2022-02-01"));
        ShoppingCard shoppingCard2 = new ShoppingCard(Date.valueOf("2022-02-11"));
        Category category1 = new Category("laptop", "cell phone", 1);
        Product product1 = new Product("ipad 2018", "ipad 2018", category1, 300, 30000000);


        //act
        category1.setId(categoryRepository.save(category1));
        product1.setId(productRepository.save(product1));
        shoppingCard1.setId(shoppingCardRepository.save(shoppingCard1));
        shoppingCard2.setId(shoppingCardRepository.save(shoppingCard2));
        Orders order1 = new Orders(product1.getId(), customer.getId(), shoppingCard1);
        Orders order2 = new Orders(product1.getId(), customer.getId(), shoppingCard2);
        order1.setId(orderRepository.save(order1));
        order2.setId(orderRepository.save(order2));


        //assert
        List<Orders> orderList = customerRepository.findOrderByUserId(customer.getId());
        assertEquals(2, orderList.size());
    }

    @Test
    public void loginTest() {
        //arrange
        customer = new Customer("hassan", "mohseni", "tehran");


        //act
        customerRepository.save(customer);

        //assert
        Customer loadedCustomer = customerRepository.login(customer.getUsername(), customer.getPassword());

        assertAll(
                () -> assertEquals("hassan", loadedCustomer.getUsername()),
                () -> assertEquals("mohseni", loadedCustomer.getPassword())
        );
    }


    @AfterEach
    public void afterEach() throws SQLException {
        String sql = "delete from customer";
        PreparedStatement preparedStatement = MyConnection.connection.prepareStatement(sql);
        preparedStatement.execute();
        preparedStatement.close();
    }

    @AfterAll
    public static void afterAll() {

    }
}