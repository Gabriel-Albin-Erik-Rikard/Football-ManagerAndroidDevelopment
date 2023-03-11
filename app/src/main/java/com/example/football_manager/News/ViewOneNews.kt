import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.football_manager.News.newsRepository
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ViewOneScreen(id: Int, navController: NavHostController) {
    val singleNews = newsRepository.getNewsById(id)

    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
            .fillMaxSize()
    ) {
        Text(
            text = " ${singleNews?.title}",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(
            modifier = Modifier
                .height(50.dp)
                .padding(10.dp)
        )
        Text(
            text = " ${singleNews?.content}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(60.dp))

        Row(modifier = Modifier.fillMaxWidth()) {

            val currentDate = remember { Calendar.getInstance().time }
            val formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(currentDate)
            Text(
                text = "Date:  $formattedDate",
                style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f)
            )
            Text(
                text = "Writer: ${singleNews?.writer}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(100.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { navController.navigate("viewOneEditedNews/${id}") },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = "Edit News")
            }
            val openDialog = remember { mutableStateOf(false) }

            Button(
                onClick = { openDialog.value = true },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "Delete News")
            }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Text(text = "Are You Sure?")
                    },
                    confirmButton = {
                        Button(onClick = {
                            openDialog.value = false
                            newsRepository.deleteNewsById(id)
                            navController.popBackStack()
                        }) {
                            Text(text = "Yes!")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { openDialog.value = false }
                        ) {
                            Text(text = "No!")
                        }
                    }
                )
            }
        }
    }
}
