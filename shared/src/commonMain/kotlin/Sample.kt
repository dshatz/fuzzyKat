import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dshatz.fuzzykat.diffutils.FuzzySearch

@Composable
fun Sample() {
    MaterialTheme {
        var elements by remember { mutableStateOf(listOf("USA", "France", "Canada", "Latvia", "Belgium", "Vietnam", "Ireland", "Jordan", "Japan")) }
        var input by remember { mutableStateOf("") }
        val results by produceState<List<Pair<String, Int>>>(emptyList(), input) {
            value = elements
                .associateWith { FuzzySearch.tokenSortRatio(it, input) }
                .entries
                .sortedByDescending { it.value }
                .map { it.key to it.value }
        }
        Card(modifier = Modifier.padding(20.dp)) {
            Column(Modifier.fillMaxSize().padding(10.dp)) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier.padding(bottom = 20.dp),
                    label = {
                        Text("Search")
                    }
                )

                LazyColumn(Modifier.fillMaxWidth()) {
                    results.forEach {
                        item {
                            Text(it.first + " (${it.second})", modifier = Modifier.padding(bottom = 5.dp))
                        }
                    }
                }
            }
        }
    }

}