import com.example.assignment1.db.MyDatabaseHelper
import com.example.assignment1.model.Goal
import com.example.assignment1.viewmodel.GoalViewModel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GoalViewModelTest {

    private lateinit var goalViewModel: GoalViewModel
    private val mockDbHelper: MyDatabaseHelper = mock()  // Mock the MyDatabaseHelper class

    @BeforeEach
    fun setUp() {
        // Initialize the GoalViewModel with the mocked database helper
        goalViewModel = GoalViewModel(mock())
    }

    @Test
    fun `test addGoal calls insertGoal in MyDatabaseHelper`() {
        // Arrange: Prepare the data
        val description = "Save for a new Laptop"
        val amount = 1500.0

        // Act: Call the addGoal function
        goalViewModel.addGoal(description, amount)

        // Assert: Verify that insertGoal method is called with the correct parameters
        verify(mockDbHelper).insertGoal(description, amount)
    }

    @Test
    fun `test refreshGoals updates goals correctly`() {
        // Arrange: Prepare the mock behavior for getAllGoals
        val mockGoals = listOf(Goal(description = "Save for a Laptop", amount = 1500.0))
        whenever(mockDbHelper.getAllGoals()).thenReturn(mockGoals)

        // Act: Call the refreshGoals function
        goalViewModel.refreshGoals()

        // Assert: Verify that the goals list was updated correctly
        Assertions.assertEquals(mockGoals, goalViewModel.goals.value)
    }
}
