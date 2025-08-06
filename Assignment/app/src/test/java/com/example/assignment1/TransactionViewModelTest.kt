import com.example.assignment1.db.MyDatabaseHelper
import com.example.assignment1.model.Transaction
import com.example.assignment1.viewmodel.TransactionViewModel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class TransactionViewModelTest {

    private lateinit var viewModel: TransactionViewModel
    private val mockDbHelper: MyDatabaseHelper = mock()  // Mock the MyDatabaseHelper class

    @BeforeEach
    fun setUp() {
        // Initialize the view model with the mocked database helper
        viewModel = TransactionViewModel(mockDbHelper)
    }

    @Test
    fun `test addTransaction calls insertTransaction in MyDatabaseHelper`() {
        // Arrange: Prepare the data
        val description = "Test Transaction"
        val amount = 100.0
        val date = "2025-04-12"
        val category = "Food"

        // Act: Call the addTransaction function
        viewModel.addTransaction(description, amount, date, category)

        // Assert: Verify that the insertTransaction method is called with the correct parameters
        verify(mockDbHelper).insertTransaction(description, amount, date, category)
    }

    @Test
    fun `test deleteTransaction updates balance correctly`() {
        // Arrange: Prepare the data
        val transaction = Transaction(1, "Test Transaction", 100.0, "2025-04-12", "Food")
        val initialBalance = 500.0
        whenever(mockDbHelper.getAllTransactions()).thenReturn(listOf(transaction))

        // Setting up initial balance
        viewModel.updateBalance(initialBalance)

        // Act: Delete the transaction (simulate)
        viewModel.deleteTransaction(transaction)

        // Assert: Verify that the balance was updated correctly
        val updatedBalance = initialBalance - transaction.value
        Assertions.assertEquals(updatedBalance, viewModel.balance.value)
    }
}
